package com.juntai.wisdom.basecomponent.base;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.net.FileRetrofit;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.PubUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Describe:视频下载
 * Create by zhangzhenlong
 * 2021-1-13
 * email:954101549@qq.com
 */
public abstract class BaseDownLoadFragment<P extends BasePresenter> extends BaseMvpFragment<P>{
    /**
     * 获取保存路径
     *
     * @return
     */
    private String getSavePath(String downloadPath) {
        String path = null;
        if (!TextUtils.isEmpty(downloadPath)) {
            if (downloadPath.contains(".mp4")) {
                path = FileCacheUtils.getAppVideoPath() + downloadPath.substring(downloadPath.lastIndexOf("/") + 1,
                        downloadPath.length());
            } else {
                if (downloadPath.contains(".jpeg") || downloadPath.contains(".jpg") || downloadPath.contains(".png") || downloadPath.contains(".svg")) {
                    path = FileCacheUtils.getAppImagePath() + downloadPath.substring(downloadPath.lastIndexOf("/") + 1, downloadPath.length());
                } else {
                    //巡检图片  直接从crm读取的"xunjiantubiao" +
                    path = FileCacheUtils.getAppImagePath() + downloadPath.substring(downloadPath.lastIndexOf("/") + 1, downloadPath.length()) + ".jpeg";

                }
            }
        }
        return path;
    }

    /**
     * 下载文件
     */
    public void downloadFileContent(String  downLoadUrl) {
        String savePath = getSavePath(downLoadUrl);
        if (!TextUtils.isEmpty(savePath)){
            final File file = new File(savePath);
            ToastUtils.toast(mContext, "已保存");
            downFileLogic(downLoadUrl, file);
        }else {
            ToastUtils.toast(mContext, "无效的下载地址");
        }
    }

    @SuppressLint("CheckResult")
    private void downFileLogic(String downloadPath, File file) {
        FileRetrofit.getInstance().getFileService()
                .getFile_GET(downloadPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        try {
                            //responseBody里的数据只可以读取一次
                            saveFileToLocal(file, responseBody.byteStream());
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e(e.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(throwable.toString());
                    }
                });
    }

    /**
     * 缓存文件到本地
     *
     * @param ins
     */
    public void saveFileToLocal(File file, InputStream ins) {
        try {
            LogUtil.d("----->in");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            PubUtil.sendBroadcastToAlbum(mContext,file.getAbsolutePath());
            LogUtil.d("----->ok");
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("----->error-" + e.toString());
        }
    }
}

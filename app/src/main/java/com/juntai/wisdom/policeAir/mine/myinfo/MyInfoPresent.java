package com.juntai.wisdom.policeAir.mine.myinfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.GlideEngine4;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.UserBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.utils.RxScheduler;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/10
 * email:954101549@qq.com
 */
public class MyInfoPresent extends BasePresenter<IModel, MyCenterContract.IMyInfoView> implements MyCenterContract.IMyInfoPresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    @SuppressLint("CheckResult")
    @Override
    public void imageChoose() {
        new RxPermissions((MyInformationActivity)getView())
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from((MyInformationActivity)getView())
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                    .countable(true)
                                    .maxSelectable(1)                     // 最多选择一张
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(MyInformationActivity.REQUEST_CODE_CHOOSE);
                        } else {
                            Toasty.info((MyInformationActivity) getView(), "请给与相应权限").show();
                        }
                    }
                });
    }

    @Override
    public void postHead(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("token", MyApp.getUserToken())
                .addFormDataPart("id", MyApp.getUid()+"")
                .addFormDataPart("file", "file",
                        RequestBody.create(MediaType.parse("file"),
                                file));
        RequestBody requestBody = builder.build();
        AppNetModule.createrRetrofit()
                .modifyHeadPortrait(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        if (getView() != null){
                            getView().onSuccess(MyCenterContract.UPDATE_HEAD_TAG,baseResult);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(MyCenterContract.UPDATE_HEAD_TAG,msg);
                        }
                    }
                });
    }

    @Override
    public void yasuo(String path) {
        Luban.with((MyInformationActivity)getView())
                .load(path)
                .ignoreBy(100)
                .setTargetDir(FileCacheUtils.getAppImagePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        //  压缩开始前调用，可以在方法内启动 loading UI
//                        getView().showLoading();
                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        getView().onSuccess(MyCenterContract.YASUO_HEAD_TAG,file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  当压缩过程出现问题时调用
                        getView().onError("yasuo","图片压缩失败");
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
//                        getView().hideLoading();
                    }
                }).launch();
    }

    @Override
    public void getUserData(String tag) {
        AppNetModule.createrRetrofit()
                .getUserData(MyApp.getAccount(), MyApp.getUserToken())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(getView()) {
                    @Override
                    public void onSuccess(UserBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
}

package com.juntai.wisdom.policeAir.base;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.utils.MD5;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.policeAir.AppHttpPath;
import com.juntai.wisdom.policeAir.R;
import com.skl.networkmonitor.NetType;
import com.skl.networkmonitor.NetworkLiveData;
import com.videoaudiocall.bean.MessageBodyBean;
import com.videoaudiocall.webSocket.MyWsManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setBackgroundResource(R.drawable.sp_filled_gray_lighter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        NetworkLiveData.get(getApplicationContext()).regist();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetworkLiveData.get(getApplicationContext()).regist();
    }

    /**
     * @param
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMessage(EventBusObject eventBusObject) {
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.NETWORK_STATUS:
                NetType netType = (NetType) eventBusObject.getEventObj();
                switch (netType) {
                    case NET_UNKNOW:
                    case NET_4G:
                    case NET_3G:
                    case NET_2G:
                        //有网络
                    case WIFI:
                        MyWsManager.getInstance() .init(getApplicationContext()).startConnect();
                        break;
                    case NOME:
                        //没有网络，提示用户跳转到设置
                        onError("","未连接,请检查网络是否正常");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取文件名称
     *
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileName(MessageBodyBean messageBodyBean) {
        String content = messageBodyBean.getContent();
        return getSavedFileName(content);
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    public String getSavedFileName(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/") + 1, content.length());
        }
        return content;
    }

    /**
     * 获取文件名称  后缀
     *
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileNameWithoutSuffix(MessageBodyBean messageBodyBean) {
        String content = messageBodyBean.getContent();
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/") + 1, content.lastIndexOf("."));
        }
        return content;
    }





    @Override
    protected String getUpdateHttpUrl() {
        return AppHttpPath.APP_UPDATE;
    }


    /**
     * 发送更新头像的广播
     */
    public void broadcasetRefreshHead() {
        Intent intent = new Intent();
        intent.setAction("action.refreshHead");
        sendBroadcast(intent);
    }
//    /**
//     * 获取builder
//     *
//     * @return
//     */
//    public FormBody.Builder getBaseBuilderWithoutUserId() {
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("account", UserInfoManager.getUserAccount());
//        builder.add("token", UserInfoManager.getUserToken());
//        return builder;
//    }


    //    /**
    //     * 是否是内部账号
    //     *
    //     * @return
    //     */
    //    public boolean isInnerAccount() {
    //        return UserInfoManager.isTest();

    //    }



    /**
     * 加密密码
     *
     * @param pwd
     * @return
     */
    protected String encryptPwd(String account, String pwd) {
        return MD5.md5(String.format("%s#%s", account, pwd));
    }

    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }



    public String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(mBaseRootCol);
        super.onBackPressed();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

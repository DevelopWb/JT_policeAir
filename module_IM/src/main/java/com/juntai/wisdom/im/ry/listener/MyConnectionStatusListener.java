package com.juntai.wisdom.im.ry.listener;

import android.content.Intent;

import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.LogUtil;

import io.rong.imlib.RongIMClient;

/**
 * 连接状态监听
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus){
            case CONNECTED://连接成功。
                LogUtil.d("RongYun-MyConnectionStatusListener：连接状态 = 成功");
                break;
            case DISCONNECTED://断开连接。
                LogUtil.e("RongYun-MyConnectionStatusListener：连接状态 = 断开连接");
                break;
            case CONNECTING://连接中。
                LogUtil.d("RongYun-MyConnectionStatusListener：连接状态 = 连接中");
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                LogUtil.e("RongYun-MyConnectionStatusListener：连接状态 = 网络不可用");
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                BaseApplication.app.sendBroadcast(new Intent()
                        .setAction(ActionConfig.BROAD_LOGIN)
                        .putExtra("error","用户其他设备登录"));//
                LogUtil.e("RongYun-MyConnectionStatusListener：连接状态 = 用户账户在其他设备登录");
                break;
        }
    }
}
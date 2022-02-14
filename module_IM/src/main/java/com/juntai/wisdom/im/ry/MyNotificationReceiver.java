package com.juntai.wisdom.im.ry;

import android.content.Context;

import com.juntai.wisdom.basecomponent.utils.LogUtil;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by Ma
 * on 2019/4/13
 */
public class MyNotificationReceiver extends PushMessageReceiver {
    /**
     * 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun: 融云通知栏消息到达: "  + pushNotificationMessage.getObjectName() + "  content: " +pushNotificationMessage.getPushContent());
        //用来接收服务器发来的通知栏消息
        return false;
    }

    /**
     * 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun: 点击通知栏消息: "  + pushNotificationMessage.getObjectName() + "  content: " +pushNotificationMessage.getPushContent());
        //用户点击通知栏消息时触发
        return false;
    }
}

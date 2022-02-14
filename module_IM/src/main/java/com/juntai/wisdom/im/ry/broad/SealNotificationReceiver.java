package com.juntai.wisdom.im.ry.broad;

import android.content.Context;

import com.juntai.wisdom.basecomponent.utils.LogUtil;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Push 消息监听
 * @aouther Ma
 * @date 2019/4/2
 */
public class SealNotificationReceiver extends PushMessageReceiver {

    /**
     * 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun Push 消息监听 onNotificationMessageArrived = " + pushNotificationMessage.getPushContent());
        return false;
    }

    /**
     * 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
     * @param context
     * @param pushType
     * @param pushNotificationMessage
     * @return
     */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtil.d("RongYun Push 消息监听 onNotificationMessageClicked = " + pushNotificationMessage.getPushContent());
        return false;
    }
}
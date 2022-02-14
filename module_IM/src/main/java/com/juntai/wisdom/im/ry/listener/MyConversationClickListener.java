package com.juntai.wisdom.im.ry.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.LogUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

/**
 * 会话界面操作
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyConversationClickListener implements RongIM.ConversationClickListener {

    /**
     * 当点击用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
        LogUtil.d("RongYun-会话界面 onUserPortraitClick = " + user.toString());
        return false;
    }

    /**
     * 当长按用户头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param user             被点击的用户的信息。
     * @param targetId         会话 id
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
        LogUtil.d("RongYun-会话界面 onUserPortraitLongClick = " + user.toString());
        return false;
    }

    /**
     * 当点击消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被点击的消息的实体信息。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        LogUtil.d("RongYun-会话界面 onMessageClick = " + message.toString());
        LogUtil.d("RongYun-会话界面 onMessageClick = " + message.getObjectName());
        if ("RC:LBSMsg".equals(message.getObjectName())){
            if ("RC:LBSMsg".equals(message.getObjectName())){
                LocationMessage locationMessage = (LocationMessage) message.getContent();
                Intent intent = new Intent();
                intent.setAction(ActionConfig.ACTION_LOCATION_LOOK);
                intent.putExtra("lat",locationMessage.getLat());
                intent.putExtra("lng",locationMessage.getLng());
                intent.putExtra("address",locationMessage.getPoi());
                context.startActivity(intent);
            }
        }
        //
        return false;
    }

    /**
     * 当点击链接消息时执行。
     *
     * @param context 上下文。
     * @param link    被点击的链接。
     * @param message 被点击的消息的实体信息
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true， 否则返回 false, false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLinkClick(Context context, String link, Message message) {
//        LogUtil.d(((LocationMessage) message.getContent()).getPoi());
        //LocationMessage
        LogUtil.d("RongYun-会话界面 onMessageLinkClick = " + message.toString());
        return false;
    }

    /**
     * 当长按消息时执行。
     *
     * @param context 上下文。
     * @param view    触发点击的 View。
     * @param message 被长按的消息的实体信息。
     * @return 如果用户自己处理了长按后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        LogUtil.d("RongYun-会话界面 onMessageLongClick = " + message.toString());
        return false;
    }
}
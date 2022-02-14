package com.juntai.wisdom.im.ry.listener;

import android.content.Context;
import android.view.View;

import com.juntai.wisdom.basecomponent.utils.LogUtil;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

/**
 * 会话列表操作监听
 * @aouther Ma
 * @date 2019/4/6
 */
public class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        LogUtil.d("RongYun-会话列表操作监听 onConversationPortraitClick = " + s);
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        LogUtil.d("RongYun-会话列表操作监听 onConversationPortraitLongClick = " + s);
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        LogUtil.d("RongYun-会话列表操作监听 onConversationLongClick = " + uiConversation.getExtra());
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        LogUtil.d("RongYun-会话列表操作监听 onConversationClick = " + uiConversation.getExtra());
        return false;
    }
}

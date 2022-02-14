package com.juntai.wisdom.im;

import android.net.Uri;
import android.os.Bundle;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.fragment.SubConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * @aouther Ma
 * @date 2019/4/6
 * ConversationListFragment   -   conversationlist
 * SubConversationListFragment   --   subconversationlist
 */
public class MsgFragment extends ConversationListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")//subconversationlist
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话，该会话非聚合显示
//                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
//                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                .build();
        setUri(uri);

        super.onCreate(savedInstanceState);
    }
}

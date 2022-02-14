package com.juntai.wisdom.im;

import android.net.Uri;
import android.support.v4.app.FragmentManager;

import com.juntai.wisdom.basecomponent.base.BaseActivity;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * 会话activity
 * @aouther Ma
 * @date 2019/4/5
 */
public class ChatActivity extends BaseActivity {
    @Override
    public int getLayoutView() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {

        String mTargetId = getIntent().getData().getQueryParameter("targetId");
        String mTargetIds = getIntent().getData().getQueryParameter("targetIds");
        //Log.e("ffffffffff",mTargetId+"   "+mTargetIds);
        //Log.e("ffffffffff",getIntent().getData().toString());
        String title  = getIntent().getData().getQueryParameter("title");
        setTitleName(title);
        setAutoHideKeyboard(false);

        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragement.setUri(uri);
    }

    @Override
    public void initData() {

    }
    public void setStatusListener(){
//        RongIMClient.setTypingStatusListener(new RongIMClient.TypingStatusListener() {
//            @Override
//            public void onTypingStatusChanged(Conversation.ConversationType type, String targetId, Collection<TypingStatus> typingStatusSet) {
//                //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
//                if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
//                    //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
//                    int count = typingStatusSet.size();
//                    if (count > 0) {
//                        Iterator iterator = typingStatusSet.iterator();
//                        TypingStatus status = (TypingStatus) iterator.next();
//                        String objectName = status.getTypingContentType();
//
//                        MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
//                        MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
//                        //匹配对方正在输入的是文本消息还是语音消息
//                        if (objectName.equals(textTag.value())) {
//                            //显示“对方正在输入”
//                            mHandler.sendEmptyMessage(SET_TEXT_TYPING_TITLE);
//                        } else if (objectName.equals(voiceTag.value())) {
//                            //显示"对方正在讲话"
//                            mHandler.sendEmptyMessage(SET_VOICE_TYPING_TITLE);
//                        }
//                    } else {
//                        //当前会话没有用户正在输入，标题栏仍显示原来标题
//                        mHandler.sendEmptyMessage(SET_TARGETID_TITLE);
//                    }
//                }
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

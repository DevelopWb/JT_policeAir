package com.videoaudiocall;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juntai.wisdom.basecomponent.utils.UserInfoManager;
import com.videoaudiocall.bean.MessageBodyBean;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-09-29 10:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-29 10:57
 */
public class OperateMsgUtil {

    /**
     * 获取私聊文本消息
     *
     * @param toUserAccout
     * @param toNickName
     * @param content
     * @param msgType      msgType":"消息类型（0：text；1：image；2：video；3：语音；4视频通话；5音频通话，6位置消息 7分享名片 8 文件9 合并群消息 10 外部分享链接"  11 切换摄像头 （content 0是前置 1是后置）,
     * @return
     */
    public static MessageBodyBean getPrivateMsg(int msgType, String toUserAccout, String toNickName, String toHead, String content) {
        MessageBodyBean messageBody = new MessageBodyBean();
        messageBody.setContent(content);
        messageBody.setCreateTime(String.valueOf(System.currentTimeMillis()));
        /**
         * 使用userid作为唯一值 链接socket
         */
        messageBody.setFromAccount(String.valueOf(UserInfoManager.getUserId()));
        messageBody.setFromNickname(UserInfoManager.getUserNickName());
        messageBody.setFromHead(UserInfoManager.getHeadPic());
        messageBody.setToAccount(toUserAccout);
        messageBody.setToNickname(toNickName);
        messageBody.setToHead(toHead);
        messageBody.setChatType(1);
        messageBody.setMsgType(msgType);
        return messageBody;
    }

    /**
     * todo 不能用json传  因为sdp很大
     * @param messageBodyBean
     * @return
     */
    public static MultipartBody.Builder getMsgBuilder(MessageBodyBean messageBodyBean) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", UserInfoManager.getUserToken())
                .addFormDataPart("userId", String.valueOf(UserInfoManager.getUserId()))
                .addFormDataPart("type", "1")
                .addFormDataPart("fromAccount", messageBodyBean.getFromAccount())
                .addFormDataPart("fromNickname", messageBodyBean.getFromNickname())
                .addFormDataPart("event", messageBodyBean.getEvent())
                .addFormDataPart("fromHead", messageBodyBean.getFromHead())
                .addFormDataPart("toAccount", TextUtils.isEmpty(messageBodyBean.getToAccount()) ? "0" : messageBodyBean.getToAccount())
                .addFormDataPart("toNickname", messageBodyBean.getToNickname())
                .addFormDataPart("toHead", messageBodyBean.getToHead())
                .addFormDataPart("content", messageBodyBean.getContent())
                .addFormDataPart("sdp", messageBodyBean.getSdp())
                .addFormDataPart("sdpMid", messageBodyBean.getSdpMid())
                .addFormDataPart("sdpMLineIndex", String.valueOf(messageBodyBean.getSdpMLineIndex()))
                .addFormDataPart("faceTimeType", String.valueOf(messageBodyBean.getFaceTimeType()))
                .addFormDataPart("msgType", String.valueOf(messageBodyBean.getMsgType()))
                .addFormDataPart("chatType", String.valueOf(messageBodyBean.getChatType()));
        return builder;

    }


    public static List<MessageBodyBean> changeGsonToList(String gsonString) {
        Gson gson = new Gson();
        List<MessageBodyBean> list = gson.fromJson(gsonString, new TypeToken<List<MessageBodyBean>>() {
        }.getType());
        return list;
    }

    public static RequestBody getJsonRequestBody(String jsonStr){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON,jsonStr);
    }
}

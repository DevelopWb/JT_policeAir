package com.videoaudiocall;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.GsonTools;
import com.juntai.wisdom.basecomponent.utils.UserInfoManager;
import com.videoaudiocall.bean.MessageBodyBean;
import com.videoaudiocall.videocall.VideoRequestActivity;

import java.util.ArrayList;
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
     * @param toUserId
     * @param toUserAccout
     * @param toNickName
     * @param content
     * @param msgType      msgType":"消息类型（0：text；1：image；2：video；3：语音；4视频通话；5音频通话，6位置消息 7分享名片 8 文件9 合并群消息 10 外部分享链接"  11 切换摄像头 （content 0是前置 1是后置）,
     * @return
     */
    public static MessageBodyBean getPrivateMsg(int msgType, int toUserId, String toUserAccout, String toNickName, String toHead, String content) {
        MessageBodyBean messageBody = new MessageBodyBean();
        messageBody.setContent(content);
        messageBody.setCreateTime(String.valueOf(System.currentTimeMillis()));
        /**
         * 使用userid作为唯一值 链接socket
         */
        messageBody.setFromAccount(String.valueOf(UserInfoManager.getUserId()));
        messageBody.setFromNickname(UserInfoManager.getUserNickName());
        messageBody.setFromHead(UserInfoManager.getHeadPic());
        messageBody.setFromUserId(UserInfoManager.getUserId());
        messageBody.setRead(true);
        // TODO: 2021-11-19 阅后即焚  先默认1 否
        messageBody.setReadBurn(1);
        messageBody.setToAccount(String.valueOf(toUserId));
        messageBody.setToNickname(toNickName);
        messageBody.setToHead(toHead);
        messageBody.setToUserId(toUserId);
        messageBody.setChatType(1);
        messageBody.setMsgType(msgType);
        return messageBody;
    }
    public static RequestBody getMsgBuilder(MessageBodyBean messageBodyBean) {
        return  getJsonRequestBody(GsonTools.createGsonString(messageBodyBean));

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

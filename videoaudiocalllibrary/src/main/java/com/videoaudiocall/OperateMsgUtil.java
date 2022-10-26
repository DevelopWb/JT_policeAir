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

import okhttp3.MultipartBody;

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
//        messageBody.setFromAccount(UserInfoManager.getUserUUID());
        messageBody.setFromNickname(UserInfoManager.getUserNickName());
        messageBody.setFromHead(UserInfoManager.getHeadPic());
        messageBody.setFromUserId(UserInfoManager.getUserId());
        messageBody.setRead(true);
        // TODO: 2021-11-19 阅后即焚  先默认1 否
        messageBody.setReadBurn(1);
        messageBody.setToAccount(toUserAccout);
        messageBody.setToNickname(toNickName);
        messageBody.setToHead(toHead);
        messageBody.setToUserId(toUserId);
        messageBody.setChatType(1);
        messageBody.setMsgType(msgType);
        return messageBody;
    }
    public static MultipartBody.Builder getMsgBuilder(MessageBodyBean messageBodyBean) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", UserInfoManager.getUserToken())
                .addFormDataPart("userId", String.valueOf(UserInfoManager.getUserId()))
                .addFormDataPart("type", "1")
                .addFormDataPart("fromUserId", String.valueOf(messageBodyBean.getFromUserId()))
                .addFormDataPart("fromAccount", messageBodyBean.getFromAccount())
                .addFormDataPart("fromNickname", messageBodyBean.getFromNickname())
                .addFormDataPart("event", messageBodyBean.getEvent())
                .addFormDataPart("fromHead", messageBodyBean.getFromHead())
                .addFormDataPart("toUserId", String.valueOf(messageBodyBean.getToUserId()))
                .addFormDataPart("toAccount", TextUtils.isEmpty(messageBodyBean.getToAccount()) ? "0" : messageBodyBean.getToAccount())
                .addFormDataPart("toNickname", messageBodyBean.getToNickname())
                .addFormDataPart("rotation", messageBodyBean.getRotation())
                .addFormDataPart("toHead", messageBodyBean.getToHead())
                .addFormDataPart("otherUserId", String.valueOf(messageBodyBean.getOtherUserId()))
                .addFormDataPart("otherAccount", messageBodyBean.getOtherAccount())
                .addFormDataPart("otherNickname", messageBodyBean.getOtherNickname())
                .addFormDataPart("otherHead", messageBodyBean.getOtherHead())
                .addFormDataPart("content", messageBodyBean.getContent())
                .addFormDataPart("sdp", messageBodyBean.getSdp())
                .addFormDataPart("sdpMid", messageBodyBean.getSdpMid())
                .addFormDataPart("sdpMLineIndex", String.valueOf(messageBodyBean.getSdpMLineIndex()))
                .addFormDataPart("duration", messageBodyBean.getDuration())
                .addFormDataPart("videoCover", messageBodyBean.getVideoCover())
                .addFormDataPart("fileSize", messageBodyBean.getFileSize())
                .addFormDataPart("fileName", messageBodyBean.getFileName())
//                .addFormDataPart("hwPushIntentUrl", OperateMsgUtil.getHuaWeiPushIntentStr(messageBodyBean))
//                .addFormDataPart("xiaomiPushIntentUrl", OperateMsgUtil.getXiaomiPushIntentStr(messageBodyBean))
                .addFormDataPart("faceTimeType", String.valueOf(messageBodyBean.getFaceTimeType()))
                .addFormDataPart("readBurn", String.valueOf(messageBodyBean.getReadBurn()))
                .addFormDataPart("msgType", String.valueOf(messageBodyBean.getMsgType()))
                .addFormDataPart("groupNickname", messageBodyBean.getGroupUserNickname())
                .addFormDataPart("groupName", messageBodyBean.getGroupName())
                .addFormDataPart("quoteMsg", messageBodyBean.getQuoteMsg())
                .addFormDataPart("chatType", String.valueOf(messageBodyBean.getChatType()));
        if (messageBodyBean.getFromUserId() == UserInfoManager.getUserId()) {
            //我发送的信息
            //收藏的时候需要上传
            builder.addFormDataPart("localCatchPath", String.valueOf(messageBodyBean.getLocalCatchPath()));
        }
        if (2 == messageBodyBean.getChatType()) {
            builder.addFormDataPart("groupId", String.valueOf(messageBodyBean.getGroupId()));
            builder.addFormDataPart("isGroupCreater", String.valueOf(messageBodyBean.getIsGroupCreater()));
            if (!TextUtils.isEmpty(messageBodyBean.getAtUserId())) {
                builder.addFormDataPart("atUserId", messageBodyBean.getAtUserId());
            }

        }
        switch (messageBodyBean.getMsgType()) {
            case 6:
                builder.addFormDataPart("lat", messageBodyBean.getLat())
                        .addFormDataPart("lng", messageBodyBean.getLng())
                        .addFormDataPart("addrName", messageBodyBean.getAddrName())
                        .addFormDataPart("addrDes", messageBodyBean.getAddrDes());
                break;
            case 11:
                //外部分享的链接
                builder.addFormDataPart("shareTitle", messageBodyBean.getShareTitle())
                        .addFormDataPart("shareContent", messageBodyBean.getShareContent())
                        .addFormDataPart("shareUrl", messageBodyBean.getShareUrl())
                        .addFormDataPart("shareAppName", messageBodyBean.getShareAppName())
                        .addFormDataPart("sharePic", messageBodyBean.getSharePic());
                break;
            default:
                break;
        }
        return builder;

    }


    public static List<MessageBodyBean> changeGsonToList(String gsonString) {
        Gson gson = new Gson();
        List<MessageBodyBean> list = gson.fromJson(gsonString, new TypeToken<List<MessageBodyBean>>() {
        }.getType());
        return list;
    }
}

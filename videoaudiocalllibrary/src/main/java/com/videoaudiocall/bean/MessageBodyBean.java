package com.videoaudiocall.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-09-29 9:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-29 9:39
 */
public class MessageBodyBean extends BaseWsMessageBean implements Parcelable {

    /**
     * fromUserId : 101
     * fromAccount : 发送者账号
     * toUserId : 97
     * toAccount : 接收者账号
     * content : 消息内容
     * date : 2021-09-29 09:35:00
     * "faceTimeType":"音视频通话状态（1发起通话；2接听；3结束）",
     * "msgType":"消息类型（0：text；1：image；2：video；3：语音；4视频通话；5音频通话，6位置消息，7分享名片，8文件，9合并消息 10 外部分享的链接）",
     * "chatType":"聊天类型（1：私聊；2公聊；4好友添加）",
     * groupId : 群组id；仅chat_type为2时需要
     */
    public long id;
    private String fromAccount;
    private String fromNickname;
    private String fromHead;
    private String toAccount;
    private String toNickname;
    private String toHead;
    private String content;
    private String createTime;
    /**
     * 1已接听；2已取消；3超时；4通话中断
     */
    private int faceTimeType;
    /**
     * 0：text；1：image；2：video；3：语音；4视频通话；5音频通话，6位置消息，7分享名片，8文件9合并消息 10 通知提示消息  11 外部分享的链接 100  消息时间
     */
    private int msgType;
    /**
     * * "chatType":"聊天类型（1：私聊；2公聊；4好友添加）",
     */
    private int chatType;



    //视频通话相关
    private String event;//

    private String sdp;//
    private int sdpMLineIndex;//
    private String sdpMid;//




    public String getSdp() {
        return sdp == null ? "" : sdp;
    }

    public String getEvent() {
        return event == null ? "" : event;
    }

    public void setEvent(String event) {
        this.event = event == null ? "" : event;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp == null ? "" : sdp;
    }

    public int getSdpMLineIndex() {
        return sdpMLineIndex;
    }

    public void setSdpMLineIndex(int sdpMLineIndex) {
        this.sdpMLineIndex = sdpMLineIndex;
    }

    public String getSdpMid() {
        return sdpMid == null ? "" : sdpMid;
    }

    public void setSdpMid(String sdpMid) {
        this.sdpMid = sdpMid == null ? "" : sdpMid;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public int getFaceTimeType() {
        return faceTimeType;
    }

    public void setFaceTimeType(int faceTimeType) {
        this.faceTimeType = faceTimeType;
    }

    public String getFromHead() {
        return fromHead == null ? "" : fromHead;
    }

    public void setFromHead(String fromHead) {
        this.fromHead = fromHead == null ? "" : fromHead;
    }

    public String getToHead() {
        return toHead == null ? "" : toHead;
    }

    public void setToHead(String toHead) {
        this.toHead = toHead == null ? "" : toHead;
    }


    public String getFromNickname() {
        return fromNickname == null ? "" : fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname == null ? "" : fromNickname;
    }

    public String getToNickname() {
        return toNickname == null ? "" : toNickname;
    }

    public void setToNickname(String toNickname) {
        this.toNickname = toNickname == null ? "" : toNickname;
    }


    public String getFromAccount() {
        return fromAccount == null ? "" : fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? "" : fromAccount;
    }


    public String getToAccount() {
        return toAccount == null ? "" : toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null ? "" : toAccount;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? "" : createTime;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.fromAccount);
        dest.writeString(this.fromNickname);
        dest.writeString(this.fromHead);
        dest.writeString(this.toAccount);
        dest.writeString(this.toNickname);
        dest.writeString(this.toHead);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeInt(this.faceTimeType);
        dest.writeInt(this.msgType);
        dest.writeInt(this.chatType);
        dest.writeString(this.event);
        dest.writeString(this.sdp);
        dest.writeInt(this.sdpMLineIndex);
        dest.writeString(this.sdpMid);
    }

    public MessageBodyBean() {
    }

    protected MessageBodyBean(Parcel in) {
        this.id = in.readLong();
        this.fromAccount = in.readString();
        this.fromNickname = in.readString();
        this.fromHead = in.readString();
        this.toAccount = in.readString();
        this.toNickname = in.readString();
        this.toHead = in.readString();
        this.content = in.readString();
        this.createTime = in.readString();
        this.faceTimeType = in.readInt();
        this.msgType = in.readInt();
        this.chatType = in.readInt();
        this.event = in.readString();
        this.sdp = in.readString();
        this.sdpMLineIndex = in.readInt();
        this.sdpMid = in.readString();
    }

    public static final Parcelable.Creator<MessageBodyBean> CREATOR = new Parcelable.Creator<MessageBodyBean>() {
        @Override
        public MessageBodyBean createFromParcel(Parcel source) {
            return new MessageBodyBean(source);
        }

        @Override
        public MessageBodyBean[] newArray(int size) {
            return new MessageBodyBean[size];
        }
    };

    @Override
    public String toString() {
        return "MessageBodyBean{" +
                "id=" + id +
                ", fromAccount='" + fromAccount + '\'' +
                ", fromNickname='" + fromNickname + '\'' +
                ", fromHead='" + fromHead + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", toNickname='" + toNickname + '\'' +
                ", toHead='" + toHead + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", faceTimeType=" + faceTimeType +
                ", msgType=" + msgType +
                ", chatType=" + chatType +
                ", event='" + event + '\'' +
                ", sdp='" + sdp + '\'' +
                ", sdpMLineIndex=" + sdpMLineIndex +
                ", sdpMid='" + sdpMid + '\'' +
                '}';
    }
}

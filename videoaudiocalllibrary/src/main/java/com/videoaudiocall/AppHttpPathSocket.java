package com.videoaudiocall;

public class AppHttpPathSocket {
    /**
     * base
     */
    public static final String BASE = "https://wx.juntaikeji.com:19156/lanshanUAV/u/app";
//    public static final String BASE = "http://192.168.124.119:8080/lanshanUAV/u/app";

    public static final String CHAT_VIDEO_URL = "turn:stun.juntaikeji.com:19603";


    /**
     * 发送消息
     */
    public static final String SEND_MSG = BASE + "/msg/sendMassage";

    /**
     * 发起视频通话
     */
    public static final String REQUEST_VIDEO_CALL = BASE + "/msg/sendVideoCall";

    /**
     * 接受视频通话
     */
    public static final String ACCESS_VIDEO_CALL = BASE + "/msg/acceptCall";

    /**
     * 结束视频通话
     */
    public static final String REJECT_VIDEO_CALL = BASE + "/msg/refuseCall";
}

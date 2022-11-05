package com.videoaudiocall.net;

public class AppHttpPathSocket {
    /**
     * base
     */
    public static final String BASE = "https://www.juntaikeji.com:21280";
    public static final String BASE_SOCKET = "wss://www.juntaikeji.com:21280/appSocket/";
//    public static final String BASE = "https://www.juntaikeji.com:21282";
//    public static final String BASE_SOCKET = "wss://www.juntaikeji.com:21282/appSocket/";

    public static final String CHAT_VIDEO_URL = "turn:stun.juntaikeji.com:19603";


    /**
     * 音视频链接建立的消息
     */
    public static final String SEND_VIDEO_MSG = BASE + "/msg/sendMsg";
    /**
     * 拨打 挂断电话
     */
    public static final String SEND_MSG_OPERATE = BASE + "/msg/sendMassage";

    /**
     * 发起视频通话
     */
    public static final String REQUEST_VIDEO_CALL = BASE + "REQUEST_VIDEO_CALL";

    /**
     * 接受视频通话
     */
    public static final String ACCESS_VIDEO_CALL = BASE + "ACCESS_VIDEO_CALL";

    /**
     * 结束视频通话
     */
    public static final String REJECT_VIDEO_CALL = BASE + "REJECT_VIDEO_CALL";
}

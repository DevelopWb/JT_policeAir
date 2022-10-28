package com.videoaudiocall.videocall;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;


import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.basecomponent.utils.RxScheduler;
import com.videoaudiocall.net.AppHttpPathSocket;
import com.videoaudiocall.net.AppNetModuleSocket;
import com.videoaudiocall.OperateMsgUtil;
import com.videoaudiocall.bean.MessageBodyBean;
import com.videoaudiocall.net.BaseSocketObserver;
import com.videoaudiocall.net.BaseSocketResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.DataChannel;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.Logging;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.MediaStreamTrack;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;
import org.webrtc.VideoDecoderFactory;
import org.webrtc.VideoEncoderFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述   接收音视频通话的服务
 * @date 2022/10/21 10:17
 */

public class ReceiveVideoCallService extends Service {
    protected final String TAG = this.getClass().getSimpleName();
    private PeerConnectionFactory mPeerConnectionFactory;
    //OpenGL ES
    private EglBase mRootEglBase;
    public static final String AUDIO_TRACK_ID = "2";//"ARDAMSa0";
    public final static String EVENT_CAMERA_CANDIDATE = "candidate";
    /**
     * 主叫挂断通话
     */
    public final static String EVENT_CAMERA_FINISH_SENDER = "sender_finish";

    public final static String EVENT_CAMERA_OFFER = "offer";
    public final static String EVENT_CAMERA_ANSWER = "answer";

    private AudioTrack mAudioTrack;
    private MessageBodyBean mMessageBodyBean;
    /**
     * 音频通话
     */
    private int callType = 5;


    public final static String EVENT_CAMERA_ACCESS = "access";
    private PeerConnection mPeerConnection;
    private AudioManager audioManager;


    public ReceiveVideoCallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventManager.getEventBus().isRegistered(this)) {
            EventManager.getEventBus().register(this);//注册
        }
        initTrack();
// TODO: 2022/10/21 响铃三声之后 自动接听
        mMessageBodyBean = intent.getParcelableExtra(BaseActivity.BASE_PARCELABLE);
//        isCallOn = true;
        /**
         * 第三步 被叫  接听  发送EVENT_CAMERA_ACCESS
         */
        mMessageBodyBean = OperateMsgUtil.getPrivateMsg(callType, mMessageBodyBean.getFromAccount(), mMessageBodyBean.getFromNickname(), mMessageBodyBean.getFromHead(), "");
        mMessageBodyBean.setFaceTimeType(1);
        mMessageBodyBean.setEvent(EVENT_CAMERA_ACCESS);
        accessVideoCall(OperateMsgUtil.getMsgBuilder(mMessageBodyBean).build(), AppHttpPathSocket.ACCESS_VIDEO_CALL);
        if (mPeerConnection == null) {
            mPeerConnection = createPeerConnection();
        }

        return super.onStartCommand(intent, flags, startId);


    }

    /**
     * 语音通话相关配置
     */
    private void initAudioConfig() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                    AudioManager.FX_KEY_CLICK);
            audioManager.setSpeakerphoneOn(true);
        }
    }

    private void initTrack() {
        mRootEglBase = EglBase.create();

        //创建 factory， pc是从factory里获得的
        mPeerConnectionFactory = createPeerConnectionFactory(mRootEglBase, getApplicationContext());
        // NOTE: this _must_ happen while PeerConnectionFactory is alive!
        Logging.enableLogToDebugOutput(Logging.Severity.LS_VERBOSE);

        MediaConstraints audioConstraints = new MediaConstraints();
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googHighpassFilter", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));
        AudioSource audioSource = mPeerConnectionFactory.createAudioSource(audioConstraints);
        mAudioTrack = mPeerConnectionFactory.createAudioTrack(AUDIO_TRACK_ID, audioSource);
        mAudioTrack.setEnabled(true);
    }

    public PeerConnection createPeerConnection() {
        Log.i(TAG, "Create PeerConnection ...");

        LinkedList<PeerConnection.IceServer> iceServers = new LinkedList<PeerConnection.IceServer>();

        PeerConnection.IceServer ice_server =
                PeerConnection.IceServer.builder(AppHttpPathSocket.CHAT_VIDEO_URL)
                        .setPassword("jtkj2020")
                        .setUsername("juntai")
                        .createIceServer();

        iceServers.add(ice_server);

        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(iceServers);
        // TCP candidates are only useful when connecting to a server that supports
        // ICE-TCP.
        rtcConfig.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.DISABLED;
        //rtcConfig.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
        //rtcConfig.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
        rtcConfig.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
        // Use ECDSA encryption.
        //rtcConfig.keyType = PeerConnection.KeyType.ECDSA;
        // Enable DTLS for normal calls and disable for loopback calls.
        rtcConfig.enableDtlsSrtp = true;
        //rtcConfig.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN;
        PeerConnection connection =
                mPeerConnectionFactory.createPeerConnection(rtcConfig,
                        mPeerConnectionObserver);
        if (connection == null) {
            Log.e(TAG, "Failed to createPeerConnection !");
            return null;
        }

        List<String> mediaStreamLabels = Collections.singletonList("ARDAMS");
//        if (4 == callType) {
//            connection.addTrack(mLocalVideoTrack, mediaStreamLabels);
//        }
        connection.addTrack(mAudioTrack, mediaStreamLabels);

        return connection;
    }


    private PeerConnection.Observer mPeerConnectionObserver = new PeerConnection.Observer() {


        @Override
        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            Log.i(TAG, "onSignalingChange: " + signalingState);
        }

        @Override
        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            Log.i(TAG, "onIceConnectionChange: " + iceConnectionState);
        }

        @Override
        public void onIceConnectionReceivingChange(boolean b) {
            Log.i(TAG, "onIceConnectionChange: " + b);
        }

        @Override
        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            Log.i(TAG, "onIceGatheringChange: " + iceGatheringState);
        }

        @Override
        public void onIceCandidate(IceCandidate iceCandidate) {
            Log.i(TAG, "onIceCandidate: " + iceCandidate);
            /**
             * 当两边通了之后  主叫和被叫都会触发这个回调
             */
            mMessageBodyBean.setEvent(EVENT_CAMERA_CANDIDATE);
            mMessageBodyBean.setSdpMLineIndex(iceCandidate.sdpMLineIndex);
            mMessageBodyBean.setSdpMid(iceCandidate.sdpMid);
            mMessageBodyBean.setSdp(iceCandidate.sdp);
            mMessageBodyBean.setFaceTimeType(2);
            mMessageBodyBean.setContent("空值");
            sendPrivateMessage(OperateMsgUtil.getMsgBuilder(mMessageBodyBean).build(), AppHttpPathSocket.SEND_MSG);
//            pause();
        }

        @Override
        public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
            for (int i = 0; i < iceCandidates.length; i++) {
                Log.i(TAG, "onIceCandidatesRemoved: " + iceCandidates[i]);
            }
            mPeerConnection.removeIceCandidates(iceCandidates);
        }

        @Override
        public void onAddStream(MediaStream mediaStream) {
            Log.i(TAG, "onAddStream: " + mediaStream.videoTracks.size());
        }

        @Override
        public void onRemoveStream(MediaStream mediaStream) {
            Log.i(TAG, "onRemoveStream");
        }

        @Override
        public void onDataChannel(DataChannel dataChannel) {
            Log.i(TAG, "onDataChannel");
        }

        @Override
        public void onRenegotiationNeeded() {
            Log.i(TAG, "onRenegotiationNeeded");
        }

        @Override
        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
            MediaStreamTrack track = rtpReceiver.track();
//            if (track instanceof VideoTrack) {
//                Log.i(TAG, "onAddVideoTrack");
//                remoteVideoTrack = (VideoTrack) track;
//                remoteVideoTrack.setEnabled(true);
//                remoteVideoTrack.addSink(mSmallSurfaceView);
//            }
        }
    };




        /*====================================================    event   ==============================================================*/

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(EventBusObject eventBusObject) {
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.VIDEO_CALL:
                MessageBodyBean messageBody = (MessageBodyBean) eventBusObject.getEventObj();
                if (messageBody != null) {
                    switch (messageBody.getMsgType()) {
                        //视频通话
                        case 4:
                            //音频通话
                        case 5:
                            String eventMsg = messageBody.getEvent();
                            if (!TextUtils.isEmpty(eventMsg)) {
                                switch (eventMsg) {
//                            case EVENT_CAMERA_ACCESS:
//                                //发送端逻辑
//                                /**
//                                 * 主叫 画面
//                                 * 第四步 对方同意接听音视频画面
//                                 */
//                                callOnSuccess();
//                                // 这时候链路通了  发起通话
//                                /**
//                                 * 第五步 主叫申请call
//                                 */
//                                doStartCall();
//                                break;
                                    case EVENT_CAMERA_FINISH_SENDER:
                                        //主动挂断  接收端的逻辑
//                                if (isCallOn) {
//                                    //已经接通了 这时候挂断
//                                    messageBody.setDuration(getTextViewValue(mDurationTv));
//                                } else {
//                                    messageBody.setDuration(null);
//                                }
                                        messageBody.setFaceTimeType(4);
//                                //关闭并记录到本地
//                                finishActivity(messageBody);
                                        break;
//                            case EVENT_CAMERA_FINISH_RECEIVER:
//                                //结束
//                                //对方不同意接听  发送端的逻辑
//                                //已经接通了 这时候挂断
//                                if (isCallOn) {
//                                    //已经接通了 这时候挂断
//                                    mSenderMessageBodyBean.setDuration(getTextViewValue(mDurationTv));
//                                } else {
//                                    mSenderMessageBodyBean.setDuration(null);
//
//                                }
//                                mSenderMessageBodyBean.setFaceTimeType(4);
//                                finishActivity(mSenderMessageBodyBean);
//                                break;
                                    case EVENT_CAMERA_OFFER:
                                        /**
                                         * 接收端逻辑
                                         * 第六步 被叫  收到offer
                                         */
                                        if (mPeerConnection == null) {
                                            mPeerConnection = createPeerConnection();
                                        }
                                        mPeerConnection.setRemoteDescription(
                                                new SimpleSdpObserver(),
                                                new SessionDescription(
                                                        SessionDescription.Type.OFFER,
                                                        messageBody.getSdp()));
                                        /**
                                         * 第七步 被叫  接听call
                                         */
                                        doAnswerCall();
                                        break;
//                            case EVENT_CAMERA_ANSWER:
//                                /**
//                                 * 第八步 主叫  被叫链接创建成功了
//                                 */
//                                isCallOn = true;
//                                mPeerConnection.setRemoteDescription(
//                                        new SimpleSdpObserver(),
//                                        new SessionDescription(
//                                                SessionDescription.Type.ANSWER,
//                                                messageBody.getSdp()));
//                                updateCallState(false);
//
//
//                                break;
                                    case EVENT_CAMERA_CANDIDATE:
                                        /**
                                         * 第九步
                                         */
                                        IceCandidate remoteIceCandidate =
                                                new IceCandidate(messageBody.getSdpMid(),
                                                        messageBody.getSdpMLineIndex(),
                                                        messageBody.getSdp());
                                        mPeerConnection.addIceCandidate(remoteIceCandidate);
                                        break;
                                    default:
                                        break;
                                }
                                return;
                            }

                            break;
                        default:
                            break;
                    }

                }
                break;
            default:
                break;
        }
    }


    /**
     * 被叫  接听会话
     */
    public void doAnswerCall() {
        if (mPeerConnection == null) {
            mPeerConnection = createPeerConnection();
        }
        MediaConstraints sdpMediaConstraints = new MediaConstraints();
        Log.i(TAG, "Create answer ...");
        mPeerConnection.createAnswer(new VideoRequestActivity.SimpleSdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                Log.i(TAG, "Create answer success !");
                /**
                 * 被叫接听后  发送 EVENT_CAMERA_ANSWER  将sdp发送给主叫
                 */
                mPeerConnection.setLocalDescription(new VideoRequestActivity.SimpleSdpObserver(),
                        sessionDescription);
                mMessageBodyBean.setSdp(sessionDescription.description);
                mMessageBodyBean.setEvent(EVENT_CAMERA_ANSWER);
                mMessageBodyBean.setFaceTimeType(2);
                mMessageBodyBean.setContent("空值");
                sendPrivateMessage(OperateMsgUtil.getMsgBuilder(mMessageBodyBean).build(), AppHttpPathSocket.SEND_MSG);
            }

            @Override
            public void onCreateFailure(String msg) {
                super.onCreateFailure(msg);
            }

            @Override
            public void onSetFailure(String msg) {
                super.onSetFailure(msg);
            }
        }, sdpMediaConstraints);
//        updateCallState(false);
        initAudioConfig();
    }
    public static class SimpleSdpObserver implements SdpObserver {
        @Override
        public void onCreateSuccess(SessionDescription sessionDescription) {
        }

        @Override
        public void onSetSuccess() {
        }

        @Override
        public void onCreateFailure(String msg) {
        }

        @Override
        public void onSetFailure(String msg) {

        }
    }











    /*====================================================    网络部分   ==============================================================*/
    /**
     * @param body
     * @param tag
     */
    public void accessVideoCall(RequestBody body, String tag) {
        sendPrivateMessage(body,tag);
    }


    /**
     * 发送私聊消息
     *
     * @param body
     * @param tag
     */
    public void sendPrivateMessage(RequestBody body, String tag) {
        AppNetModuleSocket.createrRetrofit()
                .sendMessage(body)
                .compose(RxScheduler.ObsIoMain(null))
                .subscribe(new BaseSocketObserver<BaseSocketResult>(null) {
                    @Override
                    public void onSuccess(BaseSocketResult o) {
                        Log.i(TAG, "sendPrivateMessage:success ");

                    }

                    @Override
                    public void onError(String msg) {
                        Log.i(TAG, "accessVideoCall:err ");

                    }
                });
    }




    /*====================================================    videocall   ==============================================================*/

    public PeerConnectionFactory createPeerConnectionFactory(EglBase mRootEglBase, Context context) {
        final VideoEncoderFactory encoderFactory;
        final VideoDecoderFactory decoderFactory;
//使用外置麦克风
        encoderFactory = new DefaultVideoEncoderFactory(
                mRootEglBase.getEglBaseContext(),
                false /* enableIntelVp8Encoder */,
                true);
        decoderFactory = new DefaultVideoDecoderFactory(mRootEglBase.getEglBaseContext());

        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .createInitializationOptions());

        PeerConnectionFactory.Builder builder = PeerConnectionFactory.builder()
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory);
        builder.setOptions(null);

        return builder.createPeerConnectionFactory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventManager.getEventBus().unregister(this);//注册
        if (audioManager != null) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
        }
        mRootEglBase.releaseSurface();
        mRootEglBase.release();
        if (mPeerConnection != null) {
            mPeerConnection.close();
            mPeerConnection = null;
        }
        PeerConnectionFactory.stopInternalTracingCapture();
        PeerConnectionFactory.shutdownInternalTracer();
        if (mPeerConnectionFactory != null) {
            mPeerConnectionFactory.dispose();
            mPeerConnectionFactory = null;
        }
    }
}

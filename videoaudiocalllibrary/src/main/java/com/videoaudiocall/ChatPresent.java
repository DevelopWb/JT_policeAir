package com.videoaudiocall;

import android.content.Context;
import android.util.Log;


import com.juntai.wisdom.basecomponent.bean.BaseMenuBean;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.RxScheduler;
import com.videoaudiocall.net.AppNetModuleSocket;
import com.videoaudiocall.net.BaseSocketObserver;
import com.videoaudiocall.net.BaseSocketResult;

import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.Logging;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoDecoderFactory;
import org.webrtc.VideoEncoderFactory;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-11-07 14:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-11-07 14:27
 */
public class ChatPresent extends BasePresenter<IModel, IView> {



    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * @param body
     * @param tag
     */
    public void requestVideoCall(RequestBody body, String tag) {
        sendVideoMessageOperate(body,tag);

    }

    /**
     * @param body
     * @param tag
     */
    public void accessVideoCall(RequestBody body, String tag) {
        sendVideoMessage(body,tag);

    }

    /**
     * @param body
     * @param tag
     */
    public void rejectVideoCall(RequestBody body, String tag) {
        sendVideoMessageOperate(body,tag);
    }



    /**
     * 发送私聊消息
     *
     * @param body
     * @param tag
     */
    public void sendVideoMessage(RequestBody body, String tag) {
        AppNetModuleSocket.createrRetrofit()
                .sendVideoMessage(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseSocketObserver<BaseSocketResult>(null) {
                    @Override
                    public void onSuccess(BaseSocketResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
    /**
     * 发送私聊消息
     *
     * @param body
     * @param tag
     */
    public void sendVideoMessageOperate(RequestBody body, String tag) {
        AppNetModuleSocket.createrRetrofit()
                .sendVideoMessageOperate(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseSocketObserver<BaseSocketResult>(null) {
                    @Override
                    public void onSuccess(BaseSocketResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
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

    /*
     * Read more about Camera2 here
     * https://developer.android.com/reference/android/hardware/camera2/package-summary.html
     **/
    public VideoCapturer createVideoCapturer(Context context, boolean isFrontCamera) {
        if (Camera2Enumerator.isSupported(context)) {
            return createCameraCapturer(new Camera2Enumerator(context), isFrontCamera);
        } else {
            return createCameraCapturer(new Camera1Enumerator(true), isFrontCamera);
        }
    }

    private VideoCapturer createCameraCapturer(CameraEnumerator enumerator, boolean isFrontCamera) {
        final String[] deviceNames = enumerator.getDeviceNames();

        // First, try to find front facing camera
        Log.d(TAG, "Looking for front facing cameras.");
        if (isFrontCamera) {
            for (String deviceName : deviceNames) {
                if (enumerator.isFrontFacing(deviceName)) {
                    Logging.d(TAG, "Creating front facing camera capturer.");
                    VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                    if (videoCapturer != null) {
                        return videoCapturer;
                    }
                }
            }
        } else {
            // Front facing camera not found, try something else
            Log.d(TAG, "Looking for other cameras.");
            for (String deviceName : deviceNames) {
                if (!enumerator.isFrontFacing(deviceName)) {
                    Logging.d(TAG, "Creating other camera capturer.");
                    VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                    if (videoCapturer != null) {
                        return videoCapturer;
                    }
                }
            }
        }
        return null;
    }



    /**
     * 转发方式
     * @return
     */
    public List<BaseMenuBean>  getForwardTypes() {
        List<BaseMenuBean> calls = new ArrayList<>();
        calls.add(new BaseMenuBean("逐条转发"));
        calls.add(new BaseMenuBean("合并转发"));
        return calls;
    }

}

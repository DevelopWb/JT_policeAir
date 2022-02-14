//package cn.rongcloud.rtc.initRoom;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.juntai.wisdom.basecomponent.base.BaseActivity;
//import com.juntai.wisdom.basecomponent.utils.ToastUtils;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.rongcloud.rtc.CallActivity;
//import cn.rongcloud.rtc.CenterManager;
//import cn.rongcloud.rtc.LoadDialog;
//import cn.rongcloud.rtc.R;
//import cn.rongcloud.rtc.RTCErrorCode;
//import cn.rongcloud.rtc.RongRTCConfig;
//import cn.rongcloud.rtc.RongRTCEngine;
//import cn.rongcloud.rtc.SettingActivity;
//import cn.rongcloud.rtc.callback.JoinRoomUICallBack;
//import cn.rongcloud.rtc.callback.RongRTCResultUICallBack;
//import cn.rongcloud.rtc.core.rongRTC.DevicesUtils;
//import cn.rongcloud.rtc.device.privatecloud.ServerUtils;
//import cn.rongcloud.rtc.device.utils.Consts;
//import cn.rongcloud.rtc.entity.KickEvent;
//import cn.rongcloud.rtc.message.RoomInfoMessage;
//import cn.rongcloud.rtc.room.RongRTCRoom;
//import cn.rongcloud.rtc.room.RongRTCRoomConfig;
//import cn.rongcloud.rtc.stream.local.RongRTCCapture;
//import cn.rongcloud.rtc.util.PromptDialog;
//import cn.rongcloud.rtc.util.SessionManager;
//import cn.rongcloud.rtc.util.Utils;
//import cn.rongcloud.rtc.utils.FinLog;
//import io.rong.imlib.RongIMClient;
//
//import static android.media.MediaRecorder.AudioSource.VOICE_COMMUNICATION;
//import static cn.rongcloud.rtc.SettingActivity.IS_AUTO_TEST;
//import static cn.rongcloud.rtc.SettingActivity.IS_WATER;
//import static cn.rongcloud.rtc.util.UserUtils.OBSERVER_MUST;
//import static cn.rongcloud.rtc.util.UserUtils.VIDEOMUTE_MUST;
//
//public class JoinRoomActivity extends BaseActivity implements View.OnClickListener{
//    private static final int CHECK_BUTTON_DELATY = 1100;
//    private static final int REQUEST_CODE_SELECT_COUNTRY = 1200;
//    private static final int REQUEST_CODE_VERIFY = 1300;
//    private static final int STATE_IDLE = 0;    //未初始化
//    private static final int STATE_INIT = 1;    //已初始化
//    private static final int STATE_JOINING = 2; //加入中
//    private static final int STATE_JOINED = 3;  //已加入
//    private static final int STATE_FAILED = -1; //加入失败
//    private static final String TAG = "MainPageActivity";
//    private static final int CONNECTION_REQUEST = 1;
//    private static final long KICK_SILENT_PERIOD = 5 * 60 * 1000;
//    private EditText roomEditText, edit_UserName;
//    private TextView connectButton;
//
//    //进入房间时是否关闭摄像头
//    private static boolean isVideoMute = false;
//    //当前房间大于30人时，只能以观察者身份加入房间，不能发布音视频流，app层产品逻辑
//    private static boolean isObserver = false;
//    //当前房间大于9人时，只能发布音频流，不能发布视频流，app层产品逻辑
//    private boolean canOnlyPublishAudio = false;
//    private int mStatus = STATE_IDLE;
//
//    private String roomId;
//    private String username;
//    private RongRTCConfig.Builder configBuilder;
//    List<String> unGrantedPermissions;
//    private static final String[] MANDATORY_PERMISSIONS = {
//            "android.permission.MODIFY_AUDIO_SETTINGS",
//            "android.permission.RECORD_AUDIO",
//            "android.permission.INTERNET",
//            "android.permission.CAMERA",
//            "android.permission.READ_PHONE_STATE",
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            "android.permission.BLUETOOTH_ADMIN",
//            "android.permission.BLUETOOTH"
//    };
//    private boolean mIsLive = false;
//
//    @Override
//    public int getLayoutView() {
//        return R.layout.activity_join_room;
//    }
//
//    @Override
//    public void initView() {
//        setTitleName("进入调解室");
//        roomId = getIntent().getStringExtra("roomId");
//        username = getIntent().getStringExtra("userName");
//        checkPermissions();
//        initViews();
//    }
//
//    @Override
//    public void initData() {}
//
//
//    private void initViews() {
//        roomEditText = (EditText) findViewById(R.id.room_inputnumber);
//        if (!TextUtils.isEmpty(roomId)) {
//            roomEditText.setText(roomId);
//        }
//        edit_UserName = (EditText) findViewById(R.id.tv_user_name);
//        if (!TextUtils.isEmpty(username)){
//            edit_UserName.setText(username);
//        }
//        connectButton = (TextView) findViewById(R.id.connect_button);
//        connectButton.setText("进入调解室");
//        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(roomId)) {
//            connectButton.setBackgroundResource(R.drawable.shape_corner_button_blue_invalid);
//            connectButton.setClickable(false);
//        } else {
//            connectButton.setClickable(true);
//            connectButton.setBackgroundResource(R.drawable.shape_corner_button_blue);
//        }
//        connectButton.setOnClickListener(this);
//
//        roomEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
//                    connectButton.setBackgroundResource(R.drawable.shape_corner_button_blue);
//                    connectButton.setClickable(true);
//                } else {
////                    SessionManager.getInstance().remove(UserUtils.ROOMID_KEY);
//                    connectButton.setBackgroundResource(R.drawable.shape_corner_button_blue_invalid);
//                    connectButton.setClickable(false);
//                }
//
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.connect_button){
//            roomId = roomEditText.getText().toString().trim();
//            username = edit_UserName.getText().toString().trim();
//
//            if (TextUtils.isEmpty(roomId)){
//                ToastUtils.warning(mContext,"请输入房间ID");
//                return;
//            }
//            if (TextUtils.isEmpty(username)){
//                ToastUtils.warning(mContext,"请输入姓名");
//                return;
//            }
//
//            if (RongIMClient.getInstance().getCurrentConnectionStatus() != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED){
//                ToastUtils.warning(mContext,"通讯未连接");
//                return;
//            }
////            SessionManager.getInstance().put(UserUtils.ROOMID_KEY, roomEditText.getText().toString().trim());
////            SessionManager.getInstance().put(UserUtils.USERNAME_KEY, edit_UserName.getText().toString().trim());
//            connectToRoom();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateConfiguration();
//
//        String mediaServerUrl = SessionManager.getInstance().getString("MediaUrl");
//        //设置media server地址，内部自动化测试使用，开发者一般不需要关心
//        if (!TextUtils.isEmpty(mediaServerUrl) && !ServerUtils.usePrivateCloud()) {
//            RongRTCEngine.getInstance().setMediaServerUrl(mediaServerUrl);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//    }
//
//    private void updateConfiguration() {
//
//        if (!SessionManager.getInstance().getBoolean(getResources().getString(R.string.key_use_av_setting), false)){
//            return;     // 没有进入av setting 界面，不使用本地配置。
//        }
//
//        configBuilder = new RongRTCConfig.Builder();
//        boolean isEncoderHardMode = SessionManager.getInstance().getBoolean(Consts.SP_ENCODER_TYPE_KEY, true);
//        String hardEncoderName = SessionManager.getInstance().getString(Consts.SP_ENCODER_NAME_KEY);
//        int encoderColorValue = SessionManager.getInstance().getInt(Consts.SP_ENCODER_COLOR_FORMAT_VAL_KEY, 0);
//        boolean encoderLevel_key = SessionManager.getInstance().getBoolean(Consts.SP_ENCODER_LEVEL_KEY, true);
//        configBuilder.enableHardWareEncode(isEncoderHardMode)
//                .setHardWareEncodeColor(encoderColorValue)
//                .enableHardWareEncodeHighProfile(encoderLevel_key);
//        DevicesUtils.setEnCodeColor(encoderColorValue);
//        DevicesUtils.setHighProfile(encoderLevel_key);
//
//        String encoder_bit_rate_mode_vbr = Utils.getContext().getResources().getString(R.string.encoder_bit_rate_mode_vbr);
//        String encoder_bit_rate_mode_cq = Utils.getContext().getResources().getString(R.string.encoder_bit_rate_mode_cq);
//        String encoder_bit_rate_mode_cbr = Utils.getContext().getResources().getString(R.string.encoder_bit_rate_mode_cbr);
//
//        String encoderBitRateMode = SessionManager.getInstance().getString(Consts.SP_ENCODER_BIT_RATE_MODE, getResources().getString(R.string.def_encoder_bitrate_mode));
//        if (TextUtils.equals(encoderBitRateMode, encoder_bit_rate_mode_vbr)) {
//            configBuilder.setHardWareEncodeBitrateMode(RongRTCConfig.VideoBitrateMode.VBR);
//            DevicesUtils.setEnCodeBitRateMode(RongRTCConfig.VideoBitrateMode.VBR);
//        } else if (TextUtils.equals(encoderBitRateMode, encoder_bit_rate_mode_cbr)) {
//            configBuilder.setHardWareEncodeBitrateMode(RongRTCConfig.VideoBitrateMode.CBR);
//            DevicesUtils.setEnCodeBitRateMode(RongRTCConfig.VideoBitrateMode.CBR);
//        } else if (TextUtils.equals(encoderBitRateMode, encoder_bit_rate_mode_cq)) {
//            configBuilder.setHardWareEncodeBitrateMode(RongRTCConfig.VideoBitrateMode.CQ);
//            DevicesUtils.setEnCodeBitRateMode(RongRTCConfig.VideoBitrateMode.CQ);
//        }
//
//
//        boolean isDecoderHardMode = SessionManager.getInstance().getBoolean(Consts.SP_DECODER_TYPE_KEY, true);
//        String hardDecoderName = SessionManager.getInstance().getString(Consts.SP_DECODER_NAME_KEY);
//        int decoderColorSpace = SessionManager.getInstance().getInt(Consts.SP_DECODER_COLOR_FORMAT_VAL_KEY, 0);
//        configBuilder.enableHardWareDecode(isDecoderHardMode)
//                .setHardWareDecodeColor(decoderColorSpace);
//        DevicesUtils.setDeCodeColor(decoderColorSpace);
//
//        // audio capture config
//        int audioSource = SessionManager.getInstance().getInt(Consts.SP_AUDIO_SOURCE, VOICE_COMMUNICATION);
//        int audioSampleRate = SessionManager.getInstance().getInt(Consts.SP_AUDIO_SAMPLE_RATE, 48000);
//        boolean audioSampleStereo = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_CHANNEL_STEREO_ENABLE, false);
//        int audioBitRate = SessionManager.getInstance().getInt(Consts.SP_AUDIO_TRANSPORT_BIT_RATE, 30);
//        configBuilder.setAudioSource(audioSource)
//                .setAudioSampleRate(audioSampleRate)
//                .enableStereo(audioSampleStereo)
//                .setAudioBitrate(audioBitRate);
//
//        // audio agc config
//        boolean audioAgcEnable = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_AGC_CONTROL_ENABLE, true);
//        boolean audioAgcLimiter = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_AGC_LIMITER_ENABLE, true);
//        int audioAgcTargetDbov = SessionManager.getInstance().getInt(Consts.SP_AUDIO_AGC_TARGET_DBOV, -3);
//        int audioAgcCompression = SessionManager.getInstance().getInt(Consts.SP_AUDIO_AGC_COMPRESSION, 9);
//        boolean audioPreAmplifierEnable = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_PRE_AMPLIFIER_ENABLE, true);
//        float audioPreAmplifierLevel = SessionManager.getInstance().getFloat(Consts.SP_AUDIO_PRE_AMPLIFIER_LEVEL, 1.0f);
//        configBuilder.enableAGCControl(audioAgcEnable)
//                .enableAGCLimiter(audioAgcLimiter)
//                .setAGCTargetdbov(audioAgcTargetDbov)
//                .setAGCCompression(audioAgcCompression)
//                .enablePreAmplifier(audioPreAmplifierEnable)
//                .setPreAmplifierLevel(audioPreAmplifierLevel);
//
//        // audio noise suppression
//        int audioNoiseSuppressionMode = SessionManager.getInstance().getInt(Consts.SP_AUDIO_NOISE_SUPPRESSION_MODE, getResources().getInteger(R.integer.def_ns_model));
//        int audioNoiseSuppressionLevel = SessionManager.getInstance().getInt(Consts.SP_AUDIO_NOISE_SUPPRESSION_LEVEL, getResources().getInteger(R.integer.def_ns_level));
//        boolean audioNoiseHighPassFilter = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_NOISE_HIGH_PASS_FILTER, true);
//        configBuilder.setNoiseSuppression(RongRTCConfig.NSMode.parseValue(audioNoiseSuppressionMode))
//                .setNoiseSuppressionLevel(RongRTCConfig.NSLevel.parseValue(audioNoiseSuppressionLevel))
//                .enableHighPassFilter(audioNoiseHighPassFilter);
//
//        // audio echo cancel
//        int audioEchoCancelMode = SessionManager.getInstance().getInt(Consts.SP_AUDIO_ECHO_CANCEL_MODE, 0);
//        boolean audioEchoCancelFilterEnable = SessionManager.getInstance().getBoolean(Consts.SP_AUDIO_ECHO_CANCEL_FILTER_ENABLE, getResources().getBoolean(R.bool.def_echo_filter_enable));
//        configBuilder.setEchoCancel(RongRTCConfig.AECMode.parseValue(audioEchoCancelMode))
//                .enableEchoFilter(audioEchoCancelFilterEnable);
//
//        //Set max and min bitrate
//        String minBitRate = SessionManager.getInstance().getString(SettingActivity.BIT_RATE_MIN, getResources().getString(R.string.def_min_bitrate));
//        if (!TextUtils.isEmpty(minBitRate) && minBitRate.length() > 4) {
//            int bitRateIntvalue = Integer.valueOf(minBitRate.substring(0, minBitRate.length() - 4));
//            FinLog.v(TAG, "BIT_RATE_MIN=" + bitRateIntvalue);
//            configBuilder.setMinRate(bitRateIntvalue);
//        }
//        String maxBitRate = SessionManager.getInstance().getString(SettingActivity.BIT_RATE_MAX, getResources().getString(R.string.def_max_bitrate));
//        if (!TextUtils.isEmpty(maxBitRate) && maxBitRate.length() > 4) {
//            int bitRateIntvalue = Integer.valueOf(maxBitRate.substring(0, maxBitRate.length() - 4));
//            FinLog.v(TAG, "BIT_RATE_MAX=" + bitRateIntvalue);
//            configBuilder.setMaxRate(bitRateIntvalue);
//        }
//        //set resolution
//        String resolution = SessionManager.getInstance().getString(SettingActivity.RESOLUTION);
//        String fps = SessionManager.getInstance().getString(SettingActivity.FPS);
//        RongRTCConfig.RongRTCVideoProfile videoProfile = selectiveResolution(resolution, fps);
//        configBuilder.setVideoProfile(videoProfile);
//        String codec = SessionManager.getInstance().getString(SettingActivity.CODECS);
//        if (!TextUtils.isEmpty(codec)) {
//            if ("VP8".equals(codec)) {
//                configBuilder.videoCodecs(RongRTCConfig.RongRTCVideoCodecs.VP8);
//            } else {
//                configBuilder.videoCodecs(RongRTCConfig.RongRTCVideoCodecs.H264);
//            }
//        }
//        boolean enableTinyStream = SessionManager.getInstance().getIsSupportTiny(SettingActivity.IS_STREAM_TINY);
//        configBuilder.enableTinyStream(enableTinyStream);
//
//        //在device设置界面设置的屏幕旋转角度 在此在设置一次，防止被覆盖
//        int cameraDisplayOrientation = SessionManager.getInstance().getInt(Consts.CAPTURE_CAMERA_DISPLAY_ORIENTATION_KEY, 0);
//        int frameOrientation = SessionManager.getInstance().getInt(Consts.CAPTURE_FRAME_ORIENTATION_KEY, -1);
//        boolean acquisitionMode = SessionManager.getInstance().getBoolean(Consts.ACQUISITION_MODE_KEY, true);
//
//        configBuilder.setCameraDisplayOrientation(cameraDisplayOrientation);
//        configBuilder.setFrameOrientation(frameOrientation);
//        configBuilder.enableVideoTexture(acquisitionMode);
//
//        boolean isAudioMode = SessionManager.getInstance().getBoolean(SettingActivity.IS_AUDIO_MUSIC, getResources().getBoolean(R.bool.def_audio_music_mode));
//        if (isAudioMode){
//            configBuilder.buildMusicMode();
//            RongRTCCapture.getInstance().changeAudioScenario(RongRTCConfig.AudioScenario.MUSIC);
//        }else {
//            RongRTCCapture.getInstance().changeAudioScenario(RongRTCConfig.AudioScenario.DEFAULT);
//        }
//
//
//        FinLog.v(TAG, "enableTinyStream: " + enableTinyStream);
//        FinLog.v(TAG, "audio option stereo: " + SessionManager.getInstance().getBoolean(SettingActivity.IS_STEREO));
//        FinLog.v(TAG, "audio option audioProcess: " + SessionManager.getInstance().getBoolean(SettingActivity.IS_AUDIO_PROCESS));
//        FinLog.v(TAG, "audio option audio bitrate: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_BITRATE));
//        FinLog.v(TAG, "audio option audio agc limiter: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_AGC_LIMITER));
//        FinLog.v(TAG, "audio option audio agc target dbov: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_AGC_TARGET_DBOV));
//        FinLog.v(TAG, "audio option audio agc compression: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_AGC_COMPRESSION));
//        FinLog.v(TAG, "audio option audio noise suppression: " + SessionManager.getInstance().getString(SettingActivity.AUDIO__NOISE_SUPPRESSION));
//        FinLog.v(TAG, "audio option audio noise suppression level: " + SessionManager.getInstance().getString(SettingActivity.AUDIO__NOISE_SUPPRESSION_LEVEL));
//        FinLog.v(TAG, "audio option audio echo cancel: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_ECHO_CANCEL));
//        FinLog.v(TAG, "audio option audio preAmplifier : " + SessionManager.getInstance().getString(SettingActivity.AUDIO_PREAMPLIFIER));
//        FinLog.v(TAG, "audio option audio preAmplifier level: " + SessionManager.getInstance().getString(SettingActivity.AUDIO_PREAMPLIFIER_LEVEL));
//
//        RongRTCCapture.getInstance().setRTCConfig(configBuilder.build());
//    }
//
//    /**
//     * 进入房间
//     */
//    private void connectToRoom() {
//        mStatus = STATE_JOINING;
//        updateConfiguration();
//        if (RongIMClient.getInstance().getCurrentConnectionStatus() == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
//            LoadDialog.show(this);
//            final String roomId = roomEditText.getText().toString();
//            RongRTCRoomConfig config = new RongRTCRoomConfig.Builder()
//                    .setRoomType(mIsLive ? RongRTCRoomConfig.RoomType.LIVE : RongRTCRoomConfig.RoomType.NORMAL)
//                    .setLiveType(isVideoMute ? RongRTCRoomConfig.LiveType.AUDIO : RongRTCRoomConfig.LiveType.AUDIO_VIDEO) //直播模式为音视频模式
//                    .build();
//            RongRTCEngine.getInstance().joinRoom(roomId,config, new JoinRoomUICallBack() {
//                @Override
//                protected void onUiSuccess(RongRTCRoom rtcRoom) {
//                    LoadDialog.dismiss(JoinRoomActivity.this);
//                    Toast.makeText(JoinRoomActivity.this, getResources().getString(R.string.join_room_success), Toast.LENGTH_SHORT).show();
//                    int userCount = rtcRoom.getRemoteUsers().size();
//                    if (userCount >= OBSERVER_MUST && !isObserver) {
//                        AlertDialog dialog = new AlertDialog.Builder(JoinRoomActivity.this)
//                                .setMessage(getResources().getString(R.string.join_room_observer_prompt))
//                                .setNegativeButton(getResources().getString(R.string.rtc_dialog_cancel), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        quitRoom(roomId);
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .setPositiveButton(getResources().getString(R.string.rtc_dialog_ok), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        canOnlyPublishAudio = false;
//                                        startCallActivity(true, true);
//                                    }
//                                })
//                                .create();
//                        dialog.setCancelable(false);
//                        dialog.show();
//                    } else if (userCount >= VIDEOMUTE_MUST && !isVideoMute && !isObserver) {
//                        AlertDialog dialog = new AlertDialog.Builder(JoinRoomActivity.this)
//                                .setMessage(getResources().getString(R.string.join_room_audio_only_prompt))
//                                .setNegativeButton(getResources().getString(R.string.rtc_dialog_cancel), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        quitRoom(roomId);
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .setPositiveButton(getResources().getString(R.string.rtc_dialog_ok), new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        canOnlyPublishAudio = true;
//                                        startCallActivity(true, false);
//
//                                    }
//                                })
//                                .create();
//                        dialog.setCancelable(false);
//                        dialog.show();
//                    } else {
//                        canOnlyPublishAudio = false;
//                        startCallActivity(isVideoMute, isObserver);
//                    }
//                }
//
//                @Override
//                protected void onUiFailed(RTCErrorCode errorCode) {
//                    mStatus = STATE_FAILED;
//                    LoadDialog.dismiss(JoinRoomActivity.this);
//                    Toast.makeText(JoinRoomActivity.this, getResources().getString(R.string.join_room_failed), Toast.LENGTH_SHORT).show();
//                }
//
//            });
//        } else {
//            mStatus = STATE_FAILED;
//            Toast.makeText(JoinRoomActivity.this, getResources().getString(R.string.im_connect_failed), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//    private void startCallActivity(boolean muteVideo, boolean observer) {
//        if (mStatus == STATE_JOINED)
//            return;
//        mStatus = STATE_JOINED;
//        Intent intent = new Intent(this, CallActivity.class);
//        //加入房间之前 置为默认状态
//        SessionManager.getInstance().put("VideoModeKey", "smooth");
//        //
//        intent.putExtra(CallActivity.EXTRA_ROOMID, roomEditText.getText().toString());
//        intent.putExtra(CallActivity.EXTRA_USER_NAME, edit_UserName.getText().toString());
//        intent.putExtra(CallActivity.EXTRA_CAMERA, muteVideo);
//        intent.putExtra(CallActivity.EXTRA_OBSERVER, observer);
//        intent.putExtra(CallActivity.EXTRA_AUTO_TEST, SessionManager.getInstance().getBoolean(IS_AUTO_TEST));
//        intent.putExtra(CallActivity.EXTRA_WATER, SessionManager.getInstance().getBoolean(IS_WATER));
//        intent.putExtra(CallActivity.EXTRA_IS_LIVE, mIsLive);
//        RongRTCRoom rongRTCRoom = CenterManager.getInstance().getRongRTCRoom();
//        int joinMode = RoomInfoMessage.JoinMode.AUDIO_VIDEO;
//        if (muteVideo) {
//            joinMode = RoomInfoMessage.JoinMode.AUDIO;
//        }
//        if (observer) {
//            joinMode = RoomInfoMessage.JoinMode.OBSERVER;
//        }
//        String userId = rongRTCRoom.getLocalUser().getUserId();
//        String userName = edit_UserName.getText().toString();
//        int remoteUserCount = 0;
//        if (rongRTCRoom.getRemoteUsers() != null) {
//            remoteUserCount = rongRTCRoom.getRemoteUsers().size();
//        }
//        intent.putExtra(CallActivity.EXTRA_IS_MASTER, remoteUserCount == 0);
//        RoomInfoMessage roomInfoMessage = new RoomInfoMessage(userId, userName, joinMode, System.currentTimeMillis(), remoteUserCount == 0);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userId", userId);
//            jsonObject.put("userName", userName);
//            jsonObject.put("joinMode", joinMode);
//            jsonObject.put("joinTime", System.currentTimeMillis());
//            jsonObject.put("master", remoteUserCount == 0 ? 1 : 0);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        rongRTCRoom.setRoomAttributeValue(jsonObject.toString(), userId, roomInfoMessage, new RongRTCResultUICallBack() {
//            @Override
//            public void onUiSuccess() {
//
//            }
//
//            @Override
//            public void onUiFailed(RTCErrorCode errorCode) {
//
//            }
//        });
//        startActivityForResult(intent, CONNECTION_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CONNECTION_REQUEST){
//            mStatus = STATE_INIT;
//        }else if (requestCode == REQUEST_CODE_VERIFY){
////            updateCountry();
//        }else if (requestCode == REQUEST_CODE_SELECT_COUNTRY){
//
//        }
//    }
//
//    private void checkPermissions() {
//        unGrantedPermissions = new ArrayList();
//        for (String permission : MANDATORY_PERMISSIONS) {
//            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                unGrantedPermissions.add(permission);
//            }
//        }
//        if (unGrantedPermissions.size() == 0) {//已经获得了所有权限，开始加入聊天室
//            initSDK();
//        } else {//部分权限未获得，重新请求获取权限
//            String[] array = new String[unGrantedPermissions.size()];
//            ActivityCompat.requestPermissions(this, unGrantedPermissions.toArray(array), 0);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        unGrantedPermissions.clear();
//        for (int i = 0; i < permissions.length; i++) {
//            if (grantResults[i] == PackageManager.PERMISSION_DENIED)
//                unGrantedPermissions.add(permissions[i]);
//        }
//        if (unGrantedPermissions.size() > 0) {
//            for (String permission : unGrantedPermissions) {
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                    Toast.makeText(this, getString(R.string.PermissionStr) + permission + getString(R.string.plsopenit), Toast.LENGTH_SHORT).show();
//                    finish();
//                } else ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
//            }
//        } else {
//            initSDK();
//        }
//
//    }
//
//    private void quitRoom(String roomId) {
//        mStatus = STATE_INIT;
//        canOnlyPublishAudio = false;
//        RongRTCEngine.getInstance().quitRoom(roomId, new RongRTCResultUICallBack() {
//
//                    @Override
//                    public void onUiSuccess() {
//
//                    }
//
//                    @Override
//                    public void onUiFailed(RTCErrorCode errorCode) {
//
//                    }
//                }
//        );
//    }
//
//    private void initSDK() {
//        if (mStatus < STATE_INIT) {
//            mStatus = STATE_INIT;
//
//            //ModuleIm_Init中已初始化过
////            /*
////             * 如果是连接到私有云需要在此配置服务器地址
////             * 如果是公有云则不需要调用此方法
////             */
////            RongIMClient.setServerInfo(ServerUtils.getNavServer(), UserUtils.FILE_SERVER);
////            RongIMClient.init(getApplication(), ServerUtils.getAppKey(), false);
////            /*
////             * 设置建立 Https 连接时，是否使用自签证书。
////             * 公有云用户无需调用此方法，私有云用户使用自签证书时调用此方法设置
////             */
////            /*configBuilder = new RongRTCConfig.Builder();
////            configBuilder.enableHttpsSelfCertificate(true);*/
//        }
//    }
//
//    /**
//     * 构造分辨率对应的BlinkVideoProfile对象
//     *
//     * @param resolutionStr
//     * @return
//     */
//    private RongRTCConfig.RongRTCVideoProfile selectiveResolution(String resolutionStr, String fpsStr) {
//        RongRTCConfig.RongRTCVideoProfile profile = null;
//        if (TextUtils.isEmpty(fpsStr)) {
//            fpsStr = "15";
//        }
//
//        if (resolutionStr == null || resolutionStr.equals("")){
//            return RongRTCConfig.RongRTCVideoProfile.RONGRTC_VIDEO_PROFILE_480P_15f_1;
//        }
//
//        String[] resolutionArray = resolutionStr.split("x");
//
//        profile = RongRTCConfig.RongRTCVideoProfile.parseVideoProfile(Integer.valueOf(resolutionArray[0]), Integer.valueOf(resolutionArray[1]), Integer.valueOf(fpsStr));
//        return profile;
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onKickEvent(KickEvent event) {
//        SessionManager.getInstance().put("KICK_TIME", System.currentTimeMillis());
//        SessionManager.getInstance().put("KICK_ROOM_ID", event.getRoomId());
//        final PromptDialog dialog = PromptDialog.newInstance(this, getString(R.string.member_operate_kicked));
//        dialog.setPromptButtonClickedListener(new PromptDialog.OnPromptButtonClickedListener() {
//            @Override
//            public void onPositiveButtonClicked() {
//
//            }
//
//            @Override
//            public void onNegativeButtonClicked() {
//
//            }
//        });
//        dialog.disableCancel();
//        dialog.setCancelable(false);
//        dialog.show();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            LoadDialog.dismiss(JoinRoomActivity.this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

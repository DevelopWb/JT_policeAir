package com.juntai.wisdom.bdmap.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.NotificationTool;
import com.juntai.wisdom.bdmap.utils.BaiDuLocationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/5/8 9:02  更新 位置上传的逻辑
 */
public class LocateAndUpload extends Service {
    private BaiDuLocationUtils baiDuLocationUtils = null;
    public static Double lat = 0.0;
    public static Double lng = 0.0;
    public static BDLocation bdLocation = null;
    static Callback callback;
    private int id;
    private String token, account, historyApiUrl;
    private OkHttpClient okHttpClient;
    private Request request;
    private Map<String, String> params;
    private RequestBody body;
    private Boolean isFirstWarning = true;
    private boolean uploadLocation = true;//一直上传
    private boolean startUploadLocation = false;//开始上传
    private BDAbstractLocationListener bdAbstractLocationListener;
    private String addr;
    private String locType;

    public class Binder extends android.os.Binder {
        public LocateAndUpload getService() {
            return LocateAndUpload.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        historyApiUrl = intent.getStringExtra("historyApiUrl");
//        params = (HashMap) intent.getSerializableExtra("params");
        okHttpClient = new OkHttpClient();
        //                if (BaseAppUtils.isApkInDebug(this)) {
        //                    uploadLocation = false;
        //                }else {
        //                    uploadLocation = true;
        //                }

        //        return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, NotificationTool.getNotification(this));
            //            stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        }
        bdAbstractLocationListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.e("EEEEEEEEEE", " = " + bdLocation.getLocType());
                //  161是网络定位的结果   61是GPS定位成功的结果 66 离线定位成功
                if (bdLocation.getLocType() != 161 && bdLocation.getLocType() != 61&& bdLocation.getLocType() != 66) {
                    return;
                }
                startUploadLocation = true;
                lat = bdLocation.getLatitude();
                lng = bdLocation.getLongitude();
                addr = bdLocation.getAddrStr();
                locType = getLocType(bdLocation);
                Log.e("bdLocation-->adcode", " = " + bdLocation.getAdCode());
                Log.e("bdLocation-->addr", " = " + addr);
                Log.e("bdLocation-->locType", " = " + locType);
                LocateAndUpload.bdLocation = bdLocation;
                if (callback != null) {
                    callback.onLocationChange(bdLocation);
                }
            }
        };

        baiDuLocationUtils = new BaiDuLocationUtils(getApplicationContext());
        baiDuLocationUtils.start(bdAbstractLocationListener);
    }

    /**
     * 获取定位类型
     *
     * @param bdLocation
     * @return
     */
    public static String getLocType(BDLocation bdLocation) {
        //  161是网络定位的结果   61是GPS定位成功的结果 66 离线定位成功
        String typeStr = "NET";
        int type = bdLocation.getLocType();
        switch (type) {
            case 61:
                typeStr = "GPS";
                break;
            case 66:
                typeStr = "OFF";
                break;
            case 161:
                typeStr = "NET";
                break;
            default:
                break;
        }
        return typeStr;
    }

    @Override
    public void onDestroy() {
        baiDuLocationUtils.stop(bdAbstractLocationListener);
        uploadLocation = false;
        super.onDestroy();
    }

    public static void setCallback(Callback callback) {
        LocateAndUpload.callback = callback;
    }

    public interface Callback {
        void onLocationChange(BDLocation bdLocation);
    }
}

package com.skl.networkmonitor;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.juntai.wisdom.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventManager;


public class NetworkLiveData  {

    private final Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static NetworkLiveData mNetworkLiveData;
    private NetworkReceiver mNetworkReceiver;
    private final IntentFilter mIntentFilter;

    private static final String TAG = "NetworkLiveData";

    private NetworkLiveData(Context context) {
        mContext = context;
        mNetworkReceiver = new NetworkReceiver();
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    public static NetworkLiveData get(Context context) {
        if (mNetworkLiveData == null) {
            mNetworkLiveData = new NetworkLiveData(context);
        }
        return mNetworkLiveData;
    }

    public void  regist(){
        mContext.registerReceiver(mNetworkReceiver, mIntentFilter);
    }

    public void  unRegist(){
        mContext.unregisterReceiver(mNetworkReceiver);

    }


    private static class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            EventManager.getEventBus().post(new EventBusObject(EventBusObject.NETWORK_STATUS,NetUtil.getNetType(context.getApplicationContext())));

        }
    }


}

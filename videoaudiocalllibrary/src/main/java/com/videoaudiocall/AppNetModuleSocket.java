package com.videoaudiocall;


import com.juntai.wisdom.basecomponent.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModuleSocket {
    static AppServerSocket appServer ;
    public static AppServerSocket createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(AppServerSocket.class);
        }
        return appServer;
    }
}

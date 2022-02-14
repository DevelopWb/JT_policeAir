package com.juntai.wisdom.dgjxb;


import com.juntai.wisdom.basecomponent.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModule {
    static com.juntai.wisdom.dgjxb.AppServer appServer ;
    public static com.juntai.wisdom.dgjxb.AppServer createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(com.juntai.wisdom.dgjxb.AppServer.class);
        }
        return appServer;
    }
}

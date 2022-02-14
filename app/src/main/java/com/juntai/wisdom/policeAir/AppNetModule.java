package com.juntai.wisdom.policeAir;


import com.juntai.wisdom.basecomponent.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModule {
    static com.juntai.wisdom.policeAir.AppServer appServer ;
    public static com.juntai.wisdom.policeAir.AppServer createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(com.juntai.wisdom.policeAir.AppServer.class);
        }
        return appServer;
    }
}

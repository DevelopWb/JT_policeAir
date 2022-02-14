package com.danikula.videocache2;

import okhttp3.OkHttpClient;

public class OkManager {

    protected OkHttpClient client; // = createOkHttpClient();
    private OkManager(){
        client = new OkHttpClient();
    }
    public static OkManager getInstance(){
        return Holder.ok;
    }
    private  static  class Holder{
        public static OkManager ok = new OkManager();
    }
}

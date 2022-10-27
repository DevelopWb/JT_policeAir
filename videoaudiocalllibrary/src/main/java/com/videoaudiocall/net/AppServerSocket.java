package com.videoaudiocall.net;



import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServerSocket {


    @POST(AppHttpPathSocket.SEND_MSG)
    Observable<BaseSocketResult> requestVideoCall(@Body RequestBody requestBody);


    @POST(AppHttpPathSocket.SEND_MSG)
    Observable<BaseSocketResult> accessVideoCall(@Body RequestBody requestBody);

    @POST(AppHttpPathSocket.SEND_MSG)
    Observable<BaseSocketResult> rejectVideoCall(@Body RequestBody requestBody);


    /**
     * 发送消息
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPathSocket.SEND_MSG)
    Observable<BaseSocketResult> sendMessage(@Body RequestBody requestBody);
}

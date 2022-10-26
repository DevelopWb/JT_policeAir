package com.videoaudiocall;


import com.juntai.wisdom.basecomponent.base.BaseResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServerSocket {


    @POST(AppHttpPathSocket.REQUEST_VIDEO_CALL)
    Observable<BaseResult> requestVideoCall(@Body RequestBody requestBody);


    @POST(AppHttpPathSocket.ACCESS_VIDEO_CALL)
    Observable<BaseResult> accessVideoCall(@Body RequestBody requestBody);

    @POST(AppHttpPathSocket.REJECT_VIDEO_CALL)
    Observable<BaseResult> rejectVideoCall(@Body RequestBody requestBody);


    /**
     * 发送消息
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPathSocket.SEND_MSG)
    Observable<BaseResult> sendMessage(@Body RequestBody requestBody);
}

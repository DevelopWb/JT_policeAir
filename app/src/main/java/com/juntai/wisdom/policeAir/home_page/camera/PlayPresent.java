package com.juntai.wisdom.policeAir.home_page.camera;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.bean.BaseStreamBean;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.BaseDataBean;
import com.juntai.wisdom.policeAir.bean.CommentListBean;
import com.juntai.wisdom.policeAir.bean.stream.CameraOnlineBean;
import com.juntai.wisdom.policeAir.bean.stream.CaptureBean;
import com.juntai.wisdom.policeAir.bean.stream.PlayUrlBean;
import com.juntai.wisdom.policeAir.bean.stream.RecordInfoBean;
import com.juntai.wisdom.policeAir.bean.stream.StreamCameraDetailBean;
import com.juntai.wisdom.policeAir.bean.stream.VideoInfoBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

import java.text.SimpleDateFormat;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:49
 */
public class PlayPresent extends BasePresenter<IModel, PlayContract.IPlayView> implements PlayContract.IPlayPresent {
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void openStream(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .openStream(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PlayUrlBean>(null) {
                    @Override
                    public void onSuccess(PlayUrlBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.getData());
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
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount()==null?"":MyApp.getAccount())
                .addFormDataPart("token", MyApp.getUserToken()==null?"":MyApp.getUserToken())
                .addFormDataPart("userId", MyApp.getUid()==-1?"":String.valueOf(MyApp.getUid()));
    }

    @Override
    public void capturePic(String channelid, String type, String tag) {
        AppNetModule.createrRetrofit()
                .capturePic(channelid, type)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CaptureBean>(null) {
                    @Override
                    public void onSuccess(CaptureBean o) {
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

    @Override
    public void getStreamCameraDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getStreamCameraDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraDetailBean>(null) {
                    @Override
                    public void onSuccess(StreamCameraDetailBean o) {
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


    @Override
    public void uploadStreamCameraThumbPic(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .uploadStreamCameraThumbPic(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message);
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

    @Override
    public void searchVideos(String begintime, String endtime, String channelid, String tag) {
        AppNetModule.createrRetrofit()
                .searchVideos(begintime, endtime, channelid)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<VideoInfoBean>(getView()) {
                    @Override
                    public void onSuccess(VideoInfoBean o) {
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

    @Override
    public void operateYunTai(String ptztype, int ptzparam, String channelid, String tag) {
        AppNetModule.createrRetrofit()
                .operateYunTai(ptztype, ptzparam, channelid)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseStreamBean>(getView()) {
                    @Override
                    public void onSuccess(BaseStreamBean o) {
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

    @Override
    public void playHisVideo(Map<String, String> queryMap, String tag) {
        AppNetModule.createrRetrofit()
                .getVideosUrl(queryMap)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<RecordInfoBean>(getView()) {
                    @Override
                    public void onSuccess(RecordInfoBean o) {
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

    @Override
    public void operateRecordVideo(String sessionid, String vodctrltype, String vodctrlparam, String tag) {
        AppNetModule.createrRetrofit()
                .operateRecordVideo(sessionid, vodctrltype, vodctrlparam)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseStreamBean>(getView()) {
                    @Override
                    public void onSuccess(BaseStreamBean o) {
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

    @Override
    public void addPrePosition(RequestBody requestBody, String tag) {
//        AppNetModule.createrRetrofit()
//                .addPrePosition(requestBody)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<BaseResult>(getView()) {
//                    @Override
//                    public void onSuccess(BaseResult o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }
    @Override
    public void delPrePosition(RequestBody requestBody, String tag) {
//        AppNetModule.createrRetrofit()
//                .delPrePosition(requestBody)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<BaseResult>(getView()) {
//                    @Override
//                    public void onSuccess(BaseResult o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void getPrePositions(RequestBody requestBody, String tag) {
//        AppNetModule.createrRetrofit()
//                .getPrePositions(requestBody)
//                .compose(RxScheduler.ObsIoMain(getView()))
//                .subscribe(new BaseObserver<PreSetBean>(getView()) {
//                    @Override
//                    public void onSuccess(PreSetBean o) {
//                        if (getView() != null) {
//                            getView().onSuccess(tag, o);
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        if (getView() != null) {
//                            getView().onError(tag, msg);
//                        }
//                    }
//                });
    }

    @Override
    public void getOnlineAmount(String parameter, String tag) {
        AppNetModule.createrRetrofit()
                .getOnlineAmount(parameter)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CameraOnlineBean>(getView()) {
                    @Override
                    public void onSuccess(CameraOnlineBean o) {
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
    @Override
    public void stopStream(String sessionid, String tag) {
        AppNetModule.createrRetrofit()
                .stopStream(sessionid)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseStreamBean>(getView()) {
                    @Override
                    public void onSuccess(BaseStreamBean o) {
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

    @Override
    public void getCommentList(int commentedId, int pageSize, int currentPage, String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;

        AppNetModule.createrRetrofit()
                .getAllCommentCamera(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), commentedId, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(getView()) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getChildCommentCamera(MyApp.getAccount(), MyApp.getUserToken(), commentedId, unreadId, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(viewCallBack) {
                    @Override
                    public void onSuccess(CommentListBean o) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void like(int id, int userId, int isType, int typeId, int likeId, String tag) {
        IView viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        }else{
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrCancleLikeCamera(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType,typeId,likeId)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消点赞");
                        }else {
                            ToastUtils.success(MyApp.app,"点赞成功");
                        }
                        LogUtil.e("更新点赞成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消点赞失败");
                        }else {
                            ToastUtils.success(MyApp.app,"点赞失败");
                        }
                        LogUtil.e("更新点赞失败");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void addShareRecord(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .shareToWechat(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account", MyApp.getAccount()==null?"":MyApp.getAccount())
        .add("token", MyApp.getUserToken()==null?"":MyApp.getUserToken());
        return builder;
    }



    /**
     * @param oldTime
     * @return
     */
    public String formatTimeToYun(long oldTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(oldTime);
        return time.replace(" ", "T");
    }

    /**
     * 配置view的margin属性
     */
    public void setMarginOfConstraintLayout(View view, Context context, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(context, left);
        top = DisplayUtil.dp2px(context, top);
        right = DisplayUtil.dp2px(context, right);
        bottom = DisplayUtil.dp2px(context, bottom);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }
}

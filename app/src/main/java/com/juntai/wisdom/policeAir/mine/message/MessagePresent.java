package com.juntai.wisdom.policeAir.mine.message;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.message.InformDetailBean;
import com.juntai.wisdom.policeAir.bean.message.LikeMsgListBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

/**
 * Describe:我的消息
 * Create by zhangzhenlong
 * 2020-3-13
 * email:954101549@qq.com
 */
public class MessagePresent extends BasePresenter<IModel,IMessageContract.IMessageView>
        implements IMessageContract.IMessagePresent {
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getLikeMsgList(int type, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserLikeMsg(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),type,pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LikeMsgListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(LikeMsgListBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void allReadMsg(int type, int messageId, String tag) {//类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
        AppNetModule.createrRetrofit()
                .allReadMsg(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), type, messageId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void getInformMsgDetail(int id, String tag) {
        AppNetModule.createrRetrofit()
                .getSystemDetail(MyApp.getAccount(), MyApp.getUserToken(),id, MyApp.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InformDetailBean>(getView()) {
                    @Override
                    public void onSuccess(InformDetailBean o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteSystemMsg(int logId, String tag) {
        AppNetModule.createrRetrofit()
                .deleteSystemMsg(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), logId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null){
                            getView().onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (getView() != null){
                            getView().onError(tag,msg);
                        }
                    }
                });
    }

}

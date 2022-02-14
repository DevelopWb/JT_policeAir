package com.juntai.wisdom.dgjxb.entrance;

import android.annotation.SuppressLint;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.PubUtil;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.utils.RxScheduler;

import cn.sharesdk.framework.Platform;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/3/5 15:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/5 15:55
 */
public class EntrancePresent extends BasePresenter<IModel, EntranceContract.IEntranceView> implements EntranceContract.IEntrancePresent {
    private IView iView;
    public void  setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    protected IModel createModel() {
        return null;
    }


    @SuppressLint("CheckResult")
    @Override
    public void login(String account, String password, String weChatId, String qqId,String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
                viewCallBack.showLoading();
            }
        }else{
            viewCallBack = getView();
            viewCallBack.showLoading();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule
                .createrRetrofit()
                .login(account, password, weChatId, qqId, 1)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean userBean) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onSuccess(tag, userBean);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onError(tag, PubUtil.ERROR_NOTICE);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindQQOrWeChat(String account, String token,String weChatId, String weChatName, String qqId,
                               String qqName,
                               String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
                viewCallBack.showLoading();
            }
        }else{
            viewCallBack = getView();
            viewCallBack.showLoading();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .bindQQAndWeChat(account, token,1, weChatId, weChatName, qqId, qqName)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseResult>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onSuccess(tag, o);
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
    public void bindPhoneNum(RequestBody requestBody, String tag) {
        IView  viewCallBack = null;
        if (getView()==null) {
            if (iView != null) {
                viewCallBack = iView;
                viewCallBack.showLoading();
            }
        }else{
            viewCallBack = getView();
            viewCallBack.showLoading();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .bindPhoneNum(requestBody)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<BaseResult>() {
                    @Override
                    public void accept(BaseResult baseResult) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onSuccess(tag, baseResult);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.hideLoading();
                            finalViewCallBack.onError(tag, PubUtil.ERROR_NOTICE);
                        }
                    }
                });
    }


    public void regist(RequestBody body,String tag) {
        AppNetModule.createrRetrofit()
                .regist(body)
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
}

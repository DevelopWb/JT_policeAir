package com.example.appbase.base.sendcode;


import android.text.TextUtils;

import com.example.appbase.bean.UserBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.RxScheduler;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/7/14 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/7/14 10:30
 */
public class SendCodePresent extends BasePresenter<SendCodeModel, SendCodeContract.ISendCodeView> implements SendCodeContract.IUpdateView,SendCodeContract.ISendCodePresent {
    @Override
    protected SendCodeModel createModel() {
        return new SendCodeModel(this);
    }
    public void login(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .login(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(getView()) {
                    @Override
                    public void onSuccess(UserBean o) {
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
    public void regist(String tag, RequestBody body) {
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



    /**
     * 检查手机号的格式
     */
    public boolean checkMobile(String mobile) {
        if (mobile == null || TextUtils.isEmpty(mobile)) {
            if (getView() != null) {
                getView().checkFormatError("手机号码不能为空");
            }

            return false;
        }
        if (!isMobileNO(mobile)) {
            if (getView() != null) {
                getView().checkFormatError("手机号码格式不正确");
            }
            return false;
        }
        return true;

    }


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][23456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 从网络获取验证码
     *
     * @param mobile
     */
    private void getCheckCodeFromNet(String mobile,String tag) {
        //获取验证码
        AppNetModule.createrRetrofit()
                .getSMSCode(mobile)
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

    @Override
    public void startTiming(long value) {
        if (getView() != null) {
            getView().updateSendCheckCodeViewStatus(value);
        }
    }

    @Override
    public void endTiming(long value) {
        if (getView() != null) {
            getView().updateSendCheckCodeViewStatus(value);
        }
    }

    @Override
    public void checkFormatError(String error) {
        if (getView() != null) {
            getView().checkFormatError(error);
        }
    }

    /**
     * 检查手机号的格式
     */
    public boolean mobileFormatIsOk(String mobile) {
        return getModel().mobileFormatIsOk(mobile);
    }

    @Override
    public void sendCheckCode(String mobile, String tempCode) {
        if (mobileFormatIsOk(mobile)) {
            getCheckCodeFromNet(mobile,tempCode);
        }
    }

    @Override
    public void receivedCheckCodeAndDispose() {
        getModel().receivedCheckCodeAndDispose();
    }

    /**
     * 初始化获取验证码
     */
    public void initGetTestCodeButtonStatus() {
        getModel().initGetTestCodeButtonStatus();
    }

}

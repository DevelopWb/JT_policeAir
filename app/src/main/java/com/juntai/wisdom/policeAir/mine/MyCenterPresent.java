package com.juntai.wisdom.policeAir.mine;

import android.annotation.SuppressLint;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;
import com.juntai.wisdom.basecomponent.bean.UserBean;
import com.juntai.wisdom.policeAir.bean.message.UnReadCountBean;
import com.juntai.wisdom.policeAir.mine.setting.MySettingActivity;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/7
 * email:954101549@qq.com
 */
public class MyCenterPresent extends BasePresenter<IModel, MyCenterContract.ICenterView> implements MyCenterContract.ICenterPresent {
    List<MyMenuBean> menuBeans = new ArrayList<>();
    private IView iView;

    public void setCallBack(IView iView) {
        this.iView = iView;
    }
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getUserData(String tag) {
        AppNetModule.createrRetrofit()
                .getUserData(MyApp.getAccount(), MyApp.getUserToken())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(null) {
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

    @Override
    public void getUnReadCount(String tag) {
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
                .getUnReadCount(getPublishMultipartBody().build())
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<UnReadCountBean>(null) {
                    @Override
                    public void onSuccess(UnReadCountBean o) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loginOut(String tag) {
        LogUtil.e("longitude:"+ MyApp.app.getMyLocation().longitude + "latitude"+ MyApp.app.getMyLocation().latitude);
        AppNetModule.createrRetrofit()
                .loginOut(MyApp.getAccount(), MyApp.getUserToken())
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
                            getView().onError(tag, "????????????");
                        }
                    }
                });
    }

    @Override
    public void initList() {
        menuBeans.clear();
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_score, MyCenterContract.CENTER_SCORE_TAG, MyScoreActivity.class));
////        menuBeans.add(new MyMenuBean("????????????",0,R.mipmap.my_order,MyCenterContract.CENTER_ORDER_TAG, OrderListActivity.class));
////        menuBeans.add(new MyMenuBean("????????????",0,R.mipmap.my_pingjia,MyCenterContract.CENTER_PINGJIA_TAG, MySettingActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_task, MyCenterContract.CENTER_MISSION_TAG, MyTaskListActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_favorite, MyCenterContract.CENTER_SHOUCANG_TAG, MyCollectActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_share, MyCenterContract.CENTER_SHARE_TAG, MyCollectActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_push, MyCenterContract.CENTER_FABU_TAG, MyPublishListActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_concliation, MyCenterContract.CENTER_TIAOJIE_TAG, ConciliationListActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_message, MyCenterContract.CENTER_MESSAGE_TAG, MyMessageActivity.class));
//        menuBeans.add(new MyMenuBean("????????????", 0, R.mipmap.my_device, MyCenterContract.CENTER_DEVICE_TAG, MyEquipmentListActivity.class));
        menuBeans.add(new MyMenuBean("????????????", -1, R.mipmap.my_set_list, MyCenterContract.CENTER_SETTING_TAG, MySettingActivity.class));
        menuBeans.add(new MyMenuBean("????????????", -1, R.mipmap.mycenter_quite_icon, MyCenterContract.QUITE_ACCOUNT_TAG, MySettingActivity.class));
        getView().refreshAdapter();
    }

    @Override
    public List<MyMenuBean> getMenuBeans(){
        return menuBeans;
    }

    /**
     * ??????builder
     *
     * @return
     */
    public static MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }
}

package com.juntai.wisdom.dgjxb.mine;

import android.annotation.SuppressLint;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BaseModel;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MyMenuBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.message.UnReadCountBean;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_list.ConciliationListActivity;
import com.juntai.wisdom.dgjxb.home_page.equipment.my_equipment.MyEquipmentListActivity;
import com.juntai.wisdom.dgjxb.mine.mycollect.MyCollectActivity;
import com.juntai.wisdom.dgjxb.mine.message.MyMessageActivity;
import com.juntai.wisdom.dgjxb.mine.mypublish.MyPublishListActivity;
import com.juntai.wisdom.dgjxb.mine.score.MyScoreActivity;
import com.juntai.wisdom.dgjxb.mine.setting.MySettingActivity;
import com.juntai.wisdom.dgjxb.mine.task.MyTaskListActivity;
import com.juntai.wisdom.dgjxb.utils.RxScheduler;

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
                .getUserData(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), 1)
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
                .loginOut(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),
                        String.valueOf(MyApp.app.getMyLocation().longitude),
                        String.valueOf(MyApp.app.getMyLocation().latitude),
                        1)
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
                            getView().onError(tag, "退出失败");
                        }
                    }
                });
    }

    @Override
    public void initList() {
        menuBeans.clear();
        menuBeans.add(new MyMenuBean("我的积分", 0, R.mipmap.my_score, MyCenterContract.CENTER_SCORE_TAG, MyScoreActivity.class));
//        menuBeans.add(new MyMenuBean("我的订单",0,R.mipmap.my_order,MyCenterContract.CENTER_ORDER_TAG, OrderListActivity.class));
//        menuBeans.add(new MyMenuBean("我的评价",0,R.mipmap.my_pingjia,MyCenterContract.CENTER_PINGJIA_TAG, MySettingActivity.class));
        menuBeans.add(new MyMenuBean("我的任务", 0, R.mipmap.my_task, MyCenterContract.CENTER_MISSION_TAG, MyTaskListActivity.class));
        menuBeans.add(new MyMenuBean("我的收藏", 0, R.mipmap.my_favorite, MyCenterContract.CENTER_SHOUCANG_TAG, MyCollectActivity.class));
        menuBeans.add(new MyMenuBean("我的分享", 0, R.mipmap.my_share, MyCenterContract.CENTER_SHARE_TAG, MyCollectActivity.class));
        menuBeans.add(new MyMenuBean("我的发布", 0, R.mipmap.my_push, MyCenterContract.CENTER_FABU_TAG, MyPublishListActivity.class));
        menuBeans.add(new MyMenuBean("我的调解", 0, R.mipmap.my_concliation, MyCenterContract.CENTER_TIAOJIE_TAG, ConciliationListActivity.class));
        menuBeans.add(new MyMenuBean("我的消息", 0, R.mipmap.my_message, MyCenterContract.CENTER_MESSAGE_TAG, MyMessageActivity.class));
        menuBeans.add(new MyMenuBean("我的设备", 0, R.mipmap.my_device, MyCenterContract.CENTER_DEVICE_TAG, MyEquipmentListActivity.class));
        menuBeans.add(new MyMenuBean("个人设置", -1, R.mipmap.my_set_list, MyCenterContract.CENTER_SETTING_TAG, MySettingActivity.class));
        getView().refreshAdapter();
    }

    @Override
    public List<MyMenuBean> getMenuBeans(){
        return menuBeans;
    }

    /**
     * 获取builder
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

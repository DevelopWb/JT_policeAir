package com.juntai.wisdom.dgjxb.home_page.equipment;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.bean.user_equipment.EquipmentListBean;
import com.juntai.wisdom.dgjxb.utils.RxScheduler;

/**
 * Describe:设备关联类
 * Create by zhangzhenlong
 * 2020-11-21
 * email:954101549@qq.com
 */
public class EquipmentPresent extends BasePresenter<IModel, IEquipmentContract.IEquipmentView> implements IEquipmentContract.IEquipmentPresent {

    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getMyEquipmentList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserEquipmentList(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<EquipmentListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(EquipmentListBean o) {
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

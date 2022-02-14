package com.juntai.wisdom.policeAir.mine.score;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.UserScoreListBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

/**
 * Describe:我的积分p
 * Create by zhangzhenlong
 * 2020/3/10
 * email:954101549@qq.com
 */
public class MyScorePresent extends BasePresenter<IModel,MyCenterContract.IMyScoreView> implements MyCenterContract.IMyScorePresent {
    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getScoreDetail(String type, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserScore(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),type,pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserScoreListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(UserScoreListBean o) {
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

package com.juntai.wisdom.policeAir.mine.exchange_mall;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.exchang_mall.GoodsInfoBean;
import com.juntai.wisdom.policeAir.bean.exchang_mall.GoodsListBean;
import com.juntai.wisdom.policeAir.bean.exchang_mall.HistoryGoodsListBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class ExchangeMallPresent extends BasePresenter<IModel, ExchangeMallContract.IMallView> implements ExchangeMallContract.IMallPresent {

    @Override
    protected IModel createModel() {
        return null;
    }

    @Override
    public void getAllGoodsList(String tag) {
        AppNetModule.createrRetrofit()
                .getGoodsList(MyApp.getAccount(), MyApp.getUserToken())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<GoodsListBean>(getView()) {
                    @Override
                    public void onSuccess(GoodsListBean o) {
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
    public void getExchangeHistoryList(String tag) {
        AppNetModule.createrRetrofit()
                .getHistoryGoodsList(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HistoryGoodsListBean>(getView()) {
                    @Override
                    public void onSuccess(HistoryGoodsListBean o) {
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
    public void getGoodsDetail(int id, String tag) {
        AppNetModule.createrRetrofit()
                .getGoodsDetail(MyApp.getAccount(), MyApp.getUserToken(),id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<GoodsInfoBean>(getView()) {
                    @Override
                    public void onSuccess(GoodsInfoBean o) {
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
    public void exchangeGoods(int price, int commodityId, String commodityName, String tag) {
        AppNetModule.createrRetrofit()
                .exchangeGoods(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),price,commodityId,commodityName)
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

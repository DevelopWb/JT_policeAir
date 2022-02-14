package com.juntai.wisdom.policeAir.home_page.business;


import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.bean.business.BusinessListBean;
import com.juntai.wisdom.policeAir.bean.business.BusinessNeedInfoBean;
import com.juntai.wisdom.policeAir.bean.business.MyBusinessBean;
import com.juntai.wisdom.policeAir.bean.business.MyBusinessDetailBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class BusinessPresent extends BasePresenter<IModel, BusinessContract.IBusinessView> implements BusinessContract.IBusinessPresent {
    @Override
    protected IModel createModel() {
        return null;
    }




    @Override
    public void businessList(String account,String token,String keyWord, int pageSize, int currentPage, String tag) {
        AppNetModule.createrRetrofit()
                .businessList(account,token,keyWord,pageSize,currentPage)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessListBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessListBean o) {
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
    public void businessDataNeeded(int declareId, String tag) {
        AppNetModule.createrRetrofit()
                .businessDataNeeded(MyApp.getAccount(), MyApp.getUserToken(), declareId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BusinessNeedInfoBean>(getView()) {
                    @Override
                    public void onSuccess(BusinessNeedInfoBean o) {
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
    public void creatBusiness(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .creatBusiness(requestBody)
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
    public void businessDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .businessDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MyBusinessDetailBean>(getView()) {
                    @Override
                    public void onSuccess(MyBusinessDetailBean o) {
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
    public void businessProgress(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .businessProgress(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MyBusinessBean>(getView()) {
                    @Override
                    public void onSuccess(MyBusinessBean o) {
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
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }

    /**
     * 获取业务列表
     *
     * @return
     */
    public List<MyMenuBean> getBusinessList() {
        List<MyMenuBean> menus = new ArrayList<>();
        String[] names = {"出生随父母落户", "死亡注销", "参军入伍", "住址变动所内移居", "购房落户", "持准迁证户口迁出", "升学户口迁出", "变更姓氏并改名字", "换领身份证", "临时身份证", "申领居住证", "更多",};
        int[] images = {R.mipmap.business_hukou_by_present, R.mipmap.business_die_unregist, R.mipmap.business_join, R.mipmap.business_house_move, R.mipmap.business_hukou_by_house, R.mipmap.business_hukou_out_by_prove, R.mipmap.business_hukou_out_by_school, R.mipmap.business_change_name, R.mipmap.business_change_idcard, R.mipmap.business_idcard_iterim, R.mipmap.business_receive_live_card, R.mipmap.business_more};
        for (int i = 0; i < 12; i++) {
            menus.add(new MyMenuBean(names[i], i,images[i]));
        }
        return menus;
    }

    /**
     * 获取业主基本信息
     * @param businessName  业务名称
     * @return
     */
    public List<TextListBean> getUserBaseInfo(String  businessName){
        List<TextListBean>  arrays = new ArrayList<>();
        arrays.add(new TextListBean("事项名称",businessName));
        arrays.add(new TextListBean("姓名", MyApp.getUser().getData().getRealName()));
        arrays.add(new TextListBean("身份证号", MyApp.getUser().getData().getIdNumber()));
        arrays.add(new TextListBean("手机号码", MyApp.getUser().getData().getAccount()));
        return arrays;
    }
}

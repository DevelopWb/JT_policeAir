package com.juntai.wisdom.policeAir.home_page.site_manager;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.bean.site.CustomerSourceBean;
import com.juntai.wisdom.policeAir.bean.site.EmployeeDetailBean;
import com.juntai.wisdom.policeAir.bean.site.EmployeeListBean;
import com.juntai.wisdom.policeAir.bean.site.SiteInspectionDetailBean;
import com.juntai.wisdom.policeAir.bean.site.SiteTypeBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;
import com.juntai.wisdom.policeAir.utils.UrlFormatUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Describe:场所管理present
 * Create by zhangzhenlong
 * 2020-5-22
 * email:954101549@qq.com
 */
public class SiteManagerPresent extends BasePresenter<IModel, SiteManagerContract.ISiteManagerView>
        implements SiteManagerContract.SiteManagerPresent {

    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public MultipartBody.Builder getPublishMultipartBody() {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken());
    }

    @Override
    public void getEmployeeList(int id, int pageSize, int pageNum, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getEmployeeList(MyApp.getAccount(), MyApp.getUserToken(), id, pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<EmployeeListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(EmployeeListBean o) {
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
    public void getEmployeeDetail(String tag, int id) {
        AppNetModule.createrRetrofit()
                .getEmployeeDetail(MyApp.getAccount(), MyApp.getUserToken(),id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<EmployeeDetailBean>(getView()) {
                    @Override
                    public void onSuccess(EmployeeDetailBean o) {
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
    public void getSiteInspectionList(int id, int pageSize, int pageNum, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getSiteInspectionList(MyApp.getAccount(), MyApp.getUserToken(), id, pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<EmployeeListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(EmployeeListBean o) {
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
    public void getSiteInspectionDetail(String tag, int id) {
        AppNetModule.createrRetrofit()
                .getSiteInspectionDetail(MyApp.getAccount(), MyApp.getUserToken(),id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SiteInspectionDetailBean>(getView()) {
                    @Override
                    public void onSuccess(SiteInspectionDetailBean o) {
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
    public void addEmployee(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .addEmployee(requestBody)
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
    public void addSiteInspection(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .addSiteInspection(requestBody)
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
    public void getSiteTypes(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .getSiteTypes(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SiteTypeBean>(getView()) {
                    @Override
                    public void onSuccess(SiteTypeBean o) {
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
    public void getCustomerSources(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .getCustomerSources(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CustomerSourceBean>(getView()) {
                    @Override
                    public void onSuccess(CustomerSourceBean o) {
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
    public void addSiteManager(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .addSiteManager(requestBody)
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
    public void updateSiteManager(String tag, RequestBody requestBody) {
        AppNetModule.createrRetrofit()
                .updateSiteManager(requestBody)
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

    /**
     * 获取图片相关的数据
     *
     * @return
     */
    public List<TextListBean> getPics() {
        List<TextListBean> arrays = new ArrayList<>();
        arrays.add(new TextListBean("门面照", ""));
        arrays.add(new TextListBean("营业执照", ""));
        arrays.add(new TextListBean("房屋结构图", ""));
        arrays.add(new TextListBean("消防设备图", ""));
        arrays.add(new TextListBean("监控设备图", ""));
        arrays.add(new TextListBean("更多", ""));
        return arrays;
    }

    /**
     * 修改场所，初始化图片
     * @param ids
     * @return
     */
    public List<TextListBean> getPics(String[] ids){
        List<TextListBean> arrays = new ArrayList<>();
        for (int i = 0; i < ids.length; i++){
            arrays.add(getImagePath(i, ids[i]));
        }
        arrays.add(new TextListBean("更多", ""));
        return arrays;
    }

    public TextListBean getImagePath(int position, String id){
        switch (position){
            case 0:
                return new TextListBean("门面照", UrlFormatUtil.getInspectionOriginalImg(Integer.parseInt(id)));
            case 1:
                return new TextListBean("营业执照",UrlFormatUtil.getInspectionThumImg(Integer.parseInt(id)));
            case 2:
                return new TextListBean("房屋结构图",UrlFormatUtil.getInspectionThumImg(Integer.parseInt(id)));
            case 3:
                return new TextListBean("消防设备图",UrlFormatUtil.getInspectionThumImg(Integer.parseInt(id)));
            case 4:
                return new TextListBean("监控设备图",UrlFormatUtil.getInspectionThumImg(Integer.parseInt(id)));
            default:
                return new TextListBean("",UrlFormatUtil.getInspectionThumImg(Integer.parseInt(id)));
        }
    }
}

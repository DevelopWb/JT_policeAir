package com.juntai.wisdom.policeAir.home_page.map;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.base.BaseAppPresent;
import com.juntai.wisdom.policeAir.bean.BannerNewsBean;
import com.juntai.wisdom.policeAir.bean.FlyOperatorsBean;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseDesBean;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseInfoBean;
import com.juntai.wisdom.policeAir.bean.history_track.HistoryTrackBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseDrone;
import com.juntai.wisdom.policeAir.bean.map.ResponseKeyPersonnel;
import com.juntai.wisdom.policeAir.bean.inspection.InspectionPointInfoBean;
import com.juntai.wisdom.policeAir.bean.inspection.InspectionRecordBean;
import com.juntai.wisdom.policeAir.bean.map.MapClusterItem;
import com.juntai.wisdom.policeAir.bean.MapMenuButton;
import com.juntai.wisdom.policeAir.bean.PoliceCarBean;
import com.juntai.wisdom.policeAir.bean.PoliceDetailBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseSiteBean;
import com.juntai.wisdom.policeAir.bean.site.UnitDetailBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseCarHistory;
import com.juntai.wisdom.policeAir.bean.map.ResponseInspection;
import com.juntai.wisdom.policeAir.bean.map.ResponseNews;
import com.juntai.wisdom.policeAir.bean.map.ResponsePeople;
import com.juntai.wisdom.policeAir.bean.key_personnel.InterviewListBean;
import com.juntai.wisdom.policeAir.bean.key_personnel.KeyPersonnelInfoBean;
import com.juntai.wisdom.policeAir.bean.stream.StreamCameraBean;
import com.juntai.wisdom.policeAir.utils.RxScheduler;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class MapPresenter extends BaseAppPresent<IModel, MapContract.View> implements MapContract.Presenter {
    @Override
    protected IModel createModel() {
        return null;
    }


    @Override
    public void getMenus(String tag) {
        AppNetModule.createrRetrofit()
                .getMapMenu("1")
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MapMenuButton>(getView()) {
                    @Override
                    public void onSuccess(MapMenuButton o) {
                        if (toastMessage(o.getData(),"暂无菜单数据")){
                            return;
                        }
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
    public void getCameras(String tag) {
        AppNetModule.createrRetrofit()
                .requestCamera(getBaseFormBodyBuilder().build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraBean>(getView()) {
                    @Override
                    public void onSuccess(StreamCameraBean o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getCases(String tag) {
        AppNetModule.createrRetrofit()
                .requestCase(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CaseDesBean>(getView()) {
                    @Override
                    public void onSuccess(CaseDesBean o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getPolices(String tag) {
        AppNetModule.createrRetrofit()
                .requestPeople(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponsePeople>(getView()) {
                    @Override
                    public void onSuccess(ResponsePeople o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getPolicesTrack(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getPoliceTrack(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<HistoryTrackBean>(getView()) {
                    @Override
                    public void onSuccess(HistoryTrackBean o) {
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
    public void getAllOperators(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getAllOperators(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<FlyOperatorsBean>(getView()) {
                    @Override
                    public void onSuccess(FlyOperatorsBean o) {
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
    public void getPoliceCarTrack(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getPoliceCarTrack(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseCarHistory>(getView()) {
                    @Override
                    public void onSuccess(ResponseCarHistory o) {
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
    public void getPoliceCars(String tag) {
        AppNetModule.createrRetrofit()
                .requestCar(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PoliceCarBean>(getView()) {
                    @Override
                    public void onSuccess(PoliceCarBean o) {
                        if (toastMessage(o.getData(),"数据升级中，请稍后刷新")){
                            return;
                        }
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
    public void getInspection(String tag) {
        AppNetModule.createrRetrofit()
                .requestInspection()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseInspection>(getView()) {
                    @Override
                    public void onSuccess(ResponseInspection o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getNews(String tag) {
        AppNetModule.createrRetrofit()
                .requestNews(getBaseFormBodyBuilder().build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseNews>(getView()) {
                    @Override
                    public void onSuccess(ResponseNews o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getBannerNews(String tag) {
        AppNetModule
                .createrRetrofit()
                .getBannerNews()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BannerNewsBean>(null) {
                    @Override
                    public void onSuccess(BannerNewsBean o) {
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
    public void getPolicePeopleDetail(MapClusterItem item, String tag) {
        AppNetModule.createrRetrofit()
                .requestPeopleDetail(getBaseFormBodyBuilder().add("id",String.valueOf(item.people.getId())).build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<PoliceDetailBean>(getView()) {
                    @Override
                    public void onSuccess(PoliceDetailBean o) {
                        if (getView() != null) {
                            item.people.setState(o.getData().getState());
                            item.people.setAccount(o.getData().getAccount());
                            getView().onSuccess(tag, item);
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
    public void getUnitDetail(int unitId) {
        AppNetModule.createrRetrofit()
                .getUnitDetail(getBaseFormBodyBuilder().add("id",String.valueOf(unitId)).build())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UnitDetailBean>(getView()) {
                    @Override
                    public void onSuccess(UnitDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess("", o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError("", msg);
                        }
                    }
                });
    }

    @Override
    public void getAllStreamCamerasFromVCR(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getAllStreamCamerasFromVCR(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<StreamCameraBean>(getView()) {
                    @Override
                    public void onSuccess(StreamCameraBean o) {
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
    public void getKeyPersonnels(String tag) {
        AppNetModule.createrRetrofit()
                .requestKeyPersonnel(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseKeyPersonnel>(getView()) {
                    @Override
                    public void onSuccess(ResponseKeyPersonnel o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getKeyPersonnelInfo(String tag, int id) {
        AppNetModule.createrRetrofit()
                .getKeyPersonnelInfo(MyApp.getAccount(), MyApp.getUserToken(), id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<KeyPersonnelInfoBean>(getView()) {
                    @Override
                    public void onSuccess(KeyPersonnelInfoBean o) {
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
    public void getInterviewList(int id, int pageNum, int pageSize, String tag) {
        AppNetModule.createrRetrofit()
                .getInterviewList(MyApp.getAccount(),MyApp.getUserToken(),id,pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InterviewListBean>(null) {
                    @Override
                    public void onSuccess(InterviewListBean o) {
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
    public void getSiteManagers(String tag) {
        AppNetModule.createrRetrofit()
                .requestSiteList(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseSiteBean>(getView()) {
                    @Override
                    public void onSuccess(ResponseSiteBean o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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
    public void getDrones(String tag) {
        AppNetModule.createrRetrofit()
                .requestDrone(getRequestBodyOf())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseDrone>(getView()) {
                    @Override
                    public void onSuccess(ResponseDrone o) {
                        if (toastMessage(o.getData(),"")){
                            return;
                        }
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


    public RequestBody getRequestBodyOf() {
        return getBaseFormBodyBuilder()
                .add("departmentId", MyApp.getUser().getData().getDepartmentId()+"").build();
    }

    /**
     * @param typeId (0:今日)（1:昨日）(2:自定义)
     * @return
     */
    public FormBody.Builder getRequestBodyOfPoliceTrack(int typeId, String userId) {
        return new FormBody.Builder()
                .add("account", MyApp.getAccount())
                .add("token", MyApp.getUserToken())
                .add("userId", userId)
                .add("typeId", String.valueOf(typeId));
    }

    public FormBody.Builder getBaseFormBodyBuilder() {
        return new FormBody.Builder()
                .add("account", MyApp.getAccount()==null?"":MyApp.getAccount())
                .add("token", MyApp.getUserToken()==null?"":MyApp.getUserToken())
                .add("userId", MyApp.getUid()==-1?"":String.valueOf(MyApp.getUid()));
    }

    @Override
    public void getCaseInfo(String tag, int id) {
        AppNetModule
                .createrRetrofit()
                .getCaseInfo(MyApp.getUserToken(), MyApp.getAccount(),id)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CaseInfoBean>(getView()) {
                    @Override
                    public void onSuccess(CaseInfoBean o) {
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
    public void getInspectionPointInfo(int patrolId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getInspectionPointDetail(MyApp.getAccount(), MyApp.getUserToken(),patrolId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InspectionPointInfoBean>(getView()) {
                    @Override
                    public void onSuccess(InspectionPointInfoBean o) {
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
    public void getInspectionRecord(int patrolId, int pageNum, int pageSize, String tag,  boolean isShow) {
        AppNetModule
                .createrRetrofit()
                .getInspectionRecord(MyApp.getAccount(), MyApp.getUserToken(),patrolId,pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<InspectionRecordBean>(isShow? getView() : null) {
                    @Override
                    public void onSuccess(InspectionRecordBean o) {
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
     * 获取案件类型 1待处理 2处理中 3已完成
     * @param type
     * @return
     */
    public String  getCaseTypeName (int type){
        String typeName = "";
        switch (type) {
            case 1:
                typeName = "待处理";
                break;
            case 2:
                typeName = "处理中";
                break;
            case 3:
                typeName = "已完成";
                break;
            default:
                typeName = "无";
                break;
        }
        return typeName;
    }

    /**
     * 空数据提示
     * @param data
     * @param message
     * @return
     */
    public boolean toastMessage(List data, String message){
        if (data == null || data.size() == 0){
            if (message == null || message.equals("")){
                ToastUtils.info(MyApp.app,"暂无数据");
            }else {
                ToastUtils.info(MyApp.app,message);
            }
            return true;
        }
        return false;
    }
}

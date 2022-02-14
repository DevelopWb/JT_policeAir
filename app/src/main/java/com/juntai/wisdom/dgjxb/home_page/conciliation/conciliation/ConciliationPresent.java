package com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.GlideEngine4;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationInfoBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationListBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationTypesBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.MediatorAllListBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.UnitListBean;
import com.juntai.wisdom.dgjxb.utils.RxScheduler;
import com.juntai.wisdom.video.record.VideoPreviewActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020-5-22
 * email:954101549@qq.com
 */
public class ConciliationPresent extends BasePresenter<IModel, ConciliationContract.IConciliationView>
        implements ConciliationContract.IConciliationPresent {
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void publishConciliation(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .publishConciliation(requestBody)
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
    public void getConciliationInfo(int caseId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getConciliationInfo(MyApp.getAccount(), MyApp.getUserToken(), caseId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ConciliationInfoBean>(getView()) {
                    @Override
                    public void onSuccess(ConciliationInfoBean o) {
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
    public void selectCaseNumberIsCorrect(String caseNumber, String tag) {
        AppNetModule
                .createrRetrofit()
                .selectCaseNumberIsCorrect(MyApp.getAccount(), MyApp.getUserToken(),caseNumber)
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
    public void getUnitList(String cityNumber, String tag, boolean showProgress) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule
                .createrRetrofit()
                .getUnitList(MyApp.getAccount(), MyApp.getUserToken(), cityNumber)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<UnitListBean>(showProgress ? viewCallBack : null) {
                    @Override
                    public void onSuccess(UnitListBean o) {
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

    @Override
    public void getMediatorList(String cityNumber, int unitId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getAllMediatorList(MyApp.getAccount(), MyApp.getUserToken(), cityNumber, unitId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MediatorAllListBean>(null) {
                    @Override
                    public void onSuccess(MediatorAllListBean o) {
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
    public void getConciliationTypeList(int typeId, String tag, boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getConciliationTypes(MyApp.getAccount(), MyApp.getUserToken(), typeId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ConciliationTypesBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(ConciliationTypesBean o) {
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
    public void getMyConciliationList(int type, int pageSize, int currentPage, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getMyConciliationList(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), type, pageSize, currentPage)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ConciliationListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(ConciliationListBean o) {
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
     * 录制视频
     *
     * @param activity
     */
    @SuppressLint("CheckResult")
    public void recordVideo(FragmentActivity activity) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 录制

                            MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                                    .fullScreen(false)
                                    .smallVideoWidth(500)
                                    .smallVideoHeight(480)
                                    .recordTimeMax(10000)
                                    .recordTimeMin(2000)
                                    .videoBitrate(960 * 640)
                                    .captureThumbnailsTime(1)
                                    .build();
                            MediaRecorderActivity.goSmallVideoRecorder(activity, VideoPreviewActivity.class.getName()
                                    , config);
                        } else {
                            Toasty.info(activity, "请给与相应权限").show();
                        }
                    }
                });
    }

    //选择视频
    @SuppressLint("CheckResult")
    public void videoChoose(FragmentActivity activity) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(activity)
                                    .choose(MimeType.ofVideo())
                                    .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                    .countable(true)
                                    .maxSelectable(1)                     // 最多选择一张
                                    .capture(false)//出现拍攝选项
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7
                                    // .0系统 必须设置
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(ConciliationContract.SELECT_VIDEO_RESULT);
                        } else {
                            Toasty.info(activity, "请给与相应权限").show();
                        }
                    }
                });
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

package com.juntai.wisdom.dgjxb.mine.mycollect;

import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.bean.BaseDataBean;
import com.juntai.wisdom.dgjxb.bean.CollectListBean;
import com.juntai.wisdom.dgjxb.mine.MyCenterContract;
import com.juntai.wisdom.dgjxb.utils.RxScheduler;

import java.util.List;

import okhttp3.FormBody;

/**
 * Describe:收藏、分享
 * Create by zhangzhenlong
 * 2020-3-12
 * email:954101549@qq.com
 */
public class MyCollectPresent extends BasePresenter<IModel,MyCenterContract.ICollectView> implements MyCenterContract.ICollectPresent {
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void  setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getCollectListCamera(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserCollectCamera(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
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
    public void getCollectListNews(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserCollectNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(),pageNum,pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
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
    public void getShareListCamera(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserShareCamera(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
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
    public void getShareListNews(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getUserShareNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectListBean>(showProgress? getView():null) {
                    @Override
                    public void onSuccess(CollectListBean o) {
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
    public void deleteCollecListNews(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids, String tag) {
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
                .addOrDeleteCollectsNews(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType, typeId, collectId, ids)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏成功");
                        }
                        LogUtil.e("添加收藏成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏失败");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏失败");
                        }
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteCollecListCamera(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids, String tag) {
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
                .addOrDeleteCollectsCamera(id, MyApp.getAccount(), MyApp.getUserToken(),userId,isType, typeId, collectId, ids)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏成功");
                        }
                        LogUtil.e("添加收藏成功");
                        if (finalViewCallBack != null){
                            finalViewCallBack.onSuccess(tag,o);
                        }
                    }
                    @Override
                    public void onError(String msg) {
                        if (isType == 1){//取消
                            ToastUtils.success(MyApp.app,"取消收藏失败");
                        }else {
                            ToastUtils.success(MyApp.app,"收藏失败");
                        }
                        if (finalViewCallBack != null){
                            finalViewCallBack.onError(tag,msg);
                        }
                    }
                });
    }

    @Override
    public void deleteShareListCamera(List<Integer> ids, String tag) {
        AppNetModule.createrRetrofit()
                .deleteCameraShare(MyApp.getAccount(), MyApp.getUserToken(), 1, ids)
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
    public void deleteShareListNews(List<Integer> ids,String tag) {
        AppNetModule.createrRetrofit()
                .addOrDeleteShares(MyApp.getAccount(), MyApp.getUserToken(),null,1, null, null, ids)
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
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account", MyApp.getAccount());
        builder.add("token", MyApp.getUserToken());
        return builder;
    }
}

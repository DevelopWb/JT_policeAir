package com.juntai.wisdom.policeAir.home_page.news;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.base.BaseFragment;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.bean.MoreMenuBean;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.GlideEngine4;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.BaseDataBean;
import com.juntai.wisdom.policeAir.bean.CommentListBean;
import com.juntai.wisdom.basecomponent.bean.ReportTypeBean;
import com.juntai.wisdom.policeAir.bean.news.NewsDetailBean;
import com.juntai.wisdom.policeAir.bean.news.NewsFansListBean;
import com.juntai.wisdom.policeAir.bean.news.NewsListBean;
import com.juntai.wisdom.policeAir.bean.news.NewsPersonalHomePageInfo;
import com.juntai.wisdom.policeAir.bean.news.NewsUploadPhotoBean;
import com.juntai.wisdom.policeAir.home_page.PublishContract;
import com.juntai.wisdom.policeAir.utils.RxScheduler;
import com.juntai.wisdom.video.record.VideoPreviewActivity;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Describe:资讯present
 * Create by zhangzhenlong
 * 2020-7-29
 * email:954101549@qq.com
 */
public class NewsPresent extends BasePresenter<IModel, NewsContract.INewsView> implements NewsContract.INewsPresent {
    private int compressedSize = 0;//被压缩的图片个数
    List<String> icons = new ArrayList<>();
    private IView iView;

    @Override
    protected IModel createModel() {
        return null;
    }

    public void setCallBack(IView iView) {
        this.iView = iView;
    }

    @Override
    public void getNewsList(int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule.createrRetrofit()
                .getNewsList(MyApp.isLogin()? MyApp.getUid() : 0, pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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
    public void publishNews(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .publishNews(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.message == null ? "" : o.message);
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
    public void updateNews(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .updateNews(requestBody)
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
    public void uploadNewsPhoto(String tag, RequestBody requestBody) {
        AppNetModule
                .createrRetrofit()
                .uploadNewsPhoto(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsUploadPhotoBean>(getView()) {
                    @Override
                    public void onSuccess(NewsUploadPhotoBean o) {
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
    public void searchNewsList(String keyWord, int pageNum, int pageSize, String tag) {
        AppNetModule
                .createrRetrofit()
                .searchNewsList(keyWord, pageSize, pageNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(getView()) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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
    public void getNewsInfo(int newsId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getNewsInfo(MyApp.getUid(), newsId)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsDetailBean>(getView()) {
                    @Override
                    public void onSuccess(NewsDetailBean o) {
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
    public void getAuthorInfo(int authorId, String tag) {
        AppNetModule
                .createrRetrofit()
                .getNewsAuthorInfo(authorId, MyApp.getUid())
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsPersonalHomePageInfo>(null) {
                    @Override
                    public void onSuccess(NewsPersonalHomePageInfo o) {
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
    public void getNewsListByAuthorId(int authorId, int typeId, int pageNum, int pageSize, String tag,
                                      boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getNewsListByAuthorId(authorId, typeId, pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsListBean o) {
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
    public void getFansList(int followId, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getFansList(MyApp.getAccount(), MyApp.getUserToken(), followId, MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsFansListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsFansListBean o) {
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
    public void getFollowList(int fansId, int pageNum, int pageSize, String tag, boolean showProgress) {
        AppNetModule
                .createrRetrofit()
                .getFollowList(MyApp.getAccount(), MyApp.getUserToken(), fansId, MyApp.getUid(), pageNum, pageSize)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<NewsFansListBean>(showProgress ? getView() : null) {
                    @Override
                    public void onSuccess(NewsFansListBean o) {
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
    public void addFollowOrDelete(int typeId, int followId, String tag) {
        AppNetModule
                .createrRetrofit()
                .addFollowOrDelete(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), typeId, followId)
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
    public void getCommentList(int commentedId, int pageSize, int currentPage, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;

        AppNetModule.createrRetrofit()
                .getAllCommentNews(MyApp.getAccount(), MyApp.getUserToken(), MyApp.getUid(), commentedId, pageSize,
                        currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(getView()) {
                    @Override
                    public void onSuccess(CommentListBean o) {
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
    public void getCommentChildList(int commentedId, int unreadId, int pageSize, int currentPage, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .getChildCommentNews(MyApp.getAccount(), MyApp.getUserToken(), commentedId, unreadId, pageSize,
                        currentPage)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<CommentListBean>(viewCallBack) {
                    @Override
                    public void onSuccess(CommentListBean o) {
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
    public void like(int id, int userId, int isType, int typeId, int likeId, String tag) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        IView finalViewCallBack = viewCallBack;
        AppNetModule.createrRetrofit()
                .addOrCancleLikeNews(id, MyApp.getAccount(), MyApp.getUserToken(), userId, isType, typeId, likeId)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new BaseObserver<BaseDataBean>(viewCallBack) {
                    @Override
                    public void onSuccess(BaseDataBean o) {
                        if (isType == 1) {//取消
                            ToastUtils.success(MyApp.app, "取消点赞");
                        } else {
                            ToastUtils.success(MyApp.app, "点赞成功");
                        }
                        LogUtil.e("更新点赞成功");
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (isType == 1) {//取消
                            ToastUtils.success(MyApp.app, "取消点赞失败");
                        } else {
                            ToastUtils.success(MyApp.app, "点赞失败");
                        }
                        LogUtil.e("更新点赞失败");
                        if (finalViewCallBack != null) {
                            finalViewCallBack.onError(tag, msg);
                        }
                    }
                });
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

    @SuppressLint("CheckResult")
    public void imageChoose(BaseFragment context) {
        //        SoftReference<Activity> softReference = new SoftReference<>(baseActivity);
        new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(context)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                    .countable(true)
                                    .maxSelectable(9)   // 最多选择一张
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7
                                    // .0系统 必须设置
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(NewsContract.REQUEST_CODE_CHOOSE);
                        } else {
                            ToastUtils.info(context.getContext(), "请给与相应权限");
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void imageChoose(BaseActivity context, int picCount) {
        //        SoftReference<Activity> softReference = new SoftReference<>(baseActivity);
        new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(context)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                    .countable(true)
                                    .maxSelectable(picCount)   // 最多选择一张
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7
                                    // .0系统 必须设置
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(NewsContract.REQUEST_CODE_CHOOSE);
                        } else {
                            ToastUtils.info(context, "请给与相应权限");
                        }
                    }
                });
    }

    /**
     * 图片压缩
     */
    public void imageCompress(List<String> paths, BaseMvpFragment iView) {
        compressedSize = 0;
        icons.clear();
        iView.showLoading();
        Luban.with(iView.getContext()).load(paths).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  压缩开始前调用，可以在方法内启动 loading UI
            }

            @Override
            public void onSuccess(File file) {
                compressedSize++;
                //  压缩成功后调用，返回压缩后的图片文件
                icons.add(file.getPath());
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                    iView.onSuccess(NewsContract.YASUO_PHOTO_TAG, icons);
                }
            }

            @Override
            public void onError(Throwable e) {
                //  当压缩过程出现问题时调用
                compressedSize++;
                LogUtil.e("push-图片压缩失败");
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                }
            }
        }).launch();
    }

    /**
     * 图片压缩
     */
    public void imageCompress(List<String> paths, BaseMvpActivity iView) {
        compressedSize = 0;
        icons.clear();
        iView.showLoading();
        Luban.with(iView).load(paths).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  压缩开始前调用，可以在方法内启动 loading UI
            }

            @Override
            public void onSuccess(File file) {
                compressedSize++;
                //  压缩成功后调用，返回压缩后的图片文件
                icons.add(file.getPath());
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                    iView.onSuccess(NewsContract.YASUO_PHOTO_TAG, icons);
                }
            }

            @Override
            public void onError(Throwable e) {
                //  当压缩过程出现问题时调用
                compressedSize++;
                LogUtil.e("push-图片压缩失败");
                if (compressedSize == paths.size()) {
                    iView.showLoading();
                }
            }
        }).launch();
    }

    /**
     * 录制视频
     *
     * @param fragment
     */
    @SuppressLint("CheckResult")
    public void recordVideo(BaseFragment fragment) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(fragment)
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
                            MediaRecorderActivity.goSmallVideoRecorder(fragment.getActivity(),
                                    VideoPreviewActivity.class.getName()
                                    , config);
                        } else {
                            Toasty.info(fragment.getContext(), "请给与相应权限").show();
                        }
                    }
                });
    }

    //选择视频
    @SuppressLint("CheckResult")
    public void videoChoose(BaseFragment fragment) {
        IView viewCallBack = null;
        if (getView() == null) {
            if (iView != null) {
                viewCallBack = iView;
            }
        } else {
            viewCallBack = getView();
        }
        new RxPermissions(fragment)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .compose(RxScheduler.ObsIoMain(viewCallBack))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(fragment)
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
                                    .forResult(PublishContract.SELECT_VIDEO_RESULT);
                        } else {
                            Toasty.info(fragment.getContext(), "请给与相应权限").show();
                        }
                    }
                });
    }

    public void getReportTypes(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .getReportTypes(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ReportTypeBean>(getView()) {
                    @Override
                    public void onSuccess(ReportTypeBean o) {
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

    public void report(RequestBody requestBody, String tag) {
        AppNetModule
                .createrRetrofit()
                .report(requestBody)
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

    /**
     * 设置分享菜单
     * @return
     */
    public List<MoreMenuBean> getMoreMenu(boolean isCollect, boolean canDownLoad){
        List<MoreMenuBean> moreMenuBeans = new ArrayList<>();
        moreMenuBeans.add(new MoreMenuBean("微信", R.mipmap.share_wechat));
        moreMenuBeans.add(new MoreMenuBean("朋友圈", R.mipmap.share_wechatmoments));
        moreMenuBeans.add(new MoreMenuBean("微信收藏", R.mipmap.share_wechatfavorite));
        moreMenuBeans.add(new MoreMenuBean("QQ", R.mipmap.share_qq));
        moreMenuBeans.add(new MoreMenuBean("QQ空间", R.mipmap.share_qzone));
        moreMenuBeans.add(new MoreMenuBean("投诉", R.mipmap.share_report));
//        moreMenuBeans.add(new MoreMenuBean("收藏", R.drawable.share_collect_bg_selector, isCollect));
        moreMenuBeans.add(new MoreMenuBean("下载", R.mipmap.share_download, isCollect, canDownLoad));
        moreMenuBeans.add(new MoreMenuBean("复制链接", R.mipmap.share_copy_link));
        return moreMenuBeans;
    }
}

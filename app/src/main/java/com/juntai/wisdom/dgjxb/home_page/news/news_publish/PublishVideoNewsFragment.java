package com.juntai.wisdom.dgjxb.home_page.news.news_publish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.Logger;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseBottomDialog;
import com.juntai.wisdom.bdmap.act.LocationSeltionActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.entrance.LoginActivity;
import com.juntai.wisdom.dgjxb.home_page.PublishContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.home_page.news.news_publish.preview.VideoPreActivity;
import com.juntai.wisdom.dgjxb.utils.DateUtil;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.vincent.videocompressor.VideoCompress;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 发布视频资讯
 * @aouther ZhangZhenlong
 * @date 2020-7-30
 */
public class PublishVideoNewsFragment extends BaseMvpFragment<NewsPresent> implements NewsContract.INewsView, View.OnClickListener {

    private ImageView mAddTag;
    private ImageView mVideoImage;
    private ImageView mVideoTag;
    /**
     * 起一个吸引人的名称让更多人看到哟
     */
    private EditText mTitleEt;
    /**
     * 简单介绍下视频吧
     */
    private EditText mDescriptionEt;
    /**
     * 已输入0/500
     */
    private TextView mLimitTv;
    /**
     * 位置
     */
    private TextView mAddressTv;

    //地址
    BDLocation bdLocation;
    private String locAddress = "";
    private Double locLat, locLon;
    //视频回调广播
    IntentFilter intentFilter = new IntentFilter();
    private VideoBroadcastReceiver videoBroadcastReceiver = null;
    //视频
    private String videoScreen;//视频封面
    private String videoPath;//视频地址
    private BaseBottomDialog baseBottomDialog;
    private BaseBottomDialog.OnItemClick onItemClick;
    /**
     * 预览
     */
    private TextView mYulanBtn;
    /**
     * 发布
     */
    private TextView mFabuBtn;
    private LinearLayout mOperationLayout;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_publish_video_news;
    }

    @Override
    public void initView() {
        mAddTag = getView(R.id.add_tag);
        mAddTag.setOnClickListener(this);
        mVideoImage = getView(R.id.video_image);
        mVideoTag = getView(R.id.video_tag);
        mTitleEt = getView(R.id.title_et);
        mDescriptionEt = getView(R.id.description_et);
        mLimitTv = getView(R.id.limit_tv);
        mAddressTv = getView(R.id.address_tv);
        mAddressTv.setOnClickListener(this);
        mYulanBtn = getView(R.id.yulan_btn);
        mYulanBtn.setOnClickListener(this);
        mFabuBtn = getView(R.id.fabu_btn);
        mFabuBtn.setOnClickListener(this);
        mOperationLayout = getView(R.id.operation_layout);
        mOperationLayout.setVisibility(View.VISIBLE);

        mDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    mLimitTv.setText("已输入" + s.length() + "/500");
                }
            }
        });

        //注册广播
        videoBroadcastReceiver = new VideoBroadcastReceiver();
        intentFilter.addAction(ActionConfig.BROAD_VIDEO);
        mContext.registerReceiver(videoBroadcastReceiver, intentFilter);
    }

    @Override
    public void initData() {
        setDefaultData();
        getBaseActivity().initViewRightDrawable(mAddressTv, R.mipmap.ic_push_location,23,23);
    }

    @Override
    protected void lazyLoad() {}

    /**
     * 设置默认数据
     */
    public void setDefaultData() {
        bdLocation = LocateAndUpload.bdLocation;
        if (bdLocation != null) {
            locAddress = bdLocation.getAddrStr() != null ? bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet() : "";
            locLat = bdLocation.getLatitude();
            locLon = bdLocation.getLongitude();
        } else {
            locAddress = "";
            locLat = 0.0;
            locLon = 0.0;
        }
        mAddressTv.setText(locAddress);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case NewsContract.PUBLISH_VIDEO_NEWS:
                ToastUtils.success(mContext, (String)o);
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                Objects.requireNonNull(getActivity()).finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.warning(mContext, String.valueOf(o));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.add_tag:
                //添加视频
                initBottomDialog(Arrays.asList("直接拍摄","本地视频"));
                break;
            case R.id.address_tv:
                //地址选择
                Intent intent = new Intent(mContext, LocationSeltionActivity.class);
                startActivityForResult(intent, PublishContract.REQUEST_CODE_CHOOSE_PLACE);
                break;
            case R.id.yulan_btn:
                submitData(1);
                break;
            case R.id.fabu_btn:
                submitData(0);
                break;
        }
    }

    /**
     * 发布
     */
    public void submitData(int type){
        if (MyApp.isFastClick()) {
            ToastUtils.warning(mContext,"点击过于频繁");
            return;
        }
        if (!StringTools.isStringValueOk(getBaseActivity().getTextViewValue(mTitleEt))) {
            ToastUtils.warning(mContext, "请输入标题");
            return;
        }
        if (!StringTools.isStringValueOk(videoPath)){
            ToastUtils.warning(mContext, "请选择视频");
            return;
        }
        if (!StringTools.isStringValueOk(locAddress)){
            ToastUtils.warning(mContext, "请选择发布位置");
            return;
        }
        if (!StringTools.isStringValueOk(videoScreen)){
            ToastUtils.warning(mContext, "视频封面图获取失败，请重新选择视频");
            return;
        }
        if (type == 0){
            //发布
            MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
            builder.addFormDataPart("userid",String.valueOf(MyApp.getUid()))
                    .addFormDataPart("longitude", String.valueOf(locLon))
                    .addFormDataPart("latitude", String.valueOf(locLat))
                    .addFormDataPart("typeId", "1")
                    .addFormDataPart("informationId", "0")
                    .addFormDataPart("title", getBaseActivity().getTextViewValue(mTitleEt))
                    .addFormDataPart("content", getBaseActivity().getTextViewValue(mDescriptionEt))
                    .addFormDataPart("address", locAddress);

            if (StringTools.isStringValueOk(videoPath)) {
                builder.addFormDataPart("videoFile", "videoFile", RequestBody.create(MediaType.parse("file"), new File(videoPath)));
            }
            if (StringTools.isStringValueOk(videoScreen)) {
                builder.addFormDataPart("coverFile", "coverFile", RequestBody.create(MediaType.parse("file"), new File(videoScreen)));
            }
            mPresenter.publishNews(NewsContract.PUBLISH_VIDEO_NEWS, builder.build());
        }else {
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("longitude", String.valueOf(locLon));
            bodyMap.put("latitude", String.valueOf(locLat));
            bodyMap.put("typeId", "1");
            bodyMap.put("informationId", "0");
            bodyMap.put("title", getBaseActivity().getTextViewValue(mTitleEt));
            bodyMap.put("content", getBaseActivity().getTextViewValue(mDescriptionEt));
            bodyMap.put("address", locAddress);
            bodyMap.put("videoPath", videoPath);
            bodyMap.put("videoScreen",videoScreen);
            startActivity(new Intent(mContext, VideoPreActivity.class).putExtra(VideoPreActivity.VIDEO_PART_BODY, (Serializable) bodyMap));
        }
    }

    /**
     * 视频录制成功回调广播
     */
    public class VideoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            videoPath = intent.getStringExtra("videoUri");
            videoScreen = intent.getStringExtra("videoScreenshotUri");
            Logger.e("videoSize", new File(videoPath).length()+"byte");
            ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mVideoImage);
            mVideoTag.setVisibility(View.VISIBLE);
            mAddTag.setVisibility(View.GONE);
            mVideoImage.setBackgroundColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public void onDestroyView() {
        if (videoBroadcastReceiver != null) {
            mContext.unregisterReceiver(videoBroadcastReceiver);
        }
        releaseDialog();
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublishContract.SELECT_VIDEO_RESULT && resultCode == getActivity().RESULT_OK){
            List<String> paths = Matisse.obtainPathResult(data);
            if (paths != null && paths.size() > 0){
                File videoFile = new File(paths.get(0));
                if (0 < videoFile.length()/1024 && videoFile.length()/1024 < 10000){
                    getVideoThumb(paths.get(0));
                    videoPath = paths.get(0);
                }else if (new File(paths.get(0)).length()/1024 < 50000){
                    if (StringTools.isStringValueOk(paths.get(0))){
                        getVideoThumb(paths.get(0));
                        compressVideo(paths.get(0));
                    }
                }else {
                    ToastUtils.warning(mContext,"请选择小于50m的视频");
                }
            }
        }else if (requestCode == PublishContract.REQUEST_CODE_CHOOSE_PLACE && resultCode == getActivity().RESULT_OK) {
            //地址选择
            locLat = data.getDoubleExtra("lat", 0.0);
            locLon = data.getDoubleExtra("lng", 0.0);
            locAddress = data.getStringExtra("address");
            mAddressTv.setText(locAddress);
            LogUtil.d("fffffffff" + locLat + "   " + locLon + "    " + locAddress);
        }
    }

    /**
     * 获取本地视频封面图
     * @param path 视频地址
     */
    private void getVideoThumb(String path){
//        showLoading();
        ImageLoadUtil.getBitmap(mContext.getApplicationContext(), path, R.drawable.nopicture, new ImageLoadUtil.BitmapCallBack() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                videoScreen = FileCacheUtils.saveBitmap(bitmap);
//                hideLoading();
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), videoScreen, mVideoImage);
                mVideoTag.setVisibility(View.VISIBLE);
                mAddTag.setVisibility(View.GONE);
                mVideoImage.setBackgroundColor(mContext.getResources().getColor(R.color.black));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化dialog
     */
    public void initBottomDialog(List<String> arrays) {

        if (baseBottomDialog == null) {
            onItemClick = new BaseBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (position){
                        case 0:
                            //拍摄
                            mPresenter.recordVideo(PublishVideoNewsFragment.this);
                            break;
                        case 1:
                            //选择
                            mPresenter.videoChoose(PublishVideoNewsFragment.this);
                            break;
                    }
//                    releaseDialog();
                    baseBottomDialog.dismiss();
                }
            };
            baseBottomDialog = new BaseBottomDialog();
            baseBottomDialog.setOnBottomDialogCallBack(onItemClick);
        }
        baseBottomDialog.setData(arrays);
        baseBottomDialog.show(getChildFragmentManager(), "arrays");
    }

    /**
     * 释放dialog
     */
    private void releaseDialog() {
        if (baseBottomDialog != null) {
            if (baseBottomDialog.isAdded()) {
                onItemClick = null;
                baseBottomDialog.setOnBottomDialogCallBack(null);
                if (baseBottomDialog.getDialog().isShowing()){
                    baseBottomDialog.dismiss();
                }
            }
        }
        baseBottomDialog = null;
    }

    /**
     * 视频压缩
     * @param oldPath
     */
    private void compressVideo(String oldPath){
        final MaterialDialog dialog = new MaterialDialog.Builder(getContext())
//                .title("视频压缩中。。。")// 标题
                .content("视频压缩中。。。")// 内容
                // 第一个参数表示是否是不确定的，该值为 true 表示是不确定进度条，反之为确定进度条
                // 第二个参数表示进度条的最大值
                // 第三个参数表示是否显示百分比，如果该值为 true 表示显示百分比，反之不显示百分比
                .progress(false, 100, false)// 确定进度条
                // 自定义进度格式
                .progressPercentFormat(NumberFormat.getPercentInstance())
                .canceledOnTouchOutside(false)
                .show();// 显示对话框
        //输出路径
//        showLoading();
        String outPath = FileCacheUtils.getAppVideoPath() + DateUtil.getCurrentTime("yyyyMMddHHmmss") + ".mp4";
        VideoCompress.compressVideoMedium(oldPath, outPath, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                //开始
                Logger.e("stat_compress", DateUtil.getCurrentTime("yyyyMMddHHmmss"));
                Logger.e("oldSize", new File(oldPath).length()+"byte");
//                ToastUtils.warning(mContext, "视频压缩中，请稍后。。。");
            }

            @Override
            public void onSuccess() {
                videoPath = outPath;
//                getVideoThumb();
                dialog.dismiss();
                Logger.e("success_compress", DateUtil.getCurrentTime("yyyyMMddHHmmss"));
                Logger.e("outSize", new File(outPath).length()+"byte");
            }

            @Override
            public void onFail() {
                dialog.dismiss();
                ToastUtils.error(mContext, "视频获取失败，请重新选择");
            }

            @Override
            public void onProgress(float v) {
                Logger.w("progress", v+"%");
                dialog.setProgress((int)v);
            }
        });
    }
}

package com.juntai.wisdom.dgjxb.home_page.news.news_publish.preview;

import com.bumptech.glide.Glide;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.video.Jzvd.JzvdStdPortraitScreen;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.jzvd.Jzvd;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @description 视频资讯预览
 * @aouther ZhangZhenlong
 * @date 2020-12-31
 */
public class VideoPreActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView {
    public static final String VIDEO_PART_BODY = "video_part_body";
    private JzvdStdPortraitScreen mInfoVideo;
    private String videoPath;
    private String videoThumbPath;
    Map<String, String> bodyMap;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_video_pre;
    }

    @Override
    public void initView() {
        setTitleName("资讯预览");
        bodyMap = (Map<String, String>) getIntent().getSerializableExtra(VIDEO_PART_BODY);
        videoPath = Objects.requireNonNull(bodyMap.get("videoPath"));
        videoThumbPath = Objects.requireNonNull(bodyMap.get("videoScreen"));
        mInfoVideo = (JzvdStdPortraitScreen) findViewById(R.id.info_video);

        getTitleRightTv().setText("发布");
        getTitleRightTv().setOnClickListener(v -> {
            if (bodyMap != null && bodyMap.size() > 0){
                submitData();
            }else {
                ToastUtils.warning(mContext, "请重新编辑！");
            }
        });
    }

    @Override
    public void initData() {
        if (StringTools.isStringValueOk(videoPath)){
            initVideo(videoPath, "", videoThumbPath);
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case NewsContract.PUBLISH_VIDEO_NEWS:
                ToastUtils.success(mContext, (String)o);
                EventManager.sendStringMsg(ActionConfig.FINISH_AFTER_PUBISH);
                EventManager.sendStringMsg(ActionConfig.UPDATE_NEWS_LIST);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化播放
     * @param path
     */
    private void initVideo(String path, String title, String coverUrl) {
        mInfoVideo.setUp(path, "");
        mInfoVideo.getLayoutParams().height = MyApp.H / 3;
        Glide.with(mContext.getApplicationContext()).load(coverUrl).into(mInfoVideo.thumbImageView);
        mInfoVideo.startVideo();
    }

    /**
     * 发布
     */
    public void submitData(){
        if (MyApp.isFastClick()) {
            ToastUtils.warning(mContext,"点击过于频繁");
            return;
        }
        //发布
        MultipartBody.Builder builder = mPresenter.getPublishMultipartBody();
        builder.addFormDataPart("userid",String.valueOf(MyApp.getUid()))
                .addFormDataPart("longitude", Objects.requireNonNull(bodyMap.get("longitude")))
                .addFormDataPart("latitude", Objects.requireNonNull(bodyMap.get("latitude")))
                .addFormDataPart("typeId", Objects.requireNonNull(bodyMap.get("typeId")))
                .addFormDataPart("informationId", Objects.requireNonNull(bodyMap.get("informationId")))
                .addFormDataPart("title", Objects.requireNonNull(bodyMap.get("title")))
                .addFormDataPart("content", Objects.requireNonNull(bodyMap.get("content")))
                .addFormDataPart("address", Objects.requireNonNull(bodyMap.get("address")))
                .addFormDataPart("videoFile", "videoFile", RequestBody.create(MediaType.parse("file"), new File(videoPath)))
                .addFormDataPart("coverFile", "coverFile", RequestBody.create(MediaType.parse("file"), new File(videoThumbPath)));
        mPresenter.publishNews(NewsContract.PUBLISH_VIDEO_NEWS, builder.build());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}

package com.juntai.wisdom.video.player;

import com.bumptech.glide.Glide;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadActivity;
import com.juntai.wisdom.video.Jzvd.JzvdStdPortraitScreen;
import com.juntai.wisdom.video.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;

/**
 * @aouther Ma
 * @date 2019/4/1
 * <p>
 * 视频播放
 * Intent intent = new Intent(mContext, PlayerActivity.class);
 * String ss = "http://kb167.cn:43212/zhcg/u/appInferFace/appDownloadVideo.shtml?token="
 * +App.getUserToken()+"&account="
 * +App.getAccount()+"&id=1628";
 * intent.putExtra("playPath",ss);
 * intent.putExtra("savePath", FileCacheUtils.getAppVideoPath()+1628+".mp4");
 * startActivity(intent);
 */

public class PlayerActivity extends BaseDownLoadActivity {
    private String playPath;
    private String thumbImage;
    private JzvdStdPortraitScreen jzvdStd;

    public static final String VIDEO_PATH = "video_path";
    public static final String VIDEO_THUMB_IMAGE = "video_thumb_image";

    @Override
    public int getLayoutView() {
        return R.layout.activity_player;
    }

    @Override
    public void initView() {
        setTitleName("视频");
        //下载视频到本地播放
        playPath = getIntent().getStringExtra(VIDEO_PATH);
        thumbImage = getIntent().getStringExtra(VIDEO_THUMB_IMAGE);
        jzvdStd = findViewById(R.id.videoplayer);
    }


    @Override
    public void initData() {
        JZDataSource jzDataSource = new JZDataSource(playPath,"");
//        jzDataSource.looping = true;
        jzvdStd.setUp(jzDataSource,Jzvd.SCREEN_NORMAL);
//        Jzvd.SAVE_PROGRESS = true;
//        jzvdStd.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (thumbImage != null && !thumbImage.equals("")){
            Glide.with(mContext.getApplicationContext()).load(thumbImage).into(jzvdStd.thumbImageView);
        }else {
            Glide.with(mContext.getApplicationContext()).load(playPath).into(jzvdStd.thumbImageView);
        }
        jzvdStd.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected String getTitleRightName() {
        return "保存视频";
    }

    @Override
    protected String getDownLoadPath() {
        return playPath;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}

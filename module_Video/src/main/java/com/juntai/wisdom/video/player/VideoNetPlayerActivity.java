package com.juntai.wisdom.video.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.video.Jzvd.MyJzvdStd;
import com.juntai.wisdom.video.R;

import cn.jzvd.Jzvd;

public class VideoNetPlayerActivity extends BaseActivity {
    @Override
    public int getLayoutView() {
        return R.layout.activity_drone_player;
    }
    MyJzvdStd myJzvdStd;
    Intent intent;
    String path;
    @Override
    public void initView() {
        intent = getIntent();
        path = intent.getStringExtra("path");
        if(path == null){
            ToastUtils.toast(mContext,"视频无法播放");
            finish();
        }
        getToolbar().setVisibility(View.GONE);
        init();
    }

    private void init() {
        myJzvdStd = findViewById(R.id.jz_video);
        myJzvdStd.setUp("rtmp://www.juntaikeji.com:19601/live/1", "无人机实时画面");
//        myJzvdStd.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext.getApplicationContext()).load(path).into(myJzvdStd.thumbImageView);
        myJzvdStd.startVideoAfterPreloading();
    }


    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
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

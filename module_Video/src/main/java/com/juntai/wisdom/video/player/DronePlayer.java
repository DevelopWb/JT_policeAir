package com.juntai.wisdom.video.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.video.Jzvd.MyJzvdStd;
import com.juntai.wisdom.video.R;

import cn.jzvd.Jzvd;

public class DronePlayer extends BaseActivity {

    MyJzvdStd myJzvdStd;
    Intent intent;
    int droneId;

    @Override
    public int getLayoutView() {
        return R.layout.activity_drone_player;
    }

    @Override
    public void initView() {
        intent = getIntent();
        droneId = intent.getIntExtra("droneId",-1);
        if(droneId<0){
            ToastUtils.toast(mContext,"无人机错误");
            finish();
        }
        getToolbar().setVisibility(View.GONE);
        init();
    }

    private void init() {
        String source1 = "rtmp://61.156.157.132:33332/live/"+droneId;
        myJzvdStd = findViewById(R.id.jz_video);
        myJzvdStd.setUp(source1, "无人机实时画面");
//        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(myJzvdStd.thumbImageView);
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

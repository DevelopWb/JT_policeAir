package com.juntai.wisdom.dgjxb.home_page.camera.ijkplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.utils.MediaUtils;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.juntai.wisdom.basecomponent.base.BaseDownLoadActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.PubUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseDrawerLayout;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayContract;
import com.juntai.wisdom.dgjxb.home_page.camera.PlayPresent;
import com.juntai.wisdom.dgjxb.utils.GlideImageLoader;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

/**
 * @aouther tobato
 * @description 描述   车辆/无人机直播
 * @date 2020/7/25 16:44
 */
public class CarLiveActivity extends BaseDownLoadActivity<PlayPresent> implements PlayContract.IPlayView,
        View.OnClickListener, BaseDownLoadActivity.OnFileDownloaded {

    public static String STREAM_CAMERA_URL = "stream_url";
    public static String STREAM_CAMERA_NAME = "stream_url_name";

    public static String STREAM_CAMERA_ID = "stream_id";
    public static String STREAM_CAMERA_THUM_URL = "stream_thum_url";//缩略图路径
    private PlayerView player;
    private PowerManager.WakeLock wakeLock;
    public String playName;//
    private String playUrl;
    private String mThumUrl;
    private int mCameraId;//

    public static boolean isVerScreen = true;//是否是竖屏
    private LinearLayout mVideoViewLl;
    private BaseDrawerLayout mDrawerlayout;
    private Group mOperateRightIvsG, mHorSuspensionG, mVerSuspensionG;

    private ImageView mZoomShrinkIv;
    private ImageView mVerCaptureIv;//竖屏模式下的截屏
    private ImageView mVideoIv;
    private boolean hideAllScreen = false;//是否隐藏所有按钮

    private GlideImageLoader imageLoader;
    private Banner mBanner;
    private CountDownTimer timer;
    private int countDownTime = 5;
    private int time;
    private TextView mCountdownTv;

    @Override
    protected PlayPresent createPresenter() {
        return new PlayPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.car_stream;
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            playUrl = getIntent().getStringExtra(STREAM_CAMERA_URL);
            playName = getIntent().getStringExtra(STREAM_CAMERA_NAME);
            mCameraId = getIntent().getIntExtra(STREAM_CAMERA_ID, 0);
            mThumUrl = getIntent().getStringExtra(STREAM_CAMERA_THUM_URL);
        }
        hideBottomVirtureBar();
        setFileDownLoadCallBack(this);
        setTitleName(playName);
        mVideoViewLl = (LinearLayout) findViewById(R.id.video_view_ll);
        initDrawerlayout();

        mZoomShrinkIv = (ImageView) findViewById(R.id.zoom_shrink_iv);
        mZoomShrinkIv.setOnClickListener(this);
        mVerCaptureIv = (ImageView) findViewById(R.id.app_video_capture);
        mVerCaptureIv.setOnClickListener(this);
        mVideoIv = (ImageView) findViewById(R.id.video_iv);
        mVideoIv.setOnClickListener(this);

        mBanner = findViewById(R.id.banner);
        mCountdownTv = (TextView) findViewById(R.id.countdown_tv);
    }

    /**
     * 初始化抽屉布局
     */
    private void initDrawerlayout() {
        mDrawerlayout = findViewById(R.id.drawerlayout);
        mOperateRightIvsG = findViewById(R.id.operate_right_ivs_g);
        mHorSuspensionG = findViewById(R.id.horizontal_suspension_g);
        mVerSuspensionG = findViewById(R.id.vertical_suspension_g);
        mDrawerlayout.setScrimColor(Color.TRANSPARENT);
        mDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                mOperateRightIvsG.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mOperateRightIvsG.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }


    @Override
    public void initData() {
        imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
            @Override
            public void onFullScreen() {

            }
        });
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(mThumUrl);
        mBanner.setImages(arrayList).setImageLoader(imageLoader).start();
        time = countDownTime;
        timer = new CountDownTimer(countDownTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCountdownTv.setText("倒计时:"+time);
                time--;
            }

            @Override
            public void onFinish() {
                time = 0;
                mBanner.setVisibility(View.GONE);
                mCountdownTv.setVisibility(View.GONE);
                if (player.getOffLineStatus()) {
                    player.showThumbnail(new OnShowThumbnailListener() {
                        @Override
                        public void onShowThumbnail(ImageView ivThumbnail) {
                            ImageLoadUtil.loadImage(mContext, R.mipmap.dev_offline_icon, ivThumbnail);
                        }
                    });
                }

            }
        }.start();
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去除这个Activity的标题栏
        super.onCreate(savedInstanceState);
        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this, mBaseRootCol)
                //隐藏顶部布局
                .hideHideTopBar(false)
                //分辨率
                .hideSteam(true)
                //旋转
                .hideRotation(true)
                //隐藏全屏按钮，true隐藏，false为显示
                .hideFullscreen(false)
                .hideCenterPlayer(true)
                .forbidTouch(false)
                .setForbidDoulbeUp(true)
                .setThumPic(mThumUrl)
                .setScaleType(PlayStateParams.fitxy);

        player.getFullScreenView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(playUrl)) {
                    player.setOnlyFullScreen(true);
                } else {
                    ToastUtils.toast(mContext, "无法获取流地址");
                }
            }
        });

        player.getBackView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换到竖屏模式
                player.setOnlyFullScreen(false);
            }
        });
        player.isOffLine(false);
        player.setPlaySource(playUrl).startPlay();
//        ToastUtils.warning(mContext, "设备离线");
    }

    @Override
    protected String getTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_iv:
                ToastUtils.toast(mContext, "暂未开放");
                break;
            case R.id.app_video_capture:
                //竖屏模式下的截屏
                ToastUtils.toast(mContext, "暂未开放");
                break;
            case R.id.zoom_shrink_iv:
                //切换到竖屏模式
                player.setOnlyFullScreen(false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }


    @Override
    public void onBackPressed() {
        if (player.isOnlyFullScreen) {
            //切换到竖屏模式
            player.setOnlyFullScreen(false);
            return;
        }
        if (wakeLock != null) {
            wakeLock.release();
        }
        if (player != null && player.onBackPressed()) {
            return;
        }
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onSuccess(String tag, Object o) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isVerScreen = true;
        if (player != null) {
            player.onDestroy();
        }
        setFileDownLoadCallBack(null);
        if (timer != null) {
            timer.cancel();
            timer = null;

        }
        if (mBanner != null) {
            mBanner.releaseBanner();
            mBanner.removeAllViews();
            mBanner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
                imageLoader.release();
            }

        }
        mBanner = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            isVerScreen = false;
            //显示横屏的布局
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mVideoViewLl.getLayoutParams();
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mVideoViewLl.setLayoutParams(params);
            mPresenter.setMarginOfConstraintLayout(mVideoViewLl, mContext, 0, 0, 0, 0);
            getToolbar().setVisibility(View.GONE);
            mVideoViewLl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    player.setFatherW_H(mVideoViewLl.getTop(), mVideoViewLl.getBottom());
                }
            }, 100);
            player.updateRender();
            initLayoutByOritation();
        } else {
            //竖屏
            //            showBottomVirtureBar();
            isVerScreen = true;
            //显示竖屏的布局
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mVideoViewLl.getLayoutParams();
            params.height = DisplayUtil.dp2px(mContext, 190);
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            mVideoViewLl.setLayoutParams(params);
            getToolbar().setVisibility(View.VISIBLE);
            mPresenter.setMarginOfConstraintLayout(mVideoViewLl, mContext, 10, 10, 10, 10);
            getToolbar().setVisibility(View.VISIBLE);
            player.updateRender();
            initLayoutByOritation();
        }
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    /**
     * 初始化布局
     */
    private void initLayoutByOritation() {
        if (isVerScreen) {
            //隐藏横屏悬浮布局 显示竖屏 悬浮布局

            mHorSuspensionG.setVisibility(View.GONE);
            mOperateRightIvsG.setVisibility(View.GONE);
            if (hideAllScreen) {
                mVerSuspensionG.setVisibility(View.GONE);
            } else {
                mVerSuspensionG.setVisibility(View.VISIBLE);
            }
        } else {
            //隐藏竖屏悬浮布局 显示横屏 悬浮布局
            mVerSuspensionG.setVisibility(View.GONE);
            if (hideAllScreen) {
                mHorSuspensionG.setVisibility(View.GONE);
                mOperateRightIvsG.setVisibility(View.GONE);
            } else {
                mHorSuspensionG.setVisibility(View.VISIBLE);
                mOperateRightIvsG.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 隐藏pad底部虚拟键
     */
    protected void hideBottomVirtureBar() {
        Window window;
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onOneClick(String msg) {
        switch (msg) {
            case PubUtil.ONE_CLICK_EVENT:
                hideAllScreen = !hideAllScreen;
                if (hideAllScreen) {
                    player.getBarPlayerView().setVisibility(View.GONE);
                    player.getBarSoundView().setVisibility(View.GONE);
                } else {
                    player.getBarPlayerView().setVisibility(View.VISIBLE);
                    player.getBarSoundView().setVisibility(View.VISIBLE);
                }
                initLayoutByOritation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFileDownloaded(String fileName) {

    }
}

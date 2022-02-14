package com.juntai.wisdom.policeAir.home_page.news.exoplayer_tiktok;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.basecomponent.mvp.IModel;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.video.Jzvd.JzvdStdTikTok;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;

public class TikTokActivity extends BaseMvpActivity {

    private RecyclerView mVideoRecyclerView;
    private TikTokRecyclerViewAdapter tikTokRecyclerViewAdapter;
    private List<String> videoPaths = new ArrayList<>();
    ViewPagerLayoutManager mViewPagerLayoutManager;

    private int mCurrentPosition = -1;

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter() {
            @Override
            protected IModel createModel() {
                return null;
            }
        };
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_tik_tok;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        mVideoRecyclerView = (RecyclerView) findViewById(R.id.video_recycler_view);
//        new PagerSnapHelper().attachToRecyclerView(mVideoRecyclerView);
        tikTokRecyclerViewAdapter = new TikTokRecyclerViewAdapter(R.layout.tiktok_video_item_layout, videoPaths);
        mViewPagerLayoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        mVideoRecyclerView.setLayoutManager(mViewPagerLayoutManager);
        mVideoRecyclerView.setAdapter(tikTokRecyclerViewAdapter);

        mViewPagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                autoPlayVideo();
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) {
                    Jzvd.releaseAllVideos();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                if (mCurrentPosition == position){
                    return;
                }
                if (isBottom) {
                    //是最底部，执行加载更多数据
//                    loadData()
                }
                autoPlayVideo();
                mCurrentPosition = position;
            }
        });

        mVideoRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.getCurrentUrl())) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        videoPaths.add("rtmp://www.juntaikeji.com:19601/live/1");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-05/21d0dec6c5154ee19373a1b4194ca634.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-05/2a0bb826913844b8a20cf169cf4e9ceb.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-05/8aea2e700b434a059e84b885b6fa87e7.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-05/796d31c9743843cd91ae96c820ccf031.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-06/7bfba5731e5e41d4910e286fd2327812.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-06/0b60b4a7fc3f47a4924c1eea8e21678c.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-06/8912189189484659a35922edd11a340d.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-07/5c5677dad7c14cb6a22793756543797a.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-08/2dc5269dad0a4fdcbd531702ec7f3c40.mp4");
//        videoPaths.add("https://image.juntaikeji.com/2021-01-08/9aa43045a7844f01b099bcdcecd079b3.mp4");
        tikTokRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initToolbarAndStatusBar() {
        getToolbar().setVisibility(View.GONE);
//        getToolbar().setNavigationIcon(null);
//        getToolbar().setBackgroundResource(R.drawable.bg_white_only_bottom_gray_shape_1px);
        //状态栏配置
        mBaseRootCol.setFitsSystemWindows(true);
        mImmersionBar.statusBarColor(R.color.black)
                .statusBarDarkFont(false)
                .init();
    }

    /**
     * 滑动后自动播放。
     */
    private void autoPlayVideo() {
        if (mVideoRecyclerView == null || mVideoRecyclerView.getChildAt(0) == null){
            return;
        }
        JzvdStdTikTok player = mVideoRecyclerView.getChildAt(0).findViewById(R.id.video_item);
        if (player != null) {
            player.startVideoAfterPreloading();
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {}

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
}

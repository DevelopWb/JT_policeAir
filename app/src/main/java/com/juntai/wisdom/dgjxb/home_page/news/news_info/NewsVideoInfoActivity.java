package com.juntai.wisdom.dgjxb.home_page.news.news_info;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danikula.videocache2.HttpProxyCacheServer;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.customview.WarnDialog;
import com.juntai.wisdom.dgjxb.bean.news.NewsDetailBean;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.juntai.wisdom.dgjxb.utils.UserInfoManager;
import com.juntai.wisdom.video.Jzvd.MyJzvdStd;
import com.juntai.wisdom.video.Jzvd.OnFullScreenClickListener;
import com.juntai.wisdom.video.player.PlayerActivity;

import cn.jzvd.Jzvd;

import static com.juntai.wisdom.dgjxb.MyApp.getProxy;


/**
 * @description 视频资讯详情
 * @aouther ZhangZhenlong
 * @date 2020-8-13
 */
public class NewsVideoInfoActivity extends BaseNewsInfoActivity {

    private MyJzvdStd mInfoVideo;
    private View mPartingLine;
    private LinearLayout mContentTag;
    /**
     * 内容
     */
    private TextView mInfoContent;
    private NestedScrollView mInfoScrollView;
    private ImageView mBackIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止系统截屏、录屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected String getTitleRightName() {
        return "";
    }

    @Override
    protected String getDownLoadPath() {
        return "";
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_news_video_info;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        mInfoVideo = (MyJzvdStd) findViewById(R.id.info_video);
        mPartingLine = (View) findViewById(R.id.parting_line);
        mInfoContent = (TextView) findViewById(R.id.info_content);
        mInfoScrollView = findViewById(R.id.info_scroll_view);
        mContentTag = findViewById(R.id.content_tag);
        mBackIv = findViewById(R.id.back_iv);
        mBackIv.setOnClickListener(this);
        mInfoScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 > 10){
                    mPartingLine.setVisibility(View.VISIBLE);
                }else {
                    mPartingLine.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getNewsInfo(newsId, NewsContract.GET_NEWS_INFO);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_iv){
            onBackPressed();
            return;
        }
        super.onClick(v);
        switch (v.getId()){
            case R.id.info_guanzhu_btn:
                if (0 == UserInfoManager.getAccountStatus()) {
                    //游客模式
                    MyApp.goLogin();
                    return;
                }
                //关注
                if (newsDetailBean.getIsFollow() > 0 ){
                    //删除
                    mPresenter.addFollowOrDelete(2, newsDetailBean.getUserId(), NewsContract.DELETE_FOLLOW);
                }else {
                    //添加
                    mPresenter.addFollowOrDelete(1, newsDetailBean.getUserId(), NewsContract.ADD_FOLLOW);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag){
            case NewsContract.GET_NEWS_INFO:
                NewsDetailBean news = (NewsDetailBean) o;
                newsDetailBean = news.getData();
                if (newsDetailBean == null || newsDetailBean.getId() == 0){
                    ToastUtils.warning(this,"数据已删除！");
                    finish();
                }else {
                    viewSetData();
                    mInfoTimeRead.setText(newsDetailBean.getBrowseNum() + "次播放\u3000" + newsDetailBean.getGmtCreate() + "发布");
                    if (StringTools.isStringValueOk(newsDetailBean.getVideoUrl())){
                        initVideo(newsDetailBean.getVideoUrl(), newsDetailBean.getTitle(), newsDetailBean.getCoverUrl());
                    }
                    if (StringTools.isStringValueOk(newsDetailBean.getContent())){
                        mContentTag.setVisibility(View.VISIBLE);
                        mInfoContent.setText(newsDetailBean.getContent());
                        mInfoContent.setVisibility(View.VISIBLE);
                    }else {
                        mContentTag.setVisibility(View.GONE);
                        mInfoContent.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        switch (tag){
            case NewsContract.GET_NEWS_INFO:
                mInfoVideo.setUp(null, "");
                mInfoVideo.getLayoutParams().height = MyApp.H / 3;
                break;
        }
    }

    /**
     * 开始播放
     */
    private void checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            WarnDialog warnDialog = new WarnDialog(mContext).builder().setTextStyle();
            warnDialog.getContentTextView().setMovementMethod(LinkMovementMethod.getInstance());
            warnDialog.setCanceledOnTouchOutside(false)
                    .setTitle("提示")
                    .setContent(getResources().getString(R.string.warn_not_wifi))
                    .setCancelButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    })
                    .setOkButton("继续", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mInfoVideo.startVideo();
                        }
                    }).show();
        } else if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            mInfoVideo.startVideo();
        } else {
            ToastUtils.warning(mContext,getString(R.string.bad_network));
        }
    }

    /**
     * 初始化播放
     * @param path
     */
    private void initVideo(String path, String title, String coverUrl) {
        HttpProxyCacheServer proxy = getProxy(mContext);
        proxy.preLoad(path, 5);
        String proxyUrl =proxy.getProxyUrl(path); //获取缓存地址
        mInfoVideo.setUp(proxyUrl, "");
        mInfoVideo.getLayoutParams().height = MyApp.H / 3;
        Jzvd.WIFI_TIP_DIALOG_SHOWED = true;
        Jzvd.SAVE_PROGRESS = true;
        Glide.with(mContext.getApplicationContext()).load(coverUrl).into(mInfoVideo.thumbImageView);
        //设置全屏按键功能
        mInfoVideo.setOnFullScreenClickListener(new OnFullScreenClickListener() {
            @Override
            public void onFullScreenClick() {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra(PlayerActivity.VIDEO_PATH, proxyUrl);
                startActivity(intent);
            }
        });
        checkNetwork();
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

    @Override
    protected void onDestroy() {
//        Jzvd.SAVE_PROGRESS = false;
        Jzvd.WIFI_TIP_DIALOG_SHOWED = false;
        if (mInfoVideo != null) {
            mInfoVideo.setOnFullScreenClickListener(null);
        }
        super.onDestroy();
    }
}

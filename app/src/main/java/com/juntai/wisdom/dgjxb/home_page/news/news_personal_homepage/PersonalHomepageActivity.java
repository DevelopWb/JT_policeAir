package com.juntai.wisdom.dgjxb.home_page.news.news_personal_homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.MainPagerAdapter;
import com.juntai.wisdom.dgjxb.base.customview.CustomViewPager;
import com.juntai.wisdom.dgjxb.base.customview.NewsLikeCountDialog;
import com.juntai.wisdom.dgjxb.bean.news.NewsPersonalHomePageInfo;
import com.juntai.wisdom.dgjxb.home_page.InfoDetailPresent;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.home_page.news.NewsPresent;
import com.juntai.wisdom.dgjxb.home_page.news.news_search.NewsSearchActivity;
import com.juntai.wisdom.dgjxb.mine.myinfo.MyInformationActivity;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.ToolShare;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;
import com.juntai.wisdom.im.ModuleIm_Init;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * @description 个人主页
 * @aouther ZhangZhenlong
 * @date 2020-8-16
 */
public class PersonalHomepageActivity extends BaseMvpActivity<NewsPresent> implements NewsContract.INewsView, View.OnClickListener, ViewPager.OnPageChangeListener {

    /**
     * 返回
     */
    private TextView mTitleBackTv;
    /**
     * 标题
     */
    private TextView mTitleNameTv;
    private ImageView mTitleSearchIv;
    private ImageView mTitleShareIv;
    private RelativeLayout mTitleLayout;
    private ImageView mAuthorImage;
    /**
     * 747
     */
    private TextView mNewsCountTv;
    /**
     * 747
     */
    private TextView mGuanzhuCountTv;
    /**
     * 454
     */
    private TextView mFansCountTv;
    /**
     * 747
     */
    private TextView mZanCountTv;
    /**
     * 关注
     */
    private TextView mGuanzhuBtn;
    /**
     * 私信
     */
    private TextView mSixinBtn;
    private TabLayout mTablayout;
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;
    private int authorId;//作者id
    private CustomViewPager mViewpager;
    NewsPersonalHomePageInfo.DataBean authorInfo;

    InfoDetailPresent infoDetailPresent;

    MainPagerAdapter adapter;
    private String[] title = new String[]{"全部", "文章", "视频"};
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;
    /**
     * 关注
     */
    private LinearLayout mGuanzhuTag;
    /**
     * 粉丝
     */
    private LinearLayout mFansTag;
    /**
     * 获赞
     */
    private LinearLayout mZanTag;
    private LinearLayout mGuanzhuLayout;

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        authorId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        mTitleBackTv = (TextView) findViewById(R.id.title_back_tv);
        mTitleBackTv.setOnClickListener(this);
        mTitleNameTv = (TextView) findViewById(R.id.title_name_tv);
        mTitleSearchIv = (ImageView) findViewById(R.id.title_search_iv);
        mTitleSearchIv.setOnClickListener(this);
        mTitleShareIv = (ImageView) findViewById(R.id.title_share_iv);
        mTitleShareIv.setOnClickListener(this);
        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
        mAuthorImage = (ImageView) findViewById(R.id.author_image);
        mNewsCountTv = (TextView) findViewById(R.id.news_count_tv);
        mGuanzhuCountTv = (TextView) findViewById(R.id.guanzhu_count_tv);
        mFansCountTv = (TextView) findViewById(R.id.fans_count_tv);
        mZanCountTv = (TextView) findViewById(R.id.zan_count_tv);
        mGuanzhuBtn = (TextView) findViewById(R.id.guanzhu_btn);
        mGuanzhuBtn.setOnClickListener(this);
        mSixinBtn = (TextView) findViewById(R.id.sixin_btn);
        mSixinBtn.setOnClickListener(this);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        mGuanzhuTag = (LinearLayout) findViewById(R.id.guanzhu_tag);
        mGuanzhuTag.setOnClickListener(this);
        mFansTag = (LinearLayout) findViewById(R.id.fans_tag);
        mFansTag.setOnClickListener(this);
        mZanTag = (LinearLayout) findViewById(R.id.zan_tag);
        mZanTag.setOnClickListener(this);
        mGuanzhuLayout = (LinearLayout) findViewById(R.id.guanzhu_layout);
        mViewpager = (CustomViewPager) findViewById(R.id.viewpager);

        infoDetailPresent = new InfoDetailPresent();
        infoDetailPresent.setCallBack(this);

        mFragments.append(0, PersonalNewsListFragment.newInstance(authorId, 0));
        mFragments.append(1, PersonalNewsListFragment.newInstance(authorId, 2));
        mFragments.append(2, PersonalNewsListFragment.newInstance(authorId, 1));

        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, mFragments);
        mViewpager.setAdapter(adapter);
        mViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mViewpager.addOnPageChangeListener(this);
        mTablayout.setupWithViewPager(mViewpager);
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < mTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        /*viewpager切换默认第一个*/
        mViewpager.setCurrentItem(0);
        nowFragment = 0;
    }

    @Override
    public void initData() {
        initViewLeftDrawable(mTitleBackTv, R.mipmap.module_base_app_back, 23, 23);
        initViewLeftDrawable(mGuanzhuBtn, R.mipmap.add_white, 11, 11);
        mPresenter.getAuthorInfo(authorId, NewsContract.GET_AUTHOR_INFO);
        if (authorId == MyApp.getUid()) {
            mGuanzhuBtn.setText("编辑资料");
            mGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
            mGuanzhuLayout.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
            mSixinBtn.setVisibility(View.GONE);
            mGuanzhuBtn.setCompoundDrawables(null, null, null, null);//清除
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            default:
                break;
            case NewsContract.GET_AUTHOR_INFO:
                NewsPersonalHomePageInfo newsPersonalHomePageInfo = (NewsPersonalHomePageInfo) o;
                authorInfo = newsPersonalHomePageInfo.getData();
                ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), authorInfo.getHeadPortrait(),
                        R.mipmap.my_hint_head, R.mipmap.my_hint_head, mAuthorImage);
                mTitleNameTv.setText(authorInfo.getNickName());
                mNewsCountTv.setText(String.valueOf(authorInfo.getArticleCount()));
                mGuanzhuCountTv.setText(String.valueOf(authorInfo.getFollowCount()));
                mFansCountTv.setText(String.valueOf(authorInfo.getFansCount()));
                mZanCountTv.setText(String.valueOf(authorInfo.getLikeCount()));

                if (authorId != MyApp.getUid()) {
                    if (authorInfo.getIsFollow() > 0) {
                        mGuanzhuBtn.setText("已关注");
                        mGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                        mGuanzhuLayout.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
                        mGuanzhuBtn.setCompoundDrawables(null, null, null, null);//清除
                    } else {
                        mGuanzhuBtn.setText("关注");
                        mGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                        mGuanzhuLayout.setBackgroundResource(R.drawable.news_btn_bg_blue);
                        initViewLeftDrawable(mGuanzhuBtn, R.mipmap.add_white, 11, 11);
                    }
                }
                break;
            case NewsContract.DELETE_FOLLOW:
                authorInfo.setIsFollow(0);
                mGuanzhuBtn.setText("关注");
                mGuanzhuBtn.setTextColor(getResources().getColor(R.color.white));
                mGuanzhuLayout.setBackgroundResource(R.drawable.news_btn_bg_blue);
                initViewLeftDrawable(mGuanzhuBtn, R.mipmap.add_white, 11, 11);
                break;
            case NewsContract.ADD_FOLLOW://添加成功
                authorInfo.setIsFollow(1);
                mGuanzhuBtn.setText("已关注");
                mGuanzhuBtn.setTextColor(getResources().getColor(R.color.text_gray));
                mGuanzhuLayout.setBackgroundResource(R.drawable.news_btn_bg_circle_line);
                mGuanzhuBtn.setCompoundDrawables(null, null, null, null);//清除
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.title_back_tv:
                onBackPressed();
                break;
            case R.id.title_search_iv:
                startActivity(new Intent(mContext, NewsSearchActivity.class));
                break;
            case R.id.title_share_iv:
                //分享
                initShare();
                break;
            case R.id.guanzhu_btn:
                //关注
                if (authorId == MyApp.getUid()) {
                    startActivity(new Intent(mContext, MyInformationActivity.class));
                } else {
                    if (authorInfo.getIsFollow() > 0) {
                        //删除
                        mPresenter.addFollowOrDelete(2, authorId, NewsContract.DELETE_FOLLOW);
                    } else {
                        //添加
                        mPresenter.addFollowOrDelete(1, authorId, NewsContract.ADD_FOLLOW);
                    }
                }
                break;
            case R.id.sixin_btn:
                //私信
                ModuleIm_Init.chat(mContext, authorInfo.getAccount(), authorInfo.getNickName());
                break;
            case R.id.guanzhu_tag:
                //关注列表
                startActivity(new Intent(mContext, FansListActivity.class).putExtra(AppUtils.ID_KEY, authorId).putExtra("type", 2));
                break;
            case R.id.fans_tag:
                //粉丝列表
                startActivity(new Intent(mContext, FansListActivity.class).putExtra(AppUtils.ID_KEY, authorId).putExtra("type", 1));
                break;
            case R.id.zan_tag:
                //点赞弹窗
                new NewsLikeCountDialog(mContext).builder()
                        .setData(authorInfo.getNickName(), authorInfo.getLikeCount())
                        .setOkButton("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (authorId == MyApp.getUid() && authorInfo != null) {
            authorInfo.setHeadPortrait(MyApp.getUserHeadImg());
            ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), authorInfo.getHeadPortrait(),
                    R.mipmap.my_hint_head, R.mipmap.my_hint_head, mAuthorImage);
        }
    }

    /**
     * 分享
     */
    private void initShare() {
        if (authorInfo != null) {
            ToolShare.shareForMob(mContext,
                    authorInfo.getNickName() + "的个人主页",
                    authorInfo.getShareLink(),
                    "点击查看更多",
                    authorInfo.getHeadPortrait(),
                    callback);
        }
    }

    /**
     * 分享外部回调
     */
    PlatformActionListener callback = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //  分享成功后的操作或者提示
            ToastUtils.success(mContext, "分享成功！");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            //  失败，打印throwable为错误码
            ToastUtils.warning(mContext, "分享失败！");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            //  分享取消操作
            ToastUtils.warning(mContext, "分享已取消！");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        infoDetailPresent.setCallBack(null);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        nowFragment = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

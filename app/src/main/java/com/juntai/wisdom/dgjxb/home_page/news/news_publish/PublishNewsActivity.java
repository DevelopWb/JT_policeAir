package com.juntai.wisdom.dgjxb.home_page.news.news_publish;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.MainPagerAdapter;
import com.juntai.wisdom.dgjxb.base.customview.CustomViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PublishNewsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private TabLayout mTablayout;
    private CustomViewPager mViewpager;
    private int type = 0;//0视频，1图文

    MainPagerAdapter adapter;
    private String[] title = new String[]{"视频","图文"};
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;

    @Override
    public int getLayoutView() {
        return R.layout.activity_publish_news;
    }

    @Override
    public void initView() {
        setTitleName("发布资讯");
        type = getIntent().getIntExtra("type",0);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (CustomViewPager) findViewById(R.id.viewpager);

        mFragments.append(0, new PublishVideoNewsFragment());
        mFragments.append(1, new PublishImageNewsFragment());

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
        mViewpager.setCurrentItem(type);
        nowFragment = type;
    }


    @Override
    public void initData() {

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

    @Override
    public void onBackPressed() {
        if (nowFragment == 1){
            EventManager.sendStringMsg(ActionConfig.PUBLISH_NEWS_SAVE_DRAFTS);
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (test.equals(ActionConfig.FINISH_AFTER_PUBISH)){
            finish();
        }
    }
}

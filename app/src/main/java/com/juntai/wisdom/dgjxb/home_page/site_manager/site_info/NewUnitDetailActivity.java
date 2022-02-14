package com.juntai.wisdom.dgjxb.home_page.site_manager.site_info;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.MainPagerAdapter;
import com.juntai.wisdom.dgjxb.base.customview.CustomViewPager;
import com.juntai.wisdom.dgjxb.utils.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jzvd.Jzvd;

/**
 * @description 场所详情
 * @aouther ZhangZhenlong
 * @date 2020-7-4
 */
public class NewUnitDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private TabLayout mCollectTablayout;
    private CustomViewPager mCollectViewpager;

    MainPagerAdapter adapter;
    private String[] title = new String[]{"单位详情", "工作人员", "检查记录"};
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;
    private int unitId;
    private String unitName;
    public static String UNIT_NAME = "unit_name";

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        setTitleName("场所详情");
        unitId = getIntent().getIntExtra(AppUtils.ID_KEY, 0);
        unitName = getIntent().getStringExtra(UNIT_NAME);
        mCollectTablayout = (TabLayout) findViewById(R.id.collect_tablayout);
        mCollectTablayout.setVisibility(View.VISIBLE);
        mCollectViewpager = (CustomViewPager) findViewById(R.id.collect_viewpager);
        mCollectViewpager.setScanScroll(false);

        mFragments.append(0,UnitDetailFragment.newInstance(0,unitId));
        mFragments.append(1,UnitListDataFragment.newInstance(1,unitId,unitName));
        mFragments.append(2,UnitListDataFragment.newInstance(2,unitId,unitName));

        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, mFragments);
        mCollectViewpager.setAdapter(adapter);
        mCollectViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mCollectViewpager.addOnPageChangeListener(this);
        //TabLayout
//        tabLayout.addTab(tabLayout.newTab().setText("index").setIcon(R.mipmap.point_focus));
        mCollectTablayout.setupWithViewPager(mCollectViewpager);
//        tabLayout.setOnTabSelectedListener();
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < mCollectTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mCollectTablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        /*viewpager切换默认第一个*/
        mCollectViewpager.setCurrentItem(0);
        nowFragment = 0;
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.REFRASH_SITE_EMPLOYEE_LIST.equals(test)){
            //刷新
            if (nowFragment == 1 || nowFragment == 2){
                ((UnitListDataFragment)mFragments.get(nowFragment)).lazyLoad();
            }
        }else if (AppUtils.FINISH_UNIT_INFO_ACTIVITY.equals(test)){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}

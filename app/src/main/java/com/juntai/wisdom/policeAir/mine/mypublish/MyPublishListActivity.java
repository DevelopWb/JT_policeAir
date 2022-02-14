package com.juntai.wisdom.policeAir.mine.mypublish;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.MainPagerAdapter;
import com.juntai.wisdom.policeAir.base.customview.CustomViewPager;

/**
 * 我的发布列表
 * @aouther ZhangZhenlong
 * @date 2020-3-12
 */
public class MyPublishListActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private TabLayout mCollectTablayout;
    private CustomViewPager mCollectViewpager;

    MainPagerAdapter adapter;
    private String[] title = new String[]{"警情","巡检","资讯","场所"};
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        setTitleName("我的发布");
        mCollectTablayout = (TabLayout) findViewById(R.id.collect_tablayout);
        mCollectTablayout.setVisibility(View.VISIBLE);
        mCollectViewpager = (CustomViewPager) findViewById(R.id.collect_viewpager);

        getTitleRightTv().setText("编辑");
        getTitleRightTv().setOnClickListener(v -> {
            if (getTitleRightTv().getText().equals("编辑")){
                getTitleRightTv().setText("完成");
                ((MyPublishListFragment)mFragments.get(nowFragment)).setEdit(true);
            }else {
                getTitleRightTv().setText("编辑");
                ((MyPublishListFragment)mFragments.get(nowFragment)).setEdit(false);
            }
        });

        mFragments.append(0,MyPublishListFragment.newInstance(1));
        mFragments.append(1,MyPublishListFragment.newInstance(7));
        mFragments.append(2,MyPublishListFragment.newInstance(8));
        mFragments.append(3,MyPublishListFragment.newInstance(11));

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

    /**
     * 编辑状态
     */
    public void setEdit(){
        getTitleRightTv().setText("编辑");
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {

        if (((MyPublishListFragment)adapter.getItem(nowFragment)).getEdit()){
            //切换时取消编辑
            getTitleRightTv().setText("编辑");
            ((MyPublishListFragment)adapter.getItem(nowFragment)).setEdit(false);
        }
        nowFragment = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}

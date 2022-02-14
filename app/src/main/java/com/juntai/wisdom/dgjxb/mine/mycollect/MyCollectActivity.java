package com.juntai.wisdom.dgjxb.mine.mycollect;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.MainPagerAdapter;
import com.juntai.wisdom.dgjxb.base.customview.CustomViewPager;
/**
 * 我的收藏、分享
 * @aouther ZhangZhenlong
 * @date 2020-3-12
 */
public class MyCollectActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private TabLayout mCollectTablayout;
    private CustomViewPager mCollectViewpager;
    private int function = 1;//1收藏，2分享

    MainPagerAdapter adapter;
    private String[] title;
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        mCollectTablayout = (TabLayout) findViewById(R.id.collect_tablayout);
        mCollectViewpager = (CustomViewPager) findViewById(R.id.collect_viewpager);

        function = getIntent().getIntExtra("function",0);
        if (function == 0){
            finish();
        }
        if (function == 1){
            setTitleName("我的收藏");
        }else {
            setTitleName("我的分享");
        }
        title = new String[]{"资讯", "监控"};
        mFragments.append(0,MyCollectFragment.newInstance(8,function));
        mFragments.append(1,MyCollectFragment.newInstance(0,function));
        if (title.length>1){
            mCollectTablayout.setVisibility(View.VISIBLE);
        }else {
            mCollectTablayout.setVisibility(View.GONE);
        }
        getTitleRightTv().setText("编辑");
        getTitleRightTv().setOnClickListener(v -> {
            if (getTitleRightTv().getText().equals("编辑")){
                getTitleRightTv().setText("完成");
                ((MyCollectFragment)mFragments.get(nowFragment)).setEdit(true);
            }else {
                getTitleRightTv().setText("编辑");
                ((MyCollectFragment)mFragments.get(nowFragment)).setEdit(false);
            }
        });

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
    protected void onRestart() {
        super.onRestart();
        ((MyCollectFragment)mFragments.get(nowFragment)).lazyLoad();
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
        if (((MyCollectFragment)adapter.getItem(nowFragment)).getEdit()){
            getTitleRightTv().setText("编辑");
            ((MyCollectFragment)adapter.getItem(nowFragment)).setEdit(false);
        }
        nowFragment = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

package com.juntai.wisdom.policeAir.mine.message;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.MainPagerAdapter;
import com.juntai.wisdom.policeAir.base.customview.CustomViewPager;
import com.juntai.wisdom.im.ModuleIm_Init;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 我的消息
 * @aouther ZhangZhenlong
 * @date 2020-3-25
 */
public class MyMessageActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private TabLayout mCollectTablayout;
    private CustomViewPager mCollectViewpager;

    MainPagerAdapter adapter;
//    private String[] title = new String[]{"通知消息","互动消息","物流售后","评论点赞"};
    private String[] title = new String[]{"通知消息","互动消息","评论点赞"};
    SparseArray<Fragment> mFragments = new SparseArray<>();
    int nowFragment;

    @Override
    public int getLayoutView() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        setTitleName("我的消息");
        mCollectTablayout = findViewById(R.id.collect_tablayout);
        mCollectTablayout.setVisibility(View.VISIBLE);
        mCollectViewpager = findViewById(R.id.collect_viewpager);

        getTitleRightTv().setText("全部已读");
        getTitleRightTv().setOnClickListener(v -> {
            if (nowFragment == 1){
                ModuleIm_Init.readAllMessage();
//                MyApp.getUser().getData().setImCount(0);
//                setUnReadView(mCollectTablayout.getTabAt(1),0);
            }else {
                ((InformMsgFragment)mFragments.get(nowFragment)).allRead();
            }
        });
        initFragmentOfMessage();
    }

    @Override
    public void initData() {
        setUnRead();
    }

    public void initFragmentOfMessage(){
        mFragments.append(0,InformMsgFragment.newInstance(1));
        mFragments.append(1,new MyMsgFragment());
//        mFragments.append(2,new AfterSaleMsgFragment());
        mFragments.append(2,InformMsgFragment.newInstance(4));

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
    protected void onResume() {
        super.onResume();
//        setUnRead();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置消息未读标记
     */
    public void setUnRead(){
        for (int i = 0; i < mCollectTablayout.getTabCount(); i++){
            TabLayout.Tab tab = mCollectTablayout.getTabAt(i);
            switch (i){
                case 0:
                    setUnReadView(tab, MyApp.getUnReadCountBean().getNotificationMessage());
                    break;
                case 1:
                    setUnReadView(tab, MyApp.getUnReadCountBean().getImCount());
                    break;
                case 2:
                    setUnReadView(tab, MyApp.getUnReadCountBean().getCommentMessage());
                    break;
            }
        }
    }

    private void setUnReadView(TabLayout.Tab tab, int count){
        if (count>0){
            tab.getCustomView().findViewById(R.id.read_tag).setVisibility(View.VISIBLE);
        }else {
            tab.getCustomView().findViewById(R.id.read_tag).setVisibility(View.GONE);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ActivityResult && nowFragment ==0) {
            ((InformMsgFragment)mFragments.get(nowFragment)).lazyLoad();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UN_READ_MESSAG_TAG.equals(test)){
            //刷新未读标记
            setUnRead();
            //更新角标
            ShortcutBadger.applyCount(mContext.getApplicationContext(), MyApp.getUnReadCountBean().getMessageCount() + MyApp.getUnReadCountBean().getImCount());
        }
    }
}

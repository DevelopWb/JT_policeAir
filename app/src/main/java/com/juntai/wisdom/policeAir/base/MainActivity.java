package com.juntai.wisdom.policeAir.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.Logger;
import com.juntai.wisdom.basecomponent.utils.SPTools;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.utils.UserInfoManager;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.base.customview.CustomViewPager;
import com.juntai.wisdom.policeAir.base.update.UpdateActivity;
import com.juntai.wisdom.policeAir.bean.history_track.LocationBean;
import com.juntai.wisdom.policeAir.bean.news.NewsDraftsBean;
import com.juntai.wisdom.policeAir.entrance.LoginActivity;
import com.juntai.wisdom.policeAir.home_page.MyMapFragment;
import com.juntai.wisdom.policeAir.mine.MyCenterFragment;
import com.juntai.wisdom.basecomponent.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.ObjectBox;
import com.juntai.wisdom.policeAir.utils.PermissionUtil;
import com.mob.MobSDK;
import com.videoaudiocall.net.AppHttpPathSocket;
import com.videoaudiocall.webSocket.MyWsManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.rong.pushperm.ResultCallback;
import io.rong.pushperm.RongPushPremissionsCheckHelper;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends BaseAppActivity<MainPagePresent> implements ViewPager.OnPageChangeListener,
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;
    private TabLayout mainTablayout;
    private String[] title = new String[]{"首页", "我的"};
    private int[] tabDrawables = new int[]{R.drawable.home_index, R.drawable.home_msg};
    private SparseArray<Fragment> mFragments = new SparseArray<>();
    //
    CGBroadcastReceiver broadcastReceiver = new CGBroadcastReceiver();
    PopupWindow popupWindow;
    final static Handler mHandler = new Handler();
    private List<LocationBean> locateData;


    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainViewpager = findViewById(R.id.main_viewpager);
        mainTablayout = findViewById(R.id.main_tablayout);
        mainLayout = findViewById(R.id.main_layout);
        mainViewpager.setScanScroll(false);
        mFragments.append(0, new MyMapFragment());//地图
//        mFragments.append(1, new EmptyFragment());//
//        mFragments.append(2, new EmptyScanFragment());//
//        mFragments.append(3, new NewsListFragment());//资讯
        mFragments.append(1, new MyCenterFragment());//我的
        //
        getToolbar().setVisibility(View.GONE);
        mBaseRootCol.setFitsSystemWindows(false);
        mainViewpager.setOffscreenPageLimit(2);
        initTab();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConfig.BROAD_LOGIN);
        intentFilter.addAction(ActionConfig.BROAD_CASE_DETAILS);
        registerReceiver(broadcastReceiver, intentFilter);

        if (UserInfoManager.isLogin()) {
            MyWsManager.getInstance()
                    .init(getApplicationContext())
                    .setWsUrl(AppHttpPathSocket.BASE_SOCKET + UserInfoManager.getUserId())
                    .startConnect();
        } else {
            MyWsManager.getInstance()
                    .init(getApplicationContext());
        }
    }

    @Override
    public void initData() {
        update(false);
        if (MyApp.isLogin()) {
            initForLogin();
        }
        /**分享隐私授权 true*/
        MobSDK.submitPolicyGrantResult(true, null);
        /**申请相关权限*/
        if (SPTools.getBoolean(mContext, AppUtils.SP_FLOAT_PERMISSION, true)) {
            setPushPermission();
            SPTools.saveBoolean(mContext, AppUtils.SP_FLOAT_PERMISSION, false);
        }
    }

    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, tabDrawables,
                mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(title.length);
        /*viewpager切换监听，包含滑动点击两种*/
        mainViewpager.addOnPageChangeListener(this);
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = mainTablayout.newTab();
            if (tab != null) {
                if (i == title.length - 1) {
                    tab.setCustomView(adapter.getTabView(i, true));
                } else {
                    tab.setCustomView(adapter.getTabView(i, false));
                }
                mainTablayout.addTab(tab);
            }
        }

        mainTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewpager.setCurrentItem(tab.getPosition(), false);
//                if (tab.getPosition() == 1) {
//                    //条件弹窗
//                    initPopTypePublish(mainTablayout);
//                } else if (tab.getPosition() == 2) {
//                    if (!MyApp.isLogin()) {
//                        MyApp.goLogin();
//                        return;
//                    }
//                    if (UserInfoManager.getAccountStatus() != 1) {
//                        //没有绑定手机号
//                        startActivity(new Intent(mContext, BindingPhoneActivity.class));
//                        return;
//                    }
//                    startActivityForResult(new Intent(mContext, QRScanActivity.class),
//                            AppUtils.QR_SCAN_FOR_XUANJIAN);
//                } else {
//                    mainViewpager.setCurrentItem(tab.getPosition(), false);
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                if (tab.getPosition() == 1) {
//                    //条件弹窗
//                    initPopTypePublish(mainTablayout);
//                } else if (tab.getPosition() == 2) {
//                    if (!MyApp.isLogin()) {
//                        MyApp.goLogin();
//                        return;
//                    }
//                    if (UserInfoManager.getAccountStatus() != 1) {
//                        //没有绑定手机号
//                        startActivity(new Intent(mContext, BindingPhoneActivity.class));
//                        return;
//                    }
//                    startActivityForResult(new Intent(mContext, QRScanActivity.class),
//                            AppUtils.QR_SCAN_FOR_XUANJIAN);
//                }
            }
        });

        //        mainTablayout.newTab();
        /*viewpager切换默认第一个*/
        mainViewpager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shadow_tv:
                if (popupWindow != null) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MainPageContract.UPLOAD_HISTORY:
                ObjectBox.get().boxFor(LocationBean.class).remove(locateData);
                break;
            default:
                break;
        }
    }

    AlertDialog alertDialog;
    int id22;
    String content;
    int type;//推送类型，1案件指派、3巡检退回

    /**
     * 登录被顶
     */
    public class CGBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActionConfig.BROAD_LOGIN.equals(intent.getAction())) {
                //退出IM登录
//                ModuleIm_Init.logout();
                //登录信息设置为空
                String error = intent.getStringExtra("error");
                ToastUtils.info(MyApp.app, error);
                MyApp.app.clearUserData();//清理数据
                mHandler.removeCallbacks(runnable);
                mHandler.removeCallbacksAndMessages(null);
                ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
                startActivity(new Intent(mContext, LoginActivity.class));
                //                finish();
            }
        }
    }



    @Override
    protected MainPagePresent createPresenter() {
        return new MainPagePresent();
    }

    @Override
    protected void onDestroy() {
        Log.e("EEEEEEEEEE", " = MainActivity  onDestroy");
        stopService(new Intent(MainActivity.this, LocateAndUpload.class));
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        mHandler.removeCallbacks(runnable);
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage("请选择退出方式")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApp.app.isFinish = true;
//                        ModuleIm_Init.logout();
                        finish();
                    }
                })
                .setNegativeButton("挂起", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //模拟home键,发送广播
                        //sendBroadcast(new Intent().setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
                        // .putExtra("reason","homekey"));
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * drawerlayout 开启
     *
     * @param open
     */
    public void drawerlayoutOpened(boolean open) {
        if (open) {
            mImmersionBar.reset().statusBarDarkFont(true).init();
        } else {
            mImmersionBar.reset().statusBarColor(R.color.white).statusBarDarkFont(true).init();
        }
    }

    /**
     * 申请悬浮窗权限
     */
    public void setSystemAlertPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.request_float_permission));
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PermissionUtil.gotoPermission(mContext);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 自启动权限申请
     */
    private void setPushPermission() {
        RongPushPremissionsCheckHelper.checkPermissionsAndShowDialog(this, new ResultCallback() {
            @Override
            public void onAreadlyOpened(String value) {
                LogUtil.d("权限已申请");
                setSystemAlertPermission();
            }

            @Override
            public boolean onBeforeShowDialog(String value) {
                return false;
            }

            @Override
            public void onGoToSetting(String value) {

            }

            @Override
            public void onFailed(String value, FailedType type) {
                ToastUtils.warning(mContext, getString(R.string.fail_float_permission));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(NewsDraftsBean newsDraftsBean) {
        if (newsDraftsBean != null && newsDraftsBean.getDraftsId() != null) {
            mPresenter.deleteNewsDrafts(newsDraftsBean.getDraftsId(), MainPageContract.DELETE_NEWS_DRAFTS);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.UN_READ_MESSAG_TAG.equals(test)) {
            //刷新未读标记
            adapter.setUnReadMsg(MyApp.getUnReadCountBean().getMessageCount() + MyApp.getUnReadCountBean().getImCount());
        } else if (ActionConfig.BROAD_LOGIN_AFTER.equals(test)) {
            initForLogin();
        } else if (ActionConfig.BROAD_LOGIN_OUT.equals(test)) {
            //退出登录
            mHandler.removeCallbacks(runnable);
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 登录后初始化，获取融云用户列表及开启轨迹上传任务
     */
    private void initForLogin() {
//        getIMUsers();
        /**登录IM*/
//        ModuleIm_Init.connectIM(MyApp.getUserRongYunToken());
        // TODO: 2022-02-14 以前的逻辑是实名认证通过之后才能上传位置信息  现在放开
//        if (MyApp.getUser().getData().getSettleStatus() == 2) {
        //主线程中调用：
        mHandler.postDelayed(runnable, 1000 * 1);//延时1秒
//        }
    }

    /**
     * 上传位置
     *
     * @param time
     */
    public void startUploadLocateData(long time) {
        if (runnable != null) {
            mHandler.postDelayed(runnable, time);

        }

    }

    /**
     * 查询本地数据并上传
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //do something
            locateData = ObjectBox.get().boxFor(LocationBean.class).getAll();
            Logger.e("historyDataSize", locateData.size() + "");
            // : 2022-02-17 这个地方有问题 如果数据过多  上传的时候就报异常  之前警小宝的逻辑是30以内上传  超过30 本地数据清零
            if (locateData == null||locateData.isEmpty()) {
                return;
            }
            if (locateData.size() > 0 && locateData.size() < 30) {
                LogUtil.d("本地有数据 上传位置数据");
                mPresenter.uploadHistory(new Gson().toJson(locateData), MainPageContract.UPLOAD_HISTORY);
                //每隔1分钟循环执行run方法
                startUploadLocateData(1000 * 60);
            } else {
// : 2022-02-19 超过30条  这时候一下上传所有的位置数据可能会失败  如果大于30条 就取出30条上传

                locateData= locateData.subList(0,30);
                mPresenter.uploadHistory(new Gson().toJson(locateData), MainPageContract.UPLOAD_HISTORY);
                //每隔5秒循环执行run方法
                startUploadLocateData(1000 * 5);
            }

        }
    };
}

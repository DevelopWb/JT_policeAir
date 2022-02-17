package com.juntai.wisdom.policeAir.base;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.Logger;
import com.juntai.wisdom.basecomponent.utils.NotificationTool;
import com.juntai.wisdom.basecomponent.utils.SPTools;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.policeAir.AppNetModule;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.base.update.UpdateActivity;
import com.juntai.wisdom.policeAir.bean.IMUsersBean;
import com.juntai.wisdom.policeAir.bean.history_track.LocationBean;
import com.juntai.wisdom.policeAir.bean.news.NewsDraftsBean;
import com.juntai.wisdom.policeAir.entrance.BindingPhoneActivity;
import com.juntai.wisdom.policeAir.entrance.LoginActivity;
import com.juntai.wisdom.policeAir.home_page.MyMapFragment;
import com.juntai.wisdom.policeAir.home_page.law_case.CaseInfoActivity;
import com.juntai.wisdom.policeAir.home_page.inspection.InspectionDetailActivity;
import com.juntai.wisdom.policeAir.home_page.news.news_publish.PublishNewsActivity;
import com.juntai.wisdom.policeAir.home_page.site_manager.site_add.AddNewSiteActivity;
import com.juntai.wisdom.policeAir.mine.MyCenterFragment;
import com.juntai.wisdom.policeAir.mine.task.ReportDetailActivity;
import com.juntai.wisdom.policeAir.home_page.inspection.PublishInspectionActivity;
import com.juntai.wisdom.policeAir.home_page.law_case.PublishCaseActivity;
import com.juntai.wisdom.policeAir.mine.task.PublishTReportActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.ObjectBox;
import com.juntai.wisdom.policeAir.utils.PermissionUtil;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.juntai.wisdom.policeAir.utils.UserInfoManager;
import com.juntai.wisdom.policeAir.utils.ViewUtil;
import com.juntai.wisdom.policeAir.base.customview.CustomViewPager;
import com.juntai.wisdom.im.ModuleIm_Init;
import com.juntai.wisdom.im.UserIM;
import com.mob.MobSDK;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.jzvd.Jzvd;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rong.pushperm.ResultCallback;
import io.rong.pushperm.RongPushPremissionsCheckHelper;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends UpdateActivity<MainPagePresent> implements ViewPager.OnPageChangeListener,
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;
    private TabLayout mainTablayout;
    private String[] title = new String[]{"首页",  "我的"};
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
        mainViewpager.setOffscreenPageLimit(5);
        initTab();
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConfig.BROAD_LOGIN);
        intentFilter.addAction(ActionConfig.BROAD_CASE_DETAILS);
        registerReceiver(broadcastReceiver, intentFilter);
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
                ModuleIm_Init.logout();
                //登录信息设置为空
                String error = intent.getStringExtra("error");
                ToastUtils.info(MyApp.app, error);
                MyApp.app.clearUserData();//清理数据
                mHandler.removeCallbacks(runnable);
                mHandler.removeCallbacksAndMessages(null);
                ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
                startActivity(new Intent(mContext, LoginActivity.class));
                //                finish();
            } else if (ActionConfig.BROAD_CASE_DETAILS.equals(intent.getAction())) {
                LogUtil.d("RongYun-消息监听", "MainActivity-----------");
                //案件指派
                id22 = intent.getIntExtra("id", 0);
                //推送类型：1案件指派，3巡检退回，7任务指派，8任务退回，9巡检完成推送（巡检记录审核通过）
                type = intent.getIntExtra("type", 0);
                content = intent.getStringExtra("content");
                if (BaseApplication.app.isRun) {
                    LogUtil.d("777777", "-----------前台");
                    //前台发广播
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    } else {
                        alertDialog =
                                new AlertDialog.Builder(MyApp.app.getNowActivity()).setPositiveButton("查看",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(setNotificationIntent(type));
                                            }
                                        }).setNegativeButton("取消", null).create();
                    }

                    alertDialog.setTitle(setNotificationTile(type));
                    if (StringTools.isStringValueOk(content)) {
                        alertDialog.setMessage(type == 1 ? "要求：" + content : "说明：" + content);
                    }
                    alertDialog.show();
                } else {
                    LogUtil.d("888888", "-----------后台===发送通知");
                    //后台发通知
                    Notification notification = NotificationTool.sendNotifMessage(BaseApplication.app, new Random().nextInt(10000),
                            setNotificationTile(type), content, R.mipmap.app_icon,
                            false, setNotificationIntent(type));

                }
            }
        }
    }

    /**
     * 设置标题
     *
     * @param titleType
     * @return
     */
    public String setNotificationTile(int titleType) {
        String title = "通知消息";
        switch (titleType) {
            case 1:
                title = "案件指派";
                break;
            case 3:
                title = "巡检退回";
                break;
            case 7:
                title = "任务指派";
                break;
            case 8:
                title = "任务退回";
                break;
            case 9:
                title = "巡检完成";
                break;
        }
        return title;
    }

    /**
     * 设置跳转Intent
     *
     * @param titleType
     * @return
     */
    public Intent setNotificationIntent(int titleType) {
        Intent intent;
        switch (titleType) {
            case 1:
                intent = new Intent(mContext, CaseInfoActivity.class).putExtra("id", id22);
                break;
            case 3:
                intent = new Intent(mContext, InspectionDetailActivity.class).putExtra("id", id22);
                break;
            case 7:
                intent = new Intent(mContext, PublishTReportActivity.class).putExtra("id", id22);
                break;
            case 8:
                intent = new Intent(mContext, ReportDetailActivity.class).putExtra("reportId",
                        id22);
                break;
            case 9:
                intent = new Intent(mContext, InspectionDetailActivity.class).putExtra("id", id22);
                break;
            default:
                intent = new Intent(mContext, MainActivity.class);
                break;
        }
        return intent;
    }

    /**
     * 发布的popupwindow
     *
     * @param view
     */
    public void initPopTypePublish(View view) {
        if (!MyApp.isLogin()) {
            MyApp.goLogin();
            return;
        }
        View viewPop = LayoutInflater.from(mContext).inflate(R.layout.publish_menu_layout, null);
        //背景颜色
        view.setBackgroundColor(Color.WHITE);
        TextView shadowTv = viewPop.findViewById(R.id.shadow_tv);
        shadowTv.setOnClickListener(this);
        popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewUtil.getScreenHeight(this) - mainTablayout.getLayoutParams().height - MyApp.statusBarH, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mImmersionBar.statusBarColor(R.color.white).statusBarDarkFont(true).init();
            }
        });
        //显示（自定义位置）
        popupWindow.showAtLocation(mainTablayout, Gravity.TOP, 0, 0);
        if (popupWindow.isShowing()) {
            mImmersionBar.statusBarColor(R.color.gray_light).statusBarDarkFont(true).init();
        }
        viewPop.findViewById(R.id.anjian_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //没有绑定手机号
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishCaseActivity.class));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.zixun_btn).setOnClickListener(v -> {
            if (UserInfoManager.getAccountStatus() != 1) {
                //没有绑定手机号
                startActivity(new Intent(mContext, BindingPhoneActivity.class));
                return;
            }
            startActivity(new Intent(this, PublishNewsActivity.class));
            popupWindow.dismiss();
        });
        viewPop.findViewById(R.id.site_iv).setOnClickListener(v -> {
            if (MyApp.isCompleteUserInfo()) {
                startActivity(new Intent(this, AddNewSiteActivity.class));
            }
            popupWindow.dismiss();
        });
    }

    /**
     * 获取im - - users
     */
    public void getIMUsers() {
        AppNetModule.createrRetrofit()
                .getIMUsers(MyApp.getUserToken(), MyApp.getAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<IMUsersBean>(null) {
                    @Override
                    public void onSuccess(IMUsersBean o) {
                        ArrayList<UserIM> arrayList = new ArrayList<>();
                        for (IMUsersBean.DataBean bean : o.getData()) {
                            arrayList.add(new UserIM(bean.getAccount(), bean.getRealName(), bean.getHeadPortrait()));
                        }
                        ModuleIm_Init.setUsers(arrayList);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtils.error(MyApp.app, msg);
                    }
                });
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
        if (Jzvd.backPress()) {
            return;
        }
        new AlertDialog.Builder(mContext)
                .setMessage("请选择退出方式")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApp.app.isFinish = true;
                        ModuleIm_Init.logout();
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
        Jzvd.releaseAllVideos();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AppUtils.QR_SCAN_NOMAL && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Intent intent = new Intent(this, PublishInspectionActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }

        } else if (requestCode == AppUtils.QR_SCAN_FOR_XUANJIAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("result");
                Intent intent = new Intent(this, PublishInspectionActivity.class);
                intent.putExtra("result", result);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        ModuleIm_Init.connectIM(MyApp.getUserRongYunToken());
        // TODO: 2022-02-14 以前的逻辑是实名认证通过之后才能上传位置信息  现在放开
//        if (MyApp.getUser().getData().getSettleStatus() == 2) {
        //主线程中调用：
        mHandler.postDelayed(runnable, 1000 * 1);//延时1秒
//        }
    }

    /**
     * 上传位置
     * @param time
     */
    public void  startUploadLocateData(long  time){
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
            // TODO: 2022-02-17 这个地方有问题 如果数据过多  上传的时候就报异常  之前警小宝的逻辑是30以内上传  超过30 本地数据清零
            if (locateData.size() > 0) {
                LogUtil.d("本地有数据 上传位置数据");
                mPresenter.uploadHistory(new Gson().toJson(locateData), MainPageContract.UPLOAD_HISTORY);
                //每隔1分钟循环执行run方法
                startUploadLocateData(1000 * 60);
            }

        }
    };
}

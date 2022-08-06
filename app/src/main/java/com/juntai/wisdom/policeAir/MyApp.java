package com.juntai.wisdom.policeAir;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.bean.MapMenuButton;
import com.juntai.wisdom.policeAir.bean.UserBean;
import com.juntai.wisdom.policeAir.bean.message.UnReadCountBean;
import com.juntai.wisdom.policeAir.entrance.LoginActivity;
import com.juntai.wisdom.policeAir.entrance.complete_info.CompleteInfoActivity;
import com.juntai.wisdom.policeAir.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.ObjectBox;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.orhanobut.hawk.Hawk;

/**
 * @aouther Ma
 * @date 2019/3/12
 */
public class MyApp extends BaseApplication {
    public static MyApp app;
    public static int CHECK_UPDATE_TYPE = 1;//类型id（1：警小宝）（2：巡小管）（3：邻小帮）
    public boolean isFinish = false;
    private String BUGLY_APPID = "77648a6776";//bugly appid警小宝
    public LatLng myLocation;
    public BDLocation bdLocation;
    public static long lastClickTime;//上次点击按钮时间
    public static int timeLimit = 1000;

    public static int BASE_REQUESR = 10086;
    public static int BASE_RESULT = 10087;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Hawk.init(this).build();
        //百度地图初始化
        SDKInitializer.initialize(this);
        //        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //创建压缩图片存放目录
        FileCacheUtils.creatFile(FileCacheUtils.getAppImagePath());
        ObjectBox.init(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)


    public static UserBean getUser() {
        return Hawk.get(AppUtils.SP_KEY_USER);
    }

    public static void setUser(UserBean userBean) {
        Hawk.put(AppUtils.SP_KEY_USER, userBean);
    }

    /**
     * 获取usertoken
     *
     * @return
     */
    public static String getUserToken() {
        return Hawk.get(AppUtils.SP_KEY_TOKEN);
    }


    public static boolean isLogin() {
        if (getUser() == null) {
            return false;
        } else {
            return true;
        }
    }

        /**
         * 跳转登录
         */
        public static void goLogin() {
            app.getNowActivity().startActivity(new Intent(app, LoginActivity.class));
        }

    /**
     * 获取账号
     *
     * @return
     */
    public static String getAccount() {
        UserBean userBean = getUser();
        return userBean== null ? "" : userBean.getData().getAccount();
    }

    /**
     * 获取id
     *
     * @return
     */
    public static int getUid() {
        return getUser() == null ? -1 : getUser().getData().getUserId();
    }

    /**
     * 获取头像
     *
     * @return
     */
    public static String getUserHeadImg() {
        return getUser() == null ? "" : getUser().getData().getImg();
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public static UnReadCountBean.DataBean getUnReadCountBean() {
        if (isLogin()){
            return Hawk.get(AppUtils.SP_KEY_UNREAD_COUNT) == null? new UnReadCountBean.DataBean() : Hawk.get(AppUtils.SP_KEY_UNREAD_COUNT);
        }else {
            return new UnReadCountBean.DataBean();
        }
    }

    /**
     * 缓存未读消息数
     *
     * @param unReadCountBean
     */
    public static void setUnReadCountBean(UnReadCountBean.DataBean unReadCountBean) {
        Hawk.put(AppUtils.SP_KEY_UNREAD_COUNT, unReadCountBean);
    }

    /**
     * 获取本地地图菜单
     *
     * @return
     */
    public static MapMenuButton getMapMenuButton() {
        return Hawk.get(AppUtils.SP_KEY_MAP_MENU) == null? new MapMenuButton() : Hawk.get(AppUtils.SP_KEY_MAP_MENU);
    }

    /**
     * 缓存线上地图菜单
     *
     * @param mapMenuButton
     */
    public static void setMapMenuButton(MapMenuButton mapMenuButton) {
        Hawk.put(AppUtils.SP_KEY_MAP_MENU, mapMenuButton);
    }

    /**
     * 获取当前定位
     *
     * @return
     */
    public LatLng getMyLocation() {
        if (myLocation == null) {
            myLocation = new LatLng(0, 0);
        }
        return myLocation;
    }

    public void setMyLocation(LatLng myLocation) {
        this.myLocation = myLocation;
    }


    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    @Override
    public void appBackground(boolean isBackground, Activity activity) {
        if (isBackground && !isFinish) {
            //            NitoficationTool.sendNotif(activity,
            //                    11,
            //                    "挂起",
            //                    BaseAppUtils.getAppName() + "服务正在运行",
            //                    R.mipmap.app_icon,
            //                    true,
            //                    new Intent(activity, MainActivity.class));
        } else {
            //变为前台
            //            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //            manager.cancelAll();
        }
    }

    //    @Override
    //    public String getTinkerId() {
    //        return BUGLY_APPID;
    //    }


    /**
     * 上传成功后删除压缩图片和视频缓存
     */
    public static void delCache() {
        FileCacheUtils.clearImage();
        FileCacheUtils.clearVideo();
    }


    /**
     * 退出登录清理缓存配置
     */
    public void clearUserData() {
        Hawk.delete(AppUtils.SP_KEY_USER);
        Hawk.delete(AppUtils.SP_KEY_TOKEN);
        Hawk.delete(AppUtils.SP_RONGYUN_TOKEN);
        //        Hawk.delete(AppUtils.SP_NEWS_SAVE_DRAFTS);
        Hawk.delete(AppUtils.SP_KEY_UNREAD_COUNT);
        //        clearActivitys();
    }


    /**
     * 防止暴力点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < timeLimit) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * 是否已完善用户信息
     * settleStatus;//信息审核状态（0未提交；1提交审核中；2审核通过；3审核失败）
     *
     * @return
     */
    public static boolean isCompleteUserInfo() {
        if (!MyApp.isLogin()){
            MyApp.goLogin();
            return false;
        }
        Intent intent = new Intent(app, CompleteInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (getUser().getData().getSettleStatus() == 0) {
            app.startActivity(intent);
            return false;
        } else if (getUser().getData().getSettleStatus() == 3) {
            ToastUtils.warning(app, "信息审核不通过，请重新提交！");
            app.startActivity(intent);
            return false;
        } else if (getUser().getData().getSettleStatus() == 1) {
            ToastUtils.warning(app, "信息审核中！");
            return false;
        } else if (getUser().getData().getSettleStatus() == 2) {
            return true;
        }
        return false;
    }
}

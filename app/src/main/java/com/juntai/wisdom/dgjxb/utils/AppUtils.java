package com.juntai.wisdom.dgjxb.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.dgjxb.MyApp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @aouther Ma
 * @date 2019/3/13
 */
public class AppUtils {

    public final static String SP_KEY_USER = BaseAppUtils.getAppName() + "login";
    public final static String SP_KEY_TOKEN = BaseAppUtils.getAppName() + "token";
    public final static String SP_RONGYUN_TOKEN = BaseAppUtils.getAppName() + "rongYunToken";
    public final static String SP_FLOAT_PERMISSION = BaseAppUtils.getAppName() + "floatPermission";
    //资讯草稿
    public final static String SP_NEWS_SAVE_DRAFTS = BaseAppUtils.getAppName() +"save_drafts";
    //未读消息
    public final static String SP_KEY_UNREAD_COUNT = BaseAppUtils.getAppName() + "unread_count";
    //首页地图菜单
    public final static String SP_KEY_MAP_MENU = BaseAppUtils.getAppName() + "map_menu";

    public final static int QR_SCAN_NOMAL = 1003;
    public final static int QR_SCAN_FOR_XUANJIAN = 1004;
    public final static int REQUEST_SYSTEM_DIALOG_PERMISSION = 1005;
    public final static int PUBLISH_INTERVIEW_BACK = 1006;
    public final static int PUBLISH_EMPLOYEE_BACK = 1007;

    //修改成功后关闭详情界面
    public final static String FINISH_UNIT_INFO_ACTIVITY = "finish_unit_info_activity";
    public final static String ID_KEY = "id";

    public void alertSystem(Context context){
        if(Build.VERSION.SDK_INT >=23) {
            if(!Settings.canDrawOverlays(context)) {
                //有悬浮窗权限开启服务绑定 绑定权限
                try{
                    Intent intent3 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + BaseAppUtils.getPackageName(context)));
                    context.startActivity(intent3);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 跳转支付宝
     * @param context
     * @param packageName 应用报名
     */
    public static void checkIsInstall(Context context, String packageName) {

        if ( !isAvilible( packageName , context) ){
            //没有安装Apk
            Toast.makeText(context, "未安装支付宝", Toast.LENGTH_SHORT).show();
        }else {
            //已经安装了Apk
            Intent intent = new Intent();
            intent.setData(Uri.parse("alipays://platformapi/startapp?appId=20000193"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }


    }

    /**
     * 检查是否安装了某应用
     *
     * @param packageName 包名
     * @return
     */
    public static boolean isAvilible(String packageName, Context mContext) {
        final PackageManager packageManager = mContext.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * [获取应用程序包名信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用名称
     * @return
     */
    public static synchronized String getAppName() {
        try {
            PackageManager packageManager = MyApp.app.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    MyApp.app.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return MyApp.app.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取应用图标 bitmap
     * @param context
     */
    public static synchronized Bitmap getAppIconBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    /**
     * 悬浮窗权限检查，大部分有效
     * @param context
     * @return
     */
    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

}
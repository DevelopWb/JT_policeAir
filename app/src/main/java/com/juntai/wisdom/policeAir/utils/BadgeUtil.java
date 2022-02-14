package com.juntai.wisdom.policeAir.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.RomUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.MainActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: tobato
 * @Description: 作用描述   角标配置类
 * @CreateDate: 2020/5/25 9:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/25 9:41
 */
public class BadgeUtil {


    /**
     * 配置角标
     *
     * @param num
     */
    public static void setBadgeNum(Context context, int num) {
        String name = RomUtil.getName();
        switch (name) {
            case RomUtil.ROM_MIUI:
                //小米
                setXiaoMiBadge(context, num);
                break;
            case RomUtil.ROM_EMUI:
                //华为
                setHuaWeiBadgeNum(context, num);
                break;
            default:
                break;
        }
    }

    //    /**
    //     * 小米角标
    //     * @param context
    //     * @param number
    //     */
    private static void setXiaoMiBadge(Context context, int count) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 8.0之后添加角标需要NotificationChannel
            NotificationChannel channel = new NotificationChannel("badge", "badge",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context, "badge")
                .setContentTitle(AppUtils.getAppName()).setContentText("您有" + count + "条未读消息")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.app_jing_icon))
                .setSmallIcon(R.mipmap.app_jing_icon)
                .setAutoCancel(true).setContentIntent(pendingIntent)
                .setChannelId("badge").setNumber(count)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).build();
        // 小米
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount",
                    int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationManager.notify(0, notification);
        return;
    }

    /**
     * 华为角标
     */
    private static void setHuaWeiBadgeNum(Context context, int num) {
        try {
            Bundle bunlde = new Bundle();
            String appName = BaseAppUtils.getPackageName(context);
            bunlde.putString("package", appName); // com.test.badge is your package name
            String className = appName + ".entrance.StartActivity";
            bunlde.putString("class", className); // com.test. badge.MainActivity is your apk
            // main activity
            bunlde.putInt("badgenumber", num);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher" +
                    ".settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
        }
    }
}

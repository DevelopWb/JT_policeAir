package com.juntai.wisdom.bdmap.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.LogUtil;

import java.util.List;

/**
 * 导航工具类
 * Created by Ma
 * on 2019/4/11
 */
public class NagivationUtils {
    //1.百度地图包名
    public static final String BAIDUMAP_PACKAGENAME = "com.baidu.BaiduMap";
    //2.高德地图包名
    public static final String AUTONAVI_PACKAGENAME = "com.autonavi.minimap";
    //3.腾讯地图包名
    public static final String QQMAP_PACKAGENAME = "com.tencent.map";

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(String packageName) {
        PackageManager manager = BaseApplication.app.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * 跳转百度地图导航
     * @param bd
     * @param address
     */
    public static void toBaiduMap(Context context,LatLng bd,String address){
        try {
            Uri uri = Uri.parse("baidumap://map/geocoder?" +
                    "location=" + bd.latitude + "," + bd.longitude +
                    "&name=" + address + //终点的显示名称
                    "&coord_type=db0911");//坐标 （百度同样支持他自己的db0911的坐标，但是高德和腾讯不支持）
            Intent intent = new Intent();
            intent.setPackage(BAIDUMAP_PACKAGENAME);
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e("跳转第三方Map导航" + e.getMessage());
        }
    }

    /**
     * 高德地图
     * @param bd
     * @param address
     */
    public static void toGaoDe(Context context,LatLng bd, String address){
        //转换坐标
        LatLng latLng = BD2GCJ(bd);
        try {
            Uri uri = Uri.parse("androidamap://route?sourceApplication={"+ BaseAppUtils.getAppName() +"}" +
                    "&dlat=" + latLng.latitude//终点的纬度
                    + "&dlon=" + latLng.longitude//终点的经度
                    + "&dname=" + address////终点的显示名称
                    + "&dev=0&m=0&t=0");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addCategory("android.intent.category.DEFAULT");
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e("跳转第三方Map导航" + e.getMessage());
        }
    }
    /**
     * 腾讯地图
     * @param bd
     * @param address
     */
    public static void toTengXun(Context context,LatLng bd,String address){
        LatLng latLng = BD2GCJ(bd);
        try {
            Uri uri = Uri.parse("qqmap://map/routeplan?type=drive" +
                    "&to=" + address//终点的显示名称 必要参数
                    + "&tocoord=" + latLng.latitude + "," + latLng.longitude//终点的经纬度
                    + "&referer={你的应用名称}");
            Intent intent = new Intent();
            intent.setData(uri);
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e("跳转第三方Map导航" + e.getMessage());
        }
    }

    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }
}

package com.juntai.wisdom.bdmap.act;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.juntai.wisdom.basecomponent.app.BaseApplication;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.bdmap.R;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.bdmap.utils.NagivationUtils;

import java.lang.reflect.Field;

/**
 * 外部导航封装
 * Created by Ma
 * on 2019/4/17
 */
public class NavigationDialog extends DialogFragment implements View.OnClickListener {
    //
    private Double lat = 0.0, lng = 0.0;
    private String address;
    private TextView navigationBd,navigationGd,navigationTx,nagivationClose;
    private boolean bdExist,gdExist,txExist;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_nagivation,null);
        Dialog dialog = new Dialog(getActivity(), R.style.CusDialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setTitle("标题");
        dialog.setCanceledOnTouchOutside(true);
        //Do something
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);

        navigationBd = view.findViewById(R.id.nagivation_bd);
        navigationGd = view.findViewById(R.id.nagivation_gd);
        navigationTx = view.findViewById(R.id.nagivation_tx);
        nagivationClose = view.findViewById(R.id.nagivation_close);
        navigationBd.setOnClickListener(this);
        navigationGd.setOnClickListener(this);
        navigationTx.setOnClickListener(this);
        nagivationClose.setOnClickListener(this);
        //
        bdExist = NagivationUtils.isInstalled(NagivationUtils.BAIDUMAP_PACKAGENAME);
        gdExist = NagivationUtils.isInstalled(NagivationUtils.AUTONAVI_PACKAGENAME);
        txExist = NagivationUtils.isInstalled(NagivationUtils.QQMAP_PACKAGENAME);
//        navigationBd.setVisibility(bdExist?View.VISIBLE:View.GONE);
//        navigationGd.setVisibility(gdExist?View.VISIBLE:View.GONE);
//        navigationTx.setVisibility(txExist?View.VISIBLE:View.GONE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.nagivation_bd) {
            //百度地图
            if (bdExist){
//                LatLng ll_location = getContext().getLatLng();
//                //终点是
//                LatLng ll = new LatLng(lat, lng);
//                NaviParaOption para = new NaviParaOption();
//                para.startName("从这里开始");
//                para.endPoint(ll);
//                para.endName("到这里结束");
//                try {
//                    BaiduMapNavigation.openBaiduMapNavi(para, getContext().getApplicationContext());
//                } catch (BaiduMapAppNotSupportNaviException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext().getApplicationContext(), "您尚未安装百度地图或地图版本过低", Toast.LENGTH_LONG).show();
//                }
                NagivationUtils.toBaiduMap(getActivity(),new LatLng(lat, lng),address);
            }else {
                //http://api.map.baidu.com/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving&region=西安&output=html&src=webapp.baidu.openAPIdemo
                ////调起百度PC或Web地图，展示"西安市"从（lat:34.264642646862,lng:108.95108518068 ）"我家"到"大雁塔"的驾车路线。
                String url = "http://api.map.baidu.com/direction?origin=latlng:"
                        + LocateAndUpload.lat+","
                        + LocateAndUpload.lng+"|name:我的位置&destination="
                        + address+"&mode=transit&region="
                        + LocateAndUpload.bdLocation.getCity()+"&output=html&src=webapp.baidu.openAPIdemo";
                toExplorer(url);
            }
        } else if (i == R.id.nagivation_gd) {
            //高德地图
            if (gdExist){
                NagivationUtils.toGaoDe(getActivity(),new LatLng(lat, lng),address);
            }else {
                //跳转浏览器
                ////uri.amap.com/navigation?from=116.478346,39.997361,startpoint&to=116.3246,39.966577,endpoint&via=116.402796,39.936915,midwaypoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0
                LatLng latLng = NagivationUtils.BD2GCJ(new LatLng(LocateAndUpload.lat,LocateAndUpload.lng));
                LatLng latLngTo = NagivationUtils.BD2GCJ(new LatLng(lat,lng));
                String url = "https://uri.amap.com/navigation?from="
                        + latLng.longitude+","
                        + latLng.latitude+",我的位置&to="
                        + latLngTo.longitude+","
                        + latLngTo.latitude+","
                        + address+"&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
                toExplorer(url);
            }
        } else if (i == R.id.nagivation_tx) {
            //腾讯地图
            if (txExist){
                NagivationUtils.toTengXun(getActivity(),new LatLng(lat, lng),address);
            }else {
                //跳转浏览器
                //https://apis.map.qq.com/uri/v1/routeplan?type=bus&from=我的家&fromcoord=39.980683,116.302&to=中关村&tocoord=39.9836,116.3164&policy=1&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
                LatLng latLng = NagivationUtils.BD2GCJ(new LatLng(LocateAndUpload.lat,LocateAndUpload.lng));
                LatLng latLngTo = NagivationUtils.BD2GCJ(new LatLng(lat,lng));
                String url = "https://apis.map.qq.com/uri/v1/routeplan?type=bus&from=我的位置&fromcoord="
                        + latLng.latitude +","
                        + latLng.longitude+"&to="
                        + address +"&tocoord="
                        + latLngTo.latitude +","
                        + latLngTo.longitude +"&policy=1&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77";
                toExplorer(url);
            }
        } else if (i == R.id.nagivation_close) {
            dismiss();
        }
    }

    /**
     * 显示dialog - menu
     * @param fragmentManager
     * @param lat
     * @param lng
     * @param address
     */
    public void showMenu(FragmentManager fragmentManager,Double lat, Double lng, String address){
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        //Log.e("fffffffffff","lat - " + lat + "  lng - " + lng + "  address - " + address);
        show(fragmentManager,"nav");
    }

    /**
     * 显示dialog - menu
     * @param fragmentTransaction
     * @param lat
     * @param lng
     * @param address
     */
    public void showMenu(FragmentTransaction fragmentTransaction, Double lat, Double lng, String address){
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        show(fragmentTransaction,"nav");
    }

    /**
     * 跳转浏览器
     * @param url
     */
    public void toExplorer(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        try {//利用发射获取listenersHandler
            Field field = Dialog.class.getDeclaredField("mListenersHandler");
            field.setAccessible(true);
            Handler mListenersHandler = (Handler) field.get(getDialog());
            if (mListenersHandler != null){
                mListenersHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}


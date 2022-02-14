package com.juntai.wisdom.dgjxb.home_page.map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.bdmap.utils.MapUtil;
import com.juntai.wisdom.dgjxb.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DistanceUtilActivity extends BaseActivity implements BaiduMap.OnMapClickListener,
        View.OnClickListener {
    private MapView mapView;
    private Button clear;
    private BaiduMap map;
    private TextView distanceTv;
    private List<LatLng> points = new ArrayList<>();
    private ImageView undo;
    private List<Marker> markers = new ArrayList<>();
    private List<Double> distances = new ArrayList<>();
    private List<Overlay> mLines = new ArrayList<>();
    private double distance;
    private BitmapDescriptor markerIcon = null;

    @Override
    public int getLayoutView() {
        return R.layout.activity_distance_util;
    }

    @Override
    public void initView() {
        setTitleName("地图测距");
        mapView = findViewById(R.id.map_view);
        map = mapView.getMap();
        distanceTv = findViewById(R.id.distance_tv);
        map.setOnMapClickListener(this);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(this);
        MapUtil.mapMoveTo(map, new LatLng(LocateAndUpload.lat, LocateAndUpload.lng));
        markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.line_marker);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (points.size() < 1) {
            points.add(latLng);
        } else {
            if (points.size() == 2)
                points.remove(0);
            points.add(latLng);
            double lineDistance = DistanceUtil.getDistance(points.get(points.size() - 1), points.get(points.size() - 2));
            distances.add(lineDistance);
            distance = distance + lineDistance;
            distanceTv.setText(getDistanceStr(distance) + "公里");
            if (points.size() > 1) {
                //设置折线的属性
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(0xff5ea7ff)
                        .points(points);
                Overlay mPolyline = map.addOverlay(mOverlayOptions);
                mLines.add(mPolyline);
                markers.get(markers.size() - 1).setToTop();
            }
        }
        OverlayOptions options = new MarkerOptions()
                .position(latLng)
                .icon(markerIcon)
                .anchor(0.5f, 0.5f);
        Marker marker = (Marker) map.addOverlay(options);
        marker.setToTop();
        markers.add(marker);
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {
        onMapClick(mapPoi.getPosition());
    }


    public String getDistanceStr(double distance){
        DecimalFormat df = new DecimalFormat("#.0");
        return  df.format(distance / 1000);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v){
        try {
            if (v.getId() == R.id.undo) {
                if (mLines.size() > 0) {
                    mLines.get(mLines.size() - 1).remove();
                    mLines.remove(mLines.size() - 1);
                    markers.get(markers.size() - 1).remove();
                    markers.remove(markers.size() - 1);
                    distance = distance-distances.get(distances.size()-1);
                    distances.remove(distances.size()-1);
                    distanceTv.setText(getDistanceStr(distance)+"公里");
                    points.remove(1);
                    points.add(markers.get(markers.size()-1).getPosition());
                    Collections.reverse(points);
                    if(mLines.size()==0){
                        points.remove(1);
                    }
                } else {
                    mLines.clear();
                    markers.get(0).remove();
                    markers.clear();
                    distances.clear();
                    points.clear();
                }
            }else if(v.getId() == R.id.clear){
                map.clear();
                mLines.clear();
                markers.clear();
                distances.clear();
                distance = 0;
                points.clear();
                distances.clear();
                distanceTv.setText("0公里");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.juntai.wisdom.policeAir.home_page.map;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseBottomDialog;
import com.juntai.wisdom.bdmap.utils.MapUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.history_track.HistoryTrackBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseCarHistory;
import com.juntai.wisdom.policeAir.utils.CalendarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  警员轨迹
 * @date 2020/4/2 15:05
 */
public class HistoryTrack extends BaseMvpActivity<MapPresenter> implements View.OnClickListener, MapContract.View {
    private PanoramaView panoramaView;
    private TextureMapView mapView;
    private BaiduMap map;
    //    private double lat = 35.090336, lng = 118.40202;
    private Marker locateMarker = null;
    public static final int TODAY = 20001;
    public static final int YESTERDAY = 20002;
    public static final int CUSTOM = 20003;
    public static final int CAR = 20004;
    public static final int PEOPLE = 20005;
    private String startTime, endTime;
    private SeekBar seekBar;
    private Handler handler;
    private int progress = 0;
    private boolean allowedToRun = true;
    private boolean startPlay = false;//开始播放
    private List<ResponseCarHistory.DataBean.ResultBean> points;
    private List<HistoryTrackBean.DataBean> pPoints;
    private TextView carSpeed, gpsTime, mSpeedValue;
    private BitmapDescriptor bitmap;
    private int type;
    private ImageView zoomPlus, zoomMinus, mPlayIv;
    private BaseBottomDialog baseBottomDialog;
    private double intervalTime = 3;//间隔时间
    private int defaultIntervalTime = 3;//间隔时间

    private boolean showStreetView = true;//显示街景
    /**
     * 地址：
     */
    private TextView mPositionAddrTv;
    /**
     * 定位类型：测试
     */
    private TextView mLocateTypeTv;


    @Override
    public int getLayoutView() {
        BMapManager bMapManager = new BMapManager(this.getApplication());
        bMapManager.init(i -> LogUtil.d(String.valueOf(i)));
        return R.layout.activity_history;
    }

    @Override
    public void initView() {

        setTitleName("轨迹回放");

        handler = new Handler();
        panoramaView = findViewById(R.id.panorama);
        mapView = findViewById(R.id.history_mapview);
        carSpeed = findViewById(R.id.car_speed);
        gpsTime = findViewById(R.id.car_gps_time);
        mSpeedValue = findViewById(R.id.speed_value_tv);
        seekBar = findViewById(R.id.progress_seekbar);
        zoomMinus = findViewById(R.id.zoomminus);
        mPlayIv = findViewById(R.id.play);
        zoomPlus = findViewById(R.id.zoomplus);
        zoomPlus.setOnClickListener(this);
        mPlayIv.setOnClickListener(this);
        mSpeedValue.setOnClickListener(this);
        zoomMinus.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress2, boolean fromUser) {
                progress = progress2;
                switch (type) {
                    case CAR:
                        pointMove(points.get(progress2));
                        break;
                    case PEOPLE:
                        pointMove(pPoints.get(progress2));
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogUtil.d("start_Tracking_Touch");
                startPlay = false;
                mPlayIv.performClick();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtil.d("stop_Tracking_Touch");
            }
        });
        mapView.getChildAt(1).setVisibility(View.GONE);
        map = mapView.getMap();
        panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionLow);
        panoramaView.setPanoramaViewListener(new PanoramaViewListener() {
            @Override
            public void onDescriptionLoadEnd(String s) {

            }

            @Override
            public void onLoadPanoramaBegin() {

            }

            @Override
            public void onLoadPanoramaEnd(String s) {

            }

            @Override
            public void onLoadPanoramaError(String s) {

            }

            @Override
            public void onMessage(String s, int i) {

            }

            @Override
            public void onCustomMarkerClick(String s) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });

        mapView.showZoomControls(false);
        creatThread();
        initRightTv();
        mPositionAddrTv = (TextView) findViewById(R.id.position_addr_tv);
        mLocateTypeTv = (TextView) findViewById(R.id.locate_type_tv);
    }

    /**
     * 右侧标题栏按钮
     */
    private void initRightTv() {
        getTitleRightTv().setText(showStreetView ? "隐藏街景" : "显示街景");
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStreetView = !showStreetView;
                getTitleRightTv().setText(showStreetView ? "隐藏街景" : "显示街景");
                panoramaView.setVisibility(showStreetView ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * 创建轨迹回放的线程
     */
    private void creatThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (allowedToRun) {
                    if (startPlay) {
                        if (progress < seekBar.getMax()) {
                            LogUtil.d(String.valueOf(progress));
                            progress++;
                            handler.post(progressPlus);
                            try {
                                Thread.sleep((long) (intervalTime * 1000));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            handler.post(updateUI);
                        }
                    }

                }
            }
        }).start();
    }

    /**
     * 车辆轨迹移动
     * @param resultBean
     */
    public void pointMove(ResponseCarHistory.DataBean.ResultBean resultBean) {
        if (locateMarker != null) {
            locateMarker.remove();
        }
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(new LatLng(resultBean.getLat(), resultBean.getLng()))
                .icon(bitmap);
        //在地图上添加Marker，并显示
        locateMarker = (Marker) map.addOverlay(option);
        panoramaView.setPanorama(resultBean.getLng(), resultBean.getLat());
        mPositionAddrTv.setText("位置:" + resultBean.getAddress());
        mLocateTypeTv.setText("类型:" + getCarLocType(resultBean.getPosType()));
        carSpeed.setText("时速:" + resultBean.getGpsSpeed() + "km/h");
        gpsTime.setText("时间:" + resultBean.getGpsTime());
        MapUtil.mapMoveTo(map, new LatLng(resultBean.getLat(), resultBean.getLng()));
        //        panoramaView.setPanoramaHeading(resultBean.getDirection());
    }

    /**
     * 获取车辆定位类型
     * @param posType
     * @return
     */
    private String getCarLocType(int posType) {
        String  type = null;
        switch (posType) {
            case 1:
                type = "GPS";
                break;
            case 2:
                type = "基站";
                break;
            case 3:
                type = "WiFi";
                break;
            default:
                break;
        }
        return type;
    }

    public void pointMove(HistoryTrackBean.DataBean resultBean) {
        if (locateMarker != null) {
            locateMarker.remove();
        }
        //定义Maker坐标点

        //构建Marker图标

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(new LatLng(resultBean.getLatitude(), resultBean.getLongitude()))
                .icon(bitmap);
        //在地图上添加Marker，并显示
        locateMarker = (Marker) map.addOverlay(option);
        panoramaView.setPanorama(resultBean.getLongitude(), resultBean.getLatitude());
        carSpeed.setText("时速:暂无" );
        mPositionAddrTv.setText("位置:" + resultBean.getAddress());
        mLocateTypeTv.setText("类型:" + resultBean.getPosType());
        gpsTime.setText("时间:" + resultBean.getGmtCreate());
        MapUtil.mapMoveTo(map, new LatLng(resultBean.getLatitude(), resultBean.getLongitude()));
        //        panoramaView.setPanoramaHeading(resultBean.getDirection());
    }


    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        int timeType = intent.getIntExtra("time", -1);

        switch (type) {
            case PEOPLE:
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.people_tip);
                String peopleId = intent.getStringExtra("peopleId");
                switch (timeType) {
                    case TODAY:
                        mPresenter.getPolicesTrack(mPresenter.getRequestBodyOfPoliceTrack(0, peopleId).build(),
                                MapContract.TRACK_PEOPLE);
                        break;
                    case YESTERDAY:
                        mPresenter.getPolicesTrack(mPresenter.getRequestBodyOfPoliceTrack(1, peopleId).build(),
                                MapContract.TRACK_PEOPLE);
                        break;
                    case CUSTOM:
                        //自定义时间段得轨迹
                        startTime = intent.getStringExtra("start");
                        endTime = intent.getStringExtra("end");
                        mPresenter.getPolicesTrack(mPresenter.getRequestBodyOfPoliceTrack(2, peopleId)
                                .add("startTime", startTime)
                                .add("endTime", endTime).build(), MapContract.TRACK_PEOPLE);
                        break;
                }
                break;
            case CAR:
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.car_tip);
                String imei = intent.getStringExtra("carImei");
                switch (timeType) {
                    case TODAY:
                        startTime = CalendarUtil.getTodayStartTime();
                        endTime = CalendarUtil.getCurrentTime();
                        break;
                    case YESTERDAY:
                        startTime = CalendarUtil.getYesterdayStartTime();
                        endTime = CalendarUtil.getTodayStartTime();
                        break;
                    case CUSTOM:
                        startTime = intent.getStringExtra("start");
                        endTime = intent.getStringExtra("end");
                        break;
                }
                mPresenter.getPoliceCarTrack(mPresenter.getBaseFormBodyBuilder().add("imEi", imei)
                        .add("startTime", startTime).add("endTime", endTime).build(), MapContract.TRACK_PEOPLE_CAR);
                break;
        }
    }

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }


    @Override
    protected void onResume() {
        panoramaView.onResume();
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        panoramaView.onPause();
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        allowedToRun = false;
        bitmap.recycle();
        panoramaView.destroy();
        mapView.onDestroy();
        mapView = null;
        releaseDialog();
        super.onDestroy();
    }

    Runnable progressPlus = () -> {
        seekBar.setProgress(progress);
    };
    Runnable updateUI = () -> {
        ToastUtils.toast(mContext, "播放完毕");
        startPlay = false;
        progress = 0;
        seekBar.setProgress(progress);
        mPlayIv.setImageResource(startPlay ? R.mipmap.pause_button : R.mipmap.play_button);
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (progress < seekBar.getMax()) {
                    startPlay = !startPlay;
                    mPlayIv.setImageResource(startPlay ? R.mipmap.pause_button : R.mipmap.play_button);
                } else {
                    startPlay = false;
                    progress = 0;
                    mPlayIv.performClick();
                }

                break;
            case R.id.speed_value_tv:
                //倍速
                initBottomDialog(Arrays.asList("0.5X", "0.75X", "1X", "1.25X", "1.5X", "2X"));
                break;
            case R.id.zoomminus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_OUT1, map);
                break;
            case R.id.zoomplus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_IN1, map);
                break;
        }
    }

    /**
     * 初始化dialog
     */
    public void initBottomDialog(List<String> arrays) {

        if (baseBottomDialog == null) {
            baseBottomDialog = new BaseBottomDialog();
            baseBottomDialog.setOnBottomDialogCallBack(new BaseBottomDialog.OnItemClick() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    String value = (String) adapter.getData().get(position);
                    mSpeedValue.setText(value);
                    if (value.contains("X")) {
                        value = value.replace("X", "");
                    }
                    intervalTime = defaultIntervalTime / Double.parseDouble(value);
                    baseBottomDialog.dismiss();
                }
            });
        }
        baseBottomDialog.setData(arrays);
        baseBottomDialog.show(getSupportFragmentManager(), "arrays");

    }

    /**
     * 释放dialog
     */
    private void releaseDialog() {
        if (baseBottomDialog != null) {
            if (baseBottomDialog.isAdded()) {
                baseBottomDialog.setOnBottomDialogCallBack(null);
                if (baseBottomDialog.getDialog().isShowing()){
                    baseBottomDialog.dismiss();
                }
            }
        }
        baseBottomDialog = null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

        switch (tag) {
            case MapContract.TRACK_PEOPLE:
                HistoryTrackBean historyTrackBean = (HistoryTrackBean) o;
                if (historyTrackBean != null) {
                    List<LatLng> points = new ArrayList<>();
                    pPoints = historyTrackBean.getData();
                    for (HistoryTrackBean.DataBean item : pPoints) {
                        LatLng latLng = new LatLng(item.getLatitude(), item.getLongitude());
                        points.add(latLng);
                    }
                    if (points.size() < 2) {
                        ToastUtils.toast(mContext, "轨迹点数量过少,暂无轨迹");
                        finish();
                        return;
                    }
                    //设置折线的属性
                    OverlayOptions mOverlayOptions = new PolylineOptions()
                            .width(10)
                            .color(0xAA409EFF)
                            .points(points);
                    //mPloyline 折线对象
                    Overlay mPolyline = map.addOverlay(mOverlayOptions);
                    MapUtil.mapMoveTo(map, points.get(0));
                    MapUtil.mapZoom(MapUtil.MAP_ZOOM_TO, map, 18);
                    seekBar.setMax(pPoints.size() - 1);
                    panoramaView.setPanorama(points.get(0).longitude, points.get(0).latitude);
                }
                break;
            case MapContract.TRACK_PEOPLE_CAR:
                ResponseCarHistory responseCarHistory = (ResponseCarHistory) o;

                if (responseCarHistory.getData().getCodeX() != 0) {
                    ToastUtils.toast(mContext, responseCarHistory.getData().getMessageX());
                    finish();
                    return;
                }
                points = responseCarHistory.getData().getResult();
                List<LatLng> points = new ArrayList<>();
                for (ResponseCarHistory.DataBean.ResultBean item : responseCarHistory.getData().getResult()) {
                    LatLng latLng = new LatLng(item.getLat(), item.getLng());
                    points.add(latLng);
                }
                if (points.size() < 2) {
                    ToastUtils.toast(mContext, "轨迹点数量过少,暂无轨迹");
                    finish();
                    return;
                }
                //设置折线的属性
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(0xAA409EFF)
                        .points(points);
                //在地图上绘制折线
                //mPloyline 折线对象
                Overlay mPolyline = map.addOverlay(mOverlayOptions);
                MapUtil.mapMoveTo(map, points.get(0));
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_TO, map, 18);
                seekBar.setMax(responseCarHistory.getData().getResult().size() - 1);
                panoramaView.setPanorama(points.get(0).longitude, points.get(0).latitude);
                break;
            default:
                break;
        }

    }

}

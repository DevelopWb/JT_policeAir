package com.juntai.wisdom.policeAir.home_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.SPTools;
import com.juntai.wisdom.basecomponent.utils.SnackbarUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.ProgressDialog;
import com.juntai.wisdom.bdmap.act.NavigationDialog;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.bdmap.utils.MapUtil;
import com.juntai.wisdom.bdmap.utils.SharedPreferencesUtil;
import com.juntai.wisdom.bdmap.utils.clusterutil.clustering.Cluster;
import com.juntai.wisdom.bdmap.utils.clusterutil.clustering.ClusterManager;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.base.MainActivity;
import com.juntai.wisdom.policeAir.bean.BannerNewsBean;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseDesBean;
import com.juntai.wisdom.policeAir.bean.map.MapClusterItem;
import com.juntai.wisdom.policeAir.bean.MapMenuButton;
import com.juntai.wisdom.policeAir.bean.PoliceCarBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseDrone;
import com.juntai.wisdom.policeAir.bean.map.ResponseSiteBean;
import com.juntai.wisdom.policeAir.bean.map.ResponseInspection;
import com.juntai.wisdom.policeAir.bean.map.ResponseKeyPersonnel;
import com.juntai.wisdom.policeAir.bean.map.ResponseNews;
import com.juntai.wisdom.policeAir.bean.map.ResponsePeople;
import com.juntai.wisdom.policeAir.bean.history_track.LocationBean;
import com.juntai.wisdom.policeAir.bean.stream.StreamCameraBean;
import com.juntai.wisdom.policeAir.home_page.map.DistanceUtilActivity;
import com.juntai.wisdom.policeAir.home_page.map.HistoryTrack;
import com.juntai.wisdom.policeAir.home_page.map.MapBottomListDialog;
import com.juntai.wisdom.policeAir.home_page.map.MapContract;
import com.juntai.wisdom.policeAir.home_page.map.MapMenuAdapter;
import com.juntai.wisdom.policeAir.home_page.map.MapPresenter;
import com.juntai.wisdom.policeAir.home_page.map.PanoramaActivity;
import com.juntai.wisdom.policeAir.home_page.map.SelectTime;
import com.juntai.wisdom.policeAir.home_page.weather.WeatherActivity;
import com.juntai.wisdom.basecomponent.utils.AppUtils;
import com.juntai.wisdom.policeAir.utils.DateUtil;
import com.juntai.wisdom.policeAir.utils.ImageUtil;
import com.juntai.wisdom.policeAir.utils.ObjectBox;
import com.juntai.wisdom.policeAir.utils.StringTools;
import com.sunfusheng.marqueeview.MarqueeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 地图相关
 *
 * @aouther Ma
 * @date 2019/3/14
 */
public class MyMapFragment extends BaseMvpFragment<MapPresenter> implements MapContract.View,
        View.OnClickListener,
        LocateAndUpload.Callback,
        ClusterManager.OnClusterClickListener<MapClusterItem>,
        ClusterManager.OnClusterItemClickListener<MapClusterItem>,
        MapBottomListDialog.MapBottomListItemClickListenner {

    private MapView mMapView = null;
    private LatLng myLocation = null;
    private BaiduMap mBaiduMap = null;
    private RecyclerView mapMenuButtonRv = null;
    //侧滑布局=======================================
    private DrawerLayout drawerLayout = null;
    private RelativeLayout sideLayout = null;
    private RelativeLayout partSideView = null;
    private LinearLayout mSearchLl;
    private RelativeLayout DistanceUtilBtn = null;
    private RelativeLayout sideViewNormal = null;
    private ConstraintLayout mMarqueeCl;
    private RelativeLayout twdBg, thdBg, weixingBg, jiejingBg;
    private TextView twdTv, thdTv, weixingTv, jiejingTv;
    private Switch heatMap, roadStatus, distanceUtil;
    private List<LatLng> heatMapItems = new ArrayList<>();
    private boolean firstLoad = true;

    HeatMap mHeatMap = null;
    //==============================================
    NavigationDialog navigationDialog;
    private ProgressDialog progressDialog;
    private SharedPreferencesUtil mapSP = null;
//    private MyOrientationListener myOrientationListener = null;
    private float direct = 0, locationRadius = 0;
    private String province = null, city = null, area = null;
    private Button backToMyLocation = null;
    private ImageView zoomPlus, zoomMinus;
    private ImageView satelliteMap = null, twdMap = null, thdMap = null, jiejing = null;
    private BaiduMap.OnMapClickListener normalClick;
    private BaiduMap.OnMapClickListener distanceUtilClick;
    private BaiduMap.OnMapClickListener areaUtilClick;
    private Boolean distanceUtilSwitch = false;
    private String headUrl = "";
    List<LatLng> distancePoints = new ArrayList<>();
    private boolean isFisrt = true;
    private List<MapClusterItem> clusterItemList = new ArrayList<>();
    private ClusterManager<MapClusterItem> clusterManager;
    private double distance = 0;
    private int clickedButton = -1;
    private View infowindowPeople = null;
    private int isSelectedPartType = -1;
    private boolean isFirstShow = true;
    private ImageView mScanIv;
    private ImageView mCallPoliceIv;
    private ImageView mDeleteNews;//删除资讯
    private MarqueeView marqueeView;
    private long currentTime;
    private List<BannerNewsBean.DataBean> noticeList;
    private boolean closeMarquee = false;//关闭轮播资讯

    //当前点击的marker
    Marker nowMarker;
    int dateType;//0监控，1案件。。。
    BitmapDescriptor bitmapDescriptor;
    String nowMarkerId = "";//当前markerid
    private MapStatus lastPosition;
    private int clickType = 2;//2单个marker点击，1聚合列表点击
    private MapBottomListDialog mapBottomListDialog;
    StreamCameraBean.DataBean currentStreamCamera;

    @Override
    protected MapPresenter createPresenter() {
        return new MapPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(new Intent(mContext, LocateAndUpload.class));
        } else {
            mContext.startService(new Intent(mContext, LocateAndUpload.class));
        }
        LocateAndUpload.setCallback(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocateAndUpload.setCallback(null);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mymap_latest;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        marqueeView = (MarqueeView) getView(R.id.marqueeView);
        sideViewNormal = getView(R.id.normal_side);
        mMarqueeCl = getView(R.id.marqueeView_cl);
        mDeleteNews = getView(R.id.delete_icon);
        mDeleteNews.setOnClickListener(this);
        DistanceUtilBtn = getView(R.id.distance_util_btn);
        DistanceUtilBtn.setOnClickListener(this);
        myLocation = new LatLng(0, 0);
        progressDialog = new ProgressDialog(mContext);
        navigationDialog = new NavigationDialog();
        //侧滑布局====================================================
        initDrawerlayout();
        sideLayout = getView(R.id.cehuabuju);
        partSideView = getView(R.id.part_side_view);
        twdBg = getView(R.id.twd_bg);
        thdBg = getView(R.id.thd_bg);
        jiejingBg = getView(R.id.jiejing_bg);
        weixingBg = getView(R.id.weixing_bg);
        twdTv = getView(R.id.twd_tv);
        thdTv = getView(R.id.thd_tv);
        jiejingTv = getView(R.id.jiejing_tv);
        weixingTv = getView(R.id.weixing_tv);
        heatMap = getView(R.id.relitukaiguan);
        roadStatus = getView(R.id.lukuangtukaiguan);
        distanceUtil = getView(R.id.map_distanceutil);
        //热力图监听
        heatMap.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                progressDialog.show();
                if (clusterItemList.size() > 0) {
                    heatMapItems.clear();
                    for (MapClusterItem item : clusterItemList) {
                        heatMapItems.add(item.getLatLng());
                    }
                    mHeatMap = new HeatMap.Builder().data(heatMapItems).build();
                    mBaiduMap.addHeatMap(mHeatMap);
                    progressDialog.dismiss();
                } else {
                    ToastUtils.toast(mContext, "没有选择查看的内容");
                    progressDialog.dismiss();
                    heatMap.setChecked(false);
                }
            } else {
                if (mHeatMap != null) {
                    mHeatMap.removeHeatMap();
                    heatMap.setChecked(false);
                }
            }
        });
        //路况图监听
        roadStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBaiduMap.setTrafficEnabled(true);
            } else {
                mBaiduMap.setTrafficEnabled(false);
            }
        });
        //地图测距
        distanceUtil.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBaiduMap.setOnMapClickListener(distanceUtilClick);
            } else {
                mBaiduMap.setOnMapClickListener(normalClick);
                distance = 0;
                clearTheMap(mBaiduMap);
                distancePoints.clear();
            }
        });
        //===========================================================
        backToMyLocation = getView(R.id.mylocation);
        zoomMinus = getView(R.id.zoomminus);
        zoomPlus = getView(R.id.zoomplus);
        zoomPlus.setOnClickListener(this);
        zoomMinus.setOnClickListener(this);
        backToMyLocation.setOnClickListener(this);
        getView(R.id.distance_switch).setOnClickListener(this);
        //侧滑界面地图相关按钮
        satelliteMap = getView(R.id.weixingtu);
        twdMap = getView(R.id.erdpingmiantu);
        thdMap = getView(R.id.sandfushitu);
        jiejing = getView(R.id.jiejing);
        jiejing.setOnClickListener(this);
        satelliteMap.setOnClickListener(this);
        twdMap.setOnClickListener(this);
        thdMap.setOnClickListener(this);
        //地图
        mMapView = getView(R.id.map_view_tmv);
        mBaiduMap = mMapView.getMap();
//        initLocateOritation();
        initUiSetting();
        clusterManager = new ClusterManager<>(mContext, mBaiduMap);
        clusterManager.setOnClusterItemClickListener(MyMapFragment.this);//点点击
        clusterManager.setOnClusterClickListener(MyMapFragment.this);//聚合展开
        //        map.setOnMarkerClickListener(clusterManager);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //不是大的聚合点
                if (!clusterManager.getClusterMarkerCollection().getMarkers().contains(marker)) {
                    if (nowMarker != null) {
                        if (bitmapDescriptor != null) {
                            nowMarker.setIcon(bitmapDescriptor);
                        }

                    }
                    nowMarker = marker;
                    clickType = 2;
                }
                //在nowMarker赋值之后
                clusterManager.onMarkerClick(marker);
                return false;
            }
        });
        //        map.setOnMapStatusChangeListener(clusterManager);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                if (lastPosition != null && lastPosition.zoom != mBaiduMap.getMapStatus().zoom) {
                    mMapView.removeView(infowindowPeople);
                    clickType = 2;
                    if (nowMarker != null) {
                        nowMarker.setIcon(bitmapDescriptor);
                        nowMarker = null;
                    }
                    nowMarkerId = "";
                }
                lastPosition = mBaiduMap.getMapStatus();
                clusterManager.onMapStatusChange(mapStatus);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
            }
        });
        normalClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMapView.removeView(infowindowPeople);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                mMapView.removeView(infowindowPeople);
            }
        };
        panoramaClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                startActivity(new Intent(mContext, PanoramaActivity.class).putExtra("lat",
                        latLng.latitude)
                        .putExtra("lng", latLng.longitude));
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                startActivity(new Intent(mContext, PanoramaActivity.class).putExtra("lat",
                        mapPoi.getPosition().latitude)
                        .putExtra("lng", mapPoi.getPosition().longitude));
            }
        };
        mBaiduMap.setOnMapClickListener(normalClick);
        //    entityListRl = getView(R.id.entity_list_rl);
        mapSP = new SharedPreferencesUtil(mContext);
        switch (mapSP.getIntSP("mapType")) {
            case BaiduMap.MAP_TYPE_NORMAL:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mapType(R.id.erdpingmiantu);
                break;
            case BaiduMap.MAP_TYPE_SATELLITE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                mapType(R.id.weixingtu);
                break;
        }
        View child = mMapView.getChildAt(1);
        if ((child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mapMenuButtonRv = getView(R.id.menu_list);
        // entityListRv = getView(R.id.entity_list_rv);
        // TODO: 2022-02-14 地图图标的接口
//        mPresenter.getMenus(MapContract.GET_MENUS);
        mapMenuButtonRv.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MapMenuAdapter(R.layout.map_menu_button, new ArrayList());
        mapMenuButtonRv.setAdapter(adapter);

        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
        mScanIv = (ImageView) getView(R.id.scan_iv);
        mScanIv.setOnClickListener(this);
        mCallPoliceIv = (ImageView) getView(R.id.call_police_iv);
        mCallPoliceIv.setOnClickListener(this);
    }

    private void initLocateOritation() {
//        //获取方向
//        myOrientationListener = new MyOrientationListener(mContext);
//        myOrientationListener.setOnOrientationListener(x -> {
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(locationRadius)
//                    .direction(direct)// 此处设置开发者获取到的方向信息，顺时针0-360
//                    .latitude(myLocation.latitude)
//                    .longitude(myLocation.longitude).build();
//            mBaiduMap.setMyLocationData(locData);
//            direct = x;
//        });
//        myOrientationListener.start();
    }

    private void initUiSetting() {
        //实例化UiSettings类对象
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        //通过设置enable为true或false 选择是否显示指南针
        mUiSettings.setCompassEnabled(false);
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null);
        mBaiduMap.setMyLocationConfiguration(config);
        mMapView.showZoomControls(false);
    }

    private void initDrawerlayout() {
        drawerLayout = getView(R.id.drawerlayout);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                ((MainActivity) getActivity()).drawerlayoutOpened(true);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                ((MainActivity) getActivity()).drawerlayoutOpened(false);
            }

            @Override
            public void onDrawerStateChanged(int i) {
            }
        });
    }

    MapMenuAdapter adapter;

    /**
     * 添加首页地图菜单
     *
     * @param list
     */
    protected void addMapMenuButton(final List<MapMenuButton.DataBean> list) {
        isSelectedPartType = -1;
        //RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(mContext);
        adapter.addData(list);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            nowMarkerId = "";
            nowMarker = null;
            final MapMenuButton.DataBean item = list.get(position);

            if (item.isSelected()) {
                item.setSelected(false);
                clickedButton = -1;
                adapter1.notifyItemChanged(position);
                ((TextView) getView(R.id.dl_tv)).setText("地图模式");
                sideViewNormal.setVisibility(View.VISIBLE);
                clearTheMap(mBaiduMap);
            } else {
                item.setSelected(true);
                if (clickedButton != -1) {
                    list.get(clickedButton).setSelected(false);
                    adapter1.notifyItemChanged(clickedButton);
                }
                clickedButton = position;
                adapter1.notifyItemChanged(position);
                LogUtil.d((item.getId() + ""));
                switch (item.getId()) {
                    case 1://地图选择按钮
                        //侧滑布局
                        if (drawerLayout.isDrawerVisible(sideLayout)) {
                            drawerLayout.closeDrawer(sideLayout);
                        } else {
                            drawerLayout.openDrawer(sideLayout);
                        }
                        item.setSelected(false);
                        adapter1.notifyItemChanged(position);
                        break;
                    case 2://天气实况按钮
                        clearTheMap(mBaiduMap);
                        if (!StringTools.isStringValueOk(province)) {
                            ToastUtils.warning(mContext, "定位未获取");
                        } else {
                            startActivity(new Intent(mContext, WeatherActivity.class)
                                    .putExtra("province", province)
                                    .putExtra("city", city)
                                    .putExtra("area", area == null ? "" : area));
                        }
                        item.setSelected(false);
                        adapter1.notifyItemChanged(position);
                        break;
                    case 3://视频监控按钮
                        clearTheMap(mBaiduMap);
                        mPresenter.getCameras(MapContract.GET_STREAM_CAMERAS);
                        break;
                    case 4://警情分布(案件)
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            clickCaseButton(view);
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 5://警员分布
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            mPresenter.getPolices(MapContract.GET_POLICE);
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 6://车辆分布
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            try {
                                mPresenter.getPoliceCars(MapContract.GET_CARS);
                            } catch (Exception e) {
                                ToastUtils.toast(mContext, "车辆服务异常,请联系后台人员");
                            }
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 7://场所管理
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            mPresenter.getSiteManagers(MapContract.GET_SITES);
                        } else {
                            item.setSelected(false);
                        }
                        break;
                    case 8://巡检
                        clearTheMap(mBaiduMap);
                        mPresenter.getInspection(MapContract.GET_INSPECTION);
                        break;
                    case 9://重点人员
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            mPresenter.getKeyPersonnels(MapContract.GET_KEY_PERSONNEL);
                        } else {
                            item.setSelected(false);
                        }
                        break;

                    case 10://缴费
                        clearTheMap(mBaiduMap);
                        AppUtils.checkIsInstall(getContext(), "com.eg.android.AlipayGphone");
                        item.setSelected(false);
                        break;
                    case 11://资讯
                        clearTheMap(mBaiduMap);
                        mPresenter.getNews(MapContract.GET_NEWS);
                        break;
                    case 13://无人机
                        if (MyApp.isCompleteUserInfo()) {
                            clearTheMap(mBaiduMap);
                            mPresenter.getDrones(MapContract.GET_DRONE);
                        } else {
                            item.setSelected(false);
                        }
                    default:
                        break;
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    /**
     * 地图模式切换
     *
     * @param viewId
     */
    private void mapType(int viewId) {
        switch (viewId) {
            case R.id.weixingtu:
                mBaiduMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                weixingTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.erdpingmiantu:
                mBaiduMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                twdTv.setTextColor(getResources().getColor(R.color.colorAccent));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.sandfushitu:
                mBaiduMap.setOnMapClickListener(normalClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                thdTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
            case R.id.jiejing:
                mBaiduMap.setOnMapClickListener(panoramaClick);
                twdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                thdBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                weixingBg.setBackgroundColor(getResources().getColor(R.color.transparent));
                jiejingTv.setTextColor(getResources().getColor(R.color.colorAccent));
                twdTv.setTextColor(Color.parseColor("#8a000000"));
                thdTv.setTextColor(Color.parseColor("#8a000000"));
                weixingTv.setTextColor(Color.parseColor("#8a000000"));
                break;
        }
    }

    /**
     * 案件分布
     *
     * @param view
     */
    private void clickCaseButton(View view) {
        LogUtil.d("show snackbar1111");
        if (SPTools.getBoolean(mContext, "showCaseMsg", true) && isFirstShow) {
            //            SnackbarUtil.show(view,"sss");
            SnackbarUtil.makeShort(view, "案件显示为当月").showCaseMessage("不再提示",
                    v -> SPTools.saveBoolean(mContext, "showCaseMsg", false));
            isFirstShow = false;
            LogUtil.d("show snackbar222");
        }
        mPresenter.getCases(MapContract.GET_CASES);
    }

    /**
     * 清理地图标点
     *
     * @param map
     */
    private void clearTheMap(BaiduMap map) {
        map.clear();
        clusterItemList.clear();
        clusterManager.clearItems();
        mMapView.removeView(infowindowPeople);
    }

    //每次显示都加载
    @Override
    protected void initData() {
        distanceUtilClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //                ToastUtils.toast(mContext, "click");
                if (distancePoints.size() < 1) {
                    //                    distance = DistanceUtil.getDistance()
                    OverlayOptions mTextOptions = new TextOptions()
                            .text("起点") //文字内容
                            .bgColor(0xffFFFFFF) //背景色
                            .fontSize(32) //字号
                            .fontColor(0xff5EA7FF) //文字颜色
                            //                        .rotate(-30) //旋转角度
                            .position(latLng);
                    //在地图上显示文字覆盖物
                    Overlay mText = mBaiduMap.addOverlay(mTextOptions);
                    distancePoints.add(latLng);
                } else {
                    if (distancePoints.size() == 2)
                        distancePoints.remove(0);
                    distancePoints.add(latLng);
                    distance =
                            distance + DistanceUtil.getDistance(distancePoints.get(distancePoints.size() - 1),
                                    distancePoints.get(distancePoints.size() - 2));
                    DecimalFormat df = new DecimalFormat("#.0");
                    String disStr = df.format(distance / 1000);
                    TextView child = new TextView(mContext);
                    child.setTextSize(15);
                    child.setBackgroundColor(Color.parseColor("#ffffff"));
                    child.setTextColor(Color.parseColor("#ff5ea7ff"));
                    child.setText(disStr + "公里");
                    // 调用一个参数的addView方法
                    MapViewLayoutParams params2;
                    InfoWindow pinfo;
                    if (distancePoints.get(1).latitude > distancePoints.get(0).latitude) {
                        pinfo = new InfoWindow(child, latLng, -10);
                    } else {
                        pinfo = new InfoWindow(child, latLng, 70);
                    }
                    mBaiduMap.showInfoWindow(pinfo);
                }
                //在地图上绘制折线
                //mPloyline 折线对象
                if (distancePoints.size() > 1) {
                    //设置折线的属性
                    OverlayOptions mOverlayOptions = new PolylineOptions()
                            .width(10)
                            .color(0xff5ea7ff)
                            .points(distancePoints);
                    Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
                }
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                onMapClick(mapPoi.getPosition());
            }
        };

        areaUtilClick = new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
            }
        };

        if (!closeMarquee) {
            // TODO: 2022-02-14 轮播的接口  关闭
//            mPresenter.getBannerNews(MapContract.BANNER_NEWS);
        }
    }

    /**
     * 地图点案件
     */
    private void clickCaseItem(MapClusterItem mmCase, BaiduMap map) {
        CaseDesBean.DataBean mCase = mmCase.mcase;
        LatLng caseLocation = new LatLng(mCase.getLatitude(), mCase.getLongitude());
        MapUtil.mapMoveTo(map, caseLocation);
        infowindowPeople = View.inflate(mContext, R.layout.infowindow_case, null);
        //将一个布局文件转换成一个view对象
        TextView titleTv = infowindowPeople.findViewById(R.id.item_title);
        TextView contentTv = infowindowPeople.findViewById(R.id.item_content);
        TextView timeTv = infowindowPeople.findViewById(R.id.item_time);
        titleTv.setText(mContext.getString(R.string.case_addr) + mCase.getAddress());
        contentTv.setText(mContext.getString(R.string.case_decontent) + mCase.getCaseContent());
        timeTv.setText(mContext.getString(R.string.case_date) + mCase.getCreateDate());
        ImageLoadUtil.loadImageCache(getContext(), mCase.getPhotoOne(),
                (ImageView) infowindowPeople.findViewById(R.id.case_img));
        infowindowPeople.findViewById(R.id.case_navigation).setOnClickListener(v -> {
            navigationDialog.showMenu(
                    getActivity().getSupportFragmentManager(),
                    mCase.getLatitude(),
                    mCase.getLongitude(),
                    mCase.getAddress());
        });
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(caseLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-mmCase.getBd().getBitmap().getHeight() * clickType)
                .build();
        mMapView.addView(infowindowPeople, params2);
    }


    /**
     * 地图点警员
     */
    private void clickPeopleItem(MapClusterItem item, BaiduMap map) {
        ResponsePeople.DataBean people = item.people;
        LatLng peopleLocation = new LatLng(people.getLatitude(), people.getLongitude());
        MapUtil.mapMoveTo(map, peopleLocation);
        infowindowPeople = View.inflate(mContext, R.layout.infowindow_people, null);//将一个布局文件转换成一个view对象
        String peopleInfo = "";
        peopleInfo = mContext.getString(R.string.people_name) + people.getNickname() + "\n"
                + mContext.getString(R.string.people_phone) + people.getAccount() + "\n"
                + mContext.getString(R.string.people_depaName) + people.getDepartmentName() + "\n"
                + mContext.getString(R.string.police_status) + (people.getState() == 1 ? "在线" : "离线");
        ((TextView) infowindowPeople.findViewById(R.id.people_infos)).setText(peopleInfo);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(people.getId()));
        ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                people.getHeadPortrait(),
                (ImageView) infowindowPeople.findViewById(R.id.head_image));
        infowindowPeople.findViewById(R.id.people_chat).setOnClickListener(v -> {
//            try {
//                ModuleIm_Init.chat(mContext, people.getAccount(), "指挥调度:" + people.getNickname());
//            } catch (IllegalArgumentException e) {
//                ToastUtils.toast(mContext, "通讯参数错误");
//            }
        });
        infowindowPeople.findViewById(R.id.people_history).setOnClickListener(v -> {
            infowindowPeople.findViewById(R.id.his_ll).setVisibility(View.VISIBLE);
            infowindowPeople.findViewById(R.id.people_origin).setVisibility(View.GONE);

        });

        infowindowPeople.findViewById(R.id.today_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, HistoryTrack.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.TODAY)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        infowindowPeople.findViewById(R.id.yesterday_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, HistoryTrack.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.YESTERDAY)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        infowindowPeople.findViewById(R.id.zidingyi_history).setOnClickListener(v -> {
            startActivity(new Intent(mContext, SelectTime.class)
                    .putExtra("type", HistoryTrack.PEOPLE)
                    .putExtra("time", HistoryTrack.CUSTOM)
                    .putExtra("peopleId", String.valueOf(people.getId())));
        });
        MapViewLayoutParams params2 = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(peopleLocation)
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-item.getBd().getBitmap().getHeight() * clickType)
                .build();
        mMapView.addView(infowindowPeople, params2);
    }

    private boolean panoramaSwitch = false;
    private BaiduMap.OnMapClickListener panoramaClick;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixingtu:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//打开卫星地图
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_SATELLITE);
                mapType(v.getId());
                break;
            case R.id.erdpingmiantu:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//打开卫星地图
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_NORMAL);
                mapType(v.getId());
                break;
            case R.id.sandfushitu:
                //3D俯视图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                mapSP.putIntSP("mapType", BaiduMap.MAP_TYPE_NORMAL);
                mapType(v.getId());
                break;
            case R.id.jiejing:
                if (panoramaSwitch) {
                    mBaiduMap.setOnMapClickListener(normalClick);
                    panoramaSwitch = false;
                    mapType(R.id.erdpingmiantu);
                } else {
                    mBaiduMap.setOnMapClickListener(panoramaClick);
                    panoramaSwitch = true;
                    mapType(v.getId());
                }
                break;
            case R.id.mylocation:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_TO, mBaiduMap, 16);
                MapUtil.mapMoveTo(mBaiduMap, myLocation);
                break;
            case R.id.test1:

                break;
            case R.id.test2:
                startActivity(new Intent(mContext, HistoryTrack.class));
                break;
            case R.id.distance_switch:
                mBaiduMap.setOnMapClickListener(distanceUtilSwitch ? normalClick : distanceUtilClick);
                if (distanceUtilSwitch) {
                    mBaiduMap.setOnMapClickListener(normalClick);
                    distance = 0;
                    clearTheMap(mBaiduMap);
                    distancePoints.clear();
                    distanceUtilSwitch = false;
                } else {
                    mBaiduMap.setOnMapClickListener(distanceUtilClick);
                    distanceUtilSwitch = true;
                }
                break;
            case R.id.zoomminus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_OUT1, mBaiduMap);
                break;
            case R.id.zoomplus:
                MapUtil.mapZoom(MapUtil.MAP_ZOOM_IN1, mBaiduMap);
                break;
            case R.id.distance_util_btn:
                startActivity(new Intent(mContext, DistanceUtilActivity.class));
                break;
            case R.id.delete_icon:
                closeMarquee = true;
                marqueeView.stopFlipping();
                mMarqueeCl.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 点聚合展开
     */
    @Override
    public boolean onClusterClick(Cluster<MapClusterItem> cluster) {
        List<MapClusterItem> items = new ArrayList<MapClusterItem>(cluster.getItems());
        //一标三实--不展示列表
        //if (items.get(0).getType().equals(MapClusterItem.YBSAS)) return false;
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) items);
        mapBottomListDialog = new MapBottomListDialog();
        mapBottomListDialog.setArguments(bundle);
        mapBottomListDialog.setCallback(this);
        mapBottomListDialog.show(getFragmentManager(), "list");
        LogUtil.d("mapLevel->" + String.valueOf(mMapView.getMapLevel()));
        return false;
    }

    /**
     * 地图点点击监听
     */
    @Override
    public boolean onClusterItemClick(MapClusterItem item) {
        LogUtil.d("clicked item");
        if (infowindowPeople != null) {
            mMapView.removeView(infowindowPeople);
        }
        switch (item.getType()) {
            case MapClusterItem.STREAM_CAMERA:
                if (0 == item.streamCamera.getFlag()) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.steam_cameras_tip);
                    //请求硬盘录像机下的摄像头
                    mPresenter.getAllStreamCamerasFromVCR(mPresenter.getPublishMultipartBody().addFormDataPart(
                            "dvrId", String.valueOf(item.streamCamera.getId())).build(),
                            MapContract.GET_STREAM_CAMERAS_FROM_VCR);
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
                    updateMarkerIcon(item.streamCamera.getEzOpen());
                }
                nowMarkerId = String.valueOf(item.streamCamera.getNumber());
                break;
            case MapClusterItem.PEOPLE:
                updateMarkerIcon(item.people.getHeadPortrait());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.people.getId()))) {
                    //请求警员详情
                    mPresenter.getPolicePeopleDetail(item, MapContract.GET_POLICE_DETAIL);
                }
                nowMarkerId = String.valueOf(item.people.getId());
                break;
            case MapClusterItem.CASE:
                updateMarkerIcon(item.mcase.getPhotoOne());
                if (clickType == 1 || nowMarkerId.equals(String.valueOf(item.mcase.getId()))) {
                    clickCaseItem(item, mBaiduMap);
                }
                nowMarkerId = String.valueOf(item.mcase.getId());
                break;
        }
        return false;
    }

    /**
     * 设置显示在屏幕中的地理范围
     */
    private void zoomToSpan(List<MapClusterItem> clusterItemList) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // 需要显示的marker点
        for (MapClusterItem mapClusterItem : clusterItemList) {
            builder.include(mapClusterItem.getPosition());
        }
        if (myLocation != null) {
            // 我的当前位置
            builder.include(new LatLng(myLocation.latitude, myLocation.longitude));
        }
        LatLngBounds bounds = builder.build();
        // 设置显示在屏幕中的地图地理范围
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
        mBaiduMap.setMapStatus(u);
    }

    /**
     * 更新marker图标
     */
    public void updateMarkerIcon(String path) {
        if (nowMarker == null) {
            return;
        }
        ImageLoadUtil.getBitmap(getContext().getApplicationContext(), path, R.mipmap.ic_error,
                new ImageLoadUtil.BitmapCallBack() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        try {
                            nowMarker.setIcon(BitmapDescriptorFactory.fromBitmap(ImageUtil.combineBitmap(
                                    BitmapFactory.decodeStream(getResources().getAssets().open(
                                            "ic_map_shop_bg.png")),
                                    ImageUtil.getRoundedCornerBitmap(ImageUtil.zoomImg(bitmap), 200))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        releaseBottomListDialog();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        //baiDuLocationUtils.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
//        myOrientationListener.stop();
        super.onDestroy();
    }

    /**
     * 实时更新位置
     *
     * @param bdLocation
     */
    @Override
    public void onLocationChange(BDLocation bdLocation) {
        if (bdLocation == null || mMapView == null) {
            return;
        }
        if (isFisrt) {
            MapUtil.mapMoveTo(mBaiduMap, new LatLng(bdLocation.getLatitude(),
                    bdLocation.getLongitude()));
            isFisrt = false;
        }
        LogUtil.d("更新轨迹位置->" + "latitude=" + bdLocation.getLatitude()
                + "&longitude=" + bdLocation.getLongitude());
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(direct).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        locationRadius = bdLocation.getRadius();
        mBaiduMap.setMyLocationData(locData);
        province = bdLocation.getProvince();
        city = bdLocation.getCity();
        area = bdLocation.getDistrict();
        myLocation = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MyApp.app.setMyLocation(myLocation);
        MyApp.app.setBdLocation(bdLocation);

        //保存轨迹到本地
        if (MyApp.getUser() != null &&
                MyApp.getUser().getData() != null) {
            LocationBean locationBean = new LocationBean(bdLocation.getAddrStr() + "",
                    LocateAndUpload.getLocType(bdLocation),
                    bdLocation.getLongitude() + "",
                    bdLocation.getLatitude() + "",
                    DateUtil.getCurrentTime() + "");
            if (firstLoad) {
                LogUtil.d("第一次启动  上传最新位置数据");
                firstLoad = false;
                ((MainActivity) Objects.requireNonNull(getActivity())).startUploadLocateData(500);
            }
            ObjectBox.get().boxFor(LocationBean.class).put(locationBean);
        }
    }

    /**
     * 聚合列表条目监听
     *
     * @param item
     */
    @Override
    public void onBottomListItemClick(MapClusterItem item) {
        if (infowindowPeople != null)
            mMapView.removeView(infowindowPeople);
        if (nowMarker != null) {
            nowMarker.setIcon(bitmapDescriptor);
        }
        nowMarker = null;
        mBaiduMap.hideInfoWindow();
        clickType = 1;
        onClusterItemClick(item);
        releaseBottomListDialog();
    }

    /**
     * 释放dialog
     */
    private void releaseBottomListDialog() {
        if (mapBottomListDialog != null) {
            mapBottomListDialog.setCallback(null);
            mapBottomListDialog.dismiss();
            mapBottomListDialog = null;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MapContract.GET_MENUS://获取地图菜单按钮
                MapMenuButton mapMenuButton = (MapMenuButton) o;
                MyApp.setMapMenuButton(mapMenuButton);
//                addMapMenuButton(mapMenuButton.getData());
                break;
//                OpenLiveBean openLiveBean = (OpenLiveBean) o;
//                int errorCode = openLiveBean.getErrcode();
//                if (errorCode < 0) {
//                    ToastUtils.toast(mContext, "设备离线，无数据");
//                    return;
//                }
//                String playUrl = openLiveBean.getVideourl();
//                if (StringTools.isStringValueOk(playUrl)) {
//                    if (playUrl.contains("//")) {
//                        playUrl = playUrl.substring(playUrl.indexOf("//")+2);
//                        playUrl = playUrl.substring(playUrl.indexOf("/"));
//                        playUrl =AppHttpPath.BASE_CAMERA_DNS+playUrl;
//                    }
//                }
//                String strsessionid = openLiveBean.getStrsessionid();
//                startActivity(new Intent(mContext.getApplicationContext(), PlayerLiveActivity.class)
//                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_ID, currentStreamCamera.getId())
//                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_URL, playUrl)
//                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_SESSION_ID, strsessionid)
//                        .putExtra(PlayerLiveActivity.STREAM_CAMERA_NUM, currentStreamCamera.getNumber()));
            case MapContract.BANNER_NEWS:
                BannerNewsBean bannerNewsBean = (BannerNewsBean) o;
                if (bannerNewsBean != null) {
                    noticeList = bannerNewsBean.getData();
                    if (noticeList != null && noticeList.size() > 0) {
                        mMarqueeCl.setVisibility(View.VISIBLE);
                        marqueeView.startWithList(noticeList);
                    } else {
                        mMarqueeCl.setVisibility(View.GONE);
                    }
                }
                break;
            case MapContract.GET_STREAM_CAMERAS:
                StreamCameraBean streamCameraBean = (StreamCameraBean) o;
                if (streamCameraBean != null) {
                    List<StreamCameraBean.DataBean> datas = streamCameraBean.getData();
                    if (datas != null) {
                        for (StreamCameraBean.DataBean camera : datas) {
                            MapClusterItem mCItem = new MapClusterItem(
                                    new LatLng(camera.getLatitude(), camera.getLongitude()), camera);
                            clusterItemList.add(mCItem);
                        }
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                    dateType = 0;
                }
                break;
            case MapContract.GET_STREAM_CAMERAS_FROM_VCR:
                StreamCameraBean bean = (StreamCameraBean) o;
                if (bean != null) {
                    List<StreamCameraBean.DataBean> datas = bean.getData();
                    if (datas != null) {
                        List<MapClusterItem> items = new ArrayList<MapClusterItem>();
                        for (StreamCameraBean.DataBean camera : datas) {
                            camera.setFlag(1);
                            MapClusterItem mCItem = new MapClusterItem(
                                    new LatLng(camera.getLatitude(), camera.getLongitude()), camera);
                            items.add(mCItem);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) items);
                        MapBottomListDialog mapBottomListDialog = new MapBottomListDialog();
                        mapBottomListDialog.setArguments(bundle);
                        mapBottomListDialog.setCallback(this);
                        mapBottomListDialog.show(getFragmentManager(), "list");
                    }
                }
                break;
            case MapContract.GET_CASES://案件
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.case_tip);
                CaseDesBean caseDesBean = (CaseDesBean) o;
                if (caseDesBean != null) {
                    List<CaseDesBean.DataBean> cases = caseDesBean.getData();
                    for (CaseDesBean.DataBean aCase : cases) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(aCase.getLatitude(), aCase.getLongitude()), aCase);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                    dateType = 1;
                }
                break;
            case MapContract.GET_CARS://获取车辆信息
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.car_tip);
                PoliceCarBean responseCar = (PoliceCarBean) o;
                for (PoliceCarBean.DataBean item : responseCar.getData()) {
                    MapClusterItem mCItem = new MapClusterItem(
                            new LatLng(item.getLat(), item.getLng()), item);
                    clusterItemList.add(mCItem);
                }
                clusterManager.addItems(clusterItemList);
                clusterManager.cluster();
                break;
            case MapContract.GET_POLICE://警员们
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.people_tip);
                ResponsePeople responsePeople = (ResponsePeople) o;
                for (ResponsePeople.DataBean item : responsePeople.getData()) {
                    MapClusterItem mCItem = new MapClusterItem(
                            new LatLng(item.getLatitude(), item.getLongitude()), item);
                    clusterItemList.add(mCItem);
                }
                clusterManager.addItems(clusterItemList);
                clusterManager.cluster();
                break;
            case MapContract.GET_POLICE_DETAIL://警员详情
                MapClusterItem item = (MapClusterItem) o;
                if (item.people != null) {
                    clickPeopleItem(item, mBaiduMap);
                }
                break;
            case MapContract.GET_INSPECTION://巡检
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.inspection_tip);
                ResponseInspection responseInspection = (ResponseInspection) o;
                if (responseInspection != null) {
                    List<ResponseInspection.DataBean> inspections = responseInspection.getData();
                    for (ResponseInspection.DataBean inspection : inspections) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(inspection.getLatitude(), inspection.getLongitude()),
                                inspection);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            case MapContract.GET_NEWS://资讯
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.news_tip);
                ResponseNews responseNews = (ResponseNews) o;
                if (responseNews != null) {
                    List<ResponseNews.News> news = responseNews.getData();
                    for (ResponseNews.News newsItem : news) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(newsItem.getLatitude(), newsItem.getLongitude()),
                                newsItem);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            case MapContract.GET_SITES://场所
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.site_tip);
                ResponseSiteBean realLocBean = (ResponseSiteBean) o;
                int size = realLocBean.getData().size();
                for (int i = 0; i < size; i++) {
                    MapClusterItem mCItem = new MapClusterItem(
                            new LatLng(realLocBean.getData().get(i).getLatitude(),
                                    realLocBean.getData().get(i).getLongitude()),
                            realLocBean.getData().get(i), R.mipmap.site_tip);
                    clusterItemList.add(mCItem);
                }
                clusterManager.addItems(clusterItemList);
                clusterManager.cluster();
                break;
            case MapContract.GET_KEY_PERSONNEL://重点人员
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.key_personnel_tip);
                ResponseKeyPersonnel responseKeyPersonnel = (ResponseKeyPersonnel) o;
                if (responseKeyPersonnel != null) {
                    List<ResponseKeyPersonnel.DataBean> keyPersonnels = responseKeyPersonnel.getData();
                    for (ResponseKeyPersonnel.DataBean keyPersonnel : keyPersonnels) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(keyPersonnel.getLatitude(), keyPersonnel.getLongitude()),
                                keyPersonnel);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            case MapContract.GET_DRONE://无人机
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
                ResponseDrone responseDrone = (ResponseDrone) o;
                if (responseDrone != null) {
                    List<ResponseDrone.DroneBean> droneBeans = responseDrone.getData();
                    for (ResponseDrone.DroneBean droneBean : droneBeans) {
                        MapClusterItem mCItem = new MapClusterItem(
                                new LatLng(droneBean.getLatitude(), droneBean.getLongitude()),
                                droneBean);
                        clusterItemList.add(mCItem);
                    }
                    clusterManager.addItems(clusterItemList);
                    clusterManager.cluster();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        String msg = (String) o;
        ToastUtils.toast(mContext, msg);
        switch (tag) {
            case MapContract.GET_CARS:
//                ToastUtils.toast(mContext, msg);
                break;
            case MapContract.GET_MENUS:
                //菜单
                addMapMenuButton(MyApp.getMapMenuButton().getData());
                break;
            case MapContract.GET_SITES://获取场所
                break;
            case MapContract.GET_POLICE:
                break;
            case MapContract.GET_CASES:
                break;
            case MapContract.GET_INSPECTION:
                break;
            case MapContract.GET_NEWS:
                break;
            case MapContract.GET_DRONE:
                break;
            case MapContract.GET_KEY_PERSONNEL:
                break;
            default:
                if (adapter.getData().size() > 0 && clickedButton > -1) {
                    adapter.getData().get(clickedButton).setSelected(false);
                    adapter.notifyItemChanged(clickedButton);
                    clickedButton = -1;
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void receiveMsg(String test) {
        if (ActionConfig.BROAD_LOGIN_OUT.equals(test)) {
            //刷新
            clearTheMap(mBaiduMap);
            if (clickedButton != -1) {
                adapter.getData().get(clickedButton).setSelected(false);
                adapter.notifyItemChanged(clickedButton);
            }
            clickedButton = -1;
        }
    }
}


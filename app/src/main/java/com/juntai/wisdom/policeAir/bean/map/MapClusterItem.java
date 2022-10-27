package com.juntai.wisdom.policeAir.bean.map;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.bdmap.utils.clusterutil.clustering.ClusterItem;
import com.juntai.wisdom.policeAir.bean.FlyOperatorsBean;
import com.juntai.wisdom.policeAir.bean.PoliceCarBean;
import com.juntai.wisdom.policeAir.bean.case_bean.CaseDesBean;
import com.juntai.wisdom.policeAir.bean.stream.StreamCameraBean;

/**
 * author:wong
 * Date: 2019/3/22
 * Description:
 */
public class MapClusterItem implements ClusterItem, Comparable<MapClusterItem> {
    private BitmapDescriptor Bd;
    private LatLng latLng;
    private String type;
    public StreamCameraBean.DataBean streamCamera;
    public CaseDesBean.DataBean mcase;
    public ResponsePeople.DataBean people;
    public PoliceCarBean.DataBean car;
    public ResponseSiteBean.DataBean site;//一标三实
    public ResponseInspection.DataBean inspection;
    public ResponseNews.News news;
    public FlyOperatorsBean.DataBean flyOperator;
    public ResponseKeyPersonnel.DataBean keyPersonnel;
    public ResponseDrone.DroneBean droneBean;

    public static final String DRONE = "drone";
    public static final String STREAM_CAMERA = "stream_camera";
    public static final String CASE= "case";
    public static final String PEOPLE = "people";
    public static final String CAR = "car";
    /**
     * 飞手
     */
    public static final String FLY_OPERAOTR = "FLY_OPERAOTR";
    public static final String SITE= "site";//一标三实
    public static final String INSPECTION = "inspection";
    public static final String NEWS = "news";
    public static final String KEY_PERSONNEL = "key_personnel";

    public MapClusterItem(LatLng latLng, StreamCameraBean.DataBean camera) {
        if (0==camera.getFlag()) {
            //硬盘录像机
            Bd = BitmapDescriptorFactory.fromResource(R.mipmap.steam_cameras_tip);
        }else {
            //独立摄像头
            Bd = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
        }
        this.latLng = latLng;
        this.type = STREAM_CAMERA;
        this.streamCamera = camera;
//        this.isClicked = false;
    }

    public MapClusterItem(LatLng latLng, ResponsePeople.DataBean people) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.people_tip);
//        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_shop);
        this.people = people;
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = PEOPLE;
    }

    public MapClusterItem(LatLng latLng, CaseDesBean.DataBean mcase) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.case_tip);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = CASE;
        this.mcase = mcase;
    }

    public MapClusterItem( FlyOperatorsBean.DataBean flyOperator) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.fly_operator_tip);
        this.latLng =  new LatLng(Double.parseDouble(flyOperator.getLatitude()), Double.parseDouble(flyOperator.getLongitude()));
        this.type = FLY_OPERAOTR;
        this.flyOperator = flyOperator;
    }
    public MapClusterItem(LatLng latLng, PoliceCarBean.DataBean car) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.car_tip);
        this.latLng = latLng;
        this.type = CAR;
        this.car = car;
    }

    public MapClusterItem(LatLng latLng, ResponseInspection.DataBean inspection) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.inspection_tip);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = INSPECTION;
        this.inspection = inspection;
    }

    public MapClusterItem(LatLng latLng, ResponseNews.News news) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.news_tip);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = NEWS;
        this.news = news;
    }

    public MapClusterItem(LatLng latLng, ResponseKeyPersonnel.DataBean keyPersonnel) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.key_personnel_tip);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = KEY_PERSONNEL;
        this.keyPersonnel = keyPersonnel;
    }

    public MapClusterItem(LatLng latLng, ResponseDrone.DroneBean droneBean) {
        Bd = BitmapDescriptorFactory.fromResource(R.mipmap.camera_tip);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = DRONE;
        this.droneBean = droneBean;
    }

    /**
     * 单位管理
     * @param latLng
     * @param dataBean
     * @param res
     */
    public MapClusterItem(LatLng latLng, ResponseSiteBean.DataBean dataBean, int res) {
        Bd = BitmapDescriptorFactory.fromResource(res);
//        this.isClicked = false;
        this.latLng = latLng;
        this.type = SITE;
        this.site = dataBean;
    }


    public BitmapDescriptor getBd() {
        return Bd;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getType() {
        return type;
    }

    public void setBd(int res) {
        Bd = BitmapDescriptorFactory.fromResource(res);;
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return Bd;
    }

    @Override
    public int compareTo(MapClusterItem o) {
        if (o.getType().equals(MapClusterItem.NEWS)){
            return o.news.getGmtCreate().compareTo(this.news.getGmtCreate());
        }
        return 0;
    }
}
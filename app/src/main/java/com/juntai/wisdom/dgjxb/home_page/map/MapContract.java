package com.juntai.wisdom.dgjxb.home_page.map;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.dgjxb.bean.map.MapClusterItem;

import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public interface MapContract {

    String GET_MENUS = "get_menus";//获取菜单
    String GET_STREAM_CAMERAS = "get_stream_camera";//获取所有的流摄像头
    String GET_STREAM_CAMERAS_FROM_VCR = "get_stream_camera_from";//获取硬盘录像机下的所有的流摄像头

    String GET_DRONE = "get_drone";//获取无人机
    String GET_CASES = "get_cases";//获取案件
    String GET_POLICE = "get_police";//获取警员
    String GET_POLICE_DETAIL = "GET_POLICE_DETAIL";//获取警员
    String GET_CARS = "get_cars";//获取车辆
    String GET_SITES = "get_sites";//获取场所
    String GET_NEWS = "get_news";//获取资讯
    String BANNER_NEWS = "banner_news";//滚动新闻
    String TRACK_PEOPLE = "track";//警员轨迹
    String TRACK_PEOPLE_CAR = "track_car";//警che轨迹
    //巡检
    String GET_INSPECTION = "get_inspection";//获取巡检
    String INSPECTION_POINT_INFO = "inspection_point_info";//巡检点详情
    String INSPECTIONS_RECORD = "inspections_info";//巡检记录
    //重点人员
    String GET_KEY_PERSONNEL = "get_key_personnel";//获取重点人员
    String GET_KEY_PERSONNEL_INFO = "get_key_personnel_info";//获取重点人员详情
    String GET_INTERVIEW = "get_interview";//获取走访记录

    interface View extends IView {

    }

    interface Presenter extends IPresenter<View> {
        /**
         * 获取菜单
         *
         * @param tag
         */
        void getMenus(String tag);

        /**
         * 获取摄像头数据
         *
         * @param tag
         */
        void getCameras(String tag);

        /**
         * 获取案件数据
         *
         * @param tag
         */
        void getCases(String tag);

        /**
         * 获取警员数据
         *
         * @param tag
         */
        void getPolices(String tag);

        /**
         * 获取警员轨迹数据
         *
         * @param tag
         */
        void getPolicesTrack(RequestBody requestBody, String tag);

        /**
         * 获取车辆轨迹数据
         *
         * @param tag
         */
        void getPoliceCarTrack(RequestBody requestBody, String tag);

        /**
         * 获取警车数据
         *
         * @param tag
         */
        void getPoliceCars(String tag);

        /**
         * 获取巡检数据
         *
         * @param tag
         */
        void getInspection(String tag);

        /**
         * 获取资讯数据
         *
         * @param tag
         */
        void getNews(String tag);

        /**
         * 获取单位管理数据
         *
         * @param tag
         */
        void getSiteManagers(String tag);

        /**
         * 获取无人机数据
         *
         * @param tag
         */
        void getDrones(String tag);

        /**
         * 案件跟踪
         *
         * @param tag
         * @param id
         */
        void getCaseInfo(String tag, int id);

        /**
         * 巡检点详情
         *
         * @param patrolId
         * @param tag
         */
        void getInspectionPointInfo(int patrolId, String tag);

        /**
         * 获取巡检记录
         *
         * @param patrolId
         * @param tag
         */
        void getInspectionRecord(int patrolId, int pageNum, int pageSize, String tag, boolean isShow);

        /**
         * 获取置顶资讯
         *
         * @param tag
         */
        void getBannerNews(String tag);

        /**
         * 警员详情
         */
        void getPolicePeopleDetail(MapClusterItem item, String tag);

        /**
         * 单位详情
         */
        void getUnitDetail(int unitId);

        /**
         * 获取硬盘录像机下的所有的流摄像头
         *
         * @param tag
         */
        void getAllStreamCamerasFromVCR(RequestBody requestBody, String tag);

        /**
         * 重点人员
         *
         * @param tag
         */
        void getKeyPersonnels(String tag);

        /**
         * 重点人员详情
         *
         * @param tag
         * @param id
         */
        void getKeyPersonnelInfo(String tag, int id);

        /**
         * 走访列表
         *
         * @param tag
         * @param id
         */
        void getInterviewList(int id, int pageNum, int pageSize, String tag);
    }
}

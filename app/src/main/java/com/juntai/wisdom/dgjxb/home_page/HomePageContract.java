package com.juntai.wisdom.dgjxb.home_page;

import com.juntai.wisdom.basecomponent.mvp.IPresenter;
import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/3/12 16:00
 */
public interface HomePageContract {
    String GET_WEATHER_REAL_TIME = "get_real_time";//实时数据
    String GET_FORCAST_WEATHER = "get_forcast";//预报
    String GET_PRIVINCE = "get_privince";//省份
    String GET_CITY = "get_city";//市
    String GET_TOWN = "get_town";//县
    String GET_STREET = "get_street";//街道
    String SEARCH = "search";//搜索
    String SEARCH_MORE = "search_more";//搜索更多


    interface IHomePageView extends IView {

    }

    interface IHomePagePresent extends IPresenter<IHomePageView> {
        /**
         * 认证
         *
         * @param tag
         * @param requestBody
         */
        void userAuth(String tag, RequestBody requestBody);

        /**
         * 获取实时数据 天气
         *
         * @param tag
         * @param lng
         * @param lat
         */
        void getWeatherRealTime(String tag, String lng, String lat);

        /**
         * 获取预报 天气
         *
         * @param tag
         * @param lng
         * @param lat
         */
        void getForcastWeather(String tag, String lng, String lat);

        /**
         * 获取省列表
         * @param tag
         */
        void getPrivince(String tag);

        /**
         * 获取城市列表
         * @param tag
         * @param privinceNum
         */
        void getCitys(String tag, String privinceNum);

        /**
         * 获取县列表
         *
         * @param tag
         * @param cityNum
         */
        void getTowns(String tag, String cityNum);
        /**
         * 获取街道列表
         *
         * @param tag
         * @param townNum
         */
        void getStreets(String tag, String townNum);

        void search(String tag, RequestBody requestBody);

        /**
         * 获取更多数据
         * @param tag
         * @param requestBody
         */
        void search_getmore(String tag, RequestBody requestBody);

        /**
         * 获取接警员信息
         */
        void getPolicePickerInfo();
    }

}

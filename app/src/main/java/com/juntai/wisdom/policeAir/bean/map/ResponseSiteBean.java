package com.juntai.wisdom.policeAir.bean.map;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 场所管理列表
 * @aouther ZhangZhenlong
 * @date 2020-10-20
 */
public class ResponseSiteBean extends BaseResult implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * "id": 1280,
         *             "name": "悦宝园",
         *             "latitude": 35.0515050,
         *             "longitude": 118.3126680,
         *             "address": "地址：临沂市兰山区平安路",
         *             "logoUrl": "http://61.156.157.132:32288/crm_client_logo/4106a1f67a7a49008f5218fa34024b5f.jpeg"
         */

        private int id;//场所id
        private String name;//场所名称
        private double latitude;//维度
        private double longitude;//经度
        private String address;//所在地址
        private String logoUrl;//场所logo图

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }


        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLogoUrl() {
            return logoUrl == null? "" : logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }
}

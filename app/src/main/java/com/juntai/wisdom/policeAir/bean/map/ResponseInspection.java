package com.juntai.wisdom.policeAir.bean.map;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:巡检坐标点
 * Create by zhangzhenlong
 * 2020-3-21
 * email:954101549@qq.com
 */
public class ResponseInspection extends BaseResult implements Serializable {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * type : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * "id": 52,
         *             "name": "临沂一小",
         *             "address": "山东省临沂市兰山区刘家园街",
         *             "latitude": 35.0682860,
         *             "longitude": 118.3595970,
         *             "logoUrl": "http://61.156.157.132:32288/crm_client_logo/d95e51875bf04381be832dbc35d15ba9.jpeg"
         */


        private int id;//巡检地址id
        private String name;//巡检地址名称
        private String address;//所在地址
        private double latitude;//维度
        private double longitude;//经度
        private String logoUrl;//巡检地址logo图

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getLogoUrl() {
            return logoUrl == null? "" : logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }
}

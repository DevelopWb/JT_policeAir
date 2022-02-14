package com.juntai.wisdom.policeAir.bean.map;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:无人机地图坐标点
 * Create by zhangzhenlong
 * 2021-5-13
 * email:954101549@qq.com
 */
public class ResponseDrone extends BaseResult implements Serializable {
    private List<DroneBean> data;

    public List<DroneBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DroneBean> data) {
        this.data = data;
    }

    public static class DroneBean{
        private int id;//无人机id
        private String name;//名称
        private int userId;//操作人员id
        private String img;//图片
        private double longitude;
        private double latitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
    }
}

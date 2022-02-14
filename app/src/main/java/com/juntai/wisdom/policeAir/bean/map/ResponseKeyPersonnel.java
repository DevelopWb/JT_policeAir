package com.juntai.wisdom.policeAir.bean.map;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Describe: 地图点重点人员
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class ResponseKeyPersonnel extends BaseResult implements Serializable {
    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": [
     *         {
     *             "id": 2,
     *             "name": "张景峰",
     *             "phone": "18669921199",
     *             "latitude": 35.0706590,
     *             "longitude": 118.3542510,
     *             "headPortrait": "https://www.juntaikeji.com:17002/thumbnail/head_img/ea6c1c82d5384705bd841f8b64d43381.jpeg"
     *         }
     *     ],
     *     "type": null,
     *     "message": null,
     *     "success": true
     * }
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         *"id": 2,
         *             "name": "张景峰",
         *             "phone": "18669921199",
         *             "latitude": 35.0706590,
         *             "longitude": 118.3542510,
         *             "headPortrait": "https://www.juntaikeji.com:17002/thumbnail/head_img/ea6c1c82d5384705bd841f8b64d43381.jpeg"
         */



        private int id;//重点人员id
        private String name;//姓名
        private String phone;//联系方式
        private double latitude;//维度
        private double longitude;//经度
        private String headPortrait;//头像

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

        public String getPhone() {
            return phone == null? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
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
    }
}

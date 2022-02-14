package com.juntai.wisdom.policeAir.bean.map;

import com.google.gson.annotations.SerializedName;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * author:wong
 * Date: 2019/5/7
 * Description:
 *
 */
public class ResponseCarHistory extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"code":0,"message":"success","result":[{"gpsTime":"2020-03-22 13:57:01","posType":1,"lat":34.948892124064514,"address":null,"gpsSpeed":51,"direction":310,"lng":118.42589424734845,"mileage":0},{"gpsTime":"2020-03-22 13:57:11","address":null,"gpsSpeed":60,"posType":1,"lng":118.42471954238786,"direction":317,"lat":34.949870242765456,"mileage":0}]}
     * type : null
     * message : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 0
         * message : success
         * result : [{"gpsTime":"2020-03-22 13:57:01","posType":1,"lat":34.948892124064514,"address":null,"gpsSpeed":51,"direction":310,"lng":118.42589424734845,"mileage":0},{"gpsTime":"2020-03-22 13:57:11","address":null,"gpsSpeed":60,"posType":1,"lng":118.42471954238786,"direction":317,"lat":34.949870242765456,"mileage":0}]
         */

        @SerializedName("code")
        private int codeX;
        @SerializedName("message")
        private String messageX;
        private List<ResultBean> result;

        public int getCodeX() {
            return codeX;
        }

        public void setCodeX(int codeX) {
            this.codeX = codeX;
        }

        public String getMessageX() {
            return messageX == null? "" : messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * gpsTime : 2020-03-22 13:57:01
             * posType : 1
             * lat : 34.948892124064514
             * address : null
             * gpsSpeed : 51.0
             * direction : 310
             * lng : 118.42589424734845
             * mileage : 0.0
             */

            private String gpsTime;
            private int posType;
            private double lat;
            private String address;
            private double gpsSpeed;
            private int direction;
            private double lng;
            private double mileage;

            public String getGpsTime() {
                return gpsTime == null ? "暂无" : gpsTime;
            }

            public void setGpsTime(String gpsTime) {
                this.gpsTime = gpsTime == null ? "暂无" : gpsTime;
            }

            public int getPosType() {
                return posType;
            }

            public void setPosType(int posType) {
                this.posType = posType;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public String getAddress() {
                return address == null ? "暂无" : address;
            }

            public void setAddress(String address) {
                this.address = address == null ? "暂无" : address;
            }

            public double getGpsSpeed() {
                return gpsSpeed;
            }

            public void setGpsSpeed(double gpsSpeed) {
                this.gpsSpeed = gpsSpeed;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getMileage() {
                return mileage;
            }

            public void setMileage(double mileage) {
                this.mileage = mileage;
            }
        }
    }
}

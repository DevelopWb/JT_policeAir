package com.juntai.wisdom.dgjxb.bean.history_track;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述 历史轨迹
 * @CreateDate: 2020/4/2 16:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/2 16:58
 */
public class HistoryTrackBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"longitude":118.350456,"latitude":34.978012},{"longitude":118.347583,"latitude":34.975941}]
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
         * longitude : 118.350456
         * latitude : 34.978012
         */
        private String address;//地址
        private String posType;//卫星定位-GPS, 基站定位-LBS, WIFI定位-WIFI, 蓝牙定位-BEACON
        private double longitude;
        private double latitude;
        private String gmtCreate;

        public String getAddress() {
            return address == null ? "暂无" : address;
        }

        public void setAddress(String address) {
            this.address = address == null ? "暂无" : address;
        }

        public String getPosType() {
            return posType == null ? "暂无" : posType;
        }

        public void setPosType(String posType) {
            this.posType = posType == null ? "暂无" : posType;
        }

        public String getGmtCreate() {
            return gmtCreate == null ? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate == null ? "" : gmtCreate;
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

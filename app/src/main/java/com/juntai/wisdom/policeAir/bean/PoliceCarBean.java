package com.juntai.wisdom.policeAir.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述   警车
 * @CreateDate: 2020/4/3 15:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/3 15:02
 */
public class PoliceCarBean extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"imei":"868120161015489","locDesc":null,"lng":118.36360506122294,"deviceName":"鲁Q8Q8Y8（GT200-15489）","speed":"0","hbTime":"2020-04-03 15:00:44","activationFlag":"1","lat":35.07070590658254,"posType":"GPS","powerValue":null,"electQuantity":"","accStatus":null,"powerStatus":null,"status":"1","expireFlag":"1","icon":"other","mileage":"2638783.0963754836","gpsTime":"2020-04-02 20:15:26"},{"expireFlag":"0","powerStatus":null,"mileage":"35813763.43251584","imei":"868120161040552","powerValue":null,"status":"1","gpsTime":"2020-04-03 15:01:07","lat":34.863048809650444,"locDesc":null,"hbTime":"2020-04-03 15:01:09","electQuantity":"","accStatus":null,"icon":"other","deviceName":"鲁QD57111（GT200-40552）","speed":"38","lng":118.24529471534863,"activationFlag":"1","posType":"GPS"},{"electQuantity":"","lat":35.07080519589305,"powerValue":null,"gpsTime":"2020-04-03 10:04:31","mileage":"1061273.4419449698","imei":"868120161048258","locDesc":null,"accStatus":null,"speed":"0","deviceName":"鲁Q6012警（GT200-48258）","status":"1","activationFlag":"1","hbTime":"2020-04-03 14:58:57","powerStatus":null,"expireFlag":"1","icon":"other","lng":118.36365846156666,"posType":"GPS"},{"lng":118.36365851715301,"icon":"other","lat":35.07095637534835,"hbTime":"2020-04-03 14:59:44","accStatus":null,"imei":"868120161061038","deviceName":"鲁Q7709警（GT200-61038）","status":"1","posType":"GPS","powerValue":null,"powerStatus":null,"activationFlag":"1","mileage":"4793590.2436045185","locDesc":null,"expireFlag":"1","electQuantity":"","speed":"0","gpsTime":"2020-04-03 12:00:25"}]
     * type : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * imei : 868120161015489
         * locDesc : null
         * lng : 118.36360506122294
         * deviceName : 鲁Q8Q8Y8（GT200-15489）
         * speed : 0
         * hbTime : 2020-04-03 15:00:44
         * activationFlag : 1
         * lat : 35.07070590658254
         * posType : GPS
         * powerValue : null
         * electQuantity :
         * accStatus : null
         * powerStatus : null
         * status : 1
         * expireFlag : 1
         * icon : other
         * mileage : 2638783.0963754836
         * gpsTime : 2020-04-02 20:15:26
         * "img": "https://www.juntaikeji.com:17002/thumbnail/carImg/9846d2f51ff64a57bcfb513ebbc092ce.jpeg",
         */

        private String imei;
        private String locDesc;
        private double lng;
        private String deviceName;
        private String speed;
        private String hbTime;
        private String activationFlag;
        private double lat;
        private String posType;
        private String powerValue;
        private String electQuantity;
        private String accStatus;
        private String powerStatus;
        @SerializedName("status")
        private String statusX;
        private String expireFlag;
        private String icon;
        private String mileage;
        private String gpsTime;
        private String img;//

        public String getImg() {
            return img == null? "":img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImei() {
            return imei == null ? "" : imei;
        }

        public void setImei(String imei) {
            this.imei = imei == null ? "" : imei;
        }

        public String getLocDesc() {
            return locDesc == null ? "" : locDesc;
        }

        public void setLocDesc(String locDesc) {
            this.locDesc = locDesc == null ? "" : locDesc;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getDeviceName() {
            return deviceName == null ? "" : deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName == null ? "" : deviceName;
        }

        public String getSpeed() {
            return speed == null ? "" : speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed == null ? "" : speed;
        }

        public String getHbTime() {
            return hbTime == null ? "" : hbTime;
        }

        public void setHbTime(String hbTime) {
            this.hbTime = hbTime == null ? "" : hbTime;
        }

        public String getActivationFlag() {
            return activationFlag == null ? "" : activationFlag;
        }

        public void setActivationFlag(String activationFlag) {
            this.activationFlag = activationFlag == null ? "" : activationFlag;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getPosType() {
            return posType == null ? "" : posType;
        }

        public void setPosType(String posType) {
            this.posType = posType == null ? "" : posType;
        }

        public String getPowerValue() {
            return powerValue == null ? "" : powerValue;
        }

        public void setPowerValue(String powerValue) {
            this.powerValue = powerValue == null ? "" : powerValue;
        }

        public String getElectQuantity() {
            return electQuantity == null ? "" : electQuantity;
        }

        public void setElectQuantity(String electQuantity) {
            this.electQuantity = electQuantity == null ? "" : electQuantity;
        }

        public String getAccStatus() {
            return accStatus == null ? "" : accStatus;
        }

        public void setAccStatus(String accStatus) {
            this.accStatus = accStatus == null ? "" : accStatus;
        }

        public String getPowerStatus() {
            return powerStatus == null ? "" : powerStatus;
        }

        public void setPowerStatus(String powerStatus) {
            this.powerStatus = powerStatus == null ? "" : powerStatus;
        }

        public String getStatusX() {
            return statusX == null ? "" : statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX == null ? "" : statusX;
        }

        public String getExpireFlag() {
            return expireFlag == null ? "" : expireFlag;
        }

        public void setExpireFlag(String expireFlag) {
            this.expireFlag = expireFlag == null ? "" : expireFlag;
        }

        public String getIcon() {
            return icon == null ? "" : icon;
        }

        public void setIcon(String icon) {
            this.icon = icon == null ? "" : icon;
        }

        public String getMileage() {
            return mileage == null ? "" : mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage == null ? "" : mileage;
        }

        public String getGpsTime() {
            return gpsTime == null ? "" : gpsTime;
        }

        public void setGpsTime(String gpsTime) {
            this.gpsTime = gpsTime == null ? "" : gpsTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.imei);
            dest.writeString(this.locDesc);
            dest.writeDouble(this.lng);
            dest.writeString(this.deviceName);
            dest.writeString(this.speed);
            dest.writeString(this.hbTime);
            dest.writeString(this.activationFlag);
            dest.writeDouble(this.lat);
            dest.writeString(this.posType);
            dest.writeString(this.powerValue);
            dest.writeString(this.electQuantity);
            dest.writeString(this.accStatus);
            dest.writeString(this.powerStatus);
            dest.writeString(this.statusX);
            dest.writeString(this.expireFlag);
            dest.writeString(this.icon);
            dest.writeString(this.mileage);
            dest.writeString(this.gpsTime);
            dest.writeString(this.img);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.imei = in.readString();
            this.locDesc = in.readString();
            this.lng = in.readDouble();
            this.deviceName = in.readString();
            this.speed = in.readString();
            this.hbTime = in.readString();
            this.activationFlag = in.readString();
            this.lat = in.readDouble();
            this.posType = in.readString();
            this.powerValue = in.readString();
            this.electQuantity = in.readString();
            this.accStatus = in.readString();
            this.powerStatus = in.readString();
            this.statusX = in.readString();
            this.expireFlag = in.readString();
            this.icon = in.readString();
            this.mileage = in.readString();
            this.gpsTime = in.readString();
            this.img = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}

package com.juntai.wisdom.policeAir.bean.stream;

import android.os.Parcel;
import android.os.Parcelable;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/31 13:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/31 13:50
 */
public class StreamCameraBean  extends BaseResult {

    /**
     * error : null
     * data : [{"id":38,"number":"37130201561327011001","address":"永恒华府 能看到河的三岔口","latitude":"35.07212","longitude":"118.366617","name":"永恒华府高空","ezOpen":"http://192.168.124.115:8092/thumbnail/camera_img/68cf8b11c2224596aa558deffd8f33b9.jpeg"},{"id":20,"number":"37130201561327011002","address":"南立杆向南","latitude":"35.071256","longitude":"118.362772","name":"南立杆向南","ezOpen":"http://192.168.124.115:8092/thumbnail/camera_img/50af7fcdc4aa4d0a861da3987ca46100.jpeg"},{"id":5,"number":"37130201561327011008","address":"中国山东省临沂市河东区九曲街道人民大街中段","latitude":"35.090622","longitude":"118.402116","name":"008","ezOpen":"http://192.168.124.115:8092/thumbnail/camera_img/7378a3f686c045d1abdd026e5fd122fa.jpeg"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 38
         * number : 37130201561327011001
         * address : 永恒华府 能看到河的三岔口
         * latitude : 35.07212
         * longitude : 118.366617
         * name : 永恒华府高空
         * ezOpen : http://192.168.124.115:8092/thumbnail/camera_img/68cf8b11c2224596aa558deffd8f33b9.jpeg
         */

        private int id;//监控id
        private String number;//监控编号
        private String address;//地址
        private double latitude;//维度
        private double longitude;//经度
        private String name;//监控名称
        private String ezOpen;//监控封面图片
        private int flag;//标识（0：硬盘录像机;1:摄像头）


        protected DataBean(Parcel in) {
            id = in.readInt();
            number = in.readString();
            address = in.readString();
            latitude = in.readDouble();
            longitude = in.readDouble();
            name = in.readString();
            ezOpen = in.readString();
            flag = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(number);
            dest.writeString(address);
            dest.writeDouble(latitude);
            dest.writeDouble(longitude);
            dest.writeString(name);
            dest.writeString(ezOpen);
            dest.writeInt(flag);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getFlag() {
            return 1;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number == null? "" : number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEzOpen() {
            return ezOpen == null? "" : ezOpen;
        }

        public void setEzOpen(String ezOpen) {
            this.ezOpen = ezOpen;
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

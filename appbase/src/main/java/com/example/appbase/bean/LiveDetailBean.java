package com.example.appbase.bean;

import android.os.Parcel;

import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class LiveDetailBean extends BaseResult {


    /**
     * data : {"id":3,"shopId":1,"userId":101,"liveNumber":"958332","shopName":"临沂市河东区粮油食品有限公司","title":"测试","headPortrait":"https://www.juntaikeji.com:21900/2022-05-23/1653289817732.png","coverImg":"https://www.juntaikeji.com:21900/2022-07-07/1657185853266426.jpeg","viewNumber":0,"shareLiveUrl":"https://www.baidu.com?number=958332","isCollect":-1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements android.os.Parcelable {
        /**
         * id : 3
         * shopId : 1
         * userId : 101
         * liveNumber : 958332
         * shopName : 临沂市河东区粮油食品有限公司
         * title : 测试
         * headPortrait : https://www.juntaikeji.com:21900/2022-05-23/1653289817732.png
         * coverImg : https://www.juntaikeji.com:21900/2022-07-07/1657185853266426.jpeg
         * viewNumber : 0
         * shareLiveUrl : https://www.baidu.com?number=958332
         * isCollect : -1
         */

        private int id;
        private int shopId;
        private int userId;
        private String liveNumber;
        private String shopName;
        private String title;
        private String headPortrait;
        private String coverImg;
        private String rtmpUrl;
        private int viewNumber;
        private String shareLiveUrl;
        private int isCollect;

        public int getId() {
            return id;
        }

        public String getRtmpUrl() {
            return rtmpUrl == null ? "" : rtmpUrl;
        }

        public void setRtmpUrl(String rtmpUrl) {
            this.rtmpUrl = rtmpUrl == null ? "" : rtmpUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getLiveNumber() {
            return liveNumber;
        }

        public void setLiveNumber(String liveNumber) {
            this.liveNumber = liveNumber;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public int getViewNumber() {
            return viewNumber;
        }

        public void setViewNumber(int viewNumber) {
            this.viewNumber = viewNumber;
        }

        public String getShareLiveUrl() {
            return shareLiveUrl;
        }

        public void setShareLiveUrl(String shareLiveUrl) {
            this.shareLiveUrl = shareLiveUrl;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.shopId);
            dest.writeInt(this.userId);
            dest.writeString(this.liveNumber);
            dest.writeString(this.shopName);
            dest.writeString(this.title);
            dest.writeString(this.headPortrait);
            dest.writeString(this.coverImg);
            dest.writeString(this.rtmpUrl);
            dest.writeInt(this.viewNumber);
            dest.writeString(this.shareLiveUrl);
            dest.writeInt(this.isCollect);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.shopId = in.readInt();
            this.userId = in.readInt();
            this.liveNumber = in.readString();
            this.shopName = in.readString();
            this.title = in.readString();
            this.headPortrait = in.readString();
            this.coverImg = in.readString();
            this.rtmpUrl = in.readString();
            this.viewNumber = in.readInt();
            this.shareLiveUrl = in.readString();
            this.isCollect = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.data, flags);
    }

    public LiveDetailBean() {
    }

    protected LiveDetailBean(Parcel in) {
        super(in);
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<LiveDetailBean> CREATOR = new Creator<LiveDetailBean>() {
        @Override
        public LiveDetailBean createFromParcel(Parcel source) {
            return new LiveDetailBean(source);
        }

        @Override
        public LiveDetailBean[] newArray(int size) {
            return new LiveDetailBean[size];
        }
    };
}

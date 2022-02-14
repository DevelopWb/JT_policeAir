package com.juntai.wisdom.dgjxb.bean.task;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述 任务上报
 * @CreateDate: 2020/5/17 16:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/17 16:14
 */
public class TaskSubmitedBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"address":"临沂市河东区人民大街","pictureOneUrl":"/MissionImg/d3a0f8cd60a84019bf02ba0dbcdf5e8b.jpeg","pictureTwoUrl":"/MissionImg/346db992611a46fc93c67f1630640ab9.jpeg","pictureThreeUrl":"/MissionImg/8fe19efbc8fb4253a0bb4f9f3a16dd29.jpeg","videoUrl":"/MissionImg/6a1fdd4288d54100a89dd66acc602ff4.mp4","state":1,"rejectReason":null,"qualityStart":3,"speedStart":3}
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
         * address : 临沂市河东区人民大街
         * pictureOneUrl : /MissionImg/d3a0f8cd60a84019bf02ba0dbcdf5e8b.jpeg
         * pictureTwoUrl : /MissionImg/346db992611a46fc93c67f1630640ab9.jpeg
         * pictureThreeUrl : /MissionImg/8fe19efbc8fb4253a0bb4f9f3a16dd29.jpeg
         * videoUrl : /MissionImg/6a1fdd4288d54100a89dd66acc602ff4.mp4
         * state : 1
         * rejectReason : null
         * qualityStart : 3
         * speedStart : 3
         */

        private int id;//所属任务id
        private String address;//地址
        private String pictureOneUrl;//图片
        private String pictureTwoUrl;//图片
        private String pictureThreeUrl;//图片
        private String videoUrl;//视频
        private int state;//处理状态（0：审核中；1：审核成功；2：审核失败）
        private String  rejectReason;//退回原因
        private int qualityStart;//上传质量分数（1-5分）
        private int speedStart;//上传速度分数（1-5分）

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPictureOneUrl() {
            return pictureOneUrl == null? "" : pictureOneUrl;
        }

        public void setPictureOneUrl(String pictureOneUrl) {
            this.pictureOneUrl = pictureOneUrl;
        }

        public String getPictureTwoUrl() {
            return pictureTwoUrl == null? "" : pictureTwoUrl;
        }

        public void setPictureTwoUrl(String pictureTwoUrl) {
            this.pictureTwoUrl = pictureTwoUrl;
        }

        public String getPictureThreeUrl() {
            return pictureThreeUrl == null? "" : pictureThreeUrl;
        }

        public void setPictureThreeUrl(String pictureThreeUrl) {
            this.pictureThreeUrl = pictureThreeUrl;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getRejectReason() {
            return rejectReason == null? "" : rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public int getQualityStart() {
            return qualityStart;
        }

        public void setQualityStart(int qualityStart) {
            this.qualityStart = qualityStart;
        }

        public int getSpeedStart() {
            return speedStart;
        }

        public void setSpeedStart(int speedStart) {
            this.speedStart = speedStart;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

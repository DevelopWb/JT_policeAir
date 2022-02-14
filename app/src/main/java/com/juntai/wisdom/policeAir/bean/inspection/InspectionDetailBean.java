package com.juntai.wisdom.policeAir.bean.inspection;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;

/**
 * Describe:巡检详情
 * Create by zhangzhenlong
 * 2020-3-31
 * email:954101549@qq.com
 */
public class InspectionDetailBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"realName":"韩国杰","patrolAddress":"临沂市河东区人民大街","patrolTime":"2020-04-23 14:34:12","content":"http://m.ufoto.com.cn/post/7639206","pictureOneUrl":"/patrolImg/b24ae99d9db747a2a9c8daa8c8e5bb95.jpeg","pictureTwoUrl":"/patrolImg/6afedcd723334d84a7814daf81529f37.jpeg","pictureThreeUrl":"/patrolImg/66e41ff886f64b128651137e2da3cf68.jpeg","videoUrl":"/patrolImg/e337a485506644768453f74dd904ca87.mp4"}
     * type : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable {
        /**
         * realName : 韩国杰
         * patrolAddress : 临沂市河东区人民大街
         * patrolTime : 2020-04-23 14:34:12
         * content : http://m.ufoto.com.cn/post/7639206
         * pictureOneUrl : /patrolImg/b24ae99d9db747a2a9c8daa8c8e5bb95.jpeg
         * pictureTwoUrl : /patrolImg/6afedcd723334d84a7814daf81529f37.jpeg
         * pictureThreeUrl : /patrolImg/66e41ff886f64b128651137e2da3cf68.jpeg
         * videoUrl : /patrolImg/e337a485506644768453f74dd904ca87.mp4
         */

        private String name;//巡检点名称
        private String realName;
        private String patrolAddress;
        private String patrolTime;
        private String content;
        private String pictureOneUrl;
        private String pictureTwoUrl;
        private String pictureThreeUrl;
        private String videoUrl;
        private int qualityStart;//上传质量分数（1-5分）
        private int speedStart;//上传速度分数（1-5分）
        private int state;//处理状态（1待处理；2处理中；3已完成；4已退回）
        private String rejectReason;//退回原因

        public String getRealName() {
            return realName == null? "" : realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPatrolAddress() {
            return patrolAddress == null? "" : patrolAddress;
        }

        public void setPatrolAddress(String patrolAddress) {
            this.patrolAddress = patrolAddress;
        }

        public String getPatrolTime() {
            return patrolTime == null? "" : patrolTime;
        }

        public void setPatrolTime(String patrolTime) {
            this.patrolTime = patrolTime;
        }

        public String getContent() {
            return content == null? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}

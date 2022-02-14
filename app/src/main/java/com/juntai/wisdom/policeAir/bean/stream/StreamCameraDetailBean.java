package com.juntai.wisdom.policeAir.bean.stream;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述   摄像头详情
 * @CreateDate: 2020/6/3 8:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:34
 */
public class StreamCameraDetailBean extends BaseResult {
    /**
     * error : null
     * returnValue :
     * msg : null
     * code : null
     * status : 200
     * data : []
     * type : null
     * message : null
     * success : true
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
         * "id": 38,
         *         "name": "这个才是永恒华府",
         *         "number": "37130201561327011001",
         *         "address": "中国山东省临沂市河东区九曲街道人民大街中段",
         *         "isOnline": 1,
         *         "isLike": 1,
         *         "isCollect": 0,
         *         "likeCount": 2,
         *         "commentCount": 0
         */

        private int id;//监控id
        private String name;//监控名称
        private String number;//监控编号
        private String address;//监控地址
        private int isOnline;//是否在线（0离线；1在线）
        private int isLike;//是否点赞, 0未点赞，其他为已点赞id
        private int isCollect;//是否收藏, 0未收藏，其他为已收藏id
        private int likeCount;//点赞总数
        private int commentCount;//评论总数
        private String ezOpen;//监控封面图片
        private String shareUrl;//分享链接
        private String banner;//摄像头广告
        private int viewNumber;//访问量

        public String getBanner() {
            return banner == null ? "" : banner;
        }

        public void setBanner(String banner) {
            this.banner = banner == null ? "" : banner;
        }

        public int getViewNumber() {
            return viewNumber;
        }

        public void setViewNumber(int viewNumber) {
            this.viewNumber = viewNumber;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getEzOpen() {
            return ezOpen;
        }

        public void setEzOpen(String ezOpen) {
            this.ezOpen = ezOpen;
        }

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

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}

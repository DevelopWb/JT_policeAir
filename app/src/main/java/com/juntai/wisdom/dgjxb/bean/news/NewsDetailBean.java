package com.juntai.wisdom.dgjxb.bean.news;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:资讯详情（图文、视频）
 * Create by zhangzhenlong
 * 2020-8-13
 * email:954101549@qq.com
 */
public class NewsDetailBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"id":8,"typeId":1,"title":"测试","content":"视频测试","address":"临沂市河东区九曲街道人民大街","isCollect":0,"isLike":0,"commentCount":0,"likeCount":0,"shareCount":0,"browseNum":3,"userId":101,"headPortrait":"/head_img/fc5cbd94c0b54936a63b853156004f49.jpeg","nickName":"铁人王进喜","shareUrl":"https://kb167.cn/dongGuanjiejing/share.html?id=8","gmtCreate":"2020-08-01 15:19:48"}
     * type : null
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
         * id : 8
         * typeId : 1
         * title : 测试
         * content : 视频测试
         * address : 临沂市河东区九曲街道人民大街
         * isCollect : 0
         * isLike : 0
         * commentCount : 0
         * likeCount : 0
         * shareCount : 0
         * browseNum : 3
         * userId : 101
         * headPortrait : /head_img/fc5cbd94c0b54936a63b853156004f49.jpeg
         * nickname : 铁人王进喜
         * shareUrl : https://kb167.cn/dongGuanjiejing/share.html?id=8
         * gmtCreate : 2020-08-01 15:19:48
         * "coverUrl": "https://image.juntaikeji.com/2020-08-01/f54ac7ebcf8b4d008ae1837d8a38e7cf.jpg",
         */

        private int id;//资讯id
        private String informationId;//资讯文件表外键
        private int typeId;//类型（1：视频；2：图片）
        private String title;//资讯标题
        private String content;//资讯内容
        private String videoUrl;//视频地址
        private String coverUrl;//视频封面图地址
        private String address;//资讯发布地址
        private double longitude;//经度
        private double latitude;//纬度
        private int isCollect;//是否被收藏（0:否）（其他:id主键）
        private int isLike;//是否点赞（0:否）（其他:id主键）
        private int isFollow;//0:未关注；1:已关注；2:互相关注
        private int commentCount;//评论总数
        private int likeCount;//点赞总数
        private int shareCount;//分享转发数量
        private int browseNum;//浏览数
        private int userId;//发布者id
        private String headPortrait;//发布者头像
        private String nickname;//发布者昵称；姓名
        private String shareUrl;//分享链接
        private String gmtCreate;//发布时间
        private List<String> fileList;


        public String getInformationId() {
            return informationId  == null? "" : informationId;
        }

        public void setInformationId(String informationId) {
            this.informationId = informationId;
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

        public String getCoverUrl() {
            return coverUrl == null? "" : coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTitle() {
            return title == null? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content == null? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public int getBrowseNum() {
            return browseNum;
        }

        public void setBrowseNum(int browseNum) {
            this.browseNum = browseNum;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }


        public String getShareUrl() {
            return shareUrl == null? "" : shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getNickname() {
            return nickname == null? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<String> getFileList() {
            return fileList;
        }

        public void setFileList(List<String> fileList) {
            this.fileList = fileList;
        }
    }
}

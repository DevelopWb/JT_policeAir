package com.juntai.wisdom.policeAir.bean.news;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe:资讯个人主页用户信息
 * Create by zhangzhenlong
 * 2020-8-16
 * email:954101549@qq.com
 */
public class NewsPersonalHomePageInfo extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"id":101,"nickName":"铁人王进喜","headPortrait":"/head_img/fc5cbd94c0b54936a63b853156004f49.jpeg","articleCount":4,"followCount":5,"fansCount":5,"likeCount":0}
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
         * "id": 9,
         *                  "nickName": "铁人王进喜",
         *                  "account": "18669505929",
         *                  "headPortrait": "https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
         *                  "articleCount": 0,
         *                  "followCount": 1,
         *                  "fansCount": 2,
         *                  "likeCount": 0,
         *                  "isFollow": 0,
         *                  "shareLink": "https://www.dgjpcs.cn/share/information.html?userId=9"
         */

        private int id;//用户id
        private String nickName;//昵称
        private String headPortrait;//头像
        private int articleCount;//发表的资讯数量
        private int followCount;//关注
        private int fansCount;//粉丝
        private int likeCount;//点赞
        private String shareLink;//分享链接
        private int isFollow;//0:未关注；1:已关注；2:互相关注
        private String account;//


        public String getShareLink() {
            return shareLink == null? "" : shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName == null? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public int getArticleCount() {
            return articleCount;
        }

        public void setArticleCount(int articleCount) {
            this.articleCount = articleCount;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public String getAccount() {
            return account == null? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}

package com.juntai.wisdom.dgjxb.bean.news;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:资讯列表
 * Create by zhangzhenlong
 * 2020-7-30
 * email:954101549@qq.com
 */
public class NewsListBean extends BaseResult {
    public static final int TYPE_ZERO = 0;
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"datas":[{"id":8,"informationId":"0","typeId":1,"title":"测试","videoUrl":"https://image.juntaikeji.com/2020-08-01/f54ac7ebcf8b4d008ae1837d8a38e7cf.mp4","nickName":"铁人王进喜","commentCount":0,"readNumber":1,"gmtCreate":1596266388000,"releaseTime":"1小时前","fileList":[]},{"id":7,"informationId":"1596243499","typeId":2,"title":"测试","videoUrl":null,"nickName":"铁人王进喜","commentCount":1,"readNumber":4,"gmtCreate":1594435894000,"releaseTime":"21天前 ","fileList":[{"url":"https://image.juntaikeji.com/2020-08-01/00a1496b44dc44e28c62f26e020afca2.jpg"},{"url":"https://image.juntaikeji.com/2020-08-01/da1b43df0dfc481891c3bb8c625bbd56.jpg"}]}],"total":2,"listSize":2,"pageCount":1}
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
         * datas : [{"id":8,"informationId":"0","typeId":1,"title":"测试","videoUrl":"https://image.juntaikeji.com/2020-08-01/f54ac7ebcf8b4d008ae1837d8a38e7cf.mp4","nickName":"铁人王进喜","commentCount":0,"readNumber":1,"gmtCreate":1596266388000,"releaseTime":"1小时前","fileList":[]},{"id":7,"informationId":"1596243499","typeId":2,"title":"测试","videoUrl":null,"nickName":"铁人王进喜","commentCount":1,"readNumber":4,"gmtCreate":1594435894000,"releaseTime":"21天前 ","fileList":[{"url":"https://image.juntaikeji.com/2020-08-01/00a1496b44dc44e28c62f26e020afca2.jpg"},{"url":"https://image.juntaikeji.com/2020-08-01/da1b43df0dfc481891c3bb8c625bbd56.jpg"}]}]
         * total : 2
         * listSize : 2
         * pageCount : 1
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas == null? new ArrayList<>():datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements Serializable {
            /**
             * id : 8
             * informationId : 0
             * typeId : 1
             * title : 测试
             * content
             * videoUrl : https://image.juntaikeji.com/2020-08-01/f54ac7ebcf8b4d008ae1837d8a38e7cf.mp4
             * "coverUrl": "https://image.juntaikeji.com/2020-08-01/f54ac7ebcf8b4d008ae1837d8a38e7cf.jpg",
             * nickName : 铁人王进喜
             * commentCount : 0
             * readNumber : 1
             * gmtCreate : 1596266388000
             * releaseTime : 1小时前
             * fileList : []
             */

            private int id;//资讯id
            private String informationId;//资讯文件表外键
            private int typeId;//类型（1：视频；2：图片）
            private String title;//标题
            private String videoUrl;//视频地址
            private String nickname;//昵称
            private int readNumber;//阅读数
            private int commentCount;//评论总数
            private String gmtCreate;//时间
            private String releaseTime;//发布时间
            private List<FileBean> fileList;
            private boolean isLooked;//是否已查看
            private String coverUrl;//视频封面图片
            private String content;//内容
            private String shareUrl;
            private int isCollect;//是否被收藏（0:否）（其他:id主键）

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }

            public String getShareUrl() {
                return shareUrl == null? "" : shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getContent() {
                return content == null? "" : content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCoverUrl() {
                return coverUrl == null? "" : coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
            }

            public boolean isLooked() {
                return isLooked;
            }

            public void setLooked(boolean looked) {
                isLooked = looked;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getInformationId() {
                return informationId == null? "" : informationId;
            }

            public void setInformationId(String informationId) {
                this.informationId = informationId;
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

            public String getVideoUrl() {
                return videoUrl == null? "" : videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public String getNickname() {
                return nickname == null? "" : nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public int getReadNumber() {
                return readNumber;
            }

            public void setReadNumber(int readNumber) {
                this.readNumber = readNumber;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getReleaseTime() {
                return releaseTime == null? "" : releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public List<FileBean> getFileList() {
                return fileList== null? new ArrayList<>():fileList;
            }

            public void setFileList(List<FileBean> fileList) {
                this.fileList = fileList;
            }

            public static class FileBean{
                private String fileUrl;// "https://image.juntaikeji.com/2020-08-01/00a1496b44dc44e28c62f26e020afca2.jpg"

                public String getFileUrl() {
                    return fileUrl == null? "" : fileUrl;
                }

                public void setFileUrl(String fileUrl) {
                    this.fileUrl = fileUrl;
                }
            }
        }
    }
}

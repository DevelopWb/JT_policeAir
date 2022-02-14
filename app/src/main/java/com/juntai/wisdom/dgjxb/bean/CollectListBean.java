package com.juntai.wisdom.dgjxb.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:收藏(分享)列表(咨询)
 * Create by zhangzhenlong
 * 2020-3-21
 * email:954101549@qq.com
 */
public class CollectListBean extends BaseResult {
    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "datas": [
     *         ],
     *         "total": 1,
     *         "listSize": 1,
     *         "pageCount": 1
     *     },
     *     "type": null,
     *     "message": "成功",
     *     "success": true
     * }
     */

    private DataBean data;

    public DataBean getData() {
        if (data == null){
            data = new DataBean();
        }
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private List<CollectBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<CollectBean> getDatas() {
            if (datas == null){
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<CollectBean> datas) {
            this.datas = datas;
        }

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

        public static class CollectBean implements Serializable {
            private int id;//ID
            private String name;//标题
            private String address;//
            private String url;//缩略图
            private boolean checked;
            private int collectId;//收藏表id
            private int shareId;//分型表id
            private int type;////功能，8：资讯,0:监控
            private int typeId;//资讯 1视频2图文
            private String title;//资讯标题
            private String gmtCreate;//资讯发布时间
            private int commentCount;//资讯评论数
            private String coverUrl;//视频资讯图片
            private String number;//监控用

            public String getNumber() {
                return number == null? "" : number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getCoverUrl() {
                return coverUrl == null? "" : coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public String getAddress() {
                return address == null? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUrl() {
                return url == null? "":url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public int getCollectId() {
                return collectId;
            }

            public void setCollectId(int collectId) {
                this.collectId = collectId;
            }

            public int getShareId() {
                return shareId;
            }

            public void setShareId(int shareId) {
                this.shareId = shareId;
            }

            public String getTitle() {
                return title == null? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }
        }
    }

}

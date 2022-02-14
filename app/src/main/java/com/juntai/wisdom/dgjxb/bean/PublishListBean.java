package com.juntai.wisdom.dgjxb.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:我的发布列表
 * Create by zhangzhenlong
 * 2020-3-25
 * email:954101549@qq.com
 */
public class PublishListBean extends BaseResult {

    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "datas": [
     *             {
     *                 "id": 1,
     *                 "title": "临沂市河东区",
     *                 "time": "2020-03-21 14:26:37",
     *                 "content": null,
     *                 "picture": "/patrolImg/f3b9b7dc49bb49c6ac7c35e5fb78be03.jpeg"
     *             }
     *         ],
     *         "total": 1,
     *         "listSize": 1,
     *         "pageCount": 1
     *     },
     *     "type": null,
     *     "message": null,
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

    public static class DataBean implements Serializable {
        private List<PublishBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<PublishBean> getDatas() {
            if (datas == null){
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<PublishBean> datas) {
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

        public static class PublishBean implements Serializable {
            /**
             * 场所
             *  "id": 1223,
             *                 "title": "刘家园二区9号楼",
             *                 "time": "2020-07-21 10:42:54",
             *                 "content": "刘家园二区",
             *                 "picture": "http://61.156.157.132:32288/crm_client_logo/86f15b5933f34443bfca40c6674e5f63.jpeg",
             *                 "state": 1
             */

            private int id;//主键
            private String title;//标题；地址
            private String time;//时间
            private String content;//内容
            private String picture;//图片
            private int state;//巡检状态（1待处理，2处理中，3审核通过，4审核退回）
            private boolean checked;
            private int typeId;//资讯 1视频2图文
            private int commentCount;//评论总数

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title == null? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time == null? "" : time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content == null? "" : content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicture() {
                return picture == null? "" : picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }
        }
    }
}

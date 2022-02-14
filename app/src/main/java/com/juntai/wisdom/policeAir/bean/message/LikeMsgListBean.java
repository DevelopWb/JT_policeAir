package com.juntai.wisdom.policeAir.bean.message;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:评论点赞消息列表/系统通知
 * Create by zhangzhenlong
 * 2020-3-25
 * email:954101549@qq.com
 */
public class LikeMsgListBean extends BaseResult {
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
        private List<MessageBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<MessageBean> getDatas() {
            if (datas == null){
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<MessageBean> datas) {
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

        public static class MessageBean implements Serializable {
            /**
             * 摄像头
             *"id": 74,
             *                 "logId": 1252,
             *                 "title": "上报任务审核",
             *                 "content": "上报任务审核通过！",
             *                 "contentId": null,
             *                 "contentType": 5,
             *                 "gmtCreate": "2020-05-17 15:26:56",
             *                 "headPortrait": "/notice_img/default.jpg",
             *                 "isRead": 0
             *                 评论点赞
             *                 "id": 271,
             *                 "title": "张振隆",
             *                 "content": "站的高，看得远",
             *                 "commentedId": 38,
             *                 "gmtCreate": "2020-11-02 15:44:56",
             *                 "headPortrait": "https://www.juntaikeji.com:17002/head_img/fb36b52fc93c4b3eac69187f23d78d13.jpeg",
             *                 "isRead": 1,
             *                 "fId": 270
             */

            private int id;//ID
            private String title;//标题
            private String content;//内容
            private String gmtCreate;//时间
            private String headPortrait;//缩略图
            private int isRead;//是否已读,0已读，1未读
            private int contentId;//详情id
            private int contentType;//消息对应类型（1：案件通知；2：巡检通知；3：实名认证通知；4：户籍业务通知；5：任务通知）
            private int logId;//个人消息独立标识

            private int commentedId;//commentedId
            private int typeId;
            private int fId;//

            public int getLogId() {
                return logId;
            }

            public void setLogId(int logId) {
                this.logId = logId;
            }

            public int getContentId() {
                return contentId;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public int getContentType() {
                return contentType;
            }

            public void setContentType(int contentType) {
                this.contentType = contentType;
            }

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
                return title == null? "":title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content == null? "":content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "":gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getHeadPortrait() {
                return headPortrait == null? "" : headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public int getCommentedId() {
                return commentedId;
            }

            public void setCommentedId(int commentedId) {
                this.commentedId = commentedId;
            }

            public int getfId() {
                return fId;
            }

            public void setfId(int fId) {
                this.fId = fId;
            }
        }
    }
}

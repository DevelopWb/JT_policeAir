package com.juntai.wisdom.policeAir.bean.message;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe:通知消息详情
 * Create by zhangzhenlong
 * 2020-3-27
 * email:954101549@qq.com
 */
public class InformDetailBean extends BaseResult {

    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "id": 1,
     *         "title": "APP上线通知",
     *         "content": "为了给用户更好的使用体验，我们将3月1号凌晨1点进行系统维护更新升级，如遇系统异常，请联系客服！",
     *         "gmtCreate": "2020-02-29 10:00:44",
     *         publisher
     *         "viewNumber": 3
     *
     *     },
     *     "type": null,
     *     "message": null,
     *     "success": true
     * }
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
         * id : 1
         * title : APP上线通知
         * content : 为了给用户更好的使用体验，我们将3月1号凌晨1点进行系统维护更新升级，如遇系统异常，请联系客服！
         * gmtCreate : 2020-02-29 10:00:44
         * viewNumber : 3
         */

        private int id;
        private String title;
        private String content;
        private String gmtCreate;
        private int viewNumber;
        private String publisher;

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

        public String getContent() {
            return content == null? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public int getViewNumber() {
            return viewNumber;
        }

        public void setViewNumber(int viewNumber) {
            this.viewNumber = viewNumber;
        }

        public String getPublisher() {
            return publisher == null? "" : publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }
    }
}

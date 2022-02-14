package com.juntai.wisdom.policeAir.bean.key_personnel;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * Describe: 走访记录列表
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class InterviewListBean extends BaseResult {

    /**
     * error : null
     * data : {"datas":[{"id":5,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:20:34"},{"id":4,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:19:04"},{"id":3,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:16:41"}],"total":4,"listSize":4,"pageCount":1}
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
         * datas : [{"id":5,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:20:34"},{"id":4,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:19:04"},{"id":3,"keyPersonnelId":57,"logUserId":209,"logUser":"高雷","gmtCreate":"2020-07-13 20:16:41"}]
         * total : 4
         * listSize : 4
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
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 5
             * keyPersonnelId : 57
             * logUserId : 209
             * logUser : 高雷
             * gmtCreate : 2020-07-13 20:20:34
             */

            private int id;
            private int keyPersonnelId;
            private int logUserId;
            private String logUser;
            private String gmtCreate;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getKeyPersonnelId() {
                return keyPersonnelId;
            }

            public void setKeyPersonnelId(int keyPersonnelId) {
                this.keyPersonnelId = keyPersonnelId;
            }

            public int getLogUserId() {
                return logUserId;
            }

            public void setLogUserId(int logUserId) {
                this.logUserId = logUserId;
            }

            public String getLogUser() {
                return logUser == null? "" : logUser;
            }

            public void setLogUser(String logUser) {
                this.logUser = logUser;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }
        }
    }
}

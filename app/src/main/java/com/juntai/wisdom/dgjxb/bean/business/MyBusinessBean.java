package com.juntai.wisdom.dgjxb.bean.business;

import com.google.gson.annotations.SerializedName;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/22 10:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/22 10:43
 */
public class MyBusinessBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"datas":[{"businessId":47,"title":"出生随父母落户","gmtCreate":"2020-05-22 09:59:27","status":0,"auditOpinion":"正在审核中","typeId":1},{"businessId":42,"title":"出生随父母落户","gmtCreate":"2020-05-21 17:22:15","status":1,"auditOpinion":"审核通过","typeId":1}],"total":2,"listSize":2,"pageCount":1}
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
         * datas : [{"businessId":47,"title":"出生随父母落户","gmtCreate":"2020-05-22 09:59:27","status":0,"auditOpinion":"正在审核中","typeId":1},{"businessId":42,"title":"出生随父母落户","gmtCreate":"2020-05-21 17:22:15","status":1,"auditOpinion":"审核通过","typeId":1}]
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
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * businessId : 47
             * title : 出生随父母落户
             * gmtCreate : 2020-05-22 09:59:27
             * status : 0
             * auditOpinion : 正在审核中
             * typeId : 1
             */

            private int businessId;//业务id
            private String title;//业务名称
            private String gmtCreate;//办理时间
            @SerializedName("status")
            private int statusX;//审批状态（0：审核中）（1：审核通过）（2：审核失败）
            private String auditOpinion;//反馈信息
            private int typeId;//类型id（1:户籍业务；2:调解室业务）
            private int categoryId;

            public int getBusinessId() {
                return businessId;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
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

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public String getAuditOpinion() {
                return auditOpinion == null? "" : auditOpinion;
            }

            public void setAuditOpinion(String auditOpinion) {
                this.auditOpinion = auditOpinion;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }
        }
    }
}

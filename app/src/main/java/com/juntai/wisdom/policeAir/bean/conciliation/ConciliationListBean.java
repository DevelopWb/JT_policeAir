package com.juntai.wisdom.policeAir.bean.conciliation;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:调解列表
 * Create by zhangzhenlong
 * 2020-5-23
 * email:954101549@qq.com
 */
public class ConciliationListBean extends BaseResult {
    /**
     * {
     * "error": null,
     * "returnValue": null,
     * "msg": null,
     * "code": null,
     * "status": 200,
     * "data": {
     * "datas": [
     * {
     * "id": 30,
     * "introduction": "您破功",
     * "gmtCreate": "2020-05-22 17:22:53",
     * "state": 1,
     * "fileUrl": "https://image.juntaikeji.com/2020-05-22/f53c712f47f74e9aa623aa7aacbf395f.png"
     * }
     * ],
     * "total": 3,
     * "listSize": 3,
     * "pageCount": 1
     * },
     * "type": null,
     * "message": null,
     * "success": true
     * }
     */
    private DataBean data;

    public DataBean getData() {
        if (data == null) {
            data = new DataBean();
        }
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private List<ConciliationItemBean> datas;
        private int total;// 1,
        private int listSize;//1,
        private int pageCount;//1

        public List<ConciliationItemBean> getDatas() {
            if (datas == null) {
                datas = new ArrayList<>();
            }
            return datas;
        }

        public void setDatas(List<ConciliationItemBean> datas) {
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

        public static class ConciliationItemBean implements Serializable {
            /**
             * "id": 74,
             *             "caseNumber": "459415",
             *             "introduction": "发送到放松放松",
             *             "gmtCreate": "2020-09-30 17:48:46",
             *             "type": 1,
             *             "agreement": 1
             */

            private int id;//案件ID
            private String caseNumber;//案件编号
            private String introduction;//简介
            private String gmtCreate;//申请时间
            private int type;//调解最终类型（0：未受理；1：已受理；2：二次调解；3：已完结；4：完结转为案件；5：审核失败）
            private int agreement;//是否生成了调解协议书（1已生成；0未生成）

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroduction() {
                return introduction == null? "" : introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public String getGmtCreate() {
                return gmtCreate == null? "" : gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAgreement() {
                return agreement;
            }

            public void setAgreement(int agreement) {
                this.agreement = agreement;
            }

            public String getCaseNumber() {
                return caseNumber == null? "" : caseNumber;
            }

            public void setCaseNumber(String caseNumber) {
                this.caseNumber = caseNumber;
            }
        }
    }
}

package com.juntai.wisdom.policeAir.bean.inspection;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe:扫码巡检，根据id获取巡检点信息
 * Create by zhangzhenlong
 * 2020-9-3
 * email:954101549@qq.com
 */
public class InspectionForScanBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"clientId":40,"name":"新华书店"}
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
         * clientId : 40
         * name : 新华书店
         */

        private int clientId;
        private String name;

        public int getClientId() {
            return clientId;
        }

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

package com.juntai.wisdom.policeAir.bean.conciliation;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:单位列表
 * Create by zhangzhenlong
 * 2020-7-15
 * email:954101549@qq.com
 */
public class UnitListBean extends BaseResult {

    /**
     * error : null
     * data : [{"id":1,"name":"临沂市公安局兰山分局东关街派出所","remarks":"东关街派出所","phone":"1345"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable, IPickerViewData {
        /**
         * id : 1
         * name : 临沂市公安局兰山分局东关街派出所
         * remarks : 东关街派出所
         * phone : 1345
         */

        private int id;
        private String name;
        private String remarks;//备注
        private String phone;
        private String cityNumber;

        public String getCityNumber() {
            return cityNumber == null? "" : cityNumber;
        }

        public void setCityNumber(String cityNumber) {
            this.cityNumber = cityNumber;
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

        public String getRemarks() {
            return remarks == null? "" : remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getPhone() {
            return phone == null? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String getPickerViewText() {
            return name == null? "" : name;
        }
    }
}

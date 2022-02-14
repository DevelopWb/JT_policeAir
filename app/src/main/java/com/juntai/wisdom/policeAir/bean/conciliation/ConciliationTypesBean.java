package com.juntai.wisdom.policeAir.bean.conciliation;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * Describe:调解类型
 * Create by zhangzhenlong
 * 2020-7-20
 * email:954101549@qq.com
 */
public class ConciliationTypesBean extends BaseResult {

    /**
     * error : null
     * data : [{"id":3,"name":"家庭纠纷"}]
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
         * id : 3
         * name : 家庭纠纷
         */

        private int id;
        private String name;

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

        @Override
        public String getPickerViewText() {
            return name == null? "" : name;
        }
    }
}

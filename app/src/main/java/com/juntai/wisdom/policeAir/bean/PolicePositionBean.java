package com.juntai.wisdom.policeAir.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  警察职务
 * @CreateDate: 2020/3/19 15:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/19 15:52
 */
public class PolicePositionBean extends BaseResult {

    /**
     * error : null
     * data : [{
     *             "id": 7,
     *             "name": "群众"
     *         }]
     * msg : null
     * code : 200
     * type : null
     * success : true
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * id : 1
         * postName : 领导
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
            return getName();
        }
    }
}

package com.juntai.wisdom.policeAir.bean.weather;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:责任网格
 * Create by zhangzhenlong
 * 2020-7-27
 * email:954101549@qq.com
 */
public class PoliceGriddingBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":1,"name":"德兴雅苑"},{"id":2,"name":"泰和花园"}]
     * type : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * id : 1
         * name : 德兴雅苑
         */

        private int id;
        private String name;

        public DataBean(int id, String name) {
            this.id = id;
            this.name = name;
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

        @Override
        public String getPickerViewText() {
            return name == null? "" : name;
        }
    }
}

package com.juntai.wisdom.dgjxb.bean.site;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;


/**
 * Describe:单位类型
 * Create by zhangzhenlong
 * 2020-7-13
 * email:954101549@qq.com
 */
public class SiteTypeBean extends BaseResult {

    /**
     * data : [{"id":1,"name":"公司"},{"id":2,"name":"商场"},{"id":3,"name":"超市"},{"id":4,"name":"连锁店"},{"id":5,"name":"专营店"},{"id":6,"name":"便利店"},{"id":7,"name":"百货店"},{"id":8,"name":"食杂店"},{"id":9,"name":"服装零售"}]
     * msg : null
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
         * name : 公司
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

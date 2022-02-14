package com.juntai.wisdom.dgjxb.bean.site;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * Describe:客户来源
 * Create by zhangzhenlong
 * 2020-7-13
 * email:954101549@qq.com
 */
public class CustomerSourceBean extends BaseResult {

    /**
     * data : [{"id":1,"name":"注册会员"},{"id":2,"name":"朋友介绍"},{"id":3,"name":"手工录入"},{"id":4,"name":"失败客户"},{"id":5,"name":"行业展会"},{"id":6,"name":"客户介绍"},{"id":7,"name":"电子商务"},{"id":8,"name":"合作伙伴"},{"id":9,"name":"广告"},{"id":10,"name":"专员引介"},{"id":11,"name":"市场活动"},{"id":12,"name":"公共关系"},{"id":13,"name":"口头传达"},{"id":14,"name":"研讨会"},{"id":15,"name":"其他"}]
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
         * name : 注册会员
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

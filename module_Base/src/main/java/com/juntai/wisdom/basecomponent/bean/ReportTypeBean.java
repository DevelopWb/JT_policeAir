package com.juntai.wisdom.basecomponent.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/12/26 16:54
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/26 16:54
 */
public class ReportTypeBean extends BaseResult {


    /**
     * error : null
     * data : [{"id":1,"name":"广告内容"},{"id":2,"name":"搬运抄袭"},{"id":3,"name":"不友善内容"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 广告内容
         */

        private int id;
        private String name;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

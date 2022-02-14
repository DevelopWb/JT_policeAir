package com.juntai.wisdom.dgjxb.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  辖区
 * @CreateDate: 2020/3/19 15:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/19 15:56
 */
public class PoliceAreaBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":2,"regionName":"兰山区"},{"id":3,"regionName":"河东区"},{"id":4,"regionName":"罗庄区"},{"id":8,"regionName":"平邑县"},{"id":9,"regionName":"费县"},{"id":10,"regionName":"苍山县"},{"id":11,"regionName":"郯城县"},{"id":12,"regionName":"临沭县"},{"id":13,"regionName":"莒南县"},{"id":14,"regionName":"沂南县"},{"id":15,"regionName":"蒙阴县"},{"id":16,"regionName":"沂水县"}]
     * type : null
     * message : 成功
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
         * id : 2
         * regionName : 兰山区
         */

        private int id;
        private String regionName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegionName() {
            return regionName == null? "" : regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        @Override
        public String getPickerViewText() {
            return getRegionName();
        }
    }
}

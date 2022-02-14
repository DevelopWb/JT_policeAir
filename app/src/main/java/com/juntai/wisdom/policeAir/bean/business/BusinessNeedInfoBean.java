package com.juntai.wisdom.policeAir.bean.business;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  业务材料信息
 * @CreateDate: 2020/5/21 11:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/21 11:17
 */
public class BusinessNeedInfoBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"infoId":1,"declareId":14,"name":"结婚证","examplePicture":"/declare_materials/timg.jpg"},{"infoId":2,"declareId":15,"name":"法律文书或协议书","examplePicture":null}]
     * type : null
     * message : null
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
         * infoId : 1
         * declareId : 14
         * name : 结婚证
         * examplePicture : /declare_materials/timg.jpg
         */

        private int infoId;
        private int declareId;
        private String name;
        private String examplePicture;

        public int getInfoId() {
            return infoId;
        }

        public void setInfoId(int infoId) {
            this.infoId = infoId;
        }

        public int getDeclareId() {
            return declareId;
        }

        public void setDeclareId(int declareId) {
            this.declareId = declareId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExamplePicture() {
            return examplePicture == null? "" : examplePicture;
        }

        public void setExamplePicture(String examplePicture) {
            this.examplePicture = examplePicture;
        }
    }
}

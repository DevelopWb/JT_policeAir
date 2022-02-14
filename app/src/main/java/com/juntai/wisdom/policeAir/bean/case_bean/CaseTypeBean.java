package com.juntai.wisdom.policeAir.bean.case_bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @aouther tobato  案件类型
 * @description 描述
 * @date 2020/3/25 13:57
 */
public class CaseTypeBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":28,"cateName":"土地","caseSmallType":[{"id":37,"cateName":"非法占地建设"},{"id":38,"cateName":"非法转让土地"},{"id":39,"cateName":"破坏耕地"},{"id":40,"cateName":"压占土地"}]},{"id":29,"cateName":"矿产","caseSmallType":[{"id":41,"cateName":"非法开采矿产"}]},{"id":30,"cateName":"林业","caseSmallType":[]},{"id":32,"cateName":"规划","caseSmallType":[]},{"id":33,"cateName":"市容","caseSmallType":[{"id":42,"cateName":"店外经营"},{"id":43,"cateName":"占道经营"}]},{"id":34,"cateName":"环保","caseSmallType":[]},{"id":35,"cateName":"住建房管","caseSmallType":[{"id":44,"cateName":"违建"}]},{"id":36,"cateName":"文化","caseSmallType":[]}]
     * type : null
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
         * id : 28
         * cateName : 土地
         * caseSmallType : [{"id":37,"cateName":"非法占地建设"},{"id":38,"cateName":"非法转让土地"},{"id":39,"cateName":"破坏耕地"},{"id":40,"cateName":"压占土地"}]
         */

        private int id;
        private String cateName;
        private List<CaseSmallTypeBean> caseSmallType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCateName() {
            return cateName == null? "" : cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public List<CaseSmallTypeBean> getCaseSmallType() {
            return caseSmallType;
        }

        public void setCaseSmallType(List<CaseSmallTypeBean> caseSmallType) {
            this.caseSmallType = caseSmallType;
        }

        @Override
        public String getPickerViewText() {
            return cateName == null? "" : cateName;
        }

        public static class CaseSmallTypeBean implements IPickerViewData{
            /**
             * id : 37
             * cateName : 非法占地建设
             */

            private int id;
            private String cateName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCateName() {
                return cateName == null ? "" : cateName;
            }

            public void setCateName(String cateName) {
                this.cateName = cateName == null ? "" : cateName;
            }

            @Override
            public String getPickerViewText() {
                return cateName;
            }
        }
    }
}

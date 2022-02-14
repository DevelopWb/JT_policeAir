package com.juntai.wisdom.policeAir.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  警察部门
 * @CreateDate: 2020/3/19 15:55
 * @UpdateUser: 更新者 zhangzhenlong
 * @UpdateDate: 2020/3/19 15:55
 */
public class PoliceBranchBean extends BaseResult {
    /**
     * error : null
     * data : [{"departmentId":1,"name":"东关街派出所","provinceCode":"370000","provinceName":"山东省","cityCode":"371300","cityName":"临沂市","areaCode":"371302","areaName":"兰山区","streetCode":"371302003","streetName":"金雀山街道","departmentBranch":[{"id":1,"name":"所领导"},{"id":2,"name":"一警区"}]},{"departmentId":2,"name":"五里堡派出所","provinceCode":"370000","provinceName":"山东省","cityCode":"371300","cityName":"临沂市","areaCode":"371302","areaName":"兰山区","streetCode":"371302002","streetName":"银雀山街道","departmentBranch":[{"id":31,"name":"信息采集"}]}]
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
         * departmentId : 1
         * name : 东关街派出所
         * provinceCode : 370000
         * provinceName : 山东省
         * cityCode : 371300
         * cityName : 临沂市
         * areaCode : 371302
         * areaName : 兰山区
         * streetCode : 371302003
         * streetName : 金雀山街道
         * departmentBranch : [{"id":1,"name":"所领导"},{"id":2,"name":"一警区"}]
         */

        private int departmentId;//部门id
        private String name;//部门名称
        private String provinceCode;//省代码
        private String provinceName;//省名称
        private String cityCode;//市代码
        private String cityName;//市名称
        private String areaCode;//县区代码
        private String areaName;//县区名称
        private String streetCode;//街道代码
        private String streetName;//街道名称
        private List<ChildDepartmentBean> departmentBranch;//二级部门列表

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvinceCode() {
            return provinceCode == null? "" : provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvinceName() {
            return provinceName == null? "" : provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityCode() {
            return cityCode == null? "" : cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName == null? "" : cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaCode() {
            return areaCode == null? "" : areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName == null? "" : areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getStreetCode() {
            return streetCode == null? "" : streetCode;
        }

        public void setStreetCode(String streetCode) {
            this.streetCode = streetCode;
        }

        public String getStreetName() {
            return streetName == null? "" : streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public List<ChildDepartmentBean> getDepartmentBranch() {
            return departmentBranch;
        }

        public void setDepartmentBranch(List<ChildDepartmentBean> departmentBranch) {
            this.departmentBranch = departmentBranch;
        }

        @Override
        public String getPickerViewText() {
            return getName();
        }

        public static class ChildDepartmentBean implements IPickerViewData{
            /**
             * id : 1
             * name : 所领导
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
}

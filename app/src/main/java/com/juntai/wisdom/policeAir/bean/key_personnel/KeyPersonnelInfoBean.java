package com.juntai.wisdom.policeAir.bean.key_personnel;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe: 重点人员详情
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class KeyPersonnelInfoBean extends BaseResult {
    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "id": 57,
     *         "name": "孙培佟",
     *         "phone": "176****2526",
     *         "sex": 0,
     *         "age": 41,
     *         "idNo": "**************021X",
     *         "address": "兰山区解放路永恒华府8号楼2单元1102室",
     *         "unit": "无",
     *         "headId": "https://www.juntaikeji.com:17002/thumbnail/head_img/7ffae7a8b53d4456880ef23f218701e4.jpeg",
     *         "departmentName": "东关街派出所",
     *         "gridName": "永恒华府",
     *     },
     *     "type": null,
     *     "message": null,
     *     "success": true
     * }
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         *"id": 57,
         *         "name": "孙培佟",
         *         "phone": "176****2526",
         *         "sex": 0,
         *         "age": 41,
         *         "idNo": "**************021X",
         *         "address": "兰山区解放路永恒华府8号楼2单元1102室",
         *         "unit": "无",
         *         "headId": "https://www.juntaikeji.com:17002/thumbnail/head_img/7ffae7a8b53d4456880ef23f218701e4.jpeg",
         *         "departmentName": "东关街派出所",
         *         "gridName": "永恒华府",
         */

        private int id;//重点人员id
        private String name;//姓名
        private String phone;//联系方式
        private int sex;//性别（0男；1女）
        private int age;//年龄
        private String idNo;//身份证号
        private String address;//地址
        private String unit;//部门
        private String headId;//头像图片地址
        private String departmentName;//部门名称
        private String gridName;//网格名称

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name  == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone == null? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getIdNo() {
            return idNo == null? "" : idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUnit() {
            return unit == null? "无" : unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getHeadId() {
            return headId == null? "" : headId;
        }

        public void setHeadId(String headId) {
            this.headId = headId;
        }

        public String getDepartmentName() {
            return departmentName == null? "" : departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getGridName() {
            return gridName == null? "" : gridName;
        }

        public void setGridName(String gridName) {
            this.gridName = gridName;
        }
    }

}

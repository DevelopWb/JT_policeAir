package com.juntai.wisdom.dgjxb.bean.site;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;

/**
 * @Author: tobato
 * @Description: 作用描述  场所详情
 * @CreateDate: 2020/4/5 17:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/5 17:01
 */
public class UnitDetailBean extends BaseResult {

    /**
     *{
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "id": 40,
     *         "imgId": "349,350,351",
     *         "name": "新华书店",
     *         "typeName": "企业事业",
     *         "category": "图书",
     *         "region": "山东省临沂市兰山区金雀山街道",
     *         "address": "山东省临沂市兰山区解放路136号",
     *         "contactsName": "张庆功",
     *         "contactsSex": 1,
     *         "contactsPhone": "13869900865",
     *         "standbyContacts": "",
     *         "standbyPhone": "",
     *         "synopsis": "新华书店位于解放路与沂蒙路交汇 主要经营图书类 监控齐全 消防通道两个 工作人员约在30人"
     *     },
     *     "type": null,
     *     "message": "成功",
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

    public static class DataBean implements Serializable {
        /**
         * "id": 54,
         *         "userId": 88,
         *         "imgId": "433,434,435,436,437,438",
         *         "videoUrl": "http://61.156.157.132:32288/crm_client_video/bf1074e694dd4dedb9580472092c015b.mp4",
         *         "name": "仁德大药房",
         *         "typeId": 13,
         *         "typeName": "健康护理",
         *         "provinceCode": "370000",
         *         "cityCode": "371300",
         *         "areaCode": "371302",
         *         "streetCode": "371302003",
         *         "latitude": 35.0725386,
         *         "longitude": 118.3635187,
         *         "category": "药品",
         *         "region": "山东省临沂市兰山区金雀山街道",
         *         "address": "山东省临沂市兰山区酒厂前街26",
         *         "contactsName": "蔡云艳",
         *         "contactsSex": 2,
         *         "contactsPhone": "13953958644",
         *         "standbyContacts": "王晓伟",
         *         "standbyPhone": "15806923722",
         *         "sourceId": 3,
         *         "sourceName": "手工录入",
         *         "synopsis": "销售药品",
         *         "remark": "",
         *         "gridId": 7,
         *         "gridName": "新东关"
         */

        private int id;//单位id
        private int userId;//添加人id
        private String imgId;//图片id
        private String name;//单位名称
        private int typeId;//类型id
        private String typeName;//类型名称
        private String category;//经营品类
        private String contactsName;//联系人姓名
        private int contactsSex;//性别（1：男；2：女）
        private String contactsPhone;//联系方式
        private String standbyContacts;//备用联系人
        private String  standbyPhone;//备用联系人电话
        private String synopsis;//简介
        private String videoUrl;//视频，不为空则有视频，不是视频地址
        private String remark;//备注
        private int gridId;//2,
        private String gridName;//东关网格
        private String address;//单位地址
        private double longitude;//经纬度
        private double latitude;//经纬度
        private String region;//所在区域
        private int provinceCode;//省代码
        private int cityCode;//市代码
        private int areaCode;//县区代码
        private int streetCode;//街道代码
        private int sourceId;//客户来源id
        private String sourceName;//来源名称


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getSourceId() {
            return sourceId;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }

        public String getSourceName() {
            return sourceName == null? "" : sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public int getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(int provinceCode) {
            this.provinceCode = provinceCode;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public int getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(int areaCode) {
            this.areaCode = areaCode;
        }

        public int getStreetCode() {
            return streetCode;
        }

        public void setStreetCode(int streetCode) {
            this.streetCode = streetCode;
        }

        public int getGridId() {
            return gridId;
        }

        public void setGridId(int gridId) {
            this.gridId = gridId;
        }

        public String getGridName() {
            return gridName == null? "无":gridName;
        }

        public void setGridName(String gridName) {
            this.gridName = gridName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgId() {
            return imgId == null? "" : imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeName() {
            return typeName == null? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getCategory() {
            return category == null? "" : category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getRegion() {
            return region == null? "" : region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContactsName() {
            return contactsName == null? "" : contactsName;
        }

        public void setContactsName(String contactsName) {
            this.contactsName = contactsName;
        }

        public int getContactsSex() {
            return contactsSex;
        }

        public void setContactsSex(int contactsSex) {
            this.contactsSex = contactsSex;
        }

        public String getContactsPhone() {
            return contactsPhone == null? "" : contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
        }

        public String getStandbyContacts() {
            return standbyContacts == null? "" : standbyContacts;
        }

        public void setStandbyContacts(String standbyContacts) {
            this.standbyContacts = standbyContacts;
        }

        public String getStandbyPhone() {
            return standbyPhone == null? "" : standbyPhone;
        }

        public void setStandbyPhone(String standbyPhone) {
            this.standbyPhone = standbyPhone;
        }

        public String getSynopsis() {
            return synopsis == null? "" : synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getRemark() {
            return remark == null? "" : remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}

package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class ShopManagerDetailBean extends BaseResult {


    /**
     * data : {"id":13,"userId":8088,"userNickname":null,"userAccount":"38357363","name":"临沂菜优选自营有限公司","headPortrait":"https://www.juntaikeji.com:21900/2022-05-23/1653274089152.png","introduction":"公司成立于2000年，公司300余人，主要以蔬菜配送生产为主","phoneNumber":"18596218800","backImg":"https://www.juntaikeji.com:21900/2022-05-24/1653352949251.png","cameraUrl":"37130201561327011020","shopImg":"https://www.juntaikeji.com:21900/2022-05-23/1653289186847.png,https://www.juntaikeji.com:21900/2022-05-23/1653289187032.png,https://www.juntaikeji.com:21900/2022-05-23/1653289187087.png","commodityStyle":1,"shopImgList":["https://www.juntaikeji.com:21900/2022-05-23/1653289186847.png","https://www.juntaikeji.com:21900/2022-05-23/1653289187032.png","https://www.juntaikeji.com:21900/2022-05-23/1653289187087.png"],"category":"蔬菜类,粮食类,方便食品","gpsAddress":"山东省临沂市河东区太平街道","longitude":"118.488653","latitude":"35.227224","businessLicense":"https://www.juntaikeji.com:21900/2022-05-23/1653274089160.png","realName":"张三","idCode":"1234567894564121","idPositive":"https://www.juntaikeji.com:21900/2022-05-23/1653274089167.png","handPicture":"https://www.juntaikeji.com:21900/2022-05-23/1653274089167.png","idSide":"https://www.juntaikeji.com:21900/2022-05-23/1653274089174.png","shopRealScene":"https://www.juntaikeji.com:21900/2022-05-23/1653274089195.png","isAgreement":1,"state":2,"stateContent":null,"status":0,"createTime":"2022-07-25 09:09:53"}
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
         * id : 13
         * userId : 8088
         * userNickname : null
         * userAccount : 38357363
         * name : 临沂菜优选自营有限公司
         * headPortrait : https://www.juntaikeji.com:21900/2022-05-23/1653274089152.png
         * introduction : 公司成立于2000年，公司300余人，主要以蔬菜配送生产为主
         * phoneNumber : 18596218800
         * backImg : https://www.juntaikeji.com:21900/2022-05-24/1653352949251.png
         * cameraUrl : 37130201561327011020
         * shopImg : https://www.juntaikeji.com:21900/2022-05-23/1653289186847.png,https://www.juntaikeji.com:21900/2022-05-23/1653289187032.png,https://www.juntaikeji.com:21900/2022-05-23/1653289187087.png
         * commodityStyle : 1
         * shopImgList : ["https://www.juntaikeji.com:21900/2022-05-23/1653289186847.png","https://www.juntaikeji.com:21900/2022-05-23/1653289187032.png","https://www.juntaikeji.com:21900/2022-05-23/1653289187087.png"]
         * category : 蔬菜类,粮食类,方便食品
         * gpsAddress : 山东省临沂市河东区太平街道
         * longitude : 118.488653
         * latitude : 35.227224
         * businessLicense : https://www.juntaikeji.com:21900/2022-05-23/1653274089160.png
         * realName : 张三
         * idCode : 1234567894564121
         * idPositive : https://www.juntaikeji.com:21900/2022-05-23/1653274089167.png
         * handPicture : https://www.juntaikeji.com:21900/2022-05-23/1653274089167.png
         * idSide : https://www.juntaikeji.com:21900/2022-05-23/1653274089174.png
         * shopRealScene : https://www.juntaikeji.com:21900/2022-05-23/1653274089195.png
         * isAgreement : 1
         * state : 2
         * stateContent : null
         * status : 0
         * createTime : 2022-07-25 09:09:53
         */

        private int id;
        private int userId;
        private Object userNickname;
        private String userAccount;
        private String name;
        private String headPortrait;
        private String introduction;
        private String phoneNumber;
        private String backImg;
        private String startTime;
        private String endTime;
        private String cameraUrl;
        private String shopImg;
        private int commodityStyle;
        private String category;
        private String gpsAddress;
        private String longitude;
        private String latitude;
        private String businessLicense;
        private String realName;
        private String idCode;
        private String idPositive;
        private String handPicture;
        private String idSide;
        private String shopRealScene;
        private int isAgreement;
        private int state;
        private String stateContent;
        private int status;
        private String createTime;
        private List<String> shopImgList;

        public String getStartTime() {
            return startTime == null ? "" : startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime == null ? "" : startTime;
        }

        public String getEndTime() {
            return endTime == null ? "" : endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime == null ? "" : endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(Object userNickname) {
            this.userNickname = userNickname;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getCameraUrl() {
            return cameraUrl;
        }

        public void setCameraUrl(String cameraUrl) {
            this.cameraUrl = cameraUrl;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public int getCommodityStyle() {
            return commodityStyle;
        }

        public void setCommodityStyle(int commodityStyle) {
            this.commodityStyle = commodityStyle;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getGpsAddress() {
            return gpsAddress;
        }

        public void setGpsAddress(String gpsAddress) {
            this.gpsAddress = gpsAddress;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(String businessLicense) {
            this.businessLicense = businessLicense;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdCode() {
            return idCode;
        }

        public void setIdCode(String idCode) {
            this.idCode = idCode;
        }

        public String getIdPositive() {
            return idPositive;
        }

        public void setIdPositive(String idPositive) {
            this.idPositive = idPositive;
        }

        public String getHandPicture() {
            return handPicture;
        }

        public void setHandPicture(String handPicture) {
            this.handPicture = handPicture;
        }

        public String getIdSide() {
            return idSide;
        }

        public void setIdSide(String idSide) {
            this.idSide = idSide;
        }

        public String getShopRealScene() {
            return shopRealScene;
        }

        public void setShopRealScene(String shopRealScene) {
            this.shopRealScene = shopRealScene;
        }

        public int getIsAgreement() {
            return isAgreement;
        }

        public void setIsAgreement(int isAgreement) {
            this.isAgreement = isAgreement;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateContent() {
            return stateContent == null ? "" : stateContent;
        }

        public void setStateContent(String stateContent) {
            this.stateContent = stateContent == null ? "" : stateContent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<String> getShopImgList() {
            return shopImgList;
        }

        public void setShopImgList(List<String> shopImgList) {
            this.shopImgList = shopImgList;
        }
    }
}

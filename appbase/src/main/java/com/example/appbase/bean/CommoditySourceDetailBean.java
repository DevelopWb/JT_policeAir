package com.example.appbase.bean;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditySourceDetailBean extends BaseResult {


    /**
     * data : {"commodityId":54,"supplier":13,"purchaseTime":"2022-08-10","purchaseName":"张安","photoOne":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","photoTwo":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","photoThree":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","photoList":[{"fileUrl":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png"},{"fileUrl":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png"}]}
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
         * commodityId : 54
         * supplier : 13
         * purchaseTime : 2022-08-10
         * purchaseName : 张安
         * photoOne : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * photoTwo : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * photoThree : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * photoList : [{"fileUrl":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png"},{"fileUrl":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png"}]
         */

        private int commodityId;
        private int supplier;
        private String purchaseTime;
        private String account;
        private String token;
        private String purchaseName;
        private String photoOne;
        private String photoTwo;
        private String photoThree;
        private List<PhotoListBean> photoList;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account == null ? "" : account;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token == null ? "" : token;
        }

        public int getSupplier() {
            return supplier;
        }

        public void setSupplier(int supplier) {
            this.supplier = supplier;
        }

        public String getPurchaseTime() {
            return purchaseTime;
        }

        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }

        public String getPurchaseName() {
            return purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }

        public String getPhotoOne() {
            return photoOne;
        }

        public void setPhotoOne(String photoOne) {
            this.photoOne = photoOne;
        }

        public String getPhotoTwo() {
            return photoTwo;
        }

        public void setPhotoTwo(String photoTwo) {
            this.photoTwo = photoTwo;
        }

        public String getPhotoThree() {
            return photoThree;
        }

        public void setPhotoThree(String photoThree) {
            this.photoThree = photoThree;
        }

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public static class PhotoListBean {
            public PhotoListBean(String fileUrl) {
                this.fileUrl = fileUrl;
            }

            /**
             * fileUrl : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
             */

            private String fileUrl;

            public String getFileUrl() {
                return fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }
        }
    }
}

package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SortDetailBean extends BaseResult {


    /**
     * data : {"id":349,"orderFormNumber":"c977442587cf41d0a05bb37b96c4632b","payType":0,"establishTime":"2022-07-14 10:42:58","paymentTime":"2022-05-14 11:53:01","totalPrices":21.98,"schoolId":1,"schoolNumber":"001","schoolName":"河东区东兴实验学校","name":"王景","phone":"18763312667","address":"甘肃省　金昌市　永昌县　焦家庄镇　5555","qrCodeUrl":"https://www.baidu.com?orderId=349","commodityList":[{"commodityId":11,"commodityName":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","prices":10.99,"commodityNum":1,"cartInfo":"白色;L","unique":"a1a4b2e215a348aa82e2f98a398e24bb","supplier":2,"supplierName":"测试小店","purchaseTime":"2022-07-24","purchaseName":"张三","traceabilityList":[{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]},{"commodityId":11,"commodityName":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","prices":10.99,"commodityNum":1,"cartInfo":"黑色;M","unique":"9bcd73206cc944b1a7a889070286739b","supplier":2,"supplierName":"测试小店","purchaseTime":"2022-07-24","purchaseName":"张三","traceabilityList":[{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]}]}
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
         * id : 349
         * orderFormNumber : c977442587cf41d0a05bb37b96c4632b
         * payType : 0
         * establishTime : 2022-07-14 10:42:58
         * paymentTime : 2022-05-14 11:53:01
         * totalPrices : 21.98
         * schoolId : 1
         * schoolNumber : 001
         * schoolName : 河东区东兴实验学校
         * name : 王景
         * phone : 18763312667
         * address : 甘肃省　金昌市　永昌县　焦家庄镇　5555
         * qrCodeUrl : https://www.baidu.com?orderId=349
         * commodityList : [{"commodityId":11,"commodityName":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","prices":10.99,"commodityNum":1,"cartInfo":"白色;L","unique":"a1a4b2e215a348aa82e2f98a398e24bb","supplier":2,"supplierName":"测试小店","purchaseTime":"2022-07-24","purchaseName":"张三","traceabilityList":[{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]},{"commodityId":11,"commodityName":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","prices":10.99,"commodityNum":1,"cartInfo":"黑色;M","unique":"9bcd73206cc944b1a7a889070286739b","supplier":2,"supplierName":"测试小店","purchaseTime":"2022-07-24","purchaseName":"张三","traceabilityList":[{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]}]
         */

        private int id;
        private String orderFormNumber;
        private int payType;
        private String establishTime;
        private String paymentTime;
        private double totalPrices;
        private int schoolId;
        private String schoolNumber;
        private String schoolName;
        private String remark;
        private String name;
        private String phone;
        private String address;
        /**
         * 订单状态 订单状态(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）
         */
        private Integer state;
        private String qrCodeUrl;
        private List<CommodityListBean> commodityList;

        public String getRemark() {
            return remark == null ? "" : remark;
        }

        public void setRemark(String remark) {
            this.remark = remark == null ? "" : remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderFormNumber() {
            return orderFormNumber;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public void setOrderFormNumber(String orderFormNumber) {
            this.orderFormNumber = orderFormNumber;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getEstablishTime() {
            return establishTime;
        }

        public void setEstablishTime(String establishTime) {
            this.establishTime = establishTime;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public double getTotalPrices() {
            return totalPrices;
        }

        public void setTotalPrices(double totalPrices) {
            this.totalPrices = totalPrices;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolNumber() {
            return schoolNumber;
        }

        public void setSchoolNumber(String schoolNumber) {
            this.schoolNumber = schoolNumber;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }

        public List<CommodityListBean> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<CommodityListBean> commodityList) {
            this.commodityList = commodityList;
        }

        public static class CommodityListBean {
            /**
             * commodityId : 11
             * commodityName : 亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤
             * coverImg : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
             * prices : 10.99
             * commodityNum : 1
             * cartInfo : 白色;L
             * unique : a1a4b2e215a348aa82e2f98a398e24bb
             * supplier : 2
             * supplierName : 测试小店
             * purchaseTime : 2022-07-24
             * purchaseName : 张三
             * traceabilityList : [{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]
             */

            private int commodityId;
            private String commodityName;
            private String coverImg;
            private double prices;
            private double price;
            private double commodityNum;
            private String cartInfo;
            private String unique;
            private int supplier;
            private String supplierName;
            private String purchaseTime;
            private String purchaseName;
            private String photoOne;
            private String photoTwo;
            private String photoThree;
            private List<TraceabilityListBean> traceabilityList;

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getCoverImg() {
                return coverImg;
            }

            public void setCoverImg(String coverImg) {
                this.coverImg = coverImg;
            }

            public double getPrices() {
                return prices;
            }

            public String getPhotoOne() {
                return photoOne == null ? "" : photoOne;
            }

            public void setPhotoOne(String photoOne) {
                this.photoOne = photoOne == null ? "" : photoOne;
            }

            public String getPhotoTwo() {
                return photoTwo == null ? "" : photoTwo;
            }

            public void setPhotoTwo(String photoTwo) {
                this.photoTwo = photoTwo == null ? "" : photoTwo;
            }

            public String getPhotoThree() {
                return photoThree == null ? "" : photoThree;
            }

            public void setPhotoThree(String photoThree) {
                this.photoThree = photoThree == null ? "" : photoThree;
            }

            public List<TraceabilityListBean> getTraceabilityList() {
                if (traceabilityList == null) {
                    return new ArrayList<>();
                }
                return traceabilityList;
            }

            public void setTraceabilityList(List<TraceabilityListBean> traceabilityList) {
                this.traceabilityList = traceabilityList;
            }

            public void setPrices(double prices) {
                this.prices = prices;
            }

            public double getCommodityNum() {
                return commodityNum;
            }

            public void setCommodityNum(double commodityNum) {
                this.commodityNum = commodityNum;
            }

            public String getCartInfo() {
                return cartInfo;
            }

            public void setCartInfo(String cartInfo) {
                this.cartInfo = cartInfo;
            }

            public String getUnique() {
                return unique;
            }

            public void setUnique(String unique) {
                this.unique = unique;
            }

            public int getSupplier() {
                return supplier;
            }

            public void setSupplier(int supplier) {
                this.supplier = supplier;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
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


            public static class TraceabilityListBean {
                /**
                 * commodityId : 11
                 * photoOne : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
                 * photoTwo : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
                 * photoThree : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
                 * remarks : 测试111
                 */

                private String fileUrl;

                public String getFileUrl() {
                    return fileUrl == null ? "" : fileUrl;
                }

                public void setFileUrl(String fileUrl) {
                    this.fileUrl = fileUrl == null ? "" : fileUrl;
                }
            }
        }
    }
}

package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommodityManagerDetailBean extends BaseResult {


    /**
     * data : {"id":345,"shopName":"白象2","classifyName":"水果","categoryName":"水果类","name":"橘子","coverImg":"https://www.juntaikeji.com:21900/2022-08-17/1660718908298850.jpeg","videoUrl":null,"description":"","price":2,"state":2,"stateContent":"","createTime":"2022-08-17 14:48:44","traceability":{"id":62,"commodityId":345,"supplier":27,"purchaseTime":"2022-08-17","purchaseName":"啦啦","photoOne":"https://www.juntaikeji.com:21900/2022-08-17/1660726300537556.jpeg","photoTwo":"https://www.juntaikeji.com:21900/2022-08-17/1660726315973199.jpeg","photoThree":"https://www.juntaikeji.com:21900/2022-08-17/1660726316213737.jpeg","createTime":"2022-08-17 16:52:00","traceabilityFile":[{"id":158,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306607970.jpeg"},{"id":159,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306636307.jpeg"}]}}
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
         * id : 345
         * shopName : 白象2
         * classifyName : 水果
         * categoryName : 水果类
         * name : 橘子
         * coverImg : https://www.juntaikeji.com:21900/2022-08-17/1660718908298850.jpeg
         * videoUrl : null
         * description :
         * price : 2.0
         * state : 2
         * stateContent :
         * createTime : 2022-08-17 14:48:44
         * traceability : {"id":62,"commodityId":345,"supplier":27,"purchaseTime":"2022-08-17","purchaseName":"啦啦","photoOne":"https://www.juntaikeji.com:21900/2022-08-17/1660726300537556.jpeg","photoTwo":"https://www.juntaikeji.com:21900/2022-08-17/1660726315973199.jpeg","photoThree":"https://www.juntaikeji.com:21900/2022-08-17/1660726316213737.jpeg","createTime":"2022-08-17 16:52:00","traceabilityFile":[{"id":158,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306607970.jpeg"},{"id":159,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306636307.jpeg"}]}
         */

        private int id;
        private String shopName;
        private String classifyName;
        private String categoryName;
        private String name;
        private String coverImg;
        private String videoUrl;
        private String unit;
        private String description;
        private double price;
        private double delivery;
        private int state;
        private String stateContent;
        private String createTime;
        private TraceabilityBean traceability;

        public String getUnit() {
            return unit == null ? "" : unit;
        }

        public void setUnit(String unit) {
            this.unit = unit == null ? "" : unit;
        }

        public double getDelivery() {
            return delivery;
        }

        public void setDelivery(double delivery) {
            this.delivery = delivery;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getVideoUrl() {
            return videoUrl == null ? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl == null ? "" : videoUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStateContent() {
            return stateContent;
        }

        public void setStateContent(String stateContent) {
            this.stateContent = stateContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public TraceabilityBean getTraceability() {
            return traceability;
        }

        public void setTraceability(TraceabilityBean traceability) {
            this.traceability = traceability;
        }

        public static class TraceabilityBean {
            /**
             * id : 62
             * commodityId : 345
             * supplier : 27
             * purchaseTime : 2022-08-17
             * purchaseName : 啦啦
             * photoOne : https://www.juntaikeji.com:21900/2022-08-17/1660726300537556.jpeg
             * photoTwo : https://www.juntaikeji.com:21900/2022-08-17/1660726315973199.jpeg
             * photoThree : https://www.juntaikeji.com:21900/2022-08-17/1660726316213737.jpeg
             * createTime : 2022-08-17 16:52:00
             * traceabilityFile : [{"id":158,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306607970.jpeg"},{"id":159,"traceabilityId":62,"fileUrl":"https://www.juntaikeji.com:21900/2022-08-17/1660726306636307.jpeg"}]
             */

            private int id;
            private int commodityId;
            private int supplier;
            private String purchaseTime;
            private String purchaseName;
            private String photoOne;
            private String photoTwo;
            private String photoThree;
            private String createTime;
            private List<TraceabilityFileBean> traceabilityFile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public List<TraceabilityFileBean> getTraceabilityFile() {
                return traceabilityFile;
            }

            public void setTraceabilityFile(List<TraceabilityFileBean> traceabilityFile) {
                this.traceabilityFile = traceabilityFile;
            }

            public static class TraceabilityFileBean {
                /**
                 * id : 158
                 * traceabilityId : 62
                 * fileUrl : https://www.juntaikeji.com:21900/2022-08-17/1660726306607970.jpeg
                 */

                private int id;
                private int traceabilityId;
                private String fileUrl;

                public TraceabilityFileBean(String fileUrl) {
                    this.fileUrl = fileUrl;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getTraceabilityId() {
                    return traceabilityId;
                }

                public void setTraceabilityId(int traceabilityId) {
                    this.traceabilityId = traceabilityId;
                }

                public String getFileUrl() {
                    return fileUrl;
                }

                public void setFileUrl(String fileUrl) {
                    this.fileUrl = fileUrl;
                }
            }
        }
    }
}

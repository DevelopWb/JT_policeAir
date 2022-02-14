package com.juntai.wisdom.dgjxb.bean.exchang_mall;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:商品详情实体
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class GoodsInfoBean extends BaseResult {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * commodityId : 92
         * commodityName : 牙刷
         * price : 60
         * money : 3
         * commoditySynopsis : 刷牙用
         * commodityImg : /commodityImg/32beb959e3f14700a1a0f9b36ab6f1c5.jpeg
         * picture : [{"img":"/commodityImg/346636c4cc044911b253aa37699e0294.jpeg"},{"img":"/commodityImg/349f98d2677b421fa9d68b547620e7d7.jpeg"},{"img":"/commodityImg/fd4bc46538114cdb9be20a0932d48a49.jpeg"}]
         */

        private int commodityId;//商品id
        private String commodityName;//商品名称
        private int price;//积分
        private int money;//价格
        private String commoditySynopsis;//商品简介
        private String commodityImg;//商品封面图片
        private List<PictureBean> picture;//
        private int inventoryNum;//库存

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName == null? "" : commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getCommoditySynopsis() {
            return commoditySynopsis == null? "" : commoditySynopsis;
        }

        public void setCommoditySynopsis(String commoditySynopsis) {
            this.commoditySynopsis = commoditySynopsis;
        }

        public String getCommodityImg() {
            return commodityImg == null? "" : commodityImg;
        }

        public void setCommodityImg(String commodityImg) {
            this.commodityImg = commodityImg;
        }

        public List<PictureBean> getPicture() {
            if (picture == null){
                picture = new ArrayList<>();
            }
            return picture;
        }

        public void setPicture(List<PictureBean> picture) {
            this.picture = picture;
        }

        public int getInventoryNum() {
            return inventoryNum;
        }

        public void setInventoryNum(int inventoryNum) {
            this.inventoryNum = inventoryNum;
        }

        public static class PictureBean {
            /**
             * img : /commodityImg/346636c4cc044911b253aa37699e0294.jpeg
             */

            private String img;

            public String getImg() {
                return img == null? "" : img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}

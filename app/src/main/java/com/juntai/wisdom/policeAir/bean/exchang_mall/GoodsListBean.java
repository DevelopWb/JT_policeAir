package com.juntai.wisdom.policeAir.bean.exchang_mall;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:商品列表实体
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class GoodsListBean extends BaseResult {
    private List<GoodsBean> data;

    public List<GoodsBean> getData() {
        if (data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

    public static class GoodsBean implements Serializable {

        /**
         * commodityId : 92
         * commodityName : 牙刷
         * commodityImg : /commodityImg/32beb959e3f14700a1a0f9b36ab6f1c5.jpeg
         * commoditySynopsis : 刷牙用
         * attrId : 202
         * price : 60
         * money : 3
         * inventoryNum : 90
         */

        private int commodityId;//商品id
        private String commodityName;//商品名称
        private String commodityImg;//商品封面图片
        private String commoditySynopsis;//商品描述
        private int attrId;//商品规格id
        private int price;//积分
        private double money;//价格
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

        public String getCommodityImg() {
            return commodityImg == null? "" : commodityImg;
        }

        public void setCommodityImg(String commodityImg) {
            this.commodityImg = commodityImg;
        }

        public String getCommoditySynopsis() {
            return commoditySynopsis == null? "" : commoditySynopsis;
        }

        public void setCommoditySynopsis(String commoditySynopsis) {
            this.commoditySynopsis = commoditySynopsis;
        }

        public int getAttrId() {
            return attrId;
        }

        public void setAttrId(int attrId) {
            this.attrId = attrId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getInventoryNum() {
            return inventoryNum;
        }

        public void setInventoryNum(int inventoryNum) {
            this.inventoryNum = inventoryNum;
        }
    }
}

package com.juntai.wisdom.dgjxb.bean.exchang_mall;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:历史兑换实体
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public class HistoryGoodsListBean extends BaseResult {
    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        /**
         * commodityName : 牙刷
         * commodityImg : /commodityImg/32beb959e3f14700a1a0f9b36ab6f1c5.jpeg
         * thisScore : 10
         * typeId : 0
         * createTime : 2020-06-02 14:13:34
         */

        private String commodityName;
        private String commodityImg;
        private int thisScore;
        private int typeId;
        private String createTime;

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

        public int getThisScore() {
            return thisScore;
        }

        public void setThisScore(int thisScore) {
            this.thisScore = thisScore;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getCreateTime() {
            return createTime == null? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}

package com.juntai.wisdom.policeAir.mine.exchange_mall;

import com.juntai.wisdom.basecomponent.mvp.IView;

/**
 * Describe:兑换商城关联类
 * Create by zhangzhenlong
 * 2020-6-2
 * email:954101549@qq.com
 */
public interface ExchangeMallContract {
    String GET_ALL_GOODS = "getAllGoods";
    String GET_HISTORY_GOODS = "getHistoryGoods";
    String GET_GOODS_DETAIL = "getGoodsDetail";
    String EXCHANGE_GOODS = "exchangeGoods";

    interface IMallModel{}
    interface IMallView extends IView {}
    interface IMallPresent{
        /**
         * 获取商品兑换列表
         * @param tag
         */
        void getAllGoodsList(String tag);

        /**
         * 获取兑换历史列表
         * @param tag
         */
        void getExchangeHistoryList(String tag);
        /**
         * 获取商品详情
         * @param tag
         */
        void getGoodsDetail(int id, String tag);

        /**
         * 兑换商品
         * @param price
         * @param commodityId
         * @param commodityName
         * @param tag
         */
        void exchangeGoods(int price, int commodityId, String commodityName, String tag);
    }
}

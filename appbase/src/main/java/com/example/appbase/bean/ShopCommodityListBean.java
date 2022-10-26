package com.example.appbase.bean;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/8 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/8 14:04
 */
public class ShopCommodityListBean extends BaseResult {


    /**
     * data : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"onSale":0,"notSold":0,"list":[{"id":264,"shopId":26,"name":"新鲜露天大白菜","coverImg":"https://www.juntaikeji.com:21900/2022-08-29/1661753693028.png","price":15.5,"sales":40,"isPostage":0,"browse":0}]}
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
         * totalCount : 1
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * onSale : 0
         * notSold : 0
         * list : [{"id":264,"shopId":26,"name":"新鲜露天大白菜","coverImg":"https://www.juntaikeji.com:21900/2022-08-29/1661753693028.png","price":15.5,"sales":40,"isPostage":0,"browse":0}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private int onSale;
        private int notSold;
        private List<CommodityBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getOnSale() {
            return onSale;
        }

        public void setOnSale(int onSale) {
            this.onSale = onSale;
        }

        public int getNotSold() {
            return notSold;
        }

        public void setNotSold(int notSold) {
            this.notSold = notSold;
        }

        public List<CommodityBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<CommodityBean> list) {
            this.list = list;
        }
    }
}

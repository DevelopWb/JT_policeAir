package com.example.appbase.bean;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  分拣列表
 * @UpdateUser: 更新者
 */
public class SortListBean extends BaseResult {


    /**
     * data : {"totalCount":275,"pageSize":10,"totalPage":28,"currPage":1,"list":[{"id":739,"orderFormNumber":"0498061263311084","schoolName":"河东区东兴实验学校","totalNum":1,"totalPrices":0.01,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-37"},{"id":738,"orderFormNumber":"3543051717984602","schoolName":"河东区东兴实验学校","totalNum":1,"totalPrices":0.01,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-33"},{"id":736,"orderFormNumber":"2932453970396605","schoolName":"河东区东兴实验学校","totalNum":0,"totalPrices":0,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-33"}]}
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
         * totalCount : 275
         * pageSize : 10
         * totalPage : 28
         * currPage : 1
         * list : [{"id":739,"orderFormNumber":"0498061263311084","schoolName":"河东区东兴实验学校","totalNum":1,"totalPrices":0.01,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-37"},{"id":738,"orderFormNumber":"3543051717984602","schoolName":"河东区东兴实验学校","totalNum":1,"totalPrices":0.01,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-33"},{"id":736,"orderFormNumber":"2932453970396605","schoolName":"河东区东兴实验学校","totalNum":0,"totalPrices":0,"sorting":1,"delivery":1,"establishTime":"2022-07-17 09-33"}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 739
             * orderFormNumber : 0498061263311084
             * schoolName : 河东区东兴实验学校
             * totalNum : 1
             * totalPrices : 0.01
             * sorting : 1
             * delivery : 1
             * establishTime : 2022-07-17 09-37
             */

            private int id;
            private String orderFormNumber;
            private String schoolName;
            private int totalNum;
            private double totalPrices;
            private int sorting;
            private double delivery;
            private String establishTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrderFormNumber() {
                return orderFormNumber;
            }

            public void setOrderFormNumber(String orderFormNumber) {
                this.orderFormNumber = orderFormNumber;
            }

            public String getSchoolName() {
                return schoolName;
            }

            public void setSchoolName(String schoolName) {
                this.schoolName = schoolName;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public double getTotalPrices() {
                return totalPrices;
            }

            public void setTotalPrices(double totalPrices) {
                this.totalPrices = totalPrices;
            }

            public int getSorting() {
                return sorting;
            }

            public void setSorting(int sorting) {
                this.sorting = sorting;
            }

            public double getDelivery() {
                return delivery;
            }

            public void setDelivery(double delivery) {
                this.delivery = delivery;
            }

            public String getEstablishTime() {
                return establishTime;
            }

            public void setEstablishTime(String establishTime) {
                this.establishTime = establishTime;
            }
        }
    }
}

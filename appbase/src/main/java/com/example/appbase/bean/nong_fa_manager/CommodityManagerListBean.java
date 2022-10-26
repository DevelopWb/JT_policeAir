package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品管理中的列表
 * @UpdateUser: 更新者
 */
public class CommodityManagerListBean extends BaseResult {

    /**
     * data : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":11,"shopName":"测试小店","classifyName":"","categoryName":"水果类","name":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","price":10.99,"createTime":null}]}
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
         * list : [{"id":11,"shopName":"测试小店","classifyName":"","categoryName":"水果类","name":"亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤","coverImg":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","price":10.99,"createTime":null}]
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
             * id : 11
             * shopName : 测试小店
             * classifyName :
             * categoryName : 水果类
             * name : 亚麻短袖T恤男简约日系百搭半袖2021新款夏季刺绣棉麻青少年体恤
             * coverImg : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
             * price : 10.99
             * createTime : null
             */

            private int id;
            private String shopName;
            private String classifyName;
            private String categoryName;
            private String name;
            private String coverImg;
            private double price;
            //1 待审核 2 已审核 3 未通过
            private int  status;
            private String createTime;

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

            public double getPrice() {
                return price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime == null ? "" : createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime == null ? "" : createTime;
            }

            public void setPrice(double price) {
                this.price = price;
            }

        }
    }
}

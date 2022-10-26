package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class ShopManagerListBean extends BaseResult {


    /**
     * data : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":2,"userNickname":"sc_e11mo483r264w7","userAccount":"18669505929","name":"测试小店","headPortrait":"https://www.juntaikeji.com:17002/head_img/default.jpg","phoneNumber":"18669505929","category":"蔬菜类,水果类,粮食类","createTime":"2022-07-23 15:32:18"}]}
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
         * list : [{"id":2,"userNickname":"sc_e11mo483r264w7","userAccount":"18669505929","name":"测试小店","headPortrait":"https://www.juntaikeji.com:17002/head_img/default.jpg","phoneNumber":"18669505929","category":"蔬菜类,水果类,粮食类","createTime":"2022-07-23 15:32:18"}]
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
             * id : 2
             * userNickname : sc_e11mo483r264w7
             * userAccount : 18669505929
             * name : 测试小店
             * headPortrait : https://www.juntaikeji.com:17002/head_img/default.jpg
             * phoneNumber : 18669505929
             * category : 蔬菜类,水果类,粮食类
             * createTime : 2022-07-23 15:32:18
             */

            private int id;
            private String userNickname;
            private String userAccount;
            private String name;
            private String headPortrait;
            private String phoneNumber;
            private String category;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserNickname() {
                return userNickname;
            }

            public void setUserNickname(String userNickname) {
                this.userNickname = userNickname;
            }

            public String getUserAccount() {
                return userAccount;
            }

            public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}

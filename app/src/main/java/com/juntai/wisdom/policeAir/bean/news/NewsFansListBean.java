package com.juntai.wisdom.policeAir.bean.news;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * Describe:粉丝、关注列表
 * Create by zhangzhenlong
 * 2020-8-18
 * email:954101549@qq.com
 */
public class NewsFansListBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"datas":[{"id":110,"nickName":"所长","headPortrait":"/head_img/b5995e4f711e4c67b3bead18c6836bf2.jpeg","fansCount":1,"isMutual":0,"isFans":0}],"total":5,"listSize":5,"pageCount":1}
     * type : null
     * message : null
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
         * datas : [{"id":110,"nickName":"所长","headPortrait":"/head_img/b5995e4f711e4c67b3bead18c6836bf2.jpeg","fansCount":1,"isMutual":0,"isFans":0}]
         * total : 5
         * listSize : 5
         * pageCount : 1
         */

        private int total;
        private int listSize;
        private int pageCount;
        private List<DatasBean> datas;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 110
             * nickName : 所长
             * headPortrait : /head_img/b5995e4f711e4c67b3bead18c6836bf2.jpeg
             * fansCount : 1
             * isMutual : 0
             * isFans : 0
             */
            private int id;//用户id
            private String nickName;//用户昵称
            private String headPortrait;//头像
            private int fansCount;//粉丝数量
            private int isFollow;//0:未关注；1:已关注；2:互相关注

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickName() {
                return nickName == null? "" : nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getHeadPortrait() {
                return headPortrait == null? "" : headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public int getFansCount() {
                return fansCount;
            }

            public void setFansCount(int fansCount) {
                this.fansCount = fansCount;
            }

            public int getIsFollow() {
                return isFollow;
            }

            public void setIsFollow(int isFollow) {
                this.isFollow = isFollow;
            }
        }
    }
}

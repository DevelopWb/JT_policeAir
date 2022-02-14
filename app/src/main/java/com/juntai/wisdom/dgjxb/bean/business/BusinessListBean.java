package com.juntai.wisdom.dgjxb.bean.business;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述   业务列表实体类
 * @CreateDate: 2020/5/20 16:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/20 16:03
 */
public class BusinessListBean  extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"datas":[{"id":1,"typeId":2,"name":"出生随父母落户"},{"id":2,"typeId":3,"name":"死亡注销"},{"id":3,"typeId":3,"name":"参军入伍"},{"id":4,"typeId":3,"name":"住址变动所内移居"},{"id":5,"typeId":4,"name":"购房落户"},{"id":6,"typeId":4,"name":"持准迁证户口迁出"},{"id":7,"typeId":1,"name":"大中专招生"}],"total":7,"listSize":7,"pageCount":1}
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
         * datas : [{"id":1,"typeId":2,"name":"出生随父母落户"},{"id":2,"typeId":3,"name":"死亡注销"},{"id":3,"typeId":3,"name":"参军入伍"},{"id":4,"typeId":3,"name":"住址变动所内移居"},{"id":5,"typeId":4,"name":"购房落户"},{"id":6,"typeId":4,"name":"持准迁证户口迁出"},{"id":7,"typeId":1,"name":"大中专招生"}]
         * total : 7
         * listSize : 7
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
             * id : 1
             * typeId : 2
             * name : 出生随父母落户
             */

            private int id;
//            private int typeId;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

//            public int getTypeId() {
//                return typeId;
//            }
//
//            public void setTypeId(int typeId) {
//                this.typeId = typeId;
//            }

            public String getName() {
                return name == null? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

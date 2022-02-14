package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/15 10:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/15 10:01
 */
public class SearchBean  extends BaseResult {


    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"count":21,"typeId":0,"typeName":"监控","list":[{"id":64,"name":"册山镇","content":"山东省临沂市罗庄区","picture":"/cameraImg/f0e7b530a237476699282d76a78b14c9.jpeg","account":null,"imEi":null},{"id":65,"name":"沂河","content":"山东省临沂市兰山区三河口隧道","picture":"/cameraImg/99e5ad8bcad049b497a5fd63c0dfe692.jpeg","account":null,"imEi":null},{"id":66,"name":"沂河2","content":"山东省临沂市兰山区解放立交","picture":"/cameraImg/fd0e20fd646f40b0bd3dbb2ff86239e7.jpeg","account":null,"imEi":null}]},{"count":16,"typeId":1,"typeName":"案件","list":[{"id":3789,"name":"临沂市河东区人民大街","content":"2020-04-14 08:53:54.0","picture":"/caseImg/9b7268d4c77949d0aaf943cccdf67c4f.jpeg","account":null,"imEi":null},{"id":3788,"name":"临沂市河东区人民大街","content":"2020-04-13 14:43:52.0","picture":"/caseImg/cea9761f770841c8b4b60fe965fb633f.jpeg","account":null,"imEi":null},{"id":3787,"name":"临沂市河东区人民大街","content":"2020-04-13 14:39:16.0","picture":"/caseImg/ecdb95ffaa334dd085b47227f929146e.jpeg","account":null,"imEi":null}]},{"count":0,"typeId":2,"typeName":"警员","list":[]},{"count":0,"typeId":3,"typeName":"车辆","list":[]},{"count":1,"typeId":4,"typeName":"一标三实房屋","list":[{"id":2,"name":"杨幂","content":"山东省临沂市兰山区金猴北城名居2A写字楼(天津路南)","picture":"/case_image/277dbf34830148d0861a8d3badbca1d7.jpeg","account":null,"imEi":null}]},{"count":0,"typeId":5,"typeName":"一标三实人员","list":[]},{"count":69,"typeId":6,"typeName":"一标三实单位","list":[{"id":1,"name":"临沂市兰山区海布莱德面包房","content":"山东省临沂市兰山区涑河北街","picture":"/case_image/1aeb494b546b49fea951491f905b5161.jpeg","account":null,"imEi":null},{"id":3,"name":"临沂市兰山区盘子女人坊","content":"山东省临沂市兰山区涑河北街","picture":"/case_image/da42f595ce314c35af5c79d63cec35f0.jpeg","account":null,"imEi":null},{"id":4,"name":"临沂市兰山区丹小香蛋糕店","content":"山东省临沂市兰山区涑河北街","picture":"/case_image/6b21b37316174e34a4513cd60adb4f9c.jpeg","account":null,"imEi":null}]},{"count":32,"typeId":7,"typeName":"巡检","list":[{"id":59,"name":"山东省临沂市河东区东夷大街","content":"2020-04-14 14:38:35.0","picture":"/patrolImg/9f6ad84ca5f7479581ded0224f040cea.jpeg","account":null,"imEi":null},{"id":57,"name":"山东省临沂市河东区东夷大街1号","content":"2020-04-14 14:33:13.0","picture":null,"account":null,"imEi":null},{"id":56,"name":"山东省临沂市河东区东夷大街1号","content":"2020-04-14 14:30:51.0","picture":null,"account":null,"imEi":null}]},{"count":1,"typeId":8,"typeName":"资讯","list":[{"id":27,"name":"临沂市人民政府通告！这29个村继续冻结！临沂市人民政府通告！这29个村继续冻结！","content":"临沂高铁片区建设临沂高铁片区建设\n据悉，高铁片区作为北城新区三期的核心区，也是下一步城市发展的制高点，将利用5年左右的时间，把高铁片区打造成为集交通枢纽、商务办公、星级酒店、购物娱乐、文体休闲、康养居住于一体的核心经济圈，建设畅通之城、绿色之城、智慧之城、创新之城，塑造对外开放新窗口、城市形象新名片。据悉，高铁片区作为北城新区三期的核心区，也是下一步城市发展的制高点，将利用5年左右的时间，把高铁片区打造成为集交通枢纽、商务办公、星级酒店、购物娱乐、文体休闲、康养居住于一体的核心经济圈，建设畅通之城、绿色之城、智慧之城、创新之城，塑造对外开放新窗口、城市形象新名片。\n根据部署安排，根据部署安排，临沂城投集团作为投融资主体，临沂城投集团作为投融资主体，将按照市场化运营原则，将按照市场化运营原则，承担高铁片区投融资、建设、运营、招商等工作，承担高铁片区投融资、建设、运营、招商等工作，开启了城市建设新模式。开启了城市建设新模式。","picture":"/informationImg/e5e49f87e3b04e65a3a6f324378539ef.jpeg","account":null,"imEi":null}]}]
     * type : null
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * count : 21
         * typeId : 0
         * typeName : 监控
         * list : [{"id":64,"name":"册山镇","content":"山东省临沂市罗庄区","picture":"/cameraImg/f0e7b530a237476699282d76a78b14c9.jpeg","account":null,"imEi":null},{"id":65,"name":"沂河","content":"山东省临沂市兰山区三河口隧道","picture":"/cameraImg/99e5ad8bcad049b497a5fd63c0dfe692.jpeg","account":null,"imEi":null},{"id":66,"name":"沂河2","content":"山东省临沂市兰山区解放立交","picture":"/cameraImg/fd0e20fd646f40b0bd3dbb2ff86239e7.jpeg","account":null,"imEi":null}]
         */

        private int count;
        private int typeId;
        private String typeName;
        private List<ListBean> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName == null? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 64
             * name : 册山镇
             * content : 山东省临沂市罗庄区
             * picture : http://192.168.124.115:8092/thumbnail/camera_img/01242e2cd2e84f92bd199683a0a82c82.jpeg
             * account : null
             * imEi : null
             */

            private int id;
            private int typeId;
            private String name;
            private String content;
            private String picture;
            private String account;
            private String imEi;

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name == null? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getAccount() {
                return account == null ? "" : account;
            }

            public void setAccount(String account) {
                this.account = account == null ? "" : account;
            }

            public String getImEi() {
                return imEi == null ? "" : imEi;
            }

            public void setImEi(String imEi) {
                this.imEi = imEi == null ? "" : imEi;
            }
        }
    }
}

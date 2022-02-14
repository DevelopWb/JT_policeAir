package com.juntai.wisdom.dgjxb.bean.site;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * Describe:从业人员详情
 * Create by zhangzhenlong
 * 2020-7-6
 * email:954101549@qq.com
 */
public class EmployeeDetailBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"id":1,"name":"张三","sex":1,"idNumber":"371322199910051514","address":"临沂市河东区","phone":"123456","typeName":"新华书店","remake":"测试","pictureOneUrl":"https://image.juntaikeji.com/2020-07-01/115d9a0f9998439bb7022df283317cdd.jpg","pictureTwoUrl":"https://image.juntaikeji.com/2020-07-01/f81d9490008b482eaf9848e401716bb2.jpg","pictureThreeUrl":"https://image.juntaikeji.com/2020-07-01/b671ad53f03a4312a46012f165bfac57.jpg","videoUrl":"https://image.juntaikeji.com/2020-07-01/a88fdb9f6daa49a2b26223fab50666f9.mp4"}
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
         * id : 1
         * name : 张三
         * sex : 1
         * idNumber : 371322199910051514
         * address : 临沂市河东区
         * phone : 123456
         * typeName : 新华书店
         * remake : 测试
         * pictureOneUrl : https://image.juntaikeji.com/2020-07-01/115d9a0f9998439bb7022df283317cdd.jpg
         * pictureTwoUrl : https://image.juntaikeji.com/2020-07-01/f81d9490008b482eaf9848e401716bb2.jpg
         * pictureThreeUrl : https://image.juntaikeji.com/2020-07-01/b671ad53f03a4312a46012f165bfac57.jpg
         * videoUrl : https://image.juntaikeji.com/2020-07-01/a88fdb9f6daa49a2b26223fab50666f9.mp4
         */

        private int id;
        private String name;
        private int sex;//1：男；2：女
        private String idNumber;
        private String address;
        private String phone;
        private String typeName;//所属单位
        private String remake;//备注
        private String pictureOneUrl;
        private String pictureTwoUrl;
        private String pictureThreeUrl;
        private String videoUrl;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getIdNumber() {
            return idNumber == null? "" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone == null? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTypeName() {
            return typeName == null? "" : typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getRemake() {
            return remake == null? "" : remake;
        }

        public void setRemake(String remake) {
            this.remake = remake;
        }

        public String getPictureOneUrl() {
            return pictureOneUrl == null? "" : pictureOneUrl;
        }

        public void setPictureOneUrl(String pictureOneUrl) {
            this.pictureOneUrl = pictureOneUrl;
        }

        public String getPictureTwoUrl() {
            return pictureTwoUrl == null? "" : pictureTwoUrl;
        }

        public void setPictureTwoUrl(String pictureTwoUrl) {
            this.pictureTwoUrl = pictureTwoUrl;
        }

        public String getPictureThreeUrl() {
            return pictureThreeUrl == null? "" : pictureThreeUrl;
        }

        public void setPictureThreeUrl(String pictureThreeUrl) {
            this.pictureThreeUrl = pictureThreeUrl;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}

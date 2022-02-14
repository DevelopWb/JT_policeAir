package com.juntai.wisdom.dgjxb.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/6/9 9:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/9 9:39
 */
public class PoliceDetailBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * status : 200
     * data : {"userId":99,"account":"17568086930","realName":"王彬",
     * "headPortrait":"/head_img/87f69d64e77648eea17118bab4771080.jpeg","departmentName":"清华俊景","number":9527,
     * "rOngYunToken":"UEuorJf6JtsomTq7Zs1kY4M7TTnyliJKuJ+CCyHJ2jJtQDP7WhUHZA==@thdm.cn.rongnav.com;thdm.cn.rongcfg
     * .com","state":0}
     * type : null
     * message : 成功
     * success : true
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
         *"id": 96,
         *         "nickname": "张振隆",
         *         "account": "18763739973",
         *         "headPortrait": "https://www.juntaikeji.com:17002/head_img/db77e71403e24e1e98fc023974a05d7f.jpeg",
         *         "latitude": 35.0906620,
         *         "longitude": 118.4022600,
         *         "departmentName": "东关街派出所",
         *         "state": 0
         */
        private int id;
        private String account;
        private String nickname;
        private String headPortrait;
        private String departmentName;
        private int state;//1在线
        private double latitude;
        private double longitude;


        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account == null? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getNickname() {
            return nickname == null? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getDepartmentName() {
            return departmentName == null? "" : departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}

package com.juntai.wisdom.dgjxb.bean.map;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:wong
 * Date: 2019/3/26
 * Description:
 */
public class ResponsePeople extends BaseResult implements Serializable {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":14,"longitude":118.3963342,"latitude":35.084883,"nickname":"王杰仔","departmentName":"沂州路派出所","rOngYunToken":"eu4SZ/5KtFNH1pqOm6jSIOd6/SAY2D7LmjTML/i89U4XgHvVRzetwIpoUHE9kEEfQrt3rTNb8OrP2dX+yDbXrbzIwsTSplgl","picture":"/head_img/default1.jpg","phoneNumber":"15195671260"},{"id":25,"longitude":null,"latitude":null,"nickname":"测","departmentName":"红埠寺派出所","rOngYunToken":"R2QlVG1ka9SYxEd+APEZlrNyFkC/wlRjGiatx8te+hTiou/Mk4AAOGZ8NK61atr24hmOexacTnkC8urPyI+lEvtpeuCuxlAU","picture":"/head_img/d85b9d3055f842f19ceac8cd3a7fab30.jpeg","phoneNumber":"17568086930"}]
     * type : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * "id": 87,
         *             "nickname": "苑新生",
         *             "latitude": 35.0906340,
         *             "longitude": 118.4020760,
         *             "headPortrait": "https://www.juntaikeji.com:17002/head_img/4acbbf83f3934de486fc0f179eaeeb3b.jpeg",
         *             "postName": "单位领导",
         *             "departmentName": "东关街派出所"
         */

        private int id;//人员id
        private String nickname;//人员昵称
        private double latitude;//维度
        private double longitude;//经度
        private String headPortrait;//头像
        private String postName;//职务名称
        private String departmentName;//所属部门名称
        private String account;//账号（手机号）
        private int state;//在线状态

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname == null? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

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

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getPostName() {
            return postName == null? "" : postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getDepartmentName() {
            return departmentName == null? "" : departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getAccount() {
            return account == null? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}

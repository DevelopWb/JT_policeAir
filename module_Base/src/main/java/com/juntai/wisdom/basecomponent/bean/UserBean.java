package com.juntai.wisdom.basecomponent.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;

/**
 * 个人信息
 * Created by Ma
 * on 2019/4/27
 */
public class UserBean extends BaseResult {
    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {}
     * type : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * login:
         * "userId": 101,
         *         "account": "18669505929",
         *         "password": null,
         *         "nickname": "铁人王进喜",
         *         "headPortrait": "https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
         *         "realNameStatus": 2,
         *         "settleStatus": 2,
         *         "rOngYunToken": "XIkOrHqf+lEl0cedQi+OzYM7TTnyliJKDq6gpWZ5q5htQDP7WhUHZA==@thdm.cn.rongnav.com;thdm.cn.rongcfg.com",
         *         "blacklist": 0,
         *         "frozenStatus": 0,
         *         "gmtCreate": "2020-04-20 10:33:21",
         *         "token": "B2AG55O992-B7AKG3UK5TEX99XNJ57A2-ADNVWFGK-1"
         */

        /**
         * userInfo:
         *"userId": 101,
         *         "account": "18669505929",
         *         "nickname": "铁人王进喜",
         *         "headPortrait": "https://www.juntaikeji.com:17002/head_img/ce7066cfd91b47128d20181fd151d97d.jpeg",
         *         "realNameStatus": 2,
         *         "rOngYunToken": "XIkOrHqf+lEl0cedQi+OzYM7TTnyliJKDq6gpWZ5q5htQDP7WhUHZA==@thdm.cn.rongnav.com;thdm.cn.rongcfg.com",
         *         "score": 43,
         *         "blacklist": 0,
         *         "faceTimeType": 0,
         *         "gmtCreate": "2020-04-20 10:33:21",
         *         "qqName": "",
         *         "weChatName": "bbb",
         *         "realName": "顾启杭",
         *         "departmentId": 1,
         *         "departmentName": "东关街派出所",
         *         "departmentBranchId": 1,
         *         "departmentBranchName": "所领导",
         *         "postId": 1,
         *         "postName": "单位领导",
         *         "grid": 6,
         *         "gridName": "永恒华府",
         *         "settleStatus": 2
         */

        private int userId;//用户id
        private String account;//账号
        private String password;//密码
        private String name;//昵称
        private String phoneNumber;//手机号
        private String img;//头像
        private int realNameStatus;//实名认证状态（0未提交；1提交审核中；2审核通过；3审核失败）
        private int settleStatus;//信息审核状态（0未提交；1提交审核中；2审核通过；3审核失败）
        private String rOngYunToken;//融云token rOngYunToken
        private int blacklist;//黑名单状态
        private int frozenStatus;//冻结状态
        private String gmtCreate;//注册时间
        private String token;//验证token
        private int score;//积分
        private int faceTimeType;//用户视频通话状态（0空闲；1忙线）
        private String qqName;//qq昵称
        private String weChatName;//微信昵称
        private String realName;//真实姓名
        private int departmentId;//部门id
        private String departmentName;//部门名称
        private int departmentBranchId;//二级部门id
        private String departmentBranchName;//二级部门名称
        private int postId;//职务id
        private String postName;//职务名称
        private int grid;//网格id
        private String gridName;//网格名称
        private String idNumber;//身份证号

        public String getPhoneNumber() {
            return phoneNumber == null ? "未绑定" : phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber == null ? "" : phoneNumber;
        }

        public String getIdNumber() {
            return idNumber == null? "" : idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account == null? "" : account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password == null? "" : password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img == null? "" : img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getRealNameStatus() {
            return realNameStatus;
        }

        public void setRealNameStatus(int realNameStatus) {
            this.realNameStatus = realNameStatus;
        }

        public int getSettleStatus() {
            return settleStatus;
        }

        public void setSettleStatus(int settleStatus) {
            this.settleStatus = settleStatus;
        }

        public String getrOngYunToken() {
            return rOngYunToken == null? "" : rOngYunToken;
        }

        public void setrOngYunToken(String rOngYunToken) {
            this.rOngYunToken = rOngYunToken;
        }

        public int getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(int blacklist) {
            this.blacklist = blacklist;
        }

        public int getFrozenStatus() {
            return frozenStatus;
        }

        public void setFrozenStatus(int frozenStatus) {
            this.frozenStatus = frozenStatus;
        }

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getToken() {
            return token == null? "" : token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getFaceTimeType() {
            return faceTimeType;
        }

        public void setFaceTimeType(int faceTimeType) {
            this.faceTimeType = faceTimeType;
        }

        public String getQqName() {
            return qqName == null? "" : qqName;
        }

        public void setQqName(String qqName) {
            this.qqName = qqName;
        }

        public String getWeChatName() {
            return weChatName == null? "" : weChatName;
        }

        public void setWeChatName(String weChatName) {
            this.weChatName = weChatName;
        }

        public String getRealName() {
            return realName == null? "" : realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentName() {
            return departmentName == null? "" : departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getDepartmentBranchId() {
            return departmentBranchId;
        }

        public void setDepartmentBranchId(int departmentBranchId) {
            this.departmentBranchId = departmentBranchId;
        }

        public String getDepartmentBranchName() {
            return departmentBranchName == null? "" : departmentBranchName;
        }

        public void setDepartmentBranchName(String departmentBranchName) {
            this.departmentBranchName = departmentBranchName;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getPostName() {
            return postName == null? "" : postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public int getGrid() {
            return grid;
        }

        public void setGrid(int grid) {
            this.grid = grid;
        }

        public String getGridName() {
            return gridName  == null? "" : gridName;
        }

        public void setGridName(String gridName) {
            this.gridName = gridName;
        }
    }
}

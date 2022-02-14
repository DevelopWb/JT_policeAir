package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/9 16:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/9 16:24
 */
public class PolicePickerBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"id":31,"account":"13545678500","nickname":"兰山区接警员","rongYunToken":"35/WfE7yhddtynE97dY3Cud6/SAY2D7LmjTML/i89U4XgHvVRzetwOWrMLUOPAzlgyPfuyqiw7ACOOQFHeQYfYrXUsNFOQcF","headPortrait":"/head_img/default1.jpg"}
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
         * "id": 115,
         *             "account": "12345678906",
         *             "nickname": "接警员",
         *             "rongYunToken": "0t8xTdtufn352JfzwE4yTYM7TTnyliJKe88wseZq8b9tQDP7WhUHZA==@thdm.cn.rongnav.com;thdm.cn.rongcfg.com",
         *             "headPortrait": "https://www.juntaikeji.com:17002/head_img/6c0c62ae85504cf8b0af2de00bf24e69.jpeg",
         *             "faceTimeType": 0
         */

        private int id;//用户id
        private String account;//账号（融云id）
        private String nickname;//昵称
        private String rongYunToken;
        private String headPortrait;//头像
        private int faceTimeType;//用户视频通话状态（0空闲；1忙线）

        public int getFaceTimeType() {
            return faceTimeType;
        }

        public void setFaceTimeType(int faceTimeType) {
            this.faceTimeType = faceTimeType;
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

        public String getRongYunToken() {
            return rongYunToken == null? "" : rongYunToken;
        }

        public void setRongYunToken(String rongYunToken) {
            this.rongYunToken = rongYunToken;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }
    }
}

package com.juntai.wisdom.policeAir.bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * im系统的用户信息
 * Created by Ma
 * on 2019/4/12
 */
public class IMUsersBean extends BaseResult {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IMUsersBean{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {

        /**
         * id : 14
         * account : 15195671260
         * realName : 王杰仔
         * headPortrait : "headPortrait": "https://www.juntaikeji.com:17002/head_img/4acbbf83f3934de486fc0f179eaeeb3b.jpeg"
         */

        private int id;
        private String account;
        private String realName;
        private String headPortrait;

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

        public String getRealName() {
            return realName == null? getAccount() : realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getHeadPortrait() {
            return headPortrait == null? "" : headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }
    }
}

package com.juntai.wisdom.policeAir.bean.conciliation;

import java.io.Serializable;

/**
 * Describe:第三方调解人员
 * Create by zhangzhenlong
 * 2020-5-23
 * email:954101549@qq.com
 */
public class ConciliationPeopleBean implements Serializable {
    private String type;
    private String picUrl;
    private String name;
    private String des;

    public ConciliationPeopleBean(String type, String picUrl, String name) {
        this.type = type;
        this.picUrl = picUrl;
        this.name = name;
    }

    public ConciliationPeopleBean() {
    }

    public String getType() {
        return type == null? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicUrl() {
        return picUrl == null? "" : picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name == null? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des == null? "" : des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}

package com.juntai.wisdom.dgjxb.bean;

import java.io.Serializable;

/**
 * Describe:个人中心菜单数据
 * Create by zhangzhenlong
 * 2020/3/7
 * email:954101549@qq.com
 */
public class MyMenuBean implements Serializable {
    private String name;
    private int number;
    private int imageId;
    private String tag;
    Class<?> cls;

    public MyMenuBean(){}

    public MyMenuBean(String name, int number, int imageId, String tag, Class<?> cls) {
        this.name = name;
        this.number = number;
        this.imageId = imageId;
        this.tag = tag;
        this.cls = cls;
    }

    public MyMenuBean(String name, int number, int imageId) {
        this.name = name;
        this.number = number;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}

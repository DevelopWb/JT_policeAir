package com.example.appbase.bean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SelectTextBean {
    public final static String  SCHOOL_KEY = "school_key";

    private boolean select;
    private String key;
    private Object object;

    public SelectTextBean(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

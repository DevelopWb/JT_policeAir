package com.example.appbase.bean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class BasePicVideoBean {

    public final static int TYPE_NULL = 0;
    public final static int TYPE_IMAGE = 1;
    public final static int TYPE_VIDEO = 2;
    private int type;
    private String url;
    private String cover;
    private int id;

    public BasePicVideoBean(int type) {
        this.type = type;
    }

    public BasePicVideoBean(int type, String url, int id) {
        this.type = type;
        this.url = url;
        this.id = id;
    }

    public BasePicVideoBean(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public BasePicVideoBean(int type, String url, String cover) {
        this.type = type;
        this.url = url;
        this.cover = cover;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? "" : cover;
    }
}

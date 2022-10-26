package com.example.appbase.bean;

/**
 * @Author: tobato
 * @Description: 作用描述  tab实体
 * @UpdateUser: 更新者
 */
public class BaseTabBean {

    private String title;
    private int unreadAmount;

    public BaseTabBean(String title) {
        this.title = title;
    }

    public BaseTabBean(String title, int unreadAmount) {
        this.title = title;
        this.unreadAmount = unreadAmount;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getUnreadAmount() {
        return unreadAmount;
    }

    public void setUnreadAmount(int unreadAmount) {
        this.unreadAmount = unreadAmount;
    }
}

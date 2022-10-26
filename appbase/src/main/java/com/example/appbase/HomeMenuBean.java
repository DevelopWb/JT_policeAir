package com.example.appbase;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class HomeMenuBean {

    public final static String   HOME_MENU_COMPLEX = "综合类";
    public final static String   HOME_MENU_RICE = "大米(杂粮)";
    public final static String   HOME_MENU_FLOUR = "面粉";
    public final static String   HOME_MENU_OIL = "食用油";
    public final static String   HOME_MENU_POULTRY = "禽肉、水(海)产品";
    public final static String   HOME_MENU_MEAT = "生鲜肉";
    public final static String   HOME_MENU_MILK = "牛奶";
    public final static String   HOME_MENU_EGG = "蛋类";
    public final static String   HOME_MENU_CONDIMENT = "调味品、干货";
    public final static String   HOME_MENU_OTHER = "其他";


    private String title;
    private String des;
    private int res;
    private int color;
    private int categoryId;

    public HomeMenuBean(String title, String des, int res, int color, int categoryId) {
        this.title = title;
        this.des = des;
        this.res = res;
        this.color = color;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getDes() {
        return des == null ? "" : des;
    }

    public void setDes(String des) {
        this.des = des == null ? "" : des;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

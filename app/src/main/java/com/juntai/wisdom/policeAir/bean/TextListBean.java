package com.juntai.wisdom.policeAir.bean;

/**
 * 左右文字的列表
 * Created by Ma
 * on 2019/4/18
 */
public class TextListBean {
    private String left;
    private String right;
    private int type;

    public TextListBean(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

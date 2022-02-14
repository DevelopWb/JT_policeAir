package com.juntai.wisdom.basecomponent.bean;

/**
 * Describe:更多菜单（分享等）
 * Create by zhangzhenlong
 * 2021-1-12
 * email:954101549@qq.com
 */
public class MoreMenuBean {
    private String name;
    private String imageUrl;
    private int imageId;//R.mipmap.more
    private boolean workAble = true;//是否可用
    private boolean isChoose = false;//是否已选中

    public MoreMenuBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public MoreMenuBean(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public MoreMenuBean(String name, int imageId, boolean isChoose) {
        this.name = name;
        this.imageId = imageId;
        this.isChoose = isChoose;
    }

    public MoreMenuBean(String name, int imageId, boolean isChoose, boolean workAble) {
        this.name = name;
        this.imageId = imageId;
        this.workAble = workAble;
        this.isChoose = isChoose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isWorkAble() {
        return workAble;
    }

    public void setWorkAble(boolean workAble) {
        this.workAble = workAble;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}

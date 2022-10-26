package com.example.appbase.base.customview.selectPics;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SelectPicVideoBean {

    public static  final  int TYPE_NULL = -1;
    public static  final  int TYPE_IMAGE = 0;
    public static  final  int TYPE_VIDEO = 1;

    /**
     * -1代表什么都没有 0是图片  1是视频
     */
    private int type;
    /**
     * 路径
     */
    private String path;

    /**
     * 视频封面图
     */
    private String coverPic;
    /**
     * 文字描述
     */
    private String tag;

    public SelectPicVideoBean(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public SelectPicVideoBean(int type, String path, String coverPic) {
        this.type = type;
        this.path = path;
        this.coverPic = coverPic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public void setPath(String path) {
        this.path = path == null ? "" : path;
    }

    public String getCoverPic() {
        return coverPic == null ? "" : coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic == null ? "" : coverPic;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? "" : tag;
    }
}

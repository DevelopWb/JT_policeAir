package com.juntai.wisdom.policeAir.bean.news;

/**
 * Describe:富文本资讯草稿
 * Create by zhangzhenlong
 * 2020-8-8
 * email:954101549@qq.com
 */
public class NewsDraftsBean {
    private String draftsId;
    private String htmlContent;//文本
    private String title;//标题

    public NewsDraftsBean(String draftsId, String htmlContent, String title) {
        this.draftsId = draftsId;
        this.htmlContent = htmlContent;
        this.title = title;
    }

    public String getDraftsId() {
        return draftsId;
    }

    public void setDraftsId(String draftsId) {
        this.draftsId = draftsId;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

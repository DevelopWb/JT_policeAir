package com.example.appbase.bean.nong_fa_manager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditySourceBean {

    private  String title;
    private List<String> pics;

    public CommoditySourceBean(String title, List<String> pics) {
        this.title = title;
        this.pics = pics;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public List<String> getPics() {
        if (pics == null) {
            return new ArrayList<>();
        }
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}

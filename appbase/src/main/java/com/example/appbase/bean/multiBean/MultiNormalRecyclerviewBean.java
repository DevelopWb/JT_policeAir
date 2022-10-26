package com.example.appbase.bean.multiBean;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/7/3 11:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/7/3 11:03
 */
public class MultiNormalRecyclerviewBean {


    private  String  key ;
    private Object  object ;
   private BaseQuickAdapter  baseQuickAdapter;

    public MultiNormalRecyclerviewBean(String key, Object object, BaseQuickAdapter baseQuickAdapter) {
        this.key = key;
        this.object = object;
        this.baseQuickAdapter = baseQuickAdapter;
    }

    public BaseQuickAdapter getBaseQuickAdapter() {
        return baseQuickAdapter;
    }

    public void setBaseQuickAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.baseQuickAdapter = baseQuickAdapter;
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

package com.example.appbase.bean.multiBean;

/**
 * @Author: tobato
 * @Description: 作用描述 radiobutton
 * @CreateDate: 2021/1/16 15:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/16 15:22
 */
public class MultiRadioBean {

    private String key;
    private String[] values;
    private int  defaultSelectedIndex ;

    public MultiRadioBean(String key, String[] values,  int defaultSelectedIndex) {
        this.key = key;
        this.values = values;
        this.defaultSelectedIndex = defaultSelectedIndex;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public int getDefaultSelectedIndex() {
        return defaultSelectedIndex;
    }

    public void setDefaultSelectedIndex(int defaultSelectedIndex) {
        this.defaultSelectedIndex = defaultSelectedIndex;
    }
}

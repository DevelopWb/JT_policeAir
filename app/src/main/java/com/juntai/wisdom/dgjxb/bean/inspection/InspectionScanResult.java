package com.juntai.wisdom.dgjxb.bean.inspection;

/**
 * @Author: tobato
 * @Description: 作用描述  巡检扫描结果
 * @CreateDate: 2020/5/9 14:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/9 14:42
 */
public class InspectionScanResult {

    /**
     * address : 山东省临沂市河东区滨河东路
     * code : 2
     * id : 362
     * latitude : 35.1003698
     * longitude : 118.3926777
     * name : 临沂交通运输投资集团有限公司幼儿园3
     */

    private String address;
    private int code;
    private int id;
    private double latitude;
    private double longitude;
    private String name;

    public String getAddress() {
        return address == null? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name == null? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

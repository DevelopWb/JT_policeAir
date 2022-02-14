package com.juntai.wisdom.dgjxb.bean.history_track;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Describe:本地位置缓存
 * Create by zhangzhenlong
 * 2020-10-24
 * email:954101549@qq.com
 */
@Entity
public class LocationBean {
    @Id(autoincrement = true)
    private Long id;
    private String address;//地址
    private String posType;//卫星定位-GPS, 网络NET
    private String longitude;
    private String latitude;
    @Unique
    private String gmtCreate;

    public LocationBean(String address, String posType, String longitude, String latitude, String gmtCreate) {
        this.address = address;
        this.posType = posType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.gmtCreate = gmtCreate;
    }


    @Generated(hash = 1697911461)
    public LocationBean(Long id, String address, String posType, String longitude, String latitude,
            String gmtCreate) {
        this.id = id;
        this.address = address;
        this.posType = posType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.gmtCreate = gmtCreate;
    }


    @Generated(hash = 516751439)
    public LocationBean() {
    }


    public String getAddress() {
        return address == null? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosType() {
        return posType == null? "" : posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getLongitude() {
        return longitude == null? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude == null? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGmtCreate() {
        return gmtCreate == null? "" : gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

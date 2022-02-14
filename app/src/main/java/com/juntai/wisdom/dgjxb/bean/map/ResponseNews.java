package com.juntai.wisdom.dgjxb.bean.map;

import android.os.Parcelable;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Describe:资讯坐标点
 * Create by zhangzhenlong
 * 2020-3-21
 * email:954101549@qq.com
 */
public class ResponseNews extends BaseResult implements Serializable {
    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": [
     *         {
     *
     *         }
     *     ],
     *     "type": null,
     *     "message": "成功",
     *     "success": true
     * }
     */

    private List<News> data;

    public List<News> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    public static class News{

        /**
         * "id": 14,
         *             "typeId": 2,
         *             "title": "中国乳业风云起，看“新势力”如何“虎口夺食”",
         *             "latitude": 35.0905260,
         *             "longitude": 118.4021130,
         *             "gmtCreate": "2020-08-19 17:06:58",
         *             "nickName": "韩国杰",
         *             "picture": "https://image.juntaikeji.com/2020-08-19/2b960ec423db4f05a6ee8aa615bebc80.jpg"
         */

        private int id;
        private int typeId;//1视频，2图文
        private double longitude;
        private double latitude;
        private String title;
        private String nickName;
        private String picture;
        private String gmtCreate;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getTitle() {
            return title == null? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNickName() {
            return nickName == null? "" : nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPicture() {
            return picture  == null? "" : picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
    }
}

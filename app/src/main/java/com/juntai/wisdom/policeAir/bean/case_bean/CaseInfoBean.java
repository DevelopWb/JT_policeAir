package com.juntai.wisdom.policeAir.bean.case_bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  案件信息  追踪界面
 * @CreateDate: 2020/3/26 14:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/26 14:57
 */
public class CaseInfoBean extends BaseResult {
    private DataBean data;

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * status : 200
     * type : null
     * message : 成功
     * success : true
     */


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private int id;//主案件id
        private String address;//案件地址
        private String happenDate;//案件时间
        private int bigType;//案件类型id
        private String bigName;//案件类型
        private int smallType;//案件种类id
        private String smallName;//案件种类
        private int grid;//网格id
        private String gridName;//所属网格
        private int caseType;//案件状态（1待处理；2处理中；3已完成）
        private String caseContent;//案件描述
        private String provinceCode;//省代码
        private String cityCode;//市代码
        private String areaCode;//县区代码
        private double latitude;//维度
        private double longitude;//经度
        private String caseAcceptTime;//接警中心受理时间
        private String photoOne;//图片
        private String photoTwo;//图片
        private String photoThree;//图片
        private String video;//视频
        private List<CaseChildListBean> caseChildList;//子案件列表

        public String getProvinceCode() {
            return provinceCode == null? "" : provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getCityCode() {
            return cityCode == null? "" : cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAreaCode() {
            return areaCode == null? "" : areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public int getGrid() {
            return grid;
        }

        public void setGrid(int grid) {
            this.grid = grid;
        }

        public String getGridName() {
            return gridName == null ? "无" : gridName;
        }

        public void setGridName(String gridName) {
            this.gridName = gridName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address == null? "无" : address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHappenDate() {
            return happenDate == null? "无" : happenDate;
        }

        public void setHappenDate(String happenDate) {
            this.happenDate = happenDate;
        }

        public int getBigType() {
            return bigType;
        }

        public void setBigType(int bigType) {
            this.bigType = bigType;
        }

        public String getBigName() {
            return bigName  == null? "无" : bigName;
        }

        public void setBigName(String bigName) {
            this.bigName = bigName;
        }

        public int getSmallType() {
            return smallType;
        }

        public void setSmallType(int smallType) {
            this.smallType = smallType;
        }

        public String getSmallName() {
            return smallName == null? "无" : smallName;
        }

        public void setSmallName(String smallName) {
            this.smallName = smallName;
        }

        public int getCaseType() {
            return caseType;
        }

        public void setCaseType(int caseType) {
            this.caseType = caseType;
        }

        public String getCaseContent() {
            return caseContent == null? "无" : caseContent;
        }

        public void setCaseContent(String caseContent) {
            this.caseContent = caseContent;
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

        public String getPhotoOne() {
            return photoOne == null? "" : photoOne;
        }

        public void setPhotoOne(String photoOne) {
            this.photoOne = photoOne;
        }

        public String getPhotoTwo() {
            return photoTwo == null? "" : photoTwo;
        }

        public void setPhotoTwo(String photoTwo) {
            this.photoTwo = photoTwo;
        }

        public String getPhotoThree() {
            return photoThree == null? "" : photoThree;
        }

        public void setPhotoThree(String photoThree) {
            this.photoThree = photoThree;
        }

        public String getVideo() {
            return video == null? "" : video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getCaseAcceptTime() {
            return caseAcceptTime == null ? "" : caseAcceptTime;
        }

        public void setCaseAcceptTime(String caseAcceptTime) {
            this.caseAcceptTime = caseAcceptTime == null ? "" : caseAcceptTime;
        }

        public List<CaseChildListBean> getCaseChildList() {
            if (caseChildList == null) {
                return new ArrayList<>();
            }
            return caseChildList;
        }

        public void setCaseChildList(List<CaseChildListBean> caseChildList) {
            this.caseChildList = caseChildList;
        }

        public static class CaseChildListBean implements Serializable {
            /**
             *  "childId": 3817,
             *                 "address": "山东省临沂市兰山区东风巷",
             *                 "happenDate": "2020-05-01 15:24:41",
             *                 "caseContent": "内容描述22222",
             *                 "photoOne": "https://www.juntaikeji.com:17002/thumbnail/caseImg/05ffc32ba41f46a1a0069e7ee8227914.jpeg"
             */

            private int childId;
            private String address;
            private String happenDate;
            private String photoOne;
            private String caseContent;

            public int getChildId() {
                return childId;
            }

            public void setChildId(int childId) {
                this.childId = childId;
            }

            public String getAddress() {
                return address == null? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHappenDate() {
                return happenDate == null? "" : happenDate;
            }

            public void setHappenDate(String happenDate) {
                this.happenDate = happenDate;
            }

            public String getPhotoOne() {
                return photoOne == null? "" : photoOne;
            }

            public void setPhotoOne(String photoOne) {
                this.photoOne = photoOne;
            }

            public String getCaseContent() {
                return caseContent == null? "" : caseContent;
            }

            public void setCaseContent(String caseContent) {
                this.caseContent = caseContent;
            }
        }
    }
}

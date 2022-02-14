package com.juntai.wisdom.policeAir.bean.case_bean;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述   案件描述 地图画点用
 * @CreateDate: 2020/3/27 9:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/27 9:59
 */
public class CaseDesBean  extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : [{"id":3739,"longitude":118.402087,"latitude":35.090357,"reportDate":"2020-03-26 17:18:32","place":"临沂市河东区人民大街","picture":"/caseImg/3ba0029723864a7f90af181ae2a3aabd.jpeg"},{"id":3740,"longitude":118.3949864,"latitude":35.0921669,"reportDate":"2020-03-26 17:20:16","place":"山东省临沂市河东区东夷大街","picture":"/caseImg/7e402866de1c4f2ca4e4cc500937d34d.jpeg"},{"id":3741,"longitude":118.402052,"latitude":35.090375,"reportDate":"2020-03-27 09:19:14","place":"临沂市河东区人民大街","picture":"/caseImg/78394d36e9e54ab99b8c782e816b9b79.jpeg"}]
     * type : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null? new ArrayList<>() :data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         "id": 3816,
         "address": "临沂市兰山区琅琊街",
         "latitude": 35.0708230,
         "longitude": 118.3635760,
         "caseContent": "按时飞洒发三份",
         "createDate": "2020-05-01 15:24:23",
         "photoOne": "https://www.juntaikeji.com:17002/thumbnail/caseImg/b4687f8999a54d30b941cd237ca21aff.jpeg"
         */

        private int id;//案件id
        private String address;//案件地址
        private double latitude;//维度
        private double longitude;//经度
        private String createDate;//发布时间
        private String photoOne;//案件封面图片
        private String caseContent;//案件细节

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address == null? "" : address;
        }

        public void setAddress(String address) {
            this.address = address;
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


        public String getCreateDate() {
            return createDate == null? "" : createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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

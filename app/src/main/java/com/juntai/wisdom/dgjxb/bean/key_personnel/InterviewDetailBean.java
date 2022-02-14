package com.juntai.wisdom.dgjxb.bean.key_personnel;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:走访记录详情
 * Create by zhangzhenlong
 * 2020-7-3
 * email:954101549@qq.com
 */
public class InterviewDetailBean extends BaseResult {
    /**
     * {
     *     "error": null,
     *     "returnValue": null,
     *     "msg": null,
     *     "code": null,
     *     "status": 200,
     *     "data": {
     *         "id": 1,
     *         "keyPersonnel": "王淑燕",
     *         "logPlace": "兰山区解放路",
     *         "longitude": 118.3623060,
     *         "latitude": 35.0722330,
     *         "content": "重点上访人员生活困难，需要生活困难补助，曾多次到各级政府部门反映；西关司法所分别到住所走访,认真听取其意见诉求，了解了其思想状况，并对其所提问题耐心地予以解答。同时，关切询问了上访人员近期的生活状况和实际困难。此次走访效果明显，达到了预期目的。",
     *         "logUser": null,
     *         "gmtCreate": "2020-07-03 09:12:54",
     *         "kFileDos": [
     *              {
     *                 "id": 2,
     *                 "url": "/KeyPersonnelImg/7cd184f9ac3a442cbcf5065d7e7ea17a.mp4",
     *                 "flag": true
     *             }
     *             {
     *                 "id": 1,
     *                 "keyPersonnelLogId": null,
     *                 "url": "/KeyPersonnelImg/fa676a0b19c14d5eb96ffd17d999407f.jpeg",
     *                 "flag": false
     *             }
     *         ]
     *     },
     *     "type": null,
     *     "message": null,
     *     "success": true
     * }
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable {

        /**
         * id : 1
         * keyPersonnel : 王淑燕
         * logPlace : 兰山区解放路
         * longitude : 118.362306
         * latitude : 35.072233
         * content : 重点上访人员生活困难，需要生活困难补助，曾多次到各级政府部门反映；西关司法所分别到住所走访,认真听取其意见诉求，了解了其思想状况，并对其所提问题耐心地予以解答。同时，关切询问了上访人员近期的生活状况和实际困难。此次走访效果明显，达到了预期目的。
         * logUser : null
         * gmtCreate : 2020-07-03 09:12:54
         * kFileDos : [{"id":1,"keyPersonnelLogId":null,"url":"/KeyPersonnelImg/fa676a0b19c14d5eb96ffd17d999407f.jpeg","flag":false},{"id":2,"keyPersonnelLogId":null,"url":"/KeyPersonnelImg/7cd184f9ac3a442cbcf5065d7e7ea17a.mp4","flag":true}]
         */

        private int id;//走访记录id
        private String keyPersonnel;//重点人员
        private String logPlace;//走访地点
        private double longitude;//经度
        private double latitude;//纬度
        private String content;//走访记录
        private String logUser;//走访人
        private String gmtCreate;//走访时间
        private List<KFileDosBean> kFileDos;//走访记录文件

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeyPersonnel() {
            return keyPersonnel == null? "" : keyPersonnel;
        }

        public void setKeyPersonnel(String keyPersonnel) {
            this.keyPersonnel = keyPersonnel;
        }

        public String getLogPlace() {
            return logPlace == null? "" : logPlace;
        }

        public void setLogPlace(String logPlace) {
            this.logPlace = logPlace;
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

        public String getContent() {
            return content == null? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLogUser() {
            return logUser == null? "" : logUser;
        }

        public void setLogUser(String logUser) {
            this.logUser = logUser;
        }

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public List<KFileDosBean> getKFileDos() {
            if (kFileDos == null){
                kFileDos = new ArrayList<>();
            }
            return kFileDos;
        }

        public void setKFileDos(List<KFileDosBean> kFileDos) {
            this.kFileDos = kFileDos;
        }

        public static class KFileDosBean {
            /**
             * id : 1
             * keyPersonnelLogId : null
             * url : /KeyPersonnelImg/fa676a0b19c14d5eb96ffd17d999407f.jpeg
             * flag : false
             */

            private int id;//文件id
            private String url;//文件地址
            private boolean flag;//标识(0/false:照片；1/true：视频)

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url == null? "" : url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }
        }
    }
}

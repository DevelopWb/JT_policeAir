package com.juntai.wisdom.policeAir.bean.site;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.List;

/**
 * Describe:单位检查记录详情
 * Create by zhangzhenlong
 * 2020-7-6
 * email:954101549@qq.com
 */
public class SiteInspectionDetailBean extends BaseResult {

    /**
     * error : null
     * returnValue : null
     * msg : null
     * code : null
     * data : {"id":2,"address":"临沂河东","gmtCreate":"2020-07-05 14:07:09","name":"铁人王进喜","remake":"测试","videoUrl":"https://image.juntaikeji.com/2020-07-05/0a6c20b1bcb94c71958ebb56d9a5064d.mp4","file":[{"fileUrl":"https://image.juntaikeji.com/2020-07-05/45a5d318429540c7920ab6e578ff9d6f.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/954c60ff2e05430daaf2ab4ff3fa6f87.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/0481d9b6d4bf4763897b8b63f59b0931.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/6111303ba8424002adbaab183214fb0f.jpg"}]}
     * type : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * address : 临沂河东
         * gmtCreate : 2020-07-05 14:07:09
         * name : 铁人王进喜
         * remake : 测试
         * videoUrl : https://image.juntaikeji.com/2020-07-05/0a6c20b1bcb94c71958ebb56d9a5064d.mp4
         * file : [{"fileUrl":"https://image.juntaikeji.com/2020-07-05/45a5d318429540c7920ab6e578ff9d6f.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/954c60ff2e05430daaf2ab4ff3fa6f87.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/0481d9b6d4bf4763897b8b63f59b0931.jpg"},{"fileUrl":"https://image.juntaikeji.com/2020-07-05/6111303ba8424002adbaab183214fb0f.jpg"}]
         */

        private int id;
        private String address;
        private String gmtCreate;
        private String name;
        private String remake;
        private String videoUrl;
        private List<FileBean> file;

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

        public String getGmtCreate() {
            return gmtCreate == null? "" : gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getName() {
            return name == null? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemake() {
            return remake == null? "" : remake;
        }

        public void setRemake(String remake) {
            this.remake = remake;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public List<FileBean> getFile() {
            return file;
        }

        public void setFile(List<FileBean> file) {
            this.file = file;
        }

        public static class FileBean {
            /**
             * fileUrl : https://image.juntaikeji.com/2020-07-05/45a5d318429540c7920ab6e578ff9d6f.jpg
             */

            private String fileUrl;

            public String getFileUrl() {
                return fileUrl == null? "" : fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }
        }
    }
}

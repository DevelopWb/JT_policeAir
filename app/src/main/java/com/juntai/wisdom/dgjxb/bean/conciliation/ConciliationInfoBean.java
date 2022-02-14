package com.juntai.wisdom.dgjxb.bean.conciliation;

import com.juntai.wisdom.basecomponent.base.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:调解详情
 * Create by zhangzhenlong
 * 2020-5-23
 * email:954101549@qq.com
 */
public class ConciliationInfoBean extends BaseResult {

    /**
     * error : null
     * data : {"id":265,"caseNumber":"804342","applicantName":"马云","applicantPhone":"18669505929","applicantAddress":"临沂市河东区人民大街","respondentName":"马化腾","respondentPhone":"987654321","introduction":"打车大战，滴滴对战快的","bigTypeId":1,"smallTypeId":5,"regionId":2,"unitId":1,"policeId":6,"peopleId":31,"lawyerId":11,"videoUrl":"https://image.juntaikeji.com/2020-07-09/b390657faad84456990e2e0319fc49c6.mp4","evidence":[{"fileId":279,"fileUrl":"https://image.juntaikeji.com/2020-07-09/fb8756419f5846db924692efd81faa19.jpg"},{"fileId":280,"fileUrl":"https://image.juntaikeji.com/2020-07-09/75acabac2bf349b49d00132f062e0f74.jpg"}]}
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
         * id : 265
         * caseNumber : 804342
         * applicantName : 马云
         * applicantPhone : 18669505929
         * applicantAddress : 临沂市河东区人民大街
         * respondentName : 马化腾
         * respondentPhone : 987654321
         * introduction : 打车大战，滴滴对战快的
         * bigTypeId : 1
         * smallTypeId : 5
         * regionId : 2
         * unitId : 1
         * policeId : 6
         * peopleId : 31
         * lawyerId : 11
         * videoUrl : https://image.juntaikeji.com/2020-07-09/b390657faad84456990e2e0319fc49c6.mp4
         * evidence : [{"fileId":279,"fileUrl":"https://image.juntaikeji.com/2020-07-09/fb8756419f5846db924692efd81faa19.jpg"},{"fileId":280,"fileUrl":"https://image.juntaikeji.com/2020-07-09/75acabac2bf349b49d00132f062e0f74.jpg"}]
         */

        private int id;
        private String caseNumber;//案件编号（房间号）
        private String applicantName;//申请人姓名
        private String applicantPhone;//申请人电话
        private String applicantAddress;//申请人住址
        private String respondentName;//被申请人姓名
        private String respondentPhone;//被申请人电话
        private String introduction;//简介
        private String videoUrl;//视频地址
        private String bigName;//大类型
        private String smallName;//小类型
        private String unitName;//部门名称
        private String policeName;//警员姓名
        private String policeHeadPortrait;//警员头像
        private String lawyerName;//律师姓名
        private String lawyerHeadPortrait;//律师头像
        private String peopleName;//人员姓名
        private String peopleHeadPortrait;//人员头像
        private List<EvidenceBean> evidence;//文件列表

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCaseNumber() {
            return caseNumber == null? "" : caseNumber;
        }

        public void setCaseNumber(String caseNumber) {
            this.caseNumber = caseNumber;
        }

        public String getApplicantName() {
            return applicantName == null? "" : applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getApplicantPhone() {
            return applicantPhone  == null? "" : applicantPhone;
        }

        public void setApplicantPhone(String applicantPhone) {
            this.applicantPhone = applicantPhone;
        }

        public String getApplicantAddress() {
            return applicantAddress  == null? "" : applicantAddress;
        }

        public void setApplicantAddress(String applicantAddress) {
            this.applicantAddress = applicantAddress;
        }

        public String getRespondentName() {
            return respondentName == null? "" : respondentName;
        }

        public void setRespondentName(String respondentName) {
            this.respondentName = respondentName;
        }

        public String getRespondentPhone() {
            return respondentPhone == null? "" : respondentPhone;
        }

        public void setRespondentPhone(String respondentPhone) {
            this.respondentPhone = respondentPhone;
        }

        public String getIntroduction() {
            return introduction == null? "" : introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getBigName() {
            return bigName == null? "" : bigName;
        }

        public void setBigName(String bigName) {
            this.bigName = bigName;
        }

        public String getSmallName() {
            return smallName == null? "" : smallName;
        }

        public void setSmallName(String smallName) {
            this.smallName = smallName;
        }

        public String getUnitName() {
            return unitName == null? "" : unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public String getPoliceName() {
            return policeName == null? "" : policeName;
        }

        public void setPoliceName(String policeName) {
            this.policeName = policeName;
        }

        public String getPoliceHeadPortrait() {
            return policeHeadPortrait == null? "" : policeHeadPortrait;
        }

        public void setPoliceHeadPortrait(String policeHeadPortrait) {
            this.policeHeadPortrait = policeHeadPortrait;
        }

        public String getLawyerName() {
            return lawyerName == null? "" : lawyerName;
        }

        public void setLawyerName(String lawyerName) {
            this.lawyerName = lawyerName;
        }

        public String getLawyerHeadPortrait() {
            return lawyerHeadPortrait == null? "" : lawyerHeadPortrait;
        }

        public void setLawyerHeadPortrait(String lawyerHeadPortrait) {
            this.lawyerHeadPortrait = lawyerHeadPortrait;
        }

        public String getPeopleName() {
            return peopleName == null? "" : peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public String getPeopleHeadPortrait() {
            return peopleHeadPortrait == null? "" : peopleHeadPortrait;
        }

        public void setPeopleHeadPortrait(String peopleHeadPortrait) {
            this.peopleHeadPortrait = peopleHeadPortrait;
        }

        public String getVideoUrl() {
            return videoUrl == null? "" : videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public List<EvidenceBean> getEvidence() {
            if (evidence == null){
                evidence = new ArrayList<>();
            }
            return evidence;
        }

        public void setEvidence(List<EvidenceBean> evidence) {
            this.evidence = evidence;
        }

        public static class EvidenceBean {
            /**
             * fileId : 279
             * fileUrl : https://image.juntaikeji.com/2020-07-09/fb8756419f5846db924692efd81faa19.jpg
             */

            private int fileId;//
            private String fileUrl;//

            public int getFileId() {
                return fileId;
            }

            public void setFileId(int fileId) {
                this.fileId = fileId;
            }

            public String getFileUrl() {
                return fileUrl == null? "" : fileUrl;
            }

            public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
            }
        }
    }
}

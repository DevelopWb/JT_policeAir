package com.juntai.wisdom.policeAir.home_page;

import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * Describe:发布关联类
 * Create by zhangzhenlong
 * 2020-3-16
 * email:954101549@qq.com
 */
public interface PublishContract {
    int REQUEST_CODE_CHOOSE_PLACE = 0x6;//位置
    int SELECT_VIDEO_RESULT = 0x7;//视频
    String  GET_CASE_TYPE = "get_case_type";
    String  PUBLISH_CASE = "public_case";
    String  PUBLISH_INSPECTION = "public_inspection";
    String  PUBLISH_NEWS = "public_news";
    String  FOLLOW_CASE = "follow_case";
    String CASE_INFO = "case_info";
    String  PUBLISH_TASK_REPORT = "public_task_report";
    String  PUBLISH_INTERVIEW = "public_interview";
    //扫码巡检，根据id获取巡检点信息
    String  GET_INSPECTION_INFO_SCAN = "get_inspection_info_scan";


    interface IPublishView extends IView{}
    interface IPublishPresent{

        /**
         * 发布案件
         * @param tag
         * @param requestBody
         */
        void  publishCase(String tag, RequestBody requestBody);

        /**
         * 获取案件类型
         * @param tag
         * @param id
         */
        void  getCaseType(String tag,int id);


        /**
         * 发布巡检
         * @param tag
         * @param requestBody
         */
        void publishInspection(String tag, RequestBody requestBody);

        /**
         * 任务上报
         * @param tag
         * @param requestBody
         */
        void publishTaskReport(String tag, RequestBody requestBody);
        /**
         * 重点人员走访上传
         * @param tag
         * @param requestBody
         */
        void publishInterview (String tag, RequestBody requestBody);

        /**
         * 根据id获取巡检点信息（扫码）
         * @param tag
         * @param id
         */
        void getInspectionInfo (String tag, String id);
    }

}

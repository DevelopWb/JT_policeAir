package com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation;

import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * Describe:调解关联类
 * Create by zhangzhenlong
 * 2020-5-22
 * email:954101549@qq.com
 */
public interface ConciliationContract {
    int SELECT_VIDEO_RESULT = 0x7;//视频
    String PUBLISH_CONCILIATION = "public_conciliation";
    String GET_CONCILIATION_INFO = "get_conciliation_info";
    String GET_UNIT_LIST = "get_unit_list";
    String GET_CONCILIATION_TYPE_LIST = "get_conciliation_type_list";
    String GET_CHILD_TYPE_LIST = "get_child_type_list";
    String GET_ALL_MEDIATOR_LIST = "get_all_mediator_list";

    String GET_CONCILIATION_LIST = "get_conciliation_list";//获取调解申请列表

    interface IConciliationView extends IView {
    }

    interface IConciliationPresent {
        /**
         * 申请调解
         *
         * @param tag
         * @param requestBody
         */
        void publishConciliation(String tag, RequestBody requestBody);

        /**
         * 获取单位列表
         * @param cityNumber
         * @param tag
         */
        void getUnitList(String cityNumber, String tag, boolean showProgress);

        /**
         * 获取全部调解员列表（申请调解用）
         * @param unitId 单位id
         * @param tag
         */
        void getMediatorList(String cityNumber, int unitId, String tag);

        /**
         * 获取调解类型
         * @param typeId id
         * @param tag
         */
        void getConciliationTypeList(int typeId, String tag, boolean showProgress);

        /**
         * 获取调解详情
         *
         * @param tag
         */
        void getConciliationInfo(int id, String tag);

        /**
         * 查询案件编号是否正确
         * @param caseNumber
         * @param tag
         */
        void selectCaseNumberIsCorrect(String caseNumber, String tag);

        /**
         * 获取我的调解列表
         *
         * @param tag
         * @param showProgress
         */
        void getMyConciliationList(int type, int pageSize, int currentPage, String tag, boolean showProgress);
    }
}

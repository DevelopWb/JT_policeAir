package com.juntai.wisdom.dgjxb.home_page.site_manager;

import com.juntai.wisdom.basecomponent.mvp.IView;

import okhttp3.RequestBody;

/**
 * Describe:场所管理关联类
 * Create by zhangzhenlong
 * 2020-5-22
 * email:954101549@qq.com
 */
public interface SiteManagerContract {
    String GET_SITE_INSPECTTION_LIST = "get_site_inspection_list";
    String GET_EMPLOYEE_LIST = "get_employee_list";
    String GET_SITE_TYPE_LIST = "get_site_type_list";
    String GET_CUSTOMER_SOURCE_LIST = "get_customer_source_list";
    String ADD_SITE_MANAGER = "add_site_manager";
    String UPDATE_SITE_MANAGER = "update_site_manager";

    interface ISiteManagerView extends IView {
    }

    interface SiteManagerPresent {
        /**
         * 获取单位从业人员列表
         * @param tag
         * @param id
         */
        void getEmployeeList(int id, int pageSize, int pageNum, String tag, boolean showProgress);
        /**
         * 获取从业人员详情
         * @param tag
         * @param id
         */
        void getEmployeeDetail(String tag, int id);
        /**
         * 获取单位检查记录列表
         * @param tag
         * @param id
         */
        void getSiteInspectionList(int id, int pageSize, int pageNum, String tag, boolean showProgress);
        /**
         * 获取单位检查记录详情
         * @param tag
         * @param id
         */
        void getSiteInspectionDetail(String tag, int id);
        /**
         * 添加从业人员
         * @param tag
         * @param requestBody
         */
        void addEmployee (String tag, RequestBody requestBody);
        /**
         * 添加检查记录
         * @param tag
         * @param requestBody
         */
        void addSiteInspection (String tag, RequestBody requestBody);

        /**
         * 获取单位类型
         * @param tag
         * @param requestBody
         */
        void getSiteTypes (String tag, RequestBody requestBody);

        /**
         * 获取客户来源
         * @param tag
         * @param requestBody
         */
        void getCustomerSources (String tag, RequestBody requestBody);

        /**
         * 添加场所
         * @param tag
         * @param requestBody
         */
        void addSiteManager (String tag, RequestBody requestBody);
        /**
         * 修改场所
         * @param tag
         * @param requestBody
         */
        void updateSiteManager (String tag, RequestBody requestBody);
    }
}

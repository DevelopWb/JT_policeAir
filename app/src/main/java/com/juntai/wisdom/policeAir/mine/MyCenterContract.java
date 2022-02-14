package com.juntai.wisdom.policeAir.mine;

import com.juntai.wisdom.basecomponent.mvp.IView;
import com.juntai.wisdom.policeAir.bean.MyMenuBean;

import java.io.File;
import java.util.List;

/**
 * Describe: 个人信息接口类
 * Create by zhangzhenlong
 * 2020/3/7
 * email:954101549@qq.com
 */
public interface MyCenterContract {

    String USER_DATA_TAG = "getUserData";//获取用户信息的标识
    String LOGIN_OUT_TAG = "loginOutData";//退出登录的标识
    String GET_UNREAD_COUNT = "get_unread_count";// 获取未读消息数

    //个人中心相关
    String CENTER_SCORE_TAG = "centerScoreTag";
    String CENTER_ORDER_TAG ="centerOrderTag";
    String CENTER_PINGJIA_TAG = "centerPingJiaTag";
    String CENTER_SHOUCANG_TAG ="centerShouCangTag";
    String CENTER_SHARE_TAG = "centerShareTag";
    String CENTER_FABU_TAG ="centerFabuTag";
    String CENTER_MESSAGE_TAG = "centerMessageTag";
    String CENTER_SETTING_TAG ="centerSettingTag";
    String CENTER_MISSION_TAG = "centerMissionTag";//任务
    String CENTER_TIAOJIE_TAG = "centerTiaoJieTag";//调解
    String CENTER_DEVICE_TAG = "centerDeviceTag";//我的设备


    //设置相关
    String SET_UPDATE_PSD_TAG = "setUpdatePsdTag";
    String SET_ADDRESS_TAG ="setAddressTag";
    String SET_UPDATE_TEL_TAG = "setUpdateTelTag";
    String SET_CLEAR_TAG ="setClearTag";
    String SET_UPDATE_TAG = "setUpdateTag";
    String SET_WEIXIN_TAG ="setWeiXinTag";
    String SET_QQ_TAG = "setQQTag";
    String SET_ABOUT_TAG ="setAboutTag";
    //修改头像
    String YASUO_HEAD_TAG ="yaSuoHeadTag";//压缩头像
    String UPDATE_HEAD_TAG ="updateHeadTag";//上传头像
    //收藏、分享
    String LOAD_COLLECT_LIST = "loadCollectList";//加载数据
    String DELETE_COLLECT_DATA = "DeleteCollectData";//删除
    //我的发布
    String LOAD_PUBLISH_LIST = "loadPublishList";//加载数据
    String DELETE_PUBLISH_DATA = "DeletePublishData";//删除
    //我的积分
    String GET_SCORE_DATA = "getScoreDetail";//获取积分明细
    //我的任务列表
    String GET_TASK_LIST = "getTaskList";//获取任务列表
    //任务详情
    String GET_TASK_DETAIL = "getTaskDetail";//获取任务详情

    /*---------------个人中心----------------*/
//    interface ICenterModel {
//    }

    interface ICenterView extends IView {
        void refreshAdapter();
    }

    interface ICenterPresent {
        /**
         * 获取用户信息
         * @param tag
         */
        void getUserData(String tag);
        /**
         * 获取未读消息数
         * @param tag
         */
        void getUnReadCount(String tag);

        /**
         * 退出登录
         * @param tag
         */
        void loginOut(String tag);

        void initList();

        List<MyMenuBean> getMenuBeans();
    }

    /*---------------我的信息----------------*/
    interface IMyInfoView extends IView{}
    interface IMyInfoPresent{
        /**
         * 头像选择
         */
        void imageChoose();

        /**
         * 上传头像
         * @param file
         */
        void postHead(File file);
        /**
         * 压缩图片-裁切前，防止加载的bitmap OOM
         * @param path
         */
        void yasuo(String path);

        /**
         * 获取用户信息
         * @param tag
         */
        void getUserData(String tag);
    }

    /*---------------我的积分----------------*/
    interface IMyScoreView extends IView{}
    interface IMyScorePresent{
        /**
         * 获取积分明细列表
         * @param type
         * @param pageNum
         * @param pageSize
         * @param tag
         */
        void getScoreDetail(String type, int pageNum, int pageSize, String tag, boolean showProgress);
    }

    /*---------------我的订单----------------*/
    interface IOrderModel{}
    interface IMyOrderView extends IView{}
    interface IMyOrderPresent{
        void getOrderList();
    }

    /*---------------我的收藏/分享----------------*/
    interface ICollectModel{}
    interface ICollectView extends IView{}
    interface ICollectPresent{
        /**
         * 获取监控收藏列表
         */
        void getCollectListCamera(int pageNum, int pageSize,String tag, boolean showProgress);
        /**
         * 获取资讯收藏列表
         */
        void getCollectListNews(int pageNum, int pageSize,String tag, boolean showProgress);
        /**
         * 获取监控分享列表
         */
        void getShareListCamera(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 获取资讯分享列表
         */
        void getShareListNews(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 添加（删除）收藏 （资讯）
         * @param id 重复收藏时传collectId
         * @param userId 用户id（取消收藏时不传）
         * @param isType 状态（0：收藏）（1：取消收藏）必传
         * @param typeId 类型id （取消收藏时不传）
         * @param collectId 收藏时为被收藏的主键
         * @param ids 取消收藏时为收藏表的主键（删除时传）
         */
        void deleteCollecListNews(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids,String tag);

        /**
         * 添加（删除）收藏（摄像头）
         * @param id
         * @param userId
         * @param isType
         * @param typeId
         * @param collectId
         * @param ids
         * @param tag
         */
        void deleteCollecListCamera(int id, int userId, int isType, int typeId, int collectId, List<Integer> ids,String tag);
        /**
         * 删除分享(监控)
         */
        void deleteShareListCamera(List<Integer> ids,String tag);
        /**
         * 删除分享（资讯）
         */
        void deleteShareListNews(List<Integer> ids,String tag);
    }

    /*---------------我的发布----------------*/
    interface IMyPublishListModel{}
    interface IMyPublishListView extends IView{}
    interface IMyPublishListPresent{
        /**
         * 获取我的发布(案件)
         */
        void getPublishCaseList(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 获取我的发布(巡检)
         */
        void getPublishInspectionList(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 获取我的发布(场所)
         */
        void getPublishSiteList(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 获取我的发布(资讯)
         */
        void getPublishNewsList(int pageNum, int pageSize, String tag, boolean showProgress);
        /**
         * 删除我的发布(案件)
         */
        void deletePublishCase(List<Integer> ids,String tag);
        /**
         * 删除我的发布(巡检)
         */
        void deletePublishInspection(List<Integer> ids,String tag);
        /**
         * 删除我的发布(场所)
         */
        void deletePublishSite(List<Integer> ids,String tag);
        /**
         * 删除我的发布(资讯)
         */
        void deletePublishNews(List<Integer> ids,String tag);
    }

    /*---------------我的任务-----------------*/
    interface ITaskModel{}
    interface ITaskView extends IView{}
    interface ITaskPresent{
        /**
         * 获取我的任务列表
         * @param pageNum
         * @param pageSize
         * @param keyWord 模糊搜索关键词
         * @param typeId 状态值
         * @param tag
         * @param showProgress
         */
        void getMyTaskList(int pageNum, int pageSize, String keyWord, String typeId, String tag, boolean showProgress);

        /**
         * 获取任务信息
         * @param missionId
         * @param tag
         */
        void getTaskInfo(int missionId, int taskPeopleId, String tag);
        /**
         * 获取已提交的任务详情
         * @param reportId
         * @param tag
         */
        void getTaskSubmitedDetail(int reportId, String tag);
    }
}

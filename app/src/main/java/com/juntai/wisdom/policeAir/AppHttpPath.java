package com.juntai.wisdom.policeAir;

public class AppHttpPath {
    /**
     * base
     */
    public static final String BASE = "https://wx.juntaikeji.com:19156/lanshanUAV/u/app";
//    public static final String BASE = "http://192.168.124.119:8080/lanshanUAV/u/app";

    /**
     * 车辆流直播
     */
    public static final String CAR_STREAM = "rtmp://www.juntaikeji.net:1955/live/";

    public static final String BASE_IMAGE = "http://61.156.157.132:8092";

    /**
     * 巡检缩略图
     */
    public static final String INSPECTION_THUMBNAI_IMAGE = "http://61.156.157.132:30080/crm/" +
            "/getThumbnailImg.shtml";//"http://61.156.157.132:32180"

    /**
     * 巡检原始图片地址
     */
    public static final String INSPECTION_ORIGINAL_IMAGE = "http://61.156.157.132:30080/crm//getImg" +
            ".shtml";//"http://61.156.157.132:32180"


    /**
     * 登录
     */
    public static final String LOGIN = BASE + "/login.shtml";


    /*====================================================    注册模块
    ==============================================================*/

    /**
     * 注册
     */
    public static final String REGIST = BASE + "/insertUserRegister.shtml";
    /**
     * 查询职务
     */
    public static final String SEARCH_POLICE_POSITION = BASE + "/selectPostList.shtml";
    /**
     * 查询部门
     */
    public static final String SEARCH_POLICE_BRANCH = BASE + "/selectDepartmentList.shtml";
    /**
     * 查询责任网格
     */
    public static final String SEARCH_POLICE_GRIDDING = BASE + "/selectGridList.shtml";
    /**
     * 信息补充（权限申请）
     */
    public static final String ADD_USER_INFO = BASE + "/applyAuthority.shtml";

    /**
     * 获取验证码
     */
    public static final String GET_MS_CODE = BASE + "/getSMSCode.shtml";

    /*====================================================    密码相关
    ==============================================================*/

    /**
     * 找回密码
     *
     * @return
     */
    public static final String SET_PWD = BASE + "/updatePassword.shtml";
    /**
     * 修改密码
     *
     * @return
     */
    public static final String MODIFY_PWD = BASE + "/updatePassword.shtml";


    /**
     * 修改手机号
     *
     * @return
     */
    public static final String UPDATE_PHONE = BASE + "/updateUserLoginAccount.shtml";


    /*===================================    首页==========================================*/

    /**
     * 地图菜单按钮
     */
    public static final String MAP_MENU_BUTTON = BASE + "/selectAppMapButton.shtml";

    /**
     * 地图聚合点 摄像头
     */
    public static final String MAP_CAMERA_LIST = BASE + "/selectCameraLocation.shtml";
    /**
     * 地图聚合点 案件
     */
    public static final String MAP_CASE_LIST = BASE + "/selectCaseLocation.shtml";
    /**
     * 地图聚合点 警员
     */
    public static final String MAP_POLICE_LIST = BASE + "/selectPoliceLocation.shtml";
    /**
     * 地图聚合点 车辆
     */
    public static final String MAP_CAR_LIST = BASE + "/selectCarLocation.shtml";
    /**
     * 地图聚合点 场所
     */
    public static final String MAP_SITE_LIST = BASE + "/selectPlaceLocation.shtml";
    /**
     * 地图聚合点 重点人员
     */
    public static final String MAP_KEY_PERSONNAL_LIST = BASE + "/selectKeyPersonnelLocation.shtml";
    /**
     * 地图聚合点 巡检
     */
    public static final String MAP_PATROL_LIST = BASE + "/selectPatrolLocation.shtml";
    /**
     * 地图聚合点 资讯
     */
    public static final String MAP_NEWS_LIST = BASE + "/selectInformationLocation.shtml";

    /**
     * 地图聚合点 无人机
     */
    public static final String MAP_DRONE_LIST = BASE + "/selectDroneLocation.shtml";

    /**
     * 修改人员定位位置（历史轨迹）上传人员得位置信息
     */
    public static final String USER_HISTORY_UPLOAD = BASE + "/addStaffLocation.shtml";


    //获取人员轨迹
    public static final String PEOPLE_HISTORY = BASE + "/selectPoliceHistory.shtml";

    //车辆轨迹
    public static final String CAR_HISTORY = BASE + "/selectCarHistory.shtml";

    /**
     * 用户实名认证
     */
    public static final String USER_AUTH = BASE + "/realNameAuthentication.shtml";
    /**
     * 一键报警中接警员
     */
    public static final String POLICE_PICKER = BASE + "/selectAppReceivingToken.shtml";
    /**
     * 轮播资讯
     */
    public static final String BANNER_NEWS = BASE + "/selectHomePageInformation.shtml";
    /**
     * 融云用户列表接口
     */
    public static final String IM_USERS = BASE + "/selectPoliceUserAll.shtml";


    /*====================================================    搜索和详情
    ==============================================================*/

    //搜索
    public static final String SEARCH = BASE + "/search.shtml";
    //搜索子条目 获取更多
    public static final String SEARCH_GET_MORE = BASE + "/searchList.shtml";
    //警员详情
    public static final String POLICE_DETAIL = BASE + "/selectPoliceInfo.shtml";
    //上传摄像头封面
    public static final String UPLOAD_CAMERA_COVER = BASE + "/updateCameraCoverImg.shtml";


    /*====================================================    天气
    ==============================================================*/

    //实时天气
    public static final String REALTIME_WEATHER = BASE + "/getRealTimeWeather.shtml";
    //天气预报
    public static final String FORCAST_WEATHER = BASE + "/weatherForecast.shtml";
    //获取省份
    public static final String PROVINCE = BASE + "/getProvince.shtml";
    //获取城市 u/apiAppAlarm/getProvince.shtml
    public static final String CITY = BASE + "/getCity.shtml";
    //获取地区 u/apiAppAlarm/getProvince.shtml
    public static final String AREA = BASE + "/getArea.shtml";
    //获取街道
    public static final String STREET = BASE + "/getStreet.shtml";

    /*====================================================    个人中心
    ==============================================================*/

    /**
     * 绑定手机号
     */
    public static final String BIND_PHONE = BASE + "/bindingAccount.shtml";


    /**
     * 退出登录
     */
    public static final String LOGIN_OUT = BASE + "/logout.shtml";
    /**
     * 获取用户信息
     */
    public static final String USERINFO = BASE + "/getUserInfo.shtml";
    /**
     * 修改头像
     */
    public static final String HEAD_UPDATE = BASE + "/updateAppHeadImg.shtml";

    /**
     * 获取我的监控收藏列表
     */
    public static final String USER_COLLECT_CAMERA = BASE + "/selectUserCollectCamera.shtml";
    /**
     * 获取我的资讯收藏列表
     */
    public static final String USER_COLLECT_NEWS = BASE + "/selectUserCollectInformation.shtml";
    /**
     * 获取我的监控分享列表
     */
    public static final String USER_SHARE_CAMERA = BASE + "/selectUserShareCamera.shtml";
    /**
     * 获取我的资讯分享列表
     */
    public static final String USER_SHARE_NEWS = BASE + "/selectUserShareInformation.shtml";

    /**
     * 获取我的案件发布列表
     */
    public static final String USER_PUBLISH_CASE = BASE + "/selectUserPublishCase.shtml";
    /**
     * 获取我的巡检发布列表
     */
    public static final String USER_PUBLISH_INSPECTION = BASE + "/selectUserPublishNotification.shtml";
    /**
     * 获取我的场所发布列表
     */
    public static final String USER_PUBLISH_SITE = BASE + "/selectUserClient.shtml";
    /**
     * 获取我的资讯发布列表
     */
    public static final String USER_PUBLISH_NEWS = BASE + "/selectUserPublishInformation.shtml";
    /**
     * 获取我的消息列表
     */
    public static final String USER_MESSAGE = BASE + "/selectUserMessageTypeList.shtml";
    /**
     * 获取我的积分列表
     */
    public static final String USER_SCORE = BASE + "/selectUserScoreRecord.shtml";
    /**
     * 消息全部已读
     */
    public static final String ALL_READ_MSG = BASE + "/allRead.shtml";
    /**
     * 添加（删除）用户分享
     */
    public static final String ADD_OR_DELETE_SHARE = BASE + "/userShare.shtml";

    /**
     * 删除我的发布(案件)
     */
    public static final String DELETE_PUBLISH_CASE = BASE + "/deleteUserPublishCase.shtml";
    /**
     * 删除我的发布(巡检)
     */
    public static final String DELETE_PUBLISH_INSPECTION = BASE + "/deleteUserPublishNotification.shtml";
    /**
     * 删除我的发布(场所)
     */
    public static final String DELETE_PUBLISH_SITE = BASE + "/deleteUserPublishClient.shtml";
    /**
     * 删除我的发布(资讯)
     */
    public static final String DELETE_PUBLISH_NEWS = BASE + "/deleteUserPublishInformation.shtml";
    /**
     * 获取我的通知消息详情
     */
    public static final String GET_INFORMATION_DETAIL = BASE + "/selectUserSystemMessageInfo.shtml";
    /**
     * 获取我的任务列表
     */
    public static final String USER_TASK_LIST = BASE + "/selectMissionList.shtml";
    /**
     * 获取任务列表
     */
    public static final String USER_TASK_DETAIL = BASE + "/selectMissionInfo.shtml";
    /**
     * 获取yi提交的任务列表
     */
    public static final String USER_TASK_SUBMITED_DETAIL = BASE + "/selectMissionSubmittedInfo.shtml";

    /**
     * 删除通知消息
     */
    public static final String DELETE_MY_SYSTEM_MESSAGE = BASE + "/deleteNoticeMessageLog.shtml";
    /**
     * 绑定qq或微信
     */
    public static final String BIND_QQ_WECHAT = BASE + "/bindingQQAndWeChat.shtml";
    /**
     * 未读消息数
     */
    public static final String UNREAD_COUNT = BASE + "/selectUserMessages.shtml";

    /*====================================================    发布
    ==============================================================*/


    /**
     * 发布案件
     */
    public static final String PUBLISH_CASE = BASE + "/insertCase.shtml";

    /**
     * 案件类型
     */
    public static final String CASE_TYPE = BASE + "/selectCaseType.shtml";

    /**
     * 案件（或）追踪
     */
    public static final String GET_CASE_INFO = BASE + "/selectCaseInfo.shtml";
    /**
     * 发布巡检
     */
    public static final String PUBLISH_INSPECTION = BASE + "/insertPatrolNotification.shtml";
    /**
     * 巡检商铺详情
     */
    public static final String INSPECTION_POINT_DETAIL = BASE + "/selectPatrolInfo.shtml";
    /**
     * 巡检记录
     */
    public static final String INSPECTION_RECORD = BASE + "/selectPatrolList.shtml";
    /**
     * 巡检详情
     */
    public static final String INSPECTION_DETAIL = BASE + "/selectUserPatrolInfo.shtml";

    /**
     * 检查更新
     */
    public static final String APP_UPDATE = BASE + "/detectionAppVersions.shtml";
    /**
     * 发布任务上报
     */
    public static final String PUBLISH_TASK_REPORT = BASE + "/insertTaskReport.shtml";

    /**
     * 根据id获取巡检点信息（扫码）
     */
    public static final String GET_INSPECTION_INFO_FOR_SACN = BASE + "/selectClientByQrCode.shtml";


    /*====================================================    消息类型
    ==============================================================*/

    //消息对应类型（1：案件通知；2：巡检通知；3：实名认证通知；4：户籍业务通知；5：新任务通知, 6：任务审核, 7：互联网调解通知）
    public static final int NOTICE_TYPE_CASE = 1;
    public static final int NOTICE_TYPE_INSPECTION = 2;
    public static final int NOTICE_TYPE_AUTH = 3;
    public static final int NOTICE_TYPE_CENCUS = 4;
    public static final int NOTICE_TYPE_TASK = 5;
    public static final int NOTICE_TYPE_TASK_REPORT = 6;
    public static final int NOTICE_TYPE_CONCILIATION = 7;



    /*====================================================    业务办理
    ==============================================================*/
    /**
     * 户籍业务列表
     */
    public static final String BUSINESS_LIST = BASE + "/selectCensusRegisterList.shtml";
    /**
     * 户籍业务所需材料详情
     */
    public static final String BUSINESS_DATA_NEEDED = BASE + "/selectDeclareMaterials.shtml";
    /**
     * 新增户籍业务
     */
    public static final String BUSINESS_ADD = BASE + "/insertHouseholdBusiness.shtml";
    /**
     * 用户业务办理进度
     */
    public static final String BUSINESS_PROGRESS = BASE + "/selectUserBusinessSchedule.shtml";
    /**
     * 查询用户提交申请业务详情
     */
    public static final String BUSINESS_DETAIL = BASE + "/selectUserSubmitBusinessInfo.shtml";



    /*====================================================    法律调解
    ==============================================================*/

    /**
     * 新增调解
     */
    public static final String CONCILIATION_ADD = BASE + "/insertNetCase.shtml";
    /**
     * 我的调解列表
     */
    public static final String CONCILIATION_LIST = BASE + "/selectMyMediate.shtml";
    /**
     * 调解详情
     */
    public static final String CONCILIATION_INFO = BASE + "/selectNetCaseInfo.shtml";
    /**
     * 获取全部调解员列表
     */
    public static final String GET_ALL_MEDIATOR_LIST = BASE + "/selectNetPersonnelList.shtml";
    /**
     * 获取调解类型列表
     */
    public static final String GET_CONCILIATION_TYPE_LIST = BASE + "/selectNetCaseType.shtml";
    /**
     * 查询案件编号是否正确
     */
    public static final String SELECT_CASE_NUMBER_IS_CORRECT = BASE + "/selectCaseNumberIsCorrect.shtml";
    /**
     * 获取单位列表
     */
    public static final String GET_UNIT_LIST = BASE + "/selectNetUnitList.shtml";

    /*====================================================    兑换商城
    ==============================================================*/
    /**
     * 获取商品列表
     */
    public static final String MALL_ALL_GOODS_LIST = BASE + "/selectCommodityList.shtml";
    /**
     * 查询商品详情
     */
    public static final String MALL_GOODS_DEATIL = BASE + "/selectCommodityInfo.shtml";
    /**
     * 获取兑换历史记录
     */
    public static final String MALL_HISTORY_LIST = BASE + "/selectUserExchangeRecord.shtml";
    /**
     * 商品兑换
     */
    public static final String MALL_EXCHANGE_GOODS = BASE + "/PlaceTheOrderAndSettlement.shtml";


    /*====================================================    流媒体
    ==============================================================*/
    //摄像头拉流地址
    public static final String BASE_CAMERA_URL = "http://juntaikeji.net:8060";
    /**
     * 获取视频播放地址
     */
    public static final String STREAM_OPE_ADDR = "http://61.156.157.132:35080/streamingMedia/u/app/getVideoOpenStream" +
            ".shtml";
    //    /**
    //     * 测试接口
    //     */
    //    public static final String BASE = "http://61.156.157.132:35080/streamingMedia/u/app";
    /**
     * 硬盘录像机下面的摄像头列表
     */
    public static final String STREAM_CAMERAS_FROM_VCR = BASE + "u/camera/selectCameraByDvrIdAPP.shtml?";
    /**
     * 摄像头详情
     */
    public static final String STREAM_CAMERAS_DETAIL = BASE + "/selectCameraInfo.shtml";
    /**
     * 上传封面图
     */
    public static final String UPLOAD_STREAM_CAMERAS_CAPTURE = BASE + "u/camera/updateCameraCoverImgAPP.shtml";

    /**
     * 上传封面图
     */
    public static final String UPLOAD_STREAM_CAMERAS_THUMB = BASE + "/updateCameraCoverImg.shtml";

    /**
     * 查询评论
     */
    public static final String GET_ALL_COMMENT_CAMERA = BASE + "/selectCameraCommentList.shtml";
    /**
     * 查询子评论
     */
    public static final String GET_CHILD_COMMENT_CAMERA = BASE + "/selectCameraCommentChildList.shtml";
    /**
     * 用户添加评论
     */
    public static final String ADD_COMMENT_CAMERA = BASE + "/publishCameraComment.shtml";
    /**
     * 点赞(摄像头)
     */
    public static final String ADD_OR_CANCLE_LIKE_CAMERA = BASE + "/userCameraLike.shtml";
    /**
     * 添加（删除）用户收藏（摄像头）
     */
    public static final String ADD_OR_DELETE_COLLECT_CAMERA = BASE + "/userCameraCollect.shtml";
    /**
     * 添加摄像头分享记录
     */
    public static final String SHARE_TO_WCHAT = BASE + "/shareToWeChat.shtml";


    /*==============================================  流媒体厂家api  =============================================*/

    /**
     * 录像下载
     */
    public static final String RECORD_DOWNLOAD = BASE_CAMERA_URL + "/vss/playback/start?";
    /**
     * 操作设备
     */
    public static final String OPERATE_DEV = BASE_CAMERA_URL + "/vss/device/control?";
    /**
     * 获取当前在线数
     */
    public static final String GET_ONLINE_AMOUNT = BASE_CAMERA_URL + "/vss/getcallinfo?";
    /**
     * 停止当前的播放流
     * http://www.jthw110.cn:8060/vss/stream/stop?sessionid=http-1295687-1603349681-834
     */
    public static final String STOP_STREAM = BASE_CAMERA_URL + "/vss/stream/stop?";

    /*==============================================  流媒体云台预置位  =============================================*/
    //    /**
    //     * 添加预置位
    //     */
    //    public static final String ADD_PRE_POSITION = BASE + "/addVideoPTZCollect.shtml";
    //
    //
    //    /**
    //     * 删除预置位
    //     */
    //    public static final String DEL_PRE_POSITION = BASE + "/deleteVideoPTZCollect.shtml";
    //
    //
    //    /**
    //     * 查询预置位
    //     */
    //    public static final String GET_PRE_POSITIONS = BASE + "/getVideoPTZCollect.shtml";


    /*====================================================   重点人员
    ==============================================================*/
    /**
     * 重点人员详情
     */
    public static final String KEY_PERSONNEL_DETAIL = BASE + "/selectKeyPersonnelInfo.shtml";
    /**
     * 走访上传
     */
    public static final String INTERVIEW_ADD = BASE + "/insertKeyPersonnelLog.shtml";
    /**
     * 走访列表
     */
    public static final String INTERVIEW_LIST = BASE + "/selectKeyPersonnelLog.shtml";
    /**
     * 走访详情
     */
    public static final String INTERVIEW_DETAIL = BASE + "/selectKeyPersonnelLogInfo.shtml";

    /*====================================================   场所管理
    ==============================================================*/
    /**
     * 单位详情
     */
    public static final String UNIT_DETAIL = BASE + "/selectPlaceInfo.shtml";
    /**
     * 从业人员列表
     */
    public static final String GET_EMPLOYEE_LIST = BASE + "/selectUnitInfoEmploymentPersonnel.shtml";
    /**
     * 从业人员详情
     */
    public static final String GET_EMPLOYEE_DETAIL = BASE + "/selectEmploymentStaffInfo.shtml";
    /**
     * 检查记录列表
     */
    public static final String GET_SITE_INSPECTION_LIST = BASE + "/selectUnitInfoInspectRecord.shtml";
    /**
     * 检查记录详情
     */
    public static final String GET_SITE_INSPECTION_DETAIL = BASE + "/selectInspectRecordInfo.shtml";
    /**
     * 添加从业人员
     */
    public static final String ADD_EMPLOYEE = BASE + "/insertEmploymentPersonnel.shtml";
    /**
     * 添加检查记录
     */
    public static final String ADD_SITE_INSPECTION = BASE + "/insertInspectRecord.shtml";

    /**
     * 获取单位类型列表
     */
    public static final String GET_SITE_TYPE_LIST = BASE + "/selectShopType.shtml";
    /**
     * 获取客户来源列表
     */
    public static final String GET_CUSTOMER_SOURCE_LIST = BASE + "/selectClientSource.shtml";
    /**
     * 添加场所（单位）
     */
    public static final String ADD_SITE_MANAGER = "http://61.156.157.132:30080/crm//insertClientInfo" +
            ".shtml";
    /**
     * 修改场所（单位）
     */
    public static final String UPDATE_SITE_MANAGER = "http://61.156.157.132:30080/crm//updateClient" +
            ".shtml";

    /*====================================================    资讯模块
    ==============================================================*/

    /**
     * 资讯列表
     */
    public static final String GET_NEWS_LIST = BASE + "/selectInformationList.shtml";
    /**
     * 发布资讯
     */
    public static final String NEWS_PUBLISH = BASE + "/insertInformation.shtml";
    /**
     * 修改资讯
     */
    public static final String UPDATE_NEWS = BASE + "/updateInformation.shtml";
    /**
     * 上传资讯图片
     */
    public static final String NEWS_UPLOAD_PHOTO = BASE + "/insertInformationFile.shtml";
    /**
     * 删除资讯图片
     */
    public static final String NEWS_DELETE_PHOTO = BASE + "/deleteInformationFile.shtml";
    /**
     * 搜索资讯
     */
    public static final String NEWS_SEARCH_LIST = BASE + "/selectInformationSearch.shtml";
    /**
     * 资讯详情
     */
    public static final String GET_NEWS_INFO = BASE + "/selectInformationInfo.shtml";
    /**
     * 资讯作者信息
     */
    public static final String NEWS_PERSONAL_HOMEPAGE_INFO = BASE + "/selectInformationTop.shtml";
    /**
     * 获取作者资讯列表
     */
    public static final String NEWS_PERSONAL_HOMEPAGE_NEWS_LIST = BASE + "/selectInformationBottom.shtml";

    /**
     * 获取粉丝列表
     */
    public static final String NEWS_FANS_LIST = BASE + "/selectUserFans.shtml";
    /**
     * 获取关注列表
     */
    public static final String NEWS_FOLLOW_LIST = BASE + "/selectUserFollow.shtml";
    /**
     * 添加或取消关注
     */
    public static final String NEWS_ADD_FOLLOW_OR_DELETE = BASE + "/insertFansAndFollow.shtml";

    /**
     * 查询评论
     */
    public static final String GET_ALL_COMMENT_NEWS = BASE + "/selectInformationCommentList.shtml";
    /**
     * 查询子评论
     */
    public static final String GET_CHILD_COMMENT_NEWS = BASE + "/selectInformationCommentChildList.shtml";
    /**
     * 用户添加评论
     */
    public static final String ADD_COMMENT_NEWS = BASE + "/publishInformationComment.shtml";
    /**
     * 点赞(资讯)
     */
    public static final String ADD_OR_CANCLE_LIKE_NEWS = BASE + "/userInformationLike.shtml";
    /**
     * 添加（删除）用户收藏（资讯）
     */
    public static final String ADD_OR_DELETE_COLLECT_NEWS = BASE + "/userInformationCollect.shtml";


    /*====================================================    我的设备
    ==============================================================*/
    /**
     * 获取我的设备列表
     */
    public static final String GET_USER_EQUIPMENT_LIST = BASE + "/selectUserEquipmentList.shtml";





    /*==============================================  举报  =============================================*/

    /**
     * 获取举报类型
     */
    public static final String GET_REPORT_TYPES = BASE + "/selectReportType.shtml";
    /**
     * 举报（资讯文章）
     */
    public static final String REPORT = BASE + "/reportInformation.shtml";

}

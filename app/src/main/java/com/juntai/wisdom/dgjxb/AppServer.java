package com.juntai.wisdom.dgjxb;


import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.bean.BaseStreamBean;
import com.juntai.wisdom.dgjxb.bean.PoliceDetailBean;
import com.juntai.wisdom.basecomponent.bean.ReportTypeBean;
import com.juntai.wisdom.dgjxb.bean.map.ResponseDrone;
import com.juntai.wisdom.dgjxb.bean.map.ResponseKeyPersonnel;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationInfoBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationListBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.ConciliationTypesBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.MediatorAllListBean;
import com.juntai.wisdom.dgjxb.bean.conciliation.UnitListBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionForScanBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.InterviewDetailBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.InterviewListBean;
import com.juntai.wisdom.dgjxb.bean.key_personnel.KeyPersonnelInfoBean;
import com.juntai.wisdom.dgjxb.bean.message.UnReadCountBean;
import com.juntai.wisdom.dgjxb.bean.news.NewsDetailBean;
import com.juntai.wisdom.dgjxb.bean.news.NewsFansListBean;
import com.juntai.wisdom.dgjxb.bean.news.NewsListBean;
import com.juntai.wisdom.dgjxb.bean.news.NewsPersonalHomePageInfo;
import com.juntai.wisdom.dgjxb.bean.news.NewsUploadPhotoBean;
import com.juntai.wisdom.dgjxb.bean.site.CustomerSourceBean;
import com.juntai.wisdom.dgjxb.bean.site.EmployeeDetailBean;
import com.juntai.wisdom.dgjxb.bean.site.EmployeeListBean;
import com.juntai.wisdom.dgjxb.bean.site.SiteInspectionDetailBean;
import com.juntai.wisdom.dgjxb.bean.site.SiteTypeBean;
import com.juntai.wisdom.dgjxb.bean.stream.CameraOnlineBean;
import com.juntai.wisdom.dgjxb.bean.stream.CaptureBean;
import com.juntai.wisdom.dgjxb.bean.stream.OpenLiveBean;
import com.juntai.wisdom.dgjxb.bean.stream.PlayUrlBean;
import com.juntai.wisdom.dgjxb.bean.stream.RecordInfoBean;
import com.juntai.wisdom.dgjxb.bean.stream.StreamCameraBean;
import com.juntai.wisdom.dgjxb.bean.business.BusinessListBean;
import com.juntai.wisdom.dgjxb.bean.business.BusinessNeedInfoBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionRecordBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionPointInfoBean;
import com.juntai.wisdom.dgjxb.bean.business.MyBusinessBean;
import com.juntai.wisdom.dgjxb.bean.stream.VideoInfoBean;
import com.juntai.wisdom.dgjxb.bean.user_equipment.EquipmentListBean;
import com.juntai.wisdom.dgjxb.bean.weather.PoliceGriddingBean;
import com.juntai.wisdom.dgjxb.bean.weather.ResponseForcastWeather;
import com.juntai.wisdom.dgjxb.bean.weather.ResponseRealTimeWeather;
import com.juntai.wisdom.dgjxb.bean.BannerNewsBean;
import com.juntai.wisdom.dgjxb.bean.BaseDataBean;
import com.juntai.wisdom.dgjxb.bean.case_bean.CaseDesBean;
import com.juntai.wisdom.dgjxb.bean.case_bean.CaseInfoBean;
import com.juntai.wisdom.dgjxb.bean.case_bean.CaseTypeBean;
import com.juntai.wisdom.dgjxb.bean.CityBean;
import com.juntai.wisdom.dgjxb.bean.CollectListBean;
import com.juntai.wisdom.dgjxb.bean.CommentListBean;
import com.juntai.wisdom.dgjxb.bean.history_track.HistoryTrackBean;
import com.juntai.wisdom.dgjxb.bean.IMUsersBean;
import com.juntai.wisdom.dgjxb.bean.message.InformDetailBean;
import com.juntai.wisdom.dgjxb.bean.inspection.InspectionDetailBean;
import com.juntai.wisdom.dgjxb.bean.message.LikeMsgListBean;
import com.juntai.wisdom.dgjxb.bean.MapMenuButton;
import com.juntai.wisdom.dgjxb.bean.PoliceBranchBean;
import com.juntai.wisdom.dgjxb.bean.PoliceCarBean;
import com.juntai.wisdom.dgjxb.bean.PolicePickerBean;
import com.juntai.wisdom.dgjxb.bean.PolicePositionBean;
import com.juntai.wisdom.dgjxb.bean.PublishListBean;
import com.juntai.wisdom.dgjxb.bean.map.ResponseSiteBean;
import com.juntai.wisdom.dgjxb.bean.site.UnitDetailBean;
import com.juntai.wisdom.dgjxb.bean.map.ResponseCarHistory;
import com.juntai.wisdom.dgjxb.bean.map.ResponseInspection;
import com.juntai.wisdom.dgjxb.bean.map.ResponseNews;
import com.juntai.wisdom.dgjxb.bean.map.ResponsePeople;
import com.juntai.wisdom.dgjxb.bean.SearchBean;
import com.juntai.wisdom.dgjxb.bean.SearchResultBean;
import com.juntai.wisdom.dgjxb.bean.task.TaskDetailBean;
import com.juntai.wisdom.dgjxb.bean.task.TaskListBean;
import com.juntai.wisdom.dgjxb.bean.task.TaskSubmitedBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.UserScoreListBean;
import com.juntai.wisdom.dgjxb.bean.business.MyBusinessDetailBean;
import com.juntai.wisdom.dgjxb.bean.exchang_mall.GoodsInfoBean;
import com.juntai.wisdom.dgjxb.bean.exchang_mall.GoodsListBean;
import com.juntai.wisdom.dgjxb.bean.exchang_mall.HistoryGoodsListBean;
import com.juntai.wisdom.dgjxb.bean.stream.StreamCameraDetailBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServer {
    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @POST(AppHttpPath.LOGIN)
    Observable<UserBean> login(@Query("account") String account, @Query("password") String password);

    /***************************************注册模块* ******************************************/

    /**
     * 获取职务信息
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_POSITION)
    Observable<PolicePositionBean> getPolicePosition(@Body RequestBody jsonBody);

    /**
     * 获取部门信息
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_BRANCH)
    Observable<PoliceBranchBean> getPoliceBranch(@Body RequestBody jsonBody);

    /**
     * 获取网格信息
     *
     * @return
     */
    @POST(AppHttpPath.SEARCH_POLICE_GRIDDING)
    Observable<PoliceGriddingBean> getPoliceGridding(@Body RequestBody jsonBody);

    /**
     * 获取验证码
     *
     * @return
     */
    @POST(AppHttpPath.GET_MS_CODE)
    Observable<BaseResult> getMsCode(@Query("account") String account);

    /**
     * 注册
     *
     * @return
     */
    @POST(AppHttpPath.REGIST)
    Observable<BaseResult> regist(@Body RequestBody route);

    /**
     * 补充用户信息
     *
     * @return
     */
    @POST(AppHttpPath.ADD_USER_INFO)
    Observable<BaseResult> addUserInfo(@Body RequestBody jsonBody);

    /*************************************************个人中心* **************************************************/
    /**
     * 退出登录
     *
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.LOGIN_OUT)
    Observable<BaseResult> loginOut(@Query("account") String account, @Query("token") String token);

    /**
     * 获取个人信息
     * <p>
     * 获取用户信息
     *
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.USERINFO)
    Observable<UserBean> getUserData(@Query("account") String account, @Query("token") String token);

    /**
     * 上传头像
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.HEAD_UPDATE)
    Observable<BaseResult> modifyHeadPortrait(@Body RequestBody jsonBody);


    /**
     * 绑定手机号
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.BIND_PHONE)
    Observable<BaseResult> bindPhoneNum(@Body RequestBody jsonBody);

    /**
     * 获取我的监控收藏
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_COLLECT_CAMERA)
    Observable<CollectListBean> getUserCollectCamera(@Query("account") String account, @Query("token") String token,
                                                     @Query("userId") int userId,
                                                     @Query("currentPage") int currentPage,
                                                     @Query("pageSize") int pageSize);

    /**
     * 获取我的资讯收藏
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_COLLECT_NEWS)
    Observable<CollectListBean> getUserCollectNews(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * 获取我的分享(监控)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_SHARE_CAMERA)
    Observable<CollectListBean> getUserShareCamera(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * 获取我的分享(资讯)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_SHARE_NEWS)
    Observable<CollectListBean> getUserShareNews(@Query("account") String account, @Query("token") String token,
                                                 @Query("userId") int userId,
                                                 @Query("currentPage") int currentPage,
                                                 @Query("pageSize") int pageSize);

    /**
     * 获取我的发布(案件)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_CASE)
    Observable<PublishListBean> getUserPublishCase(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * 获取我的发布(巡检)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_INSPECTION)
    Observable<PublishListBean> getUserPublishInspection(@Query("account") String account,
                                                         @Query("token") String token, @Query("userId") int userId,
                                                         @Query("currentPage") int currentPage,
                                                         @Query("pageSize") int pageSize);

    /**
     * 获取我的发布(场所)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_SITE)
    Observable<PublishListBean> getUserPublishSite(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId, @Query("currentPage") int currentPage
            , @Query("pageSize") int pageSize);

    /**
     * 获取我的发布(资讯)
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_PUBLISH_NEWS)
    Observable<PublishListBean> getUserPublishNews(@Query("account") String account, @Query("token") String token,
                                                   @Query("userId") int userId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * 获取通知消息详情
     *
     * @param account
     * @param token
     * @param id      消息id
     * @return
     */
    @POST(AppHttpPath.GET_INFORMATION_DETAIL)
    Observable<InformDetailBean> getSystemDetail(@Query("account") String account, @Query("token") String token,
                                                 @Query("id") int id, @Query("userId") int userId);

    /**
     * 删除通知消息
     *
     * @param account
     * @param token
     * @param logId   消息id
     * @return
     */
    @POST(AppHttpPath.DELETE_MY_SYSTEM_MESSAGE)
    Observable<BaseResult> deleteSystemMsg(@Query("account") String account, @Query("token") String token,
                                           @Query("userId") int userId, @Query("logId") int logId);

    /**
     * 获取评论消息/获取通知消息
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_MESSAGE)
    Observable<LikeMsgListBean> getUserLikeMsg(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("typeId") int typeId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 获取我的积分明细
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      用途（1获取；2使用,null全部）
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @return
     */
    @POST(AppHttpPath.USER_SCORE)
    Observable<UserScoreListBean> getUserScore(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("typeId") String typeId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 消息全部已读
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId  类型id（1:通知消息）（2:互动消息）（3:物流售后）（4:评论和赞）
     * @param id      大于0为单条消息已读
     * @return
     */
    @POST(AppHttpPath.ALL_READ_MSG)
    Observable<BaseResult> allReadMsg(@Query("account") String account, @Query("token") String token,
                                      @Query("userId") int userId, @Query("typeId") int typeId, @Query("id") int id);

    /**
     * 添加（删除）分享(资讯)
     *
     * @param account
     * @param token
     * @param userId  用户id（删除分享时不传）
     * @param isType  状态（0：分享）（1：删除分享）必传
     * @param shareId 分享时为被分享的主键）
     * @param ids     删除分享时为分享表的主键（删除时传）
     */
    @POST(AppHttpPath.ADD_OR_DELETE_SHARE)
    Observable<BaseResult> addOrDeleteShares(@Query("account") String account, @Query("token") String token, @Query(
            "userId") String userId,
                                             @Query("isType") int isType, @Query("typeId") String typeId,
                                             @Query("shareId") String shareId, @Query("ids") List<Integer> ids);

    /**
     * 删除分享（监控）
     *
     * @param account
     * @param token
     * @param isType  状态（0：分享）（1：删除分享）必传
     * @param ids
     * @return
     */
    @POST(AppHttpPath.SHARE_TO_WCHAT)
    Observable<BaseResult> deleteCameraShare(@Query("account") String account, @Query("token") String token,
                                             @Query("isType") int isType, @Query("ids") List<Integer> ids);

    /**
     * 删除发布
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_CASE)
    Observable<BaseResult> deletePublishCase(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * 删除巡检
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_INSPECTION)
    Observable<BaseResult> deletePublishInspection(@Query("account") String account, @Query("token") String token,
                                                   @Query("ids") List<Integer> ids);

    /**
     * 删除场所
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_SITE)
    Observable<BaseResult> deletePublishSite(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * 删除资讯
     *
     * @param account
     * @param token
     * @param ids
     * @return
     */
    @POST(AppHttpPath.DELETE_PUBLISH_NEWS)
    Observable<BaseResult> deletePublishNews(@Query("account") String account, @Query("token") String token, @Query(
            "ids") List<Integer> ids);

    /**
     * 获取我的任务列表
     *
     * @param account
     * @param token
     * @param userId
     * @param currentPage 页码
     * @param pageSize    每页显示数
     * @param typeId      0正在审核；1审核通过；2审核失败,不传获取全部，搜索用
     * @param keyWord     关键词 模糊搜索
     * @return
     */
    @POST(AppHttpPath.USER_TASK_LIST)
    Observable<TaskListBean> getUserTask(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId,
                                         @Query("currentPage") int currentPage, @Query("pageSize") int pageSize,
                                         @Query("keyWord") String keyWord, @Query("typeId") String typeId);

    /**
     * 获取任务详情
     *
     * @param account
     * @param token
     * @param userId
     * @param missionId    任务id
     * @param taskPeopleId 人员任务分配id
     * @return
     */
    @POST(AppHttpPath.USER_TASK_DETAIL)
    Observable<TaskDetailBean> getTaskInfo(@Query("account") String account, @Query("token") String token,
                                           @Query("userId") int userId, @Query("missionId") int missionId,
                                           @Query("taskPeopleId") int taskPeopleId);

    /**
     * 获取已提交任务详情
     *
     * @param account
     * @param token
     * @param reportId 任务id
     * @return
     */
    @POST(AppHttpPath.USER_TASK_SUBMITED_DETAIL)
    Observable<TaskSubmitedBean> getTaskSubmitDetail(@Query("account") String account, @Query("token") String token,
                                                     @Query("reportId") int reportId);

    /**
     * 绑定微信或QQ
     *
     * @param account    手机号
     * @param source     用户来源（1警小宝；2巡小管；3邻小帮；4云调解室）
     * @param weChatId
     * @param weChatName
     * @param qqId
     * @param qqName
     * @return
     */
    @POST(AppHttpPath.BIND_QQ_WECHAT)
    Observable<BaseResult> bindQQAndWeChat(@Query("account") String account, @Query("token") String token,@Query(
            "source") int source,
                                           @Query("weChatId") String weChatId, @Query("weChatName") String weChatName,
                                           @Query("qqId") String qqId, @Query("qqName") String qqName);

    /**
     * 获取未读消息数
     *
     * @return
     */
    @POST(AppHttpPath.UNREAD_COUNT)
    Observable<UnReadCountBean> getUnReadCount(@Body RequestBody requestBody);


    /**********************************************************************首页地图
     * *********************************************************************/
    /**
     * 获取首页地图右侧菜单
     *
     * @param typeId App类型(1：警小宝）（3：巡小管）（2：邻小帮）
     * @return
     */
    @POST(AppHttpPath.MAP_MENU_BUTTON)
    Observable<MapMenuButton> getMapMenu(@Query("typeId") String typeId);

    /**
     * 请求监控坐标列表
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CAMERA_LIST)
    Observable<StreamCameraBean> requestCamera(@Body RequestBody requestBody);

    /**
     * 请求案件
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CASE_LIST)
    Observable<CaseDesBean> requestCase(@Body RequestBody requestBody);

    /**
     * 请求车辆
     *
     * @return
     */
    @POST(AppHttpPath.MAP_CAR_LIST)
    Observable<PoliceCarBean> requestCar(@Body RequestBody requestBody);

    /**
     * 请求警员
     *
     * @return
     */
    @POST(AppHttpPath.MAP_POLICE_LIST)
    Observable<ResponsePeople> requestPeople(@Body RequestBody requestBody);

    /**
     * 请求巡检
     *
     * @return
     */
    @POST(AppHttpPath.MAP_PATROL_LIST)
    Observable<ResponseInspection> requestInspection();

    /**
     * 场所管理
     *
     * @return
     */
    @POST(AppHttpPath.MAP_SITE_LIST)
    Observable<ResponseSiteBean> requestSiteList(@Body RequestBody requestBody);

    /**
     * 请求资讯
     *
     * @return
     */
    @POST(AppHttpPath.MAP_NEWS_LIST)
    Observable<ResponseNews> requestNews(@Body RequestBody requestBody);
    /**
     * 请求无人机列表
     *
     * @return
     */
    @POST(AppHttpPath.MAP_DRONE_LIST)
    Observable<ResponseDrone> requestDrone(@Body RequestBody requestBody);

    /**
     * 请求重点人员列表
     *
     * @return
     */
    @POST(AppHttpPath.MAP_KEY_PERSONNAL_LIST)
    Observable<ResponseKeyPersonnel> requestKeyPersonnel(@Body RequestBody requestBody);

    /**
     * 警员详情
     *
     * @return
     */
    @POST(AppHttpPath.POLICE_DETAIL)
    Observable<PoliceDetailBean> requestPeopleDetail(@Body RequestBody body);

    /**
     * 上传摄像头封面图
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPLOAD_CAMERA_COVER)
    Observable<BaseResult> uploadCameraCover(@Body RequestBody jsonBody);

    /**
     * 用户认证
     */
    @POST(AppHttpPath.USER_AUTH)
    Observable<BaseResult> userAuth(@Body RequestBody jsonBody);

    /**
     * 一键报警 接警员
     */
    @POST(AppHttpPath.POLICE_PICKER)
    Observable<PolicePickerBean> getPolicePickerInfo(@Query("account") String account, @Query("token") String token,
                                                     @Query("departmentId") int departmentId);


    //搜索
    @POST(AppHttpPath.SEARCH)
    Observable<SearchBean> search(@Body RequestBody requestBody);

    //搜索
    @POST(AppHttpPath.SEARCH_GET_MORE)
    Observable<SearchResultBean> search_get_more(@Body RequestBody requestBody);

    /**
     * 轮播资讯
     *
     * @return
     */
    @POST(AppHttpPath.BANNER_NEWS)
    Observable<BannerNewsBean> getBannerNews();

    /**
     * 获取im用户信息
     *
     * @return
     */
    @POST(AppHttpPath.IM_USERS)
    Observable<IMUsersBean> getIMUsers(@Query("token") String token, @Query("account") String account);

    //车辆轨迹
    @POST(AppHttpPath.CAR_HISTORY)
    Observable<ResponseCarHistory> getPoliceCarTrack(@Body RequestBody body);

    //人员轨迹
    @POST(AppHttpPath.PEOPLE_HISTORY)
    Observable<HistoryTrackBean> getPoliceTrack(@Body RequestBody body);

    /**
     * 上传轨迹
     */
    @POST(AppHttpPath.USER_HISTORY_UPLOAD)
    Observable<BaseResult> uploadHistory(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId, @Query("json") String json);

    /*====================================================    天气
    ==============================================================*/


    //实时天气
    @POST(AppHttpPath.REALTIME_WEATHER)
    Observable<ResponseRealTimeWeather> getWeatherRealtime(@Query("longitude") String longitude,
                                                           @Query("latitude") String latitude);

    //天气预报
    @POST(AppHttpPath.FORCAST_WEATHER)
    Observable<ResponseForcastWeather> getForcast(@Query("longitude") String longitude,
                                                  @Query("latitude") String latitude);

    /**
     * 获取省列表
     *
     * @return
     */
    @POST(AppHttpPath.PROVINCE)
    Observable<CityBean> getProvince();

    /**
     * 获取城市列表
     *
     * @param cityNum
     * @return
     */
    @POST(AppHttpPath.CITY)
    Observable<CityBean> getCity(@Query("cityNum") String cityNum);

    /**
     * 获取地区列表
     *
     * @param cityNum
     * @return
     */
    @POST(AppHttpPath.AREA)
    Observable<CityBean> getArea(@Query("cityNum") String cityNum);

    /**
     * 获取街道列表
     *
     * @return
     */
    @POST(AppHttpPath.STREET)
    Observable<CityBean> getStreet(@Query("cityNum") String townNum);

    /*****************************************************************密码模块
     * **************************************************************************/

    /**
     * 找回密码
     *
     * @return
     */
    @POST(AppHttpPath.SET_PWD)
    Observable<BaseResult> setPwd(@Query("account") String account, @Query("password") String password, @Query("code") String code);

    /**
     * 修改密码
     *
     * @return
     */
    @POST(AppHttpPath.MODIFY_PWD)
    Observable<BaseResult> modifyPwd(@Body RequestBody requestBody);

    /**
     * 修改手机号（账号）
     *
     * @param account
     * @param token
     * @param userId
     * @param newAccount  新手机号
     * @param password    新密码
     * @param oldPassword 旧密码
     * @return
     */
    @POST(AppHttpPath.UPDATE_PHONE)
    Observable<BaseResult> updatePhone(@Query("account") String account, @Query("token") String token,
                                       @Query("phoneNumber") String phoneNumber, @Query("userId") int userId,
                                       @Query("newAccount") String newAccount, @Query("password") String password,
                                       @Query("oldPassword") String oldPassword,@Query("code") String code);


    /*====================================================    上传案件、巡检等
    ==============================================================*/

    /**
     * 上传案件
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_CASE)
    Observable<BaseResult> publishCase(@Body RequestBody jsonBody);

    /**
     * 获取上传案件的类型
     *
     * @return
     */
    @POST(AppHttpPath.CASE_TYPE)
    Observable<CaseTypeBean> getCaseType(@Query("account") String account, @Query("token") String token,
                                         @Query("id") int id);

    /**
     * 案件（或追踪）详情--及追踪列表
     *
     * @param token
     * @param account
     * @param id      案件id
     * @return
     */
    @POST(AppHttpPath.GET_CASE_INFO)
    Observable<CaseInfoBean> getCaseInfo(@Query("token") String token, @Query("account") String account,
                                         @Query("id") int id);

    /**
     * 上传巡检
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_INSPECTION)
    Observable<BaseResult> publishInspection(@Body RequestBody jsonBody);

    /**
     * 巡检详情
     *
     * @param account
     * @param token
     * @param patrolId 巡检id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_DETAIL)
    Observable<InspectionDetailBean> getInspectionDetail(@Query("account") String account,
                                                         @Query("token") String token, @Query("id") int patrolId);

    /**
     * /**
     * 巡检点详情
     *
     * @param account
     * @param token
     * @param id      巡检id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_POINT_DETAIL)
    Observable<InspectionPointInfoBean> getInspectionPointDetail(@Query("account") String account,
                                                                 @Query("token") String token, @Query("id") int id);

    /**
     * /**
     * 巡检记录
     *
     * @param account
     * @param token
     * @param patrolId 巡检id
     * @return
     */
    @POST(AppHttpPath.INSPECTION_RECORD)
    Observable<InspectionRecordBean> getInspectionRecord(@Query("account") String account,
                                                         @Query("token") String token, @Query("patrolId") int patrolId
            , @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 上传任务上报
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.PUBLISH_TASK_REPORT)
    Observable<BaseResult> publishTaskReport(@Body RequestBody jsonBody);

    /**
     * 根据id获取巡检点信息（扫码）
     */
    @POST(AppHttpPath.GET_INSPECTION_INFO_FOR_SACN)
    Observable<InspectionForScanBean> getInspectionInfo(@Query("codeNumber") String codeNumber);

    /*====================================================    业务模块
    ==============================================================*/

    /**
     * 业务列表
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_LIST)
    Observable<BusinessListBean> businessList(@Query("account") String account, @Query("token") String token, @Query(
            "keyWord") String keyWord, @Query("pageSize") int pageSize, @Query("currentPage") int currentPage);


    /**
     * 业务需要的资料
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_DATA_NEEDED)
    Observable<BusinessNeedInfoBean> businessDataNeeded(@Query("account") String account,
                                                        @Query("token") String token,
                                                        @Query("declareId") int declareId);

    /**
     * 新增业务
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.BUSINESS_ADD)
    Observable<BaseResult> creatBusiness(@Body RequestBody jsonBody);

    /**
     * 业务办理进度
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_PROGRESS)
    Observable<MyBusinessBean> businessProgress(@Body RequestBody jsonBody);

    /**
     * 查询用户提交申请业务详情
     *
     * @return
     */
    @POST(AppHttpPath.BUSINESS_DETAIL)
    Observable<MyBusinessDetailBean> businessDetail(@Body RequestBody jsonBody);


    /*====================================================    调解模块
    ==============================================================*/

    /**
     * 申请调解
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_ADD)
    Observable<BaseResult> publishConciliation(@Body RequestBody jsonBody);

    /**
     * 获取调解列表
     *
     * @param account
     * @param token
     * @param userId
     * @param typeId      类型id （1：调解申请；2：正在调解；3：调解完成）
     * @param pageSize    默认10
     * @param currentPage 默认1
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_LIST)
    Observable<ConciliationListBean> getMyConciliationList(@Query("account") String account,
                                                           @Query("token") String token,
                                                           @Query("userId") int userId, @Query("typeId") int typeId,
                                                           @Query("pageSize") int pageSize,
                                                           @Query("currentPage") int currentPage);

    /**
     * 获取调解详情
     *
     * @return
     */
    @POST(AppHttpPath.CONCILIATION_INFO)
    Observable<ConciliationInfoBean> getConciliationInfo(@Query("account") String account,
                                                         @Query("token") String token, @Query("caseId") int caseId);

    /**
     * 获取全部调解员列表
     *
     * @param account 用户名
     * @param token   验证token
     * @param unitId  单位id
     * @return
     */
    @POST(AppHttpPath.GET_ALL_MEDIATOR_LIST)
    Observable<MediatorAllListBean> getAllMediatorList(@Query("account") String account, @Query("token") String token
            , @Query("cityNumber") String cityNumber, @Query("unitId") int unitId);

    /**
     * 获取单位列表
     *
     * @param account
     * @param token
     * @param cityNumber 辖区id
     * @return
     */
    @POST(AppHttpPath.GET_UNIT_LIST)
    Observable<UnitListBean> getUnitList(@Query("account") String account, @Query("token") String token, @Query(
            "cityNumber") String cityNumber);

    /**
     * 获取调解类型列表
     *
     * @param account
     * @param token
     * @param typeId  类型id（0：大类，其他子类）
     * @return
     */
    @POST(AppHttpPath.GET_CONCILIATION_TYPE_LIST)
    Observable<ConciliationTypesBean> getConciliationTypes(@Query("account") String account,
                                                           @Query("token") String token, @Query("typeId") int typeId);

    /**
     * 查询案件编号是否正确
     *
     * @param account
     * @param token
     * @param caseNumber
     * @return
     */
    @POST(AppHttpPath.SELECT_CASE_NUMBER_IS_CORRECT)
    Observable<BaseResult> selectCaseNumberIsCorrect(@Query("account") String account, @Query("token") String token,
                                                     @Query("caseNumber") String caseNumber);

    /*====================================================    流媒体
    ==============================================================*/

    /**
     * 添加摄像头分享记录
     *
     * @return
     */
    @POST(AppHttpPath.SHARE_TO_WCHAT)
    Observable<BaseResult> shareToWechat(@Body RequestBody requestBody);

    /**
     * 获取硬盘录像机下的所有的流摄像头
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_FROM_VCR)
    Observable<StreamCameraBean> getAllStreamCamerasFromVCR(@Body RequestBody requestBody);

    /**
     * 摄像头详情
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_CAMERAS_DETAIL)
    Observable<StreamCameraDetailBean> getStreamCameraDetail(@Body RequestBody requestBody);

    /**
     * 上传封面图
     *
     * @return
     */
    @POST(AppHttpPath.UPLOAD_STREAM_CAMERAS_THUMB)
    Observable<BaseResult> uploadStreamCameraThumbPic(@Body RequestBody requestBody);

    /**
     * 打开视频流
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_OPE_ADDR)
    Observable<PlayUrlBean> openStream(@Body RequestBody requestBody);

    /**
     * 会话id   保活的接口
     *
     * @param sessionid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/video_keepalive/{sessionid}")
    Observable<OpenLiveBean> keepAlive(@Path("sessionid") String sessionid);

    /**
     * 截图
     *
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/get_image/{channelid}/{type}")
    Observable<CaptureBean> capturePic(@Path("channelid") String channelid, @Path("type") String type);


    /**
     * 录像查询
     *
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/history_search/{begintime}/{endtime}/{channelid}")
    Observable<VideoInfoBean> searchVideos(@Path("begintime") String begintime, @Path("endtime") String endtime,
                                           @Path("channelid") String channelid);


    /**
     * 录像点播 获取rtmp流
     *
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/playback/start?")
    Observable<RecordInfoBean> getVideosUrl(@QueryMap Map<String, String> options);


    /**
     * 云台操控
     *
     * @param ptztype
     * @param ptzparam
     * @param channelid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/ptz/{ptztype}/{ptzparam}/{channelid}")
    Observable<BaseStreamBean> operateYunTai(@Path("ptztype") String ptztype, @Path("ptzparam") int ptzparam,
                                             @Path("channelid") String channelid);


    /**
     * 录像控制
     * "sessionid":    (字符串) 点播返回的sessionid句柄
     * "vodctrltype":  (字符串) "play","pause","stop","jump"
     * "vodctrlparam": (字符串)  0(pause,stop) / 0.125,0.25,0.5,1,2,4,8,16(play) (范围:0-32)/ 从开始时间跳转的秒数(jump)
     *
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/his_stream_ctrl/{sessionid}/{vodctrltype}/{vodctrlparam}")
    Observable<BaseStreamBean> operateRecordVideo(@Path("sessionid") String sessionid,
                                                  @Path("vodctrltype") String vodctrltype,
                                                  @Path("vodctrlparam") String vodctrlparam);

    /**
     * 获取在线数
     */
    @GET(AppHttpPath.GET_ONLINE_AMOUNT)
    Observable<CameraOnlineBean> getOnlineAmount(@Query("q") String q);

    /**
     * 停止流
     */
    @GET(AppHttpPath.STOP_STREAM)
    Observable<BaseStreamBean> stopStream(@Query("sessionid") String q);


    /**
     * 根据监控id获取评论列表
     *
     * @param commentedId 监控id
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_ALL_COMMENT_CAMERA)
    Observable<CommentListBean> getAllCommentCamera(@Query("account") String account, @Query("token") String token,
                                                    @Query("userId") int userId,
                                                    @Query("commentedId") int commentedId,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("currentPage") int currentPage);

    /**
     * 根据主评论id获取子评论列表
     *
     * @param commentedId
     * @return
     */
    @POST(AppHttpPath.GET_CHILD_COMMENT_CAMERA)
    Observable<CommentListBean> getChildCommentCamera(@Query("account") String account, @Query("token") String token,
                                                      @Query("commentedId") int commentedId,
                                                      @Query("unreadId") int unreadId,
                                                      @Query("pageSize") int pageSize,
                                                      @Query("currentPage") int currentPage);

    /**
     * 添加评论
     *
     * @param body
     * @return
     */
    @POST(AppHttpPath.ADD_COMMENT_CAMERA)
    Observable<BaseResult> addCommentCamera(@Body RequestBody body);

    /**
     * 添加或取消点赞(摄像头)
     *
     * @param id      重复添加点赞时传likeid
     * @param account
     * @param token
     * @param userId  用户id（取消点赞时不传）
     * @param isType  状态（0：点赞）（1：取消点赞）必传
     * @param typeId  类型id （取消点赞时不传）
     * @param likeId  点赞时为被点赞的主键；取消点赞时为点赞表的主键
     * @return
     */
    @POST(AppHttpPath.ADD_OR_CANCLE_LIKE_CAMERA)
    Observable<BaseDataBean> addOrCancleLikeCamera(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                   @Query("userId") int userId, @Query("isType") int isType,
                                                   @Query("typeId") int typeId, @Query("likeId") int likeId);

    /**
     * 添加（删除）收藏 (摄像头)
     *
     * @param id        重复添加收藏时传collectId
     * @param account
     * @param token
     * @param userId    用户id（取消收藏时不传）
     * @param isType    状态（0：收藏）（1：取消收藏）必传
     * @param typeId    类型id（1监控点赞；2评论点赞）
     * @param collectId 收藏时为被收藏的主键
     * @param ids       取消收藏时为收藏表的主键（删除时传）
     * @return
     */
    @POST(AppHttpPath.ADD_OR_DELETE_COLLECT_CAMERA)
    Observable<BaseDataBean> addOrDeleteCollectsCamera(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                       @Query("userId") int userId, @Query("isType") int isType,
                                                       @Query("typeId") int typeId,
                                                       @Query("collectId") int collectId,
                                                       @Query("ids") List<Integer> ids);

    /*==============================================  流媒体云台预置位  =============================================*/


    //    @POST(AppHttpPath.ADD_PRE_POSITION)
    //    Observable<BaseResult> addPrePosition(@Body RequestBody body);
    //
    //    /**
    //     * 删除预置位
    //     *
    //     * @param body
    //     * @return
    //     */
    //    @POST(AppHttpPath.DEL_PRE_POSITION)
    //    Observable<BaseResult> delPrePosition(@Body RequestBody body);
    //
    //    /**
    //     * 获取预置位
    //     *
    //     * @param body
    //     * @return
    //     */
    //    @POST(AppHttpPath.GET_PRE_POSITIONS)
    //    Observable<PreSetBean> getPrePositions(@Body RequestBody body);

    /*====================================================    兑换商城模块
    ==============================================================*/

    /**
     * 获取商品列表
     *
     * @param account
     * @param token
     * @return
     */
    @POST(AppHttpPath.MALL_ALL_GOODS_LIST)
    Observable<GoodsListBean> getGoodsList(@Query("account") String account, @Query("token") String token);

    /**
     * 获取商品详情
     *
     * @param account
     * @param token
     * @param commodityId
     * @return
     */
    @POST(AppHttpPath.MALL_GOODS_DEATIL)
    Observable<GoodsInfoBean> getGoodsDetail(@Query("account") String account, @Query("token") String token, @Query(
            "commodityId") int commodityId);

    /**
     * 获取兑换记录
     *
     * @param account
     * @param token
     * @param userId
     * @return
     */
    @POST(AppHttpPath.MALL_HISTORY_LIST)
    Observable<HistoryGoodsListBean> getHistoryGoodsList(@Query("account") String account,
                                                         @Query("token") String token, @Query("userId") int userId);

    /**
     * 兑换商品
     *
     * @param account
     * @param token
     * @param userId
     * @param price         商品积分价格
     * @param commodityId   商品id
     * @param commodityName 商品名称
     * @return
     */
    @POST(AppHttpPath.MALL_EXCHANGE_GOODS)
    Observable<BaseResult> exchangeGoods(@Query("account") String account, @Query("token") String token, @Query(
            "userId") int userId,
                                         @Query("price") int price, @Query("commodityId") int commodityId, @Query(
                                                 "commodityName") String commodityName);

    /*====================================================    重点人员模块
    ==============================================================*/

    /**
     * 重点人员详情
     *
     * @return
     */
    @POST(AppHttpPath.KEY_PERSONNEL_DETAIL)
    Observable<KeyPersonnelInfoBean> getKeyPersonnelInfo(@Query("account") String account,
                                                         @Query("token") String token, @Query("id") int id);

    /**
     * 走访上传
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_ADD)
    Observable<BaseResult> addInterview(@Body RequestBody jsonBody);

    /**
     * 获取走访列表
     *
     * @param account
     * @param token
     * @param id      重点人员id
     * @param page    页码
     * @param rows    条数
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_LIST)
    Observable<InterviewListBean> getInterviewList(@Query("account") String account, @Query("token") String token,
                                                   @Query("id") int id,
                                                   @Query("currentPage") int page, @Query("pageSize") int rows);

    /**
     * 获取走访详情
     *
     * @return
     */
    @POST(AppHttpPath.INTERVIEW_DETAIL)
    Observable<InterviewDetailBean> getInterviewDetail(@Query("account") String account, @Query("token") String token
            , @Query("id") int id);

    /*====================================================    场所管理模块
    ==============================================================*/

    /**
     * 单位详情
     *
     * @return
     */
    @POST(AppHttpPath.UNIT_DETAIL)
    Observable<UnitDetailBean> getUnitDetail(@Body RequestBody requestBody);

    /**
     * 从业人员列表
     *
     * @param account
     * @param token
     * @param unitId      场所id
     * @param pageSize    页码
     * @param currentPage 条数
     * @return
     */
    @POST(AppHttpPath.GET_EMPLOYEE_LIST)
    Observable<EmployeeListBean> getEmployeeList(@Query("account") String account, @Query("token") String token,
                                                 @Query("unitId") int unitId,
                                                 @Query("pageSize") int pageSize,
                                                 @Query("currentPage") int currentPage);

    /**
     * 从业人员详情
     *
     * @return
     */
    @POST(AppHttpPath.GET_EMPLOYEE_DETAIL)
    Observable<EmployeeDetailBean> getEmployeeDetail(@Query("account") String account, @Query("token") String token,
                                                     @Query("personnelId") int personnelId);

    /**
     * 检查记录列表
     *
     * @param account
     * @param token
     * @param unitId      场所id
     * @param pageSize    页码
     * @param currentPage 条数
     * @return
     */
    @POST(AppHttpPath.GET_SITE_INSPECTION_LIST)
    Observable<EmployeeListBean> getSiteInspectionList(@Query("account") String account, @Query("token") String token, @Query("unitId") int unitId,
                                                       @Query("pageSize") int pageSize,
                                                       @Query("currentPage") int currentPage);

    /**
     * 检查记录详情
     *
     * @return
     */
    @POST(AppHttpPath.GET_SITE_INSPECTION_DETAIL)
    Observable<SiteInspectionDetailBean> getSiteInspectionDetail(@Query("account") String account,
                                                                 @Query("token") String token,
                                                                 @Query("recordId") int recordId);

    /**
     * 添加从业人员
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_EMPLOYEE)
    Observable<BaseResult> addEmployee(@Body RequestBody jsonBody);

    /**
     * 添加检查记录
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_SITE_INSPECTION)
    Observable<BaseResult> addSiteInspection(@Body RequestBody jsonBody);

    /**
     * 获取单位类型列表
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.GET_SITE_TYPE_LIST)
    Observable<SiteTypeBean> getSiteTypes(@Body RequestBody jsonBody);

    /**
     * 获取客户来源列表
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.GET_CUSTOMER_SOURCE_LIST)
    Observable<CustomerSourceBean> getCustomerSources(@Body RequestBody jsonBody);

    /**
     * 添加场所
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.ADD_SITE_MANAGER)
    Observable<BaseResult> addSiteManager(@Body RequestBody jsonBody);

    /**
     * 修改场所
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPDATE_SITE_MANAGER)
    Observable<BaseResult> updateSiteManager(@Body RequestBody jsonBody);

    /*====================================================    资讯模块
    ==============================================================*/

    /**
     * 获取资讯列表
     *
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_NEWS_LIST)
    Observable<NewsListBean> getNewsList(@Query("userId") int userId, @Query("pageSize") int pageSize, @Query("currentPage") int currentPage);

    /**
     * 上传资讯
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.NEWS_PUBLISH)
    Observable<BaseResult> publishNews(@Body RequestBody jsonBody);

    /**
     * 修改资讯
     *
     * @param jsonBody
     * @return
     */
    @POST(AppHttpPath.UPDATE_NEWS)
    Observable<BaseResult> updateNews(@Body RequestBody jsonBody);


    /**
     * 资讯图片上传
     *
     * @return
     */
    @POST(AppHttpPath.NEWS_UPLOAD_PHOTO)
    Observable<NewsUploadPhotoBean> uploadNewsPhoto(@Body RequestBody jsonBody);

    /**
     * 资讯图片删除
     *
     * @return
     */
    @POST(AppHttpPath.NEWS_DELETE_PHOTO)
    Observable<BaseResult> deleteNewsPhoto(@Query("account") String account, @Query("token") String token, @Query(
            "informationId") String informationId);

    /**
     * 资讯搜索
     *
     * @param keyWord
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.NEWS_SEARCH_LIST)
    Observable<NewsListBean> searchNewsList(@Query("keyWord") String keyWord, @Query("pageSize") int pageSize,
                                            @Query("currentPage") int currentPage);

    /**
     * 获取资讯详情
     *
     * @param userId
     * @param infoId
     * @return
     */
    @POST(AppHttpPath.GET_NEWS_INFO)
    Observable<NewsDetailBean> getNewsInfo(@Query("userId") int userId, @Query("infoId") int infoId);

    /**
     * 获取资讯作者信息
     *
     * @param userId 看谁传谁的id
     * @param fId    登录人的id
     * @return
     */
    @POST(AppHttpPath.NEWS_PERSONAL_HOMEPAGE_INFO)
    Observable<NewsPersonalHomePageInfo> getNewsAuthorInfo(@Query("userId") int userId, @Query("fId") int fId);

    /**
     * 获取个人主页资讯列表
     */
    @POST(AppHttpPath.NEWS_PERSONAL_HOMEPAGE_NEWS_LIST)
    Observable<NewsListBean> getNewsListByAuthorId(@Query("userId") int userId, @Query("typeId") int typeId,
                                                   @Query("currentPage") int currentPage,
                                                   @Query("pageSize") int pageSize);

    /**
     * 获取个人粉丝列表
     *
     * @param account
     * @param token
     * @param userId      看谁传谁id
     * @param followId    自己的id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @POST(AppHttpPath.NEWS_FANS_LIST)
    Observable<NewsFansListBean> getFansList(@Query("account") String account, @Query("token") String token,
                                             @Query("userId") int userId, @Query("followId") int followId,
                                             @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 获取个人关注列表
     *
     * @param account
     * @param token
     * @param userId      看谁传谁id
     * @param fansId      自己的id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @POST(AppHttpPath.NEWS_FOLLOW_LIST)
    Observable<NewsFansListBean> getFollowList(@Query("account") String account, @Query("token") String token,
                                               @Query("userId") int userId, @Query("fansId") int fansId,
                                               @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);

    /**
     * 添加或取消关注
     *
     * @param account
     * @param token
     * @param fansId
     * @param typeId   1 :关注；2:取消关注
     * @param followId
     * @return
     */
    @POST(AppHttpPath.NEWS_ADD_FOLLOW_OR_DELETE)
    Observable<BaseResult> addFollowOrDelete(@Query("account") String account, @Query("token") String token,
                                             @Query("fansId") int fansId, @Query("typeId") int typeId, @Query(
                                                     "followId") int followId);

    /**
     * 根据资讯id获取评论列表
     *
     * @param commentedId 资讯文章id
     * @param pageSize
     * @param currentPage
     * @return
     */
    @POST(AppHttpPath.GET_ALL_COMMENT_NEWS)
    Observable<CommentListBean> getAllCommentNews(@Query("account") String account, @Query("token") String token,
                                                  @Query("userId") int userId, @Query("commentedId") int commentedId,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("currentPage") int currentPage);

    /**
     * 根据主评论id获取子评论列表
     *
     * @param commentedId
     * @return
     */
    @POST(AppHttpPath.GET_CHILD_COMMENT_NEWS)
    Observable<CommentListBean> getChildCommentNews(@Query("account") String account, @Query("token") String token,
                                                    @Query("commentedId") int commentedId,
                                                    @Query("unreadId") int unreadId,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("currentPage") int currentPage);

    /**
     * 添加评论
     *
     * @param body
     * @return
     */
    @POST(AppHttpPath.ADD_COMMENT_NEWS)
    Observable<BaseResult> addCommentNews(@Body RequestBody body);

    /**
     * 添加或取消点赞(资讯)
     *
     * @param id      重复添加点赞是传likeid
     * @param account
     * @param token
     * @param userId  用户id（取消点赞时不传）
     * @param isType  状态（0：点赞）（1：取消点赞）必传
     * @param typeId  类型id （取消点赞时不传）
     * @param likeId  点赞时为被点赞的主键；取消点赞时为点赞表的主键
     * @return
     */
    @POST(AppHttpPath.ADD_OR_CANCLE_LIKE_NEWS)
    Observable<BaseDataBean> addOrCancleLikeNews(@Query("id") int id, @Query("account") String account, @Query("token"
    ) String token,
                                                 @Query("userId") int userId, @Query("isType") int isType,
                                                 @Query("typeId") int typeId, @Query("likeId") int likeId);

    /**
     * 添加（删除）收藏 (资讯)
     *
     * @param id        重复添加收藏时传collectId
     * @param account
     * @param token
     * @param userId    用户id（取消收藏时不传）
     * @param isType    状态（0：收藏）（1：取消收藏）必传
     * @param typeId    类型id （取消收藏时不传）
     * @param collectId 收藏时为被收藏的主键
     * @param ids       取消收藏时为收藏表的主键（删除时传）
     * @return
     */
    @POST(AppHttpPath.ADD_OR_DELETE_COLLECT_NEWS)
    Observable<BaseDataBean> addOrDeleteCollectsNews(@Query("id") int id, @Query("account") String account, @Query(
            "token") String token,
                                                     @Query("userId") int userId, @Query("isType") int isType,
                                                     @Query("typeId") int typeId,
                                                     @Query("collectId") int collectId,
                                                     @Query("ids") List<Integer> ids);

    /*====================================================    我的设备
    ==============================================================*/

    /**
     * 获取我的设备列表
     *
     * @return
     */
    @POST(AppHttpPath.GET_USER_EQUIPMENT_LIST)
    Observable<EquipmentListBean> getUserEquipmentList(@Query("account") String account, @Query("token") String token
            , @Query("unreadId") int unreadId,
                                                       @Query("currentPage") int currentPage, @Query("pageSize") int pageSize);



    /**
     * 获取举报类型
     *
     * @return
     */
    @POST(AppHttpPath.GET_REPORT_TYPES)
    Observable<ReportTypeBean> getReportTypes(@Body RequestBody jsonBody);
    /**
     * 获取举报类型
     *
     * @return
     */
    @POST(AppHttpPath.REPORT)
    Observable<BaseResult> report(@Body RequestBody jsonBody);



}

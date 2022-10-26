package com.example.net;

public class AppHttpPath {


//    public static final String BASE = "http://192.168.124.148:8080/jt-mall";
        public static final String BASE = "https://www.juntaikeji.com:21965/jt-mall";
public static final String BASE_LIVE_URL = "ws://www.juntaikeji.com:21970/jt-mall/liveSocket";
//public static final String BASE_LIVE_URL = "ws://192.168.124.148:8080/jt-mall/liveSocket";

    public static final String ALL_CITYS = "https://restapi.amap.com/v3/config/district?";
    /*==============================================  流媒体相关  =============================================*/


    //摄像头拉流地址
    public static final String BASE_CAMERA_URL = "http://www.juntaikeji.net:8060";
    /**
     * 获取视频播放地址
     */
    public static final String STREAM_OPE_ADDR = "http://61.156.157.132:35080/streamingMedia/u/app/getVideoOpenStream.shtml";

    /**
     * 上传图片或视频
     */
    public static final String UPLOAD_FILES = BASE + "/uploadFile/upload";
    /**
     * 登录
     */
    public static final String LOGIN = BASE + "/member/login";
    /**
     * 注册
     */
    public static final String REGIST = BASE + "/member/register";


    /**
     * /**
     * 检查更新
     */
    public static final String APP_UPDATE = BASE + "/member/detectionAppVersions";

    /**getOrderFormList
     * 获取短信验证码
     */
    public static final String GET_SMS_CODE = BASE + "/member/getSMSCode";


    /**
     * 查找周边商铺
     */
    public static final String GET_SHOPES_AROUND = BASE + "/buyers/selectPeripheryShopList";







    /*====================================================    个人中心   ==============================================================*/


    /**
     * 个人详情
     */
    public static final String GET_USER_INFO = BASE + "/member/getUserInfo";
    /**
     * 提交意见反馈
     */
    public static final String COMMIT_SUGGESTION = BASE + "/opinion/save";
    /**
     * 退出登录
     */
    public static final String LOGOUT = BASE + "/member/logout";

    /**
     * 修改密码
     */
    public static final String MODIFY_PWD = BASE + "/member/updateMemberPwd";
    /**
     * 修改个人信息
     */
    public static final String MODIFY_USER_INFO = BASE + "/member/updateMember";

    /**
     * 修改账户
     */
    public static final String MODIFY_USER_ACCOUNT = BASE + "/member/updateUserAccountNumber";





    /*====================================================    天气   ==============================================================*/

    //实时天气
    public static final String REALTIME_WEATHER = BASE + "/buyers/getRealTimeWeather";
    //天气预报
    public static final String FORCAST_WEATHER = BASE + "/buyers/weatherForecast";
    //获取省份
    public static final String PROVINCE = BASE + "/u/appConnector/getProvince.shtml";
    //获取城市 u/apiAppAlarm/getProvince.shtml
    public static final String CITY = BASE + "/u/appConnector/getCity.shtml";
    //获取地区 u/apiAppAlarm/getProvince.shtml
    public static final String AREA = BASE + "/u/appConnector/getArea.shtml";
    //获取街道
    public static final String STREET = BASE + "/u/appConnector/getStreet.shtml";






    /*====================================================    商品   ==============================================================*/


    //商品类目
    public static final String COMMODIFY_LABELS = BASE + "/buyers/selectShopCategoryList";
    /**
     * 商品推荐
     */
    public static final String COMMODIFY_RECOMMEND = BASE + "/buyers/selectRecommendList";
    /**
     * 商品详情
     */
    public static final String COMMODIFY_DETAIL = BASE + "/buyers/selectCommodityInfo";
    /**
     * 商品评价
     */
    public static final String COMMODIFY_EVALUATION = BASE + "/buyers/selectCommodityEvaluateList";





    /*====================================================    店铺   ==============================================================*/

    /**
     * 店铺详情
     */
    public static final String SHOP_DETAIL = BASE + "/buyers/selectShopInfo";
    /**
     * 店铺详情
     */
    public static final String SELL_SHOP_DETAIL = BASE + "/seller/selectShopInfo";
    /**
     * 店铺商品列表
     */
    public static final String SHOP_COMMODITY_LIST = BASE + "/buyers/selectShopCommodityList";
    /**
     * 店铺收藏或取消
     */
    public static final String SHOP_COLLECT = BASE + "/buyers/addCollectShop";
    /**
     * 店铺收藏列表
     */
    public static final String SHOP_COLLECT_LIST = BASE + "/buyers/getShopCollectList";
    /**
     * 商品收藏或取消
     */
    public static final String COMMODITY_COLLECT = BASE + "/buyers/addCollectCommodity";
    /**
     * 商品收藏列表
     */
    public static final String COMMODITY_COLLECT_LIST = BASE + "/buyers/getCommodityCollectList";







    /*====================================================    购物车   ==============================================================*/

    /**
     * 加入修改购物车
     */
    public static final String EDIT_CART = BASE + "/buyers/addShoppingTrolley";
    /**
     * 购物车列表
     */
    public static final String CART_LIST = BASE + "/buyers/selectShoppingTrolleyList";
    /**
     * 删除购物车商品
     */
    public static final String DELETE_CART_COMMODITY = BASE + "/buyers/deleteShoppingTrolley";





    /*====================================================    地址管理   ==============================================================*/


    /**
     * 地址列表
     */
    public static final String ADDR_LIST = BASE + "/buyers/selectShippingAddressList";
    /**
     * 添加（修改）用户收货地址
     */
    public static final String ADD_OR_EDIT_ADDR = BASE + "/buyers/addShippingAddress";
    /**
     * 删除用户收货地址
     */
    public static final String DELETE_ADDR = BASE + "/buyers/deleteShippingAddress";
    /**
     * 设置默认地址
     */
    public static final String SET_DEFAULT_ADDR = BASE + "/buyers/defaultShippingAddress";







    /*====================================================    订单相关   ==============================================================*/


    /**
     * 购物车生成订单
     */
    public static final String CREAT_ORDER_CART = BASE + "/buyers/createTrolleyOrderForm";

    /**
     * 购物车生成订单
     */
    public static final String CREAT_ORDER_BUY = BASE + "/buyers/createOrderForm";
    /**
     * 提交订单
     */
    public static final String COMMIT_ORDER = BASE + "/buyers/submitOrderForm";
    /**
     * 取消订单
     */
    public static final String CANCEL_ORDER = BASE + "/buyers/cancelOrderForm";
    /**
     * 提醒发货
     */
    public static final String NOTICE_SEND = BASE + "/buyers/remindDelivery";
    /**
     * 删除已取消订单
     */
    public static final String DELETE_CANCEL_ORDER = BASE + "/buyers/deleteOrder";
    /**
     * 确认收货
     */
    public static final String CONFIRM_RECEIVED = BASE + "/buyers/confirmOrderForm";
    /**
     * 订单状态
     */
    public static final String ORDER_STATUS = BASE + "/buyers/getOrderFormStateNum";
    /**
     * 订单列表
     */
    public static final String ORDER_LIST = BASE + "/buyers/getOrderFormList";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = BASE + "/buyers/getOrderFormInfo";

    /**
     * 对公付款
     */
    public static final String ORDER_PAY_PUB_ACCOUNT = BASE + "/buyers/publicPay/publicPayTradeAppPayRequest";
    /**
     * 微信支付
     */
    public static final String ORDER_PAY_PUB_WEIXIN = BASE + "/buyers/weChatPay/weChatPayTradeAppPayRequest";
    /**
     * 支付宝支付
     */
    public static final String ORDER_PAY_ZHIFUBAO = BASE + "/buyers/aliPay/aliPayTradeAppPayRequest";
    /**
     * 订单状态数量
     */
    public static final String ORDER_STATUS_AMOUNT = BASE + "/buyers/getOrderFormStateNum";

    /**
     * 退货原因
     */
    public static final String GET_REFUND_REASON = BASE + "/buyers/getSalesReturnCause";

    /**
     * 申请退款
     */
    public static final String REQUEST_REFUND = BASE + "/buyers/addSalesReturn";

    /**
     * 发布评价
     */
    public static final String START_EVALUATE = BASE + "/buyers/addCommodityEvaluate";





    /*====================================================    消息   ==============================================================*/


    /**
     * 发送消息
     */
    public static final String SEND_MSG = BASE + "/msg/sendMassage";
    /**
     * 消息已读
     */
    public static final String MESSAGE_READ = BASE + "/msg/updateMsgRead";
    /**
     * 未读消息详情
     */
    public static final String UNREAD_CONTACT_MSG = BASE + "/msg/selectMsgInfo";
    /**
     * 消息列表
     */
    public static final String NEWS_LIST = BASE + "/msg/selectMsgList";


    /**
     * 搜索
     */
    public static final String SEARCH_COMMODITY = BASE + "/member/search";

    /**
     * 搜索
     */
    public static final String SEARCH_SHOP = BASE + "/member/search";




    /*====================================================    直播   ==============================================================*/
    /**
     * 直播类型标签
     */
    public static final String GET_LIVE_TYPE = BASE + "/live/getLiveType";
    /**
     * 获取直播列表
     */
    public static final String GET_LIVE_LIST = BASE + "/live/getLiveRoomList";


//    public static final String BASE_LIVE_URL ="ws://42.192.40.58:5000/ws";

    /**
     * 开启直播
     */
    public static final String START_LIVE = BASE + "/live/openLive";
    /**
     * 直播详情
     */
    public static final String LIVE_DETAIL = BASE + "/live/getLiveInfo";


    /**
     * 上传图片
     */
    public static final String UPLOAD_PICS = BASE + "/uploadFile/upload";
    /**
     * 直播间商品列表
     */
    public static final String LIVE_ROOM_COMMODITIES = BASE + "/buyers/selectLiveCommodityList";

    public static final String SHOP_COLLECT_FINISH = BASE + "/buyers/addCollectShop///finish";
    public static final String SHOP_UNCOLLECT = BASE + "/buyers/unCollectShop";





    /*====================================================    农发管理端   ==============================================================*/


    /**
     * 学校列表
     */
    public static final String SCHOOL_LIST = BASE + "/manage/selectSchoolList";


    /**
     * 商品列表
     */

    public static final String MANAGER_COMMODITY_LIST = BASE + "/manage/selectCommodityList";
    public static final String MANAGER_COMMODITY_DETAIL = BASE + "/manage/selectCommodityInfo";
    /**
     * 更新商品的状态
     */
    public static final String UPDATE_COMMODITY_STATUS = BASE + "/manage/updateCommodityState";
    public static final String UPDATE_COMMODITY_STATUS_DOWN = BASE + "UPDATE_COMMODITY_STATUS_DOWN";
    /**
     * 店铺管理列表
     */
    public static final String MANAGER_SHOP_LIST = BASE + "/manage/selectShopList";
    public static final String MANAGER_SHOP_DETAIL = BASE + "/manage/selectShopInfo";
    /**
     * 提交店铺审核
     */
    public static final String COMMIT_SHOP_CHECK = BASE + "/manage/updateShopState";






    /*====================================================    农发订单管理   ==============================================================*/

    /**
     * 分拣详情
     */
    public static final String SORT_ORDER_DETAIL = BASE + "/manage/selectSortingOrderInfo";

    public static final String NF_ORDER_LIST = BASE + "/manage/selectOrderFormList";

    /**
     * 分拣状态
     */
    public static final String UPDATE_SORT_STATUS = BASE + "/manage/updateSorting";
    /**
     * 分配状态
     */
    public static final String UPDATE_DELIVERY_STATUS = BASE + "/manage/updateDelivery";


}
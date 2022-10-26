package com.example.net;


import com.example.appbase.bean.AroundShopBean;
import com.example.appbase.bean.CartListBean;
import com.example.appbase.bean.CitysBean;
import com.example.appbase.bean.CollectDataBean;
import com.example.appbase.bean.CommodityDesListBean;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.CommodityEvaluationBean;
import com.example.appbase.bean.IdNameBean;
import com.example.appbase.bean.LiveDetailBean;
import com.example.appbase.bean.LiveListBean;
import com.example.appbase.bean.LiveResultBean;
import com.example.appbase.bean.LiveTypeListBean;
import com.example.appbase.bean.NewsListBean;
import com.example.appbase.bean.PlayUrlBean;
import com.example.appbase.bean.SellOrderListBean;
import com.example.appbase.bean.ShopCommodityListBean;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.bean.ShopListDataBean;
import com.example.appbase.bean.UserBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerListBean;
import com.example.appbase.bean.nong_fa_manager.SchoolListBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerListBean;
import com.example.appbase.bean.nong_fa_manager.SortDetailBean;
import com.example.appbase.bean.order.ConfirmOrderBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.appbase.bean.order.OrderDetailDataBean;
import com.example.appbase.bean.order.OrderListBean;
import com.example.appbase.bean.order.OrderPayWxBean;
import com.example.appbase.bean.order.OrderPayZfbBean;
import com.example.appbase.bean.order.OrderStatusAmountBean;
import com.example.appbase.bean.order.RefundReasonBean;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.OpenLiveBean;
import com.juntai.disabled.basecomponent.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.bean.address.AddressListBean;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageListBean;
import com.juntai.disabled.basecomponent.bean.shop.ShopDetailBuyBean;
import com.juntai.disabled.basecomponent.bean.weather.CityBean;
import com.juntai.disabled.basecomponent.bean.weather.ResponseForcastWeather;
import com.juntai.disabled.basecomponent.bean.weather.ResponseRealTimeWeather;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServer {

    /**
     * 会话id   保活的接口
     *
     * @param sessionid
     * @return
     */
    @GET(AppHttpPath.BASE_CAMERA_URL + "/vss/video_keepalive/{sessionid}")
    Observable<OpenLiveBean> keepAlive(@Path("sessionid") String sessionid);

    /**
     * 打开视频流
     *
     * @return
     */
    @POST(AppHttpPath.STREAM_OPE_ADDR)
    Observable<PlayUrlBean> openStream(@Body RequestBody requestBody);

    @POST(AppHttpPath.GET_SHOPES_AROUND)
    Observable<AroundShopBean> getAroundShopes(@Body RequestBody requestBody);


    /*====================================================    地址管理   ==============================================================*/
    @POST(AppHttpPath.ADDR_LIST)
    Observable<AddressListBean> getAddrList(@Body RequestBody requestBody);

    @POST(AppHttpPath.SET_DEFAULT_ADDR)
    Observable<BaseResult> setDefaultAdddr(@Body RequestBody requestBody);

    @POST(AppHttpPath.ADD_OR_EDIT_ADDR)
    Observable<BaseResult> addOrEditAddrList(@Body RequestBody requestBody);

    @POST(AppHttpPath.DELETE_ADDR)
    Observable<BaseResult> deleteAddr(@Query("account") String account, @Query("token") String token, @Query("typeEnd") String typeEnd, @Query("id") List<Integer> ids);




    /*====================================================    天气   ==============================================================*/


    //实时天气
    @POST(AppHttpPath.REALTIME_WEATHER)
    Observable<ResponseRealTimeWeather> getWeatherRealtime(@Query("longitude") String longitude, @Query("latitude") String latitude);

    //天气预报
    @POST(AppHttpPath.FORCAST_WEATHER)
    Observable<ResponseForcastWeather> getForcast(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @POST(AppHttpPath.PROVINCE)
    Observable<CityBean> getProvince();

    @POST(AppHttpPath.CITY)
    Observable<CityBean> getCity(@Query("cityNum") int cityNum);

    @POST(AppHttpPath.AREA)
    Observable<CityBean> getArea(@Query("cityNum") int cityNum);

    @POST(AppHttpPath.STREET)
    Observable<CityBean> getStreet(@Query("cityName") int townNum);




    /*====================================================    商品信息   ==============================================================*/


    @POST(AppHttpPath.COMMODIFY_LABELS)
    Observable<IdNameBean> getCommodityLaBels();


    @POST(AppHttpPath.COMMODIFY_RECOMMEND)
    Observable<CommodityDesListBean> getCommodityRecommendList(@Body RequestBody requestBody);

    @POST(AppHttpPath.COMMODIFY_DETAIL)
    Observable<CommodityDetailBean> getCommodityDetail(@Body RequestBody requestBody);

    @POST(AppHttpPath.COMMODIFY_EVALUATION)
    Observable<CommodityEvaluationBean> getCommodityEvaluation(@Body RequestBody requestBody);



    /*====================================================    店铺详情   ==============================================================*/


    @POST(AppHttpPath.SHOP_DETAIL)
    Observable<ShopDetailBuyBean> getShopDetail(@Body RequestBody requestBody);


    @POST(AppHttpPath.SELL_SHOP_DETAIL)
    Observable<ShopDetailSellBean> getSellShopDetail(@Body RequestBody requestBody);

    @POST(AppHttpPath.SHOP_COMMODITY_LIST)
    Observable<ShopCommodityListBean> getShopCommodityList(@Body RequestBody requestBody);


    @POST(AppHttpPath.SHOP_COLLECT)
    Observable<BaseResult> collectShop(@Body RequestBody requestBody);


    @POST(AppHttpPath.COMMODITY_COLLECT)
    Observable<BaseResult> collectCommodity(@Body RequestBody requestBody);


    @POST(AppHttpPath.SHOP_COLLECT_LIST)
    Observable<CollectDataBean> getShopCollectList(@Body RequestBody requestBody);

    @POST(AppHttpPath.COMMODITY_COLLECT_LIST)
    Observable<CollectDataBean> getCommodityCollectList(@Body RequestBody requestBody);



    /*====================================================    购物车   ==============================================================*/


    @POST(AppHttpPath.EDIT_CART)
    Observable<BaseResult> editCart(@Body RequestBody requestBody);

    @POST(AppHttpPath.CART_LIST)
    Observable<CartListBean> getCartList(@Body RequestBody requestBody);

    @POST(AppHttpPath.DELETE_CART_COMMODITY)
    Observable<BaseResult> deleteCartCommodity(@Query("account") String account, @Query("token") String token, @Query("typeEnd") String typeEnd, @Query("id") List<Integer> ids);


    @GET(AppHttpPath.ALL_CITYS)
    Observable<CitysBean> getAllCitys(@Query("keywords") String keywords, @Query("subdistrict") String subdistrict, @Query("key") String key);


    /**
     * 商城登录
     *
     * @return
     */
    @POST(AppHttpPath.LOGIN)
    Observable<UserBean> login(@Body RequestBody requestBody);


    /**
     * 登录
     *
     * @return
     */
    @POST(AppHttpPath.REGIST)
    Observable<UserBean> regist(@Body RequestBody requestBody);

    /**
     * 上传文件
     *
     * @return
     */
    @POST(AppHttpPath.UPLOAD_FILES)
    Observable<UploadFileBean> uploadFiles(@Body RequestBody requestBody);

    @POST(AppHttpPath.MODIFY_PWD)
    Observable<BaseResult> modifyPwd(@Body RequestBody requestBody);

    /**
     * account  手机号
     *
     * @return
     */
    @GET(AppHttpPath.GET_SMS_CODE + "/{phoneNumber}")
    Observable<BaseResult> getSMSCode(@Path("phoneNumber") String path);






    /*====================================================    个人中心   ==============================================================*/


    /**
     * account  手机号
     *
     * @return
     */
    @POST(AppHttpPath.GET_USER_INFO)
    Observable<UserBean> getUserInfo(@Body RequestBody requestBody);

    /**
     * 修改账户
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPath.MODIFY_USER_ACCOUNT)
    Observable<BaseResult> modifyAccount(@Body RequestBody requestBody);


    @POST(AppHttpPath.COMMIT_SUGGESTION)
    Observable<BaseResult> commitSuggestion(@Body RequestBody requestBody);

    /**
     * 修改用户信息
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPath.MODIFY_USER_INFO)
    Observable<BaseResult> modifyUserInfo(@Body RequestBody requestBody);

    @POST(AppHttpPath.LOGOUT)
    Observable<BaseResult> logout(@Body RequestBody requestBody);



    /*====================================================    订单部分   ==============================================================*/

    @POST(AppHttpPath.CREAT_ORDER_CART)
    Observable<CreatOrderBean> creatOrderCart(@Body RequestBody requestBody);

    @POST(AppHttpPath.CREAT_ORDER_BUY)
    Observable<CreatOrderBean> creatOrderBuy(@Body RequestBody requestBody);

    @POST(AppHttpPath.COMMIT_ORDER)
    Observable<ConfirmOrderBean> commitOrder(@Body RequestBody requestBody);

    @POST(AppHttpPath.CANCEL_ORDER)
    Observable<BaseResult> cancelOrder(@Body RequestBody requestBody);

    @POST(AppHttpPath.NOTICE_SEND)
    Observable<BaseResult> noticeSend(@Body RequestBody requestBody);

    @POST(AppHttpPath.DELETE_CANCEL_ORDER)
    Observable<BaseResult> deleteCancelOrder(@Body RequestBody requestBody);

    @POST(AppHttpPath.REQUEST_REFUND)
    Observable<BaseResult> requestRefund(@Body RequestBody requestBody);

    @POST(AppHttpPath.START_EVALUATE)
    Observable<BaseResult> startEvaluate(@Body RequestBody requestBody);


    @POST(AppHttpPath.CONFIRM_RECEIVED)
    Observable<BaseResult> confirmReceived(@Body RequestBody requestBody);

    @POST(AppHttpPath.ORDER_STATUS)
    Observable<BaseResult> getOrderStatus(@Body RequestBody requestBody);


    @POST(AppHttpPath.ORDER_LIST)
    Observable<OrderListBean> getOrderList(@Body RequestBody requestBody);

    @POST(AppHttpPath.ORDER_DETAIL)
    Observable<OrderDetailDataBean> getOrderDetail(@Body RequestBody requestBody);

    @POST(AppHttpPath.ORDER_STATUS_AMOUNT)
    Observable<OrderStatusAmountBean> getOrderStatusAmount(@Body RequestBody requestBody);

    @POST(AppHttpPath.GET_REFUND_REASON)
    Observable<RefundReasonBean> getRefundReasons(@Body RequestBody requestBody);


    @POST(AppHttpPath.ORDER_PAY_PUB_WEIXIN)
    Observable<OrderPayWxBean> payByWeixin(@Body RequestBody requestBody);

    @POST(AppHttpPath.ORDER_PAY_ZHIFUBAO)
    Observable<OrderPayZfbBean> payByZhifubao(@Body RequestBody requestBody);

    @POST(AppHttpPath.ORDER_PAY_PUB_ACCOUNT)
    Observable<BaseResult> payByPubAccount(@Body RequestBody requestBody);






    /*====================================================    消息   ==============================================================*/

    @POST(AppHttpPath.SEND_MSG)
    Observable<BaseResult> sendMessage(@Body RequestBody requestBody);

    @POST(AppHttpPath.MESSAGE_READ)
    Observable<BaseResult> messageRead(@Body RequestBody requestBody);

    @POST(AppHttpPath.UNREAD_CONTACT_MSG)
    Observable<MessageListBean> getContactUnreadMsg(@Body RequestBody requestBody);

    @POST(AppHttpPath.NEWS_LIST)
    Observable<NewsListBean> getNewsList(@Body RequestBody requestBody);

    @POST(AppHttpPath.SEARCH_COMMODITY)
    Observable<CommodityDesListBean> startSearchCommodity(@Body RequestBody requestBody);

    @POST(AppHttpPath.SEARCH_SHOP)
    Observable<ShopListDataBean> startSearchShop(@Body RequestBody requestBody);





    /*====================================================    直播   ==============================================================*/

    @POST(AppHttpPath.GET_LIVE_TYPE)
    Observable<LiveTypeListBean> getLiveType();

    @POST(AppHttpPath.GET_LIVE_LIST)
    Observable<LiveListBean> getLiveList(@Body RequestBody requestBody);


    /**
     * 开启直播
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPath.START_LIVE)
    Observable<LiveResultBean> startLive(@Body RequestBody requestBody);

    @POST(AppHttpPath.LIVE_DETAIL)
    Observable<LiveDetailBean> getLiveDetail(@Body RequestBody requestBody);

    /**
     * 上传文件
     *
     * @return
     */
    @POST(AppHttpPath.LIVE_ROOM_COMMODITIES)
    Observable<ShopCommodityListBean> getLiveRoomCommodities(@Body RequestBody requestBody);


    /*====================================================    农发管理端   ==============================================================*/
    @POST(AppHttpPath.SCHOOL_LIST)
    Observable<SchoolListBean> getSchoolList();

    @POST(AppHttpPath.MANAGER_COMMODITY_LIST)
    Observable<CommodityManagerListBean> getManagerCommodityList(@Body RequestBody requestBody);

    @POST(AppHttpPath.MANAGER_COMMODITY_DETAIL)
    Observable<CommodityManagerDetailBean> getManagerCommodityDetail(@Body RequestBody requestBody);

    @POST(AppHttpPath.MANAGER_SHOP_LIST)
    Observable<ShopManagerListBean> getManagerShopList(@Body RequestBody requestBody);

    @POST(AppHttpPath.MANAGER_SHOP_DETAIL)
    Observable<ShopManagerDetailBean> getManagerShopDetail(@Body RequestBody requestBody);

    @POST(AppHttpPath.UPDATE_COMMODITY_STATUS)
    Observable<BaseResult> updateCommodityStatus(@Body RequestBody requestBody);

    @POST(AppHttpPath.COMMIT_SHOP_CHECK)
    Observable<BaseResult> commitShopCheck(@Body RequestBody requestBody);

    @POST(AppHttpPath.UPDATE_SORT_STATUS)
    Observable<BaseResult> updateSortStatus(@Body RequestBody requestBody);

    @POST(AppHttpPath.SORT_ORDER_DETAIL)
    Observable<SortDetailBean> getSortOrderDetail(@Body RequestBody requestBody);
    @POST(AppHttpPath.NF_ORDER_LIST)
    Observable<SellOrderListBean> getNfOrderList(@Body RequestBody requestBody);

    @POST(AppHttpPath.UPDATE_DELIVERY_STATUS)
    Observable<BaseResult> updateDeliveryStatus(@Body RequestBody requestBody);
}
package com.example.appbase.base;

import com.example.appbase.bean.CommodityDesListBean;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.LiveListBean;
import com.example.appbase.bean.LiveTypeListBean;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.bean.ShopListDataBean;
import com.example.appbase.bean.UserBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
import com.example.appbase.bean.multiBean.MultiRadioBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.SchoolListBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.SortDetailBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.bean.shop.ShopDetailBuyBean;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.disabled.basecomponent.utils.RxScheduler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/6/3 8:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:38
 */
public abstract class BaseAppPresent<M extends IModel, V extends IView> extends BasePresenter<M, V> {

    /**
     * @param arrays
     * @param typeName
     */
    public void initRadioType(List<MultipleItem> arrays, String typeName, String[] values, int defaultIndex,
                              boolean isImportant) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean(typeName,
                isImportant)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_RADIO, new MultiRadioBean(typeName, values, defaultIndex)));
    }


    /**
     * initTextType
     *
     * @param arrays
     * @param key
     */
    public void initTextSelectType(List<MultipleItem> arrays, String key, String id, String value,
                                   boolean isImportant, boolean isDetail) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (key, isImportant)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_SELECT,
                new TextKeyValueBean(key, value, id, String.format("%s%s", "请选择",
                        key), 0, isImportant, isDetail)));
    }

    /**
     * initTextType
     *
     * @param arrays
     * @param typeName
     * @param editHeightType 0代表高度固定 1代表不固定
     */
    public void initTextType(List<MultipleItem> arrays, int layoutType, String typeName, String value,
                             boolean isImportant, int editHeightType, boolean isDetail) {
        switch (layoutType) {
            case MultipleItem.ITEM_SELECT:
                arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                        (typeName, isImportant)));
                arrays.add(new MultipleItem(MultipleItem.ITEM_SELECT,
                        new TextKeyValueBean(typeName, value, String.format("%s%s", "请选择",
                                typeName), 0, isImportant, isDetail)));
                break;
            case MultipleItem.ITEM_EDIT:
                arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean(typeName,
                        isImportant)));
                arrays.add(new MultipleItem(MultipleItem.ITEM_EDIT,
                        new TextKeyValueBean(typeName, value,
                                String.format("%s%s", "请输入", typeName), editHeightType, isImportant, isDetail)));

                break;
            case MultipleItem.ITEM_EDIT2:
                arrays.add(new MultipleItem(MultipleItem.ITEM_EDIT2,
                        new TextKeyValueBean(typeName, value,
                                String.format("%s%s", "请输入", typeName), editHeightType, isImportant, isDetail)));
                break;
            default:
                break;
        }

    }





    /*====================================================    商城   ==============================================================*/

    public FormBody.Builder getBaseBuilderWithoutParama() {
        FormBody.Builder builder = new FormBody.Builder();
        return builder;
    }

    public void editCart(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .editCart(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
    public void getCommodityRecommendList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getCommodityRecommendList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDesListBean>(null) {
                    @Override
                    public void onSuccess(CommodityDesListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getCommodityDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getCommodityDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDetailBean>(getView()) {
                    @Override
                    public void onSuccess(CommodityDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    public void creatOrderCart(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .creatOrderCart(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CreatOrderBean>(getView()) {
                    @Override
                    public void onSuccess(CreatOrderBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    public void creatOrderBuy(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .creatOrderBuy(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CreatOrderBean>(getView()) {
                    @Override
                    public void onSuccess(CreatOrderBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void login(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .login(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(getView()) {
                    @Override
                    public void onSuccess(UserBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void regist(String tag, RequestBody body) {
        AppNetModule.createrRetrofit()
                .regist(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void modifyPwd(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .modifyPwd(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    /**
     * 上传文件
     *
     * @param
     * @param body
     */
    private void uploadFiles(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .uploadFiles(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UploadFileBean>(getView()) {
                    @Override
                    public void onSuccess(UploadFileBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.getFilePaths());
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    /**
     * 上传文件
     *
     * @param filePaths
     */
    public void uploadFile(String tag, String... filePaths) {
        if (filePaths.length > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String filePath : filePaths) {
                try {
                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            uploadFiles(builder.build(), tag);
        }

    }

    /**
     * 上传文件
     *
     * @param filePaths
     */
    public void uploadFile(List<String> filePaths, String tag) {
        if (filePaths.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String filePath : filePaths) {
                try {
                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            uploadFiles(builder.build(), tag);
        }

    }

    /**
     * 获取用户信息
     *
     * @param tag
     * @param body
     */
    public void getUserInfo(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .getUserInfo(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBean>(null) {
                    @Override
                    public void onSuccess(UserBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    public void getShopDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getShopDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopDetailBuyBean>(getView()) {
                    @Override
                    public void onSuccess(ShopDetailBuyBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getSellShopDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getSellShopDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopDetailSellBean>(getView()) {
                    @Override
                    public void onSuccess(ShopDetailSellBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void collectShop(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .collectShop(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void collectCommodity(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .collectCommodity(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void startSearchCommodity(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .startSearchCommodity(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDesListBean>(null) {
                    @Override
                    public void onSuccess(CommodityDesListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void startSearchShop(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .startSearchShop(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopListDataBean>(null) {
                    @Override
                    public void onSuccess(ShopListDataBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getLiveList(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .getLiveList(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveListBean>(null) {
                    @Override
                    public void onSuccess(LiveListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getLiveType(String tag) {
        AppNetModule.createrRetrofit()
                .getLiveType()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveTypeListBean>(null) {
                    @Override
                    public void onSuccess(LiveTypeListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }




    /*====================================================    农发管理端   ==============================================================*/

    public void getSchoolList(String tag) {
        AppNetModule.createrRetrofit()
                .getSchoolList()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SchoolListBean>(getView()) {
                    @Override
                    public void onSuccess(SchoolListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getManagerShopDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getManagerShopDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopManagerDetailBean>(getView()) {
                    @Override
                    public void onSuccess(ShopManagerDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void commitShopCheck(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .commitShopCheck(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void updateSortStatus(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .updateSortStatus(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getSortOrderDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getSortOrderDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SortDetailBean>(getView()) {
                    @Override
                    public void onSuccess(SortDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void updateDeliveryStatus(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .updateDeliveryStatus(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void getManagerCommodityDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getManagerCommodityDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityManagerDetailBean>(getView()) {
                    @Override
                    public void onSuccess(CommodityManagerDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }

    public void updateCommodityStatus(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .updateCommodityStatus(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
}

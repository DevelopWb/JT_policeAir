package com.example.appbase.base.multi;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;
import com.example.appbase.base.BaseRecyclerviewActivity;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.example.appbase.base.selectPics.SelectPhotosFragment;
import com.example.appbase.bean.BasePicVideoBean;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.bean.multiBean.BaseAdapterDataBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.LocationBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.example.appbase.bean.multiBean.MultiRadioBean;
import com.example.appbase.util.StringTools;
import com.example.appbase.util.UserInfoManager;
import com.example.appbase.util.bannerImageLoader.BannerObject;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public abstract class BaseMultiRecyclerActivity extends BaseRecyclerviewActivity<MultiPresent> implements IView, SelectPhotosFragment.OnPhotoItemClick {

    private int currentPosition;
    private TextView mSelectTv;
    private TextKeyValueBean selectBean;

    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {

    }

    @Override
    public void initData() {
        super.initData();
        setTitleName(getTitleName());
        initAdapterClick();
    }

    private void initAdapterClick() {

        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                MultipleItem multipleItem = (MultipleItem) adapter.getData().get(position);
                switch (multipleItem.getItemType()) {
                    case MultipleItem.ITEM_PIC:
                        MultiPicBean businessPicBean = (MultiPicBean) multipleItem.getObject();
                        List<BannerObject> bannerObjects = new ArrayList<>();
                        String picPath = businessPicBean.getPicPath();
                        bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, picPath));
                        if (isDetail()) {
                            DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, 0);
                        } else {
                            choseImage(0, BaseMultiRecyclerActivity.this, 1);

                        }
                        break;
                    case MultipleItem.ITEM_SELECT:
                        mSelectTv = (TextView) adapter.getViewByPosition(mRecyclerview, position,
                                R.id.select_value_tv);
                        selectBean = (TextKeyValueBean) multipleItem.getObject();
                        break;
                    default:
                        break;
                }
            }
        });


    }


    /**
     * 获取adapter中的数据
     *
     * @return
     */
    protected BaseAdapterDataBean getBaseOfAdapterData() {
        BaseAdapterDataBean baseAdapterDataBean = new BaseAdapterDataBean();
        CommoditySourceDetailBean.DataBean sourceBean = new CommoditySourceDetailBean.DataBean();
        FormBody.Builder builder = getBaseBuilder();
        builder.add("userAccount", UserInfoManager.getAccount());
        List<MultipleItem> arrays = baseQuickAdapter.getData();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {
                case MultipleItem.ITEM_RADIO:
                    //户口类别 家庭经济状况 项目级别 这几个上传的字段对应的是从1开始的 所以需要从默认的索引+1  相反的 获取到详情展示的时候 就需要-1了
                    MultiRadioBean radioBean = (MultiRadioBean) array.getObject();
                    switch (radioBean.getKey()) {
//                        case HomePageContract.COMMODITY_POST_FREE:
//                            commodityDetailBean.setIsPostage(radioBean.getDefaultSelectedIndex());
//                            break;
                        default:
                            break;
                    }
                    break;
                case MultipleItem.ITEM_HEAD_PIC:
                    MultiPicBean headPicBean = (MultiPicBean) array.getObject();
                    if (TextUtils.isEmpty(headPicBean.getPicPath())) {
                        ToastUtils.toast(mContext, "请选择店铺照片");
                        return null;
                    }
                    builder.add("headPortrait", headPicBean.getPicPath());
                    break;

                case MultipleItem.ITEM_LOCATION:
                    LocationBean locationBean = (LocationBean) array.getObject();
                    builder.add("gpsAddress", locationBean.getAddress());
                    builder.add("longitude", locationBean.getLongitude());
                    builder.add("latitude", locationBean.getLatitude());
                    break;

                case MultipleItem.ITEM_FRAGMENT:
                case MultipleItem.ITEM_FRAGMENT2:
                case MultipleItem.ITEM_FRAGMENT_VIDEO:
                    //多选图片
                    ItemFragmentBean fragmentPicBean = (ItemFragmentBean) array.getObject();
                    List<BasePicVideoBean> photos = fragmentPicBean.getFragmentPics();
                    String name = fragmentPicBean.getKey();
                    String msg = String.format("请选择%s", name);
                    if (photos != null && photos.size() > 0) {
                        BasePicVideoBean path = photos.get(0);
                        switch (name) {
//                            case HomePageContract.COMMODITY_PRIMARY_PIC:
//                                commodityDetailBean.setCoverImg(path);
//
//                                break;
//                            case HomePageContract.COMMODITY_VIDEO:
//                                commodityDetailBean.setVideoUrl(path);
//                                break;
//                            case HomePageContract.COMMODITY_BANNER_PICS:
//                                List<CommodityDetailBean.ImagesBean> imagesBeans = new ArrayList<>();
//
//                                for (String photo : photos) {
//                                    imagesBeans.add(new CommodityDetailBean.ImagesBean(photo));
//                                }
//                                commodityDetailBean.setImages(imagesBeans);
//                                commodityDetailBean.setCommodityImg(imagesBeans);
//
//                                break;
                            default:
                                break;
                        }
                    }


                    break;
                case MultipleItem.ITEM_PIC:
                    MultiPicBean picBean = (MultiPicBean) array.getObject();
                    String picKey = picBean.getPicName();
                    String picPath = picBean.getPicPath();
                    if (picKey != null) {
                        switch (picKey) {
//                            case HomePageContract.SHOP_INNER_PICS:
//                                if (!StringTools.isStringValueOk(picPath)) {
//                                    ToastUtils.toast(mContext, "请选择店铺实景相片");
//                                    return null;
//                                }
//                                builder.add("shopRealScene", picPath);
//                                break;
                        }
                    }
                    break;
                case MultipleItem.ITEM_EDIT:
                    TextKeyValueBean textValueEditBean = (TextKeyValueBean) array
                            .getObject();
                    String textValue = textValueEditBean.getValue();
                    if (textValueEditBean.isImportant() && TextUtils.isEmpty(textValue)) {
                        String key = textValueEditBean.getKey();
                        ToastUtils.toast(mContext, "请输入" + key);
                        return null;
                    }
                    switch (textValueEditBean.getKey()) {
//                        case HomePageContract.SHOP_NAME:
//                            builder.add("name", textValue);
//                            break;
                    }
                    break;

                case MultipleItem.ITEM_SELECT:
                    TextKeyValueBean textValueSelectBean = (TextKeyValueBean) array.getObject();
                    String textSelectValue = textValueSelectBean.getIds();

                    if (textValueSelectBean.isImportant() && !StringTools.isStringValueOk(textSelectValue)) {
                        ToastUtils.toast(mContext, "请选择" + textValueSelectBean.getKey());
                        return null;
                    }
                    switch (textValueSelectBean.getKey()) {
//                        case HomePageContract.SHOP_CATEGORY:
//                            builder.add("category", textSelectValue);
//                            break;
                    }
                    break;

            }
        }
        baseAdapterDataBean.setBuilder(builder);
        baseAdapterDataBean.setSourceBean(sourceBean);
        return baseAdapterDataBean;
    }


    /**
     * 是否是详情
     *
     * @return
     */
    protected abstract boolean isDetail();


    protected abstract String getTitleName();

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        BaseMultiRecyclerAdapter  baseMultiRecyclerAdapter = new BaseMultiRecyclerAdapter(null, isDetail(), getSupportFragmentManager());
        return baseMultiRecyclerAdapter;
    }


    @Override
    protected MultiPresent createPresenter() {
        return new MultiPresent();
    }


    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {
        List<BannerObject> bannerObjects = new ArrayList<>();
        List<String> pics = adapter.getData();
        for (String pic : pics) {
            bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, pic));
        }
        DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, position);
    }
}

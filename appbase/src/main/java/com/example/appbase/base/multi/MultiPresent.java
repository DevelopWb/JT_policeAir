package com.example.appbase.base.multi;

import android.text.TextUtils;

import com.example.appbase.R;
import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.base.multi.adapters.CommodityManagerSourceDetailAdapter;
import com.example.appbase.bean.BasePicVideoBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.MultiNormalRecyclerviewBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerDetailBean;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class MultiPresent extends BaseAppPresent<IModel, IView> {
    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 商品审核
     *
     * @return
     */
    public List<MultipleItem> checkCommodity(CommodityManagerDetailBean.DataBean bean,  boolean showRb) {
        CommodityManagerDetailBean.DataBean.TraceabilityBean traceabilityBean = null;
        if (bean != null) {
            traceabilityBean = bean.getTraceability();

        }
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_NAME, bean == null ? "暂无" : bean.getShopName()
                , true, 0, true);

        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CLASSIFY_NAME, bean == null ? "暂无" : bean.getClassifyName()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CATEGORY_NAME, bean == null ? "暂无" : bean.getCategoryName()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_NAME, bean == null ? "暂无" : bean.getName()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_PRICE, bean == null ? "暂无" : String.valueOf(bean.getPrice())
                , true, 0, true);

        initTextSelectType(arrays, MultiContact.COMMODITY_UNIT, "", bean == null ? "" : bean.getUnit(), true,true);

        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_SEND_LEVEL, bean == null ? "" :
                        String.valueOf(bean.getDelivery())
                , true, 0, true);


        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CREAT_TIME, bean == null ? "暂无" : bean.getCreateTime()
                , true, 0, true);
        if (bean!=null&& !TextUtils.isEmpty(bean.getDescription())) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                    (MultiContact.COMMODITY_DES, true)));
            arrays.add(new MultipleItem(MultipleItem.ITEM_RICH_TEXT, bean.getDescription()));
        }

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.COMMODITY_PIC_VIDEO, false)));

        List<BasePicVideoBean> fragmentPics = new ArrayList<>();
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getCoverImg())) {
                addFragmentPics(bean.getCoverImg(), fragmentPics);
            }
            if (!TextUtils.isEmpty(bean.getVideoUrl())) {
                addFragmentPics(bean.getVideoUrl(), fragmentPics);
            }
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(3, true ?
                fragmentPics.size() : 3,
                2, 1, false,
                fragmentPics)));


        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_PROVIDER, bean == null ? "" : bean.getShopName()
                , true, 0, true);

        initTextSelectType(arrays, MultiContact.COMMODITY_RESTOC_TIME, "0", traceabilityBean == null ? "" : traceabilityBean.getPurchaseTime(), true, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_RESTOC_PERSON, traceabilityBean == null ? "" : traceabilityBean.getPurchaseName()
                , true, 0, true);
        if (traceabilityBean != null) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_NORMAL_RECYCLEVIEW,new MultiNormalRecyclerviewBean(MultiContact.COMMODITY_SOURCE,traceabilityBean,new CommodityManagerSourceDetailAdapter(R.layout.commodity_source_detail_item))));
        }else {
            initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_SOURCE, "暂无"
                    , true, 0, true);
        }
        if (showRb) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                    (MultiContact.IS_AGREE, false)));
        }



        return arrays;
    }

    /**
     * 审核店铺
     *
     * @return
     */
    public List<MultipleItem> checkShop(ShopManagerDetailBean.DataBean bean, boolean showRb) {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.REAL_NAME, bean == null ? "" :
                        bean.getRealName()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.IDCARD, bean == null ? "" :
                        bean.getIdCode()
                , true, 0, true);
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_BIG, "店家资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_FRONT, 1, bean == null ? "" :
                        bean.getBusinessLicense())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_BACK, 2, bean == null ? "" :
                        bean.getIdPositive())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_HAND, 3, bean == null ? "" :
                        bean.getIdSide())));


        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.USER_ACCOUNT, bean == null ? "" :
                        bean.getUserAccount()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_NAME, bean == null ? "" :
                        bean.getName()
                , true, 0, true);

        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_TEL, bean == null ? "" :
                        bean.getPhoneNumber()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_CATEGORY, bean == null ? "" :
                        bean.getCategory()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_ADDR, bean == null ? "" :
                        bean.getGpsAddress()
                , true, 0, true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_CREAT_TIME, bean == null ? "" :
                        bean.getCreateTime()
                , true, 0, true);


        initTextSelectType(arrays, MultiContact.SHOP_ORDER_START_TIME, "", bean == null ? "" : bean.getStartTime(), true,true);
        initTextSelectType(arrays, MultiContact.SHOP_ORDER_END_TIME, "", bean == null ? "" : bean.getEndTime(), true,true);



        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_BIG, "店铺资料"));

        List<BasePicVideoBean> fragmentPics = new ArrayList<>();
        if (bean != null) {
            addFragmentPics(bean.getHeadPortrait(), fragmentPics);
            addFragmentPics(bean.getBusinessLicense(), fragmentPics);
            addFragmentPics(bean.getShopRealScene(), fragmentPics);
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(3, true ?
                fragmentPics.size() : 3,
                3, 1, true,
                fragmentPics)));
        if (showRb) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                    (MultiContact.IS_AGREE, false)));
        }

        return arrays;
    }

    private void addFragmentPics(String picPath, List<BasePicVideoBean> fragmentPics) {
        fragmentPics.add(new BasePicVideoBean(BasePicVideoBean.TYPE_IMAGE,picPath));
//        if (!TextUtils.isEmpty(picPath)) {
//            if (picPath.contains(BaseInspectionActivity.SDCARD_TAG)) {
//                fragmentPics.add(picPath);
//            } else {
//                if (picPath.contains(AppHttpPath.BASE_IMAGE_THUM)) {
//                    fragmentPics.add(picPath);
//                } else {
//                    fragmentPics.add(UrlFormatUtil.getImageThumUrl(picPath));
//                }
//
//            }
//
//        }
    }

}

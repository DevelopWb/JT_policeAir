package com.juntai.wisdom.dgjxb.home_page.search;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.AppHttpPath;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MultipleItem;
import com.juntai.wisdom.dgjxb.bean.SearchBean;
import com.juntai.wisdom.dgjxb.bean.SearchMoreBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/15 10:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/15 10:10
 */
public class SearchAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SearchAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_TITLE, R.layout.item_title);
        addItemType(MultipleItem.ITEM_CONTENT, R.layout.all_info_item);
        addItemType(MultipleItem.ITEM_LOAD_MORE, R.layout.item_content_load_more);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.ITEM_TITLE:
                SearchBean.DataBean dataBean = (SearchBean.DataBean) item.getObject();
                helper.setText(R.id.item_title_name_tv, dataBean.getTypeName());

                break;
            case MultipleItem.ITEM_CONTENT:
                SearchBean.DataBean.ListBean listBean = (SearchBean.DataBean.ListBean) item.getObject();
                helper.setText(R.id.all_info_item_title_tv, listBean.getName());
                helper.setText(R.id.all_info_item_des_tv, listBean.getContent());
                int type = listBean.getTypeId();
                if (0 == type) {
                    //监控
                    ImageLoadUtil.loadImage(mContext.getApplicationContext(), listBean.getPicture(),
                            R.drawable.nopicture, R.drawable.nopicture, helper.getView(R.id.all_info_item_iv));
                }else if (5 == type) {
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), listBean.getPicture(),
                            helper.getView(R.id.all_info_item_iv));
                } else if (6 == type) {//单位
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), listBean.getPicture(),
                            helper.getView(R.id.all_info_item_iv));
                } else if (7 == type) {
                    //巡检点
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), listBean.getPicture(),
                            helper.getView(R.id.all_info_item_iv));
                } else {
                    ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                            listBean.getPicture(), helper.getView(R.id.all_info_item_iv));
                }
                break;
            case MultipleItem.ITEM_LOAD_MORE:
                SearchMoreBean searchMoreBean = (SearchMoreBean) item.getObject();
                String msg = searchMoreBean.getMsg();
                if (!mContext.getString(R.string.load_more_no_data).equals(msg)) {
                    helper.setGone(R.id.item_load_more_tv, true);
                } else {
                    helper.setGone(R.id.item_load_more_tv, false);
                }
                helper.setText(R.id.item_load_more_tv, searchMoreBean.getMsg());
                break;
            default:
                break;
        }
    }
}

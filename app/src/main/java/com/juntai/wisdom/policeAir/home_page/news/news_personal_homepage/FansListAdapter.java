package com.juntai.wisdom.policeAir.home_page.news.news_personal_homepage;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.news.NewsFansListBean;

import java.util.List;

/**
 * Describe:粉丝列表适配器
 * Create by zhangzhenlong
 * 2020-8-18
 * email:954101549@qq.com
 */
public class FansListAdapter extends BaseQuickAdapter<NewsFansListBean.DataBean.DatasBean, BaseViewHolder> {
    public FansListAdapter(int layoutResId, List<NewsFansListBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsFansListBean.DataBean.DatasBean item) {
        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), item.getHeadPortrait(),
                R.mipmap.my_hint_head,R.mipmap.my_hint_head, helper.getView(R.id.info_user_image));
        helper.setText(R.id.info_user_name, item.getNickName()).setText(R.id.info_time_read, "粉丝数："+item.getFansCount());
        if (item.getId() == MyApp.getUid()){
            helper.setVisible(R.id.info_guanzhu_btn, false);
        }else {
            helper.setVisible(R.id.info_guanzhu_btn, true);
            if (item.getIsFollow() > 0){
                helper.setText(R.id.info_guanzhu_btn, "已关注");
                helper.setTextColor(R.id.info_guanzhu_btn, mContext.getResources().getColor(R.color.text_gray));
                helper.setBackgroundRes(R.id.info_guanzhu_btn, R.drawable.news_btn_bg_circle_line);
            }else {
                helper.setText(R.id.info_guanzhu_btn, "关注");
                helper.setTextColor(R.id.info_guanzhu_btn, mContext.getResources().getColor(R.color.white));
                helper.setBackgroundRes(R.id.info_guanzhu_btn, R.drawable.news_btn_bg_blue);
            }
        }
        helper.addOnClickListener(R.id.info_guanzhu_btn);
    }
}
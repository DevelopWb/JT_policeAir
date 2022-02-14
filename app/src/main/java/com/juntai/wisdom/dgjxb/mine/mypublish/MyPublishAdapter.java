package com.juntai.wisdom.dgjxb.mine.mypublish;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.PublishListBean;
import com.juntai.wisdom.dgjxb.utils.UrlFormatUtil;

import java.util.List;

/**
 * Describe:我的发布
 * Create by zhangzhenlong
 * 2020-3-12
 * email:954101549@qq.com
 */
public class MyPublishAdapter extends BaseQuickAdapter<PublishListBean.DataBean.PublishBean, BaseViewHolder> {
    boolean isEdit = false;
    //第一种方式：把check标记放在实体类里，最后的时候遍历循环
    //第二种方式：创建一个列表集合，每次操作
    int type;//1案件，7巡检，8资讯，11 场所
    public MyPublishAdapter(int layoutResId, List data, int t) {
        super(layoutResId, data);
        type = t;
    }

    @Override
    protected void convert(BaseViewHolder helper, PublishListBean.DataBean.PublishBean item) {
        if (isEdit){
        //编辑状态
            helper.setChecked(R.id.item_collect_check,item.isChecked());
            helper.getView(R.id.item_collect_check).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_collect_check).setOnClickListener(v -> {
                item.setChecked(((CheckBox)v).isChecked());
            });
        }else {
            helper.getView(R.id.item_collect_check).setVisibility(View.GONE);
            item.setChecked(false);
        }

        helper.setVisible(R.id.item_iv,true);
        if (type == 8){
            helper.setVisible(R.id.item_more_iv,true);
        } else {
            helper.setVisible(R.id.item_more_iv,false);
        }
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getPicture(),
                R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
        helper.setText(R.id.item_name,item.getTitle());
        if (type == 1){
            helper.setText(R.id.item_content,item.getTime());
        }else if (type == 7 || type == 11){
            helper.setText(R.id.item_content,item.getTime());
            helper.setVisible(R.id.item_status,true);
            setStatus(helper.getView(R.id.item_status),item.getState());
        }else {
            helper.setText(R.id.item_content,item.getTime());
        }

        helper.addOnClickListener(R.id.item_more_iv);
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setStatus(TextView textView, int status){
        if (type == 11){
            switch (status){
                case 1:
                    textView.setTextColor(mContext.getResources().getColor(R.color.success_color));
                    textView.setText("审核通过");
                    break;
                case 2:
                    textView.setTextColor(mContext.getResources().getColor(R.color.fail_color));
                    textView.setText("审核拒绝");
                    break;
                default:
                    textView.setTextColor(mContext.getResources().getColor(R.color.orange));
                    textView.setText("审核中");
                    break;
            }
        }else {
            switch (status){
                case 3:
                    textView.setTextColor(mContext.getResources().getColor(R.color.success_color));
                    textView.setText("审核通过");
                    break;
                case 4:
                    textView.setTextColor(mContext.getResources().getColor(R.color.fail_color));
                    textView.setText("审核拒绝");
                    break;
                default:
                    textView.setTextColor(mContext.getResources().getColor(R.color.orange));
                    textView.setText("审核中");
                    break;
            }
        }
    }
}

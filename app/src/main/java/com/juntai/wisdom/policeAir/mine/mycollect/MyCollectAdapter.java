package com.juntai.wisdom.policeAir.mine.mycollect;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.CollectListBean;

import java.util.List;

/**
 * 收藏、分享
 * @aouther ZhangZhenlong
 * @date 2020-3-12
 */
public class MyCollectAdapter extends BaseQuickAdapter<CollectListBean.DataBean.CollectBean, BaseViewHolder> {
    boolean isEdit = false;
    //第一种方式：把check标记放在实体类里，最后的时候遍历循环
    //第二种方式：创建一个列表集合，每次操作
    int type;//1收藏、2分享
    public MyCollectAdapter(int layoutResId, List data, int t) {
        super(layoutResId, data);
        type = t;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectListBean.DataBean.CollectBean item) {

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
        //功能 8：资讯,0:监控
        if (0==item.getType()) {
            helper.setVisible(R.id.item_iv,true);
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getUrl(),
                    R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
            helper.setText(R.id.item_name,item.getName());
            helper.setText(R.id.item_content,item.getAddress());
        }else if (8 == item.getType()){
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getCoverUrl(),
                    R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
            helper.setText(R.id.item_name,item.getTitle());
            helper.setText(R.id.item_content,item.getGmtCreate());
        }
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public boolean getEdit(){
        return isEdit;
    }
}
package com.juntai.wisdom.policeAir.mine.message;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.message.LikeMsgListBean;

import java.util.List;

/**
 * Describe:评论消息
 * Create by zhangzhenlong
 * 2020-3-25
 * email:954101549@qq.com
 */
public class LikeMsgAdapter extends BaseQuickAdapter<LikeMsgListBean.DataBean.MessageBean, BaseViewHolder> {

    public LikeMsgAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeMsgListBean.DataBean.MessageBean item) {
        if (item.getIsRead() == 0){//0已读,1未读
            helper.setVisible(R.id.read_tag,false);
        }else {//未读
            helper.setVisible(R.id.read_tag,true);
        }
        ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(), item.getHeadPortrait(),
                R.mipmap.my_hint_head,R.mipmap.my_hint_head,helper.getView(R.id.item_image));

        helper.setText(R.id.item_name,item.getTitle());
        helper.setText(R.id.item_content,item.getContent());
        helper.setText(R.id.item_date,item.getGmtCreate());
    }
}

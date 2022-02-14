package com.juntai.wisdom.dgjxb.home_page.news.news_comment;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.CommentBean;
import com.juntai.wisdom.dgjxb.utils.StringTools;

import java.util.List;

/**
 * 评论
 * @aouther Ma
 * @date 2019/7/19
 */
public class CommentAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {
    public CommentAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {
        helper.addOnClickListener(R.id.item_comment_huifu);//回复
        helper.addOnClickListener(R.id.item_comment_like);//点赞
        helper.addOnClickListener(R.id.item_comment_user_more);//更多
        helper.addOnClickListener(R.id.item_comment_iv);//图片大图查看
        helper.addOnClickListener(R.id.child1_iv);
        helper.addOnClickListener(R.id.child2_iv);

        helper.addOnClickListener(R.id.item_comment_user_image);
        helper.addOnClickListener(R.id.child1_name);
        helper.addOnClickListener(R.id.child2_name);

        helper.setText(R.id.item_comment_user_name,item.getNickname());
        helper.setText(R.id.item_comment_content,item.getContent());
        helper.setText(R.id.item_comment_time,item.getGmtCreate());
        if (item.getLikeCount() > 0){
            helper.setText(R.id.item_comment_like_cnt,item.getLikeCount() + "");
        }else {
            helper.setText(R.id.item_comment_like_cnt,"赞");
        }
        if (item.getIsLike() > 0){
            helper.getView(R.id.item_comment_like).setSelected(true);
        }else {
            helper.getView(R.id.item_comment_like).setSelected(false);
        }

        ImageLoadUtil.loadCircularImage(mContext, item.getHeadPortrait(),
                R.mipmap.my_hint_head,R.mipmap.my_hint_head,helper.getView(R.id.item_comment_user_image));

        if (StringTools.isStringValueOk(item.getCommentUrl())){
            ImageLoadUtil.loadImage(mContext, item.getCommentUrl(), helper.getView(R.id.item_comment_iv));
            helper.setVisible(R.id.item_comment_iv, true);
        }else {
            helper.setGone(R.id.item_comment_iv, false);
        }

        //子回复，，，文章有
        if (item.getCommentChildList().size() <= 2){
            helper.getView(R.id.item_comment_user_more).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.item_comment_user_more).setVisibility(View.VISIBLE);
            helper.setText(R.id.item_comment_user_more,"更多回复>");
        }
        //子回复
        if (item.getCommentChildList().size() == 0){
            helper.getView(R.id.item_comment_child1).setVisibility(View.GONE);
            helper.getView(R.id.item_comment_child2).setVisibility(View.GONE);
        }
        //第一条子回复
        if (item.getCommentChildList().size() > 0){
            CommentBean child1 = item.getCommentChildList().get(0);
            helper.getView(R.id.item_comment_child1).setVisibility(View.VISIBLE);
//            SpannableString textSpanned1 = new SpannableString(item.getCommentChildList().get(0).getNickname()
//                    + "：" + item.getCommentChildList().get(0).getContent());
//            textSpanned1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorAccent)),
//                    0, item.getCommentChildList().get(0).getNickname().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            helper.setText(R.id.item_comment_child1,textSpanned1);
            helper.setText(R.id.child1_name, child1.getNickname());
            helper.setText(R.id.child1_content, "：" + child1.getContent());
            if (StringTools.isStringValueOk(child1.getCommentUrl())){
                ImageLoadUtil.loadImage(mContext, child1.getCommentUrl(), helper.getView(R.id.child1_iv));
                helper.setVisible(R.id.child1_iv, true);
            }else {
                helper.setGone(R.id.child1_iv, false);
            }
        }
        //第2条子回复
        if (item.getCommentChildList().size() > 1){
            CommentBean child2 = item.getCommentChildList().get(1);
            helper.getView(R.id.item_comment_child2).setVisibility(View.VISIBLE);
            helper.setText(R.id.child2_name, child2.getNickname());
            helper.setText(R.id.child2_content, "：" + child2.getContent());
            if (StringTools.isStringValueOk(child2.getCommentUrl())){
                ImageLoadUtil.loadImage(mContext, child2.getCommentUrl(), helper.getView(R.id.child2_iv));
                helper.setVisible(R.id.child2_iv, true);
            }else {
                helper.setGone(R.id.child2_iv, false);
            }
        }
    }
}
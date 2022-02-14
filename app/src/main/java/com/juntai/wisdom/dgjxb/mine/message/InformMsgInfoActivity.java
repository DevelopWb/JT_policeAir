package com.juntai.wisdom.dgjxb.mine.message;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.message.InformDetailBean;

/**
 * 通知消息详情
 * @aouther ZhangZhenlong
 * @date 2020-3-27
 */
public class InformMsgInfoActivity extends BaseMvpActivity<MessagePresent> implements IMessageContract.IMessageView {
    /**
     * 标题
     */
    private TextView mTitleTv;
    /**
     * 标题
     */
    private TextView mNameTv;
    /**
     * 02月18日
     */
    private TextView mDateTv;
    /**
     * 144
     */
    private TextView mCountTv;
    /**
     * 144
     */
    private TextView mContentTv;
    private int id;

    @Override
    public int getLayoutView() {
        return R.layout.activity_inform_msg_info;
    }

    @Override
    public void initView() {
        setTitleName("");
        id = getIntent().getIntExtra("id",0);
        mTitleTv = findViewById(R.id.title_tv);
        mNameTv = findViewById(R.id.name_tv);
        mDateTv = findViewById(R.id.date_tv);
        mCountTv = findViewById(R.id.count_tv);
        mContentTv = findViewById(R.id.content_tv);
//        initViewRightDrawable(mCountTv,R.mipmap.browse_icon);
    }

    @Override
    public void initData() {
        mPresenter.getInformMsgDetail(id,IMessageContract.GET_INFORMATION_DETAIL);
    }

    /**
     * 设置图标
     *
     * @param textView
     * @param drawableId
     */
    private void initViewRightDrawable(TextView textView, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, 10), DisplayUtil.dp2px(this, 10));//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        textView.setCompoundDrawables(drawable,null, null, null);//只放右边
    }

    @Override
    protected MessagePresent createPresenter() {
        return new MessagePresent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        if (tag.equals(IMessageContract.GET_INFORMATION_DETAIL)){
            InformDetailBean informDetailBean = (InformDetailBean) o;
            mTitleTv.setText(informDetailBean.getData().getTitle());
            mNameTv.setText(informDetailBean.getData().getPublisher());
            mDateTv.setText(informDetailBean.getData().getGmtCreate());
            mCountTv.setText(informDetailBean.getData().getViewNumber()+"");
            mContentTv.setText(informDetailBean.getData().getContent());
        }
    }
}

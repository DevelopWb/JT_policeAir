package com.juntai.wisdom.dgjxb.mine.message;

import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.im.MsgFragment;

/**
 * 互动消息
 * Created by Ma
 * on 2019/5/7
 */
public class MyMsgFragment extends BaseMvpFragment<MessagePresent> implements IMessageContract.IMessageView {
    TextView null_view,titleTv;
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_msg;
    }

    @Override
    protected void initView() {
//        null_view = getView(R.id.status_top_null_view);
//        titleTv = getView(R.id.status_top_title);
//        null_view.getLayoutParams().height = MyApp.statusBarH;
//        titleTv.setText("消息");
        //my_msg
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.my_msg,new MsgFragment())
                .commit();
    }

    @Override
    protected void initData() {

    }

    /**
     * 全部已读
     */
    public void allRead(){

    }

    @Override
    protected MessagePresent createPresenter() {
        return new MessagePresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onError(String tag, Object o) {

    }
}

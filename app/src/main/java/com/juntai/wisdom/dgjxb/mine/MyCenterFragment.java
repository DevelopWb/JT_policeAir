package com.juntai.wisdom.dgjxb.mine;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DialogUtil;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.SPTools;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.MyMenuBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.message.UnReadCountBean;
import com.juntai.wisdom.dgjxb.home_page.news.exoplayer_tiktok.TikTokActivity;
import com.juntai.wisdom.dgjxb.mine.mycenter.MyMenuAdapter;
import com.juntai.wisdom.dgjxb.mine.myinfo.MyInformationActivity;
import com.juntai.wisdom.dgjxb.utils.AppUtils;
import com.juntai.wisdom.dgjxb.utils.GridDividerItemDecoration;
import com.juntai.wisdom.im.IUnReadMessageLinstener;
import com.juntai.wisdom.im.ModuleIm_Init;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 个人中心
 * @aouther ZhangZhenlong
 * @date 2020/3/9
 */
public class MyCenterFragment extends BaseMvpFragment<MyCenterPresent> implements MyCenterContract.ICenterView, View.OnClickListener {

    private UserBean userBean;
    MyMenuAdapter myMenuAdapter;
    private String headUrl = "";

    private TextView mStatusTopTitle;
    private ImageView mHeadImage;
    private TextView mNickname;
    /**
     * 18763739973
     */
    private TextView mTelNumber;
    private RecyclerView mMenuRecycler;
    /**
     * 退出账号
     */
    private TextView mLoginOut;
    private int imUnReadCount;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_center;
    }

    @Override
    protected void initView() {
        mStatusTopTitle = getView(R.id.status_top_title);
        mHeadImage = getView(R.id.headImage);
        mHeadImage.setOnClickListener(this);
        mNickname = getView(R.id.nickname);
        mTelNumber = getView(R.id.tel_number);
        mMenuRecycler = getView(R.id.menu_recycler);
        mLoginOut = getView(R.id.login_out);
        mLoginOut.setOnClickListener(this);
        myMenuAdapter = new MyMenuAdapter(R.layout.item_my_center_menu, mPresenter.getMenuBeans());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
        myMenuAdapter.setGridLayoutManager(gridLayoutManager);
        mMenuRecycler.setLayoutManager(gridLayoutManager);
        mMenuRecycler.addItemDecoration(new GridDividerItemDecoration(mContext));
        mMenuRecycler.setAdapter(myMenuAdapter);
        mStatusTopTitle.setText("个人中心");
//        headUrl = MyApp.getUserHeadImg();

        myMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (myMenuAdapter.getData().get(position).getCls() != null) {
                    if (!MyApp.isLogin()){
                        MyApp.goLogin();
                        return ;
                    }
                    switch (myMenuAdapter.getData().get(position).getTag()) {
                        case MyCenterContract.CENTER_SHOUCANG_TAG:
                            startActivity(new Intent(mContext, myMenuAdapter.getData().get(position).getCls())
                                    .putExtra("function", 1));
                            break;
                        case MyCenterContract.CENTER_SHARE_TAG:
                            startActivity(new Intent(mContext, myMenuAdapter.getData().get(position).getCls())
                                    .putExtra("function", 2));
                            break;
                        default:
                            startActivity(new Intent(mContext, myMenuAdapter.getData().get(position).getCls()));
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.initList();
        mHeadImage.setImageResource(R.mipmap.default_user_head_icon);
        /**设置im未读消息监听*/
        ModuleIm_Init.setUnReadMessageListener(new IUnReadMessageLinstener() {
            @Override
            public void onCountChanged(int count) {
                imUnReadCount = count;
                UnReadCountBean.DataBean unReadCountBean = MyApp.getUnReadCountBean();
                if (unReadCountBean != null){
                    unReadCountBean.setImCount(imUnReadCount);
                    MyApp.setUnReadCountBean(unReadCountBean);
                    List<MyMenuBean> menuBeans = myMenuAdapter.getData();
                    for (int i = 0; i < menuBeans.size(); i++) {
                        MyMenuBean bean = menuBeans.get(i);
                        if (MyCenterContract.CENTER_MESSAGE_TAG.equals(bean.getTag())) {
                            bean.setNumber(unReadCountBean.getMessageCount()+imUnReadCount);
                            myMenuAdapter.notifyItemChanged(i);
//                            app角标
                             ShortcutBadger.applyCount(mContext.getApplicationContext(), unReadCountBean.getMessageCount()+imUnReadCount);
                            break;
                        }
                    }
                    LogUtil.e("refresh-->");
                    EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.isLogin()){
            mLoginOut.setVisibility(View.VISIBLE);
            mPresenter.getUserData(MyCenterContract.USER_DATA_TAG);
            mPresenter.getUnReadCount(MyCenterContract.GET_UNREAD_COUNT);
        }else {
            mLoginOut.setVisibility(View.GONE);
        }
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    protected MyCenterPresent createPresenter() {
        return new MyCenterPresent();
    }


    @Override
    public void onClick(View v) {
        if (!MyApp.isLogin()){
            MyApp.goLogin();
            return;
        }
        switch (v.getId()) {
            case R.id.headImage:
                //用户信息设置
                if (userBean != null){
                    Intent intent = new Intent(mContext, MyInformationActivity.class);
                    intent.putExtra("user", userBean.getData());
                    intent.putExtra("headUrl", headUrl);
                    startActivity(intent);
                }else {
                    mPresenter.getUserData(MyCenterContract.USER_DATA_TAG);
                }
//                startActivity(new Intent(mContext, TikTokActivity.class));
                break;
            case R.id.login_out:
                //退出登录
                DialogUtil.getMessageDialog(mContext, "是否退出登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.loginOut(MyCenterContract.LOGIN_OUT_TAG);
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MyCenterContract.USER_DATA_TAG:
                userBean = (UserBean) o;
                UserBean.DataBean dataBean = userBean.getData();
                if (dataBean != null){
                    mLoginOut.setVisibility(View.VISIBLE);
                    mNickname.setText(dataBean.getNickname());
                    mNickname.setAlpha(0.8f);
                    mTelNumber.setText(MyApp.getAccount());
                    mTelNumber.setVisibility(View.VISIBLE);
                    if (!headUrl.equals(userBean.getData().getHeadPortrait())) {
                        headUrl = userBean.getData().getHeadPortrait();
                        ImageLoadUtil.loadCirImgNoCrash(mContext.getApplicationContext(), headUrl, mHeadImage, R.mipmap.default_user_head_icon, R.mipmap.default_user_head_icon);
                    }
                    Hawk.put(AppUtils.SP_KEY_USER,userBean);
                }
                break;
            case MyCenterContract.GET_UNREAD_COUNT:
                UnReadCountBean countBean = (UnReadCountBean)o;
                if (countBean.getData() != null){
                    UnReadCountBean.DataBean unReadCountBean = countBean.getData();
                    unReadCountBean.setImCount(imUnReadCount);
                    MyApp.setUnReadCountBean(unReadCountBean);
                    List<MyMenuBean> myMenuBeans = myMenuAdapter.getData();
                    for (MyMenuBean myMenuBean : myMenuBeans) {
                        switch (myMenuBean.getTag()) {
                            case MyCenterContract.CENTER_MISSION_TAG:
                                myMenuBean.setNumber(unReadCountBean.getMissionCount());
                                break;
                            case MyCenterContract.CENTER_MESSAGE_TAG:
                                myMenuBean.setNumber(unReadCountBean.getMessageCount()+ imUnReadCount);
                                break;
                            default:
                                break;
                        }
                    }
                    ShortcutBadger.applyCount(mContext.getApplicationContext(), unReadCountBean.getMessageCount() + imUnReadCount);
                    myMenuAdapter.notifyDataSetChanged();
                    EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
                }
                break;
            case MyCenterContract.LOGIN_OUT_TAG:
                ToastUtils.success(mContext, "退出成功");
                ModuleIm_Init.logout();
                SPTools.saveString(mContext, "login", "");
                MyApp.app.clearUserData();//清理数据
                ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
                //重置界面
                EventManager.sendStringMsg(ActionConfig.BROAD_LOGIN_OUT);
                EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
                mNickname.setText("点击头像登录");
                mNickname.setAlpha(0.3f);
                mTelNumber.setVisibility(View.GONE);
                mLoginOut.setVisibility(View.GONE);
                mPresenter.initList();
                headUrl = "";
                mHeadImage.setImageResource(R.mipmap.default_user_head_icon);
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, String.valueOf(o));
    }

    @Override
    public void refreshAdapter() {
        myMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        ModuleIm_Init.setUnReadMessageListener(null);
        super.onDestroyView();
    }
}

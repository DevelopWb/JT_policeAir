package com.juntai.wisdom.policeAir.mine.myinfo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.TextListBean;
import com.juntai.wisdom.policeAir.bean.UserBean;
import com.juntai.wisdom.policeAir.mine.MyCenterContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的信息
 *
 * @aouther ZhangZhenlong
 * @date 2020/3/10
 */
public class MyInformationActivity extends BaseMvpActivity<MyInfoPresent> implements MyCenterContract.IMyInfoView {

        public static final int REQUEST_CODE_CHOOSE = 0x3;
        public static final int START_CROP_IMAGE_REQUEST = 0x4;
        MyInfoAdapter myInfoAdapter;
        List<TextListBean> beanList = new ArrayList<>();
        ImageView imageView;
        TextView nicknameTv;
        private LinearLayout headLayout;
        //裁切前压缩图片
        File fileCrash;
        private UserBean.DataBean userBean;
        private String headUrl;

        private RecyclerView mRecyclerview;
        private SmartRefreshLayout mSmartrefreshlayout;

        @Override
        public int getLayoutView() {
            return R.layout.recycleview_layout;
        }

        @Override
        public void initView() {
            setTitleName("我的信息");
            userBean = (UserBean.DataBean) getIntent().getSerializableExtra("user");
            headUrl = getIntent().getStringExtra("headUrl");
            mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
            mSmartrefreshlayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);

            mSmartrefreshlayout.setEnableLoadMore(false);
            mSmartrefreshlayout.setEnableRefresh(false);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
            myInfoAdapter = new MyInfoAdapter(R.layout.item_myinfo, beanList);
            mRecyclerview.setAdapter(myInfoAdapter);
            //
            setHeadView();
        }

        /**
         * 添加头部
         */
        public void setHeadView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.include_myinfo_head, null);
            myInfoAdapter.setHeaderView(view);
            headLayout = view.findViewById(R.id.myinfo_headLayout);
            imageView = view.findViewById(R.id.myinfo_headimage);
            nicknameTv = view.findViewById(R.id.myinfo_nickname);
            headLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2022-02-14 更改头像的逻辑关闭
//                    mPresenter.imageChoose();
                }
            });
        }

        @Override
        public void initData() {
            if (userBean != null && userBean.getUserId() != 0){
                beanList.add(new TextListBean("账号", userBean.getAccount()));
                beanList.add(new TextListBean("昵称", userBean.getName()));
//                beanList.add(new TextListBean("手机号码", userBean.getPhoneNumber()));
//                beanList.add(new TextListBean("真实姓名", userBean.getRealName() == ""? "未实名" : userBean.getRealName()));
//                beanList.add(new TextListBean("身份证号", userBean.getIdnumber()));
//                beanList.add(new TextListBean("积分", userBean.getScore() +""));
//                beanList.add(new TextListBean("QQ昵称", userBean.getQqName() == ""? "未绑定" : userBean.getQqName()));
//                beanList.add(new TextListBean("微信昵称", userBean.getWeChatName() == ""? "未绑定" : userBean.getWeChatName()));

                if (userBean.getSettleStatus() == 2){
                    beanList.add(new TextListBean("部门", userBean.getDepartmentName()));
                    beanList.add(new TextListBean("二级部门", userBean.getDepartmentBranchName()));
                    beanList.add(new TextListBean("职务", userBean.getPostName()));
                    beanList.add(new TextListBean("网格", userBean.getGridName()));
                }

                nicknameTv.setText(userBean.getName());
                ImageLoadUtil.loadCirImgNoCrash(getApplicationContext(), headUrl, imageView, R.mipmap.default_user_head_icon,R.mipmap.default_user_head_icon);
            }else {
                mPresenter.getUserData(MyCenterContract.USER_DATA_TAG);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
    //            List<Uri> result = Matisse.obtainResult(data);
                String path = Matisse.obtainPathResult(data).get(0);
                mPresenter.yasuo(path);
            } else if (requestCode == START_CROP_IMAGE_REQUEST && resultCode == RESULT_OK) {
                String path = data.getStringExtra("path");
                mPresenter.postHead(new File(path));
                if (fileCrash != null) {
                    fileCrash = null;
                }
            }
        }


        @Override
        protected MyInfoPresent createPresenter() {
            return new MyInfoPresent();
        }

        @Override
        public void onSuccess(String tag, Object o) {
            switch (tag) {
                case MyCenterContract.YASUO_HEAD_TAG://压缩成功
                    fileCrash = (File) o;
                    Intent intent = new Intent(mContext, ImageCropActivity.class);
                    intent.putExtra("path", fileCrash.getPath());
                    startActivityForResult(intent, START_CROP_IMAGE_REQUEST);
                    break;
                case MyCenterContract.UPDATE_HEAD_TAG://上传成功
                    BaseResult baseResult = (BaseResult) o;
                    ToastUtils.success(mContext, "修改成功");
                    headUrl = baseResult.message;
                    ImageLoadUtil.loadCircularImage(getApplicationContext(), headUrl,R.mipmap.default_user_head_icon,R.mipmap.default_user_head_icon, imageView);
//                    MyApp.addImUserInfo(MyApp.getUser().getData().getAccount(), MyApp.getUser().getData().getName(),headUrl);
                    break;
                case MyCenterContract.USER_DATA_TAG:
                    UserBean user = (UserBean) o;
                    userBean = user.getData();
                    headUrl = userBean.getImg();
                    initData();
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
        protected void onDestroy() {
            super.onDestroy();
        }
}

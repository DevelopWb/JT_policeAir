package com.juntai.wisdom.dgjxb.mine.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.AppHttpPath;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.base.customview.DeleteMsgDialog;
import com.juntai.wisdom.dgjxb.bean.message.LikeMsgListBean;
import com.juntai.wisdom.dgjxb.bean.UserBean;
import com.juntai.wisdom.dgjxb.bean.message.UnReadCountBean;
import com.juntai.wisdom.dgjxb.home_page.conciliation.conciliation_common.ConciliatonInfoActivity;
import com.juntai.wisdom.dgjxb.home_page.law_case.CaseInfoActivity;
import com.juntai.wisdom.dgjxb.home_page.inspection.InspectionDetailActivity;
import com.juntai.wisdom.dgjxb.home_page.news.news_comment.CommentChildActivity;
import com.juntai.wisdom.dgjxb.mine.task.MyTaskListActivity;
import com.juntai.wisdom.dgjxb.mine.task.ReportDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知消息
 * @aouther ZhangZhenlong
 * @date 2020-3-13
 */
public class InformMsgFragment extends BaseMvpFragment<MessagePresent> implements IMessageContract.IMessageView {
    private RecyclerView mRecyclerview;
    private SmartRefreshLayout mSmartrefreshlayout;

    List<LikeMsgListBean.DataBean.MessageBean> likeMsgs = new ArrayList<>();
    LikeMsgAdapter likeMsgAdapter;
    int type = 1;//功能，1通知消息，4评论点赞
    int page = 1, pagesize = 20;
    private int deletePosition;//删除标记

    public static InformMsgFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type",type);
        InformMsgFragment fragment = new InformMsgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    protected MessagePresent createPresenter() {
        return new MessagePresent();
    }

    @Override
    protected void lazyLoad() {
        page = 1;
        getData(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.normal_recycleview_layout;
    }

    @Override
    protected void initView() {
        mRecyclerview = getView(R.id.recyclerview);
        mRecyclerview.setBackgroundColor(getResources().getColor(R.color.transparent));
        mSmartrefreshlayout = getView(R.id.smartrefreshlayout);

        mSmartrefreshlayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData(false);
        });
        mSmartrefreshlayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getData(false);
        });

        likeMsgAdapter = new LikeMsgAdapter(R.layout.item_my_message,likeMsgs);
        likeMsgAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView(getString(R.string.none_message),R.mipmap.none_message));
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerview.setAdapter(likeMsgAdapter);

        likeMsgAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (type == 1){
                LikeMsgListBean.DataBean.MessageBean messageBean = likeMsgAdapter.getData().get(position);
                switch (messageBean.getContentType()) {
                    case AppHttpPath.NOTICE_TYPE_CASE:
                        //案件
                        startActivity(new Intent(mContext, CaseInfoActivity.class).putExtra("id", messageBean.getContentId()));
                        break;
                    case AppHttpPath.NOTICE_TYPE_INSPECTION:
                        //巡检
                        startActivity(new Intent(mContext, InspectionDetailActivity.class).putExtra("id", messageBean.getContentId()));
                        break;
                    case AppHttpPath.NOTICE_TYPE_TASK:
                        //新任务
                        getActivity().startActivity(new Intent(getActivity(), MyTaskListActivity.class));
//                        getActivity().startActivityForResult(new Intent(getActivity(), PublishTReportActivity.class).putExtra("id", messageBean.getContentId()),BaseActivity.ActivityResult);
                        break;
                    case AppHttpPath.NOTICE_TYPE_TASK_REPORT:
                        //上报审核
                        startActivity(new Intent(mContext, ReportDetailActivity.class).putExtra("reportId", messageBean.getContentId()));
                        break;
                    case AppHttpPath.NOTICE_TYPE_CONCILIATION:
                        startActivity(new Intent(mContext, ConciliatonInfoActivity.class).putExtra("id", messageBean.getContentId()));
                        break;
                    default:
                        startActivity(new Intent(getContext(),InformMsgInfoActivity.class).putExtra("id",messageBean.getId()));
                        break;
                }
                mPresenter.allReadMsg(type, messageBean.getId(), "");
            }else {
                startActivity(new Intent(mContext, CommentChildActivity.class)
                        .putExtra("id",likeMsgAdapter.getData().get(position).getfId())
                        .putExtra("type",likeMsgAdapter.getData().get(position).getTypeId())
                        .putExtra("unReadId",likeMsgAdapter.getData().get(position).getId()));
            }
            if (likeMsgAdapter.getData().get(position).getIsRead() == 1){
                likeMsgAdapter.getData().get(position).setIsRead(0);//已读
                likeMsgAdapter.notifyItemChanged(position);
                UnReadCountBean.DataBean unReadCount = MyApp.getUnReadCountBean();
                unReadCount.setMessageCount(unReadCount.getMessageCount() > 0? 0 : unReadCount.getMessageCount() - 1);
                MyApp.setUnReadCountBean(unReadCount);
                refreshUnRead();
            }
        });

        likeMsgAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 1){
                    DeleteMsgDialog  deleteMsgDialog = new DeleteMsgDialog(mContext).builder();
                    deleteMsgDialog.setData("从消息列表删除")
                            .setCanceledOnTouchOutside(true)
                            .setButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletePosition = position;
                            mPresenter.deleteSystemMsg(likeMsgAdapter.getItem(position).getLogId(), IMessageContract.DELETE_SYSTEM_MESSAGE);
                        }
                    }).show();
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 判断是否存在未读消息 存在false,不存在true
     * @param messages
     * @return
     */
    public boolean isAllRead(List<LikeMsgListBean.DataBean.MessageBean> messages){
        for (LikeMsgListBean.DataBean.MessageBean messageBean : messages){
            if (messageBean.getIsRead() == 1){
                return false;
            }
        }
        return true;
    }

    /**
     * 刷新未读标记
     */
    public void refreshUnRead(){
        UnReadCountBean.DataBean unReadCount = MyApp.getUnReadCountBean();
        if (isAllRead(likeMsgAdapter.getData())){
            if (type == 1){
                unReadCount.setNotificationMessage(0);
            }else {
                unReadCount.setCommentMessage(0);
            }
        }
        MyApp.setUnReadCountBean(unReadCount);
        EventManager.sendStringMsg(ActionConfig.UN_READ_MESSAG_TAG);
    }

    /**
     * 获取数据
     */
    public void getData(boolean showProgress){
        mPresenter.getLikeMsgList(type,page,pagesize,IMessageContract.GET_MESSAGE_LIST,showProgress);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 全部已读
     */
    public void allRead(){
        mPresenter.allReadMsg(type, -1, IMessageContract.ALL_READ);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case IMessageContract.GET_MESSAGE_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                LikeMsgListBean likeMsgListBean = (LikeMsgListBean) o;
                if (page == 1){
                    likeMsgAdapter.getData().clear();
                }
                if (likeMsgListBean.getData().getDatas().size() < pagesize){
                    mSmartrefreshlayout.finishLoadMoreWithNoMoreData();
                }else {
                    mSmartrefreshlayout.setNoMoreData(false);
                }
                likeMsgAdapter.addData(likeMsgListBean.getData().getDatas());
                break;
            case IMessageContract.ALL_READ:
                //全部已读
                for (LikeMsgListBean.DataBean.MessageBean messageBean: likeMsgAdapter.getData()){
                    messageBean.setIsRead(0);
                }
                refreshUnRead();
                likeMsgAdapter.notifyDataSetChanged();
                break;
            case IMessageContract.DELETE_SYSTEM_MESSAGE:
                if (likeMsgAdapter.getData().get(deletePosition).getIsRead() == 1){
                    UnReadCountBean.DataBean unReadCount = MyApp.getUnReadCountBean();
                    unReadCount.setMessageCount(unReadCount.getMessageCount() > 0? 0 : unReadCount.getMessageCount() - 1);
                    MyApp.setUnReadCountBean(unReadCount);
                }
                likeMsgAdapter.getData().remove(deletePosition);
                likeMsgAdapter.notifyDataSetChanged();
                refreshUnRead();
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String tag, Object o) {
        ToastUtils.error(mContext, String.valueOf(o));
        switch (tag){
            case IMessageContract.GET_MESSAGE_LIST:
                mSmartrefreshlayout.finishRefresh();
                mSmartrefreshlayout.finishLoadMore();
                break;
            default:
                break;
        }
    }

}

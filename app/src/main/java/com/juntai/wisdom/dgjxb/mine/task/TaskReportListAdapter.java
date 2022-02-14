package com.juntai.wisdom.dgjxb.mine.task;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.task.TaskDetailBean;

import java.util.List;

/**
 * Describe:任务上报列表
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class TaskReportListAdapter extends BaseQuickAdapter<TaskDetailBean.DataBean.ReportBean, BaseViewHolder> {

    public TaskReportListAdapter(int layoutResId, @Nullable List<TaskDetailBean.DataBean.ReportBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskDetailBean.DataBean.ReportBean item) {
        ImageLoadUtil.loadImage(mContext.getApplicationContext(), item.getPictureOneUrl(),
                R.drawable.nopicture,R.drawable.nopicture,helper.getView(R.id.item_iv));
        helper.setText(R.id.item_name,item.getCreate());
        helper.setText(R.id.item_content,item.getGmtCreate());
        helper.setVisible(R.id.item_status,true);
        setStatus(helper.getView(R.id.item_status),item.getState());
    }

    public void setStatus(TextView textView, int status){
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
    }
}

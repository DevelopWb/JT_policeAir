package com.juntai.wisdom.policeAir.mine.task;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.bean.task.TaskListBean;

import java.util.List;

/**
 * Describe:任务列表适配器
 * Create by zhangzhenlong
 * 2020-5-16
 * email:954101549@qq.com
 */
public class MyTaskListAdapter extends BaseQuickAdapter<TaskListBean.DataBean.TaskBean, BaseViewHolder> {

    public MyTaskListAdapter(int layoutResId, @Nullable List<TaskListBean.DataBean.TaskBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean.DataBean.TaskBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setText(R.id.item_content,item.getRemark());
        if (item.getIsRead() == 0){
            helper.setVisible(R.id.read_tag,false);
        }else {
            helper.setVisible(R.id.read_tag,true);
        }
    }
}

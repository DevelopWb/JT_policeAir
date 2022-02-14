package com.juntai.wisdom.dgjxb.entrance.complete_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.PoliceBranchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 部门输入下来提示列表
 * Create by zhangzhenlong
 * 2020-10-21
 * email:954101549@qq.com
 */
public class DataValueAdapter extends BaseAdapter implements Filterable {
    private List<PoliceBranchBean.DataBean> allDatas = new ArrayList<>();
    private List<PoliceBranchBean.DataBean> datas = new ArrayList<>();
    private Context mContext;
    private ArrayFilter mFilter;

    public DataValueAdapter(Context context, List<PoliceBranchBean.DataBean> allDatas) {
        this.mContext = context;
        this.allDatas = allDatas;
    }

    public List<PoliceBranchBean.DataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<PoliceBranchBean.DataBean> datas) {
        this.datas = datas;
    }

    public List<PoliceBranchBean.DataBean> getAllDatas() {
        return allDatas;
    }

    public void setAllDatas(List<PoliceBranchBean.DataBean> allDatas) {
        this.allDatas = allDatas;
    }

    @Override
    public int getCount() {
        return datas == null? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_branch_mark, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.title);
            holder.tv_tag = (TextView) view.findViewById(R.id.tag);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        PoliceBranchBean.DataBean branchBean = datas.get(i);
        holder.tv_name.setText(branchBean.getName());
        holder.tv_tag.setText(branchBean.getProvinceName()+branchBean.getCityName()+branchBean.getAreaName()+branchBean.getStreetName());
        return view;
    }

    static class ViewHolder{
        TextView tv_name;
        TextView tv_tag;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * 数据过滤
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {//constraint用户输入关键词
            FilterResults results = new FilterResults();
            if (allDatas == null) {
                allDatas = new ArrayList<>();
            }
            ArrayList<PoliceBranchBean.DataBean> newData = new ArrayList<>();
            if (constraint == null || constraint.toString().trim().length() == 0) {
            } else {
                String prefixString = constraint.toString().toLowerCase();
                for (PoliceBranchBean.DataBean testBean : allDatas) {
                    if (testBean.getName().startsWith(prefixString)) {
                        newData.add(testBean);
                    }
                }
            }
            results.values = newData;
            results.count = newData.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datas = (ArrayList)results.values;
            if (datas == null || datas.size() ==0){
                ToastUtils.warning(mContext, "暂无相关部门！");
            }
            notifyDataSetChanged();
        }

        //给输入框返回的选择结果
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            PoliceBranchBean.DataBean testBean = (PoliceBranchBean.DataBean) resultValue;
            return testBean.getName();
        }
    }
}

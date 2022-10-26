package com.example.appbase.base.customview.selectPics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/6/12 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/6/12 16:30
 */
public class SelectPicVideoRv extends RecyclerView {


    private OnPhotoItemClick onPhotoItemClick;
    private int mSpanCount = 4;//一行的个数，默认4
    private int mMaxCount = 9;//最大个数，默认9个
    private boolean isDetail = false;//不是详情 可删除
    private GridLayoutManager layoutManager;


    public SelectPicVideoRv setmSpanCount(int mSpanCount) {
        this.mSpanCount = mSpanCount;
        if (layoutManager != null) {
            layoutManager.setSpanCount(mSpanCount);
        }
        return  this;
    }


    public int getmMaxCount() {
        return mMaxCount;
    }

    public SelectPicVideoRv setmMaxCount(int mMaxCount) {
        this.mMaxCount = mMaxCount;
        return  this;
    }

    public SelectPicVideoRv setDetail(boolean detail) {
        isDetail = detail;
        if (selectedPicsAdapter != null) {
            selectedPicsAdapter.setDetail(isDetail);
        }
        return  this;
    }

    public SelectPicVideoRv setOnPhotoItemClick(OnPhotoItemClick onPhotoItemClick) {
        this.onPhotoItemClick = onPhotoItemClick;
        return this;
    }




    private SelectedPicVideoAdapter selectedPicsAdapter;


    public SelectPicVideoRv(Context context) {
        super(context);
        initView(context);
    }

    public SelectPicVideoRv(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public SelectPicVideoRv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }


    private void initView(Context context) {
        layoutManager = new GridLayoutManager(context, mSpanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        selectedPicsAdapter = new SelectedPicVideoAdapter(R.layout.selected_pic_item);
        selectedPicsAdapter.setDetail(isDetail);
        setLayoutManager(layoutManager);
        setAdapter(selectedPicsAdapter);
        selectedPicsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        selectedPicsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SelectPicVideoBean selectPicVideoBean = (SelectPicVideoBean) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.select_pic_icon_iv) {
                    switch (selectPicVideoBean.getType()) {
                        case SelectPicVideoBean.TYPE_NULL:
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onSelectPic(adapter,view, position);
                            }
                            break;
                        case SelectPicVideoBean.TYPE_IMAGE:
                            //图片路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onPicClick(adapter,view, position);
                            }
                            break;
                        case SelectPicVideoBean.TYPE_VIDEO:
                            //视频路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onVedioPicClick(adapter,view, position);
                            }
                            break;
                        default:
                            break;
                    }

                } else if (id == R.id.delete_item_iv) {
                    adapter.remove(position);
                    addData(selectedPicsAdapter.getData());
                }
            }
        });
        setData(null);
    }

    public void setData(List<SelectPicVideoBean> arrays) {
        if (selectedPicsAdapter != null) {
            if (arrays == null) {
                arrays = new ArrayList<>();
            }
            if (arrays.size() < mMaxCount + 1 && !isDetail) {
                arrays.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_NULL,""));
            }
            selectedPicsAdapter.setNewData(arrays);
        }
    }

    public void addData(List<SelectPicVideoBean> arrays) {
        if (selectedPicsAdapter != null) {
            List<SelectPicVideoBean> pics = new ArrayList<>();
            for (SelectPicVideoBean array : arrays) {
                if (SelectPicVideoBean.TYPE_NULL!=array.getType()) {
                    pics.add(array);
                }
            }
            if (pics.size() < mMaxCount && !isDetail) {
                pics.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_NULL,""));
            }
            selectedPicsAdapter.setNewData(pics);
        }
    }
    public List<SelectPicVideoBean> getAdapterData() {
        List<SelectPicVideoBean> arrays = selectedPicsAdapter.getData();
        List<SelectPicVideoBean> arrays_return = new ArrayList<>();
        for (SelectPicVideoBean array : arrays) {
            if (SelectPicVideoBean.TYPE_NULL!=array.getType()) {
                arrays_return.add(array);
            }
        }
        return arrays_return;
    }

    /**
     * 视频图片和普通图片的点击事件
     */
    public interface OnPhotoItemClick {
        void onVedioPicClick(BaseQuickAdapter adapter, View view, int position);

        void onPicClick(BaseQuickAdapter adapter, View view, int position);

        void onSelectPic(BaseQuickAdapter adapter, View view, int position);

    }


}

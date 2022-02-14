package com.juntai.wisdom.basecomponent.widght;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.basecomponent.R;
import com.juntai.wisdom.basecomponent.bean.MoreMenuBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Describe:底部分享菜单
 * Create by zhangzhenlong
 * 2021-1-12
 * email:954101549@qq.com
 */
public class BaseMoreBottomDialog extends DialogFragment implements View.OnClickListener {

    private List<MoreMenuBean> arrays = new ArrayList<>();
    private RecyclerView mBaseBottomDialogRv;
    private OnItemClick onItemClick;
    /**
     * 取消
     */
    private TextView mBaseBottomDialogCancelTv;
    private BottomDialogGridAdapter adapter;

    public BaseMoreBottomDialog setOnBottomDialogCallBack(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        return this;
    }

    public void setData(List<MoreMenuBean> arrays) {
        this.arrays = arrays;
        refrashAdapter();
    }

    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
        setShowsDialog(true);

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            getDialog().setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            getDialog().setOwnerActivity(activity);
        }
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG);
            if (dialogState != null) {
                getDialog().onRestoreInstanceState(dialogState);
            }
        }
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.more_bottom_dialog, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        try {//利用反射获取listenersHandler
            Field field = Dialog.class.getDeclaredField("mListenersHandler");
            field.setAccessible(true);
            Handler mListenersHandler = (Handler) field.get(getDialog());
            if (mListenersHandler != null){
                mListenersHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    private void initView(View view) {
        mBaseBottomDialogRv = (RecyclerView) view.findViewById(R.id.base_bottom_dialog_rv);
        mBaseBottomDialogCancelTv = (TextView) view.findViewById(R.id.base_bottom_dialog_cancel_tv);
        mBaseBottomDialogCancelTv.setOnClickListener(this);
        adapter = new BottomDialogGridAdapter(R.layout.more_menu_layout);
        mBaseBottomDialogRv.setAdapter(adapter);
        mBaseBottomDialogRv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (onItemClick != null) {
                    onItemClick.onItemClick(adapter,view,position);
                }
            }
        });
        adapter.setNewData(arrays);
        setCancelable(true);
        getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dismiss();
            }
        });
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
    }

    public BottomDialogGridAdapter getAdapter() {
        return adapter;
    }

    public void refrashAdapter(){
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.base_bottom_dialog_cancel_tv) {
            dismiss();
        }
    }

    public  interface OnItemClick{
        void  onItemClick(BaseQuickAdapter adapter, View view, int position);
    }
}

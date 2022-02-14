package com.juntai.wisdom.dgjxb.home_page.map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.bdmap.act.NavigationDialog;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.map.MapClusterItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * author:wong
 * Date: 2019/5/30
 * Description:
 */
public class MapBottomListDialog extends DialogFragment implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView bottomListRv;
    private MapBottomListItemClickListenner mapBottomListItemClickListenner;
    private MapClusterItem selectedItem = null;
    private List<MapClusterItem> list = new ArrayList<>();
    private String type = null;
    View view;
    NavigationDialog navigationDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.bottom_list_layout, null);
        Dialog dialog = new Dialog(getActivity(), R.style.CusDialog);
        navigationDialog = new NavigationDialog();
        dialog.setContentView(view);
        dialog.setTitle("标题");
        dialog.setCanceledOnTouchOutside(true);
        //Do something
        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        bottomListRv = view.findViewById(R.id.bottom_list_rv);
        bottomListRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        bottomListRv.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        Bundle bundle = getArguments();
        if (bundle != null) {
            list = (List<MapClusterItem>) bundle.getSerializable("data");
            if(list.size()>0 && MapClusterItem.NEWS.equals(list.get(0).getType())){
                Collections.sort(list);
            }
            ClusterClickAdapter clusterClickAdapter = new ClusterClickAdapter(R.layout.item_case,list);
            bottomListRv.setAdapter(clusterClickAdapter);
            clusterClickAdapter.setOnItemClickListener(this::onItemClick);
            type = list.get(0).getType();
            if(MapClusterItem.CASE.equals(list.get(0).getType())){
                clusterClickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    if (view.getId() == R.id.item_right) {//导航
                        navigationDialog.showMenu(
                                getFragmentManager(),
                                ((MapClusterItem) adapter.getData().get(position)).mcase.getLatitude(),
                                ((MapClusterItem) adapter.getData().get(position)).mcase.getLongitude(),
                                ((MapClusterItem) adapter.getData().get(position)).mcase.getAddress());
                    }
                });
            }
        }
        return dialog;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MapClusterItem mapClusterItem;
        mapClusterItem = (MapClusterItem)adapter.getData().get(position);
        this.selectedItem = mapClusterItem;
        dismiss();
    }

    public void setCallback(MapBottomListItemClickListenner mapBottomListItemClickListenner) {
        this.mapBottomListItemClickListenner = mapBottomListItemClickListenner;
    }

    public interface MapBottomListItemClickListenner {
        void onBottomListItemClick(MapClusterItem item);
    }

    @Override
    public void onDestroy() {
        // 通过接口回传数据给activity
        if (mapBottomListItemClickListenner != null && selectedItem != null) {
            mapBottomListItemClickListenner.onBottomListItemClick(selectedItem);
        }
        super.onDestroy();
    }
}

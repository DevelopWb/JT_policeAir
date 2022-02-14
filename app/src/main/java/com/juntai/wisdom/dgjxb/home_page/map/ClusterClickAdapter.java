package com.juntai.wisdom.dgjxb.home_page.map;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.bdmap.service.LocateAndUpload;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.bean.map.MapClusterItem;

import java.util.List;

/**
 * author:wong  地图底部弹窗的适配器
 * Date: 2019/4/19
 * Description:
 */
public class ClusterClickAdapter extends BaseQuickAdapter<MapClusterItem, BaseViewHolder> {
    public ClusterClickAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapClusterItem item) {
        ImageView imageView = helper.getView(R.id.item_image);
        switch (item.getType()) {
            case MapClusterItem.STREAM_CAMERA:
                //标识 区分硬盘录像机和独立摄像头
//                helper.setGone(R.id.item_tag_tv,true);
//                if (0==item.streamCamera.getFlag()) {
//                    //网络视频录像机
//                    helper.setText(R.id.item_tag_tv,"NVR");
//                    helper.setBackgroundColor(R.id.item_tag_tv,
//                            ContextCompat.getColor(mContext.getApplicationContext(),R.color.blue));
//                }else{
//                    //网络摄像机IP
//                    helper.setText(R.id.item_tag_tv,"IPC");
//                    helper.setBackgroundColor(R.id.item_tag_tv,
//                            ContextCompat.getColor(mContext.getApplicationContext(),R.color.colorGreen));
//                }

                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.streamCamera.getEzOpen(),imageView);
                helper.setText(R.id.item_title, item.streamCamera.getName())
                        .setText(R.id.item_content, "编号:" + item.streamCamera.getNumber())
                        .setText(R.id.item_time, item.streamCamera.getAddress());
                break;
            case MapClusterItem.CAR:
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),item.car.getImg(),imageView);
                helper.setText(R.id.item_title, item.car.getDeviceName())
                        .setText(R.id.item_content, "距离:" + String.valueOf(Math.round(DistanceUtil.getDistance(
                                new LatLng(item.car.getLat(), item.car.getLng()),
                                new LatLng(LocateAndUpload.lat, LocateAndUpload.lng)) / 1000)) + "公里")
                        .setText(R.id.item_time, item.car.getGpsTime());
                break;
            case MapClusterItem.CASE:
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.mcase.getPhotoOne(), imageView);
                helper.setText(R.id.item_title, item.mcase.getAddress())
                        .setText(R.id.item_content, item.mcase.getCaseContent())
                        .setText(R.id.item_time, item.mcase.getCreateDate());
                helper.getView(R.id.item_right).setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.item_right);
                break;
            case MapClusterItem.PEOPLE:
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ImageLoadUtil.loadCircularImage(mContext.getApplicationContext(),
                        item.people.getHeadPortrait(),
                        R.mipmap.default_user_head_icon,
                        R.mipmap.default_user_head_icon, imageView);
                helper.setText(R.id.item_title, "" + item.people.getNickname())
                        .setText(R.id.item_content, "职务:" + item.people.getPostName())
//                        .setText(R.id.item_case_content, "状态:" + "离线")
                        .setText(R.id.item_time, "所属单位:" + item.people.getDepartmentName());
                break;
            case MapClusterItem.SITE:
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.site.getLogoUrl(),imageView);
                helper.setText(R.id.item_title, "" + item.site.getName());
                helper.setText(R.id.item_content, item.site.getAddress());
                helper.getView(R.id.item_time).setVisibility(View.GONE);
                break;

            case MapClusterItem.INSPECTION:
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(), item.inspection.getLogoUrl(),imageView);
                helper.setText(R.id.item_title, item.inspection.getName())
                        .setText(R.id.item_time, item.inspection.getAddress());
                helper.setVisible(R.id.item_content,false);
                break;
            case MapClusterItem.NEWS:
                ImageLoadUtil.loadImageForList(mContext.getApplicationContext(), item.news.getPicture(), imageView, item.news.getTypeId());
                ((TextView)helper.getView(R.id.item_title)).setMaxLines(2);
                helper.setText(R.id.item_title, item.news.getTitle())
                        .setText(R.id.item_content, item.news.getGmtCreate());
                helper.getView(R.id.item_time).setVisibility(View.GONE);
                break;
            case MapClusterItem.KEY_PERSONNEL:
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                        item.keyPersonnel.getHeadPortrait(),imageView);
                helper.setText(R.id.item_title, item.keyPersonnel.getName())
                        .setText(R.id.item_content, item.keyPersonnel.getPhone());
                helper.getView(R.id.item_time).setVisibility(View.GONE);
                break;
            case MapClusterItem.DRONE:
                ImageLoadUtil.loadImageCache(mContext.getApplicationContext(),
                        item.droneBean.getImg(), imageView);
                helper.setText(R.id.item_title, item.droneBean.getName());
                helper.getView(R.id.item_time).setVisibility(View.GONE);
                helper.setVisible(R.id.item_content,false);
                break;
        }
    }
}

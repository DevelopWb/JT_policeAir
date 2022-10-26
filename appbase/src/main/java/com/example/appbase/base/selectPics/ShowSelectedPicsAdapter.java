package com.example.appbase.base.selectPics;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.bean.BasePicVideoBean;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * Author:wang_sir
 * Time:2018/7/19 10:52
 * Description:This is ShowSelectedPicsAdapter
 */
public class ShowSelectedPicsAdapter extends BaseQuickAdapter<BasePicVideoBean, BaseViewHolder> {

    private boolean isShowTag = false;

    public void setShowTag(boolean showTag) {
        isShowTag = showTag;
    }
    private int widthAndHeigh = 60;
    private boolean delateable = true;
    private boolean isBigPic = false;

    public void setWidthAndHeigh(int widthAndHeigh) {
        this.widthAndHeigh = widthAndHeigh;
    }
    public void setDelateable(boolean delateable) {
        this.delateable = delateable;
    }

    public ShowSelectedPicsAdapter(int layoutResId, boolean isBigPic) {
        super(layoutResId);
        this.isBigPic = isBigPic;
    }


    @Override
    protected void convert(BaseViewHolder helper, BasePicVideoBean item) {

        switch (item.getType()) {
            case BasePicVideoBean.TYPE_NULL:
                helper.setGone(R.id.item_video_tag, false);
                if (isBigPic) {
                    ImageLoadUtil.loadCentercropImage(mContext.getApplicationContext(), 0, (ImageView) helper.getView(R.id.select_pic_icon_iv));
                }else {
                    ImageLoadUtil.loadCentercropImage(mContext.getApplicationContext(), R.mipmap.add_icons, (ImageView) helper.getView(R.id.select_pic_icon_iv));

                }
                helper.setGone(R.id.delete_item_iv, false);
                break;
            case BasePicVideoBean.TYPE_IMAGE:
            case BasePicVideoBean.TYPE_VIDEO:
                if (delateable) {
                    helper.setGone(R.id.delete_item_iv, true);
                }else{
                    helper.setGone(R.id.delete_item_iv, false);
                }

                if (BasePicVideoBean.TYPE_VIDEO==item.getType()) {
                    ImageLoadUtil.loadVideoScreenshot(mContext, item.getUrl(), helper.getView(R.id.select_pic_icon_iv),  new ImageLoadUtil.OnImageLoadSuccess() {
                        @Override
                        public void loadSuccess(int width, int height) {
                            //加载成功
                        }
                    });
                    helper.setGone(R.id.item_video_tag, true);
                } else {
                    ImageLoadUtil.loadImageNoCache(mContext, item.getUrl(), (ImageView) helper.getView(R.id.select_pic_icon_iv));
                    helper.setGone(R.id.item_video_tag, false);
                }
                break;
            default:
                break;
        }

        if (isShowTag) {
            helper.setVisible(R.id.item_tag, true);
            switch (helper.getLayoutPosition()) {
                case 0:
                    helper.setText(R.id.item_tag, "店铺头像");
                    break;
                case 1:
                    helper.setText(R.id.item_tag, "营业执照");
                    break;
                case 2:
                    helper.setText(R.id.item_tag, "店铺实景图");
                    break;
                default:
                    helper.setVisible(R.id.item_tag, false);
                    break;
            }
        } else {
            helper.setVisible(R.id.item_tag, false);
        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_item_iv);
        if (!isBigPic) {
            ImageView imageView = helper.getView(R.id.select_pic_icon_iv);
            ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.width = DisplayUtil.dp2px(mContext, widthAndHeigh);// 控件的宽强制设成30
            linearParams.height = DisplayUtil.dp2px(mContext, widthAndHeigh);// 控件的高强制设成30
            imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }

    }
}
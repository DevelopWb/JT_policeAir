package com.juntai.wisdom.policeAir.mine.exchange_mall;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.utils.ActionConfig;
import com.juntai.wisdom.basecomponent.utils.DialogUtil;
import com.juntai.wisdom.basecomponent.utils.EventManager;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.policeAir.R;
import com.juntai.wisdom.policeAir.MyApp;
import com.juntai.wisdom.policeAir.bean.exchang_mall.GoodsInfoBean;
import com.juntai.wisdom.policeAir.utils.GlideImageLoader;
import com.juntai.wisdom.video.img.ImageZoomActivity;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

/**
 * @description 商品详情
 * @aouther ZhangZhenlong
 * @date 2020-6-2
 */
public class GoodsInfoActivity extends BaseMvpActivity<ExchangeMallPresent> implements ExchangeMallContract.IMallView, View.OnClickListener {

    private Banner banner;
    /**
     * 30积分
     */
    private TextView mItemPrice;
    /**
     * 立即兑换
     */
    private TextView mItemName;
    /**
     * 立即兑换
     */
    private TextView mPayBtn;
    private int goodsId;//商品id
    private GoodsInfoBean goodsInfoBean;
    private ArrayList<String> images = new ArrayList<>();
    protected boolean hasVideo = false;//是否有视频文件
    private GlideImageLoader imageLoader;

    @Override
    protected ExchangeMallPresent createPresenter() {
        return new ExchangeMallPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initView() {
        setTitleName("商品详情");
        goodsId = getIntent().getIntExtra("id",0);
        banner = (Banner) findViewById(R.id.banner);
        mItemPrice = (TextView) findViewById(R.id.item_price);
        mItemName = (TextView) findViewById(R.id.item_name);
        mPayBtn = (TextView) findViewById(R.id.pay_btn);
        mPayBtn.setOnClickListener(this);

        /*anner*/
        banner = findViewById(R.id.banner);
        banner.isAutoPlay(false);
        ArrayList<String> photos = new ArrayList<>();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //查看图片
                photos.clear();
                photos.addAll(images);
                if (hasVideo) {
                    photos.remove(0);
                }
                startActivity(new Intent(mContext, ImageZoomActivity.class).putExtra("paths", photos).putExtra("item", hasVideo ? position - 1 : position));
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.getGoodsDetail(goodsId,ExchangeMallContract.GET_GOODS_DETAIL);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag){
            case ExchangeMallContract.GET_GOODS_DETAIL:
                goodsInfoBean = (GoodsInfoBean) o;
                mItemPrice.setText(goodsInfoBean.getData().getPrice()+"积分");
                mItemName.setText(goodsInfoBean.getData().getCommodityName());

                images.clear();
                for (GoodsInfoBean.DataBean.PictureBean pictureBean:goodsInfoBean.getData().getPicture()){
                    if (pictureBean.getImg().contains("mp4")){
                        hasVideo = true;
                    }
                    images.add(pictureBean.getImg());
                }
                imageLoader = new GlideImageLoader().setOnFullScreenCallBack(null);
                banner.setImages(images).setImageLoader(imageLoader).start();
                break;
            case ExchangeMallContract.EXCHANGE_GOODS:
//                ToastUtils.success(mContext,"兑换成功");
                DialogUtil.getMessageDialog(mContext, getString(R.string.exchange_success_remind)).setTitle("兑换成功").show();
                EventManager.sendStringMsg(ActionConfig.UPDATE_MY_SCORE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.pay_btn:
                //立即兑换
                if (goodsInfoBean.getData().getInventoryNum() < 1){
                    ToastUtils.warning(mContext,"库存不足");
                    return;
                }

                DialogUtil.getConfirmDialog(mContext, "确定兑换该商品？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MyApp.getUser().getData().getScore() < goodsInfoBean.getData().getPrice()){
                            ToastUtils.warning(mContext,"积分不足");
                            return;
                        }
                        mPresenter.exchangeGoods(goodsInfoBean.getData().getPrice(),goodsInfoBean.getData().getCommodityId(),
                                goodsInfoBean.getData().getCommodityName(),ExchangeMallContract.EXCHANGE_GOODS);
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.releaseBanner();
            banner.removeAllViews();
            banner.setOnBannerListener(null);
            if (imageLoader != null) {
                imageLoader.setOnFullScreenCallBack(null);
                imageLoader.release();
            }

        }
        banner = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}

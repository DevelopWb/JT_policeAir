package com.juntai.wisdom.basecomponent.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.juntai.wisdom.basecomponent.R;
import com.juntai.wisdom.basecomponent.bean.BaseMenuBean;
import com.juntai.wisdom.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.basecomponent.utils.DividerItemDecoration;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.basecomponent.utils.LoadingDialog;
import com.juntai.wisdom.basecomponent.utils.ScreenUtils;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class BaseActivity extends RxAppCompatActivity implements Toolbar.OnMenuItemClickListener {
    public abstract int getLayoutView();

    public abstract void initView();

    public abstract void initData();
    public final String TAG = getClass().getSimpleName();
    public static String BASE_PARCELABLE = "parcelable";//请求的回执
    public static String BASE_STRING = "baseString";//

    public Context mContext;
    public Toast toast;
    private Toolbar toolbar;
    protected CoordinatorLayout mBaseRootCol;
    private boolean title_menu_first = true;
    private TextView mBackTv;
    public ImmersionBar mImmersionBar;
    private TextView titleName, titleRightTv;
    private boolean autoHideKeyboard = true;
    public FrameLayout frameLayout;
    public static int ActivityResult = 1001;//activity返回值
    public static int BASE_REQUEST_RESULT = 10086;//请求的回执

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventManager.getEventBus().register(this);//注册
        mContext = this;
        mImmersionBar = ImmersionBar.with(this);
//        initWidows();
        setContentView(R.layout.activity_base);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        frameLayout = findViewById(R.id.base_content);
        if (0 != getLayoutView()) {
            frameLayout.addView(View.inflate(this, getLayoutView(), null));
        }
        toolbar = findViewById(R.id.base_toolbar);
        mBaseRootCol = findViewById(R.id.base_col);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBackTv = findViewById(R.id.back_tv);
        titleName = findViewById(R.id.title_name);
        titleRightTv = findViewById(R.id.title_rightTv);
        initToolbarAndStatusBar(true);
        initLeftBackTv(true);
        initView();
        initData();
    }
    /**
     * 配置view的margin属性
     */
    public void setMarginOfLinearLayout(View view, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(this, left);
        top = DisplayUtil.dp2px(this, top);
        right = DisplayUtil.dp2px(this, right);
        bottom = DisplayUtil.dp2px(this, bottom);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getLayoutParams());
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }
    /**
     * 配置view的margin属性
     */
    public void setMarginParentOfConstraintLayout(View view, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(this, left);
        top = DisplayUtil.dp2px(this, top);
        right = DisplayUtil.dp2px(this, right);
        bottom = DisplayUtil.dp2px(this, bottom);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(view.getLayoutParams());
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }
    /**
     * 字体大小不随系统变化而变化 字体待定
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        try {
            Configuration config=new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config,res.getDisplayMetrics() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 警小宝 东关派出所版本 初始化toolbar和状态栏
     */
    protected void initToolbarAndStatusBar(boolean visible) {
        if (visible) {
            getToolbar().setVisibility(View.VISIBLE);
            getToolbar().setNavigationIcon(null);
            getToolbar().setBackgroundResource(R.drawable.sp_filled_gray_lighter);
            //状态栏配置
            mBaseRootCol.setFitsSystemWindows(true);
            mImmersionBar.statusBarColor(R.color.gray_light)
                    .statusBarDarkFont(true)
                    .init();
        } else {
            getToolbar().setVisibility(View.GONE);
            //状态栏配置
            mBaseRootCol.setFitsSystemWindows(false);
            mImmersionBar.reset().transparentStatusBar().init();
        }

    }

    /**
     * 初始化左侧按钮 默认不显示
     *
     * @param isShow 是否显示
     */
    protected void initLeftBackTv(boolean isShow) {
        if (isShow) {
            mBackTv.setVisibility(View.VISIBLE);
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.module_base_app_back);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mBackTv.setCompoundDrawables(drawable, null, null, null);
            mBackTv.setText("返回");
            mBackTv.setCompoundDrawablePadding(-DisplayUtil.dp2px(this, 3));
            mBackTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            mBackTv.setVisibility(View.GONE);
        }

    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTitleRightTv() {
        titleRightTv.setVisibility(View.VISIBLE);
        return titleRightTv;
    }

    /**
     * 展示加载动画
     */
    public void showLoadingDialog(Context context) {
        LoadingDialog.getInstance().showProgress(context);
    }

    /**
     * 获取左控件
     *
     * @return
     */
    public TextView getTitleLeftTv() {
        mBackTv.setVisibility(View.VISIBLE);
        return mBackTv;
    }

    /**
     * 标题
     *
     * @param title
     */
    public void setTitleName(String title) {
        titleName.setText(title);
        titleName.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    /**
     * title右侧:图标类
     */
    private void setRightRes() {
        //扩展menu
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //添加监听
        toolbar.setOnMenuItemClickListener(this);
    }

    /**
     * 显示右侧图标
     *
     * @param itemId
     */
    public void showTitleRes(int... itemId) {
        if (title_menu_first) {
            setRightRes();
            title_menu_first = false;
        }
        for (int item : itemId) {
            //显示
            toolbar.getMenu().findItem(item).setVisible(true);//通过id查找,也可以用setIcon()设置图标
            //            toolBar.getMenu().getItem(0).setVisible(true);//通过位置查找
        }
    }

    /**
     * 隐藏title图标
     *
     * @param itemId :图标对应的选项idFs
     */
    public void hindTitleRes(int... itemId) {
        //        if (titleBack != null)
        //            titleBack.setVisibility(View.GONE);
        for (int item : itemId) {
            //隐藏
            toolbar.getMenu().findItem(item).setVisible(false);
        }
    }

    /**
     * toolbar菜单监听---子activity直接onMenuItemClick()
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    /**
     * 清理webview
     *
     * @param webView
     */
    public void closeWebView(WebView webView) {
        if (webView != null) {
//            ViewGroup parent = webView.getParent();
//            if (parent != null) {
//                parent.re(webView);
//            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    /**
     * 点击空白隐藏键盘
     *
     * @param event
     * @param view
     * @param activity
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof TextView) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘  view 可以是当前点击的view 没必要全是edittext
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 点击空白自动隐藏键盘- - - 默认
     *
     * @param autoHideKeyboard:false - 不自动隐藏
     */
    public void setAutoHideKeyboard(boolean autoHideKeyboard) {
        this.autoHideKeyboard = autoHideKeyboard;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                if (autoHideKeyboard) {
                    hideKeyboard(ev, view, BaseActivity.this);//调用方法判断是否需要隐藏键盘
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        ToastUtils.info(mContext,"长按");
        return super.onKeyLongPress(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        EventManager.getEventBus().unregister(this);//解除注册
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
            mImmersionBar = null;
        }
        this.mContext = null;
        stopLoadingDialog();
    }

    /**
     * 停止加载动画
     */
    public void stopLoadingDialog() {
        LoadingDialog.getInstance().dismissProgress();
    }

    /**
     * 获取测试数据
     *
     * @return
     */
    public List<String> getTestData() {
        return Arrays.asList(new String[]{"test1", "test2", "test3", "测试很测试很多数据的测试很多数据的多数据的"});
    }

    /**
     * 获取TextView的值
     *
     * @param textView
     * @return
     */
    public String getTextViewValue(TextView textView) {
        if (TextUtils.isEmpty(textView.getText().toString().trim())) {
            return "";
        }
        return textView.getText().toString().trim();
    }



    /**
     * 初始化窗口 在界面为初始化之前调用
     */
    protected void initWidows() {
        //设置屏幕适配 360为设计图尺寸px/2
        ScreenUtils screenUtils = ScreenUtils.getInstance(getApplicationContext());
        if (screenUtils.isPortrait()) {
            screenUtils.adaptScreen4VerticalSlide(this, 360);
        } else {
            screenUtils.adaptScreen4HorizontalSlide(this, 360);
        }

    }

    /**
     * 初始化recyclerview LinearLayoutManager
     */
    public void initRecyclerview(RecyclerView recyclerView, BaseQuickAdapter baseQuickAdapter, @RecyclerView.Orientation int orientation) {
        LinearLayoutManager managere = new LinearLayoutManager(this, orientation, false);
//        baseQuickAdapter.setEmptyView(getAdapterEmptyView("一条记录也没有", emptyImage));
        baseQuickAdapter.setHeaderAndEmpty(true);
        recyclerView.setLayoutManager(managere);
        recyclerView.setAdapter(baseQuickAdapter);
    }

    /**
     * 获取空布局
     *
     * @param text
     * @return
     */
    public View getAdapterEmptyView(String text, int imageId) {
        View view = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        TextView noticeTv = view.findViewById(R.id.none_tv);
        noticeTv.setText(text);
        ImageView imageView = view.findViewById(R.id.none_image);
        if (-1==imageId) {
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(imageId);
        }

        return view;
    }

    /**
     * 释放imageview内存资源
     *
     * @param imageView
     */
    public void recycleImageView(ImageView imageView) {
        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        bm.recycle();
        bm = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(EventBusObject eventBusObject) {
        Log.d("MyWsManager", "MyWsManager-----BaseActivity--");

    }
    /**
     * 设置顶部图标
     * @param textView
     * @param drawableId
     */
    public void initViewTopDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        textView.setCompoundDrawables(null, drawable, null, null);//放顶部
    }
    /**
     * 设置左边图标
     * @param textView
     * @param drawableId
     */
    public void initViewLeftDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        textView.setCompoundDrawables(drawable, null, null, null);//放左边
    }

    /**
     * 设置右边图标
     *
     * @param textView
     * @param drawableId
     */
    public void initViewRightDrawable(TextView textView, int drawableId, int width, int height) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, DisplayUtil.dp2px(this, width), DisplayUtil.dp2px(this, height));//第一个 0 是距左边距离，第二个 0 是距上边距离，40 分别是长宽
        textView.setCompoundDrawables(null, null, drawable, null);//只放右边
    }

    /**
     * 添加分割线
     *
     * @param recyclerView
     * @param haveTopLine
     * @param isHorizontalDivider 水平分割线
     * @param haveEndLine         最后一个item下是否划线
     */
    public void addDivider(boolean isHorizontalDivider, RecyclerView recyclerView, boolean haveTopLine, boolean haveEndLine) {
        DividerItemDecoration dividerItemDecoration;
        if (isHorizontalDivider) {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_hor_line_sp);
        } else {
            dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
        }
        if (haveTopLine) {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.ALL);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.START);
            }
        } else {
            if (haveEndLine) {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.END);
            } else {
                dividerItemDecoration.setDividerMode(DividerItemDecoration.INSIDE);

            }
        }
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * @return
     */
    public List<BaseMenuBean> getBaseBottomDialogMenus(String... names) {
        List<BaseMenuBean> calls = new ArrayList<>();
        if (names.length == 0) {
            return null;
        }
        for (String name : names) {
            calls.add(new BaseMenuBean(name));
        }
        return calls;
    }

    /**
     * @return
     */
    public List<BaseMenuBean> getBaseBottomDialogMenus(BaseMenuBean... menus) {
        List<BaseMenuBean> calls = new ArrayList<>();
        if (menus.length == 0) {
            return null;
        }
        for (BaseMenuBean menu : menus) {
            calls.add(menu);
        }
        return calls;
    }
    /**
     * 图片压缩成功回调
     */
    public interface OnImageCompressedPath {
        void  compressedImagePath(File file);
    }
}

package com.juntai.wisdom.dgjxb.home_page.news.news_comment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.juntai.wisdom.basecomponent.base.BaseActivity;
import com.juntai.wisdom.basecomponent.base.BaseMvpActivity;
import com.juntai.wisdom.basecomponent.base.BaseObserver;
import com.juntai.wisdom.basecomponent.base.BaseResult;
import com.juntai.wisdom.basecomponent.utils.BaseAppUtils;
import com.juntai.wisdom.basecomponent.utils.FileCacheUtils;
import com.juntai.wisdom.basecomponent.utils.GlideEngine4;
import com.juntai.wisdom.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.basecomponent.utils.LoadingDialog;
import com.juntai.wisdom.basecomponent.utils.LogUtil;
import com.juntai.wisdom.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.basecomponent.widght.BaseEditText;
import com.juntai.wisdom.dgjxb.AppNetModule;
import com.juntai.wisdom.dgjxb.MyApp;
import com.juntai.wisdom.dgjxb.R;
import com.juntai.wisdom.dgjxb.home_page.news.NewsContract;
import com.juntai.wisdom.dgjxb.utils.Reflex;
import com.juntai.wisdom.dgjxb.utils.StringTools;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 文章评论
 * Created by Ma
 * on 2019/7/24
 */
public class EditCommentDialog extends DialogFragment {
    BaseEditText editText;
    ImageView commentImage;
    ImageView deleteBtn;
    boolean isCommentIng = false;
    RefreshListener refreshListener;
    BaseMvpActivity baseActivity;

    String imagePath;//图片地址（本地）

    int typeId;  //评论类型id 8资讯
    int commentedId;// 被评论的内容id（资讯id）
    int fId;//评论子id（回复评论时传主评论的id，默认不传）
    int commentedUserId;//被评论人id
    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_comment,container,false);
        editText = view.findViewById(R.id.edittext_comment);
        commentImage = view.findViewById(R.id.comment_iv);
        deleteBtn = view.findViewById(R.id.delete_btn);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        view.findViewById(R.id.send_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editComment();
            }
        });
        commentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringTools.isStringValueOk(imagePath)){
                    //图片选择
                    imageChoose((BaseActivity) getActivity());
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBtn.setVisibility(View.GONE);
                imagePath = null;
                commentImage.setImageResource(R.mipmap.comment_add_image);
            }
        });
        if (StringTools.isStringValueOk(imagePath)){
            ImageLoadUtil.loadImageCache(getContext().getApplicationContext(), imagePath, commentImage);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        LogUtil.e("dialog-->onCreateView()");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSoftInputListener();
    }

    /**
     * 点击非输入框区域时，自动收起键盘
     */
    private void initSoftInputListener() {
        getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        LogUtil.e("dialog-->initSoftInputListener()");
                        InputMethodManager manager = (InputMethodManager)getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (getDialog().getCurrentFocus() != null
                                    && getDialog().getCurrentFocus().getWindowToken() != null) {
                                manager.hideSoftInputFromWindow(
                                        getDialog().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public void onDestroy() {
        refreshListener = null;
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 设置宽度为屏宽、位置靠近屏幕底部
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        LogUtil.e("dialog-->onActivityCreated()");
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        //显示软键盘
//                        LogUtil.e("dialog-->setOnShowListener()");
//                        InputMethodManager inputManager =
//                               (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputManager.showSoftInput(editText, 0);
//                    }
//                },100);
//            }
//        });
    }

    /**
     * 发布评论
     */
    public void editComment(){
        //正在提交或者为空
        if (isCommentIng) {
            return;
        }
        if (!StringTools.isStringValueOk(editText.getText().toString().trim()) && !StringTools.isStringValueOk(imagePath)){
            return;
        }
        isCommentIng = true;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("account", MyApp.getAccount())
                .addFormDataPart("userId", String.valueOf(MyApp.getUid()))
                .addFormDataPart("token", MyApp.getUserToken())
                .addFormDataPart("typeId", String.valueOf(typeId))
                .addFormDataPart("commentedId", String.valueOf(commentedId))
                .addFormDataPart("content", editText.getText().toString().trim());
        if (fId > 0 && commentedUserId > 0){
            builder.addFormDataPart("fId", String.valueOf(fId))
                    .addFormDataPart("commentedUserId", String.valueOf(commentedUserId));
        }
        if (StringTools.isStringValueOk(imagePath)) {
            builder.addFormDataPart("commentFile", "commentFile", RequestBody.create(MediaType.parse("file"), new File(imagePath)));
        }
        Observable<BaseResult> observable = null;
        if (typeId == 0){//监控
            observable = AppNetModule.createrRetrofit()
                    .addCommentCamera(builder.build());
        }else {//资讯
            observable = AppNetModule.createrRetrofit()
                    .addCommentNews(builder.build());
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseResult>(baseActivity) {
                    @Override
                    public void onSuccess(BaseResult o) {
                        isCommentIng = false;
                        editText.setText("");
                        if (baseActivity != null){
                            ToastUtils.success(baseActivity, "评论成功");
                        }
                        if(refreshListener != null){
                            refreshListener.refresh();
                        }
                        imagePath = null;
                        dismiss();
                    }
                    @Override
                    public void onError(String msg) {
                        if (baseActivity != null){
                            baseActivity.onError("",msg);
                        }
                        isCommentIng = false;
                    }
                });
    }


    /**
     * 显示评论弹窗
     * @param manager
     * @param tag
     * @param typeId 评论类型id 8资讯 0监控
     * @param commentedId 被评论的内容id（资讯id）
     * @param fId 评论子id（回复评论时传主评论的id，默认不传）
     * @param commentedUserId  被评论人id
     * @param listener  回复成功回调
     * @param baseActivity
     */
    public void show(FragmentManager manager, String tag, int typeId, int commentedId, int fId, int commentedUserId, RefreshListener listener, BaseMvpActivity baseActivity) {
        show(manager,tag);
        this.typeId = typeId;
        this.commentedId = commentedId;
        this.fId = fId;
        this.commentedUserId = commentedUserId;
        refreshListener = listener;
        WeakReference<BaseMvpActivity> weakReference = new WeakReference<>(baseActivity);
        this.baseActivity = weakReference.get();
    }

    public void show(FragmentTransaction transaction, String tag, int typeId, int commentedId, int fId, int commentedUserId, RefreshListener listener) {
        show(transaction,tag);
        this.typeId = typeId;
        this.commentedId = commentedId;
        this.fId = fId;
        this.commentedUserId = commentedUserId;
        refreshListener = listener;
    }

    public interface RefreshListener{
        void refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e("dialog-->onResume()");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //显示软键盘
                InputMethodManager inputManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        },200);
    }

    @Override
    public void onDestroyView() {
        Handler mListenersHandler = (Handler)Reflex.getFieldObject(Dialog.class,getDialog(),"mListenersHandler");
        if (mListenersHandler != null){
            mListenersHandler.removeCallbacksAndMessages(null);
        }
        refreshListener = null;
        baseActivity = null;
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewsContract.REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            imageCompress(Matisse.obtainPathResult(data).get(0), getContext());
        }
    }

    /**
     * 图片选择
     * @param context
     */
    @SuppressLint("CheckResult")
    public void imageChoose(BaseActivity context) {
        new RxPermissions(context)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Matisse.from(context)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)//是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                                    .countable(true)
                                    .maxSelectable(9)   // 最多选择一张
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, BaseAppUtils.getFileprovider()))
                                    //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7
                                    // .0系统 必须设置
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine4())
                                    .forResult(NewsContract.REQUEST_CODE_CHOOSE);
                        } else {
                            ToastUtils.info(context.getApplicationContext(), "请给与相应权限");
                        }
                    }
                });
    }

    /**
     * 图片压缩
     */
    public void imageCompress(String path, Context context) {
        LoadingDialog.getInstance().showProgress(context);
        Luban.with(context.getApplicationContext()).load(path).ignoreBy(100).setTargetDir(FileCacheUtils.getAppImagePath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {
                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                //  压缩开始前调用，可以在方法内启动 loading UI
            }

            @Override
            public void onSuccess(File file) {
                //  压缩成功后调用，返回压缩后的图片文件
                LoadingDialog.getInstance().dismissProgress();
                imagePath = file.getPath();
                ImageLoadUtil.loadImageNoCrash(context.getApplicationContext(), imagePath, commentImage);
                deleteBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                //  当压缩过程出现问题时调用
                LogUtil.e("push-图片压缩失败");
                LoadingDialog.getInstance().dismissProgress();
            }
        }).launch();
    }
}

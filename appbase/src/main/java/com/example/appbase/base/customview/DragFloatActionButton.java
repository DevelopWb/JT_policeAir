//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.appbase.base.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

public class DragFloatActionButton extends android.support.v7.widget.AppCompatImageView {
    private int parentHeight;
    private int parentWidth;
    Handler handler = new Handler();
    Runnable runnable;
    private int lastX;
    private int lastY;
    private boolean isDrag;
    private ViewGroup parent;

    public DragFloatActionButton(Context context) {
        super(context);
        this.runnable = new NamelessClass_1();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.runnable = new NamelessClass_1();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



        this.runnable = new NamelessClass_1();
    }
    class NamelessClass_1 implements Runnable {
        NamelessClass_1() {
        }

        public void run() {
            DragFloatActionButton.this.setAlpha(0.9F);
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int)event.getRawX();
        int rawY = (int)event.getRawY();
        int  action = event.getAction();
        switch(action & 255) {
            case MotionEvent.ACTION_DOWN:
                this.setAlpha(0.9F);
                this.setPressed(true);
                this.isDrag = false;
                this.getParent().requestDisallowInterceptTouchEvent(true);
                this.lastX = rawX;
                this.lastY = rawY;
                if (this.getParent() != null) {
                    this.parent = (ViewGroup)this.getParent();
                    this.parentHeight = this.parent.getHeight();
                    this.parentWidth = this.parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    this.setPressed(false);
                    this.moveHide(rawX);
                } else {
                    this.myRunable();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ((double)this.parentHeight > 0.2D && (double)this.parentWidth > 0.2D) {
                    this.isDrag = true;
                    this.setAlpha(0.9F);
                    int dx = rawX - this.lastX;
                    int dy = rawY - this.lastY;
                    int distance = (int)Math.sqrt((double)(dx * dx + dy * dy));
                    if (distance == 0) {
                        this.isDrag = false;
                    } else {
                        float x = this.getX() + (float)dx;
                        float y = this.getY() + (float)dy;
                        x = x < 0.0F ? 0.0F : (x > (float)(this.parentWidth - this.getWidth()) ? (float)(this.parentWidth - this.getWidth()) : x);
                        y = this.getY() < 0.0F ? 0.0F : (this.getY() + (float)this.getHeight() > (float)this.parentHeight ? (float)(this.parentHeight - this.getHeight()) : y);
                        this.setX(x);
                        this.setY(y);
                        this.lastX = rawX;
                        this.lastY = rawY;
                        Log.i("aa", "isDrag=" + this.isDrag + "getX=" + this.getX() + ";getY=" + this.getY() + ";parentWidth=" + this.parentWidth);
                    }
                } else {
                    this.isDrag = false;
                }
        }

        return isDrag || super.onTouchEvent(event);
    }

    private boolean isNotDrag() {
        return !this.isDrag && (this.getX() == 0.0F || this.getX() == (float)(this.parentWidth - this.getWidth()));
    }

    private void moveHide(int rawX) {
        if (rawX >= this.parentWidth / 2) {
            this.animate().setInterpolator(new DecelerateInterpolator()).setDuration(500L).xBy((float)(this.parentWidth - this.getWidth()) - this.getX()).start();
            this.myRunable();
        } else {
            ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", new float[]{this.getX(), 0.0F});
            oa.setInterpolator(new DecelerateInterpolator());
            oa.setDuration(500L);
            oa.start();
            this.myRunable();
        }

    }

    private void myRunable() {
        this.handler.removeCallbacks(this.runnable);
        this.handler.postDelayed(this.runnable, 2000L);
    }
}

package com.juntai.wisdom.policeAir.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * author:wong
 * Date: 2019/7/16
 * Description:
 */
public class ImageUtil {
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null || foreground == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap
                .createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 4 - 7, null);
        canvas.save();
        canvas.restore();
        return newmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)throws Exception {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Bitmap zoomImg(Bitmap bitmap){
        int width = 80;
        int height = 80;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
// 设置想要的大小
        int newWidth = 150;
        int newHeight = 150;
// 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
// 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
// 得到新的图片
        Bitmap bmhead = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bmhead;
    }
}

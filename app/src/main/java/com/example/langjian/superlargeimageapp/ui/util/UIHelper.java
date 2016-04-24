package com.example.langjian.superlargeimageapp.ui.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Description
 * Created by langjian on 2016/4/21.
 * Version
 */
public class UIHelper {

    private static WindowManager windowManager;

    public static DisplayMetrics getDisplayManager(Activity context) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        return displaymetrics;
    }

    public static WindowManager getWindowManager() {
        return windowManager;
    }

    // 将Bitmap转换成InputStream
    public static InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将Bitmap转换成InputStream
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将InputStream转换成Bitmap
    public Bitmap InputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    // Drawable转换成InputStream
    public static InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = drawable2Bitmap(d);
        return Bitmap2InputStream(bitmap);
    }

    // Drawable转换成Bitmap
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static float getScreenWidth() {
        return getDisplayManager((Activity) ApplicationUtil.getGlobalContext()).widthPixels;
    }
}

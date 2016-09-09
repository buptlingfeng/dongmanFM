package com.dongman.fm.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.dongman.fm.utils.FMLog;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by liuzhiwei on 15/7/11.
 */
public class ImageUtils {

    private static final String TAG = "ImageUtils";

    /**
     * 异步方法
     * @param context
     * @param url
     * @param imageView
     */
    public static void getImage(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    public static void getImage(Context context, final String url, ImageView imageView, int width, int heigth) {
        Picasso.with(context)
                .load(url)
                .transform(new CropSquareTransformation(width))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        FMLog.i(TAG, "onSuccess : " + url);
                    }

                    @Override
                    public void onError() {
                        FMLog.e(TAG, "onError : " + url == null?"the url is null!": url);
                    }
                });
    }

    /**
     * 同步方法，不要在主线程中调用
     * @param context
     * @param url
     * @return
     */
    public static Drawable getDrawabel(Context context, String url) {
        Bitmap bitmap = getBitmap(context,url);
        if(bitmap != null) {
            return new BitmapDrawable(bitmap);
        } else {
            return null;
        }
    }

    /**
     * 同步方法，不要在主线程中调用
     * @param context
     * @param url
     * @return
     */
    public static Bitmap getBitmap(Context context, String url) {
        try {
            return Picasso.with(context).load(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromResources(Context context, int resId) {
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res, resId);
    }

    public static Bitmap convertBytes2Bimap(byte[] b) {
        if (b.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static byte[] convertBitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap convertDrawable2BitmapSimple(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }
    public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        // 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        return bd;
    }

    public static Bitmap scaleByWidth(Bitmap bm, float targetWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = targetWidth / width;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scale,scale);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class CropSquareTransformation implements Transformation {
        private float targetWidth;
        public CropSquareTransformation(int targetWidth) {
            this.targetWidth = targetWidth;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            int width = source.getWidth();
            int height = source.getHeight();
            float scale = targetWidth / width;
            // CREATE A MATRIX FOR THE MANIPULATION
            Matrix matrix = new Matrix();
            // RESIZE THE BIT MAP
            matrix.postScale(scale,scale);
            Bitmap resizedBitmap = Bitmap.createBitmap(source, 0, 0, width, height, matrix, false);
            source.recycle();

//            int w = (int) (width * scale);
//            int y = (int) (height * scale);
//
//            Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
//            Bitmap resizedBitmap = Bitmap.createBitmap(w, y, config);
//            source.recycle();

            return resizedBitmap;
        }

        @Override
        public String key() { return "square()"; }
    }
}
package com.dongman.fm.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by liuzhiwei on 16/4/4.
 */
public class UILImageLoader implements ImageLoader {

    private Bitmap.Config mConfig;

    private static UILImageLoader INSANCE = new UILImageLoader();

    public static UILImageLoader getInstance() {
        return INSANCE;
    }

    private UILImageLoader() {
        this(Bitmap.Config.RGB_565);
    }

    private UILImageLoader(Bitmap.Config config) {
        this.mConfig = config;
    }

    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {

        Picasso picasso = Picasso.with(activity);
        RequestCreator creator = picasso.load(new File(path));
        creator.placeholder(defaultDrawable);
        creator.error(defaultDrawable);
        creator.config(mConfig);
        creator.resize(width,height);
        creator.centerCrop();
        creator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        creator.into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}

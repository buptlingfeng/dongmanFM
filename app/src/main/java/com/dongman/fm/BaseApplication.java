package com.dongman.fm;

import android.app.Application;
import android.graphics.Color;

import com.dongman.fm.utils.UILImageLoader;

import java.lang.reflect.Field;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by liuzhiwei on 15/7/9.
 */
public class BaseApplication extends Application {

    private static BaseApplication INSTANCE;

    private String appkey;
    private String channelId;
    private String appVerion;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initGalleryFinal();
    }

    private void initGalleryFinal() {
        int color = this.getResources().getColor(R.color.theme_color);
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(color)
                .setFabNornalColor(color)
                .setFabPressedColor(Color.rgb(0x01, 0x83, 0x93))
                .setCheckSelectedColor(Color.rgb(0x00, 0xac, 0xc1))
                .setCropControlColor(Color.rgb(0x00, 0xac, 0xc1))
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true).build();

        ImageLoader imageloader = UILImageLoader.getInstance();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .setTakePhotoFolder(this.getExternalFilesDir("GalleryFinal/DCIM"))
                .setEditPhotoCacheFolder(this.getExternalFilesDir("GalleryFinal/edittemp"))
                .build();

        GalleryFinal.init(coreConfig);
    }

    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    public String getAppKey() {
        return appkey;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getAppVerion() {
        return appVerion;
    }
}

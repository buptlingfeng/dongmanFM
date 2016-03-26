package com.dongman.fm;

import android.app.Application;

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

package com.dongman.fm.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by liuzhiwei on 15/7/12.
 */
public class DMUtils {


    public static String getBuildVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            String name = pi.versionName;
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}

package com.dongman.fm.utils;

import android.util.Log;

/**
 * Created by liuzhiwei on 15/11/15.
 */
public class FMLog {

    public static void v(String tag, String content) {
        Log.v(tag, content);
    }

    public static void d(String tag, String content) {
        Log.d(tag, content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }

    public static void w(String tag, String content) {
        Log.w(tag, content);
    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

}

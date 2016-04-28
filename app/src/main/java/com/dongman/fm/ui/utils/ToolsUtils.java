package com.dongman.fm.ui.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by liuzhiwei on 16/4/28.
 */
public class ToolsUtils {

    public static int getScreenWidth (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeigth (Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }



}

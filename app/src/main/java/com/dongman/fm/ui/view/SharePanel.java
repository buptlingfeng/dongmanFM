package com.dongman.fm.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dongman.fm.R;

/**
 * Created by liuzhiwei on 16/4/24.
 */
public class SharePanel extends PopupWindow {

    public SharePanel(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.share_panel,null);
        setContentView(view);
    }

    public void show(Activity activity) {

        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.share_panel_animation);
        ColorDrawable colorDrawable = new ColorDrawable(0x401a181a);
        setBackgroundDrawable(colorDrawable);
        showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}

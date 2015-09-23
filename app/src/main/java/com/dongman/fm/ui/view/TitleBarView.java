package com.dongman.fm.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dongman.fm.R;

/**
 * Created by liuzhiwei on 15/7/7.
 */
public class TitleBarView extends RelativeLayout {

    TextView leftTitleBar;
    TextView centerTitleBar;
    TextView rightTitleBar;

    public TitleBarView(Context context) {
        super(context);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_titlebar, this, true);
        leftTitleBar = (TextView) findViewById(R.id.left_title_bar);
        centerTitleBar = (TextView) findViewById(R.id.content_title_bar);
        rightTitleBar = (TextView) findViewById(R.id.right_title_bar);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.TitleBarView);

        leftTitleBar.setBackgroundResource(a.getResourceId(R.styleable.TitleBarView_left_title_background, 0));
        rightTitleBar.setBackgroundResource(a.getResourceId(R.styleable.TitleBarView_right_title_background, 0));

        a.recycle();
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTitleContent(String content) {
        if(content != null) {
            centerTitleBar.setText(content);
        }
    }

    public void setLeftButtonBackground(Drawable drawable) {
        if(drawable != null) {
            leftTitleBar.setBackground(drawable);
        }
    }

    public void setLeftButtonClickListener(OnClickListener listener) {
        if(listener != null) {
            leftTitleBar.setOnClickListener(listener);
        }
    }

    public void setRightButtonBackground(Drawable drawable) {
        if(drawable != null) {
            rightTitleBar.setBackground(drawable);
        }
    }

    public void setRightButtonListener(OnClickListener listener) {
        if(listener != null) {
            rightTitleBar.setOnClickListener(listener);
        }
    }



}

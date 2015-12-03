package com.dongman.fm.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongman.fm.R;


/**
 * Created by liuzhiwei on 15/11/14.
 */
public class NavigationView extends LinearLayout {

    private TextView title;
    private ImageView imageView;
    private int normal;
    private int pressed;
    private boolean isActive = false;

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_navigation, this, true);
        title = (TextView) findViewById(R.id.nav_title);
        imageView = (ImageView) findViewById(R.id.nav_imageView);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Nav);

        if(a.hasValue(R.styleable.Nav_nav_title)){
            int id = a.getResourceId(R.styleable.Nav_nav_title, -1);
            if(id != -1)
                title.setText(context.getString(id));
        }

        if(a.hasValue(R.styleable.Nav_normal_drawable)) {
            int id = a.getResourceId(R.styleable.Nav_normal_drawable, -1);
            normal = id;
            if(id != -1) {
                imageView.setBackgroundResource(normal);
            }
        }

        if(a.hasValue(R.styleable.Nav_pressed_drawable)) {
            int id = a.getResourceId(R.styleable.Nav_pressed_drawable, -1);
            pressed = id;
        }

    }

    public void isSelected(boolean isSelected) {
        if(isSelected && !isActive) {
            isActive = true;
            if(pressed != -1) imageView.setBackgroundResource(pressed);
        } else if(!isSelected && isActive) {
            isActive = false;
            if(normal != -1) imageView.setBackgroundResource(normal);
        }
    }

}

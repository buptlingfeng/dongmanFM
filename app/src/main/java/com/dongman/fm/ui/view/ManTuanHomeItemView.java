package com.dongman.fm.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;

/**
 * Created by liuzhiwei on 16/8/21.
 */
public class ManTuanHomeItemView extends FrameLayout {

    private ImageView imageView;
    private TextView textView;
    private Context context;

    public ManTuanHomeItemView(Context context) {
        this(context, null);
    }

    public ManTuanHomeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ManTuanHomeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.group_index_2,this);
        imageView = (ImageView) view.findViewById(R.id.group_image);
        textView = (TextView) view.findViewById(R.id.group_title);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ManTuanHomeItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.group_index_2,this);
        imageView = (ImageView) view.findViewById(R.id.group_image);
        textView = (TextView) view.findViewById(R.id.group_title);
    }

    public void setImage(String url) {
        ImageUtils.getImage(context, url, imageView);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

}

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
import com.dongman.fm.utils.ToolsUtils;

/**
 * Created by liuzhiwei on 16/8/21.
 */
public class TopicHomeItemView extends FrameLayout {

    private Context context;
    private ImageView imageView;
    private TextView textView;

    public TopicHomeItemView(Context context) {
        this(context, null);
    }

    public TopicHomeItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopicHomeItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_subject_recommend_item, this);
        imageView = (ImageView) view.findViewById(R.id.topic_imageview);
        textView = (TextView) view.findViewById(R.id.topic_title);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TopicHomeItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_subject_recommend_item, this);
        imageView = (ImageView) view.findViewById(R.id.topic_imageview);
        textView = (TextView) view.findViewById(R.id.topic_title);
    }

    public void setImage(String url) {
        ImageUtils.getImage(context, url, imageView, ToolsUtils.getScreenWidth(context), 0);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void setTitle(String title) {
        textView.setText(title);
    }

}

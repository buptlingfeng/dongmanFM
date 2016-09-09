package com.dongman.fm.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
public class BannerView extends FrameLayout {

    private Context context;

    private ImageView imageView;
    private TextView textView;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_view, this);
        imageView = (ImageView) view.findViewById(R.id.banner_imageview);
        textView = (TextView) view.findViewById(R.id.banner_des);
        this.context = context;
    }

    public void setImage(String url) {
        ImageUtils.getImage(context, url, imageView, ToolsUtils.getScreenWidth(context), 0);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    public void setDescription(String des) {
        textView.setText(des);
    }

}

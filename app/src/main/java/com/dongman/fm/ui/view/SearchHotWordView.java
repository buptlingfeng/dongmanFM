package com.dongman.fm.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dongman.fm.R;

/**
 * Created by liuzhiwei on 16/8/21.
 */
public class SearchHotWordView extends FrameLayout {

    private Context context;

    private TextView textView;

    public SearchHotWordView(Context context) {
        this(context, null);
    }

    public SearchHotWordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchHotWordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_keyword_recommend_item, this);
        textView = (TextView) view.findViewById(R.id.search_keyword);
    }

    public void setTextView(String text) {
        textView.setText(text);
    }
}

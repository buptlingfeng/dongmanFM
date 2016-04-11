package com.dongman.fm.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/4/10.
 */
public class ArticalEditActivity extends BaseActivity {

    private static final String TAG = ArticalEditActivity.class.getSimpleName();

    private EditText mTitle;
    private EditText mContent;

    private View mCancel;
    private View mPublish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_title, new LinearLayout(this), false);
        actionBar.setCustomView(actionbarLayout);
        mCancel = actionbarLayout.findViewById(R.id.cancel_action);
        mPublish = actionbarLayout.findViewById(R.id.publish_action);
        initView();
    }

    private void initView() {
        mTitle = (EditText) findViewById(R.id.title);
        mContent = (EditText) findViewById(R.id.content);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.cancel_action:
                        ArticalEditActivity.this.finish();
                        break;
                    case R.id.publish_action:

                        String title = mTitle.getText().toString();
                        String content = mContent.getText().toString();
                        FMLog.d(TAG, "标题为：" + title);
                        FMLog.d(TAG, "内容为：" + content);
                        ArticalEditActivity.this.finish();
                        break;
                }
            }
        };

        mCancel.setOnClickListener(listener);
        mPublish.setOnClickListener(listener);

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager)mTitle.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mTitle, 0);
            }
        }, 300);
    }

}

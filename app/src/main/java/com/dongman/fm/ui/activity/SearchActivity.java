package com.dongman.fm.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dongman.fm.BaseActivity;
import com.dongman.fm.R;

/**
 * Created by liuzhiwei on 15/8/2.
 */
public class SearchActivity extends BaseActivity {

    private static final int INVORK_KEYBOARD = 1;

    private Handler mHandler;
    private EditText mInputEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INVORK_KEYBOARD:
                        InputMethodManager inputManager = (InputMethodManager)mInputEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(mInputEdit, 0);
                        break;
                    default:
                        break;
                }
            }
        };

        mInputEdit = (EditText) findViewById(R.id.search_input);
        mInputEdit.setFocusable(true);
        mInputEdit.setFocusableInTouchMode(true);
        mInputEdit.requestFocus();
        mHandler.sendEmptyMessageDelayed(INVORK_KEYBOARD,300);

    }
}

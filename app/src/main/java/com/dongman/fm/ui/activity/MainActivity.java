package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.dongman.fm.R;
import com.dongman.fm.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.dongman.fm.detail");
                Bundle bundle = new Bundle();
                bundle.putString("anime_url", "http://www.dongman.fm/subject/detail_m/13578");
                bundle.putString("anime_title", "今天开始是魔王");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        this.getResources().getIdentifier("","","");
    }

    private void init() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        int taskid = this.getTaskId();
        Log.i(TAG, "MainActivity task id is : " + taskid);
    }
}

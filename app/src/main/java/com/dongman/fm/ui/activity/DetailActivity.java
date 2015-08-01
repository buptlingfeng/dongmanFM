package com.dongman.fm.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.BaseActivity;
import com.dongman.fm.ui.fragment.DetailFragment;
import com.dongman.fm.ui.view.TitleBarView;

/**
 * Created by liuzhiwei on 15/6/10.
 */
public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private static final String ANIME_URL = "anime_url";
    private static final String ANIME_TITLE = "anime_title";

    private String mURL = "";
    private String mTitle = "";

    private TitleBarView mTitleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mURL = bundle.getString(ANIME_URL);
        mTitle = bundle.getString(ANIME_TITLE);
        if(mURL == null) {
            Toast.makeText(this,"url 为空", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {

        mTitleView = (TitleBarView) findViewById(R.id.title_bar_view);
        mTitleView.setTitleContent(mTitle);
        mTitleView.setLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "close the activity");
                DetailActivity.this.finish();
            }
        });
        mTitleView.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "right button is clicked!");
            }
        });

        DetailFragment detailFragment = new DetailFragment(mURL);
        addFragment(detailFragment);
    }

    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}

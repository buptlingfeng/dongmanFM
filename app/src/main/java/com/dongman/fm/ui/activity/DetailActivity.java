package com.dongman.fm.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.DetailFragment;
import com.dongman.fm.ui.view.TitleBarView;

/**
 * Created by liuzhiwei on 15/6/10.
 */
public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private static final String ANIME_ID = "id";
    private static final String ANIME_TITLE = "name";

//    private String mURL = "";
    private String mTitle = "";
    private int mID;

    private TitleBarView mTitleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(ANIME_TITLE);
        mID = bundle.getInt(ANIME_ID);
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

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ANIME_ID, mID);
        detailFragment.setArguments(bundle);
        addFragment(detailFragment,"detailFragment");
    }

}

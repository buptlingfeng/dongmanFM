package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.DetailFragment;
/**
 * Created by liuzhiwei on 15/6/10.
 */
public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private static final String ANIME_ID = "id";
    private static final String ANIME_TITLE = "name";

//    private String mURL = "";
    private String mTitle = "";
    private String mID;

//    private TitleBarView mTitleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        mTitle = bundle.getString(ANIME_TITLE);
        mID = bundle.getString(ANIME_ID);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void initView() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mTitle);

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ANIME_ID, mID);
        detailFragment.setArguments(bundle);
        addFragment(detailFragment,"detailFragment");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

}

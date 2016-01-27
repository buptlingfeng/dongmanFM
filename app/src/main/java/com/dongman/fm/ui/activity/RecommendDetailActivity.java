package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.RecommendDetailFragment;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/12/13.
 */
public class RecommendDetailActivity extends BaseActivity {

    private static final String TAG = RecommendDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail_activity);
        RecommendDetailFragment detailFragment = new RecommendDetailFragment();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("info");
        detailFragment.setArguments(bundle);
        addFragment(detailFragment, "detail");
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

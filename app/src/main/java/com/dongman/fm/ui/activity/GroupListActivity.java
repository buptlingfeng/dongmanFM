package com.dongman.fm.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.CommunityGroupFragment;

/**
 * Created by liuzhiwei on 16/4/10.
 */
public class GroupListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail_activity);
        CommunityGroupFragment fragment = new CommunityGroupFragment();
        addFragment(fragment, "group_list");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home) {
            onBackPressed();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.group_detail_menu, menu);
        return true;
    }
}

package com.dongman.fm.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.CommentListFragment;

/**
 * Created by liuzhiwei on 16/7/31.
 */
public class CommentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_detail_activity);
        CommentListFragment fragment = new CommentListFragment();
        addFragment(fragment, "comment_list");
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
        return true;
    }

}

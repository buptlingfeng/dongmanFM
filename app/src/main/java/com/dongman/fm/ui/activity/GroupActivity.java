package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.DetailFragment;
import com.dongman.fm.ui.fragment.GroupDetailFragment;
import com.dongman.fm.ui.fragment.GroupListFragment;

/**
 * Created by liuzhiwei on 16/1/10.
 */
public class GroupActivity extends BaseActivity {

    private static final int GROUP_LIST   = 1;
    private static final int GROUP_DETAIL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail_activity);

        Intent intent = getIntent();

        if(intent != null) {
            Bundle bundle = intent.getBundleExtra("data");
            int type;
            Fragment fragment;
//            if(bundle != null) {
//                type = bundle.getInt("type", GROUP_LIST);
//                switch (type) {
//                    case GROUP_LIST:
//                        fragment = new GroupListFragment();
//                        addFragment(fragment, "group_list");
//                        break;
//                    case GROUP_DETAIL:
//                        fragment = new GroupDetailFragment();
//                        addFragment(fragment, "groupDetail");
//                        break;
//                    default:
//                        break;
//                }
//            }
            fragment = new GroupDetailFragment();
            addFragment(fragment, "groupDetail");
        }

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

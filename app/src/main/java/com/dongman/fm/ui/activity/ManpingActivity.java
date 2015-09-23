package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.ManpingDetailFragment;

/**
 * Created by liuzhiwei on 15/8/12.
 */
public class ManpingActivity extends BaseActivity {

    private String mID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manping_activity);
        Intent intent = getIntent();
        mID = intent.getStringExtra("id");
        init();
    }

    private void init() {
        ManpingDetailFragment manpingDetailFragment = new ManpingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",mID);
        manpingDetailFragment.setArguments(bundle);
        addFragment(manpingDetailFragment);
    }

    private void addFragment(Fragment fragment) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}

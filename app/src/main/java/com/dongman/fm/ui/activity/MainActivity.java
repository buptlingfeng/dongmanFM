package com.dongman.fm.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.CommunityFragment;
import com.dongman.fm.ui.fragment.HomePageFragment;
import com.dongman.fm.ui.fragment.RecommendFragment;
import com.dongman.fm.utils.FMLog;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentTabHost mMainNavigation;
    private View mIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        initView(this);
    }

    private void initView(Context context) {

        mMainNavigation = (FragmentTabHost) findViewById(R.id.main_navigation);
        mMainNavigation.setup(this, getSupportFragmentManager(),R.id.fragment_container);

        mIndicator = getIndicatorView("首页", R.layout.main_navigation_item);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("首页").setIndicator(mIndicator), HomePageFragment.class, null);

        mIndicator = getIndicatorView("推荐", R.layout.main_navigation_item);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("推荐").setIndicator(mIndicator), RecommendFragment.class, null);

        mIndicator = getIndicatorView("社区", R.layout.main_navigation_item);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("社区").setIndicator(mIndicator), CommunityFragment.class, null);

        mIndicator = getIndicatorView("消息", R.layout.main_navigation_item);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("消息").setIndicator(mIndicator), HomePageFragment.class, null);

        mIndicator = getIndicatorView("个人", R.layout.main_navigation_item);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("个人").setIndicator(mIndicator), HomePageFragment.class, null);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==  KeyEvent.KEYCODE_BACK) {
            FMLog.v(TAG, "onKeyDown back 键");
        }
        finish();
//        return super.onKeyDown(keyCode, event);
        return true;
    }

    private void addFragment(Fragment fragment, String tag) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment result = fragmentManager.findFragmentByTag(tag);
        if(result != null) {
            transaction.attach(result);
        } else {
            transaction.add(R.id.fragment_container, fragment, tag);
        }
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    private View getIndicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.navigation_title);
        tv.setText(name);
        return v;
    }
}

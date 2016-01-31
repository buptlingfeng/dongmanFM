package com.dongman.fm.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.BackHandledInterface;
import com.dongman.fm.ui.fragment.BaseFragment;
import com.dongman.fm.ui.fragment.CommunityFragment;
import com.dongman.fm.ui.fragment.HomePageFragment;
import com.dongman.fm.ui.fragment.MessageFragment;
import com.dongman.fm.ui.fragment.RecommendDetailFragment;
import com.dongman.fm.ui.fragment.RecommendFragment;
import com.dongman.fm.utils.FMLog;


public class MainActivity extends BaseActivity implements BackHandledInterface{

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentTabHost mMainNavigation;
    private View mHome, mRecommend, mCommmunity, mMessage, mPersonal;

    private BaseFragment mBackHandedFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        initView();
    }

    private void initView() {

        mMainNavigation = (FragmentTabHost) findViewById(R.id.main_navigation);
        mMainNavigation.setup(this, getSupportFragmentManager(),R.id.fragment_container);

        mHome = getIndicatorView("首页", R.layout.main_navigation_item);
        ImageView icon = (ImageView) mHome.findViewById(R.id.navigation_icon);
        icon.setImageResource(R.drawable.icon_home);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("首页").setIndicator(mHome), HomePageFragment.class, null);

        mRecommend = getIndicatorView("推荐", R.layout.main_navigation_item);
        icon = (ImageView) mRecommend.findViewById(R.id.navigation_icon);
        icon.setImageResource(R.drawable.icon_tuijian);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("推荐").setIndicator(mRecommend), RecommendFragment.class, null);

        mCommmunity = getIndicatorView("小组", R.layout.main_navigation_item);
        icon = (ImageView) mCommmunity.findViewById(R.id.navigation_icon);
        icon.setImageResource(R.drawable.icon_shequ);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("小组").setIndicator(mCommmunity), CommunityFragment.class, null);

        mMessage = getIndicatorView("消息", R.layout.main_navigation_item);
        icon = (ImageView) mMessage.findViewById(R.id.navigation_icon);
        icon.setImageResource(R.drawable.icon_xiaoxi);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("消息").setIndicator(mMessage), MessageFragment.class, null);

        mPersonal = getIndicatorView("个人", R.layout.main_navigation_item);
        icon = (ImageView) mPersonal.findViewById(R.id.navigation_icon);
        icon.setImageResource(R.drawable.icon_people);
        mMainNavigation.addTab(mMainNavigation.newTabSpec("个人").setIndicator(mPersonal), HomePageFragment.class, null);

        mMainNavigation.getTabWidget().setDividerDrawable(android.R.color.transparent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getSupportFragmentManager().popBackStack();
//                mMainNavigation.setVisibility(View.VISIBLE);
            }
        }
    }

    private View getIndicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.navigation_title);
        tv.setText(name);
        return v;
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }
}

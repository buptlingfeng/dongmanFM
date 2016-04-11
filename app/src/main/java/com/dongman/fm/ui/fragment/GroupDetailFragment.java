package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.utils.FMLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 16/1/12.
 */
public class GroupDetailFragment extends BaseFragment {

    private static String TAG = GroupDetailFragment.class.getSimpleName();

    private Activity mActivity;
    private ViewPager mViewPager;

    private TextView mNav1, mNav2, mNav3, mNav4;

    public GroupDetailFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.group_detail_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View root){

        List<Fragment> data = new ArrayList<>();
        data.add(new GroupCreationFragment());
        data.add(new GroupClassicFragment());
        data.add(new GroupTalkFragment());
        data.add(new GroupShowFragment());

        mViewPager = (ViewPager) root.findViewById(R.id.main_viewPager);
        CommunityPagerAdapter adapter = new CommunityPagerAdapter(getChildFragmentManager());
        adapter.setData(data);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setOffscreenPageLimit(1);
    }


    public static class CommunityPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mData;

        public CommunityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(List<Fragment> data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }

    public static class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

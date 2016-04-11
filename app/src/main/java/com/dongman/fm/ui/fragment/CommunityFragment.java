package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/12/2.
 */
public class CommunityFragment extends BaseFragment {

    private static final String TAG = CommunityFragment.class.getName();

    private Activity mActivity;
    private ViewPager mViewPager;

    private TextView mNav1, mNav2;

    public CommunityFragment() {
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
        View root = inflater.inflate(R.layout.community_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View root){

        List<Fragment> data = new ArrayList<>();
        data.add(new CommunityHotSpotFragment());
        data.add(new CommunityGroupFragment());

        mViewPager = (ViewPager) root.findViewById(R.id.main_viewPager);
        CommunityPagerAdapter adapter = new CommunityPagerAdapter(getChildFragmentManager());
        adapter.setData(data);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setOffscreenPageLimit(1);
    }


    class CommunityPagerAdapter extends FragmentPagerAdapter {

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

    class PageChangeListener implements ViewPager.OnPageChangeListener {

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

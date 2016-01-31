package com.dongman.fm.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.view.viewpager.indicator.FragmentListPageAdapter;
import com.dongman.fm.ui.view.viewpager.indicator.IndicatorViewPager;
import com.dongman.fm.ui.view.viewpager.indicator.ScrollIndicatorView;
import com.dongman.fm.ui.view.viewpager.indicator.slidebar.ColorBar;
import com.dongman.fm.ui.view.viewpager.indicator.transition.OnTransitionTextListener;
import com.dongman.fm.utils.FMLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/11/15.
 */
public class HomePageFragment extends BaseFragment {

    private static final String TAG = "HomePageFragment";

    private IndicatorViewPager mIndicatorViewPager;
    private ScrollIndicatorView mIndicator;
    private ViewPager mViewPager;
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homepage_fragment_container, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mViewPager = (ViewPager) root.findViewById(R.id.main_viewPager);
        mIndicator = (ScrollIndicatorView) root.findViewById(R.id.main_indicator);
        mIndicator.setScrollBar(new ColorBar(mActivity, Color.RED, 5));

        // 设置滚动监听
        int selectColorId = R.color.red;
        int unSelectColorId = R.color.tab_top_text_1;
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(mActivity, selectColorId, unSelectColorId));
        mIndicator.setSplitAuto(true);//设置自动布局

        mViewPager.setOffscreenPageLimit(2);
        mIndicatorViewPager = new IndicatorViewPager(mIndicator,mViewPager);

        List<Fragment> data = new ArrayList<>();

        data.add(new HomePageFocusFragment());
        data.add(new HomePageSquareFragment());

        List<String> titles = new ArrayList<>();
        titles.add("关注");
        titles.add("广场");

        ContentAdapter adapter = new ContentAdapter(titles, data, mActivity, getChildFragmentManager());
        mIndicatorViewPager.setAdapter(adapter);
    }


    private class ContentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        List<Fragment> mData;
        List<String> mTitles;
        LayoutInflater mInflater;

        public ContentAdapter(List<String> titles, List<Fragment> data, Context context, FragmentManager fragmentManager) {
            super(fragmentManager);
            mData = data;
            mTitles = titles;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(mData == null) {
                return 0;
            }
            return mData.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.main_indicator, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mTitles.get(position));
            textView.setPadding(20, 0, 20, 0);
            FMLog.i(TAG, "getViewForTab : " + position);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return mData.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentListPageAdapter.POSITION_NONE;
        }

    }
}

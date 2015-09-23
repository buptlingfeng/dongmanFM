package com.dongman.fm.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.HomePageFragment;
import com.dongman.fm.ui.fragment.ManpingFragment;
import com.dongman.fm.ui.fragment.SearchFragment;
import com.dongman.fm.ui.fragment.SubjectFragment;
import com.dongman.fm.ui.view.viewpager.indicator.FragmentListPageAdapter;
import com.dongman.fm.ui.view.viewpager.indicator.IndicatorViewPager;
import com.dongman.fm.ui.view.viewpager.indicator.ScrollIndicatorView;
import com.dongman.fm.ui.view.viewpager.indicator.slidebar.ColorBar;
import com.dongman.fm.ui.view.viewpager.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private IndicatorViewPager mIndicatorViewPager;
    private ScrollIndicatorView mIndicator;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        mViewPager = (ViewPager) findViewById(R.id.main_viewPager);
        mIndicator = (ScrollIndicatorView) findViewById(R.id.main_indicator);
        mIndicator.setScrollBar(new ColorBar(this, Color.RED, 5));

        // 设置滚动监听
        int selectColorId = R.color.tab_top_text_2;
        int unSelectColorId = R.color.tab_top_text_1;
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColorId(this, unSelectColorId, unSelectColorId));
        mIndicator.setSplitAuto(true);//设置自动布局

        mViewPager.setOffscreenPageLimit(2);
        mIndicatorViewPager = new IndicatorViewPager(mIndicator,mViewPager);
        mInflater = LayoutInflater.from(this);

        List<Fragment> data = new ArrayList<>();
        HomePageFragment homePageFragment = new HomePageFragment();
        data.add(homePageFragment);
        data.add(new SubjectFragment());
        data.add(new ManpingFragment());
        data.add(new SearchFragment());

        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("主题");
        titles.add("漫评");
        titles.add("搜索");

        mIndicatorViewPager.setAdapter(new ContentAdapter(titles,data, getSupportFragmentManager()));
    }

    private class ContentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        List<Fragment> mData;
        List<String> mTitles;

        public ContentAdapter(List<String> titles, List<Fragment> data, FragmentManager fragmentManager) {
            super(fragmentManager);
            mData = data;
            mTitles = titles;
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
            Log.i(TAG, "getViewForTab");
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Log.i(TAG, "ContentAdapter getFragmentForPage");
            return mData.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentListPageAdapter.POSITION_NONE;
        }

    }
}

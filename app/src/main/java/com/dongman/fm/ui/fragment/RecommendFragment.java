package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dongman.fm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/11/15.
 */
public class RecommendFragment extends BaseFragment {

    private static final String TAG = RecommendFragment.class.getName();

    private TextView mNav1, mNav2, mNav3, mNav4, mNav5;
    private ViewPager mViewPager;
    private EditText mInputEdit;
    private TextView mCurrentTab;

    private Activity mActivity;

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
        View root = inflater.inflate(R.layout.recommend_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {

        NavClickListener listener = new NavClickListener();
        mNav1 = (TextView)root.findViewById(R.id.nav1);
        mNav2 = (TextView)root.findViewById(R.id.nav2);
        mNav3 = (TextView)root.findViewById(R.id.nav3);
        mNav4 = (TextView)root.findViewById(R.id.nav4);
        mNav5 = (TextView)root.findViewById(R.id.nav5);

        mNav1.setOnClickListener(listener);
        mNav2.setOnClickListener(listener);
        mNav3.setOnClickListener(listener);
        mNav4.setOnClickListener(listener);
        mNav5.setOnClickListener(listener);


        mInputEdit = (EditText) root.findViewById(R.id.search_input);
        mInputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == mInputEdit.getId() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.search");
                    mActivity.startActivity(intent);
                }
                return false;
            }
        });


        List<Fragment> data = new ArrayList<>();
        data.add(new RecommendHotFragmet());
        data.add(new TopicFragment());
        data.add(new ManPingFragmet());
        data.add(new RecommendNewsFragmet());
        data.add(new RecommendChartletFragmet());
        mViewPager = (ViewPager) root.findViewById(R.id.main_viewPager);
        RecommendContentAdapter adapter = new RecommendContentAdapter(getChildFragmentManager());
        adapter.setData(data);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        mViewPager.setOffscreenPageLimit(1);
        mCurrentTab = mNav1;
        mNav1.setTextColor(mActivity.getResources().getColor(R.color.red));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menus,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    class NavClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav1:
                    changeStatus(mNav1, 0);
                    break;
                case R.id.nav2:
                    changeStatus(mNav2, 1);
                    break;
                case R.id.nav3:
                    changeStatus(mNav3, 2);
                    break;
                case R.id.nav4:
                    changeStatus(mNav4, 3);
                    break;
                case R.id.nav5:
                    changeStatus(mNav5, 4);
                default:
                    break;
            }
        }
    }

    private void changeStatus(TextView nav, int position) {
        if(mCurrentTab != nav) {
            mCurrentTab.setTextColor(mActivity.getResources().getColor(R.color.nav_default_color));
            nav.setTextColor(mActivity.getResources().getColor(R.color.red));
            mViewPager.setCurrentItem(position);
            mCurrentTab = nav;
        }
    }

    class RecommendContentAdapter extends FragmentPagerAdapter {

        private List<Fragment> mData;

        public RecommendContentAdapter(FragmentManager fm) {
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
            switch (position) {
                case 0:
                    changeStatus(mNav1, 0);
                    break;
                case 1:
                    changeStatus(mNav2, 1);
                    break;
                case 2:
                    changeStatus(mNav3, 2);
                    break;
                case 3:
                    changeStatus(mNav4, 3);
                    break;
                case 4:
                    changeStatus(mNav5, 4);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}

package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.activity.GroupDetailActivity;
import com.dongman.fm.ui.activity.GroupListActivity;
import com.dongman.fm.ui.fragment.adapter.CommunityHotspotAdapter;
import com.dongman.fm.ui.view.Banner;
import com.dongman.fm.utils.FMLog;

import java.util.ArrayList;

/**
 * Created by liuzhiwei on 16/1/28.
 */
public class CommunityHotSpotFragment extends BaseFragment{

    private static final String TAG = CommunityHotSpotFragment.class.getSimpleName();

    private Activity mActivity;

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private CommunityHotspotAdapter mAdater;

    public CommunityHotSpotFragment(){}

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
        View root = inflater.inflate(R.layout.community_hotsport_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
//        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
//        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
//        mRecycleView.setHasFixedSize(true);
//        mRecycleView.setLongClickable(true);
//        mLinearLayoutManager = new LinearLayoutManager(getActivity());
//        mRecycleView.setLayoutManager(mLinearLayoutManager);
//
//        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                FMLog.d(TAG, "onRefresh");
//            }
//        });
//        mAdater = new CommunityHotspotAdapter(mActivity);
//        mRecycleView.setAdapter(mAdater);







        //TODO Banner
        ArrayList<View> viewList = new ArrayList<View>();
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(R.drawable.test7);
        viewList.add(iv);
        iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setImageResource(R.drawable.subject_image);
        viewList.add(iv);
        Banner banner = (Banner) root.findViewById(R.id.banner);
        SimpleAdapter adapter = new SimpleAdapter(viewList);
        banner.setAdapter(adapter);


        //TODO 推荐的番团
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.all_group_indicator:
                        Intent intent1 = new Intent(getActivity(), GroupListActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    default:
                        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                        getActivity().startActivity(intent);
                        break;
                }

            }
        };
        root.findViewById(R.id.recommend_group_1).setOnClickListener(listener);
        root.findViewById(R.id.recommend_group_2).setOnClickListener(listener);
        root.findViewById(R.id.recommend_group_3).setOnClickListener(listener);
        root.findViewById(R.id.recommend_group_4).setOnClickListener(listener);
        root.findViewById(R.id.recommend_group_5).setOnClickListener(listener);
        root.findViewById(R.id.recommend_group_6).setOnClickListener(listener);

        root.findViewById(R.id.all_group_indicator).setOnClickListener(listener);
        //TODO 人气榜


    }


    class SimpleAdapter extends PagerAdapter {

        private ArrayList<View> mViewList = new ArrayList<View>();

        public SimpleAdapter(ArrayList<View> list) {
            mViewList = list;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mViewList.get(position);
            container.removeView(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
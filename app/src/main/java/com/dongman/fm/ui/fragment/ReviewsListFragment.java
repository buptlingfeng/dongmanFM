package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.fragment.adapter.ReviewsListFragmentAdapter;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/12/24.
 */
public class ReviewsListFragment extends BaseFragment {

    private static final String TAG = ReviewsListFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private ActionBar mActionBar;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private ReviewsListFragmentAdapter mAdater;

    private View mBack;

    @Override
    public void onAttach(Activity activity) {
        mActivity = (BaseActivity) activity;
        super.onAttach(activity);
        mActionBar = mActivity.getSupportActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_list_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {
        mActionBar.setTitle("评论信息");
        mBack = root.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLongClickable(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FMLog.d(TAG, "onRefresh");
            }
        });
        mAdater = new ReviewsListFragmentAdapter(mActivity);
        mRecycleView.setAdapter(mAdater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

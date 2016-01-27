package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.adapter.RecommendAdapter;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/12/13.
 * 包括的内容为：Cosplay、漫画
 */
public class RecommendChartletFragmet extends BaseFragment {

    private static final String TAG = RecommendChartletFragmet.class.getSimpleName();

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private RecommendAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FMLog.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.recommend_fragment_hot, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        FMLog.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    private void initView(View root) {

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

        mAdapter = new RecommendAdapter(getActivity());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

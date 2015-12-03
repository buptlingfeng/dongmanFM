package com.dongman.fm.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.BaseFragment;
import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.adapter.HomePageSquareFragmentAdapter;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/11/14.
 */
public class HomePageSquareFragment extends BaseFragment {

    private static final String TAG = HomePageSquareFragment.class.getName();

    private RecyclerView mRecycleView;
    private HomePageSquareFragmentAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.homepage_fragment_square, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLongClickable(true);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
//        mLinearLayoutManager.setOrientation(0);
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FMLog.d(TAG, "onRefresh");
            }
        });

        mAdapter = new HomePageSquareFragmentAdapter(mContext);
        mRecycleView.setAdapter(mAdapter);
    }

}

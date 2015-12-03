package com.dongman.fm.ui.fragment;

import android.content.Context;
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
 * Created by liuzhiwei on 15/11/15.
 */
public class RecommendFragment extends com.dongman.fm.BaseFragment {

    private static final String TAG = RecommendFragment.class.getName();

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private RecommendAdapter mAdapter;
    private View mNav1, mNav2, mNav3, mNav4;

    private Context mContext;
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
        mNav1 = root.findViewById(R.id.nav1);
        mNav2 = root.findViewById(R.id.nav2);
        mNav3 = root.findViewById(R.id.nav3);
        mNav4 = root.findViewById(R.id.nav4);

        mNav1.setOnClickListener(listener);
        mNav2.setOnClickListener(listener);
        mNav3.setOnClickListener(listener);
        mNav4.setOnClickListener(listener);

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

    class NavClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nav1:

                    break;
                case R.id.nav2:

                    break;
                case R.id.nav3:

                    break;
                case R.id.nav4:

                    break;
                default:

                    break;
            }
        }
    }

}

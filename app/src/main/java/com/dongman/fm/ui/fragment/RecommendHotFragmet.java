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
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.RecommendAdapter;
import com.dongman.fm.utils.FMLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuzhiwei on 15/12/13.
 * 展示热门的信息，主打的推荐信息，例如年终盘点等
 */
public class RecommendHotFragmet extends BaseFragment {

    private static final String TAG = RecommendHotFragmet.class.getSimpleName();

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

    private void getData(int page, String type, int size) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("type", type);
        params.put("size", size + "");
        asyncGet(APIConfig.ARTICAL_LIST, params, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }
}

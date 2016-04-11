package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.RecommendAdapter;
import com.dongman.fm.utils.FMLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

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

    private Handler mHandler;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_UI:
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };
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
        getData("cosplay,shouhui");
    }

    private void getData(String type) {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("size", "10");
        params.put("type", type);

        asyncGet(APIConfig.ARTICAL_LIST, params, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "onFailure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());

                    data = data.getJSONObject("data");
                    JSONArray list = null;
                    if (data != null) {
                        list = data.getJSONArray("list");
                    }
                    if(list != null) {
                        mAdapter.setData(list);
                        mHandler.sendEmptyMessage(REFRESH_UI);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

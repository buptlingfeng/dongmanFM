package com.dongman.fm.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.BaseFragment;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.HomePageFragmentAdapter;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhiwei on 15/6/17.
 */
public class HomePageFragmentOld extends BaseFragment {

    private static final String TAG = HomePageFragmentOld.class.getName();
    private static final int DATA_READY = 1;
    private static final int DARA_FAILED = 2;

    private Context mContext;
    private Handler mHandler;

    private EditText mInputEdit;
    private RecyclerView mRecommendContent;
    private LinearLayoutManager mLayoutManager;
    private HomePageFragmentAdapter mRecommendAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    private int mPageNumber = 1;
    private boolean isLoadingMore = false;
    private boolean isPullUpdate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.homepage_fragment_focus, container, false);
        initView(root);
        mContext = this.getActivity();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_READY:
                        mRecommendAdapter.notifyDataSetChanged();
                        if(mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                        break;
                    case DARA_FAILED:
                        Toast.makeText(getActivity(),"数据已经加载完毕",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initView(View root) {
        mInputEdit = (EditText) root.findViewById(R.id.search_input);
        mInputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == mInputEdit.getId() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.search");
                    mContext.startActivity(intent);
                }
                return false;
            }
        });

        mRecommendContent = (RecyclerView) root.findViewById(R.id.recycleview);
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
        mRecommendContent.setHasFixedSize(true);
        mRecommendContent.setLongClickable(true);
//        mRecommendContent.setOrientation(TwoWayLayoutManager.Orientation.VERTICAL);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecommendContent.setLayoutManager(mLayoutManager);
        mRecommendContent.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mRecommendAdapter.getItemCount();
                //lastVisibleItem >= totalItemCount - 1 表示剩下1个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 3 && dy > 0) {
                    if (isLoadingMore) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        getData(mPageNumber++, false);//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = true;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1, true);
                isPullUpdate = false;

            }
        });
        mRecommendAdapter = new HomePageFragmentAdapter(getActivity());
        mRecommendContent.setAdapter(mRecommendAdapter);
        getData(mPageNumber, true);
    }

    private void getData(int pageNum, final boolean isFrist) {
        Map<String, String> params = new HashMap<>();
        params.put("page", Integer.toString(pageNum));
        asyncGet(APIConfig.HOME_DATA_API, params, new IRequestCallBack(){

            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(TAG,"data 获取失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    String isEnd = data.getString("msg");
                    if("success".equals(isEnd)){
                        JSONArray array = data.getJSONArray("list");
                        if(isFrist) {
                            mRecommendAdapter.setData(array);
                            mPageNumber = 2;
                        } else {
                            mRecommendAdapter.addData(array);
                        }
                        mHandler.sendEmptyMessage(DATA_READY);
                    }
                    isLoadingMore = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

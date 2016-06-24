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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.view.CircleImageView;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhiwei on 15/9/8.
 */
public class ManpingFragment extends BaseFragment {

    private static final String TAG = ManpingFragment.class.getSimpleName();

    private static final int DATA_READY  = 1;
    private static final int DARA_FAILED = 2;

    private Context mContext;
    private Handler mHandler;

    private RecyclerView mRecycleView;
    private ManpingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;

    private int mPageNumber = 1;

    private boolean isLoadingMore = false;
    private boolean isPullUpdate = false;


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_manping);
        mContext = getActivity();
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_container);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mLayoutManager = new LinearLayoutManager(mContext);
        mAdapter = new ManpingAdapter(mContext);
        mRecycleView.setAdapter(mAdapter);
        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_READY:
                        mAdapter.notifyDataSetChanged();
                        if(mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                        break;
                    case DARA_FAILED:
                        Toast.makeText(getActivity(), "数据已经加载完毕", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mAdapter.getItemCount();
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
        getData(1,true);
    }

    private void getData(int pageNum, final boolean isFrist) {
        Map<String, String> params = new HashMap<>();
        params.put("page", Integer.toString(pageNum));
        params.put("tag", "review");
        asyncGet(APIConfig.MANPIN_LIST, params, new IRequestCallBack() {

            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(TAG, "data 获取失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    String isEnd = data.getString("msg");
                    if ("success".equals(isEnd)) {
                        JSONArray array = data.getJSONArray("list");
                        if (isFrist) {
                            mAdapter.setData(array);
                            mPageNumber = 2;
                        } else {
                            mAdapter.addData(array);
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


    class ManpingAdapter extends RecyclerView.Adapter<ManpingViewHolder> {

        JSONArray mData;
        Context mContext;
        LayoutInflater mInflater;

        public ManpingAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public ManpingAdapter setData(JSONArray array) {
            mData = array;
            return this;
        }

        public ManpingAdapter addData(JSONArray array) {
            if(array != null && array.length() != 0) {
                for(int i = 0; i < array.length(); i++) {
                    try {
                        mData.put(array.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }

        @Override
        public ManpingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ManpingViewHolder(mInflater.inflate(R.layout.manping_recommend_item,null,false));
        }

        @Override
        public void onBindViewHolder(ManpingViewHolder holder, int position) {
            try {
                holder.bindView(mData.getJSONObject(position));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            if(mData == null) {
                return 0;
            } else {
                return mData.length();
            }
        }
    }

    class ManpingViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView userName;
        CircleImageView avatarImg;
        ImageView animeImg;
        TextView createTime;
        TextView animeName;
        TextView commentCount;
        TextView voteCount;


        public ManpingViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.homepage_recommend_item_abstract);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            animeImg = (ImageView) itemView.findViewById(R.id.homepage_recommend_item_image);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            animeName = (TextView) itemView.findViewById(R.id.anime_name);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);
            voteCount = (TextView) itemView.findViewById(R.id.vote_count);
        }

        public void bindView(final JSONObject obj) {
            try {
                title.setText(obj.getString("title"));
                userName.setText(obj.getString("user_name"));
                ImageUtils.getImage(mContext, obj.getString("avatar_img_url"), avatarImg);
                ImageUtils.getImage(mContext, obj.getString("img_url"),animeImg);
                createTime.setText(obj.getString("create_time"));
                animeName.setText(obj.getString("subject_title"));
                commentCount.setText("评论：" + obj.getString("comments"));
                voteCount.setText("赞：" + obj.getString("votes"));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.dongman.fm.manping");
                        try {
                            intent.putExtra("id", obj.getString("target_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.startActivity(intent);
                    }
                });

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

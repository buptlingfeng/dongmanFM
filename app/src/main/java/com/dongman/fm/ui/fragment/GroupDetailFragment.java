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
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/1/12.
 */
public class GroupDetailFragment extends BaseFragment {

    private static String TAG = GroupDetailFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private Bundle mArguments;

    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private PostsListAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setTitle("一拳超人（小组）");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_list_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {
        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_widget);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLongClickable(true);
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FMLog.d(TAG, "onRefresh");
            }
        });

        mAdapter = new PostsListAdapter();
        mRecycleView.setAdapter(mAdapter);
    }

    class PostsListAdapter extends RecyclerView.Adapter<PostItemViewHolder> {

        LayoutInflater mInflater;

        public PostsListAdapter () {
            mInflater = LayoutInflater.from(mActivity);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public PostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.post_item_layout, null, false);
            return new PostItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PostItemViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    class PostItemViewHolder extends RecyclerView.ViewHolder {

        private View view;

        public PostItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void bindView(int position) {
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    RecommendDetailFragment fragment = new RecommendDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", "http://192.168.11.100/files/index.html");
                    bundle.putString("title","一拳超人为什么这么牛逼");
                    fragment.setArguments(bundle);
                    mActivity.addFragment(fragment, "postContent");
                }
            });
        }

    }

}

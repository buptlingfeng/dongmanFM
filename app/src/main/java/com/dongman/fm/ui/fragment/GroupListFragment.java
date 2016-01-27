package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
 * Created by liuzhiwei on 16/1/10.
 */
public class GroupListFragment extends BaseFragment {

    private static final String TAG = GroupListFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private ActionBar mActionBar;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private GroupListAdapter mAdapter;

    private View mBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        mActionBar = mActivity.getSupportActionBar();
        mActionBar.setTitle("新番讨论区");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        FMLog.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.group_list_fragment, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onResume() {
        mActionBar.setTitle("新番讨论区");
        super.onResume();
    }

    private void initView(View root) {

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

        mAdapter = new GroupListAdapter();
        mRecycleView.setAdapter(mAdapter);
    }

    class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupListItemViewHolder> {

        LayoutInflater mInflater;

        public GroupListAdapter() {
            mInflater = LayoutInflater.from(mActivity);
        }

        @Override
        public GroupListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.group_item, null, false);
            return new GroupListItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupListItemViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return 7;
        }



        class GroupListItemViewHolder extends RecyclerView.ViewHolder {
            View item;

            public GroupListItemViewHolder(View itemView) {
                super(itemView);
                item = itemView;
            }

            public void bindView(int position) {
                item.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        mActivity.addFragment(new GroupDetailFragment(), "groupDetail");
                    }
                });
            }
        }
    }
}

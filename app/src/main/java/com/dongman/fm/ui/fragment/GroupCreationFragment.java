package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/1/28.
 */
public class GroupCreationFragment extends BaseFragment{

    private static final String TAG = GroupCreationFragment.class.getSimpleName();

    private Activity mActivity;

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private ClassicAdapter mAdater;

    public GroupCreationFragment(){}

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.community_group_fragment, container, false);
        initView(root);
        return root;
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
        mAdater = new ClassicAdapter(mActivity);
        mRecycleView.setAdapter(mAdater);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(0,0,5,5));
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int left;
        private int right;
        private int top;
        private int bottom;

        public SpacesItemDecoration(int left, int right, int top, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.top = top;

            // Add top margin only for the first item to avoid double left between items
            if(parent.getChildLayoutPosition(view) == 0)
                outRect.top = top;
        }
    }

    public static class ClassicAdapter extends RecyclerView.Adapter<ClassicAdapter.CommunityViewHolder>{

        private Context mContext;
        private LayoutInflater mInflater;

        public ClassicAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = mInflater.inflate(R.layout.group_index_item, null, false);
            } else {
                view = mInflater.inflate(R.layout.state_recommend_item, null, false);
            }

            return new CommunityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommunityViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        class CommunityViewHolder extends RecyclerView.ViewHolder {

            private View root;
            private int viewType;

            public CommunityViewHolder(View itemView) {
                super(itemView);
                root = itemView;
            }

            public void bindView(int position) {

                root.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.dongman.fm.group");
                        Bundle data = new Bundle();
                        data.putInt("type", 2);
                        intent.putExtra("data", data);
                        mContext.startActivity(intent);
                    }
                });
            }
        }

    }
}

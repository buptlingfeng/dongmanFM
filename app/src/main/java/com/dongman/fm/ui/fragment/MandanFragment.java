package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/1/23.
 */
public class MandanFragment extends BaseFragment {

    private static final String TAG = MandanFragment.class.getSimpleName();

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private MandanListAdapter mAdapter;

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
        View root = inflater.inflate(R.layout.mandan_fragment, container, false);
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

        mAdapter = new MandanListAdapter(getActivity());
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class MandanListAdapter extends RecyclerView.Adapter<MandanListAdapter.RecommendViewHolder> {

        private Activity mActivity;
        private LayoutInflater mInflater;

        public MandanListAdapter(Activity activity) {
            mActivity = activity;
            mInflater = LayoutInflater.from(activity);
        }

        @Override
        public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int id = R.layout.mandan_item;

            View view = mInflater.inflate(id, null, false);
            return new RecommendViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecommendViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemViewType(int position) {
            return position % 6;
        }

        @Override
        public int getItemCount() {
            return 14;
        }



        class RecommendViewHolder extends RecyclerView.ViewHolder {

            View item;

            public RecommendViewHolder(View itemView) {
                super(itemView);
                item = itemView;
            }

            public void bindView(int postion) {
                ImageView topicView = (ImageView)itemView.findViewById(R.id.topic_image);
                if(postion % 2 == 0) {
                    topicView.setImageResource(R.drawable.test5);
                } else {
                    topicView.setImageResource(R.drawable.test6);
                }
//                item.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction("com.dongman.fm.recommend_detail");
//                        Bundle bundle = new Bundle();
//                        bundle.putString("title", "逢真纪");
//                        bundle.putString("url", "http://192.168.11.100/files/index.html");
//                        intent.putExtra("info", bundle);
//                        mActivity.startActivity(intent);
//                    }
//                });
            }
        }
    }

}

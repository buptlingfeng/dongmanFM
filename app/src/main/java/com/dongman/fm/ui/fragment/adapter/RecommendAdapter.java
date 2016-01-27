package com.dongman.fm.ui.fragment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;

/**
 * Created by liuzhiwei
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private static final String TAG = RecommendAdapter.class.getName();

    private Activity mActivity;
    private LayoutInflater mInflater;

    public RecommendAdapter(Activity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = 0;
        switch (viewType) {
            case 5:
                id = R.layout.state_recommend_item;
                break;
            case 4:
                id = R.layout.state_image_item;
                break;
            case 3:
                id = R.layout.state_text_item;
                break;
            case 2:
                id = R.layout.state_image_text_item;
                break;
            case 1:
                id = R.layout.manping_recommend_item;
                break;
            case 0:
                id = R.layout.subject_recommend_item;
                break;
            default:
                break;
        }
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
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.recommend_detail");
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "逢真纪");
                    bundle.putString("url", "http://192.168.11.100/files/index.html");
                    intent.putExtra("info", bundle);
                    mActivity.startActivity(intent);
                }
            });
        }
    }
}

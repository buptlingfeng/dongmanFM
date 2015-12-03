package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {
    private static final String TAG = RecommendAdapter.class.getName();

    private Context mContext;
    private LayoutInflater mInflater;

    public RecommendAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = 0;
        FMLog.d(TAG, "ViewType + : " + viewType);
        switch (viewType) {
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
        return position % 5;
    }

    @Override
    public int getItemCount() {
        return 11;
    }



    class RecommendViewHolder extends RecyclerView.ViewHolder {
        public RecommendViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(int postion) {

        }
    }
}

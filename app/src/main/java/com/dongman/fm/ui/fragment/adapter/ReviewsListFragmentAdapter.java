package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;

/**
 * Created by liuzhiwei on 15/12/26.
 */
public class ReviewsListFragmentAdapter extends RecyclerView.Adapter<ReviewsListFragmentAdapter.ReviewViewHolder>{

    private Context mContext;

    public ReviewsListFragmentAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.review_item, null, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public ReviewViewHolder(View view) {
            super(view);
        }
    }
}

package com.dongman.fm.ui.fragment.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.data.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 16/7/31.
 */
public class BrowsersAdapter extends RecyclerView.Adapter<BrowsersAdapter.BrowserViewHolder>{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<UserInfo> mData;

    public BrowsersAdapter(Activity activity) {
        mContext = activity;
        mInflater = LayoutInflater.from(activity);
        mData = new ArrayList<>();
    }

    @Override
    public BrowsersAdapter.BrowserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.browser_item, null, false);
        return new BrowserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrowserViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class BrowserViewHolder extends RecyclerView.ViewHolder {

        private View itemView;

        public BrowserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bindView(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

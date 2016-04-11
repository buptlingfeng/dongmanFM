package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongman.fm.R;
import com.dongman.fm.ui.view.Banner;

import java.util.ArrayList;

/**
 * Created by liuzhiwei on 15/12/2.
 */
public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;

    public CommunityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.group_item, null, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
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
                    intent.setAction("com.dongman.fm.group.detail");
                    Bundle data = new Bundle();
                    data.putInt("type", 2);
                    intent.putExtra("data", data);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}

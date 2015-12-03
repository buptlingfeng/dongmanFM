package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;

import org.json.JSONObject;

/**
 * Created by liuzhiwei on 15/11/22.
 */
public class HomePageFocusFragmentAdapter extends RecyclerView.Adapter<HomePageFocusFragmentAdapter.FucusViewHolder> {

    private JSONObject mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public HomePageFocusFragmentAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public HomePageFocusFragmentAdapter(JSONObject data) {
        mData = data;
    }

    public void addData() {

    }

    public void setData() {

    }

    @Override
    public FucusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View root = mInflater.inflate(R.layout.state_image_item, null, false);

        return new FucusViewHolder(root);
    }

    @Override
    public void onBindViewHolder(FucusViewHolder holder, int position) {
        //TODO 数据的处理
        holder.bindView(null);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    class FucusViewHolder extends RecyclerView.ViewHolder {

        public FucusViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(JSONObject data) {

        }
    }

}

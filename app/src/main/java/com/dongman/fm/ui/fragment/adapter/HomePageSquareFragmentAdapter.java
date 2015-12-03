package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/11/22.
 */
public class HomePageSquareFragmentAdapter extends RecyclerView.Adapter<HomePageSquareFragmentAdapter.SquareViewHolder> {

    private static final String TAG = HomePageSquareFragmentAdapter.class.getName();

    private Context mContext;
    private LayoutInflater mInflater;

    public HomePageSquareFragmentAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public SquareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = 0;
        FMLog.d(TAG, "ViewType + : " + viewType);
        switch (viewType) {
            case 0:
                id = R.layout.state_image_item;
                break;
            case 1:
                id = R.layout.state_text_item;
                break;
            case 2:
                id = R.layout.state_image_text_item;
                break;
            default:
                break;
        }

        View view = mInflater.inflate(id, null, false);
        return new SquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SquareViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class SquareViewHolder extends RecyclerView.ViewHolder {

        public SquareViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(int positon) {

        }
    }

}

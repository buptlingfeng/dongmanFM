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
public class CommunityHotspotAdapter extends RecyclerView.Adapter<CommunityHotspotAdapter.CommunityViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;

    public CommunityHotspotAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = 0;
        switch (viewType) {
            case 1:
                id = R.layout.community_hot_spot_header;
                break;
            case 2:
                id = R.layout.community_hot_spot_topic_item;
                break;
            default:
                break;
        }

        View view = mInflater.inflate(id, null, false);
        return new CommunityViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class CommunityViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private int viewType;

        public CommunityViewHolder(View itemView, int type) {
            super(itemView);
            root = itemView;
            viewType = type;
            switch (viewType) {
                case 1:
                    ArrayList<View> viewList = new ArrayList<View>();

                    ImageView iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.test7);
                    viewList.add(iv);
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.subject_image);
                    viewList.add(iv);

                    Banner banner = (Banner) itemView.findViewById(R.id.banner);
                    SimpleAdapter adapter = new SimpleAdapter(viewList);
                    banner.setAdapter(adapter);
                    break;
                case 2:

                default:
                    break;
            }
            root = itemView;
        }

        public void bindView(int position) {
            if (viewType == 2 && position == 1) {
                root.findViewById(R.id.index_title).setVisibility(View.VISIBLE);
            }
        }
    }

    class SimpleAdapter extends PagerAdapter {

        private ArrayList<View> mViewList = new ArrayList<View>();

        public SimpleAdapter(ArrayList<View> list) {
            mViewList = list;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mViewList.get(position);
            container.removeView(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

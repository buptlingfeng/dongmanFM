package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
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
        int id = 0;
        switch (viewType) {
            case 1://banner
                id = R.layout.homepage_banner_item;
                break;
            case 2:
                id = R.layout.community_group_container;
                break;
            default:
                break;
        }

        View view = mInflater.inflate(id, null, false);
        return new CommunityViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, int position) {
        holder.bindView(position, getItemViewType(position));
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
            viewType = type;
            switch (viewType) {
                case 1:
                    ArrayList<View> viewList = new ArrayList<View>();

                    ImageView iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.subject_image_1);
                    viewList.add(iv);
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.subject_image);
                    viewList.add(iv);
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.subject_image_1);
                    viewList.add(iv);
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.drawable.subject_image);
                    viewList.add(iv);

                    Banner banner = (Banner) itemView.findViewById(R.id.banner);
                    SimpleAdapter adapter = new SimpleAdapter(viewList);
                    banner.setAdapter(adapter);
                    break;
                default:
                    new CommunityGroupView(itemView);
                    break;
            }
            root = itemView;
        }

        public void bindView(int position, int type) {
            switch (type) {
                case 1:

                    break;
                case 2:

                    break;
                default:
                    break;
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

    class CommunityGroupView {

        private RecyclerView mRecycleView;
        private LinearLayoutManager mLinearLayoutManager;

        public CommunityGroupView(View view) {
            mRecycleView = (RecyclerView) view.findViewById(R.id.group_recycler);
            mLinearLayoutManager = new LinearLayoutManager(mContext);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecycleView.setLayoutManager(mLinearLayoutManager);
            mRecycleView.setAdapter(new CommunityGroupViewAdapter());
        }

    }

    class CommunityGroupViewAdapter extends RecyclerView.Adapter<GroupViewItemViewHolder> {

        @Override
        public GroupViewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.community_group_item, null, false);
            return new GroupViewItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupViewItemViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return 9;
        }
    }

    class GroupViewItemViewHolder extends RecyclerView.ViewHolder {

        public GroupViewItemViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(int positon) {
            // TODO: 增加点击响应
        }
    }
}

package com.dongman.fm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ZoomButtonsController;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.utils.ToolsUtils;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.dongman.fm.utils.ZoomTutorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 16/8/7.
 */
public class StillsActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private StillsAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    private StillsActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_stills);
        initData();
        initView();
    }

    private void initData() {
        mData.add("http://img.dongman.fm/subject/6c391052303b39daa5fc7ac8509cfc2c.png");
        mData.add("http://img.dongman.fm/subject/9d0152466d8b3d46823a6d5fe2b3bc84.jpeg");
        mData.add("http://img.dongman.fm/subject/e263af6940c1a9d6e86b5d7c1f4b06ae.png");
        mData.add("http://img.dongman.fm/subject/64555a27c88b1027bf969347b3ae673c.jpeg");
        mData.add("http://img.dongman.fm/subject/d4269c703321f6de8fc89591faa2359a.jpeg");
        mData.add("http://img.dongman.fm/subject/391c2892e78577ba153d555b97a50d39.png");
        mData.add("http://img.dongman.fm/subject/cc4e9850fb266bbe4839e301a6105bbd.png");
        mData.add("http://img.dongman.fm/subject/709937e35e449ed77720c0717ed0af8b.jpeg");
        mData.add("http://img.dongman.fm/subject/1b20286564e26937bef1bbae520143cd.png");
        mData.add("http://img.dongman.fm/subject/00e8c2c23f48102fef5e5c41797f34c0.jpeg");
        mData.add("http://img.dongman.fm/subject/8e4e1884ded301bc19bde3b8794797a9.jpeg");
        mData.add("http://img.dongman.fm/subject/2805958a4856577075b67ae7a197769d.jpeg");
        mData.add("http://img.dongman.fm/subject/781ec93af457bc443ff3c0801f6452c6.png");
        mData.add("http://img.dongman.fm/subject/90f6c9af27ce1b0b65fcd8150883dd72.jpeg");
        mData.add("http://img.dongman.fm/subject/1a7965824fdeb3605b18822cd661aa85.jpeg");
        mData.add("http://img.dongman.fm/subject/25591b2fc23b76583ed253e7f5dd8147.jpeg");
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mLayoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(10, 10, 10, 10));
        mAdapter = new StillsAdapter(this);
        mAdapter.setData(mData);
        mRecyclerView.setAdapter(mAdapter);
    }


    class StillsAdapter extends RecyclerView.Adapter<StillViewHolder> {
        LayoutInflater inflater;
        List<String> data;
        public StillsAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setData(List<String> strings) {
            data = strings;
        }

        @Override
        public StillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.still_item, null);
            return new StillViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StillViewHolder holder, int position) {
            holder.bindView(data.get(position), position);
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            } else {
                return data.size();
            }
        }
    }

    class StillViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public StillViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.still_img);
        }

        public void bindView(String url, final int position) {
            ImageUtils.getImage(StillsActivity.this, url, imageView, ToolsUtils.getScreenWidth(StillsActivity.this) / 3, -1);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setViewPagerAndZoom(view, position);
                }
            });
        }

        public void setViewPagerAndZoom(View v ,int position) {
            //得到要放大展示的视图界面
            ViewPager expandedView = (ViewPager) mActivity.findViewById(R.id.detail_view);
            //最外层的容器，用来计算
            View containerView = (FrameLayout) mActivity.findViewById(R.id.container);
            //实现放大缩小类，传入当前的容器和要放大展示的对象
            ZoomTutorial mZoomTutorial = new ZoomTutorial(containerView, expandedView);

            ViewPagerAdapter adapter = new ViewPagerAdapter(mActivity, mData, mZoomTutorial);
            expandedView.setAdapter(adapter);
            expandedView.setCurrentItem(position);

            // 通过传入Id来从小图片扩展到大图，开始执行动画
            mZoomTutorial.zoomImageFromThumb(v);
            mZoomTutorial.setOnZoomListener(new ZoomTutorial.OnZoomListener() {

                @Override
                public void onThumbed() {
                    // TODO 自动生成的方法存根
                    System.out.println("现在是-------------------> 小图状态");
                }

                @Override
                public void onExpanded() {
                    // TODO 自动生成的方法存根
                    System.out.println("现在是-------------------> 大图状态");
                }
            });
        }

    }

    public class ViewPagerAdapter extends PagerAdapter {

        private List<String> sDrawables;
        private Context mContext;
        private ZoomTutorial mZoomTutorial;

        public ViewPagerAdapter( Context context , List<String> imgIds,ZoomTutorial zoomTutorial) {
            this.sDrawables = imgIds;
            this.mContext = context;
            this.mZoomTutorial = zoomTutorial;
        }

        @Override
        public int getCount() {
            return sDrawables.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {

            final ImageView imageView = new ImageView(mContext);
            ImageUtils.getImage(mContext, sDrawables.get(position), imageView, ToolsUtils.getScreenWidth(mContext), -1);
//            imageView.setImageResource(sDrawables[position]);
            container.addView(imageView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);

            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mZoomTutorial.closeZoomAnim(position);
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}

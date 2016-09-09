package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.ui.activity.GroupDetailActivity;
import com.dongman.fm.ui.activity.GroupListActivity;
import com.dongman.fm.ui.fragment.adapter.CommunityHotspotAdapter;
import com.dongman.fm.utils.ToolsUtils;
import com.dongman.fm.ui.view.Banner;
import com.dongman.fm.ui.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by liuzhiwei on 16/1/28.
 */
public class CommunityHotSpotFragment extends BaseFragment{

    private static final String TAG = CommunityHotSpotFragment.class.getSimpleName();

    private Activity mActivity;

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private CommunityHotspotAdapter mAdater;

    private RecommendGroupItemEntry index1, index2, index3, index4, index5, index6;

    private XRank mXRank;

    public CommunityHotSpotFragment(){}

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.community_hotsport_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {

        //TODO Banner
        ArrayList<View> viewList = new ArrayList<>();
        ImageView iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        iv.setImageResource(R.drawable.test7);
        ImageUtils.getImage(mContext, "http://img.dongman.fm/public/b94a110dbe68193c8cc067435c702224.jpg", iv, ToolsUtils.getScreenWidth(mContext), 0);
        viewList.add(iv);
        iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        iv.setImageResource(R.drawable.subject_image);
        ImageUtils.getImage(mContext, "http://img.dongman.fm/public/ef6acfa198df5ce68e64d5817f621d8f.jpg", iv, ToolsUtils.getScreenWidth(mContext), 0);
        viewList.add(iv);
        iv = new ImageView(mContext);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        iv.setImageResource(R.drawable.test9);
        ImageUtils.getImage(mContext, "http://img.dongman.fm/public/f9d68537d0939848ed7ebd084f28fb98.jpg", iv, ToolsUtils.getScreenWidth(mContext), 0);
        viewList.add(iv);

        Banner banner = (Banner) root.findViewById(R.id.banner);
        SimpleAdapter adapter = new SimpleAdapter(viewList);
        banner.setAdapter(adapter);
        //TODO Recommend
        index1 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_1));
        index2 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_2));
        index3 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_3));
        index4 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_4));
        index5 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_5));
        index6 = new RecommendGroupItemEntry(root.findViewById(R.id.recommend_group_6));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.all_group_indicator:
                        Intent intent1 = new Intent(getActivity(), GroupListActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    default:
                        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                        getActivity().startActivity(intent);
                        break;
                }

            }
        };
        index1.onClick(listener);
        index2.onClick(listener);
        index3.onClick(listener);
        index4.onClick(listener);
        index5.onClick(listener);
        index6.onClick(listener);

        {
            index1.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test11));
            index1.title.setText("甲铁城的卡巴内瑞");
            index2.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test22));
            index2.title.setText("姐姐的妄想日记");
            index3.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test33));
            index3.title.setText("从零开始的异世界生活");
            index4.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test44));
            index4.title.setText("新葫芦兄弟");
            index5.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test55));
            index5.title.setText("一拳超人");
            index6.imageView.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test66));
            index6.title.setText("半田君传说");
        }

        root.findViewById(R.id.all_group_indicator).setOnClickListener(listener);
        //TODO 人气榜

        mXRank = new XRank(root.findViewById(R.id.xrank));
        {
            mXRank.userAvatar.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.test_user));
            mXRank.userName.setText("番米酱");
            mXRank.followCount.setText("132人关注");
            ImageUtils.getImage(mActivity, "http://img.dongman.fm/public/85138bcbc900152275e63f616a4f8338.jpg", mXRank.imageView1, ToolsUtils.getScreenWidth(mActivity)/3, 0);
            ImageUtils.getImage(mActivity, "http://img.dongman.fm/public/ecc2b2ef678b5b3d5309df29cc219853.jpg", mXRank.imageView2, ToolsUtils.getScreenWidth(mActivity)/3, 0);
            ImageUtils.getImage(mActivity, "http://img.dongman.fm/public/acc5ad380e67d6a1c5fc1b28953b86f7.jpg", mXRank.imageView3, ToolsUtils.getScreenWidth(mActivity)/3, 0);
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

    public class RecommendGroupItemEntry{
        View container;
        CircleImageView imageView;
        TextView title;
        TextView count;

        public RecommendGroupItemEntry(View root) {
            container = root;
            imageView = (CircleImageView) root.findViewById(R.id.group_image);
            title = (TextView)root.findViewById(R.id.group_title);
            count = (TextView)root.findViewById(R.id.group_join_count);
        }

        public void update(RecommendGroupItemData data) {

        }

        public void
        onClick(View.OnClickListener listener) {
            container.setOnClickListener(listener);
        }
    }

    public class  RecommendGroupItemData {
        String id;
        String imgUrl;
        String title;
    }

    public class XRank {
        View container;
        CircleImageView userAvatar;
        TextView userName;
        TextView followCount;
        TextView followButton;
        TextView summary;
        ImageView imageView1, imageView2, imageView3;

        XRank(View root) {
            container = root;
            userAvatar = (CircleImageView) root.findViewById(R.id.user_avatar);
            userName = (TextView) root.findViewById(R.id.user_name);
            followCount = (TextView) root.findViewById(R.id.follow_count);
            followButton = (TextView) root.findViewById(R.id.follow_button);
            summary = (TextView) root.findViewById(R.id.xrank_summary);
            imageView1 = (ImageView) root.findViewById(R.id.image1);
            imageView2 = (ImageView) root.findViewById(R.id.image2);
            imageView3 = (ImageView) root.findViewById(R.id.image3);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.follow_button:
//                            followButton.
//                            followButton.
                            break;
                    }
                }
            };

            followButton.setOnClickListener(listener);
        }
    }

}
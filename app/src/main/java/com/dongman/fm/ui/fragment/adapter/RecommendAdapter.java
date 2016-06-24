package com.dongman.fm.ui.fragment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.ui.utils.ToolsUtils;
import com.dongman.fm.utils.FMLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    private static final String TAG = "RecommendAdapter";

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<DataItem> mListData;

    public RecommendAdapter(Activity activity) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mListData = new ArrayList<>();
    }

    public void setData(JSONArray data) {
        mListData.clear();
        addData(data);
    }

    public void addData(JSONArray data) {
        if(data != null && data.length() > 0) {
            for (int i = 0; i < data.length(); i++) {
                try {
                    JSONObject object = data.getJSONObject(i);
                    DataItem item = DataItem.create(object);
                    if (item != null) {
                        mListData.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recommend_list_item, null, false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendViewHolder holder, int position) {
        holder.bindView(position, getDataItem(position));
    }

    public DataItem getDataItem(int position) {
        if (position < mListData.size()) {
            return mListData.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView title;
        TextView summary;
        TextView browseCount;
        TextView createTime;
//        LinearLayout imageViewContainer;
        ImageView imageView1, imageView2, imageView3;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            item = itemView;

            title = (TextView) itemView.findViewById(R.id.article_title);
            summary = (TextView) itemView.findViewById(R.id.article_summary);
//            imageViewContainer = (LinearLayout) itemView.findViewById(R.id.article_image_container);
            imageView1 = (ImageView) itemView.findViewById(R.id.article_image1);
            imageView2 = (ImageView) itemView.findViewById(R.id.article_image2);
            imageView3 = (ImageView) itemView.findViewById(R.id.article_image3);
            createTime = (TextView) itemView.findViewById(R.id.article_create_time);
            browseCount = (TextView) itemView.findViewById(R.id.article_browse_count);

        }

        public void bindView(int postion, final DataItem data) {
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.recommend_detail");
                    Bundle bundle = new Bundle();
//                    bundle.putString("title", "逢真纪");
//                    bundle.putString("url", "http://m.dongman.fm/article/detail/6059");
                    bundle.putString("title", data.title);
                    bundle.putString("id", data.id);
                    intent.putExtra("info", bundle);
                    mActivity.startActivity(intent);
                }
            });

            title.setText(data.title);
            if (data.title.contains("概念")) {
                FMLog.d(TAG, " ");
            }
            summary.setText(data.summary);
            createTime.setText(data.createTime);
            browseCount.setText(data.browseCount + "人看过");
            FMLog.d(TAG, "列表图片信息："+ data.imageUrls.size());
            for (int i = 0; i < 3; i++) {
                String imageUrl;
                ImageView view = imageView1;
                if (data.imageUrls.size() > i) {
                    imageUrl = data.imageUrls.get(i);
                    switch (i) {
                        case 0:
                            view = imageView1;
                            break;
                        case 1:
                            view = imageView2;
                            break;
                        case 2:
                            view = imageView3;
                            break;
                    }
                    ImageUtils.getImage(mActivity, imageUrl, view,
                            ToolsUtils.getScreenWidth(mActivity)/3, ToolsUtils.getScreenHeigth(mActivity));
                } else {
                    break;
                }
            }
//            if (data.imageUrls != null && data.imageUrls.size() > 0) {
//                imageViewContainer.setWeightSum(data.imageUrls.size());
//                for (int i = 0; i < data.imageUrls.size(); i++) {
//                    ImageView imageView = new ImageView(mActivity);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    ImageUtils.getImage(mActivity, data.imageUrls.get(i), imageView);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageViewContainer.getWidth()/data.imageUrls.size(), imageViewContainer.getHeight());
//                    imageViewContainer.addView(imageView,params);
//                }
//                imageViewContainer.requestLayout();
//            }

        }
    }
    public static class DataItem {

        public String title;
        public String summary;
        public List<String> imageUrls;
        public String id;
        public String browseCount;
        public String createTime;

        public static DataItem create(JSONObject data) {
            if(data != null) {
                DataItem result = new DataItem();
                try {
                    result.title = data.getString("title");
                    result.summary = data.getString("content");
                    result.id = data.getString("id");
//                    result.imageUrl = data.getJSONArray("thumbs");
                    JSONArray images = data.getJSONArray("thumbs");
                    if (images != null) {
                        result.imageUrls = new ArrayList<>();
                        for (int i = 0; i < images.length(); i++) {
                            result.imageUrls.add(images.get(i).toString());
                        }
                    }
                    result.browseCount = data.getString("browse_count");
                    result.createTime = data.getString("create_time");
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        }

    }
}
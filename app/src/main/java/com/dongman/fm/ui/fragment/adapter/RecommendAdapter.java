package com.dongman.fm.ui.fragment.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.utils.ToolsUtils;
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
        View view = mInflater.inflate(R.layout.recommend_list_item, parent, false);
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
        ImageView imageView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            item = itemView;

            title = (TextView) itemView.findViewById(R.id.article_title);
            summary = (TextView) itemView.findViewById(R.id.article_summary);
            imageView = (ImageView) itemView.findViewById(R.id.article_image);
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
            for (int i = 0; i < 3; i++) {
                ImageUtils.getImage(mActivity, data.imageUrls, imageView,
                        ToolsUtils.getScreenWidth(mActivity), ToolsUtils.getScreenHeigth(mActivity));
            }

        }
    }
    public static class DataItem {

        public String title;
        public String summary;
        public String imageUrls;
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
                    String imgUrl = data.getString("img_url");
                    result.imageUrls = imgUrl;
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
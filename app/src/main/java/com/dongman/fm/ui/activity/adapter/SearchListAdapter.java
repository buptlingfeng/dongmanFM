package com.dongman.fm.ui.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 15/8/17.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchItemViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mData;

    public SearchListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(JSONArray data) {
        mData = data;
    }

    public void addData(JSONArray array) {

        if(array != null && array.length() > 0) {
            for(int i = 0; i < array.length(); i++) {
                try {
                    mData.put(array.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public SearchItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_list_item, null, false);

        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.SearchItemViewHolder holder, int position) {
        try {
            holder.bindView(mData.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.length();
        } else {
            return 0;
        }
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder{

        ImageView animeImg;
        TextView animeTitle;
        TextView animCategory;
        TextView country;
        TextView playCount;
        JSONObject data;
        LinearLayout rateContainer;
        TextView animeAverage;

        public SearchItemViewHolder(View itemView) {
            super(itemView);
            animeImg = (ImageView) itemView.findViewById(R.id.anime_image);
            animeTitle = (TextView) itemView.findViewById(R.id.anime_name);
            animCategory = (TextView) itemView.findViewById(R.id.anime_genres);
            country = (TextView) itemView.findViewById(R.id.anime_countries);
            playCount = (TextView) itemView.findViewById(R.id.play_count);
            rateContainer = (LinearLayout) itemView.findViewById(R.id.anime_average_star_container);
            animeAverage = (TextView) itemView.findViewById(R.id.anime_average);
        }

        public void bindView(final JSONObject object) {
            data = object;
            try {
                ImageUtils.getImage(mContext, object.getString("img_url"), animeImg);
                final String title = object.getString("title");
                animeTitle.setText(title);

                animCategory.setText("类型：" + object.getString("category"));
                country.setText("国家：" + object.getString("country"));
                playCount.setText("播放：" + object.getString("play_count"));
                double score = object.getDouble("total_score");

                int childCount = rateContainer.getChildCount();
                int starNum = (int) Math.ceil(score / 2);
                if(starNum == 0) {
                    for(int i = 0; i < childCount - 1; i++) {
                        ImageView childView = (ImageView)rateContainer.getChildAt(i);
                        childView.setImageResource(R.drawable.icon_star_grey);
                    }
                } else {
                    for(int i = 0; i < childCount - 2 && i < starNum; i++) {
                        ImageView childView = (ImageView)rateContainer.getChildAt(i);
                        childView.setImageResource(R.drawable.icon_star_yellow);
                    }
                }

                animeAverage.setText(score + "分");

                final String id = object.getString("id");
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.dongman.fm.detail");
                        intent.putExtra("id", id);
                        intent.putExtra("name", title);
                        mContext.startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

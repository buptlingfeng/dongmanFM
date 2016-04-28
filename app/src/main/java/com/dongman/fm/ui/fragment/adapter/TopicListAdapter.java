package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.ui.utils.ToolsUtils;
import com.dongman.fm.utils.FMLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 15/8/6.
 */
public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

    private static final String TAG = TopicListAdapter.class.getName();

    private static final int BANNER  = 1;//标示Banner样式
    private static final int REVIEW = 2;//标示漫评的样式
    private static final int TOPIC = 3;//标示主题的样式
    private static final int MANDAN  = 4;//标示漫单的样式

    private Context mContext;
    private LayoutInflater mInflater;
    private JSONArray mData = new JSONArray();

    public TopicListAdapter(JSONArray array, Context context) {
        mData = array;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public TopicListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public TopicListAdapter setData(JSONArray array) {
        addData(array);
        return this;
    }

    public TopicListAdapter addData(JSONArray array) {
        if(array != null && array.length() != 0) {
            for(int i = 0; i < array.length(); i++) {
                try {
                    JSONObject data = array.getJSONObject(i);
                    String type = data.getString("type");
                    if (type.equals("topic")) {
                        mData.put(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public TopicListAdapter.TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = mInflater.inflate(R.layout.subject_recommend_item, null, false);
        return new TopicViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(TopicListAdapter.TopicViewHolder holder, int position) {
        try {
            holder.bindView(mData.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        } else {
            return mData.length();
        }
    }

    public class TopicViewHolder extends  RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;
        JSONObject data;

        public TopicViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.anime_name);
            imageView = (ImageView) itemView.findViewById(R.id.topic_image);
        }

        public void bindView(final JSONObject obj) {
            try {

                data = obj;
                title.setText(obj.getString("title"));
                ImageUtils.getImage(mContext, obj.getString("img_url"), imageView, 1080, 615);
                final String id = obj.getString("target_id");
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.dongman.fm.subject");
                        intent.putExtra("id", id);
                        mContext.startActivity(intent);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


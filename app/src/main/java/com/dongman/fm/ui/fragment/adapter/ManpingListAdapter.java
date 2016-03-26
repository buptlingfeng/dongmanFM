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
import com.dongman.fm.ui.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by liuzhiwei on 15/8/6.
 */
public class ManpingListAdapter extends RecyclerView.Adapter<ManpingListAdapter.ManpingViewHolder> {

    private static final String TAG = ManpingListAdapter.class.getName();

    private Context mContext;
    private LayoutInflater mInflater;
    private JSONArray mData = new JSONArray();

    public ManpingListAdapter(JSONArray array, Context context) {
        mData = array;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public ManpingListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public ManpingListAdapter setData(JSONArray array) {
        addData(array);
        return this;
    }

    public ManpingListAdapter addData(JSONArray array) {
        if(array != null && array.length() != 0) {
            for(int i = 0; i < array.length(); i++) {
                try {
                    JSONObject data = array.getJSONObject(i);
                    String type = data.getString("type");
                    if (type.equals("review")) {
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
    public ManpingListAdapter.ManpingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.manping_recommend_item,null,false);
        return new ManpingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManpingListAdapter.ManpingViewHolder holder, int position) {
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

    public class ManpingViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView userName;
        CircleImageView avatarImg;
        ImageView animeImg;
        TextView createTime;
        TextView animeName;
        TextView commentCount;
        TextView voteCount;


        public ManpingViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.homepage_recommend_item_abstract);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            animeImg = (ImageView) itemView.findViewById(R.id.homepage_recommend_item_image);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            animeName = (TextView) itemView.findViewById(R.id.anime_name);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);
            voteCount = (TextView) itemView.findViewById(R.id.vote_count);
        }

        public void bindView(final JSONObject obj) {
            try {
                title.setText(obj.getString("title"));
                userName.setText(obj.getString("user_name"));
                ImageUtils.getImage(mContext, obj.getString("avatar_img_url"), avatarImg);
                ImageUtils.getImage(mContext, obj.getString("img_url"),animeImg);
                createTime.setText(obj.getString("create_time"));
                animeName.setText(obj.getString("subject_title"));
                commentCount.setText("评论：" + obj.getString("comments"));
                voteCount.setText("赞：" + obj.getString("votes"));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.dongman.fm.manping");
                        try {
                            intent.putExtra("id", obj.getString("target_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.startActivity(intent);
                    }
                });

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

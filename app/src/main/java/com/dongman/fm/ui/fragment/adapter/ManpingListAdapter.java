package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.ui.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuzhiwei on 15/8/6.
 */
public class ManpingListAdapter extends RecyclerView.Adapter<ManpingListAdapter.ManpingViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ReviewData> mData = new ArrayList<>();

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
                    ReviewData reviewData = ReviewData.create(data);
                    if (reviewData != null) {
                        mData.add(reviewData);
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
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        } else {
            return mData.size();
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
        TextView summary;


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
            summary = (TextView) itemView.findViewById(R.id.manping_summary);
        }

        public void bindView(final ReviewData data) {
            title.setText(data.title);
            userName.setText(data.userName);
            ImageUtils.getImage(mContext,data.avatarImgUrl, avatarImg);
            ImageUtils.getImage(mContext, data.imgUrl, animeImg);
            createTime.setText(data.createTime);
            animeName.setText(data.subjectTitle);
            commentCount.setText("评论：" + data.comments);
            voteCount.setText("赞：" + data.votes);
            summary.setText(data.content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.manping");
                    intent.putExtra("id", data.id);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    static class ReviewData {

        String id;
        String title;
        String createTime;
        String userId;
        String userName;
        String avatarImgUrl;
        String imgUrl;
        String votes;
        String comments;
        String subjectTitle;
        String totalScore;
        String subjectId;
        String content;
        String tag;
        String userUrl;

        public static ReviewData create(JSONObject data) {
            ReviewData result = null;
            if (data != null) {
                try {
                    result = new ReviewData();
                    result.id = data.getString("id");
                    result.title = data.getString("title");
                    result.createTime = data.getString("create_time");
                    result.userId = data.getString("user_id");
                    result.userName = data.getString("user_name");
                    result.avatarImgUrl = data.getString("avatar_img_url");
                    result.imgUrl = data.getString("img_url");
                    result.votes = data.getString("votes");
                    result.comments = data.getString("comments");
                    result.subjectTitle = data.getString("subject_title");
                    result.totalScore = data.getString("total_score");
                    result.subjectId = data.getString("subject_id");
                    result.content = data.getString("content");
                    result.tag = data.getString("tag");
                    result.userUrl = data.getString("user_url");
                } catch (JSONException e) {

                }
            }

            return result;
        }
    }
}

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

import java.util.Random;


/**
 * Created by liuzhiwei on 15/8/6.
 */
public class HomePageFragmentAdapter extends RecyclerView.Adapter<HomePageFragmentAdapter.BaseViewHolder> {

    private static final String TAG = HomePageFragmentAdapter.class.getName();

    private static final int BANNER  = 1;//标示Banner样式
    private static final int REVIEW = 2;//标示漫评的样式
    private static final int TOPIC = 3;//标示主题的样式
    private static final int MANDAN  = 4;//标示漫单的样式

    private Context mContext;
    private LayoutInflater mInflater;
    private JSONArray mData = new JSONArray();

    public HomePageFragmentAdapter(JSONArray array, Context context) {
        mData = array;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public HomePageFragmentAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public HomePageFragmentAdapter setData(JSONArray array) {
        mData = array;
        return this;
    }

    public HomePageFragmentAdapter addData(JSONArray array) {
        if(array != null && array.length() != 0) {
            for(int i = 0; i < array.length(); i++) {
                try {
                    mData.put(array.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    @Override
    public HomePageFragmentAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case BANNER:
                break;
            case REVIEW:
                final View view = mInflater.inflate(R.layout.manping_recommend_item,null,false);
                return new ReviewViewHolder(view,HomePageType.REVIEW);
            case TOPIC:
                View view1 = mInflater.inflate(R.layout.subject_recommend_item, null, false);
                return new TopicViewHolder(view1,HomePageType.TOPIC);
            case MANDAN:
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(HomePageFragmentAdapter.BaseViewHolder holder, int position) {
        try {
            holder.bindView(mData.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取View的类型，也就是支持一个List中存在多个样式的view
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        try {
            if(mData == null) {
                return super.getItemViewType(position);
            }
            JSONObject object = mData.getJSONObject(position);
            String type = object.getString("type");
            int index = HomePageType.valueOf(type.toUpperCase()).index;
            Log.i(TAG, "position is : " + position +"  type is : " + type + " and the index is : " + index);
            return index;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        } else {
            return mData.length();
        }
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public HomePageType mType;
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public BaseViewHolder(View itemView, HomePageType type) {
            this(itemView);
            mType = type;
        }

        public abstract void bindView(JSONObject obj);
    }

    public class ReviewViewHolder extends BaseViewHolder {

        TextView title;
        TextView userName;
        CircleImageView avatarImg;
        ImageView animeImg;
        TextView createTime;
        TextView animeName;
        TextView commentCount;
        TextView voteCount;


        public ReviewViewHolder(View itemView, HomePageType type) {
            super(itemView, type);
            title = (TextView) itemView.findViewById(R.id.homepage_recommend_item_abstract);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            avatarImg = (CircleImageView) itemView.findViewById(R.id.avatar_img);
            animeImg = (ImageView) itemView.findViewById(R.id.homepage_recommend_item_image);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            animeName = (TextView) itemView.findViewById(R.id.anime_name);
            commentCount = (TextView) itemView.findViewById(R.id.comment_count);
            voteCount = (TextView) itemView.findViewById(R.id.vote_count);
        }

        @Override
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
    static int index = 1;
    public class TopicViewHolder extends BaseViewHolder {

        TextView title;
        ImageView imageView;
        JSONObject data;

        public TopicViewHolder(View itemView, HomePageType type) {
            super(itemView, type);
            title = (TextView) itemView.findViewById(R.id.anime_name);
            imageView = (ImageView) itemView.findViewById(R.id.topic_image);
        }

        @Override
        public void bindView(final JSONObject obj) {
            try {
                index ++;
                data = obj;
                title.setText(obj.getString("title"));
                ImageUtils.getImage(mContext, obj.getString("img_url"), imageView);
                final String id = obj.getString("target_id");
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(index % 2 == 0) {
                            Intent intent = new Intent();
                            intent.setAction("com.dongman.fm.theme");
                            mContext.startActivity(intent);
                            return;
                        }
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

    public enum HomePageType{
        BANNER(1,"banner"), REVIEW(2, "review"), TOPIC(3,"topic"), MANDAN(4,"mandan");
        int index;
        String name;
        HomePageType(int i, String t) {
            index = i;
            name = t;
        }
    }

}

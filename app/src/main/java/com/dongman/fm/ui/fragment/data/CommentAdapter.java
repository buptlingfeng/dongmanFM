package com.dongman.fm.ui.fragment.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.ui.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 15/7/31.
 */
public class CommentAdapter extends BaseAdapter{

    private JSONArray mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public void setData(JSONArray data) {
        mData = data;
    }

    public CommentAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new JSONArray();
        for (int i = 0; i < 3; i++) {
            mData.put(createData());
        }
    }

    @Override
    public int getCount() {
        if(mData != null) {
            return mData.length();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mData != null && mData.length() > position) {
            try {
                return mData.get(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.detail_review_item, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (CircleImageView) convertView.findViewById(R.id.comment_avatar);
            viewHolder.content = (TextView) convertView.findViewById(R.id.comment_content);
            viewHolder.date = (TextView) convertView.findViewById(R.id.comment_date);
            viewHolder.nick = (TextView) convertView.findViewById(R.id.comment_nick);
            JSONObject data;
            try {
                data = mData.getJSONObject(position);
                AnimeComment animeComment = convertData(data);
                viewHolder.content.setText(animeComment.content);
                viewHolder.nick.setText(animeComment.userNick);
                viewHolder.date.setText(animeComment.date);
                ImageUtils.getImage(mContext,animeComment.avatar,viewHolder.avatar);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
         }
        return convertView;
    }

    public AnimeComment convertData(JSONObject object) {
        if (object == null) {
            return null;
        }
        AnimeComment commentData = new AnimeComment();
        try {
//            commentData.animeID = object.getString("subject_id");
//            commentData.avatar = object.getString("avatar");
//            commentData.commentId = object.getString("id");
            commentData.avatar = object.getString("avatar");
            commentData.content = object.getString("content");
            commentData.userNick = object.getString("userNick");
            commentData.date = object.getString("date");
            return commentData;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commentData;
    }

    public static JSONObject createData() {
        try {
            JSONObject object = new JSONObject();
            object.put("avatar","http://animes-public.stor.sinaapp.com/Uploads/556aeb1ee5099.jpg");
            object.put("userNick", "小明");
            object.put("content","观看请注意：前方高能！！！！");
            object.put("date","2015-07-31");

            return object;
        } catch (JSONException e) {
            return null;
        }

    }

    public class ViewHolder {
        TextView nick;
        TextView content;
        CircleImageView avatar;
        TextView favour;
        TextView date;
    }

    public class AnimeComment {

        private String animeID;
        private String userNick;
        private String commentId;
        private String content;
        private String avatar;
        private int replyCount;
        private int favourCount;
        private String date;

    }

}

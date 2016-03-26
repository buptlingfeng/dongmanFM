package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.ui.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 16/3/26.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CommentData> mData;

    public CommentsAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    public void setData(List<CommentData> data) {
        mData = data;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_item, null, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentNick;
        CircleImageView commentAvatar;
        TextView commentDate;
        TextView commentContent;

        View itemView;
        public CommentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            commentAvatar = (CircleImageView) itemView.findViewById(R.id.comment_avatar);
            commentDate   = (TextView) itemView.findViewById(R.id.comment_date);
            commentContent = (TextView) itemView.findViewById(R.id.comment_content);
            commentNick   = (TextView) itemView.findViewById(R.id.comment_nick);
        }

        public void bindView(int position) {
            if (position < mData.size()) {
                CommentData data = mData.get(position);
                commentNick.setText(data.userName);
                commentDate.setText(data.createTime);
                commentContent.setText(data.content);
                ImageUtils.getImage(mContext, data.avatarUrl, commentAvatar);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 去用户个人页面
                    }
                });
            }
        }
    }
}

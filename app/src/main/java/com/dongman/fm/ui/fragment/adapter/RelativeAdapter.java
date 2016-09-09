package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.data.ReviewInfo;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.ui.activity.BrowserActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuzhiwei on 16/3/2.
 */
public class RelativeAdapter extends RecyclerView.Adapter<RelativeAdapter.RelativeViewHolder>{

    public static final int MANTIE      = 1;
    public static final int ANIMES      = 2;
    public static final int TOPIC       = 3;
    public static final int MANPING     = 4;
    public static final int PLAY        = 5;
    public static final int BROWSER     = 6;

    private LayoutInflater mInflater;
    private List mData;
    private Context mActivity;

    private int mType;

    public RelativeAdapter(Context activity, int type) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mData = new ArrayList<>();
        mType = type;
    }

    public void setData(List data) {
        mData = data;
    }

    public void addData(List data) {
        if (data != null) {
            Iterator iterator = data.iterator();
            while (iterator.hasNext()) {
                mData.add(iterator.next());
            }
        }
    }

    @Override
    public RelativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        switch (mType) {
            case ANIMES:
                layoutId = R.layout.anime_item;
                break;
            case MANTIE:
                layoutId = R.layout.mantie_item;
                break;
            case MANPING:
                layoutId = R.layout.manping_item;
                break;
            case PLAY:
                layoutId = R.layout.play_button;
                break;
            default:
                layoutId = R.layout.mantie_item;
                break;
        }
        View view = mInflater.inflate(layoutId, null, false);
        return new RelativeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelativeViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class RelativeViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        public RelativeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bindView(int position) {
            if (position < mData.size()) {
                final Object temData = mData.get(position);
                switch (mType) {
                    case ANIMES:
                        final RelativeRecommend data = (RelativeRecommend) temData;
                        TextView title = (TextView) itemView.findViewById(R.id.anime_title);
                        ImageView imageView = (ImageView) itemView.findViewById(R.id.anime_image);

                        title.setText(data.title);
                        ImageUtils.getImage(mActivity, data.imageUrl, imageView);

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setAction("com.dongman.fm.detail");
                                Bundle bundle = new Bundle();
                                bundle.putString("name", data.title);
                                bundle.putString("id", data.id);
                                intent.putExtras(bundle);
                                mActivity.startActivity(intent);
                            }
                        });
                        break;
                    case MANPING:
                        final ReviewInfo reviewInfo = (ReviewInfo) temData;
                        TextView manpingTitle = (TextView) itemView.findViewById(R.id.manping_title);
                        TextView manpingContent = (TextView) itemView.findViewById(R.id.manping_content);

                        manpingTitle.setText(reviewInfo.title);
                        manpingContent.setText(reviewInfo.summary);

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TODO 为什么一定要加这个标志
                                intent.setAction("com.dongman.fm.manping");
                                intent.putExtra("id", reviewInfo.id);
                                mActivity.startActivity(intent);
                            }
                        });
                        break;
                    case PLAY:
                        TextView playCount = (TextView) itemView.findViewById(R.id.play_count);
                        playCount.setText((position + 1) + "");
                        playCount.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mActivity, BrowserActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", "http://www.tudou.com/albumplay/iF2R9jBUhso/0wew3SKHxHg.html");
                                intent.putExtras(bundle);
                                mActivity.startActivity(intent);
                            }
                        });
                        break;
                    default:
                        final RelativeRecommend relativeRecommend = (RelativeRecommend) temData;
                        TextView mantieTitle = (TextView) itemView.findViewById(R.id.mantie_title);
                        ImageView mantieImageView = (ImageView) itemView.findViewById(R.id.mantie_image);

                        mantieTitle.setText(relativeRecommend.title);
                        ImageUtils.getImage(mActivity, relativeRecommend.imageUrl, mantieImageView);

                        if (mType == MANTIE) {
                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction("com.dongman.fm.recommend_detail");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("title", relativeRecommend.title);
                                    bundle.putString("id", relativeRecommend.id);
                                    intent.putExtra("info", bundle);
                                    mActivity.startActivity(intent);
                                }
                            });
                        } else if (mType == TOPIC) {
                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setAction("com.dongman.fm.subject");
                                    intent.putExtra("id", relativeRecommend.id);
                                    mActivity.startActivity(intent);
                                }
                            });
                        }
                        break;
                }
            }
        }
    }

}

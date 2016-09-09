package com.dongman.fm.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.utils.ImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/7/21.
 */
public class DetailRecommendAdapter extends RecyclerView.Adapter<DetailRecommendAdapter.SimpleViewHolder> {

    private List<RecommendData> mDatas;
    private Context mContext;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.detail_anime_title);
            imageView = (ImageView) view.findViewById(R.id.detail_anime_image);
        }
    }

    public DetailRecommendAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    public DetailRecommendAdapter addData(RecommendData data,int index) {
        mDatas.add(index, data);
        notifyItemInserted(index);
        return this;
    }

    public void setData(List<RecommendData> list) {
        mDatas = list;
    }

    public DetailRecommendAdapter removeData(int index) {
        mDatas.remove(index);
        notifyItemRemoved(index);
        return this;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.detail_recommend_view, null, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.title.setText(mDatas.get(position).title);
        ImageUtils.getImage(mContext,mDatas.get(position).imageUrl,holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class RecommendData {
        public String subjectId;
        public String imageUrl;
        public String title;

        public static RecommendData make(JSONObject object) {
            if(object == null)
                return null;
            try {
                RecommendData result = new RecommendData();
                result.subjectId = object.getString("subject_id");
                result.imageUrl = object.getString("img_url");
                result.title = object.getString("title");
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

}

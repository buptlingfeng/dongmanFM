package com.dongman.fm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.view.RecyclerViewHeader;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by liuzhiwei on 15/8/14.
 */
public class SubjectActivity extends BaseActivity {

    private static final String TAG = SubjectActivity.class.getSimpleName();
    private static final int DATA_ARRIVE = 1;
    private static final int DATA_FAILED = 2;

    private RecyclerView mRecycleView;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerViewHeader mHeader;
    private AnimesAdapter mAdapter;

    private Handler mHandler;

    private String mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mID = intent.getStringExtra("id");
        setContentView(R.layout.subject_actvity);
        init();
    }

    private void init() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycleview_container);
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mHeader = RecyclerViewHeader.fromXml(this, R.layout.subject_header_item);
        mHeader.attachTo(mRecycleView);
        mAdapter = new AnimesAdapter(this);
        mRecycleView.setAdapter(mAdapter);

        mRecycleView.addItemDecoration(new SpacesItemDecoration(10, 10, 10, 10));

        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_ARRIVE:
                        JSONObject basic = (JSONObject)msg.obj;
                        createHeader(basic);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case DATA_FAILED:
                        break;
                    default:
                        break;
                }
            }
        };

        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void createHeader(JSONObject object) {
        try {
            TextView subjectTitle = (TextView) mHeader.findViewById(R.id.subject_title);
            TextView createTime   = (TextView) mHeader.findViewById(R.id.create_time);
            TextView subjectTotalAnimes = (TextView) mHeader.findViewById(R.id.subject_total_animes);
            ImageView subjectImg  = (ImageView) mHeader.findViewById(R.id.subject_img);
            TextView expandableText = (TextView) mHeader.findViewById(R.id.expandable_text);

            subjectTitle.setText(object.getString("title"));
            createTime.setText(object.getString("create_time"));
            subjectTotalAnimes.setText("" + object.getInt("subject_count"));
            ImageUtils.getImage(this, object.getString("img_url"), subjectImg);
            expandableText.setText(object.getString("summary"));
            mHeader.requestLayout();
        }catch (Exception e) {
            if(e != null)
                e.printStackTrace();
        }


    }

    private void getData() {

        asyncGet(APIConfig.TOPIC_DETAIL, "id", mID, new IRequestCallBack() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = mHandler.obtainMessage();
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject object = new JSONObject(response.body().string());

                    JSONObject basic = object.getJSONObject("basic");
                    message.obj = basic;

                    if(!object.isNull("subject") ) {
                        JSONArray animes = object.getJSONArray("subject");
                        mAdapter.setData(animes);
                        message.what = DATA_ARRIVE;
                    } else {
                        message.what = DATA_FAILED;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    class AnimesAdapter extends RecyclerView.Adapter<AnimeViewHolder> {

        JSONArray mData;
        Context mContext;
        LayoutInflater mInflater;

        public AnimesAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        public AnimesAdapter setData(JSONArray array) {
            mData = array;
            return this;
        }

        @Override
        public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AnimeViewHolder(mInflater.inflate(R.layout.subject_anime_item,null,false));
        }

        @Override
        public void onBindViewHolder(AnimeViewHolder holder, int position) {
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
    }

    class AnimeViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView  title;
        TextView  score;
        public AnimeViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.anime_image);
            title     = (TextView)  itemView.findViewById(R.id.anime_name);
            score     = (TextView)  itemView.findViewById(R.id.anime_score);
        }

        public void bindView(JSONObject data) {
            try{
                ImageUtils.getImage(SubjectActivity.this, data.getString("img_url"), imageView);
                title.setText(data.getString("title"));
                score.setText(data.getString("total_score") + "分");
            } catch (Exception e){

            }
        }

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int left;
        private int right;
        private int top;
        private int bottom;

        public SpacesItemDecoration(int left, int right, int top, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = left;
            outRect.right = right;
            outRect.bottom = bottom;

            // Add top margin only for the first item to avoid double left between items
            if(parent.getChildLayoutPosition(view) == 0)
                outRect.top = top;
        }
    }

}

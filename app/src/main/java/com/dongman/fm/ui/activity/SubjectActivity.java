package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.data.TopicInfo;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.CommentsAdapter;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.FullyLinearLayoutManager;
import com.dongman.fm.ui.view.SpacesItemDecoration;

import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/8/14.
 */
public class SubjectActivity extends BaseActivity {

    private static final String TAG = SubjectActivity.class.getSimpleName();
    private static final int DATA_ARRIVE = 1;
    private static final int DATA_FAILED = 2;

    private Handler mHandler;

    private String mID;

    private RecyclerView mAnimesRecycleView;
    private RelativeAdapter mAnimesAdapter;
    private LinearLayoutManager mAnimesLinearLayoutManager;

    private RecyclerView mTopicRecycleView;
    private RelativeAdapter mTopicAdapter;
    private LinearLayoutManager mTopicsLinearLayoutManager;

    private View mCommentsContainer;
    private RecyclerView mCommentsRecycleView;
    private FullyLinearLayoutManager mCommentLayoutManager;
    private CommentsAdapter mCommentAdapter;


    private TopicInfo mTopicInfo;
    private List<RelativeRecommend> mAnimes;
    private List<RelativeRecommend> mTopics;
    private List<CommentData> mComments;

    private TextView mTitleView;
    private TextView mCreateTimeView;
    private TextView mAnimeCountView;
    private TextView mSummaryView;
    private ImageView mImageView;

    private TextView mMoreComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mID = intent.getStringExtra("id");
        setContentView(R.layout.subject_actvity);
        init();
    }

    private void init() {

        mTitleView = (TextView) findViewById(R.id.topic_title);
        mCreateTimeView = (TextView) findViewById(R.id.create_time);
        mAnimeCountView = (TextView) findViewById(R.id.subject_total_animes);
        mSummaryView = (TextView) findViewById(R.id.expandable_text);
        mImageView = (ImageView) findViewById(R.id.subject_img);

        mMoreComments = (TextView) findViewById(R.id.footer_hint_words);

        mAnimesRecycleView = (RecyclerView) findViewById(R.id.animes_recycleview);
        mAnimesAdapter = new RelativeAdapter(this, RelativeAdapter.ANIMES);
        mAnimesLinearLayoutManager = new LinearLayoutManager(this);
        mAnimesLinearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mAnimesRecycleView.setLayoutManager(mAnimesLinearLayoutManager);
        mAnimesRecycleView.setAdapter(mAnimesAdapter);
        mAnimesRecycleView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        mTopicRecycleView = (RecyclerView) findViewById(R.id.topic_recycleview);
        mTopicAdapter = new RelativeAdapter(this, RelativeAdapter.MANTIE);
        mTopicsLinearLayoutManager = new LinearLayoutManager(this);
        mTopicsLinearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mTopicRecycleView.setLayoutManager(mTopicsLinearLayoutManager);
        mTopicRecycleView.setAdapter(mTopicAdapter);
        mTopicRecycleView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        mCommentsContainer = findViewById(R.id.comments_container);
        mCommentsRecycleView = (RecyclerView) findViewById(R.id.comments_recycleview);
        mCommentLayoutManager = new FullyLinearLayoutManager(this);
        mCommentLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mCommentAdapter = new CommentsAdapter(this);
        mCommentsRecycleView.setAdapter(mCommentAdapter);


        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_ARRIVE:
                        mTitleView.setText(mTopicInfo.title);
                        mCreateTimeView.setText(mTopicInfo.createTime);
                        mAnimeCountView.setText(mTopicInfo.subjectCount + "部动画");
                        mSummaryView.setText(mTopicInfo.summary);
                        ImageUtils.getImage(SubjectActivity.this, mTopicInfo.imageUrl, mImageView);

                        mAnimesAdapter.setData(mAnimes);
                        mAnimesAdapter.notifyDataSetChanged();

                        if (mTopics != null && mTopics.size() > 0) {
                            mTopicAdapter.setData(mTopics);
                            mTopicAdapter.notifyDataSetChanged();
                        }

                        if (mComments != null && mComments.size() > 0) {
                            mCommentAdapter.setData(mComments);
                            mCommentAdapter.notifyDataSetChanged();
                        } else {
                            mCommentsContainer.setVisibility(View.GONE);
                            mMoreComments.setText("快来抢沙发~");
                        }

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

    private void getData() {

        asyncGet(APIConfig.TOPIC_DETAIL, "id", mID, new IRequestCallBack() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    data = data.getJSONObject("data");

                    JSONObject basicInfo = data.getJSONObject("topic");
                    mTopicInfo = TopicInfo.create(basicInfo);

                    //处理推荐动漫逻辑
                    JSONArray relativeSubjects = data.getJSONArray("subject");
                    if (relativeSubjects != null && relativeSubjects.length() > 0) {
                        mAnimes = new ArrayList<>(relativeSubjects.length());
                        for (int i = 0; i < relativeSubjects.length(); i++) {
                            JSONObject object = relativeSubjects.getJSONObject(i);
                            RelativeRecommend animeInfo = RelativeRecommend.create(object);
                            mAnimes.add(animeInfo);
                        }
                    }

                    //处理推荐盘点
                    JSONArray relativeTopics = data.getJSONArray("relative");
                    if (relativeTopics != null && relativeTopics.length() > 0) {
                        mTopics = new ArrayList<>(relativeTopics.length());
                        for (int i = 0; i < relativeTopics.length(); i++) {
                            JSONObject object = relativeTopics.getJSONObject(i);
                            RelativeRecommend animeInfo = RelativeRecommend.create(object);
                            mTopics.add(animeInfo);
                        }
                    }

                    //处理评论逻辑
                    JSONArray comments = data.getJSONArray("comments");
                    if (comments != null && comments.length() > 0) {
                        mComments = new ArrayList<>();
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject object = comments.getJSONObject(i);
                            CommentData comment = CommentData.create(object);
                            mComments.add(comment);
                        }
                    }
                    mHandler.sendEmptyMessage(DATA_ARRIVE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

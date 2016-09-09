package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.AnimeInfo;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.data.ReviewInfo;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.activity.CommentActivity;
import com.dongman.fm.ui.activity.PlayActivity;
import com.dongman.fm.ui.activity.StillsActivity;
import com.dongman.fm.ui.fragment.adapter.BrowsersAdapter;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuzhiwei on 15/7/11.
 */
public class DetailFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "DetailFragment";
    private String          mID;
    private BaseActivity mActivity;

    private View mAnimsContainer, mArticleContainer, mDottedLine;

    private RecyclerView mAnimesRecyclerView, mArticleRecyclerView, mPlayRecyclerview, mBrowserRecyclerview;
    private LinearLayout mManpingContainer, mStarContainer;
    private RelativeAdapter mAnimesAdapter, mArticleAdapter, mPlayAdapter;
    private BrowsersAdapter mBrowsersAdapter;

    private TextView average, averageCount, animeGenres, animeSubtype;

    private LinearLayout commentContainer;

    private View mStills, mTopics, mGroups;

    private ImageView animeImage;

    private AnimeInfo mAnimeInfo;
    private List<RelativeRecommend> mAnimesRecommends = new ArrayList<>();
    private List<RelativeRecommend> mMantieRecommends = new ArrayList<>();
    private List<ReviewInfo> mReviewsRecommends = new ArrayList<>();
    private List<CommentData> mComments = new ArrayList<>();


    private Handler mHandler;

    private boolean isDataReady = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mID = bundle.getString("id");

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_UI:
                        isDataReady = true;
//                        mModuleAdapter.notifyDataSetChanged();
                        ImageUtils.getImage(mContext, mAnimeInfo.imageLarge, animeImage);
                        average.setText(mAnimeInfo.scoreAllAvg + "分");
                        animeGenres.setText("类型: " + mAnimeInfo.genres);
                        animeSubtype.setText("篇幅: " + mAnimeInfo.rateCount);
                        averageCount.setText(mAnimeInfo.rateCount + "人评分");

                        int childCount = mStarContainer.getChildCount();
                        int starNum = (int) Math.ceil((mAnimeInfo.scoreAllAvg + 1) / 2);
                        if(starNum == 0) {
                            for(int i = 0; i < childCount - 1; i++) {
                                ImageView childView = (ImageView)mStarContainer.getChildAt(i);
                                childView.setImageResource(R.drawable.icon_star_grey);
                            }
                        } else {
                            for(int i = 0; i < childCount - 1 && i <= starNum; i++) {
                                ImageView childView = (ImageView)mStarContainer.getChildAt(i);
                                childView.setImageResource(R.drawable.icon_star_yellow);
                            }
                        }
                        //测试数据
                        int count = Integer.parseInt(mAnimeInfo.rateCount);
                        List test = new ArrayList();
                        for (int i = 0; i < count; i++) {
                            test.add(i);
                        }
                        mPlayAdapter.setData(test);
                        mPlayAdapter.notifyDataSetChanged();

                        mBrowsersAdapter.notifyDataSetChanged();

                        //relative animes
                        if (mAnimesRecommends.size() == 0) {
                            mAnimsContainer.setVisibility(View.GONE);
                        } else {
                            mAnimesAdapter.setData(mAnimesRecommends);
                            mAnimesAdapter.notifyDataSetChanged();
                        }

                        //relative article
                        if (mMantieRecommends.size() == 0) {
                            mArticleContainer.setVisibility(View.GONE);
                        } else {
                            mArticleAdapter.setData(mMantieRecommends);
                            mArticleAdapter.notifyDataSetChanged();
                        }

                        //relative manping
                        if (mReviewsRecommends != null && mReviewsRecommends.size() > 0) {
                            for (int i = 0; i < mReviewsRecommends.size(); i++) {
                                View child = LayoutInflater.from(mActivity).inflate(R.layout.manping_item, null);
                                final ReviewInfo reviewInfo = mReviewsRecommends.get(i);
                                TextView manpingTitle = (TextView) child.findViewById(R.id.manping_title);
                                TextView manpingContent = (TextView) child.findViewById(R.id.manping_content);

                                manpingTitle.setText(reviewInfo.title);
                                manpingContent.setText(reviewInfo.summary);

                                child.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//TODO 为什么一定要加这个标志
                                        intent.setAction("com.dongman.fm.manping");
                                        intent.putExtra("id", reviewInfo.id);
                                        mActivity.startActivity(intent);
                                    }
                                });
                                mManpingContainer.addView(child);
                            }
                        } else {
                            mManpingContainer.setVisibility(View.GONE);
                        }
                        mManpingContainer.requestLayout();

                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {

        //初始化动漫基本信息模块
        animeImage = (ImageView) root.findViewById(R.id.detail_anime_image);

        animeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayActivity.class);
                getActivity().startActivity(intent);
            }
        });

        mDottedLine = root.findViewById(R.id.dotted_line);
        mDottedLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        average = (TextView) root.findViewById(R.id.anime_average);
        averageCount = (TextView) root.findViewById(R.id.average_count);
        animeGenres = (TextView) root.findViewById(R.id.anime_genres);
        animeSubtype = (TextView) root.findViewById(R.id.anime_subtype);

        mStarContainer = (LinearLayout) root.findViewById(R.id.anime_average_star_container);
        mPlayRecyclerview = (RecyclerView) root.findViewById(R.id.play_button_recyclerview);
        LinearLayoutManager playLinear = new LinearLayoutManager(mActivity);
        playLinear.setOrientation(LinearLayoutManager.HORIZONTAL);
        mPlayRecyclerview.setLayoutManager(playLinear);
        mPlayAdapter = new RelativeAdapter(mActivity, RelativeAdapter.PLAY);
        mPlayRecyclerview.setAdapter(mPlayAdapter);
        mPlayRecyclerview.addItemDecoration(new SpacesItemDecoration(20,0,10,10));

        mBrowserRecyclerview = (RecyclerView) root.findViewById(R.id.browser_recyclerview);
        LinearLayoutManager browserLinear = new LinearLayoutManager(mActivity);
        browserLinear.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBrowserRecyclerview.setLayoutManager(browserLinear);
        mBrowsersAdapter = new BrowsersAdapter(mActivity);
        mBrowserRecyclerview.setAdapter(mBrowsersAdapter);
        mBrowserRecyclerview.addItemDecoration(new SpacesItemDecoration(20,0,0,0));

        //初始化相关动漫模块
        mAnimsContainer = root.findViewById(R.id.animes_relative);
        mAnimesRecyclerView = (RecyclerView) root.findViewById(R.id.relative_anime_recycleview);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
        mAnimesRecyclerView.setLayoutManager(linearLayoutManager1);
        mAnimesAdapter = new RelativeAdapter(mActivity, RelativeAdapter.ANIMES);
        mAnimesRecyclerView.setAdapter(mAnimesAdapter);
        mAnimesRecyclerView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        //初始化剧照列表以及专题的View
        mStills = root.findViewById(R.id.stills);
        mStills.setOnClickListener(this);
        mTopics = root.findViewById(R.id.topics);
        mTopics.setOnClickListener(this);

        //relative_article_recycleview
        mArticleContainer = root.findViewById(R.id.article_relative);
        mArticleRecyclerView = (RecyclerView) root.findViewById(R.id.relative_article_recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mArticleRecyclerView.setLayoutManager(linearLayoutManager);
        mArticleAdapter = new RelativeAdapter(mActivity, RelativeAdapter.MANTIE);
        mArticleRecyclerView.setAdapter(mArticleAdapter);
        mArticleRecyclerView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        //relative_manping_recycleview
        mManpingContainer = (LinearLayout) root.findViewById(R.id.manping_container);

        commentContainer = (LinearLayout) root.findViewById(R.id.article_huifu_container);
        commentContainer.setOnClickListener(this);
        getData(mID);
    }


    public void getData(String id) {
        asyncGet(APIConfig.SUBJECT_DETAIL, "id", id, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    data = data.getJSONObject("data");
                    JSONObject subject = data.getJSONObject("subject");
                    mAnimeInfo = AnimeInfo.create(subject);
                    //处理推荐动漫逻辑
                    if (!data.isNull("relative_subjects")) {
                        JSONArray relativeSubjects = data.getJSONArray("relative_subjects");
                        if (relativeSubjects != null && relativeSubjects.length() > 0) {
                            mAnimesRecommends = new ArrayList<>(relativeSubjects.length());
                            for (int i = 0; i < relativeSubjects.length(); i++) {
                                JSONObject object = relativeSubjects.getJSONObject(i);
                                RelativeRecommend animeInfo = RelativeRecommend.create(object);
                                mAnimesRecommends.add(animeInfo);
                            }
                        }
                    }
                    //处理推荐漫贴的逻辑
                    if (!data.isNull("relative_articles")) {
                        JSONArray relativeArticles = data.getJSONArray("relative_articles");
                        if (relativeArticles != null && relativeArticles.length() > 0) {
                            mMantieRecommends = new ArrayList<>();
                            for (int i = 0; i < relativeArticles.length(); i++) {
                                JSONObject object = relativeArticles.getJSONObject(i);
                                RelativeRecommend mantie = RelativeRecommend.create(object);
                                mMantieRecommends.add(mantie);
                            }
                        }
                    }

                    //处理推荐漫评的逻辑
                    JSONArray reviews = data.getJSONArray("reviews");
                    if (reviews != null && reviews.length() > 0) {
                        mReviewsRecommends = new ArrayList<>();
                        for (int i = 0; i < reviews.length(); i++) {
                            JSONObject object = reviews.getJSONObject(i);
                            ReviewInfo reviewInfo = ReviewInfo.create(object);
                            mReviewsRecommends.add(reviewInfo);
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
                    mHandler.sendEmptyMessage(REFRESH_UI);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_huifu_container:
                Intent comment = new Intent(mActivity, CommentActivity.class);
                mActivity.startActivity(comment);
                break;
            case R.id.stills:
                Intent stills = new Intent(mActivity, StillsActivity.class);
                mActivity.startActivity(stills);
                break;
            case R.id.topics:
//                Intent topic = new Intent(mActivity, )
                break;

        }
    }
}

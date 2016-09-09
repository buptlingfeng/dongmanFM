package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.AnimeInfo;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.CommentActivity;
import com.dongman.fm.ui.fragment.adapter.CommentsAdapter;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.CircleImageView;
import com.dongman.fm.ui.view.FullyLinearLayoutManager;
import com.dongman.fm.ui.view.ObservableScrollView;
import com.dongman.fm.ui.view.SharePanel;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.like.LikeButton;
import com.like.OnLikeListener;

import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/8/13.
 */
public class ManpingDetailFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = ManpingDetailFragment.class.getSimpleName();

    private static final int DATA_ARRIVE = 1;

    private TextView mManpinOwnerName;
    private CircleImageView mManpingOwnerAvatar;
    private TextView mManpingUpdateTime;

    private TextView mManpingTitle;
    private WebView mWebView;
    private View mCommentsContainer;
    private RecyclerView mCommentsRecycleView;
    private FullyLinearLayoutManager mCommentLayoutManager;
    private CommentsAdapter mCommentAdapter;

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeAdapter mMantieAdapter;
    private TextView mRecommendTag;
    private TextView mRelativeAnimeTitle;
    private ImageView mRelativeAnimeImage;

    private Handler mHandler;

    private String mID;

    private JSONObject mBasicData;
    private List<CommentData> mComments;
    private Activity mActivity;
    private ObservableScrollView mScrollView;


    private View mBack, mShare, mComment;
    private LikeButton mVote;
    private TextView mCommentCount, mVoteCount;

    private View mHeader, mIndicator;

    private AnimeInfo mAnimeInfo;

    private boolean isVisible = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mID = this.getArguments().getString("id");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manping_detail, null, false);
        initView(view);
        return view;
    }

    private void initView(View root) {

        mManpinOwnerName = (TextView) root.findViewById(R.id.manping_detail_user_name);
        mManpingOwnerAvatar = (CircleImageView) root.findViewById(R.id.manping_detail_user_avatar);
        mRecommendTag = (TextView) root.findViewById(R.id.recommend_tag);
        mRelativeAnimeTitle = (TextView) root.findViewById(R.id.releate_anime);
        mRelativeAnimeImage = (ImageView) root.findViewById(R.id.releate_anime_image);
        mHeader = root.findViewById(R.id.manping_detail_head_container);
        mIndicator = root.findViewById(R.id.mapping_indicator);

        mScrollView = (ObservableScrollView) root.findViewById(R.id.root_container);

        mScrollView.setOnScrollListener(new ObservableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                if (y < oldY) {//向下滑动
                    if (isVisible) {
                        return;
                    }
                    mHeader.setVisibility(View.VISIBLE);
                    mIndicator.setVisibility(View.VISIBLE);
                    isVisible = true;

                } else {//向上滑动
                    if (!isVisible) {
                        return;
                    }
                    if (oldY <= 0 && y <= 0) {//防止回震的情况
                        return;
                    }
                    mHeader.setVisibility(View.GONE);
                    mIndicator.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });

        mBack = root.findViewById(R.id.article_back);
        mShare = root.findViewById(R.id.share);
        mVote = (LikeButton) root.findViewById(R.id.article_vote);
        mComment = root.findViewById(R.id.article_huifu);

        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mComment.setOnClickListener(this);
        mRelativeAnimeImage.setOnClickListener(this);

        mVote.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        mCommentCount = (TextView)root.findViewById(R.id.article_huifu_count);
        mVoteCount = (TextView) root.findViewById(R.id.article_vote_count);

        mManpingUpdateTime = (TextView) root.findViewById(R.id.manping_detail_updatetime);

        mManpingTitle = (TextView) root.findViewById(R.id.manping_detail_title);

        mWebView = (WebView) root.findViewById(R.id.webView1);
        mWebView.setWebViewClient(new CustomWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new CustomChromClient());


//        mManpingVote = (TextView) root.findViewById(R.id.manping_detail_vote);

        mCommentsContainer = root.findViewById(R.id.comments_container);
        mCommentsRecycleView = (RecyclerView) root.findViewById(R.id.comments_recycleview);
        mCommentLayoutManager = new FullyLinearLayoutManager(getActivity());
        mCommentLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mCommentsRecycleView.setLayoutManager(mCommentLayoutManager);
        mCommentAdapter = new CommentsAdapter(getActivity());
        mCommentsRecycleView.setAdapter(mCommentAdapter);

        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mMantieAdapter = new RelativeAdapter(mContext,RelativeAdapter.MANTIE);
        mRecycleView.setAdapter(mMantieAdapter);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_ARRIVE:
                        try {
                            String userName = mBasicData.getString("user_name");
                            mManpinOwnerName.setText(userName);
                            String url = mBasicData.getString("avatar_img_url");
                            ImageUtils.getImage(getActivity(), url, mManpingOwnerAvatar);
                            String updateTime = mBasicData.getString("create_time");
                            mManpingUpdateTime.setText(updateTime);
                            String tag = mBasicData.getString("tag");
                            mRecommendTag.setText(tag);
                            String manpingTitle = mBasicData.getString("title");
                            mManpingTitle.setText(manpingTitle);

                            String reviewUrl = mBasicData.getString("review_url");
                            mWebView.loadUrl(reviewUrl);
                            String votes = mBasicData.getString("vote_count");
                            mVoteCount.setText(votes);
                            mCommentAdapter.setData(mComments);
                            mCommentAdapter.notifyDataSetChanged();
                            if(mComments != null && mComments.size() > 0) {
                                addCommentFooter(true);
                            } else {
                                addCommentFooter(false);
                            }
                            if (mAnimeInfo != null) {
                                mRelativeAnimeTitle.setText(mAnimeInfo.title);
                                ImageUtils.getImage(mActivity, mAnimeInfo.imageLarge, mRelativeAnimeImage);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        getData();
    }

    private void getData() {
        asyncGet(APIConfig.MANPING_DETAIL, "id", mID, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject data = object.getJSONObject("data");
                    mBasicData = data.getJSONObject("review");

                    JSONArray comments = data.getJSONArray("comments");
                    if (comments != null && comments.length() > 0) {
                        mComments = new ArrayList<>();
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject commentsJSONObject = comments.getJSONObject(i);
                            CommentData comment = CommentData.create(commentsJSONObject);
                            mComments.add(comment);
                        }
                    }
                    JSONObject subjectInfo = data.getJSONObject("subject");

                    mAnimeInfo = new AnimeInfo();
                    mAnimeInfo.id = subjectInfo.getString("id");
                    mAnimeInfo.title = subjectInfo.getString("title");
                    mAnimeInfo.imageLarge = subjectInfo.getString("img_url");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.sendEmptyMessage(DATA_ARRIVE);
                }
            }
        });
    }

    private void addCommentFooter(boolean isMore) {
        Context context = getActivity();
        if(context == null) {
            Log.e(TAG, "addCommentFooter and the context is null;");
            return;
        }
        View footer = LayoutInflater.from(context).inflate(R.layout.more_footer,null);
        TextView hint = (TextView) footer.findViewById(R.id.footer_hint_words);

        if(isMore) {
            hint.setText("点击查看更多");
        } else {
            hint.setText("快去抢沙发~");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_back:
                mActivity.finish();
                break;
            case R.id.share:
                new SharePanel(mActivity).show();
                break;
            case R.id.article_huifu:
                Intent comment = new Intent(mActivity, CommentActivity.class);
                mActivity.startActivity(comment);
                break;
            case R.id.releate_anime_image:
                if (mAnimeInfo != null) {
                    Intent detail = new Intent();
                    detail.setAction("com.dongman.fm.detail");
                    Bundle data = new Bundle();
                    data.putString("id", mAnimeInfo.id);
                    data.putString("name", mAnimeInfo.title);
                    detail.putExtras(data);
                    mActivity.startActivity(detail);
                }
                break;

        }
    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }

    class CustomChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            FMLog.d(TAG, "newProgress " + newProgress);
            super.onProgressChanged(view, newProgress);
        }

    }

}

package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.dongman.fm.utils.FMLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/12/7.
 */
public class RecommendDetailFragment extends BaseFragment implements IRequestCallBack{

    private static final String TAG = RecommendDetailFragment.class.getSimpleName();

    private BaseActivity mActivity;
//    private TextView mMoreReviews;
    private ActionBar mActionBar;

    private RecyclerView mRelativeRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeAdapter mRelativeAdapter;

//    private EditText mInputEdit;
    private LinearLayout mReviewsContainer;
    private TextView mReviewCount;
    private TextView mTitleView, mBrowseCount, mCreateTimeView;

    private TextView mVoteContent;

    private String mId;
    private String mTitle;

    private Handler mHandler;
    private WebView mWebView;

    private DetailData mData;

//    private View mReview1,mReview2,mReview3;


    @Override
    public void onAttach(Activity activity) {
        mActivity = (BaseActivity)activity;
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mId = bundle.getString("id");
        mTitle = bundle.getString("title");
        mActionBar = mActivity.getSupportActionBar();
        getData(mId);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_UI:
                        if (mWebView != null) {
                            mWebView.loadUrl(mData.articleUrl);
                        }
                        if (mData.animes.size() == 0) {
                            mRelativeRecyclerView.setVisibility(View.GONE);
                        }
                        mRelativeAdapter.setData(mData.animes);
                        mRelativeAdapter.notifyDataSetChanged();
                        if (mData.createTime == null || mData.createTime.equals("null")) {
                            Log.e(TAG, "the create time is null");
                            mCreateTimeView.setText("三天前");
                        } else {
                            mCreateTimeView.setText(mData.createTime);
                        }
                        mBrowseCount.setText(mData.browseCount + "人看过");
//                        if (mData.source == null || mData.createTime.equals("null")) {
//                            mSourceTag.setVisibility(View.GONE);
//                        } else {
//                            mSourceTag.setText(mData.source);
//                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_detail_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {

        mActionBar.setTitle("详情");
        mReviewsContainer = (LinearLayout) root.findViewById(R.id.article_huifu_container);
        mReviewsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.addFragment(new CommentListFragment(), "ReviewList");
            }
        });

        mTitleView = (TextView) root.findViewById(R.id.article_title);
        mTitleView.setText(mTitle);
        mCreateTimeView = (TextView) root.findViewById(R.id.create_time);
        mBrowseCount = (TextView) root.findViewById(R.id.browse_count);
//        mSourceTag = (TextView) root.findViewById(R.id.source_tag);

        mReviewCount = (TextView) root.findViewById(R.id.article_huifu_count);
        mVoteContent = (TextView) root.findViewById(R.id.article_vote_count);

        mWebView = (WebView) root.findViewById(R.id.webView1);
        mWebView.setWebViewClient(new CustomWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(mActivity), "android");
        mWebView.setWebChromeClient(new CustomChromClient());

        mRelativeRecyclerView = (RecyclerView) root.findViewById(R.id.recycleview);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRelativeRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRelativeAdapter = new RelativeAdapter(mActivity, RelativeAdapter.ANIMES);
        mRelativeRecyclerView.setAdapter(mRelativeAdapter);
        mRelativeRecyclerView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));
    }

    private void getData(String id) {

        asyncGet(APIConfig.ARTICAL_DETAIL, "id", id, new IRequestCallBack() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    data = data.getJSONObject("data");
                    mData = DetailData.create(data);
                    mHandler.sendEmptyMessage(REFRESH_UI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FMLog.d(TAG, TAG + " onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FMLog.d(TAG, TAG + " onDetach()");
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    @Override
    public void onFailure(Request request, IOException e) {

    }

    @Override
    public void onResponse(Response response) throws IOException {

    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            FMLog.d(TAG, "浏览器图片信息：" + url);
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

    class JavaScriptinterface {

        Activity activity;

        public JavaScriptinterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show();
        }
    }

    public static class DetailData {

        String title;
        String createTime;
        String articleId;
        String source;
        int browseCount;
        String articleUrl;
        List<CommentData> comments;
        List<RelativeRecommend> animes;

        DetailData() {
            comments = new ArrayList<>();
            animes = new ArrayList<>();
        }

        public static DetailData create(JSONObject object) {
            DetailData data = null;
            try {
                if (object != null) {
                    data = new DetailData();
                    data.title = object.getString("title");
                    data.createTime = object.getString("create_time");
                    data.articleId = object.getString("article_id");
                    data.browseCount = object.getInt("browse_count");
                    data.articleUrl = object.getString("article_url");
                    data.source = object.getString("source");
                    JSONArray comments = object.getJSONArray("comments");
                    if (comments != null) {
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject comment = comments.getJSONObject(i);
                            CommentData commentData = CommentData.create(comment);
                            data.comments.add(commentData);
                        }
                    }

                    JSONArray animes = object.getJSONArray("relative");
                    if (animes != null) {
                        for (int i = 0; i < animes.length(); i++) {
                            JSONObject anime = animes.getJSONObject(i);
                            RelativeRecommend relativeAnime = RelativeRecommend.create(anime);
                            data.animes.add(relativeAnime);
                        }
                    }
                }
                return data;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }
    }
}

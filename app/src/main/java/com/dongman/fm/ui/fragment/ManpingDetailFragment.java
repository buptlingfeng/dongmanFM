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
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.CommentAdapter;
import com.dongman.fm.ui.fragment.adapter.CommentsAdapter;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.CircleImageView;
import com.dongman.fm.ui.view.CustomListView;
import com.dongman.fm.ui.view.FullyLinearLayoutManager;
import com.dongman.fm.ui.view.SpacesItemDecoration;

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
public class ManpingDetailFragment extends BaseFragment {

    private static final String TAG = ManpingDetailFragment.class.getSimpleName();

    private static final int DATA_ARRIVE = 1;

    private TextView mManpinOwnerName;
    private CircleImageView mManpingOwnerAvatar;
    private TextView mManpingUpdateTime;

    private TextView mManpingTitle;
    private TextView mManpingVote;
    private WebView mWebView;

//    private CustomListView mManpingCommentsList;
//    private CommentAdapter mCommentAdapter;

    private View mCommentsContainer;
    private RecyclerView mCommentsRecycleView;
    private FullyLinearLayoutManager mCommentLayoutManager;
    private CommentsAdapter mCommentAdapter;

    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeAdapter mMantieAdapter;

    private Handler mHandler;

    private String mID;

    private JSONObject mBasicData;
    private List<CommentData> mComments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mID = this.getArguments().getString("id");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manping_detail, null, false);
        init(view);
        return view;
    }

    private void init(View root) {

        mManpinOwnerName = (TextView) root.findViewById(R.id.manping_detail_user_name);
        mManpingOwnerAvatar = (CircleImageView) root.findViewById(R.id.manping_detail_user_avatar);

        mManpingOwnerAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.dongman.fm.detail");
                intent.putExtra("id", 1);
                intent.putExtra("name", "海贼王");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        mManpingUpdateTime = (TextView) root.findViewById(R.id.manping_detail_updatetime);

        mManpingTitle = (TextView) root.findViewById(R.id.manping_detail_title);
//        mManpingContent = (TextView) root.findViewById(R.id.manping_detail_content);
        mWebView = (WebView) root.findViewById(R.id.webView1);
        mWebView.setWebViewClient(new CustomWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new CustomChromClient());


        mManpingVote = (TextView) root.findViewById(R.id.manping_detail_vote);

        mCommentsContainer = findViewById(R.id.comments_container);
        mCommentsRecycleView = (RecyclerView) findViewById(R.id.comments_recycleview);
        mCommentLayoutManager = new FullyLinearLayoutManager(getActivity());
        mCommentLayoutManager.setOrientation(OrientationHelper.VERTICAL);
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

                            String manpingTitle = mBasicData.getString("title");
                            mManpingTitle.setText(manpingTitle);

                            String reviewUrl = mBasicData.getString("review_url");
                            mWebView.loadUrl(reviewUrl);
                            String votes = mBasicData.getString("vote_count");
                            mManpingVote.setText("(" + votes + ")");

                            mCommentAdapter.setData(mComments);
                            mCommentAdapter.notifyDataSetChanged();
//                            if(mComments != null && mComments.length() > 0) {
//                                addCommentFooter(true);
//                            } else {
//                                addCommentFooter(false);
//                            }
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

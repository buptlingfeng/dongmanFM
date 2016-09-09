package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.utils.FMLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuzhiwei on 15/12/7.
 */
public class TopicDetailFragment extends BaseFragment implements IRequestCallBack{

    private static final String TAG = TopicDetailFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private View mBack;
    private TextView mTitleView;
    private View mMoreReviews;
    private ActionBar mActionBar;

    private String mUrl;
    private String mTitle;

    @Override
    public void onAttach(Activity activity) {
        mActivity = (BaseActivity)activity;
        super.onAttach(activity);
        FMLog.d(TAG, TAG + " onAttach()");
        Bundle bundle = getArguments();
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title");
        mActionBar = mActivity.getSupportActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_detail_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {

        mTitleView = (TextView)root.findViewById(R.id.title);
//        mTitleView.setText(mTitle);
        mActionBar.setTitle(mTitle);
//        mMoreReviews = root.findViewById(R.id.extra_action);
//        mMoreReviews.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                mActivity.addFragment(new CommentListFragment(), "ReviewList");
//            }
//        });
//        mBack = root.findViewById(R.id.back);
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mActivity.onBackPressed();
//            }
//        });
        WebView mWebView = (WebView) root.findViewById(R.id.webView1);
        mWebView.setWebViewClient(new CustomWebViewClient());
        mWebView.loadUrl(mUrl);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(mActivity), "android");
        mWebView.setWebChromeClient(new CustomChromClient());
    }

    private void getReviews() {
        Map<String, String> parems = new HashMap<>();
        asyncGet("", parems, this);
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
}

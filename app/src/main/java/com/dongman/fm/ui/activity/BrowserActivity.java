package com.dongman.fm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/7/24.
 */
public class BrowserActivity extends BaseActivity {

    private static final String TAG = BrowserActivity.class.getSimpleName();
    private WebView mWebView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUrl = bundle.getString("url");
        setContentView(R.layout.browser_activity);
        initView();
    }

    private void initView () {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new CustomWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
        mWebView.setWebChromeClient(new CustomChromClient());
//        String url = "http://www.tudou.com/programs/view/html5embed.action?code=ET_48lSobVI&autoPlay=true&playType=AUTO";
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onPause() {
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView,(Object[])null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            FMLog.d(TAG, "shouldOverrideUrlLoading：" + url);
            if (url.startsWith("tudou://")) {
                return false;
            }
            view.loadUrl(url);
//            FMLog.d(TAG, "shouldOverrideUrlLoading：" + url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
            FMLog.e(TAG, "onReceivedError  errorCode : " + errorCode + " description : " + description + " failingUrl : " + failingUrl);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            FMLog.d(TAG, "onLoadResource：" + url);
            super.onLoadResource(view, url);
        }
    }

    class CustomChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
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

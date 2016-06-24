package com.dongman.fm.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/5/7.
 */
public class PlayActivity extends BaseActivity {
    private static final String TAG = "PlayActivity";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        initView();
    }

    private void initView () {
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new CustomWebViewClient());

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
        mWebView.setWebChromeClient(new CustomChromClient());
        String url = "http://www.tudou.com/programs/view/html5embed.action?code=ET_48lSobVI&autoPlay=true&playType=AUTO";
        mWebView.loadUrl(url);
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
}

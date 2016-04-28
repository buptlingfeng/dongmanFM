package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dongman.fm.R;
import com.dongman.fm.utils.FMLog;
import com.dongman.fm.weibo.UsersAPI;
import com.dongman.fm.weibo.WeiboConstant;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * Created by liuzhiwei on 16/4/23.
 */
public class WeiboLoginActivity extends BaseActivity {
    public static final String TAG = "WeiboLoginActivity";

    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tmp);
        AuthInfo authInfo = new AuthInfo(WeiboLoginActivity.this, WeiboConstant.WB_APPKEY, WeiboConstant.WB_REDIRECT_URL, WeiboConstant.SCOPE);
        mSsoHandler = new SsoHandler(WeiboLoginActivity.this, authInfo);
        initView();
    }

    private void initView() {
        View login = findViewById(R.id.weibo_login);
        View logout = findViewById(R.id.login_out);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.weibo_login:
                        mSsoHandler.authorize(new AuthListener());
                        break;
                    case R.id.login_out:
                        WeiboParameters params = new WeiboParameters(WeiboConstant.WB_APPKEY);
                        params.add("access_token", mAccessToken.getToken());
                        new AsyncWeiboRunner(WeiboLoginActivity.this).requestAsync(WeiboConstant.WB_REDIRECT_URL, params, "POST", new LogoutListener());
                        break;
                }
            }
        };
        login.setOnClickListener(listener);
        logout.setOnClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle bundle) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
            if (mAccessToken.isSessionValid()) {
                FMLog.d(TAG, "accessToken : " + mAccessToken);
                UsersAPI usersAPI = new UsersAPI(mAccessToken);
                long uid = Long.parseLong(mAccessToken.getUid());
                usersAPI.show(uid, new RequestListener() {
                    @Override
                    public void onComplete(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            //TODO 获取用户信息
                            FMLog.d(TAG, s);
                        }
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {
                        FMLog.e(TAG, "failed");
                    }
                });
            } else {
                String code = bundle.getString("code", "");
                FMLog.d(TAG, "code : " + code);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {
            //TODO 取消授权
        }
    }

    class LogoutListener implements RequestListener {
        @Override
        public void onComplete(String s) {
            FMLog.d(TAG, "LogoutListener.onComplete");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            FMLog.d(TAG, "LogoutListener.onWeiboException");
        }
    }
}

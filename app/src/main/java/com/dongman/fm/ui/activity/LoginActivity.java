package com.dongman.fm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.OkHttpUtil;
import com.dongman.fm.qq.QQConstant;
import com.dongman.fm.ui.view.SharePanel;
import com.dongman.fm.utils.FMLog;
import com.dongman.fm.weibo.UsersAPI;
import com.dongman.fm.weibo.WeiboConstant;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuzhiwei on 16/4/25.
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private Context mContext;

    //QQ登陆
    private Tencent mTencent;
    private IUiListener mListener;

    //微博登陆
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken mAccessToken;

    private String mUid;
    private String mServiceName;
    private String mGander;
    private String mName;
    private String mAvatarUrl;
    private String mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
    }

    private void initView() {
        View qqLogin = findViewById(R.id.login_qq);
        View weiboLogin = findViewById(R.id.login_weibo);
        View oneselfLogin = findViewById(R.id.login_oneself);
        View register = findViewById(R.id.register_user);
        View loginSkip = findViewById(R.id.login_skip);
        ClickListener listener = new ClickListener();
        qqLogin.setOnClickListener(listener);
        weiboLogin.setOnClickListener(listener);
        oneselfLogin.setOnClickListener(listener);
        register.setOnClickListener(listener);
        loginSkip.setOnClickListener(listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mListener != null) {
            Tencent.onActivityResultData(requestCode,resultCode,data,mListener);
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_qq:
                    mTencent = Tencent.createInstance(QQConstant.APP_ID, mContext.getApplicationContext());
                    if (!mTencent.isSessionValid()) {
                        mListener = new BaseUiListener();
                        mTencent.login(LoginActivity.this, QQConstant.SCOPE, mListener);
                    } else {
                        logoutQQ();
                    }
                    break;
                case R.id.login_weibo:
                    if (mAccessToken == null || !mAccessToken.isSessionValid()) {
                        AuthInfo authInfo = new AuthInfo(mContext, WeiboConstant.WB_APPKEY, WeiboConstant.WB_REDIRECT_URL, WeiboConstant.SCOPE);
                        mSsoHandler = new SsoHandler(LoginActivity.this, authInfo);
                        mSsoHandler.authorize(new WeiBoLoginAuthListener());
                    } else {
                        logoutWeiBo();
                    }
                    break;
                case R.id.login_oneself:
                    break;
                case R.id.register_user:
                    SharePanel panel = new SharePanel(LoginActivity.this);
                    panel.show();
                    break;
                case R.id.login_skip:
                    break;
            }
        }
    }

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            mUid = openId;
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }

    /**
     * 注销掉QQ登陆
     */
    public void logoutQQ() {
        FMLog.d(TAG, "logoutQQ");
        if (mTencent != null) {
            mTencent.logout(this);
        }
    }


    public void logoutWeiBo() {
        FMLog.d(TAG, "logoutWeiBo");
        if (mAccessToken != null && mAccessToken.isSessionValid()) {
            WeiboParameters params = new WeiboParameters(WeiboConstant.WB_APPKEY);
            params.add("access_token", mAccessToken.getToken());
            new AsyncWeiboRunner(this).requestAsync(WeiboConstant.WB_REDIRECT_URL, params, "POST", new WeiBoLogoutListener());
            mAccessToken.setToken("");
        } else {
            FMLog.e(TAG, "Logout args error!");
        }
    }

    public void syncUserInfo() {

        JSONObject data = new JSONObject();
        try {
            data.put("service_name", mServiceName);
            data.put("name", mName);
            data.put("uid", mUid);
            data.put("gender", mGander);
            data.put("avatar_url", mAvatarUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        OkHttpUtil.asyncPost(APIConfig.LOGIN_OTHER_API, data, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                FMLog.d(TAG, "onFailure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = new String(response.body().bytes());
                try {
                    JSONObject data = new JSONObject(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            FMLog.d(TAG, "onComplete : " + o.toString());
            initOpenidAndToken((JSONObject)o);
            UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
            FMLog.d(TAG, mTencent.getQQToken().toString());
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    try {
                        if (o instanceof JSONObject) {
                            JSONObject data = (JSONObject)o;
                            mName = data.getString("nickname");
                            mAvatarUrl = data.getString("figureurl_qq_2");
                            mDescription = data.getString("msg");
                            if (data.getString("gender").equals("男")) {
                                mGander = "1";
                            } else {
                                mGander = "0";
                            }
                            mServiceName = "qq";

                            syncUserInfo();
                        }
                    } catch (JSONException e) {

                    }
                }

                @Override
                public void onError(UiError uiError) {
                    FMLog.d(TAG, "onError");
                }

                @Override
                public void onCancel() {
                    FMLog.d(TAG, "onCancel");
                }
            });
        }

        @Override
        public void onError(UiError uiError) {
            FMLog.d(TAG, "onError");
        }

        @Override
        public void onCancel() {
            FMLog.d(TAG, "onCancel");
        }
    }


    //微博登陆回调
    class WeiBoLoginAuthListener implements WeiboAuthListener {

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

        }
    }

    class WeiBoLogoutListener implements RequestListener {
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

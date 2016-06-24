package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dongman.fm.R;
import com.dongman.fm.qq.QQConstant;
import com.dongman.fm.ui.view.SharePanel;
import com.dongman.fm.utils.FMLog;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/4/24.
 */
public class QQLogingActivity extends BaseActivity {
    private static final String TAG = "QQLogingActivity";
    private Tencent mTencent;
    private BaseUiListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tmp);
        mTencent = Tencent.createInstance(QQConstant.APP_ID, this.getApplicationContext());
        mListener = new BaseUiListener();
        findViewById(R.id.weibo_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
//        login();
    }

    private void login() {
        SharePanel sharePanel = new SharePanel(this);
        sharePanel.show();
    }

    private void logout() {
        mTencent.logout(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,mListener);
    }
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            FMLog.d(TAG, "onComplete : " + o.toString());
            initOpenidAndToken((JSONObject)o);
            UserInfo userInfo = new UserInfo(QQLogingActivity.this, mTencent.getQQToken());
            FMLog.d(TAG, mTencent.getQQToken().toString());
            userInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    FMLog.d(TAG, "onComplete");
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
}

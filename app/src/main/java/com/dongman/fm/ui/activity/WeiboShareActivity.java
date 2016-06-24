package com.dongman.fm.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.dongman.fm.utils.FMLog;
import com.dongman.fm.weibo.WeiboConstant;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by liuzhiwei on 16/4/24.
 */
public class WeiboShareActivity extends BaseActivity implements IWeiboHandler.Response{

    private static final String TAG = "WeiboShareActivity";
    private IWeiboShareAPI mWeiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, WeiboConstant.WB_APPKEY);
//        mWeiboShareAPI.registerApp();
//        sendMultiMessage();
    }

    private void sendMultiMessage() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = new TextObject();

        weiboMessage.textObject.text = "初始化微博的分享消息 if (hasText)";
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(this, request); //发送请求消息到微博,唤起微博分享界面
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                FMLog.d(TAG, "ErrorCode.ERR_OK");
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                FMLog.d(TAG, "ErrorCode.ERR_CANCEL");
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                FMLog.d(TAG, "ErrorCode.ERR_FAIL");
                break;
        }
    }
}

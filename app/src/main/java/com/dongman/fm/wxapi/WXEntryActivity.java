package com.dongman.fm.wxapi;

import android.os.Bundle;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.utils.FMLog;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by liuzhiwei on 16/4/20.
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private static final String WX_APPID = "wx4dc8c8e6b65be760";

    private IWXAPI mWXAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_center_fragment);
        mWXAPI = WXAPIFactory.createWXAPI(this, WX_APPID, false);
        mWXAPI.registerApp(WX_APPID);

//        WXTextObject textObject = new WXTextObject();
//        textObject.text = "Hello 微信";
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObject;
//        msg.description = "sadadadasadas";
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = System.currentTimeMillis() + "";
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        mWXAPI.sendReq(req);
        mWXAPI.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        FMLog.d(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        FMLog.d(TAG, "onResp");
    }
}

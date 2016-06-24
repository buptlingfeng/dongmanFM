package com.dongman.fm.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.utils.FMLog;
import com.dongman.fm.weibo.WeiboConstant;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by liuzhiwei on 16/4/24.
 */
public class SharePanel extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "SharePanel";
    private static final int WEBPAGE = 1;
    private static final int IMAGE   = 2;

    private static final String WX_APPID = "wx4dc8c8e6b65be760";

    //微信分享
    private IWXAPI mWXAPI;
    private WebPageInfo mWebPageObject;

    //微博分享
    private IWeiboShareAPI mWeiboShareAPI;

    private Activity mActivity;
    private View friends, weChat, qq, weibo;

    public SharePanel(Activity activity) {
        super(activity);
        mActivity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.share_panel,null);
        initView(view);
        setContentView(view);
    }

    private void initView (View view) {
        friends = view.findViewById(R.id.share_friends);
        weChat = view.findViewById(R.id.share_wechat);
        qq = view.findViewById(R.id.share_qq);
        weibo = view.findViewById(R.id.share_weibo);
        friends.setOnClickListener(this);
        weChat.setOnClickListener(this);
        qq.setOnClickListener(this);
        weibo.setOnClickListener(this);
    }

    public SharePanel build(WebPageInfo webPageObject) {
        mWebPageObject = webPageObject;
        return this;
    }

    public void show() {

        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.share_panel_animation);
        ColorDrawable colorDrawable = new ColorDrawable(0x401a181a);
        setBackgroundDrawable(colorDrawable);
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_friends:
                wxShare(SendMessageToWX.Req.WXSceneTimeline, WEBPAGE);
                break;
            case R.id.share_wechat:
                wxShare(SendMessageToWX.Req.WXSceneSession, WEBPAGE);
                break;
            case R.id.share_qq:
                break;
            case R.id.share_weibo:
                weiBoShare();
                break;
        }
    }

    //微信分享
    private void wxShare(int scene, int type) {
        mWXAPI = WXAPIFactory.createWXAPI(mActivity, WX_APPID, false);
        mWXAPI.registerApp(WX_APPID);

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = mWebPageObject.shareUrl;

        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = mWebPageObject.shareTitle;
        msg.description = mWebPageObject.shareDes;
        msg.thumbData = ImageUtils.bmpToByteArray(mWebPageObject.bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "";
        req.message = msg;
        req.scene = scene;
        mWXAPI.sendReq(req);
        mWXAPI.handleIntent(mActivity.getIntent(), new WXAPIEventHandler());
    }

    private void weiBoShare() {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, WeiboConstant.WB_APPKEY);
        mWeiboShareAPI.registerApp();

        FMLog.d(TAG, "weiboshare");

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        weiboMessage.textObject = new TextObject();
//
//        weiboMessage.textObject.text = "初始化微博的分享消息 if (hasText)";

        WebpageObject webpageObject = new WebpageObject();
        webpageObject.identify = Utility.generateGUID();
        webpageObject.title = "初始化微博的分享消息";
        webpageObject.description = "这是我第一次尝试去分享微博，网页版本的微博信息";
        webpageObject.defaultText = "Webpage 默认文案";
        webpageObject.actionUrl = "http://m.dongman.fm/review/detail/13837";
        webpageObject.setThumbImage(ImageUtils.convertDrawable2BitmapSimple(mActivity.getResources().getDrawable(R.drawable.search_image)));

        weiboMessage.mediaObject = webpageObject;

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        mWeiboShareAPI.sendRequest(mActivity, request); //发送请求消息到微博,唤起微博分享界面
//        Intent intent = new Intent(mActivity, WeiboShareActivity.class);
//        mActivity.startActivity(intent);
    }

    class WXAPIEventHandler implements IWXAPIEventHandler {

        @Override
        public void onReq(BaseReq baseReq) {
            FMLog.d(TAG, "onReq");
        }

        @Override
        public void onResp(BaseResp baseResp) {
            FMLog.d(TAG, "onResp");
        }
    }


    class WebPageInfo {
        public String shareUrl, shareTitle, shareDes;
        public Bitmap bitmap;
    }

}

package com.dongman.fm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.LoginActivity;
import com.dongman.fm.ui.activity.QQLogingActivity;
import com.dongman.fm.ui.activity.WeiboLoginActivity;
import com.dongman.fm.ui.activity.WeiboShareActivity;
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
 * Created by liuzhiwei on 16/4/15.
 */
public class UserCenterFragment extends BaseFragment {

    private static final String TAG = "UserCenterFragment";
    private static final String WX_APPID = "wx4dc8c8e6b65be760";

    private IWXAPI mWXAPI;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_center_fragment, container, false);
        initView(root);
        return root;
    }

    private void initView(View view) {
        View userCenter = view.findViewById(R.id.user_center);
        View myCollection = view.findViewById(R.id.my_collection);
        View myArtical = view.findViewById(R.id.my_artical);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.user_center:
                        wxLogig();
                        break;
                    case R.id.my_collection:
                        weiboLogin();
                        break;
                    case R.id.my_artical:
                        qqLogin();
                        break;
                }
            }
        };

        userCenter.setOnClickListener(listener);
        myCollection.setOnClickListener(listener);
        myArtical.setOnClickListener(listener);
    }
    //微博登陆
    private void weiboLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    //微博分享
    private void weiboShare() {

        Intent intent = new Intent(getActivity(), WeiboShareActivity.class);
        getActivity().startActivity(intent);
    }

    private void qqLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }


    //微信分享
    private void wxLogig() {
//        Intent intent = new Intent(getActivity(), WXEntryActivity.class);
//        getActivity().startActivity(intent);
        mWXAPI = WXAPIFactory.createWXAPI(getActivity(), WX_APPID, false);
        mWXAPI.registerApp(WX_APPID);

        WXTextObject textObject = new WXTextObject();
        textObject.text = "Hello 微信";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = "sadadadasadas";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "";
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        mWXAPI.sendReq(req);
        mWXAPI.handleIntent(getActivity().getIntent(), new WXAPIEventHandler());
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
}

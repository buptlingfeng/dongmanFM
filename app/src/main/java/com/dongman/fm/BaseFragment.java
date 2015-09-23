package com.dongman.fm;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.network.OkHttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by liuzhiwei on 15/6/10.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void getImage(String url, ImageView imageView) {
        ImageUtils.getImage(getActivity(), url, imageView);
    }

    public void asyncGet(String url, IRequestCallBack requestCallBack) {
        asyncGet(url, null, requestCallBack);
    }

    public void asyncGet(String url, Map<String,String> parmas, IRequestCallBack requestCallBack) {
        if(parmas != null) {
            String resultUrl = OkHttpUtil.attachHttpGetParams(url, parmas);
            Request request =  new Request.Builder()
                    .url(resultUrl)
                    .build();
            asyncGet(request, requestCallBack);
        } else {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            asyncGet(request,requestCallBack);
        }
    }

    public void asyncGet(String url, String key, String value, IRequestCallBack requestCallBack) {
        String resultUrl = OkHttpUtil.attachHttpGetParam(url,key,value);
        Request request = new Request.Builder()
                .url(resultUrl)
                .build();
        asyncGet(request, requestCallBack);
    }

    public void asyncGet(Request request, final IRequestCallBack requestCallBack) {
        if(request == null) {
            Log.e("BaseActivity.async", "the request is null!");
            return;
        }
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if(requestCallBack != null) {
                    requestCallBack.onFailure(request,e);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(requestCallBack != null) {
                    requestCallBack.onResponse(response);
                }
            }
        };

        OkHttpUtil.asyncGet(request, callback);
    }
}
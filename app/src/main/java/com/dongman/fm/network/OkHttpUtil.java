package com.dongman.fm.network;

import android.text.TextUtils;
import android.util.Log;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzhiwei on 15/7/12.
 */
public class OkHttpUtil {
    public static final String TAG = "OkHttpUtil";
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
//    public static final MediaType BYTE_TYPE = MediaType.parse("application/byte");
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

//    static{
//        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
//    }

    public static Response get(String url) throws IOException{

        Request request =  new Request.Builder()
                .url(url)
                .build();
        return syncGet(request);
    }

    /**
     * 同步方法
     * @param request
     * @return
     * @throws IOException
     */
    public static Response syncGet(Request request) throws IOException{
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     * @param request
     * @param responseCallback
     */
    public static void asyncGet(Request request, Callback responseCallback){
        if(responseCallback != null) {
            mOkHttpClient.newCall(request).enqueue(responseCallback);
        } else {
            Log.e(TAG, "asyncGet:The responseCallback is null!");
        }
    }



    public static Response syncPost(String url, JSONObject json) throws IOException{

        if(json != null) {
            RequestBody body = RequestBody.create(JSON_TYPE, json.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = mOkHttpClient.newCall(request).execute();
            return response;
        } else {

            return null;
        }
    }

    public static void asyncPost(String url, JSONObject json, Callback responseCallback) {
        if(json != null && responseCallback != null) {
            RequestBody body = RequestBody.create(JSON_TYPE, json.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            mOkHttpClient.newCall(request).enqueue(responseCallback);
        } else {
            Log.e(TAG, "The json or the callback is null!");
        }
    }





    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 这里使用了HttpClinet的API。只是为了方便
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params){
        if(params == null || params.size() == 0)
            return null;
        return URLEncodedUtils.format(params, CHARSET_NAME);
    }
    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params){
        String urlParams = formatParams(params);
        if(TextUtils.isEmpty(urlParams)) {
            return url;
        } else {
            return url + "?" + urlParams;
        }
    }

    public static String attachHttpGetParams(String url, Map<String, String> params) {

        return attachHttpGetParams(url, convertMap(params));
    }

    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }


    /**
     * 将Map的数组结构转化成BasicNameValuePair的形式
     * @param map
     * @return
     */
    public static List<BasicNameValuePair> convertMap(Map<String, String> map) {
        if(map == null || map.size() == 0) {
            return null;
        }
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(),
                    entry.getValue()));
        }
        return params;
    }
}

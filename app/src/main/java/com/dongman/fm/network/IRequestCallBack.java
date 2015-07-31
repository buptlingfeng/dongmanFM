package com.dongman.fm.network;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by liuzhiwei on 15/7/16.
 */
public interface IRequestCallBack {

    public void onFailure(Request request, IOException e);

    public void onResponse(Response response) throws IOException;

}

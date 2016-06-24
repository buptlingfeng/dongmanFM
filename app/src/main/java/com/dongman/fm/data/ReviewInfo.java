package com.dongman.fm.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/3/26.
 */
public class ReviewInfo {
    public String title;
    public String summary;
    public String createTime;
    public String id;


    public static ReviewInfo create(JSONObject object) {
        ReviewInfo reviewInfo = null;
        try {
            if (object != null) {
                reviewInfo = new ReviewInfo();
                reviewInfo.title = object.getString("title");
                reviewInfo.summary = object.getString("short_content");
                reviewInfo.createTime = object.getString("create_time");
                reviewInfo.id = object.getString("id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewInfo;
    }
}

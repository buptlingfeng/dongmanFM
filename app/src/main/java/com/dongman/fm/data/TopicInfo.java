package com.dongman.fm.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/3/27.
 */
public class TopicInfo {

    public String id;
    public String title;
    public String summary;
    public String imageUrl;
    public String createTime;
    public String collectCount;
    public String subjectCount;
    public String commentCount;
    public String[] category;

    public static TopicInfo create(JSONObject object) {
        TopicInfo info = null;
        try {
            if (object != null) {
                info = new TopicInfo();
                info.id = object.getString("id");
                info.title = object.getString("title");
                info.summary = object.getString("summary");
                info.imageUrl = object.getString("img_url");
                info.createTime = object.getString("create_time");
                info.collectCount = object.getString("collect_count");
                info.subjectCount = object.getString("subject_count");
                info.commentCount = object.getString("comment_count");
                JSONArray catgorys = object.getJSONArray("category");
                if (catgorys.length() > 0) {
                    info.category = new String[catgorys.length()];
                    for (int i = 0; i < catgorys.length(); i++) {
                        info.category[i] = catgorys.getString(i);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }
}

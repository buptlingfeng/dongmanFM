package com.dongman.fm.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/3/20.
 */
public class CommentData {

    public String avatarUrl;
    public String content;
    public String createTime;
    public String userName;
    public String userId;
    public int votes;

    public static CommentData create(JSONObject data) {
        CommentData result = null;
        try {
            if (data != null) {
                result = new CommentData();
                result.avatarUrl = data.getString("avatar_url");
                result.content = data.getString("content");
                result.createTime = data.getString("create_time");
                result.userName = data.getString("user_name");
                result.userId = data.getString("user_id");
                result.votes = data.getInt("votes");
            }
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

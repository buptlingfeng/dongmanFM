package com.dongman.fm.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/3/20.
 */
public class RelativeRecommend {

    public String title;
    public String imageUrl;
    public String id;

    public static RelativeRecommend create(JSONObject object) {
        RelativeRecommend anime = null;
        try {
            if (object != null) {
                anime = new RelativeRecommend();
                anime.title = object.getString("title");

                if (object.has("img_url")) {
                    anime.imageUrl = object.getString("img_url");
                } else if (object.has("image_url")) {
                    anime.imageUrl = object.getString("image_url");
                }

                if (object.has("id")) {
                    anime.id = object.getString("id");
                } else if (object.has("subject_id")) {
                    anime.id = object.getString("subject_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return anime;
    }
}

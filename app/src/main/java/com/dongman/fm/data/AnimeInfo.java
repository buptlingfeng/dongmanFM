package com.dongman.fm.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 16/3/26.
 */
public class AnimeInfo {

    public String id;
    public String title;
    public String summary;
    public String genres;
    public long   scoreAllAvg;
    public String imageLarge;
    public String state;
    public String rateCount;

    public static AnimeInfo create(JSONObject data) {
        AnimeInfo animeInfo = null;
        if (data != null) {
            try {
                animeInfo = new AnimeInfo();
                animeInfo.id = data.getString("subject_id");
                animeInfo.title = data.getString("title");
                animeInfo.summary = data.getString("summary");
                animeInfo.genres = data.getString("genres");
                animeInfo.scoreAllAvg = data.getLong("score_all_avg");
                animeInfo.imageLarge = data.getString("img_large");
                animeInfo.rateCount = data.getString("rate_count");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return animeInfo;
    }
}

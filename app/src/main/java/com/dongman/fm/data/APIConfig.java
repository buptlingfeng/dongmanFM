package com.dongman.fm.data;

/**
 * Created by liuzhiwei on 15/7/20.
 */
public class APIConfig {

    public static final String HOST = "http://api.dongman.fm";

    public static final String TOPIC_LIST = HOST + "/topic/getlist";

    public static final String MANPIN_LIST = HOST + "/review/getlist";

    public static final String TOPIC_DETAIL   = HOST + "/topic/detail";

    public static final String MANPING_DETAIL = HOST + "/review/detail";

    public static final String SUBJECT_DETAIL = HOST + "/subject/detail";

    public static final String ARTICAL_LIST   = HOST + "/article/getlist";

    public static final String ARTICAL_DETAIL = HOST + "/article/detail";

    public static final String SEARCH_API = HOST + "/search/q";

    public static final String DETAIL_API = HOST + "/api/detail";

    public static final String LOGIN_OTHER_API = HOST + "/account/login_other";
}

package com.dongman.fm.login;

import android.content.Context;

/**
 * Created by liuzhiwei on 16/4/24.
 */
public class Login {

    public static final int DONGMANFM = 1;
    public static final int WEIBO = 2;
    public static final int WEIXIN = 3;
    public static final int QQ = 4;

    private static Login INSTANCE;

    private Context mContext;
    private int currentLoginType;

    private Login(Context context){
        mContext = context;
    }

    public static Login getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Login(context);
        }
        return INSTANCE;
    }

    private boolean logout() {

        switch (currentLoginType) {
            case DONGMANFM:
                break;
            case WEIBO:
                break;
            case WEIXIN:
                break;
            case QQ:
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 登陆接口
     * @param type 指定通过哪种方式进行登陆，包括微信、微博、QQ、自主的登陆等四种方式
     */
    private void login(int type) {

        currentLoginType = type;

    }
}

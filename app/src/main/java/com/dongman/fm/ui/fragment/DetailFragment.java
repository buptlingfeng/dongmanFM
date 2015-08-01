package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.BaseFragment;
import com.dongman.fm.ui.fragment.data.CommentAdapter;
import com.dongman.fm.ui.fragment.data.RecommendAdapter;
import com.dongman.fm.ui.view.CustomListView;
import com.dongman.fm.ui.view.ExpandableTextView;
import com.dongman.fm.ui.view.loading.Titanic;
import com.dongman.fm.ui.view.loading.TitanicTextView;
import com.dongman.fm.ui.view.recycleview.widget.TwoWayView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by liuzhiwei on 15/7/11.
 */
public class DetailFragment extends BaseFragment {

    private static final String TAG = "DetailFragment";
    private static final int DETAIL_DATA_ARRIVED_FAILED = 0;
    private static final int DETAIL_DATA_ARRIVED_SUCCEED = 1;
    private static final int RECOMMEND_DATA_ARRIVED_FAILED = 2;
    private static final int RECOMMEND_DATA_ARRIVED_SUCCEED = 3;

    private String          mURL;

    private RelativeLayout  mRootView;
    private TwoWayView      mRecommendView;
    private RecommendAdapter mRecommendAdapter;
    private CustomListView  mCommentList;
    private CommentAdapter  mCommentAdapter;

    private Dialog          mLoaddingDialog;
    private Titanic         mTitanic;

    private Handler         mHandler;
    private JSONObject      mData;

    public DetailFragment(String params) {
        mURL = params;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page_fragment, container, false);
        initLoadingView();
        init(view);
        return view;
    }

    private void init(final View root) {

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DETAIL_DATA_ARRIVED_SUCCEED:
                        if(mData == null) {
                            Log.e(TAG, "The json data is null !");
                        }
//                        mProgressView.setVisibility(View.GONE);
                        mRootView.setVisibility(View.VISIBLE);
                        initView(root, mData);
                        mTitanic.cancel();
                        mLoaddingDialog.cancel();
                        break;
                    case DETAIL_DATA_ARRIVED_FAILED:
                        break;
                    case RECOMMEND_DATA_ARRIVED_FAILED:
                        break;
                    case RECOMMEND_DATA_ARRIVED_SUCCEED:
                        mRecommendAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };

        mRootView = (RelativeLayout)root.findViewById(R.id.detail_root_content);
        mRootView.setVisibility(View.GONE);
//        mProgressView = root.findViewById(R.id.detail_progress_view);

        asyncGet(mURL, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "数据请求失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = new String(response.body().bytes());
                try {
                    mData = new JSONObject(result);
                    mHandler.sendEmptyMessage(DETAIL_DATA_ARRIVED_SUCCEED);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "数据请求成功");
            }
        });

    }

    private void initView(View root, final JSONObject data) {

        mRecommendView = (TwoWayView) root.findViewById(R.id.recommend_list);
        mRecommendView.setHasFixedSize(true);
        mRecommendView.setLongClickable(true);
        mRecommendAdapter = new RecommendAdapter(getActivity());
        mRecommendView.setAdapter(mRecommendAdapter);

        mCommentList = (CustomListView) root.findViewById(R.id.comment_list);
        mCommentAdapter = new CommentAdapter(getActivity());
        mCommentList.setAdapter(mCommentAdapter);
        mCommentList.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.more_footer, null));

        AnimeDescription anime = AnimeDescription.make(data);
        if(anime != null) {
            //获取推荐信息
            getRecommendAnimes(new IRequestCallBack() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String result = new String(response.body().bytes());
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object == null)
                            return;
                        JSONArray array = object.getJSONArray("data");
                        if (array == null || array.length() == 0)
                            return;
                        List<RecommendAdapter.RecommendData> recommendList = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            RecommendAdapter.RecommendData recommendData = RecommendAdapter.RecommendData.make(obj);
                            if (recommendData != null) {
                                recommendList.add(recommendData);
                            }
                            mRecommendAdapter.setData(recommendList);
                        }
                        mHandler.sendEmptyMessage(RECOMMEND_DATA_ARRIVED_SUCCEED);
                        Log.i(TAG, "已经更新页面");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, anime);

            ImageView imageView = (ImageView) root.findViewById(R.id.detail_anime_image);
            getImage(anime.imageUrl, imageView);
            TextView animeSubtype = (TextView) root.findViewById(R.id.anime_subtype);
            animeSubtype.setText("篇幅：" + anime.subType);
            TextView animeCountries = (TextView) root.findViewById(R.id.anime_countries);
            animeCountries.setText("国家地区：" + anime.country);
            TextView animeGenres = (TextView) root.findViewById(R.id.anime_genres);
            animeGenres.setText("类型：" + anime.casts);
            TextView animeCasts = (TextView) root.findViewById(R.id.anime_casts);
            animeCasts.setText("声优：" + anime.genres);
            ExpandableTextView animeSummary = (ExpandableTextView) root.findViewById(R.id.anime_summary);
            animeSummary.setText(anime.summary);

            LinearLayout rateContainer = (LinearLayout) root.findViewById(R.id.anime_average_star_container);
            int childCount = rateContainer.getChildCount();
            int starNum = (int) Math.ceil(anime.rateAverage / 2);
            for(int i = 0; i < childCount - 2 && i < starNum; i++) {
                ImageView childView = (ImageView)rateContainer.getChildAt(i);
                childView.setImageResource(R.drawable.icon_star_yellow);
            }

            TextView animeAverage = (TextView) root.findViewById(R.id.anime_average);
            animeAverage.setText(anime.rateAverage + "分");

        } else {
            //获取数据失败的情况
        }

    }

    private void initLoadingView() {
        mLoaddingDialog = new Dialog(getActivity(), R.style.Dialog_Fullscreen);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.loading_dialog, null, false);
        mLoaddingDialog.setContentView(view);
        mLoaddingDialog.show();
        TitanicTextView tv = (TitanicTextView) view.findViewById(R.id.my_text_view);
        tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Satisfy-Regular.ttf"));
        mTitanic = new Titanic();
        mTitanic.start(tv);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getRecommendAnimes(IRequestCallBack callBack, AnimeDescription animeDes) {

        if(animeDes == null)
            return;

        Map<String, String> params = new HashMap<>();
        params.put("subject_id",animeDes.animeID);
        params.put("page", "1");
        params.put("size", "9");

        asyncGet(APIConfig.RECOMMEND_API,params,callBack);

    }

    public static class AnimeDescription {

        private String animeID;//ID字段

        public String subType;//是否是电影或者TV
        public String summary;//简介
        public String genres;//类型、分类
        public String casts;//声优
        public String imageUrl;//图片链接
        public double rateAverage;//评分
        public int browseCount;//浏览次数
        public int rateCount;//评分人数
        public int    playCount;//播放次数
        public String country;//动漫产地
        public int explodeCount;//当前集数

        public JSONArray reviews;

        public static AnimeDescription make(JSONObject json) {
            try{
                if(json == null) {
                    return null;
                }
                AnimeDescription anime = new AnimeDescription();
                anime.animeID   = json.getString("subject_id");
                anime.subType   = json.getString("subtype");//是否是电影或者TV
                anime.summary   = json.getString("summary");//简介
                anime.genres    = json.getString("genres");//类型、分类
                anime.casts     = json.getString("casts");//声优
                anime.imageUrl  = json.getString("img_large");//
                anime.rateAverage   = json.getDouble("score_all_avg");
                anime.browseCount   = Integer.parseInt(json.getString("browse_count"));
                anime.rateCount = Integer.parseInt(json.getString("rate_count"));
                anime.playCount = Integer.parseInt(json.getString("play_count"));
                anime.country   = json.getString("countries");
                anime.explodeCount  = Integer.parseInt(json.getString("all_explode"));
                anime.reviews   = json.getJSONArray("Reviews");
                return anime;
            } catch (JSONException e) {
                Log.e(TAG, "the AnimeDescription make failed");
                e.printStackTrace();
                return null;
            }
        }

        public String getAnimeID() {
            return animeID;
        }
    }

}

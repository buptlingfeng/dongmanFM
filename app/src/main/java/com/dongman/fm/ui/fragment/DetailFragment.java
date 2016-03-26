package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.data.AnimeInfo;
import com.dongman.fm.data.CommentData;
import com.dongman.fm.data.RelativeRecommend;
import com.dongman.fm.data.ReviewInfo;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.fragment.adapter.RelativeAdapter;
import com.dongman.fm.ui.view.CircleImageView;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.dongman.fm.utils.FMLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by liuzhiwei on 15/7/11.
 */
public class DetailFragment extends BaseFragment {

    private static final String TAG = "DetailFragment";
    private static final int ANIME_BASE     = 1;
    private static final int ANIME_RELS     = 2;
    private static final int MANTIE_RELS    = 3;
    private static final int MANPING_RELS   = 4;
    private static final int ANIME_COMMENTS = 5;

    private static final int MORE_COMMENTS  = 6;

    private String          mID;
    private RecyclerView    mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ModuleAdapter mModuleAdapter;
    private BaseActivity mActivity;


    private AnimeInfo mAnimeInfo;
    private List<RelativeRecommend> mAnimesRecommends = new ArrayList<>();
    private List<RelativeRecommend> mMantieRecommends = new ArrayList<>();
    private List<ReviewInfo> mReviewsRecommends = new ArrayList<>();
    private List<CommentData> mComments = new ArrayList<>();


    private Handler mHandler;

    private boolean isDataReady = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mID = bundle.getString("id");

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case REFRESH_UI:
                        isDataReady = true;
                        mModuleAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_page_fragment, container, false);
        initView(view);

        return view;
    }

    private void initView(View root) {
        mRecyclerView = (RecyclerView)root.findViewById(R.id.recycleview);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mModuleAdapter = new ModuleAdapter(mContext);
        mRecyclerView.setAdapter(mModuleAdapter);
        getData(mID);

    }

    public void getData(String id) {
        asyncGet(APIConfig.SUBJECT_DETAIL, "id", id, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject data = new JSONObject(response.body().string());
                    data = data.getJSONObject("data");
                    JSONObject subject = data.getJSONObject("subject");
                    mAnimeInfo = AnimeInfo.create(subject);
                    //处理推荐动漫逻辑
                    if (!data.isNull("relative_subjects")) {
                        JSONArray relativeSubjects = data.getJSONArray("relative_subjects");
                        if (relativeSubjects != null && relativeSubjects.length() > 0) {
                            mAnimesRecommends = new ArrayList<>(relativeSubjects.length());
                            for (int i = 0; i < relativeSubjects.length(); i++) {
                                JSONObject object = relativeSubjects.getJSONObject(i);
                                RelativeRecommend animeInfo = RelativeRecommend.create(object);
                                mAnimesRecommends.add(animeInfo);
                            }
                        }
                    }
                    //处理推荐漫贴的逻辑
                    if (!data.isNull("relative_articles")) {
                        JSONArray relativeArticles = data.getJSONArray("relative_articles");
                        if (relativeArticles != null && relativeArticles.length() > 0) {
                            mMantieRecommends = new ArrayList<>();
                            for (int i = 0; i < relativeArticles.length(); i++) {
                                JSONObject object = relativeArticles.getJSONObject(i);
                                RelativeRecommend mantie = RelativeRecommend.create(object);
                                mMantieRecommends.add(mantie);
                            }
                        }
                    }

                    //处理推荐漫评的逻辑
                    JSONArray reviews = data.getJSONArray("reviews");
                    if (reviews != null && reviews.length() > 0) {
                        mReviewsRecommends = new ArrayList<>();
                        for (int i = 0; i < reviews.length(); i++) {
                            JSONObject object = reviews.getJSONObject(i);
                            ReviewInfo reviewInfo = ReviewInfo.create(object);
                            mReviewsRecommends.add(reviewInfo);
                        }
                    }
                    //处理评论逻辑
                    JSONArray comments = data.getJSONArray("comments");
                    if (comments != null && comments.length() > 0) {
                        mComments = new ArrayList<>();
                        for (int i = 0; i < comments.length(); i++) {
                            JSONObject object = comments.getJSONObject(i);
                            CommentData comment = CommentData.create(object);
                            mComments.add(comment);
                        }
                    }
                    mHandler.sendEmptyMessage(REFRESH_UI);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class ModuleAdapter extends RecyclerView.Adapter<ModuleViewHolder> {
        private LayoutInflater mInflater;
        ModuleAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public ModuleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            int id = 0;
            switch (viewType) {
                case ANIME_BASE:
                    id = R.layout.anime_base;
                    break;
                case ANIME_RELS:
                    id = R.layout.relative_anims_recommend;
                    break;
                case MANTIE_RELS:
                    id = R.layout.relative_recommend;
                    break;
                case MANPING_RELS:
                    id = R.layout.manping_item;
                    break;
                case ANIME_COMMENTS:
                    id = R.layout.review_item;
                    break;
                case MORE_COMMENTS:
                    id = R.layout.more_footer;
                    break;
            }

            View view = mInflater.inflate(id, null, false);
            return new ModuleViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(ModuleViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemViewType(int position) {

            switch (position) {
                case 0 :
                    return ANIME_BASE;
                case 1 :
                    return ANIME_RELS;
                case 2 :
                    return MANTIE_RELS;
                default:
                    if (mReviewsRecommends.size() + 3 > position) {
                        return MANPING_RELS;
                    } else if (mComments.size() + mReviewsRecommends.size() + 3 > position){
                        return ANIME_COMMENTS;
                    } else {
                        FMLog.d(TAG, "More : " + position);
                        return MORE_COMMENTS;
                    }
            }
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            int count = 0;
            if (isDataReady) {
                 count = 1 + (mAnimesRecommends.size() > 0 ? 1:0) + (mMantieRecommends.size()>0 ? 1:0) + mReviewsRecommends.size() + mComments.size() + 1;
            }

            return count;
        }
    }

    class ModuleViewHolder extends RecyclerView.ViewHolder {

        View viewHolder;
        int viewType;
        public ModuleViewHolder(View itemView, int type) {
            super(itemView);
            viewHolder = itemView;
            viewType = type;
        }


        public void bindView(int position) {

            switch (viewType) {
                case ANIME_BASE:
                    ImageView animeImage = (ImageView) itemView.findViewById(R.id.detail_anime_image);
                    TextView average = (TextView) itemView.findViewById(R.id.anime_average);
                    TextView averageCount = (TextView) itemView.findViewById(R.id.average_count);
                    ImageUtils.getImage(mContext, mAnimeInfo.imageLarge, animeImage);
                    average.setText(mAnimeInfo.scoreAllAvg + "分");
                    averageCount.setText(mAnimeInfo.rateCount);
                    break;
                case ANIME_RELS:
                    RecyclerView recyclerView1 = (RecyclerView) viewHolder.findViewById(R.id.recycleview);
                    LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
                    linearLayoutManager1.setOrientation(OrientationHelper.HORIZONTAL);
                    recyclerView1.setLayoutManager(linearLayoutManager1);
                    RelativeAdapter animesAdapter = new RelativeAdapter(mActivity, RelativeAdapter.ANIMES);
                    recyclerView1.setAdapter(animesAdapter);
                    recyclerView1.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));
                    animesAdapter.setData(mAnimesRecommends);
                    break;
                case MANTIE_RELS:
                    RecyclerView recyclerView = (RecyclerView) viewHolder.findViewById(R.id.recycleview);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    RelativeAdapter mantieAdapter = new RelativeAdapter(mActivity, RelativeAdapter.MANTIE);
                    recyclerView.setAdapter(mantieAdapter);
                    recyclerView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));
                    mantieAdapter.setData(mMantieRecommends);
                    break;
                case MANPING_RELS:
                    TextView manpingTitle = (TextView) viewHolder.findViewById(R.id.manping_title);
                    TextView manpingContent = (TextView) viewHolder.findViewById(R.id.manping_content);
                    ReviewInfo reviewInfo = mReviewsRecommends.get(position - 3);
                    manpingTitle.setText(reviewInfo.title);
                    manpingContent.setText(reviewInfo.summary);
                    break;
                case ANIME_COMMENTS:
                    CircleImageView commentAvatar = (CircleImageView) itemView.findViewById(R.id.comment_avatar);
                    TextView commentDate   = (TextView) itemView.findViewById(R.id.comment_date);
                    TextView commentContent = (TextView) itemView.findViewById(R.id.comment_content);
                    TextView commentNick   = (TextView) itemView.findViewById(R.id.comment_nick);

                    CommentData data = mComments.get(position - (3 + mReviewsRecommends.size()));

                    commentNick.setText(data.userName);
                    commentDate.setText(data.createTime);
                    commentContent.setText(data.content);
                    ImageUtils.getImage(mContext, data.avatarUrl, commentAvatar);
                    break;
                case MORE_COMMENTS:
                    TextView hint = (TextView) viewHolder.findViewById(R.id.footer_hint_words);
                    hint.setText("点击查看更多");
                    break;
            }
        }
    }


}

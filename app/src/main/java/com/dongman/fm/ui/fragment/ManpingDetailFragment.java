package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.fragment.adapter.CommentAdapter;
import com.dongman.fm.ui.view.CircleImageView;
import com.dongman.fm.ui.view.CustomListView;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by liuzhiwei on 15/8/13.
 */
public class ManpingDetailFragment extends BaseFragment {

    private static final String TAG = ManpingDetailFragment.class.getSimpleName();

    private static final int DATA_ARRIVE = 1;

    private TextView mManpinOwnerName;
    private CircleImageView mManpingOwnerAvatar;
    private TextView mManpingUpdateTime;

    private TextView mManpingTitle;
    private TextView mManpingContent;
    private TextView mManpingVote;

    private CustomListView mManpingCommentsList;
    private CommentAdapter mCommentAdapter;

    private Handler mHandler;

    private String mID;

    private JSONObject mBasicData;
    private JSONArray  mComments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mID = this.getArguments().getString("id");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manping_detail, null, false);
        init(view);
        return view;
    }

    private void init(View root) {
        mManpinOwnerName = (TextView) root.findViewById(R.id.manping_detail_user_name);
        mManpingOwnerAvatar = (CircleImageView) root.findViewById(R.id.manping_detail_user_avatar);
        mManpingUpdateTime = (TextView) root.findViewById(R.id.manping_detail_updatetime);

        mManpingTitle = (TextView) root.findViewById(R.id.manping_detail_title);
        mManpingContent = (TextView) root.findViewById(R.id.manping_detail_content);
        mManpingVote = (TextView) root.findViewById(R.id.manping_detail_vote);

        mManpingCommentsList = (CustomListView) root.findViewById(R.id.manping_detail_comments_list);
        mCommentAdapter = new CommentAdapter(getActivity());
        mManpingCommentsList.setAdapter(mCommentAdapter);
        mManpingCommentsList.setFocusable(false);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DATA_ARRIVE:
                        try {
                            String userName = mBasicData.getString("user_name");
                            mManpinOwnerName.setText(userName);
                            String url = mBasicData.getString("avatar_img_url");
                            ImageUtils.getImage(getActivity(), url, mManpingOwnerAvatar);
                            String updateTime = mBasicData.getString("create_time");
                            mManpingUpdateTime.setText(updateTime);

                            String manpingTitle = mBasicData.getString("title");
                            mManpingTitle.setText(manpingTitle);
                            String manpingContent = mBasicData.getString("content");
                            String resultContent = manpingContent.replaceAll("<p>","\r\n     ");
                            mManpingContent.setText(resultContent);
                            String votes = mBasicData.getString("vote_count");
                            mManpingVote.setText("(" + votes + ")");

                            mCommentAdapter.setData(mComments);
                            mCommentAdapter.notifyDataSetChanged();
                            if(mComments != null && mComments.length() > 0) {
                                addCommentFooter(true);
                            } else {
                                addCommentFooter(false);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        getData();
    }

    private void getData() {
        asyncGet(APIConfig.MANPING_DETAIL, "id", mID, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject data = object.getJSONObject("basic");
                    mBasicData = data;
                    if(!object.isNull("comments")) {
                        mComments = object.getJSONArray("comments");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.sendEmptyMessage(DATA_ARRIVE);
                }
            }
        });
    }

    private void addCommentFooter(boolean isMore) {
        Context context = getActivity();
        if(context == null) {
            Log.e(TAG, "addCommentFooter and the context is null;");
            return;
        }
        View footer = LayoutInflater.from(context).inflate(R.layout.more_footer,null);
        TextView hint = (TextView) footer.findViewById(R.id.footer_hint_words);

        if(isMore) {
            hint.setText("点击查看更多");
        } else {
            hint.setText("快去抢沙发~");
        }
        mManpingCommentsList.addFooterView(footer);

    }
}

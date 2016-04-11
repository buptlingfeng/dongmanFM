package com.dongman.fm.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.data.APIConfig;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.ui.activity.adapter.SearchListAdapter;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuzhiwei on 15/8/2.
 */
public class SearchActivity extends BaseActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    private static final int INVORK_KEYBOARD = 6;
    private static final int DATA_ARRIVE = 1;
    private static final int DATA_FAILED = 2;

    private Handler mHandler;
    private EditText mInputEdit;
    private View mBack;
    private ImageButton mSearchButton;
    private RecyclerView mSearchResultList;
    private LinearLayoutManager mLayoutManager;
    private SearchListAdapter mAdapter;
    private JSONArray mSearchListData;

    private String mSearchKeyword;
    private int mPageCount = 1;

    private boolean isLoadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {

        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case INVORK_KEYBOARD:
                        InputMethodManager inputManager = (InputMethodManager)mInputEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.showSoftInput(mInputEdit, 0);
                        break;
                    case DATA_ARRIVE:
//                        mAdapter.setData(mSearchListData);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case DATA_FAILED:
                        break;
                    default:
                        break;
                }
            }
        };

        mHandler.sendEmptyMessageDelayed(INVORK_KEYBOARD, 300);

        mBack = findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSearchButton = (ImageButton) findViewById(R.id.search_right_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mSearchKeyword)) {//TODO 检查mSearchKeyword是否是完全由空格组成
                    mPageCount = 1;
                    getSearchList(mSearchKeyword, mPageCount, true);
                } else {
                    //TODO 提示用户需要输入
                }
            }
        });

        mInputEdit = (EditText) findViewById(R.id.search_input);
        mInputEdit.setFocusable(true);
        mInputEdit.setFocusableInTouchMode(true);
        mInputEdit.requestFocus();

        mInputEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchKeyword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mInputEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        mInputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) mInputEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mPageCount = 1;
                    //TODO search
                    getSearchList(mSearchKeyword, mPageCount, true);
                    return true;

                }
                return false;
            }
        });


        mSearchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        mLayoutManager = new LinearLayoutManager(this);
        mSearchResultList.setLayoutManager(mLayoutManager);
        mAdapter = new SearchListAdapter(this);
        mSearchResultList.setAdapter(mAdapter);

        mSearchResultList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mAdapter.getItemCount();
                //lastVisibleItem >= totalItemCount - 1 表示剩下1个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 3 && dy > 0) {
                    if (isLoadingMore) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        getSearchList(mSearchKeyword, mPageCount++, false);//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = true;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    private void getSearchList(String keyWord, int page, final boolean isFrist) {
        Map<String,String> parms = new HashMap<>();
        parms.put("q",keyWord);
        parms.put("page", Integer.toString(page));

        asyncGet(APIConfig.SEARCH_API, parms, new IRequestCallBack() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(DATA_FAILED);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Message message = mHandler.obtainMessage();
                try {//TODO 数据结构改变，增加扩展字段来表示是否已经加载完毕
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject data = object.getJSONObject("data");

                    if(!data.isNull("list") ) {
                        mSearchListData = data.getJSONArray("list");
                        if(isFrist) {
                            mAdapter.setData(mSearchListData);
                            mPageCount = 2;
                        } else {
                            mAdapter.addData(mSearchListData);
                        }
                        message.what = DATA_ARRIVE;
                    } else {
                        message.what = DATA_FAILED;
                    }
                    isLoadingMore = false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(message);
                }
            }
        });
    }
}

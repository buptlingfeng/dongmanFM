package com.dongman.fm.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.dongman.fm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liuzhiwei on 15/9/8.
 */
public class SearchFragment extends BaseFragment {

    private Context mContext;
    private EditText mInputEdit;
    private GridView mRecommendWrods;
    private RecommendWordsAdapter mAdapter;


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.search_fragment);
        mContext = getActivity();
        mInputEdit = (EditText) findViewById(R.id.search_input);
        mInputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == mInputEdit.getId() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.search");
                    mContext.startActivity(intent);
                }
                return false;
            }
        });
        mRecommendWrods = (GridView) findViewById(R.id.recommend_search_list);
        mAdapter = new RecommendWordsAdapter(mContext);
        mRecommendWrods.setAdapter(mAdapter);
        JSONArray data = new JSONArray();
        for(int i = 0; i < 9; i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("key", "齐天大圣");
                data.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    class RecommendWordsAdapter extends BaseAdapter {

        LayoutInflater mInflater;
        JSONArray mData;

        RecommendWordsAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if(mData == null) {
                return 0;
            } else {
                return mData.length();
            }
        }

        public RecommendWordsAdapter setData(JSONArray array) {
            mData = array;
            return this;
        }

        public RecommendWordsAdapter addData(JSONArray array) {
            if(array != null && array.length() != 0) {
                for(int i = 0; i < array.length(); i++) {
                    try {
                        mData.put(array.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.search_hot_words_item,null);
            return view;
        }
    }
}

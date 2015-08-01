package com.dongman.fm.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 15/6/17.
 */
public class HomePageFragment extends BaseFragment {

    private Context mContext;

    private EditText mInputEdit;
    private GridView mRecommendContent;
//    private

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_homepage, container, false);
        initView(root);
        mContext = this.getActivity();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View root) {
        mInputEdit = (EditText) root.findViewById(R.id.search_input);
        mInputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(v.getId() == mInputEdit.getId() && event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Scroller scroller = new Scroller(HomePageFragment.this.getActivity());
                    Intent intent = new Intent();
                    intent.setAction("com.dongman.fm.search");
                    mContext.startActivity(intent);
                }
                return false;
            }
        });

        mRecommendContent = (GridView) root.findViewById(R.id.recommend_content_gridview);
        mRecommendContent.setAdapter(new RecommendContentAdapter(getActivity()));
    }


    private class RecommendContentAdapter extends BaseAdapter {

        List<String> mList;
        Context mContext;

        public RecommendContentAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
            for(int i = 0; i < 9; i++) {
                mList.add("推荐的：" + i);
            }
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.homepage_recommend_item, null);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.homepage_recommend_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(mList.get(position));
            return convertView;
        }

    }

    private class ViewHolder {
        TextView textView;
    }

}

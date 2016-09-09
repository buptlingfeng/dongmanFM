package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.BaseActivity;
import com.dongman.fm.ui.fragment.adapter.ReviewsListFragmentAdapter;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 15/12/24.
 */
public class CommentListFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = CommentListFragment.class.getSimpleName();

    private BaseActivity mActivity;
    private View mRootContainer;
    private RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private ReviewsListFragmentAdapter mAdater;

    private EditText mInput;
    private Button mDelete,mSendComment;
    private View mOpenCamera;

    private View mBack;

    @Override
    public void onAttach(Activity activity) {
        mActivity = (BaseActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_list_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View root) {
        mRootContainer = root.findViewById(R.id.root_container);
        mBack = root.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        mRecycleView = (RecyclerView) root.findViewById(R.id.recycleview);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLongClickable(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(mLinearLayoutManager);

        mAdater = new ReviewsListFragmentAdapter(mActivity);
        mRecycleView.setAdapter(mAdater);

        mInput = (EditText) root.findViewById(R.id.comment_input);
        mInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mSendComment.setVisibility(View.VISIBLE);
                    mDelete.setVisibility(View.VISIBLE);
                }
            }
        });



        mDelete = (Button) root.findViewById(R.id.clear_input);
        mSendComment = (Button) root.findViewById(R.id.send_comment);

        mDelete.setOnClickListener(this);
        mSendComment.setOnClickListener(this);
//        mRootContainer.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (view.getId() != R.id.comment_input) {
//                    InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                return false;
//            }
//        });
        mOpenCamera = root.findViewById(R.id.open_camera);
        mOpenCamera.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() != R.id.clear_input) {
            InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        switch (view.getId()) {
            case R.id.clear_input:
                mInput.setText("");
                break;
            case R.id.send_comment:
                mInput.setText("");
                mInput.clearFocus();
                InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mSendComment.setVisibility(View.INVISIBLE);
                mDelete.setVisibility(View.INVISIBLE);
                break;
            case R.id.open_camera:
                Intent intent2 = new Intent();
                intent2.setAction("com.dongman.fm.image");
                intent2.putExtra("type", 1);
                mActivity.startActivityForResult(intent2, 1);
                break;
        }
    }



}

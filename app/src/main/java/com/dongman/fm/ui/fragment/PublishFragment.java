package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongman.fm.R;
import com.dongman.fm.ui.activity.ArticalEditActivity;
import com.dongman.fm.ui.activity.BaseActivity;

/**
 * Created by liuzhiwei on 16/4/4.
 */
public class PublishFragment extends DialogFragment{

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void setStyle(int style, int theme) {
        super.setStyle(style, theme);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        //全屏dialog
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.publish_dialog_layout, null);
        initView(view);
        builder.setView(view);
        return builder.create();
    }

    private void initView(View view) {

        View action1 = view.findViewById(R.id.action1);
        View action2 = view.findViewById(R.id.action2);
        View action3 = view.findViewById(R.id.action3);
        View action4 = view.findViewById(R.id.action4);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.action1://打开相册
                        Intent intent1 = new Intent();
                        intent1.setAction("com.dongman.fm.image");
                        intent1.putExtra("type", 1);
                        getActivity().startActivityForResult(intent1, 1);
                        dismiss();
                        break;
                    case R.id.action2://打开相机
                        Intent intent2 = new Intent();
                        intent2.setAction("com.dongman.fm.image");
                        intent2.putExtra("type", 2);
                        getActivity().startActivityForResult(intent2, 1);
                        dismiss();
                        break;
                    case R.id.action3:

                        Intent intent3 = new Intent(getActivity(), ArticalEditActivity.class);
                        getActivity().startActivityForResult(intent3, 3);
                        dismiss();
                        break;
                    case R.id.action4:
                        Intent intent4 = new Intent(getActivity(), ArticalEditActivity.class);
                        getActivity().startActivityForResult(intent4, 3);
                        dismiss();
                        break;
                }
            }
        };

        action1.setOnClickListener(listener);
        action2.setOnClickListener(listener);
        action3.setOnClickListener(listener);
        action4.setOnClickListener(listener);
    }
}

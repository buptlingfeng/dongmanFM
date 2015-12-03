package com.dongman.fm.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dongman.fm.R;
import com.dongman.fm.BaseFragment;

/**
 * Created by liuzhiwei on 15/6/17.
 */
public class DiscoveryFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage_fragment_focus, container, false);
    }

}

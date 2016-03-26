package com.dongman.fm.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liuzhiwei on 16/3/2.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int left;
    private int right;
    private int top;
    private int bottom;

    public SpacesItemDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;

        // Add top margin only for the first item to avoid double left between items
        if(parent.getChildLayoutPosition(view) == 0)
            outRect.top = top;
    }
}

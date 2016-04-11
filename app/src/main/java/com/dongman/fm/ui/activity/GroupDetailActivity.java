package com.dongman.fm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.ui.fragment.PublishFragment;
import com.dongman.fm.ui.view.FullyLinearLayoutManager;
import com.dongman.fm.utils.FMLog;

/**
 * Created by liuzhiwei on 16/4/2.
 */
public class GroupDetailActivity extends BaseActivity {

    private static final String TAG = GroupDetailActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FullyLinearLayoutManager mLinearLayoutManager;

    private TextView mNav1, mNav2, mNav3, mNav4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        initView();
    }


    private void initView(){

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRecyclerView.setFocusable(false);
        mLinearLayoutManager = new FullyLinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ListAdapter adapter = new ListAdapter();
        mRecyclerView.setAdapter(adapter);

        mNav1 = (TextView) findViewById(R.id.nav1);
        mNav2 = (TextView) findViewById(R.id.nav2);
        mNav3 = (TextView) findViewById(R.id.nav3);
        mNav4 = (TextView) findViewById(R.id.nav4);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nav1:
                        changeNavState(mNav1);
                        break;
                    case R.id.nav2:
                        changeNavState(mNav2);
                        break;
                    case R.id.nav3:
                        changeNavState(mNav3);
                        break;
                    case R.id.nav4:
                        changeNavState(mNav4);
                        break;
                }
            }
        };
        mNav1.setOnClickListener(listener);
        mNav2.setOnClickListener(listener);
        mNav3.setOnClickListener(listener);
        mNav4.setOnClickListener(listener);
        changeNavState(mNav1);
    }

    private void changeNavState(TextView nav) {

        float size = 17;
        int color = this.getResources().getColor(R.color.tab_top_text_1);
        mNav1.setTextSize(size);
        mNav2.setTextSize(size);
        mNav3.setTextSize(size);
        mNav4.setTextSize(size);

        mNav1.setTextColor(color);
        mNav2.setTextColor(color);
        mNav3.setTextColor(color);
        mNav4.setTextColor(color);

        nav.setTextSize(20);
        nav.setTextColor(this.getResources().getColor(R.color.red));
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_avatar_toolbar_sample, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bianji:
                showDialog();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialog() {
        PublishFragment dialog = new PublishFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    private class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_recommend_item, parent, false);
            FMLog.d(TAG,"onCreateViewHolder");
            return new RecyclerView.ViewHolder(view) {};
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FMLog.d(TAG, "holder.itemView.setOnClickListener");
                    int k = mRecyclerView.getPaddingBottom() + mRecyclerView.getPaddingTop();
                    FMLog.d(TAG, "mRecyclerView.getPadding : " + k);
                    FMLog.d(TAG, "holder.itemView.getMeasuredHeight() : " + holder.itemView.getMeasuredHeight());

                    Intent intent = new Intent(GroupDetailActivity.this, ManpingActivity.class);
                    intent.putExtra("id","13786");
                    GroupDetailActivity.this.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

}

package com.dongman.fm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongman.fm.R;
import com.dongman.fm.image.ImageUtils;
import com.dongman.fm.ui.fragment.PublishFragment;
import com.dongman.fm.ui.utils.ToolsUtils;
import com.dongman.fm.ui.view.listview.SmoothListView;
import com.dongman.fm.utils.FMLog;
import com.dongman.fm.utils.UILImageLoader;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhiwei on 16/4/2.
 */
public class GroupDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = GroupDetailActivity.class.getSimpleName();

    private static final int HEADER = 0, FOOTER = -1, CONTENT = 1;

    private static final int IMAGE = 1, ARTICAL = 2;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private GroupDetailPostAdapter mAdapter;
    private Context mContext;

    private FloatingActionMenu mMenuBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        mContext = this;
        initView();
    }


    private void initView() {

        mMenuBlue = (FloatingActionMenu) findViewById(R.id.menu_blue);

        findViewById(R.id.open_camera).setOnClickListener(this);
        findViewById(R.id.open_photo).setOnClickListener(this);
        findViewById(R.id.open_post).setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter = new GroupDetailPostAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.open_camera:
                Intent intent2 = new Intent();
                intent2.setAction("com.dongman.fm.image");
                intent2.putExtra("type", 2);
                GroupDetailActivity.this.startActivityForResult(intent2, IMAGE);
                mMenuBlue.toggle(true);
                break;
            case R.id.open_photo:
                Intent intent1 = new Intent();
                intent1.setAction("com.dongman.fm.image");
                intent1.putExtra("type", 1);
                GroupDetailActivity.this.startActivityForResult(intent1, IMAGE);
                mMenuBlue.toggle(true);
                break;
            case R.id.open_post:
                Intent intent3 = new Intent(GroupDetailActivity.this, ArticalEditActivity.class);
                GroupDetailActivity.this.startActivityForResult(intent3, ARTICAL);
                mMenuBlue.toggle(true);
                break;
            case R.id.back:
                GroupDetailActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_avatar_toolbar_sample, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
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
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        if (bundle == null) {
            return;
        }
        if (requestCode == IMAGE) {

            ArrayList<String> photos = bundle.getStringArrayList("photos");
            String content = bundle.getString("content");
            PostItemData postItemData = new PostItemData();
            postItemData.content = content;
            postItemData.images = photos;
            postItemData.type = PostType.IMAGE;
            mAdapter.addData(postItemData);
            mAdapter.notifyDataSetChanged();
        } else if (requestCode == ARTICAL) {
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            PostItemData postItemData = new PostItemData();
            postItemData.content = content;
            postItemData.title = title;
            postItemData.type = PostType.ARITLE;
            mAdapter.addData(postItemData);
            mAdapter.notifyDataSetChanged();
        }

    }

    public void showDialog() {
        PublishFragment dialog = new PublishFragment();
        dialog.show(getFragmentManager(), "dialog");
    }

    private class GroupDetailPostAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        List<PostItemData> mData;
        LayoutInflater layoutInflater;
        Context context;

        GroupDetailPostAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            mData = new ArrayList<>();
            this.context = context;
        }

        public void setData(List<PostItemData> data) {
            if (data != null) {
                mData = data;
            }
        }

        public void addData(PostItemData data) {
            mData.add(0,data);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == HEADER) {
                view = layoutInflater.inflate(R.layout.group_detail_header, parent, false);
            } else if (viewType == FOOTER) {
                view = layoutInflater.inflate(R.layout.footer_view, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.state_recommend_item, parent, false);
            }
            return new ItemViewHolder(context, view);
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, int position) {
            if (mData == null || mData.size() < 1) {
                return;
            }
            switch (getItemViewType(position)) {
                case CONTENT:
                    holder.bindView(mData.get(position - 1));
                    break;
                case HEADER:
                    break;
                case FOOTER:
                    break;
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else if (position == getItemCount()) {
                return -1;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return mData.size() + 1;
        }
    }

    enum PostType{

        ARITLE(1), IMAGE(2);
        PostType(int i) {}
    }

    class PostItemData {
        String id;
        String userName;
        String createTime;
        String avatarUrl;
        String content;
        String title;
        String vote;
        String comment;
        PostType type;
        ArrayList<String> images;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        Context context;
        public ItemViewHolder(Context context,View itemView) {
            super(itemView);
            view = itemView;
            this.context = context;
        }

        public void bindView(PostItemData data) {
            View imageContainer = view.findViewById(R.id.images_container);

            if (data.type == PostType.ARITLE) {
                TextView content = (TextView) view.findViewById(R.id.message_content);
                imageContainer.setVisibility(View.GONE);
                if (data.content != null) {
                    content.setText(data.content);
                }
            } else if (data.type == PostType.IMAGE) {
                imageContainer.setVisibility(View.VISIBLE);
                ImageView imageView1 = (ImageView) view.findViewById(R.id.article_image1);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.article_image2);
                ImageView imageView3 = (ImageView) view.findViewById(R.id.article_image3);
                TextView textView = (TextView) view.findViewById(R.id.message_content);
                if(data.content != null) {
                    textView.setText(data.content);
                }
                if (data.images != null && data.images.size() > 0) {
                    for (int i = 0; i < 3; i++) {
                        String photoPath;
                        ImageView view = imageView1;
                        if (data.images.size() > i) {
                            photoPath = data.images.get(i);
                            photoPath = "file://" + photoPath;
                            switch (i) {
                                case 0:
                                    view = imageView1;
                                    break;
                                case 1:
                                    view = imageView2;
                                    break;
                                case 2:
                                    view = imageView3;
                                    break;
                            }
                            ImageUtils.getImage(context, photoPath, view,
                                    ToolsUtils.getScreenWidth(context)/3, ToolsUtils.getScreenHeigth(context));
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }
}

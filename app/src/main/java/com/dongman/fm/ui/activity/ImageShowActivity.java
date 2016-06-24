package com.dongman.fm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dongman.fm.R;
import com.dongman.fm.ui.view.SpacesItemDecoration;
import com.dongman.fm.utils.FMLog;
import com.dongman.fm.utils.UILImageLoader;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by liuzhiwei on 16/4/4.
 */
public class ImageShowActivity extends BaseActivity {

    private static final String TAG = ImageShowActivity.class.getSimpleName();
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int OPEN_ALBUM = 1;
    private static final int OPEN_CAMERA = 2;


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PhotosAdapter mAdapter;
    private List<PhotoInfo> mData;

    private EditText mInput;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mImageSize;

    private View mCancel, mPublish;
    private View mAddMore;

    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 1);
        setContentView(R.layout.publish_image);
        mData = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View actionbarLayout = LayoutInflater.from(this).inflate(R.layout.custom_actionbar_title, new LinearLayout(this), false);
        actionBar.setCustomView(actionbarLayout);
        mCancel = actionbarLayout.findViewById(R.id.cancel_action);
        mPublish = actionbarLayout.findViewById(R.id.publish_action);


        initView();
        openImages();
        DisplayMetrics  dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mImageSize = (int) (mScreenWidth / 3.5);
        FMLog.d(TAG, "imagesize : " + mImageSize);
    }

    private void initView() {
        mInput = (EditText)findViewById(R.id.input_content);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        mAdapter = new PhotosAdapter(this, mData);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20, 0, 10, 10));

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageShowActivity.this.finish();
            }
        });
        mPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 发送按钮
                Intent result = new Intent();
                Bundle bundle = new Bundle();
                if (mData != null && mData.size() > 0) {
                    ArrayList<String> photos = new ArrayList<String>();
                    for(PhotoInfo photoInfo : mData) {
                        photos.add(photoInfo.getPhotoPath());
                    }
                    bundle.putStringArrayList("photos", photos);
                }
                String inputContext = mInput.getText().toString();
                bundle.putString("content", inputContext);
                result.putExtras(bundle);

                ImageShowActivity.this.setResult(OPEN_ALBUM,result);
                ImageShowActivity.this.finish();
            }
        });
        mAddMore = findViewById(R.id.add_more);
        mAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImages();
            }
        });
    }

    private void openImages() {
        switch (mType) {
            case OPEN_ALBUM:
                //带配置
                FunctionConfig config = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(8)
                        .setSelected(mData)
                        .build();
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if (reqeustCode == REQUEST_CODE_GALLERY) {
                            mData.clear();
                            mData.addAll(resultList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        if (requestCode == REQUEST_CODE_GALLERY) {
                            Log.d(TAG, errorMsg);
                        }
                    }
                });
                break;
            case OPEN_CAMERA:
                //带配置
                FunctionConfig config1 = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(8)
                        .setEnablePreview(true)
                        .build();
                GalleryFinal.openCamera(REQUEST_CODE_GALLERY, config1, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if (reqeustCode == REQUEST_CODE_GALLERY) {
                            mData.addAll(resultList);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        if (requestCode == REQUEST_CODE_GALLERY) {
                            Log.d(TAG, errorMsg);
                        }
                    }
                });
                break;
        }
    }

    class PhotosAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

        LayoutInflater layoutInflater;
        private Activity activity;
        private List<PhotoInfo> photoInfos;
        private Drawable defaultDrawable;

        public PhotosAdapter(Activity activity, List<PhotoInfo> data) {
            this.activity = activity;
            layoutInflater = LayoutInflater.from(activity);
            photoInfos = data;
        }



        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.publis_photo_list_item, null, false);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoViewHolder holder, int position) {
            GFImageView imageView = (GFImageView) holder.itemView.findViewById(R.id.iv_photo);
            defaultDrawable = activity.getResources().getDrawable(R.drawable.ic_gf_default_photo);
            PhotoInfo photoInfo = photoInfos.get(position);
            UILImageLoader.getInstance().displayImage(activity, photoInfo.getPhotoPath(),
                    imageView, defaultDrawable, mImageSize, mImageSize);
        }

        @Override
        public int getItemCount() {
            return photoInfos.size();
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        public PhotoViewHolder(View itemView) {
            super(itemView);
        }
    }

}

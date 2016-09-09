package com.dongman.fm.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongman.fm.utils.ImageUtils;
import com.dongman.fm.network.IRequestCallBack;
import com.dongman.fm.network.OkHttpUtil;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

public class BaseFragment extends Fragment {

	public static final int REFRESH_UI = 1;
	public static final int DATA_READY = 3;
	public static final int DARA_FAILED = 4;

	protected LayoutInflater inflater;
	private View contentView;
	protected Context mContext;
	private ViewGroup container;
	protected boolean isRendered = false;

	protected BackHandledInterface mBackHandledInterface;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity().getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		onCreateView(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected void onCreateView(Bundle savedInstanceState) {

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
	}

	public boolean onBackPressed() {
		return false;
	}

	public Context getApplicationContext() {
		return mContext;
	}

	public void setContentView(int layoutResID) {
		setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}

	public View findViewById(int id) {
		if (contentView != null)
			return contentView.findViewById(id);
		return null;
	}

	// http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public void getImage(String url, ImageView imageView) {
		ImageUtils.getImage(getActivity(), url, imageView);
	}

	public void asyncGet(String url, IRequestCallBack requestCallBack) {
		asyncGet(url, null, requestCallBack);
	}

	public void asyncGet(String url, Map<String,String> parmas, IRequestCallBack requestCallBack) {
		if(parmas != null) {
			String resultUrl = OkHttpUtil.attachHttpGetParams(url, parmas);
			Request request =  new Request.Builder()
					.url(resultUrl)
					.build();
			asyncGet(request, requestCallBack);
		} else {
			Request request = new Request.Builder()
					.url(url)
					.build();
			asyncGet(request,requestCallBack);
		}
	}

	public void asyncGet(String url, String key, String value, IRequestCallBack requestCallBack) {
		String resultUrl = OkHttpUtil.attachHttpGetParam(url,key,value);
		Request request = new Request.Builder()
				.url(resultUrl)
				.build();
		asyncGet(request, requestCallBack);
	}

	public void asyncGet(Request request, final IRequestCallBack requestCallBack) {
		if(request == null) {
			Log.e("BaseActivity.async", "the request is null!");
			return;
		}
		Callback callback = new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {
				if(requestCallBack != null) {
					requestCallBack.onFailure(request,e);
				}
			}

			@Override
			public void onResponse(Response response) throws IOException {
				if(requestCallBack != null) {
					requestCallBack.onResponse(response);
				}
			}
		};

		OkHttpUtil.asyncGet(request, callback);
	}

}

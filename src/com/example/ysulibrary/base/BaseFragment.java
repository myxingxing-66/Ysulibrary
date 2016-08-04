package com.example.ysulibrary.base;

import com.example.ysulibrary.util.SingleManager;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class BaseFragment extends Fragment {
	
	public Context ct;
	public View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ct = getActivity();
	}
	
	/**
	 * 用户是否登录
	 */
	protected boolean isLogined(){
		return SingleManager.getInstance().getCurrentUser().isLogined();
	}
	
	/**
	 * 这个方法主要是初始化view
	 * 
	 * @param inflater
	 */
	public abstract void initView();

	/**
	 * 这个方法主要是初始化初始化数据，进行从服务器拉去数据
	 */
	public abstract void initData();

}

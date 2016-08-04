package com.example.ysulibrary;

import android.app.Application;

import com.example.ysulibrary.net.OkHttpUtil;

public class YsuApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		OkHttpUtil okHttpUtil = new OkHttpUtil(getApplicationContext());
	}
}

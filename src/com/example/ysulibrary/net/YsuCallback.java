package com.example.ysulibrary.net;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YsuCallback implements Callback {
	
	private Context mContext;
	
	public YsuCallback(Context mContext) {
		this.mContext = mContext;
	}

	@Override
	public void onFailure(Call arg0, IOException arg1) {
		try {
			onFailure(arg1.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResponse(Call arg0, Response arg1) throws IOException {
		if (arg1.code() == 200) {
			onSuccess(arg1.body().string());
		}else {
			onFailure(arg1.body().string());
		}
		
	}
	
	public void onSuccess(String result) throws IOException {
		System.out.println(result.toString());

	}

	public void onFailure(String error) throws IOException {
		System.out.println(error);
	}
	
}

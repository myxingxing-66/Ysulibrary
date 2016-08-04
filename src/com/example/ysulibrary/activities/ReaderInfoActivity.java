package com.example.ysulibrary.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.widget.ListView;

import com.example.ysulibrary.R;
import com.example.ysulibrary.adapter.ReaderInfoAdapter;
import com.example.ysulibrary.base.BaseActivity;
import com.example.ysulibrary.config.Config;
import com.example.ysulibrary.event.YsuEvent;
import com.example.ysulibrary.net.IPUtil;
import com.example.ysulibrary.net.OkHttpUtil;
import com.example.ysulibrary.net.YsuCallback;
import com.example.ysulibrary.util.ParseLibrary;

public class ReaderInfoActivity extends BaseActivity {
	
	private List<String> info;
	private ListView lv_info;
	private ReaderInfoAdapter readerInfoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reder_info);
		EventBus.getDefault().register(this);
		initData();
		initView();
	}

	@Override
	public void initView() {
		lv_info = (ListView)findViewById(R.id.lv_info);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	// 在ui线程执行
	public void onUserEvent(YsuEvent event) {
		switch (event.getInfo()) {
		case Config.READERINFO_PARSE_SUCCESS:
			readerInfoAdapter = new ReaderInfoAdapter(mContext, info);
			lv_info.setAdapter(readerInfoAdapter);
			break;
		case Config.READERINFO_PARSE_ERROR:
			ShowToast("数据异常,请重试...");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initData() {
		OkHttpUtil.enqueue(IPUtil.redr_info_rule, null, new YsuCallback(mContext){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				info = new ArrayList<String>();
				info = ParseLibrary.getReaderInfo(result);
				if (info.size() != 0) {
					EventBus.getDefault().post(new YsuEvent(Config.READERINFO_PARSE_SUCCESS));
				}else {
					EventBus.getDefault().post(new YsuEvent(Config.READERINFO_PARSE_ERROR));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}

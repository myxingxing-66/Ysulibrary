package com.example.ysulibrary.activities;

import java.io.IOException;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.widget.ListView;

import com.example.ysulibrary.R;
import com.example.ysulibrary.adapter.NowLendAdapter;
import com.example.ysulibrary.base.BaseActivity;
import com.example.ysulibrary.bean.NowLend;
import com.example.ysulibrary.config.Config;
import com.example.ysulibrary.event.NowLendEvent;
import com.example.ysulibrary.net.IPUtil;
import com.example.ysulibrary.net.OkHttpUtil;
import com.example.ysulibrary.net.YsuCallback;
import com.example.ysulibrary.util.ParseLibrary;

public class NowLendActivity extends BaseActivity {
	
	private ListView lv_now_lend;
	private List<NowLend> now_lends;
	private NowLendAdapter nowLendAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_now_lend);
		EventBus.getDefault().register(this);
		initData();
		initView();
	}

	@Override
	public void initView() {
		lv_now_lend = (ListView)findViewById(R.id.lv_now_lend);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	// 在ui线程执行
	public void onUserEvent(NowLendEvent event) {
		switch (event.getInfo()) {
		case Config.NOW_LEND_EMPTY:
			ShowToast("您当前暂未借阅任何书籍");
			break;
		case Config.NOW_LEND_SUCCESS:
			System.out.println("接收到消息");
			nowLendAdapter = new NowLendAdapter(mContext, now_lends);
			lv_now_lend.setAdapter(nowLendAdapter);
			break;
		case Config.NOW_LEND_FAILED:
			ShowToast("数据解析错误");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initData() {
		OkHttpUtil.enqueue(IPUtil.now_lend, null, new YsuCallback(mContext){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				now_lends = ParseLibrary.getNowLend(result);
				if (now_lends.size() == 0) {
					EventBus.getDefault().post(new NowLendEvent(Config.NOW_LEND_EMPTY));
				}else if (now_lends.size() != 0) {
					EventBus.getDefault().post(new NowLendEvent(Config.NOW_LEND_SUCCESS));
				}else {
					EventBus.getDefault().post(new NowLendEvent(Config.NOW_LEND_FAILED));
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

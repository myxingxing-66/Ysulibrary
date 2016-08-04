package com.example.ysulibrary.activities;

import java.io.IOException;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.widget.ListView;

import com.example.ysulibrary.R;
import com.example.ysulibrary.adapter.PregBookAdapter;
import com.example.ysulibrary.base.BaseActivity;
import com.example.ysulibrary.bean.BookPreg;
import com.example.ysulibrary.config.Config;
import com.example.ysulibrary.event.PreBookEvent;
import com.example.ysulibrary.net.IPUtil;
import com.example.ysulibrary.net.OkHttpUtil;
import com.example.ysulibrary.net.YsuCallback;
import com.example.ysulibrary.util.ParseLibrary;

public class PreBookActivity extends BaseActivity {
	
	private List<BookPreg> preglist;
	private ListView lv_pre_book;
	private PregBookAdapter preBookAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_pre_book);
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	//在ui线程执行
	public void onUserEvent(PreBookEvent preBookEvent){
		switch (preBookEvent.getInfo()) {
		case Config.PRE_BOOK_SUCCESS:
			preBookAdapter = new PregBookAdapter(preglist, mContext);
			lv_pre_book.setAdapter(preBookAdapter);
			break;
		case Config.PRE_BOOK_FAILED:
			ShowToast("数据解析失败");
			break;
		case Config.PRE_BOOK_EMPTY:
			ShowToast("暂无信息");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initView() {
		lv_pre_book = (ListView)findViewById(R.id.lv_pre_book);
	}

	@Override
	public void initData() {
		OkHttpUtil.enqueue(IPUtil.pre_book, null, new YsuCallback(mContext){

			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				preglist = ParseLibrary.getPreLists(result);
				if (preglist.size() == 0) {
					EventBus.getDefault().post(new PreBookEvent(Config.PRE_BOOK_EMPTY));
				}else if (preglist.size() != 0) {
					EventBus.getDefault().post(new PreBookEvent(Config.PRE_BOOK_SUCCESS));
				}else {
					EventBus.getDefault().post(new PreBookEvent(Config.PRE_BOOK_FAILED));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

}

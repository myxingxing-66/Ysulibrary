package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.webkit.WebView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.NewsLib;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.YsuEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;

public class NewsDetailActivity extends BaseActivity {
	
	private WebView wv_news_detail;
	private String newsString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_news_detail);
		initView();
		initData();
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUserEvent(YsuEvent event){
		switch (event.getInfo()) {
		case Config.SUCCESS:
			wv_news_detail.loadDataWithBaseURL(null, newsString, "text/html", "utf-8", null);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initView() {
		wv_news_detail = (WebView)findViewById(R.id.wv_news_detail);
		wv_news_detail.getSettings().setJavaScriptEnabled(true);
		wv_news_detail.getSettings().setDefaultTextEncodingName("utf-8");
	}

	@Override
	public void initData() {
		NewsLib newsLib = (NewsLib) getIntent().getSerializableExtra("news"); 
		OkHttpUtil.enqueue(IPUtil.LIBRARY+newsLib.getHref(), null, new YsuCallback(mContext){
			@Override
			public void onSuccess(String result) throws IOException {
				newsString = ParseLibrary.getNesDetail(result);
				EventBus.getDefault().post(new YsuEvent(Config.SUCCESS));
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

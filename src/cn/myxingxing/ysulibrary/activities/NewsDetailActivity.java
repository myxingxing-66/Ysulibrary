package cn.myxingxing.ysulibrary.activities;

import android.os.Bundle;
import android.webkit.WebView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.NewsLib;
import cn.myxingxing.ysulibrary.net.IPUtil;

public class NewsDetailActivity extends BaseActivity {
	
	private WebView wv_news_detail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		wv_news_detail = (WebView)findViewById(R.id.wv_news_detail);
		wv_news_detail.getSettings().setJavaScriptEnabled(true);
		NewsLib newsLib = (NewsLib) getIntent().getSerializableExtra("news"); 
		wv_news_detail.loadUrl(IPUtil.LIBRARY+newsLib.getHref());
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

}

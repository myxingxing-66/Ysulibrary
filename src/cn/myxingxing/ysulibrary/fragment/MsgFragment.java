package cn.myxingxing.ysulibrary.fragment;

import java.io.IOException;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.activities.NewsDetailActivity;
import cn.myxingxing.ysulibrary.adapter.NewsLibrsryAdapter;
import cn.myxingxing.ysulibrary.base.BaseFragment;
import cn.myxingxing.ysulibrary.bean.LibraryNewsEvent;
import cn.myxingxing.ysulibrary.bean.NewsLib;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;

public class MsgFragment extends BaseFragment implements OnItemClickListener{
	
	private ListView lv_news;
	private List<NewsLib> listNews;
	private NewsLibrsryAdapter newsLibraryAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_msg, container, false);
		initView();
		initData();
		return view;
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUserEvent(LibraryNewsEvent event){
		switch (event.getInfo()) {
		case Config.SUCCESS:
			newsLibraryAdapter = new NewsLibrsryAdapter(listNews, ct);
			lv_news.setAdapter(newsLibraryAdapter);
			break;
		case Config.FAILED:
			ShowToast("数据解析失败");
			break;
		case Config.EMPTY:
			ShowToast("暂无通知");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initData() {
		OkHttpUtil.enqueue(IPUtil.LIBRARY, null, new YsuCallback(ct){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				listNews = ParseLibrary.getNews(result);
				if (listNews == null) {
					EventBus.getDefault().post(new LibraryNewsEvent(Config.FAILED));
				}else if (listNews.size() == 0) {
					EventBus.getDefault().post(new LibraryNewsEvent(Config.EMPTY));
				}else {
					EventBus.getDefault().post(new LibraryNewsEvent(Config.SUCCESS));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

	@Override
	public void initView() {
		lv_news = (ListView)view.findViewById(R.id.lv_news);
		lv_news.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		startActivity(new Intent(ct, NewsDetailActivity.class).putExtra("news", listNews.get(position)));
	}

}

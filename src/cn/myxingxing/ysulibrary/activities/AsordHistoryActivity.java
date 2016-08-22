package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.adapter.AsordAdapter;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.BookAsord;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.AsordHistoryEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;
import cn.myxingxing.ysulibrary.view.xlist.XListView;
import cn.myxingxing.ysulibrary.view.xlist.XListView.IXListViewListener;

public class AsordHistoryActivity extends BaseActivity {
	
	private int page = 1;
	private List<BookAsord> asords;
	private XListView lv_asord_history;
	private AsordAdapter asordAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.activity_asord_history);
		initData();
		initView();
	}
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	//在ui线程执行
	public void onUserEvent(AsordHistoryEvent asordHistoryEvent){
		switch (asordHistoryEvent.getInfo()) {
		case Config.ASORD_HISTORY_SUCCESS:
			asordAdapter = new AsordAdapter(asords, mContext);
			lv_asord_history.setAdapter(asordAdapter);
			break;
		case Config.ASORD_HISTORY_FAILED:
			ShowToast("数据解析失败");
			break;
		case Config.ASPRD_HISTORY_EMPTY:
			ShowToast("您还未荐购过任何书籍");
			break;
		case Config.ASORD_HISTORY_NOMORE:
			ShowToast("已无更多数据");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initView() {
		lv_asord_history = (XListView)findViewById(R.id.lv_asord_history);
		lv_asord_history.setPullLoadEnable(true);
		lv_asord_history.setPullRefreshEnable(true);
		lv_asord_history.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				page = 1;
				initData();
				lv_asord_history.stopRefresh();
			}
			
			@Override
			public void onLoadMore() {
				page++;
				loadMore();
			}
		});
	}

	private void loadMore() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", page+"");
		OkHttpUtil.enqueue(IPUtil.asord_history, map, new YsuCallback(mContext){

			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				List<BookAsord> list = ParseLibrary.getAsordHist(result);
				if (list.size() == 0) {
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASORD_HISTORY_NOMORE));
				}else if (list.size() != 0) {
					asords.addAll(list);
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASORD_HISTORY_SUCCESS));
				}else {
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASORD_HISTORY_FAILED));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

	@Override
	public void initData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", page+"");
		OkHttpUtil.enqueue(IPUtil.asord_history, map, new YsuCallback(mContext){

			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				asords = ParseLibrary.getAsordHist(result);
				if (asords.size() == 0) {
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASPRD_HISTORY_EMPTY));
				}else if (asords.size() != 0) {
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASORD_HISTORY_SUCCESS));
				}else {
					EventBus.getDefault().post(new AsordHistoryEvent(Config.ASORD_HISTORY_FAILED));
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

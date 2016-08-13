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
import cn.myxingxing.ysulibrary.adapter.LendHistoryAdapter;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.BookHist;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.LendHistoryEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;
import cn.myxingxing.ysulibrary.view.xlist.XListView;
import cn.myxingxing.ysulibrary.view.xlist.XListView.IXListViewListener;

public class LendHistoryActivity extends BaseActivity {
	
	private XListView lv_lend_history;
	private List<BookHist> lendlist;
	private int page = 1;
	private LendHistoryAdapter lendHistoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lend_history);
		EventBus.getDefault().register(this);
		initView();
		initData();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	//在ui线程执行
	public void onUserEvent(LendHistoryEvent lendHistoryEvent){
		switch (lendHistoryEvent.getInfo()) {
		case Config.LEND_HISTORY_SUCCESS:
			lendHistoryAdapter = new LendHistoryAdapter(lendlist, mContext);
			lv_lend_history.setAdapter(lendHistoryAdapter);
			break;
		case Config.LEND_HISTORY_EMPTY:
			ShowToast("您还未借过任何书籍");
			break;
		case Config.LEND_HISTORY_FAILED:
			ShowToast("数据解析错误");
			break;
		case Config.LEND_HISTORY_NOMORE:
			ShowToast("已无更多数据");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initView() {
		lv_lend_history = (XListView)findViewById(R.id.lv_lend_history);
		lv_lend_history.setPullLoadEnable(true);
		lv_lend_history.setPullRefreshEnable(true);
		lv_lend_history.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				page = 1;
				initData();
				lv_lend_history.stopRefresh();
			}
			
			@Override
			public void onLoadMore() {
				page++;
				loadMore();
			}
		});
	}

	private void loadMore(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", page+"");
		OkHttpUtil.enqueue(IPUtil.lend_history, map, new YsuCallback(mContext){

			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				List<BookHist> list = ParseLibrary.getLendHist(result);
				if (list.size() == 0) {
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_NOMORE));
				}else if (list.size() != 0) {
					lendlist.addAll(list);
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_SUCCESS));
				}else {
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_FAILED));
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
		OkHttpUtil.enqueue(IPUtil.lend_history, map, new YsuCallback(mContext){

			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				lendlist = ParseLibrary.getLendHist(result);
				if (lendlist.size() == 0) {
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_EMPTY));
				}else if (lendlist.size() != 0) {
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_SUCCESS));
				}else {
					EventBus.getDefault().post(new LendHistoryEvent(Config.LEND_HISTORY_FAILED));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

}

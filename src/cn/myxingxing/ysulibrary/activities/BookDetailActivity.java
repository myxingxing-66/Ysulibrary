package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.widget.ListView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.adapter.LocationAdapter;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.Lotinfo;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.BookDetailEvent;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;

public class BookDetailActivity extends BaseActivity {
	
	private ListView listView;
	private List<Lotinfo> initLoclist = new ArrayList<Lotinfo>();
	private String detailUrl;
	private LocationAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		EventBus.getDefault().register(this);
		initView();
		initData();
	}

	@Override
	public void initView() {
		listView = (ListView)findViewById(R.id.lv_location);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUserEvent(BookDetailEvent event) {
		switch (event.getInfo()) {
		case Config.DETAIL_EMPTY:
			ShowToast("暂无数据");
			break;
		case Config.DETAIL_FAILED:
			ShowToast("数据解析异常");
			break;
		case Config.DETAIL_SUCCESS:
			adapter = new LocationAdapter(mContext, R.layout.item_location_info, initLoclist);
			listView.setAdapter(adapter);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void initData() {
		detailUrl = getIntent().getStringExtra("detailUrl");
		OkHttpUtil.enqueue(detailUrl, null, new YsuCallback(mContext){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				initLoclist = ParseLibrary.getLoctionBooks(result);
				if (initLoclist == null) {
					EventBus.getDefault().post(new BookDetailEvent(Config.DETAIL_FAILED));
				}else if (initLoclist.size() == 0) {
					EventBus.getDefault().post(new BookDetailEvent(Config.DETAIL_EMPTY));
				}else {
					EventBus.getDefault().post(new BookDetailEvent(Config.DETAIL_SUCCESS));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}

}

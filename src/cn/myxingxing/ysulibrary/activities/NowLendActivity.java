package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.widget.ListView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.adapter.NowLendAdapter;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.bean.NowLend;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.NowLendEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;

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
		case Config.EMPTY:
			ShowToast("您当前暂未借阅任何书籍");
			break;
		case Config.SUCCESS:
			System.out.println("接收到消息");
			nowLendAdapter = new NowLendAdapter(mContext, now_lends);
			lv_now_lend.setAdapter(nowLendAdapter);
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
				if (now_lends == null) {
					EventBus.getDefault().post(new NowLendEvent(Config.EMPTY));
				}else if (now_lends.size() != 0) {
					EventBus.getDefault().post(new NowLendEvent(Config.SUCCESS));
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

package cn.myxingxing.ysulibrary;

import android.app.Application;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;

public class YsuApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		OkHttpUtil okHttpUtil = new OkHttpUtil(getApplicationContext());
	}
}

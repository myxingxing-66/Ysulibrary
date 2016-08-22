package cn.myxingxing.ysulibrary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;

public class SplashActivity extends BaseActivity {
	
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(mContext, MainActivity.class));
				finish();
			}
		}, 1500);
	}

	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			startActivity(new Intent(mContext, MainActivity.class));
			finish();
		}
		return super.onTouchEvent(event);
	}



	@Override
	public void initView() {
		
	}

	@Override
	public void initData() {
		
	}

}

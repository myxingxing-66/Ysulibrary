package cn.myxingxing.ysulibrary.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;

public class SuggestionActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

}

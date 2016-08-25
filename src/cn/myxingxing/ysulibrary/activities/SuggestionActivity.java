package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.JSONCallback;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.util.SingleManager;

public class SuggestionActivity extends BaseActivity {
	
	private EditText et_suggestion;
	private Button btn_ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);
		initView();
		initData();
	}

	@Override
	public void initView() {
		et_suggestion = (EditText)findViewById(R.id.et_suggestion);
		btn_ok = (Button)findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("".equals(et_suggestion.getText().toString().trim())) {
					ShowToast("内容不能为空");
					return;
				}else {
					Map<String, String> maps = new HashMap<String, String>();
					if (isLogined()) {
						maps.put("name", SingleManager.getInstance().getCurrentUser().getName());
						maps.put("number", SingleManager.getInstance().getCurrentUser().getNumber());
					}else {
						maps.put("name", "匿名");
						maps.put("number", "000");
					}
					maps.put("suggestion", et_suggestion.getText().toString());
					OkHttpUtil.enqueue(IPUtil.suggestion, maps, new JSONCallback(SuggestionActivity.this){
						@Override
						public void onSuccess(JSONObject json)throws IOException {
							super.onSuccess(json);
							try {
								if (json.getBoolean("result")) {
									System.out.println("提交成功");
								}else {
									System.out.println("提交失败");
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onFailure(String error) throws IOException {
							super.onFailure(error);
						}
					});
				}
			}
		});
	}

	@Override
	public void initData() {

	}

}

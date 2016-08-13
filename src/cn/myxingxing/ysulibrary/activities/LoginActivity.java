package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.event.LoginSucceedEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.SingleManager;

public class LoginActivity extends BaseActivity {
	
	private EditText et_number,et_passwd,et_check;
	private Button btn_login;
	private ImageView imv_checkcode;
	private CheckBox cb_rember;
	private Bitmap bmp;
	
	public static final int SHOW_CHECKCODE = 0;
	public static final int LOGIN_ERROR = 1;
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_CHECKCODE:
				imv_checkcode.setImageBitmap(bmp);
				break;
			case LOGIN_ERROR:
				ShowToast("’À∫≈ªÚ√‹¬Î¥ÌŒÛ...");
				et_number.setText("");
				et_passwd.setText("");
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		EventBus.getDefault().register(this);
		initView();
		initData();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	// ‘⁄uiœﬂ≥Ã÷¥––
	public void onUserEvent(LoginSucceedEvent event) {
		if (!event.isLoged()) {
			ShowToast("’À∫≈ªÚ√‹¬Î¥ÌŒÛ");
		}
	}

	@Override
	public void initView() {
		et_number = (EditText)findViewById(R.id.et_number);
		et_passwd = (EditText)findViewById(R.id.et_passwd);
		et_check = (EditText)findViewById(R.id.et_check);
		btn_login = (Button)findViewById(R.id.btn_login);
		imv_checkcode = (ImageView)findViewById(R.id.imv_checkcode);
		cb_rember = (CheckBox)findViewById(R.id.cb_rember);
		et_number.setText("130120010082");
		et_passwd.setText("888"); 
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_number.getText().toString().trim())) {
					ShowToast("«Î ‰»Î—ß∫≈");
				}else if (TextUtils.isEmpty(et_passwd.getText().toString().trim())) {
					ShowToast("«Î ‰»Î√‹¬Î");
				}else {
					Map<String, String> maps = new HashMap<String, String>();
					maps.put("number", et_number.getText().toString());
					maps.put("passwd", et_passwd.getText().toString());
					maps.put("captcha", et_check.getText().toString());
					maps.put("select", "cert_no");
					maps.put("returnUrl", "");
					OkHttpUtil.enqueue(IPUtil.login, maps, new YsuCallback(mContext){
						@Override 
						public void onSuccess(String result) throws IOException {
							super.onSuccess(result);
							try {
								final String res = Jsoup.parse(result).getElementById("header_opac").getElementsByTag("strong").text();
								if (TextUtils.isEmpty(res)) { 
									EventBus.getDefault().post(new LoginSucceedEvent(false));
								}else {
									String name = Jsoup.parse(result).getElementById("header_opac").getElementsByTag("font").text();
									System.out.println("name is " + name);
									SingleManager.getInstance().getCurrentUser().setName(name);
									SingleManager.getInstance().getCurrentUser().setNumber(et_number.getText().toString().trim());
									SingleManager.getInstance().getCurrentUser().setPassword(et_passwd.getText().toString().trim());
									SingleManager.getInstance().getCurrentUser().setLogined(true);
									EventBus.getDefault().post(new LoginSucceedEvent(true));
									finish();
								}
							} catch (Exception e) {
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
		new Thread(new Runnable() {
			@Override 
			public void run() {
				bmp = OkHttpUtil.getCode(IPUtil.getCheckCode);
				Message message = new Message();
				message.what = SHOW_CHECKCODE;
				handler.sendMessage(message);
			}
		}).start();
	}

}

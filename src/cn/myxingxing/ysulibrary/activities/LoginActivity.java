package cn.myxingxing.ysulibrary.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
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
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	
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
				ShowToast("账号或密码错误...");
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
	// 在ui线程执行
	public void onUserEvent(LoginSucceedEvent event) {
		if (!event.isLoged()) {
			ShowToast("账号或密码错误");
			et_number.setText("");
			et_passwd.setText("");
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
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_number.getText().toString().trim())) {
					ShowToast("请输入学号");
				}else if (TextUtils.isEmpty(et_check.getText().toString().trim())) {
					ShowToast("验证码不能为空");
				}else if (TextUtils.isEmpty(et_passwd.getText().toString().trim())) {
					ShowToast("请输入密码");
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
							try {
								final String res = Jsoup.parse(result).getElementById("header_opac").getElementsByTag("strong").text();
								if (TextUtils.isEmpty(res)) { 
									EventBus.getDefault().post(new LoginSucceedEvent(false));
								}else {
									String accountId=et_number.getText().toString();
									String passwordId=et_passwd.getText().toString();
									editor=pref.edit();
									if(cb_rember.isChecked()){
										editor.putBoolean("remember_password", true);
										editor.putString("accountId", accountId);
										editor.putString("passwordId", passwordId);
									}else{
										editor.clear();
									}
									editor.commit();
									String name = Jsoup.parse(result).getElementById("header_opac").getElementsByTag("font").text();
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
		
		pref=PreferenceManager.getDefaultSharedPreferences(this);
		boolean isRemember=pref.getBoolean("remember_password", false);
		if(isRemember){
			String accountId=pref.getString("accountId", " ");
			String passwordId=pref.getString("passwordId", " ");
			et_number.setText(accountId);
			et_passwd.setText(passwordId);
			cb_rember.setChecked(true);
		}
		
	}

}

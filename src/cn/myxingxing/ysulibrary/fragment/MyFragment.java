package cn.myxingxing.ysulibrary.fragment;

import java.io.IOException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.activities.AsordHistoryActivity;
import cn.myxingxing.ysulibrary.activities.LendHistoryActivity;
import cn.myxingxing.ysulibrary.activities.LoginActivity;
import cn.myxingxing.ysulibrary.activities.NowLendActivity;
import cn.myxingxing.ysulibrary.activities.PreBookActivity;
import cn.myxingxing.ysulibrary.activities.ReaderInfoActivity;
import cn.myxingxing.ysulibrary.activities.SuggestionActivity;
import cn.myxingxing.ysulibrary.base.BaseFragment;
import cn.myxingxing.ysulibrary.event.LoginSucceedEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.SingleManager;

public class MyFragment extends BaseFragment implements OnClickListener{
	
	private LinearLayout ly_my_login,ly_my_information;
	private LinearLayout ly_now_lend,ly_lend_history;
	private LinearLayout ly_recom_history,ly_appointment,ly_logout,ly_suggestion;
	private LinearLayout ly_have_login;
	private TextView tv_to_login,tv_name,tv_number;
	private ImageView imv_to_login;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_mycenter, null);
		initData();
		initView();
		return view;
	} 
	
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUserEvent(LoginSucceedEvent loginSucceedEvent){
		if (loginSucceedEvent.isLoged()) {
			ly_have_login.setVisibility(View.VISIBLE);
			tv_to_login.setVisibility(View.GONE);
			imv_to_login.setVisibility(View.GONE);
			ly_my_login.setClickable(false);
			tv_name.setText(SingleManager.getInstance().getCurrentUser().getName());
			tv_number.setText("Ñ§ºÅ:" + SingleManager.getInstance().getCurrentUser().getNumber());
		}else {
			ly_have_login.setVisibility(View.GONE);
			tv_to_login.setVisibility(View.VISIBLE);
			imv_to_login.setVisibility(View.VISIBLE);
			ly_my_login.setClickable(true);
		}
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ly_suggestion) {
			startActivity(new Intent(ct, SuggestionActivity.class));
			return;
		}
		if (SingleManager.getInstance().getCurrentUser().isLogined()) {
			switch (v.getId()) {
			case R.id.ly_my_login:
				ShowToast("ÒÑµÇÂ½");
				break;
			case R.id.ly_logout:
				OkHttpUtil.enqueue(IPUtil.logout, null, new YsuCallback(ct){
					@Override
					public void onSuccess(String result) throws IOException {
						SingleManager.getInstance().getCurrentUser().setLogined(false);
						EventBus.getDefault().post(new LoginSucceedEvent(false));
					}
					@Override
					public void onFailure(String error) throws IOException {
						super.onFailure(error);
					}
				});
				break;
			case R.id.ly_my_information:
				startActivity(new Intent(ct, ReaderInfoActivity.class));
				break;
			case R.id.ly_now_lend:
				startActivity(new Intent(ct, NowLendActivity.class));
				break;
			case R.id.ly_lend_history:
				startActivity(new Intent(ct, LendHistoryActivity.class));
				break;
			case R.id.ly_recom_history:
				startActivity(new Intent(ct, AsordHistoryActivity.class));
				break;
			case R.id.ly_appointment:
				startActivity(new Intent(ct, PreBookActivity.class));
			default:
				break;
			}
		}else {
			if (v.getId() != R.id.ly_logout) {
				startActivity(new Intent(ct, LoginActivity.class));
			}
		}
	}

	@Override
	public void initView() {
		ly_my_login = (LinearLayout)view.findViewById(R.id.ly_my_login);
		ly_my_information = (LinearLayout)view.findViewById(R.id.ly_my_information);
		ly_now_lend = (LinearLayout)view.findViewById(R.id.ly_now_lend);
		ly_lend_history = (LinearLayout)view.findViewById(R.id.ly_lend_history);
		ly_recom_history = (LinearLayout)view.findViewById(R.id.ly_recom_history);
		ly_appointment = (LinearLayout)view.findViewById(R.id.ly_appointment);
		ly_logout = (LinearLayout)view.findViewById(R.id.ly_logout);
		ly_have_login = (LinearLayout)view.findViewById(R.id.ly_have_login);
		tv_to_login = (TextView)view.findViewById(R.id.tv_to_login);
		tv_name = (TextView)view.findViewById(R.id.tv_name);
		tv_number = (TextView)view.findViewById(R.id.tv_number);
		imv_to_login = (ImageView)view.findViewById(R.id.imv_to_login);
		ly_suggestion = (LinearLayout)view.findViewById(R.id.ly_suggestion);
		
		ly_my_login.setOnClickListener(this);
		ly_my_information.setOnClickListener(this);
		ly_now_lend.setOnClickListener(this);
		ly_lend_history.setOnClickListener(this);
		ly_recom_history.setOnClickListener(this);
		ly_appointment.setOnClickListener(this);
		ly_logout.setOnClickListener(this);
		ly_have_login.setOnClickListener(this);
		ly_suggestion.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}

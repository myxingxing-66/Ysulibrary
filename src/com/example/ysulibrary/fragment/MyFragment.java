package com.example.ysulibrary.fragment;

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

import com.example.ysulibrary.R;
import com.example.ysulibrary.activities.AsordHistoryActivity;
import com.example.ysulibrary.activities.LendHistoryActivity;
import com.example.ysulibrary.activities.LoginActivity;
import com.example.ysulibrary.activities.NowLendActivity;
import com.example.ysulibrary.activities.PreBookActivity;
import com.example.ysulibrary.activities.ReaderInfoActivity;
import com.example.ysulibrary.base.BaseFragment;
import com.example.ysulibrary.event.LoginSucceedEvent;
import com.example.ysulibrary.net.IPUtil;
import com.example.ysulibrary.net.OkHttpUtil;
import com.example.ysulibrary.net.YsuCallback;
import com.example.ysulibrary.util.SingleManager;

public class MyFragment extends BaseFragment implements OnClickListener{
	
	private LinearLayout ly_my_login,ly_my_information;
	private LinearLayout ly_now_lend,ly_lend_history;
	private LinearLayout ly_recom_history,ly_appointment,ly_logout;
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
			System.out.println("接受到登陆的消息"); 
			ly_have_login.setVisibility(View.VISIBLE);
			tv_to_login.setVisibility(View.GONE);
			imv_to_login.setVisibility(View.GONE);
			ly_my_login.setClickable(false);
			tv_name.setText(SingleManager.getInstance().getCurrentUser().getName());
			tv_number.setText("学号:" + SingleManager.getInstance().getCurrentUser().getNumber());
			System.out.println("执行完毕");
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
		switch (v.getId()) {
		case R.id.ly_my_login:
			if (SingleManager.getInstance().getCurrentUser().isLogined()) {
				
			}else {
				startActivity(new Intent(ct, LoginActivity.class));
			}
			break;
		case R.id.ly_logout:
			OkHttpUtil.enqueue(IPUtil.logout, null, new YsuCallback(ct){
				@Override
				public void onSuccess(String result) throws IOException {
					super.onSuccess(result);
					System.out.println("退出登录");
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
			if (SingleManager.getInstance().getCurrentUser().isLogined()) {
				startActivity(new Intent(ct, ReaderInfoActivity.class));
			}
			break;
		case R.id.ly_now_lend:
			if (SingleManager.getInstance().getCurrentUser().isLogined()) {
				startActivity(new Intent(ct, NowLendActivity.class));
			}
			break;
		case R.id.ly_lend_history:
			if (SingleManager.getInstance().getCurrentUser().isLogined()) {
				startActivity(new Intent(ct, LendHistoryActivity.class));
			}
			break;
		case R.id.lv_asord_history:
			if (isLogined()) {
				startActivity(new Intent(ct, AsordHistoryActivity.class));
			}
			break;
		case R.id.lv_pre_book:
			if (isLogined()) {
				startActivity(new Intent(ct, PreBookActivity.class));
			}
			break;
		default:
			break;
		}
	}
	
//	private void changeLogin(){
//		if (SingleManager.getInstance().getCurrentUser().isLogined()) {
//			ly_have_login.setVisibility(View.VISIBLE);
//			tv_to_login.setVisibility(View.GONE);
//			tv_name.setText(SingleManager.getInstance().getCurrentUser().getName());
//			tv_number.setText("学号:"+ SingleManager.getInstance().getCurrentUser().getNumber());
//			imv_to_login.setVisibility(View.GONE);
//			ly_my_login.setClickable(false);
//		}else {
//			ly_have_login.setVisibility(View.GONE);
//			tv_to_login.setVisibility(View.VISIBLE);
//			ly_my_login.setClickable(true);
//			imv_to_login.setVisibility(View.VISIBLE);
//		}
//	}

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
		
		ly_my_login.setOnClickListener(this);
		ly_my_information.setOnClickListener(this);
		ly_now_lend.setOnClickListener(this);
		ly_lend_history.setOnClickListener(this);
		ly_recom_history.setOnClickListener(this);
		ly_appointment.setOnClickListener(this);
		ly_logout.setOnClickListener(this);
		ly_have_login.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}

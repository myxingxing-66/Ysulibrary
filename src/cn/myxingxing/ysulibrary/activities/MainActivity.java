package cn.myxingxing.ysulibrary.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.base.BaseActivity;
import cn.myxingxing.ysulibrary.base.BaseFragment;
import cn.myxingxing.ysulibrary.fragment.HomeFragment;
import cn.myxingxing.ysulibrary.fragment.MsgFragment;
import cn.myxingxing.ysulibrary.fragment.MyFragment;

public class MainActivity extends BaseActivity {
	
	private HomeFragment homeFragment;
	private MsgFragment msgFragment;
	private MyFragment myFragment;
	private BaseFragment[] fragments;
	private RadioGroup rgMain;
	private int index = 1;
	private int currnetIndex = 1;
	private int currentItem = R.id.rb_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rgMain = (RadioGroup)findViewById(R.id.main_group);
		rgMain.check(currentItem);
		initView();
		initData();
	}  


	@Override 
	public void initView() {
		
	}


	@Override
	public void initData() {
		homeFragment = new HomeFragment();
		msgFragment = new MsgFragment();
		myFragment = new MyFragment();
		fragments = new BaseFragment[]{msgFragment,homeFragment,myFragment};
		
		getFragmentManager().beginTransaction()
			.add(R.id.main_fragment, msgFragment)
			.add(R.id.main_fragment,homeFragment)
			.add(R.id.main_fragment,myFragment)
			.hide(msgFragment)
			.hide(myFragment)
			.show(homeFragment)
			.commit();
		
		rgMain.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_msg:
					index = 0;
					break;
				case R.id.rb_main:
					index = 1;
					break;
				case R.id.rb_my_center:
					index = 2;
					break;
				}
				if (currnetIndex != index) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.hide(fragments[currnetIndex])
						.show(fragments[index])
						.commit();
				}
				currentItem = checkedId;
				currnetIndex = index;
			}
		});
	}
	
}

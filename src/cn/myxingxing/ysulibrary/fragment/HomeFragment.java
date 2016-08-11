package cn.myxingxing.ysulibrary.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.myxingxing.ysulibrary.base.BaseFragment;
import cn.myxingxing.ysulibrary.view.xlist.XListView;

import com.example.ysulibrary.R;

public class HomeFragment extends BaseFragment implements OnItemClickListener,OnClickListener,OnItemSelectedListener{
	
	private int mScreenWidth;
	private LinearLayout ly_no_data;
	private TextView tv_title;
	private Spinner first_spinner;
	private EditText et_book_name;
	private Button btn_search_ok;
	private XListView lv_book;
	private TextView tv_no;
	private RelativeLayout ly_progress,layout_action;
	private PopupWindow morePop;
	private String strSearchType;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.frag_home, container, false);
		initData();
		initView();
		return view;
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void initView() {
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		
		view.findViewById(R.id.ly_search_top).setOnClickListener(this);
		ly_no_data = (LinearLayout)view.findViewById(R.id.ly_no_data);
		tv_title = (TextView)view.findViewById(R.id.tv_title);
		first_spinner = (Spinner)view.findViewById(R.id.first_spinner);
		et_book_name = (EditText)view.findViewById(R.id.et_book_name);
		btn_search_ok = (Button)view.findViewById(R.id.btn_search_ok);
		lv_book = (XListView)view.findViewById(R.id.lv_book);
		tv_no = (TextView)view.findViewById(R.id.tv_no);
		ly_progress = (RelativeLayout)view.findViewById(R.id.lv_pre_book);
		layout_action = (RelativeLayout)view.findViewById(R.id.layout_action);
		
		//初始化检索条目
		String[] firstsp = getResources().getStringArray(R.array.titlespinner);
		ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,firstsp);
		first_spinner.setAdapter(firstAdapter);	
		first_spinner.setOnItemSelectedListener(this);	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_search_top:
			showPop();
			break;
		case R.id.btn_search_ok:
			
			break;
		case R.id.btn_layout_book_search:
			changeTextView(v);
			break;
		case R.id.btn_hot_book:
			changeTextView(v);
			break;
		case R.id.btn_layout_new_book:
			changeTextView(v);
			break;
		default:
			break;
		}
	}
	
	private void changeTextView(View view){
		switch (view.getId()) {
		case R.id.btn_hot_book:
			tv_title.setText("热门借阅");
			morePop.dismiss();
			break;
		case R.id.btn_layout_new_book:
			tv_title.setText("新书通报");
			morePop.dismiss();
			break;
		case R.id.btn_layout_book_search:
			tv_title.setText("检索");
			morePop.dismiss();
			break;
		default:
			break;
		}
	}
	
	private void showPop() {
		View view = LayoutInflater.from(ct).inflate(R.layout.pop_book, null);
		view.findViewById(R.id.btn_layout_book_search).setOnClickListener(this);
		view.findViewById(R.id.btn_hot_book).setOnClickListener(this);
		view.findViewById(R.id.btn_layout_new_book).setOnClickListener(this);
		morePop = new PopupWindow(view, mScreenWidth, 600);
		morePop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					morePop.dismiss();
					return true;
				}
				return false;
			}
		});
		morePop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		morePop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		morePop.setTouchable(true);
		morePop.setFocusable(true);
		morePop.setOutsideTouchable(true);
		morePop.setBackgroundDrawable(new BitmapDrawable());
		morePop.setAnimationStyle(R.style.MenuPop);
		morePop.showAsDropDown(layout_action, 0, -dip2px(ct, 2.0F));
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String temp = parent.getItemAtPosition(position).toString();
		if("题名".equals(temp)){
			strSearchType = "title";
		}else if("责任者".equals(temp)){
			strSearchType = "author";
		}else if("主题词".equals(temp)){
			strSearchType = "keyword";
		}else if("ISBN/ISSN".equals(temp)){
			strSearchType = "isbn";
		}else if("订购号".equals(temp)){
			strSearchType = "asordno";
		}else if("分类号".equals(temp)){
			strSearchType = "coden";
		}else if("索书号".equals(temp)){
			strSearchType = "callno";
		}else if("出版社".equals(temp)){
			strSearchType = "publisher";
		}else if("丛书名".equals(temp)){
			strSearchType = "series";
		}else if("题名拼音".equals(temp)){
			strSearchType = "tpinyin";
		}else if("责任者拼音".equals(temp)){
			strSearchType = "apinyin";
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		strSearchType = "title";
	}

}

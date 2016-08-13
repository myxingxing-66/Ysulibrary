package cn.myxingxing.ysulibrary.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.activities.BookDetailActivity;
import cn.myxingxing.ysulibrary.adapter.NotelistAdapter;
import cn.myxingxing.ysulibrary.adapter.SearchBookAdapter;
import cn.myxingxing.ysulibrary.base.BaseFragment;
import cn.myxingxing.ysulibrary.bean.NoteList;
import cn.myxingxing.ysulibrary.bean.SearchBook;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.SearchBookEvent;
import cn.myxingxing.ysulibrary.net.IPUtil;
import cn.myxingxing.ysulibrary.net.OkHttpUtil;
import cn.myxingxing.ysulibrary.net.YsuCallback;
import cn.myxingxing.ysulibrary.util.ParseLibrary;
import cn.myxingxing.ysulibrary.view.xlist.XListView;
import cn.myxingxing.ysulibrary.view.xlist.XListView.IXListViewListener;

public class HomeFragment extends BaseFragment implements OnItemClickListener,OnClickListener,OnItemSelectedListener{
	
	private int mScreenWidth;
	private int page = 1;
	private LinearLayout ly_no_data,layout_book_search;
	private TextView tv_title;
	private Spinner first_spinner;
	private EditText et_book_name;
	private XListView lv_book;
	private TextView tv_no;
	private RelativeLayout ly_progress,layout_action;
	private PopupWindow morePop;
	private String strSearchType;
	private List<SearchBook> searchBookList;
	private List<NoteList> topLendList;
	private SearchBookAdapter searchBookAdapter;
	private NotelistAdapter topLendaAdapter;
	private int LISTVIEW_TAG =1;
	private static final int LV_SEARCH = 1;
	private static final int LV_HOT = 2;
	private static final int LV_NEW = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

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
		view.findViewById(R.id.btn_search_ok).setOnClickListener(this);;
		ly_no_data = (LinearLayout)view.findViewById(R.id.ly_no_data);
		tv_title = (TextView)view.findViewById(R.id.tv_title);
		first_spinner = (Spinner)view.findViewById(R.id.first_spinner);
		et_book_name = (EditText)view.findViewById(R.id.et_book_name);
		lv_book = (XListView)view.findViewById(R.id.lv_book);
		tv_no = (TextView)view.findViewById(R.id.tv_no);
		ly_progress = (RelativeLayout)view.findViewById(R.id.ly_progress);
		layout_book_search = (LinearLayout)view.findViewById(R.id.layout_book_search);
		layout_action = (RelativeLayout)view.findViewById(R.id.layout_action);
		
		String[] firstsp = getResources().getStringArray(R.array.titlespinner);
		ArrayAdapter<String> firstAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,firstsp);
		first_spinner.setAdapter(firstAdapter);	
		first_spinner.setOnItemSelectedListener(this);	
		initXlistView();
	}
 
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onUserEvent(SearchBookEvent event) {
		switch (event.getInfo()) {
		case Config.SEARCH_BOOK_FAILED:
			showErrorView();
			ShowToast("数据解析失败,请重试");
			break;
		case Config.SEARCH_BOOK_EMPTY:
			showErrorView();
			break;
		case Config.SEARCH_BOOK_SUCCESS:
			searchBookAdapter = new SearchBookAdapter(searchBookList, ct);
			showSuccessView();
			lv_book.stopRefresh();
			lv_book.setAdapter(searchBookAdapter);
			break;
		case Config.SEARCH_LOAD_EMPTY:
			ShowToast("已无更多数据");
			lv_book.stopLoadMore();
			ly_progress.setVisibility(View.GONE);
			break;
		case Config.SEARCH_LOAD_MORE_SUCCRESS:
			lv_book.stopLoadMore();
			searchBookAdapter.notifyDataSetChanged();
			break;
		case Config.TOP_LEND_SUCCESS:
			showSuccessView();
			topLendaAdapter = new NotelistAdapter(ct, R.layout.item_lend, topLendList);
			lv_book.setAdapter(topLendaAdapter);
			
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_search_top:
			showPop();
			break;
		case R.id.btn_search_ok:
			searchBook();
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
	
	private void searchLoadMore(){
		ly_progress.setVisibility(View.VISIBLE);
		ly_no_data.setVisibility(View.GONE);
		Map<String, String> map = new HashMap<String, String>();
		map.put("strSearchType", strSearchType);
		map.put("match_flag", "forward");
		map.put("historyCount", "1");
		map.put("strText", et_book_name.getText().toString());
		map.put("doctype", "ALL");//图书种类
		map.put("displaypg", "20");//默认20本
		map.put("showmode", "list");
		map.put("sort", "CATA_DATE");
		map.put("orderby", "desc");
		map.put("location", "ALL");
		map.put("page", page+"");
		OkHttpUtil.enqueue(IPUtil.search_book, map, new YsuCallback(ct){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				List<SearchBook> list = ParseLibrary.getSearchBooks(result);
				if (list == null) {
					EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_FAILED));
				}else if (list.size() != 0) {
					EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_LOAD_EMPTY));
				}else {
					searchBookList.addAll(list);
					EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_LOAD_MORE_SUCCRESS));
				}
			}

			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	} 
	
	private void searchBook() {
		if (TextUtils.isEmpty(et_book_name.getText().toString())) {
			Toast.makeText(ct, "请输入要检索的关键字", Toast.LENGTH_SHORT).show();
			return;
		}else {
			ly_progress.setVisibility(View.VISIBLE);
			ly_no_data.setVisibility(View.GONE);
			Map<String, String> map = new HashMap<String, String>();
			map.put("strSearchType", strSearchType);
			map.put("match_flag", "forward");
			map.put("historyCount", "1");
			map.put("strText", et_book_name.getText().toString());
			map.put("doctype", "ALL");//图书种类
			map.put("displaypg", "20");//默认20本
			map.put("showmode", "list");
			map.put("sort", "CATA_DATE");
			map.put("orderby", "desc");
			map.put("location", "ALL");
			map.put("page", page+"");
			OkHttpUtil.enqueue(IPUtil.search_book, map, new YsuCallback(ct){
				@Override
				public void onSuccess(String result) throws IOException {
					super.onSuccess(result);
					searchBookList = ParseLibrary.getSearchBooks(result);
					if (searchBookList == null) {
						EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_FAILED));
					}else if (searchBookList.size() != 0) {
						EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_SUCCESS));
					}else {
						EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_EMPTY));
					}
				}

				@Override
				public void onFailure(String error) throws IOException {
					super.onFailure(error);
				}
			});
		}
	}
	
	private void changeTextView(View view){
		switch (view.getId()) {
		case R.id.btn_hot_book:
			lv_book.setVisibility(View.GONE);
			layout_book_search.setVisibility(View.GONE);
			tv_title.setText("热门借阅");
			tv_title.setTag("热门借阅");
			LISTVIEW_TAG = LV_HOT;
			ly_progress.setVisibility(View.VISIBLE);
			lv_book.setOnItemClickListener(this);
			showHotBook();
			break;
		case R.id.btn_layout_new_book:
			tv_title.setText("新书通报");
			tv_title.setTag("新书通报");
			LISTVIEW_TAG = LV_NEW;
			layout_book_search.setVisibility(View.GONE);
			lv_book.setOnItemClickListener(this);
			break;
		case R.id.btn_layout_book_search:
			tv_title.setText("检索");
			tv_title.setText("检索");
			LISTVIEW_TAG = LV_SEARCH;
			lv_book.setVisibility(View.GONE);
			layout_book_search.setVisibility(View.VISIBLE);
			lv_book.setOnItemClickListener(this);
			break;
		default:
			break;
		}
		morePop.dismiss();
	}
	
	private void showHotBook(){
		OkHttpUtil.enqueue(IPUtil.top_lend, null, new YsuCallback(ct){
			@Override
			public void onSuccess(String result) throws IOException {
				super.onSuccess(result);
				topLendList = ParseLibrary.getTopLendBooks(result);
				if (topLendList == null) {
					EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_FAILED));
				}else if (topLendList.size() == 0) {
					EventBus.getDefault().post(new SearchBookEvent(Config.SEARCH_BOOK_EMPTY));
				}else {
					EventBus.getDefault().post(new SearchBookEvent(Config.TOP_LEND_SUCCESS));
				}
			}
			@Override
			public void onFailure(String error) throws IOException {
				super.onFailure(error);
			}
		});
	}
	
	private void showErrorView(){
		ly_no_data.setVisibility(View.VISIBLE);
		ly_progress.setVisibility(View.GONE);
		lv_book.setVisibility(View.GONE);
	}
	
	private void showSuccessView(){
		ly_no_data.setVisibility(View.GONE);
		ly_progress.setVisibility(View.GONE);
		lv_book.setVisibility(View.VISIBLE);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(ct, BookDetailActivity.class);
		switch (LISTVIEW_TAG) {
		case LV_SEARCH:
			intent.putExtra("detailUrl", searchBookList.get(position-1).getBook_url().toString());
			break;
		case LV_NEW:
			
			break;
		case LV_HOT:
			
			break;
		default:
			break;
		}
		startActivity(intent);
	}
	
	private void initXlistView(){
		lv_book.setPullLoadEnable(true);
		lv_book.setPullRefreshEnable(true);
		lv_book.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				searchBookList = new ArrayList<SearchBook>();
				page = 1;
				searchBook();
			}
			
			@Override
			public void onLoadMore() {
				page++;
				searchLoadMore();
			}
		});
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}

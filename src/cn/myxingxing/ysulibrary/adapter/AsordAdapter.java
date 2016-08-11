package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import cn.myxingxing.ysulibrary.bean.BookAsord;

import com.example.ysulibrary.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AsordAdapter extends BaseAdapter {
	
	private List<BookAsord> lists;
	private Context mContext;
	
	public AsordAdapter(List<BookAsord> lists, Context context) {
		this.lists = lists;
		mContext = context;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BookAsord bookAsord = lists.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_asord_hist, null);
		TextView tv_book_title = (TextView)view.findViewById(R.id.tv_book_title);
		TextView tv_author = (TextView)view.findViewById(R.id.tv_author);
		TextView tv_book_publish = (TextView)view.findViewById(R.id.tv_book_publish);
		TextView tv_book_asord_date = (TextView)view.findViewById(R.id.tv_book_asord_date);
		TextView tv_asord_state = (TextView)view.findViewById(R.id.tv_asord_state);
		TextView tv_asord_other = (TextView)view.findViewById(R.id.tv_asord_other);
		
		tv_book_title.setText(bookAsord.getName());
		tv_author.setText("作者:" + bookAsord.getAuthor());
		tv_book_publish.setText("出版社:" + bookAsord.getPublish());
		tv_book_asord_date.setText("荐购日期" + bookAsord.getAsordday());
		tv_asord_state.setText("荐购状态:" + bookAsord.getNow());
		tv_asord_other.setText("处理备注:" + bookAsord.getBeizhu());
		return view;
	}

}

package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.BookHist;

public class LendHistoryAdapter extends BaseAdapter {
	
	private List<BookHist> lists;
	private Context mContext;
	
	public LendHistoryAdapter(List<BookHist> lists, Context context) {
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
		BookHist bookItem = lists.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_lend_hist, null);
		TextView tv_book_title = (TextView)view.findViewById(R.id.tv_book_title);
		TextView author = (TextView)view.findViewById(R.id.tv_author);
		TextView tv_book_isbn = (TextView)view.findViewById(R.id.tv_book_isbn);
		TextView tv_book_lend_date = (TextView)view.findViewById(R.id.tv_book_lend_date);
		TextView tv_book_return_date= (TextView)view.findViewById(R.id.tv_book_return_date);
		TextView tv_book_location = (TextView)view.findViewById(R.id.tv_book_location);
				
		tv_book_title.setText(bookItem.getName());
		author.setText("作者:" + bookItem.getAuthor());
		tv_book_isbn.setText("条形码:" + bookItem.getIsbn());
		tv_book_lend_date.setText("借阅日期:" + bookItem.getLendyear());
		tv_book_return_date.setText("归还日期:" + bookItem.getGiveyear());
		tv_book_location.setText("馆藏地:" + bookItem.getLocation());
		return view;
	}

}

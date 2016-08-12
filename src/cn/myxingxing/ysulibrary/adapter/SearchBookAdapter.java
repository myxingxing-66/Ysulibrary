package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.bean.SearchBook;

import com.example.ysulibrary.R;

public class SearchBookAdapter extends BaseAdapter {
	
	private List<SearchBook> lists;
	private Context mContext;
	
	public SearchBookAdapter(List<SearchBook> lists, Context context) {
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
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_book, null);
		SearchBook searchBook = lists.get(position);
		TextView bookTitle = (TextView)view.findViewById(R.id.title_textView);
		TextView bookAuthor = (TextView)view.findViewById(R.id.author_textView);
		TextView bookISBN = (TextView)view.findViewById(R.id.ISBN_textView);
		TextView bookBorrow = (TextView)view.findViewById(R.id.borrow_textView);
		bookTitle.setText(searchBook.getName());
		bookAuthor.setText(searchBook.getAuther()+searchBook.getPublish());
		bookISBN.setText(searchBook.getIsbn());
		bookBorrow.setText(searchBook.getSave_num() + searchBook.getNow_num());
		return view;
	}

}

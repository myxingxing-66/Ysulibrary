package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.NewBook;

public class NewbookAdapter extends ArrayAdapter<NewBook> {
	
	private int resourceId;

	public NewbookAdapter(Context context, int resource, List<NewBook> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewBook bookItem = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView bookTitle = (TextView)view.findViewById(R.id.title_textView);
		TextView bookAuthor = (TextView)view.findViewById(R.id.author_textView);
		TextView bookISBN = (TextView)view.findViewById(R.id.ISBN_textView);
		
		bookTitle.setText(bookItem.getName());
		bookAuthor.setText(bookItem.getInfo());
		bookISBN.setText(bookItem.getIsbn());
		
		return view;
	}

}

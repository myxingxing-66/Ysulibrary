package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.NoteList;



public class NotelistAdapter extends ArrayAdapter<NoteList> {
	
	private int resourceId;

	public NotelistAdapter(Context context, int resource, List<NoteList> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NoteList bookItem = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView bookTitle = (TextView)view.findViewById(R.id.title_textView);
		TextView bookAuthor = (TextView)view.findViewById(R.id.author_textView);
		TextView bookpublish= (TextView)view.findViewById(R.id.publish_textView);
		TextView booknumber = (TextView)view.findViewById(R.id.number_textView);
		TextView bookno=(TextView)view.findViewById(R.id.no_textView);
		
		bookTitle.setText(bookItem.getTitle());
		bookAuthor.setText(bookItem.getAuthor());
		bookpublish.setText(bookItem.getPublish());
		booknumber.setText(bookItem.getLend_num());
		bookno.setText(bookItem.getNumber());
		
		return view;
	}

}

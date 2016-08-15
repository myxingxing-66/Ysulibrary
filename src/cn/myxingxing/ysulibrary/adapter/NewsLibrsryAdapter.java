package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.NewsLib;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsLibrsryAdapter extends BaseAdapter {
	
	private List<NewsLib> lists;
	private Context mContext;
	
	public NewsLibrsryAdapter(List<NewsLib> lists, Context context) {
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
		NewsLib newsLib = lists.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_library_news, null);
		TextView tv_title = (TextView)view.findViewById(R.id.tv_news_title);
		TextView tv_time = (TextView)view.findViewById(R.id.tv_news_time);
		tv_title.setText(newsLib.getTitle());
		tv_time.setText(newsLib.getTime());
		return view;
	}

}

package com.example.ysulibrary.adapter;

import java.util.List;

import com.example.ysulibrary.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReaderInfoAdapter extends BaseAdapter {
	
	private Context context;
	private List<String> info;
	
	public ReaderInfoAdapter(Context context, List<String> info) {
		this.context = context;
		this.info = info;
	}

	@Override
	public int getCount() {
		return info.size();
	}

	@Override
	public Object getItem(int position) {
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_read_info, null);
		TextView tx_info_item = (TextView)view.findViewById(R.id.tx_info_item);
		tx_info_item.setText(info.get(position));
		return view;
	}

}

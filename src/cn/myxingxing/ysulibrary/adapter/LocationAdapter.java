package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.Lotinfo;

public class LocationAdapter extends ArrayAdapter<Lotinfo> {
	
	private int resourceId;
	
	public LocationAdapter(Context context, int textViewResourceId,List<Lotinfo> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Lotinfo lotinfo = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		
		TextView locaTextView = (TextView)view.findViewById(R.id.locationTextView);
		TextView loisbnTextView = (TextView)view.findViewById(R.id.loisbnTextView);
		TextView lonowTextView = (TextView)view.findViewById(R.id.lonowTextView);
		TextView lonumberTextView = (TextView)view.findViewById(R.id.lonumberTextView);
		
		locaTextView.setText(lotinfo.getLocal());
		loisbnTextView.setText(lotinfo.getIsbn());
		lonowTextView.setText(lotinfo.getNow());
		lonumberTextView.setText(lotinfo.getNumber());
		return view;
	}	
}

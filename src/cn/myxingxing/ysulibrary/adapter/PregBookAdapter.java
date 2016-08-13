package cn.myxingxing.ysulibrary.adapter;

import java.util.List;

import cn.myxingxing.ysulibrary.R;
import cn.myxingxing.ysulibrary.bean.BookPreg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PregBookAdapter extends BaseAdapter {
	
	private List<BookPreg> lists;
	private Context mContext;
	
	public PregBookAdapter(List<BookPreg> lists, Context context) {
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
		BookPreg bookPreg = lists.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_preg_book, null);
		TextView tv_book_isbn = (TextView) view.findViewById(R.id.tv_book_isbn);
		TextView tv_tit_author = (TextView)view.findViewById(R.id.tv_tit_author);
		TextView tv_book_location = (TextView)view.findViewById(R.id.tv_book_location);
		TextView tv_book_asord_date = (TextView)view.findViewById(R.id.tv_book_asord_date);
		TextView tv_book_deadline = (TextView)view.findViewById(R.id.tv_book_deadline);
		TextView tv_book_get = (TextView)view.findViewById(R.id.tv_book_get);
		TextView tv_book_state = (TextView)view.findViewById(R.id.tv_book_state);
		
		tv_book_isbn.setText("索书号:" + bookPreg.getIsbn());
		tv_tit_author.setText("题名/责任者:" + bookPreg.getTit_aut());
		tv_book_location.setText("馆藏地:" + bookPreg.getLocation());
		tv_book_asord_date.setText("预约(到书)日:" + bookPreg.getPregday());
		tv_book_deadline.setText("截止日期:" + bookPreg.getEndday());
		tv_book_get.setText("取书地:" + bookPreg.getHavebook());
		tv_book_state.setText("状态:" + bookPreg.getNow());
		return view;
	}

}

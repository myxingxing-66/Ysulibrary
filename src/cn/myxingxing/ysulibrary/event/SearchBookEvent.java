package cn.myxingxing.ysulibrary.event;

public class SearchBookEvent {
	private int info;

	public SearchBookEvent(int info) {
		super();
		this.info = info;
	}

	public int getInfo() {
		return info;
	}
}

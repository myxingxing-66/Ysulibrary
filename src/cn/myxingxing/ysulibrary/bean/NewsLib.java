package cn.myxingxing.ysulibrary.bean;

public class NewsLib {
	private String title;
	private String href;
	private String time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "NewsLib [title=" + title + ", href=" + href + ", time=" + time
				+ "]";
	}
}

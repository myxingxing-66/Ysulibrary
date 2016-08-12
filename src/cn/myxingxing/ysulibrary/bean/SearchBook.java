package cn.myxingxing.ysulibrary.bean;

public class SearchBook {
	private String name; //题目
	private String auther; //作者
	private String publish; //出版社
	private String save_num; //馆藏
	private String now_num; //可借数量
	private String type; //书刊类型
	private String isbn; //索书号
	private String base_url="http://202.206.242.99/opac/";
	private String book_url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getSave_num() {
		return save_num;
	}
	public void setSave_num(String save_num) {
		this.save_num = save_num;
	}
	public String getNow_num() {
		return now_num;
	}
	public void setNow_num(String now_num) {
		this.now_num = now_num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBase_url() {
		return base_url;
	}
	public void setBase_url(String base_url) {
		this.base_url = base_url;
		System.out.println("base_url"+base_url);
	}
	public String getBook_url() {
		return book_url;
	}
	public void setBook_url(String book_url) {
		this.book_url = base_url + book_url;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}

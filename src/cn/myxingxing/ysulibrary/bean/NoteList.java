package cn.myxingxing.ysulibrary.bean;

import cn.myxingxing.ysulibrary.net.IPUtil;

public class NoteList {
	private String number="";//序号
	private String title="";//书名
	private String author="";//作者
	private String publish="";//出版社
	private String isbn="";//索书号
	private String sav_num="";//馆藏数目
	private String lend_num="";//可借数目
	private String bookhref="";//书的地址
	private String baseUrl= IPUtil.IP;
	public String getBookhref(){
		return bookhref;
	}
	public String getNumber(){
		return number;
	}
	public String getTitle(){
		return title;
	}
	public String getAuthor(){
		return author;
	}
	public String getPublish(){
		return publish;
	}
	public String getIsbn(){
		return isbn;
	}
	public String getSav_num(){
		return sav_num;
	}
	public String getLend_num(){
		return lend_num;
	}
	public void setNumber(String number){
		this.number=number;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public void setAuthor(String author){
		this.author=author;
	}
	public void setPublish(String publish){
		this.publish=publish;
	}
	public void setIsbn(String isbn){
		this.isbn=isbn;
	}
	public void setSav_num(String save_num){
		this.sav_num=save_num;
	}
	public void setLend_num(String lend_num){
		this.lend_num=lend_num;
	}
	public void setBookhref(String bookhref){
		this.bookhref=baseUrl+bookhref;
	}
}

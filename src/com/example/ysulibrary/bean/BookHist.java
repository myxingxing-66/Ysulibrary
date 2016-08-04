package com.example.ysulibrary.bean;

import com.example.ysulibrary.net.IPUtil;

public class BookHist {
	private String num="";//编号
	private String isbn="";//索书号
	private String name="";//书名
	private String author="";//作者
	private String lendyear="";//借出日期
	private String giveyear="";//归还日期
	private String location="";//馆藏地
	private String baseurl=IPUtil.IP;
	private String bookurl="";
	public String getBookurl() {
		return bookurl;
	}
	public void setBookurl(String bookurl) {
		this.bookurl = baseurl+bookurl;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLendyear() {
		return lendyear;
	}
	public void setLendyear(String lendyear) {
		this.lendyear = lendyear;
	}
	public String getGiveyear() {
		return giveyear;
	}
	public void setGiveyear(String giveyear) {
		this.giveyear = giveyear;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}

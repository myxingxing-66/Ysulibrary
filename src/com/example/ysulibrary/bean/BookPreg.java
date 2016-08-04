package com.example.ysulibrary.bean;

public class BookPreg {
	private String isbn="";//索书号
	private String tit_aut="";//书名和作者
	private String location="";//馆藏地
	private String pregday="";//预约日
	private String endday="";//截止日期
	private String havebook="";//取书处
	private String now="";//状态
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTit_aut() {
		return tit_aut;
	}
	public void setTit_aut(String tit_aut) {
		this.tit_aut = tit_aut;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPregday() {
		return pregday;
	}
	public void setPregday(String pregday) {
		this.pregday = pregday;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	public String getHavebook() {
		return havebook;
	}
	public void setHavebook(String havebook) {
		this.havebook = havebook;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	
}

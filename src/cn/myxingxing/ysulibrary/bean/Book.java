package cn.myxingxing.ysulibrary.bean;

public class Book {
	private String nowlend="";//已借和可借
	private String maxlend="";//最大借阅
	private String isbn="";//索书号
	private String tit_aut="";//提名和作者
	private String lendday="";//借出日期
	private String giveday="";//应还日期
	private String lennum="";//续借数量
	private String location="";//馆藏地
	private String other="";//附件
	public String getNowlend() {
		return nowlend;
	}
	public void setNowlend(String nowlend) {
		this.nowlend = nowlend;
	}
	public String getMaxlend() {
		return maxlend;
	}
	public void setMaxlend(String maxlend) {
		this.maxlend = maxlend;
	}
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
	public String getLendday() {
		return lendday;
	}
	public void setLendday(String lendday) {
		this.lendday = lendday;
	}
	public String getGiveday() {
		return giveday;
	}
	public void setGiveday(String giveday) {
		this.giveday = giveday;
	}
	public String getLennum() {
		return lennum;
	}
	public void setLennum(String lennum) {
		this.lennum = lennum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}

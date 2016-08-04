package com.example.ysulibrary.util;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.ysulibrary.bean.BookAsord;
import com.example.ysulibrary.bean.BookHist;
import com.example.ysulibrary.bean.BookPreg;
import com.example.ysulibrary.bean.NowLend;
import com.example.ysulibrary.config.Config;
import com.example.ysulibrary.event.YsuEvent;

public class ParseLibrary {
	
	/**
	 * @author myxingxing
	 * @param String类型的网页内容
	 * @return 个人信息
	 */
	public static List<String> getReaderInfo(String result){
		List<String> info = new ArrayList<String>();
		try {
			Document doc=Jsoup.parse(result);
			Elements mylib_msg=doc.getElementsByClass("mylib_msg");
			Elements a=mylib_msg.get(0).getElementsByTag("a");
			for(int i=0;i<4;i++){
				String s=a.get(i).text().toString();
				info.add(s);
			}
			Element mylib_info=doc.getElementById("mylib_info");
			Elements tr1=mylib_info.getElementsByTag("tr").get(0).getElementsByTag("td");
			Elements tr=mylib_info.getElementsByTag("tr");
			for(int i=1;i<tr1.size();i++){
				String ss=tr1.get(i).text().toString();
				info.add(ss);
			}
			for(int i=1;i<tr.size();i++){
				Elements td=tr.get(i).getElementsByTag("td");
				for(int j=0;j<td.size();j++){
					String sss=td.get(j).text().toString();
					info.add(sss);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			EventBus.getDefault().post(new YsuEvent(Config.READERINFO_PARSE_ERROR));
		}
		return info;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 当前借阅的图书信息
	 */
	public static List<NowLend> getNowLend(String result){
		List<NowLend> nowLend = new ArrayList<NowLend>();
		try {
			Document doc = Jsoup.parse(result);
			Element table = doc.getElementsByClass("table_line").get(0);
			Elements tr = table.getElementsByTag("tr");
			for (int i = 1; i < tr.size(); i++) {
				Elements td = tr.get(i).getElementsByTag("td");
				NowLend book = new NowLend();
				book.setIsbn(td.get(0).text().toString());
				book.setTit_aut(td.get(1).text().toString());
				book.setLendday(td.get(2).text().toString());
				book.setGiveday(td.get(3).text().toString());
				book.setLennum(td.get(4).text().toString());
				book.setLocation(td.get(5).text().toString());
				book.setOther(td.get(6).text().toString());
				nowLend.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return nowLend;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 当前借阅的图书信息
	 */
	public static List<BookHist> getLendHist(String result){
		List<BookHist> lendHists = new ArrayList<BookHist>();
		try {
			Document doc=Jsoup.parse(result);
			Element table=doc.getElementsByClass("table_line").get(0);
			Elements tr=table.getElementsByTag("tr");
			for(int i=1;i<tr.size();i++){
				Elements td=tr.get(i).getElementsByTag("td");
				BookHist book=new BookHist();
				book.setNum(td.get(0).text().toString());
				book.setIsbn(td.get(1).text().toString());
				book.setName(td.get(2).text().toString());
				book.setBookurl(td.get(2).getElementsByTag("a").attr("href").substring(2, 35).toString());
				book.setAuthor(td.get(3).text().toString());
				book.setLendyear(td.get(4).text().toString());
				book.setGiveyear(td.get(5).text().toString());
				book.setLocation(td.get(6).text().toString());
				lendHists.add(book);	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return lendHists;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 当前荐购的图书信息
	 */
	public static List<BookAsord> getAsordHist(String result){
		List<BookAsord> asords = new ArrayList<BookAsord>();
		try {
			Document doc=Jsoup.parse(result);
			Element table=doc.getElementsByClass("table_line").get(0);
			Elements tr=table.getElementsByTag("tr");
			for(int i=1;i<tr.size();i++){
				Elements td=tr.get(i).getElementsByTag("td");
				BookAsord book=new BookAsord();
				book.setName(td.get(0).text().toString());
				book.setAuthor(td.get(1).text().toString());
				book.setPublish(td.get(2).text().toString());
				book.setAsordday(td.get(3).text().toString());
				book.setNow(td.get(4).text().toString());
				book.setBeizhu(td.get(5).text().toString());
				asords.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return asords;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 当前预约的图书信息
	 */
	public static List<BookPreg> getPreLists(String result){
		List<BookPreg> pregs = new ArrayList<BookPreg>();
		try {
			Document doc=Jsoup.parse(result);
			Element table=doc.getElementsByClass("table_line").get(0);
			Elements tr=table.getElementsByTag("tr");
			for(int i=1;i<tr.size();i++){
				Elements td=tr.get(i).getElementsByTag("td");
				BookPreg book=new BookPreg();
				book.setIsbn(td.get(0).text().toString());
				book.setTit_aut(td.get(1).text().toString());
				book.setLocation(td.get(2).text().toString());
				book.setPregday(td.get(3).text().toString());
				book.setEndday(td.get(4).text().toString());
				book.setHavebook(td.get(5).text().toString());
				book.setNow(td.get(6).text().toString());
				pregs.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pregs;
	}
}

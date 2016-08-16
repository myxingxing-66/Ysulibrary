package cn.myxingxing.ysulibrary.util;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.myxingxing.ysulibrary.bean.BookAsord;
import cn.myxingxing.ysulibrary.bean.BookHist;
import cn.myxingxing.ysulibrary.bean.BookPreg;
import cn.myxingxing.ysulibrary.bean.Lotinfo;
import cn.myxingxing.ysulibrary.bean.NewBook;
import cn.myxingxing.ysulibrary.bean.NewsLib;
import cn.myxingxing.ysulibrary.bean.NoteList;
import cn.myxingxing.ysulibrary.bean.NowLend;
import cn.myxingxing.ysulibrary.bean.SearchBook;
import cn.myxingxing.ysulibrary.config.Config;
import cn.myxingxing.ysulibrary.event.YsuEvent;

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
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 检索的图书信息
	 */
	public static List<SearchBook> getSearchBooks(String reslut){
		Document doc = Jsoup.parse(reslut);
		List<SearchBook> searchBookBeans = new ArrayList<SearchBook>();
		try {
			Elements eles = doc.getElementsByClass("book_list_info");
			for (Element e : eles) {
				SearchBook searchBookBean = new SearchBook();
				searchBookBean.setType(e.getElementsByTag("span").get(0).text());
				searchBookBean.setName(e.getElementsByTag("a").get(0).text());
				searchBookBean.setBook_url(e.getElementsByTag("a").attr("href"));
				searchBookBean.setIsbn(e.getElementsByTag("h3").get(0).childNode(2).toString());
				searchBookBean.setType(e.getElementsByTag("span").get(0).text());
				String s=e.getElementsByTag("p").get(0).childNode(4).toString();
				searchBookBean.setPublish(s.substring(0,s.length()-11)+" "+s.substring(s.length()-5, s.length()));
				searchBookBean.setSave_num(e.getElementsByTag("span").get(1).childNode(0).toString());
				searchBookBean.setNow_num(e.getElementsByTag("span").get(1).childNode(2).toString());
				searchBookBean.setAuther(e.getElementsByTag("p").get(0).childNode(2).toString());
				searchBookBeans.add(searchBookBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return searchBookBeans;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 馆藏信息
	 */
	public static List<Lotinfo> getLoctionBooks(String result){
		List<Lotinfo> list = new ArrayList<Lotinfo>();
		Document doc = Jsoup.parse(result);
		try {
			Elements infolist = doc.getElementsByClass("whitetext");
			if (infolist.size() == 0) {
				return null;
			} else {
				for (int i = 0; i < infolist.size(); i++) {
					Lotinfo location = new Lotinfo();
					location.setIsbn(infolist.get(i).getElementsByTag("td").get(0).text());
					location.setNumber(infolist.get(i).getElementsByTag("td").get(1).text());
					location.setYear(infolist.get(i).getElementsByTag("td").get(2).text());
					location.setLocal(infolist.get(i).getElementsByTag("td").get(3).text());
					location.setNow(infolist.get(i).getElementsByTag("td").get(4).text());
					list.add(location);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 热门借阅
	 */
	public static List<NoteList> getTopLendBooks(String result){
		List<NoteList> list = new ArrayList<NoteList>();
		Document doc = Jsoup.parse(result);
		try {
			Elements note=doc.getElementsByClass("table_line");
			Elements note_tr=note.get(0).getElementsByTag("tr");
			for(int i=1;i<note_tr.size();i++){
				NoteList notetr=new NoteList();
				Elements notetd=note_tr.get(i).getElementsByTag("td");
				notetr.setNumber(notetd.get(0).text().toString());
				notetr.setTitle(notetd.get(1).text().toString());
				notetr.setBookhref(notetd.get(1).getElementsByTag("a").attr("href").substring(2, 35).toString());
				notetr.setAuthor(notetd.get(2).text().toString());
				notetr.setPublish(notetd.get(3).text().toString());
				notetr.setIsbn(notetd.get(4).text().toString());
				notetr.setSav_num(notetd.get(5).text().toString());
				notetr.setLend_num(notetd.get(6).text().toString());
				list.add(notetr);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 新书通报
	 */
	public static List<NewBook> getNewBooks(String result){
		List<NewBook> list = new ArrayList<NewBook>();
		Document doc = Jsoup.parse(result);
		try {
			Elements listbook=doc.getElementsByClass("list_books");
			for(int i=0;i<listbook.size();i++){
				NewBook newbook=new NewBook();
				newbook.setName(listbook.get(i).getElementsByTag("a").text().toString());
				newbook.setDetailUrl(listbook.get(i).getElementsByTag("a").attr("href").substring(2, 35).toString());
				newbook.setIsbn(listbook.get(i).getElementsByTag("h3").get(0).childNode(1).toString());
				newbook.setInfo(listbook.get(i).getElementsByTag("p").get(0).childNode(2).toString());
				list.add(newbook);
			}
		} catch (Exception e) {
			return null;
		}
		return list;
	}
	
	/**
	 * @author myxingxing
	 * @param result 字符串类型的网页
	 * @return 图书馆通知公告
	 */
	public static List<NewsLib> getNews(String result){
		List<NewsLib> list = new ArrayList<NewsLib>();
		Document doc = Jsoup.parse(result);
		try {
			Elements elementNew = doc.getElementById("ctl00_ContentPlaceHolder1_tz_nomar_td").getElementsByTag("a");
			for (int i = 0; i < elementNew.size() ; i++) {
				NewsLib newsLib = new NewsLib();
				newsLib.setHref(elementNew.get(i).attr("href"));
				newsLib.setTime(elementNew.get(i).attr("title"));
				newsLib.setTitle(elementNew.get(i).text().toString());
				list.add(newsLib);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public static String getNesDetail(String result){
		Document document = Jsoup.parse(result);
		Element element = null;
		try {
			element = document.getElementById("main");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return element.html().toString();
	}
	
}

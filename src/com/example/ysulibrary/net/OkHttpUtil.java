package com.example.ysulibrary.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class OkHttpUtil {
	private Context context;
	public static String Seisson;
	public static OkHttpClient mOkHttpClient;
	public static CookiesManager cookiesManager;
	
	public static CookiesManager getCookManager(){
		return cookiesManager;
	}
	
	public OkHttpUtil(Context mcontext) {
		this.context = mcontext;
		cookiesManager = new CookiesManager();
		mOkHttpClient = builder.connectTimeout(60, TimeUnit.SECONDS).cookieJar(cookiesManager).build();

	}

	private static final okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();

	/**
	 * 该不会开启异步线程。
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Response execute(Request request) throws IOException {
		return mOkHttpClient.newCall(request).execute();
	}

	/**
	 * 开启异步线程访问网络
	 * 
	 * @param request
	 * @param responseCallback
	 */
	public static void enqueue(String url, Map<String, String> maps,Callback responseCallback) {
		mOkHttpClient.newCall(getPraRequest(url, maps)).enqueue(responseCallback);
	}

	public static void enqueueFile(String url, File file,Callback responseCallback) {
		mOkHttpClient.newCall(getFileRequest(url, file)).enqueue(responseCallback);
	}

	public static void downPic(String url, Callback responseCallback) {
		mOkHttpClient.newCall(getPraRequest(url, null)).enqueue(responseCallback);
	}

	public static Bitmap getCode(String url){
		InputStream is = null;
		try {
			is = mOkHttpClient.newCall(getCheckCode(url)).execute().body().byteStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bm = BitmapFactory.decodeStream(is);
		return bm;
	}
	
	/**
	 * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
	 * 
	 * @param request
	 */
	public static void enqueue(Request request) {
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {

			}
		});

	}

	public static String getStringFromServer(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = execute(request);
		if (response.isSuccessful()) {
			String responseUrl = response.body().string();
			return responseUrl;
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 这里使用了HttpClinet的API。只是为了方便
	 * 
	 * @param params
	 * @return
	 */
	public static String formatParams(List<BasicNameValuePair> params) {
		return URLEncodedUtils.format(params, CHARSET_NAME);
	}

	/**
	 * 为HttpGet 的 url 方便的添加多个name value 参数。
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String attachHttpGetParams(String url,
			List<BasicNameValuePair> params) {
		return url + "?" + formatParams(params);
	}

	/**
	 * 为HttpGet 的 url 方便的添加1个name value 参数。
	 * 
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String attachHttpGetParam(String url, String name,String value) {
		return url + "?" + name + "=" + value;
	}

	public static RequestBody getRequestBody(Map<String, String> maps) {
		Builder builder = new FormBody.Builder().add("DeviceType", "ANDROID").add("SystemVersion", "1.0");
		for (String key : maps.keySet()) {
			builder.add(key, maps.get(key));
		}
		return builder.build();
	}

	private static Request getPraRequest(String url, Map<String, String> maps) {
		Builder builder = new FormBody.Builder().add("DeviceType", "ANDROID").add("SystemVersion", "1.0");
		if (maps != null) {
			for (String key : maps.keySet()) {
				builder.add(key, maps.get(key));
			}
		}
		return new Request.Builder().url(url).post(builder.build()).build();
	}

	public static Request getRequest(String url, Map<String, String> maps) {
		Builder builder = new FormBody.Builder().add("DeviceType", "ANDROID").add("SystemVersion", "1.0");
		for (String key : maps.keySet()) {
			builder.add(key, maps.get(key));
		}
		return new Request.Builder().url(url).post(builder.build()).build();
	}

	public static Request getFileRequest(String url, File file) {
		// RequestBody requestBody =
		// RequestBody.create(MediaType.parse("application/octet-stream"),
		// file);
		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		// builder.addFormDataPart("file",
		// null,RequestBody.create(MediaType.parse("image/png"),file));
		/*
		 * Builder builder= new FormBody.Builder().add("DeviceType",
		 * "ANDROID").add("SystemVersion", "1.0"); builder.addFormDataPart
		 */
		/*
		 * .addFormDataPart("hello", "android") .addFormDataPart("photo",
		 * file.getName(), RequestBody.create(null, file))
		 * .addPart(Headers.of("Content-Disposition",
		 * "form-data; name=\"another\";filename=\"another.dex\""),
		 * RequestBody.create(MediaType.parse("application/octet-stream"),
		 * file)) .build();
		 */
		// MultipartBody body= new
		// MultipartBody.Builder().addFormDataPart("file", file.getName(),
		// requestBody).build();
		MultipartBody body = builder.addPart(Headers.of("Content-Disposition","form-data; name=\"file\";filename=\"file.jpg\""),RequestBody.create(MediaType.parse("image/png"), file)).build();
		// RequestBody body= new
		// MultipartBody.Builder().addFormDataPart(MediaType.parse(
		// file.getName(), requestBody).build();

		return new Request.Builder().url(url).post(body).build();

	}

	public static Request getCheckCode(String url){
		return new Request.Builder().url(url).build();
	}
	
	private class CookiesManager implements CookieJar {
		private final PersistentCookieStore cookieStore = new PersistentCookieStore(context);
		@Override
		public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
			if (cookies != null && cookies.size() > 0) {
				for (Cookie item : cookies) {
					cookieStore.add(url, item);
				}
			}
		}

		@Override
		public List<Cookie> loadForRequest(HttpUrl url) {
			List<Cookie> cookies = cookieStore.get(url);
			return cookies;
		}
	}

}
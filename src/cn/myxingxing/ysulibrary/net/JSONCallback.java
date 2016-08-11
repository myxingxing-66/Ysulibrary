package cn.myxingxing.ysulibrary.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

public class JSONCallback implements Callback {
	private Activity mContext;

	public JSONCallback(Activity mcontext) {
		this.mContext = mcontext;
	}

	@Override
	public void onFailure(Call arg0, IOException arg1) {
		mContext.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(mContext, "«Î«Û ß∞‹£¨«ÎºÏ≤ÈÕ¯¬Á¥ÌŒÛ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void onSuccess(JSONObject json) throws IOException {
		System.out.println(json.toString());

	}

	public void OnLoginSuccess(JSONObject json, Response response)throws IOException {
	}

	public void onFailure(String error) throws IOException {
		System.out.println(error);
	}

	@Override
	public void onResponse(Call arg0, final Response arg1) throws IOException {
		if (arg1.isSuccessful()) {
			try {
				onSuccess(new JSONObject(arg1.body().string()));
			} catch (JSONException e) {
				onFailure("JSONΩ‚Œˆ“Ï≥£");
				e.printStackTrace();
			}
			OnLoginSuccess(null, arg1);
		} else {
			onFailure("«Î«Û ß∞‹£¨∑µªÿ¬Î" + "arg1.code()");
			System.out.println(arg1.code() + "");
		}
	}
}

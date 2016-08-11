package cn.myxingxing.ysulibrary.util;

import cn.myxingxing.ysulibrary.bean.User;

public class SingleManager {
	
	private static SingleManager _instance = null;

	public static SingleManager getInstance() {
		if (_instance == null) {

			_instance = new SingleManager();
		}
		return _instance;
	}

	private SingleManager() {

	}

	private User user = new User();

	public User getCurrentUser() {
		return this.user;
	}
}

package com.example.ysulibrary.event;

public class LoginSucceedEvent {
	private boolean loged;

	public LoginSucceedEvent(boolean loged) {
		this.loged = loged;
	}

	public boolean isLoged() {
		return loged;
	}
	
}

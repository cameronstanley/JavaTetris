package com.cameronstanley.javatetris.network.messages;

public class LoginResponse {
	
	private boolean isLoggedIn;
	
	public LoginResponse() {
		
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
}

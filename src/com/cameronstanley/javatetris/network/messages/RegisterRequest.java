package com.cameronstanley.javatetris.network.messages;

public class RegisterRequest {
	
	private String desiredUsername;
	private String desiredPassword;

	public RegisterRequest() {
		
	}

	public String getDesiredUsername() {
		return desiredUsername;
	}

	public void setDesiredUsername(String desiredUsername) {
		this.desiredUsername = desiredUsername;
	}

	public String getDesiredPassword() {
		return desiredPassword;
	}

	public void setDesiredPassword(String desiredPassword) {
		this.desiredPassword = desiredPassword;
	}
	
}

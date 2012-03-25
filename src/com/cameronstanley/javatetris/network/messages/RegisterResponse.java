package com.cameronstanley.javatetris.network.messages;

public class RegisterResponse {
	
	private boolean isSuccessfullyRegistered;
	
	public RegisterResponse() {
		
	}

	public boolean isSuccessfullyRegistered() {
		return isSuccessfullyRegistered;
	}

	public void setSuccessfullyRegistered(boolean isSuccessfullyRegistered) {
		this.isSuccessfullyRegistered = isSuccessfullyRegistered;
	}
	
}

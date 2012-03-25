package com.cameronstanley.javatetris.network.messages;

import com.cameronstanley.javatetris.server.User;

public class StartNewGameRequest {
	
	private User desiredOpponent;
	
	public StartNewGameRequest() {
		
	}

	public User getDesiredOpponent() {
		return desiredOpponent;
	}

	public void setDesiredOpponent(User desiredOpponent) {
		this.desiredOpponent = desiredOpponent;
	}
	
}

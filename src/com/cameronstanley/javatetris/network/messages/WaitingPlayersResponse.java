package com.cameronstanley.javatetris.network.messages;

import java.util.ArrayList;

import com.cameronstanley.javatetris.server.User;

public class WaitingPlayersResponse {
	
	private ArrayList<User> waitingPlayers;
	
	public WaitingPlayersResponse() {
		
	}

	public ArrayList<User> getWaitingPlayers() {
		return waitingPlayers;
	}

	public void setWaitingPlayers(ArrayList<User> waitingPlayers) {
		this.waitingPlayers = waitingPlayers;
	}
	
}

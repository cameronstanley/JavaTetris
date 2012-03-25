package com.cameronstanley.javatetris.network;

import com.cameronstanley.javatetris.server.User;
import com.esotericsoftware.kryonet.Connection;

public class PlayerConnection extends Connection {

	private User user;
	
	public PlayerConnection() {
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}

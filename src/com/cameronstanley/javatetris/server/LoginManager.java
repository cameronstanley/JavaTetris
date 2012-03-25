package com.cameronstanley.javatetris.server;

import java.util.ArrayList;

public class LoginManager {

	private ArrayList<User> loggedInUsers;
	
	public LoginManager() {
		loggedInUsers = new ArrayList<User>();
	}
	
	public boolean isUserLoggedIn(User user) {
		return loggedInUsers.contains(user);
	}
	
	public boolean logInUser(User user) {
		if (!isUserLoggedIn(user)) {
			loggedInUsers.add(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean logOutUser(User user) {
		if (isUserLoggedIn(user)) {
			loggedInUsers.remove(user);
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<User> getLoggedInUsers() {
		return loggedInUsers;
	}

	public void setLoggedInUsers(ArrayList<User> loggedInUsers) {
		this.loggedInUsers = loggedInUsers;
	}
	
}

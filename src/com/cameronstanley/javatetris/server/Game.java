package com.cameronstanley.javatetris.server;

public class Game {

	private User player1;
	private User player2;
	
	public Game() {
		
	}

	public User getPlayer1() {
		return player1;
	}

	public void setPlayer1(User player1) {
		this.player1 = player1;
	}

	public User getPlayer2() {
		return player2;
	}

	public void setPlayer2(User player2) {
		this.player2 = player2;
	}
	
}

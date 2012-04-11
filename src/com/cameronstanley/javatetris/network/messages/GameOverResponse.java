package com.cameronstanley.javatetris.network.messages;

public class GameOverResponse {

	private char winner;
	
	public GameOverResponse() {
		
	}

	public char getWinner() {
		return winner;
	}

	public void setWinner(char winner) {
		this.winner = winner;
	}
	
}

package com.cameronstanley.javatetris.client.model;

public class OnlineMultiplayerGame {

	private Player player;
	private Player opponent;
	
	public static final int BOARDWIDTH = 10;
	public static final int BOARDHEIGHT = 20;
	public static final int TETROMINOQUEUESIZE = 5;
	public static final int TETROMINOSTARTROTATION = 0;
	public static final int TETROMINOSTARTX = (BOARDWIDTH / 2) - 2;
	public static final int TETROMINOSTARTY = -1;
	
	public OnlineMultiplayerGame() {
		player = new Player();
		opponent = new Player();
		
		player.setBoard(new Board(BOARDWIDTH, BOARDHEIGHT));		
		player.setTetrominoQueue(new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY));
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
}
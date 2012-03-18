package com.cameronstanley.javatetris.client.model;

public class OnlineMultiplayerGame {

	private Player player1;
	private Player player2;
	
	public static final int BOARDWIDTH = 10;
	public static final int BOARDHEIGHT = 20;
	public static final int TETROMINOQUEUESIZE = 5;
	public static final int TETROMINOSTARTROTATION = 0;
	public static final int TETROMINOSTARTX = (BOARDWIDTH / 2) - 2;
	public static final int TETROMINOSTARTY = -1;
	
	public OnlineMultiplayerGame() {
		player1.setBoard(new Board(BOARDWIDTH, BOARDHEIGHT));
		player2.setBoard(new Board(BOARDWIDTH, BOARDHEIGHT));
		
		player1.setTetrominoQueue(new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY));
	}
	
}

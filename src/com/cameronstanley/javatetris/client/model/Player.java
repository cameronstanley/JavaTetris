package com.cameronstanley.javatetris.client.model;

public class Player {

	private Board board;
	private Tetromino activeTetromino;
	private TetrominoQueue tetrominoQueue;
	private int level;
	private int totalLinesCleared;
	private long score;
	
	public Player() {
		
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Tetromino getActiveTetromino() {
		return activeTetromino;
	}

	public void setActiveTetromino(Tetromino activeTetromino) {
		this.activeTetromino = activeTetromino;
	}

	public TetrominoQueue getTetrominoQueue() {
		return tetrominoQueue;
	}

	public void setTetrominoQueue(TetrominoQueue tetrominoQueue) {
		this.tetrominoQueue = tetrominoQueue;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTotalLinesCleared() {
		return totalLinesCleared;
	}

	public void setTotalLinesCleared(int totalLinesCleared) {
		this.totalLinesCleared = totalLinesCleared;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
}

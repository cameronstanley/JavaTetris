package com.cameronstanley.javatetris.client.model;

public class GameLogic {

	public static void rotateTetromino(Player player) {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition());
		movedTetromino.rotateForward();
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public static void moveTetrominoRight(Player player) {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition() + 1,
				player.getActiveTetromino().getYPosition());
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}	
	}
	
	public static void moveTetrominoLeft(Player player) {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(), 
				player.getActiveTetromino().getXPosition() - 1,
				player.getActiveTetromino().getYPosition());
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public static void moveTetrominoDown(Player player) {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition() + 1);
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public static void hardDropTetromino(Player player) {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition() + 1);
		
		while(true) {
			if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
				player.getActiveTetromino().setYPosition(movedTetromino.getYPosition());
				movedTetromino.setYPosition(movedTetromino.getYPosition() + 1);
			} else {
				break;
			}
		}
	}
	
	public static Tetromino generateProjectedDroppedTetromino(Player player) {
		Tetromino projectedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition());
				
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition());
		
		while(true) {
			if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
				projectedTetromino.setYPosition(movedTetromino.getYPosition());
				movedTetromino.setYPosition(movedTetromino.getYPosition() + 1);
			} else {
				break;
			}
		}
		
		return projectedTetromino;		
	}
	
}

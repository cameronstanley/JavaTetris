package com.cameronstanley.javatetris.client.model;

/**
 * Defines the logic and flow of a single player game of JavaTetris. Contains
 * all necessary data structures for representing the state of the game.
 * 
 * @author Cameron
 */
public class SinglePlayerGame {

	private Player player;
	
	/**
	 * The switch for representing the state of the game.
	 */
	private boolean isActive;
	
	/**
	 * Retains the amount of time since the state was last updated.
	 */
	private long timeElapsed;
	
	/**
	 * The standard width of a single player game board.
	 */
	public static final int BOARDWIDTH = 10;
	
	/**
	 * The standard height of a single player game board.
	 */
	public static final int BOARDHEIGHT = 20;
	
	/**
	 * The standard maximum size of the TetrominoQueue.
	 */
	public static final int TETROMINOQUEUESIZE = 5;
	
	/**
	 * The starting tetromino rotation for tetrominoes generated by the
	 * TetrominoQueue.
	 */
	public static final int TETROMINOSTARTROTATION = 0;
	
	/**
	 * The starting x coordinate of the tetromino.
	 */
	public static final int TETROMINOSTARTX = (BOARDWIDTH / 2) - 2;
	
	/**
	 * The starting y coordinate of the tetromino.
	 */
	public static final int TETROMINOSTARTY = -1;
	
	/**
	 * Creates and initializes a single player game.
	 */
	public SinglePlayerGame() {
		player = new Player();
		player.setBoard(new Board(BOARDHEIGHT, BOARDWIDTH));
		player.setTetrominoQueue(new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY));
		isActive = false;
	}
	
	/**
	 * Resets the state of the game.
	 */
	public void newGame() {
		player.getBoard().clear();
		player.getTetrominoQueue().clear();
		player.getTetrominoQueue().fill();
		player.setActiveTetromino(player.getTetrominoQueue().nextTetromino());
		player.setScore(0);
		player.setTotalLinesCleared(0);
		player.setLevel(1);
		
		isActive = true;
		timeElapsed = 0;
	}
	
	/**
	 * Advances the state of the game given the elapsed time in milliseconds.
	 * Responsible for the active tetromino gravity, placing tetorminoes on 
	 * the board, and deciding if the game is over.
	 * 
	 * @param delta The elapsed time in milliseconds.
	 */
	public void updateState(long delta) {
		// Reset the game if the active flag is false
		if(!isActive) {
			newGame();
		}		
	
		// The level speed determines how many milliseconds must elapse before
		// the game's state is updated. The time varies from 500 to 100 milliseconds,
		// getting smaller as the level increases. The speed changes by 20 
		// millisecond intervals until level 20, where it remains at 100 for
		// any further level increases.
		int levelSpeed;		
		if(player.getLevel() > 20) {
			levelSpeed = 100;
		} else {
			levelSpeed = 500 - ((player.getLevel() - 1) * 20); 
		}
		
		// Only update the game's state if the time elapsed is greater than the
		// level speed
		timeElapsed += delta;
		if(timeElapsed < levelSpeed) {
			return;
		} else {
			timeElapsed = 0;
		}
		
		// Try to move active tetromino down; place on board if can't drop further
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(), 
				player.getActiveTetromino().getRotation(), 
				player.getActiveTetromino().getXPosition(), 
				player.getActiveTetromino().getYPosition() + 1);
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			
			// Move the piece down
			player.setActiveTetromino(movedTetromino);
			
		} else {
			
			// Check that the game is not over; a piece being placed on the
			// top row means the game is over
			if(player.getActiveTetromino().getYPosition() <= 0) {
				
				// Game is over
				isActive = false;
								
			} else {
				
				// Place the tetromino on the board and set the active
				// tetromino to the next tetromino in the queue
				player.getBoard().placeTetromino(player.getActiveTetromino());
				player.setActiveTetromino(player.getTetrominoQueue().nextTetromino());
				
				// Clear any full rows on the board
				int linesCleared = player.getBoard().clearFullLines();
				player.setTotalLinesCleared(player.getTotalLinesCleared() + linesCleared);
				
				// Update player score depending on how many lines were cleared
				switch(linesCleared) {
				case 1:
					player.setScore(player.getScore() + (40 * player.getLevel()));
					break;
				case 2:
					player.setScore(player.getScore() + (100 * player.getLevel()));
					break;
				case 3:
					player.setScore(player.getScore() + (300 * player.getLevel()));
					break;
				case 4:
					player.setScore(player.getScore() + (1200 * player.getLevel()));
					break;
				default:
					break;
				}
				
				// Update level; a maximum level of 99 is enforced
				if (player.getLevel() > 99) {
					player.setLevel(99);
				} else {
					player.setLevel((player.getTotalLinesCleared() / 10) + 1);
				}
				
			}
			
		}		
		
	}
	
	public void rotateActiveTetromino() {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition());
		movedTetromino.rotateForward();
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public void moveActiveTetrominoRight() {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition() + 1,
				player.getActiveTetromino().getYPosition());
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public void moveActiveTetrominoLeft() {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(), 
				player.getActiveTetromino().getXPosition() - 1,
				player.getActiveTetromino().getYPosition());
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public void moveActiveTetrominoDown() {
		Tetromino movedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition() + 1);
		
		if(player.getBoard().isValidTetrominoPosition(movedTetromino)) {
			player.setActiveTetromino(movedTetromino);
		}
	}
	
	public void hardDropActiveTetromino() {
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
	
	public Tetromino generateProjectedTetromino() {
		Tetromino projectedTetromino = new Tetromino(player.getActiveTetromino().getType(),
				player.getActiveTetromino().getRotation(),
				player.getActiveTetromino().getXPosition(),
				player.getActiveTetromino().getYPosition());
		
		while(true) {
			Tetromino tempTetromino = new Tetromino(projectedTetromino.getType(),
					projectedTetromino.getRotation(),
					projectedTetromino.getXPosition(),
					projectedTetromino.getYPosition() + 1);
			
			if(player.getBoard().isValidTetrominoPosition(tempTetromino)) {
				projectedTetromino.setYPosition(tempTetromino.getYPosition());
			} else {
				break;
			}
		}
		
		return projectedTetromino;
	}

	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the switch for representing the state of the game
	 * @return The switch for representing the state of the game
	 */
	public boolean getIsActive() {
		return isActive;
	}
	
}

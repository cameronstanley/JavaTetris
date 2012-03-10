package com.cameronstanley.javatetris.model;

/**
 * Defines the logic and flow of a single player game of JavaTetris. Contains
 * all necessary data structures for representing the state of the game.
 * 
 * @author Cameron
 */
public class SinglePlayerGame {

	/**
	 * The board used for storing placed tetrominoes.
	 */
	private Board board;
	
	/**
	 * The active tetromino being controller by the player.
	 */
	private Tetromino activeTetromino;
	
	/**
	 * The queue of tetrominoes that will be played.
	 */
	private TetrominoQueue tetrominoQueue;
	
	private Player player;
	
	/**
	 * The switch for representing the state of the game.
	 */
	private boolean isActive;
	
	private int level;
	
	private int totalLinesCleared;
	
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
		board = new Board(BOARDHEIGHT, BOARDWIDTH);
		tetrominoQueue = new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY);
		player = new Player();
		level = 1;
		totalLinesCleared = 0;
		isActive = false;
	}
	
	/**
	 * Resets the state of the game.
	 */
	private void newGame() {
		board.clearBoard();
		tetrominoQueue.clear();
		tetrominoQueue.fill();
		activeTetromino = tetrominoQueue.nextTetromino();
		player.setScore(0);
		level = 1;
		totalLinesCleared = 0;
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
		if(level > 20) {
			levelSpeed = 100;
		} else {
			levelSpeed = 500 - ((level - 1) * 20); 
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
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(), 
				activeTetromino.getRotation(), 
				activeTetromino.getXPosition(), 
				activeTetromino.getYPosition() + 1);
		
		if(board.isValidTetrominoPosition(movedTetromino)) {
			
			// Move the piece down
			activeTetromino = movedTetromino;
			
		} else {
			
			// Check that the game is not over; a piece being placed on the
			// top row means the game is over
			if(activeTetromino.getYPosition() <= 0) {
				
				// Game is over
				isActive = false;
								
			} else {
				
				// Place the tetromino on the board and set the active
				// tetromino to the next tetromino in the queue
				board.placeTetromino(activeTetromino);
				activeTetromino = tetrominoQueue.nextTetromino();
				
				// Clear any full rows on the board
				int linesCleared = board.clearFullLines();
				totalLinesCleared += linesCleared;
				
				// Update player score depending on how many lines were cleared
				switch(linesCleared) {
				case 1:
					player.setScore(player.getScore() + (40 * level));
					break;
				case 2:
					player.setScore(player.getScore() + (100 * level));
					break;
				case 3:
					player.setScore(player.getScore() + (300 * level));
					break;
				case 4:
					player.setScore(player.getScore() + (1200 * level));
					break;
				default:
					break;
				}
				
				// Update level; a maximum level of 99 is enforced
				if (level > 99) {
					level = 99;
				} else {
					level = (totalLinesCleared / 10) + 1;;
				}
				
			}
			
		}		
		
	}
	
	public void rotateActiveTetromino() {
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(),
				activeTetromino.getXPosition(),
				activeTetromino.getYPosition());
		movedTetromino.rotateForward();
		
		if(board.isValidTetrominoPosition(movedTetromino)) {
			activeTetromino = movedTetromino;
		}
	}
	
	public void moveActiveTetrominoRight() {
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(),
				activeTetromino.getXPosition() + 1,
				activeTetromino.getYPosition());
		
		if(board.isValidTetrominoPosition(movedTetromino)) {
			activeTetromino = movedTetromino;
		}
	}
	
	public void moveActiveTetrominoLeft() {
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(), 
				activeTetromino.getXPosition() - 1,
				activeTetromino.getYPosition());
		
		if(board.isValidTetrominoPosition(movedTetromino)) {
			activeTetromino = movedTetromino;
		}
	}
	
	public void moveActiveTetrominoDown() {
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(),
				activeTetromino.getXPosition(),
				activeTetromino.getYPosition() + 1);
		
		if(board.isValidTetrominoPosition(movedTetromino)) {
			activeTetromino = movedTetromino;
		}
	}
	
	public void hardDropActiveTetromino() {
		Tetromino movedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(),
				activeTetromino.getXPosition(),
				activeTetromino.getYPosition() + 1);
		
		while(true) {
			if(board.isValidTetrominoPosition(movedTetromino)) {
				activeTetromino.setYPosition(movedTetromino.getYPosition());
				movedTetromino.setYPosition(movedTetromino.getYPosition() + 1);
			} else {
				break;
			}
		}
	}
	
	public Tetromino generateProjectedTetromino() {
		Tetromino projectedTetromino = new Tetromino(activeTetromino.getType(),
				activeTetromino.getRotation(),
				activeTetromino.getXPosition(),
				activeTetromino.getYPosition());
		
		while(true) {
			Tetromino tempTetromino = new Tetromino(projectedTetromino.getType(),
					projectedTetromino.getRotation(),
					projectedTetromino.getXPosition(),
					projectedTetromino.getYPosition() + 1);
			
			if(board.isValidTetrominoPosition(tempTetromino)) {
				projectedTetromino.setYPosition(tempTetromino.getYPosition());
			} else {
				break;
			}
		}
		
		return projectedTetromino;
	}
	
	/**
	 * Returns the board used for storing placed tetrominoes.
	 * 
	 * @return The board used for storing placed tetrominoes.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Returns the active tetromino being controlled by the player.
	 * @return The active tetromino being controlled by the player.
	 */
	public Tetromino getActiveTetromino() {
		return activeTetromino;
	}
	
	/**
	 * Returns the queue of tetrominoes that will be played.
	 * 
	 * @return The queue of tetrominoes that will be played.
	 */
	public TetrominoQueue getTetrominoQueue() {
		return tetrominoQueue;
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
	
	public int getLevel() {
		return level;
	}
	
	public int getTotalLinesCleared() {
		return totalLinesCleared;
	}
	
}

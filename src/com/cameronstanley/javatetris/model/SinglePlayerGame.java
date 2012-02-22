package com.cameronstanley.javatetris.model;

public class SinglePlayerGame {

	private Board board;
	private Tetromino activeTetromino;
	private TetrominoQueue tetrominoQueue;
	private boolean isActive;
	
	public static final int BOARDWIDTH = 10;
	public static final int BOARDHEIGHT = 20;
	public static final int TETROMINOQUEUESIZE = 5;
	public static final int TETROMINOSTARTROTATION = 0;
	public static final int TETROMINOSTARTX = BOARDWIDTH / 2;
	public static final int TETROMINOSTARTY = 0;
	
	public SinglePlayerGame() {
		board = new Board(BOARDWIDTH, BOARDHEIGHT);
		tetrominoQueue = new TetrominoQueue(TETROMINOQUEUESIZE, TETROMINOSTARTROTATION, TETROMINOSTARTX, TETROMINOSTARTY);
		isActive = false;
	}
	
	private void newGame() {
		board.clearBoard();
		tetrominoQueue.clear();
		tetrominoQueue.fill();
		activeTetromino = tetrominoQueue.nextTetromino();
		isActive = true;
	}
	
	public void updateState(long delta) {
		if(!isActive) {
			newGame();
		} 
		
		// TODO: Implement logic for advancing state only after the level's timing speed has elapsed
		
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
			if(activeTetromino.getYPosition() == 0) {
				
				// Game is over
				isActive = false;
				
			} else {
				
				// Place the tetromino on the board and set the active
				// tetromino to the next tetromino in the queue
				board.placeTetromino(activeTetromino);
				activeTetromino = tetrominoQueue.nextTetromino();
				
				// Clear any full rows on the board
				board.clearFullLines();						// TODO: Use return value to increment player score
				
			}
			
		}		
		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Tetromino getActiveTetromino() {
		return activeTetromino;
	}
	
	public TetrominoQueue getTetrominoQueue() {
		return tetrominoQueue;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
}

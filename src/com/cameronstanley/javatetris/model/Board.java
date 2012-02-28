package com.cameronstanley.javatetris.model;

/**
 * Represents a board for storing placed tetrominoes and empty squares.
 * 
 * @author Cameron
 */
public class Board {

	/**
	 * A two-dimensional array with each element representing the 
	 * contents of the square.
	 */
	private TetrominoType[][] board;
	
	/**
	 * The height or number of rows of the board.
	 */
	private int height;
	
	/**
	 * The width or number of columns of the board.
	 */
	private int width;
	
	/**
	 * Creates a board with the given height and width dimensions.
	 * 
	 * @param height The height of the board or number of rows.
	 * @param width The width of the board or number of columns.
	 */
	public Board(int height, int width) {
		board = new TetrominoType[height][width];
		this.height = height;
		this.width = width;
	}
	
	/**
	 * Sets every array element of the board to EMPTY.
	 */
	public void clearBoard() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				board[i][j] = TetrominoType.EMPTY;
			}
		}
	}
	
	/**
	 * Checks if the specifed tetromino is in a legal configuration for the
	 * current board. The tetromino must not exceed the board's boundaries and
	 * must not intersect any previously placed tetrominoes.
	 * 
	 * @param tetromino The tetromino to check.
	 * @return <code>true</code> if the tetromino legally fits on the board; 
	 * <code>false</code> otherwise.
	 */
	public boolean isValidTetrominoPosition(Tetromino tetromino) {
		for(int i = 0; i < Tetromino.GRIDSIDELENGTH; i++) {
			for(int j = 0; j < Tetromino.GRIDSIDELENGTH; j++) {
				
				// Don't perform any checks if position is empty
				if (tetromino.getTetrominoRepresentation()[i][j] == 0) {
					continue;
				}
				
				// Check tetromino does not exceed board boundaries
				if((tetromino.getXPosition() + j) < 0) {
					return false;
				} else if((tetromino.getXPosition() + j) > width - 1) {
					return false;
				} else if((tetromino.getYPosition() + i) < 0) {
					continue;	// Allowed to fill over the top of the board
				} else if((tetromino.getYPosition() + i) > height - 1) {
					return false;
				}
				
				// Check board position is not currently occupied
				if(board[i + tetromino.getYPosition()][j + tetromino.getXPosition()] != TetrominoType.EMPTY) {
					return false;
				}
				
			}
		}
		
		return true;
	}
	
	/**
	 * Copies the given tetromino's individual squares to the board.
	 * 
	 * @param tetromino The tetromino to place on the board.
	 */
	public void placeTetromino(Tetromino tetromino) {
		for(int i = 0; i < Tetromino.GRIDSIDELENGTH; i++) {
			for(int j = 0; j < Tetromino.GRIDSIDELENGTH; j++) {
				
				if(tetromino.getTetrominoRepresentation()[i][j] == 0) {
					continue;
				} else {
					board[i + tetromino.getYPosition()][j + tetromino.getXPosition()] = tetromino.getType();
				}
				
			}
		}
	}
	
	/**
	 * Checks the entire board for full rows (all 1's) and deletes them.
	 * Any deleted lines shift the rows above it down.
	 * 
	 * @see Board#isLineFull(int)
	 * @see Board#clearLine(int)
	 * @return The total number of lines cleared.
	 */
	public int clearFullLines() {
		int linesCleared = 0;
		
		for(int i = 0; i < height; i++) {
			if(isLineFull(i)) {
				linesCleared++;
				clearLine(i);
			}
		}
		
		return linesCleared;
	}
	
	/**
	 * Checks if all of the elements of the board at the given row are
	 * filled with tetromino pieces.
	 * 
	 * @param row The row number to check.
	 * @return <code>true</code> if the row only contains 1's; 
	 * 		   <code>false</code> otherwise.
	 */
	public boolean isLineFull(int row) {		
		for(int i = 0; i < width; i++) {
			if(board[row][i] == TetrominoType.EMPTY) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Shifts all rows above the current row down one position.
	 * 
	 * @param row The row at which the shifting will begin.
	 */
	public void clearLine(int row) {
		for(int i = row; i > 0; i--) {
			for(int j = 0; j < width; j++) {
				board[i][j] = board[i - 1][j];
			}
		}
	}
	
	/**
	 * Returns the two-dimensional array representing the state of the
	 * board.
	 * 
	 * @return A two-dimensional array of TetrominoTypes which represent
	 * the type of the tetrominoes and empty positions.
	 */
	public TetrominoType[][] getBoard() {
		return board;
	}
	
	/**
	 * Returns the width or number of columns of the board.
	 * 
	 * @return The width or number of columns of the board.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height or number of rows of the board.
	 * 
	 * @return The height or number of rows of the board.
	 */
	public int getHeight() {
		return height;
	}
	
}

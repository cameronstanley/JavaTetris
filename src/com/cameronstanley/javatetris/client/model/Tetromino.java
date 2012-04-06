package com.cameronstanley.javatetris.client.model;

/**
 * Represents a tetromino, which are the four joined blocks used in the game
 * of Tetris to fill the board. 
 * 
 * @author Cameron
 */
public class Tetromino {
	
	/**
	 * The TetrominoType which describes the arrangement/shape of the tetromino.
	 */
	private TetrominoType type;
	
	/**
	 * The orientation of the tetromino. Logically correct values are 0, 1, 2, 3.
	 */
	private int rotation;
	
	/**
	 * The x coordinate of the tetromino grid representation's top left corner.
	 */
	private int xPosition;
	
	/**
	 * The y coordinate of the tetromino grid representation's top left corner.
	 */
	private int yPosition;
	
	/**
	 * Describes the length of the side of the grid representation of a tetromino.
	 */
	public static final int GRIDSIDELENGTH = 4;
	
	public Tetromino() {
		
	}
	
	/**
	 * Creates a tetromino using the given type, rotation, x position, and y position.
	 * 
	 * @param type The type that comprises the shape of the tetromino.
	 * @param rotation The rotation of the tetromino. Values range from 0-4.
	 * @param xPosition The x coordinate of the top left corner of the tetromino's grid representation.
	 * @param yPosition The y coordinate of the top left corner of the tetromino's grid representation.
	 */
	public Tetromino(TetrominoType type, int rotation, int xPosition, int yPosition) {
		this.type = type;
		this.rotation = rotation;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	/**
	 * Shifts the tetromino's rotation forwards (...0, 1, 2, 3, 0...).
	 */
	public void rotateForward() {
		rotation = (rotation == 3) ? 0 : rotation + 1 ;
	}
	
	/**
	 * Shifts the tetromino's rotation backwards (...0, 3, 2, 1, 0...).
	 */
	public void rotateBackward() {
		rotation = (rotation == 0) ? 3 : rotation - 1;
	}
	
	/**
	 * Returns a 4x4 array of zeroes and ones representing the shape of the 
	 * tetromino using its type and rotation.
	 * 
	 * @return A 4x4 two-dimensional array of zeroes and ones representing the
	 * shape of the tetromino.
	 */
	public int[][] getTetrominoRepresentation() {
		switch(type) {
		
		case I:
			switch(rotation) {
			case 0:
			case 2:
				return new int[][] {{0, 0, 0, 0},
								    {1, 1, 1, 1},
								    {0, 0, 0, 0},
								    {0, 0, 0, 0}};
			case 1:
			case 3:
				return new int[][] {{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 1, 0, 0}};
			}
			break;
			
		case J:
			switch(rotation) {
			case 0:
				return new int[][] {{0, 0, 0, 0},
								    {1, 1, 1, 0},
								    {0, 0, 1, 0},
								    {0, 0, 0, 0}};
			case 1:
				return new int[][] {{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{1, 1, 0, 0},
					    			{0, 0, 0, 0}};
			case 2:
				return new int[][] {{1, 0, 0, 0},
					    			{1, 1, 1, 0},
					    			{0, 0, 0, 0},
					    			{0, 0, 0, 0}};
			case 3:
				return new int[][] {{0, 1, 1, 0},
					    			{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 0, 0, 0}};
			}
			break;
			
		case L:
			switch(rotation) {
			case 0:
				return new int[][] {{0, 0, 0, 0},
								    {1, 1, 1, 0},
								    {1, 0, 0, 0},
								    {0, 0, 0, 0}};
			case 1:
				return new int[][] {{1, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 0, 0, 0}};
			case 2:
				return new int[][] {{0, 0, 1, 0},
					    			{1, 1, 1, 0},
					    			{0, 0, 0, 0},
					    			{0, 0, 0, 0}};
			case 3:
				return new int[][] {{0, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 1, 1, 0},
					    			{0, 0, 0, 0}};
			}
			break;
			
		case O:
			switch(rotation) {
			case 0:
			case 1:
			case 2:
			case 3:
				return new int[][] {{0, 0, 0, 0},
								    {0, 1, 1, 0},
								    {0, 1, 1, 0},
								    {0, 0, 0, 0}};
			}
			break;
			
		case S:
			switch(rotation) {
			case 0:
			case 2:
				return new int[][] {{0, 0, 0, 0},
								    {0, 1, 1, 0},
								    {1, 1, 0, 0},
								    {0, 0, 0, 0}};
			case 1:
			case 3:
				return new int[][] {{1, 0, 0, 0},
					    			{1, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 0, 0, 0}};
			}
			break;
			
		case T:
			switch(rotation) {
			case 0:
				return new int[][] {{0, 0, 0, 0},
								    {1, 1, 1, 0},
								    {0, 1, 0, 0},
								    {0, 0, 0, 0}};
			case 1:
				return new int[][] {{0, 1, 0, 0},
					    			{0, 1, 1, 0},
					    			{0, 1, 0, 0},
					    			{0, 0, 0, 0}};
			case 2:
				return new int[][] {{0, 1, 0, 0},
					    			{1, 1, 1, 0},
					    			{0, 0, 0, 0},
					    			{0, 0, 0, 0}};
			case 3:
				return new int[][] {{0, 1, 0, 0},
					    			{1, 1, 0, 0},
					    			{0, 1, 0, 0},
					    			{0, 0, 0, 0}};
			}
			break;
			
		case Z:
			switch(rotation) {
			case 0:
			case 2:
				return new int[][] {{0, 0, 0, 0},
								    {1, 1, 0, 0},
								    {0, 1, 1, 0},
								    {0, 0, 0, 0}};
			case 1:
			case 3:
				return new int[][] {{0, 1, 0, 0},
					    			{1, 1, 0, 0},
					    			{1, 0, 0, 0},
					    			{0, 0, 0, 0}};
			}
			break;
			
		}
		
		return null;
	}
	
	/**
	 * Returns the type of the tetromino.
	 * 
	 * @see TetrominoType
	 * @return The type of the tetromino.
	 */
	public TetrominoType getType() {
		return type;
	}
	
	/**
	 * Modifies the type of the tetromino.
	 * 
	 * @param type The type of the tetromino to set.
	 */
	public void setType(TetrominoType type) {
		this.type = type;
	}
	
	/**
	 * Returns the rotation of the tetromino.
	 * 
	 * @return The rotation of the tetromino.
	 */
	public int getRotation() {
		return rotation;
	}
	
	/**
	 * Modifies the rotation of the tetromino.
	 * 
	 * @param rotation The rotation of the tetromino to set.
	 */
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Returns the x coordinate of the top left corner of the tetromino's grid
	 * representation.
	 * 
	 * @return The tetromino's x position.
	 */
	public int getXPosition() {
		return xPosition;
	}
	
	/**
	 * Modifies the x coordinate of the top left corner of the tetromino's grid
	 * representation.
	 * 
	 * @param xPosition The x position of the tetromino to set.
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	/**
	 * Returns the y coordinate of the top right corner of the tetromino's grid
	 * representation.
	 * 
	 * @return The tetromino's y position.
	 */
	public int getYPosition() {
		return yPosition;
	}
	
	/**
	 * Modifies the y coordinate of the top left corner of the tetromino's grid
	 * representation.
	 * 
	 * @param yPosition The y position of the tetromino to set.
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
}

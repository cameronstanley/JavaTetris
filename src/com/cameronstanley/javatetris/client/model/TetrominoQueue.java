package com.cameronstanley.javatetris.client.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a queue of tetrominoes that will be played. An ArrayList is used
 * as the underlying data structure for containing the tetrominoes.
 * 
 * @author Cameron
 */
public class TetrominoQueue {

	/**
	 * An ArrayList that contains the collection of tetrominoes.
	 */
	private ArrayList<Tetromino> tetrominoes;
	
	/**
	 * The maximum number of tetrominoes the queue holds.
	 */
	private int size;
	
	/**
	 * The starting rotation used when generating tetrominoes.
	 */
	private int startRotation;
	
	/**
	 * The starting x position used when generating tetrominoes.
	 */
	private int startXPosition;
	
	/**
	 * The starting y position used when generating tetrominoes.
	 */
	private int startYPosition;
	
	/**
	 * A random number generator for selecting a TetrominoType when generating
	 * tetrominoes.
	 */
	private Random randomGenerator;
	
	/**
	 * Creates a TetrominoQueue for generating and maintaining tetrominoes in
	 * an ArrayList.
	 * 
	 * @param size The number of tetrominoes to keep in the queue.
	 * @param startRotation The starting rotation of generated tetrominoes.
	 * @param startXPosition The starting x coordinate of generated tetrominoes.
	 * @param startYPosition The starting y coordinate of generated tetrominoes.
	 */
	public TetrominoQueue(int size, int startRotation, int startXPosition, int startYPosition) {
		this.size = size;
		this.startRotation = startRotation;
		this.startXPosition = startXPosition;
		this.startYPosition = startYPosition;
		tetrominoes = new ArrayList<Tetromino>();
		randomGenerator = new Random();
	}
	
	/**
	 * Pops the next tetromino off the front of the queue and fills it back up.
	 * 
	 * @return The next tetromino to be used.
	 */
	public Tetromino nextTetromino() {
		Tetromino nextTetromino = tetrominoes.get(0);
		tetrominoes.remove(0);
		fill();
		return nextTetromino;
	}
	
	/**
	 * Fills the queue with tetrominoes until it reaches the set size.
	 */
	public void fill() {
		while(tetrominoes.size() < size) {
			tetrominoes.add(createTetromino());
		}
	}
	
	/**
	 * Removes all tetrominoes from the queue.
	 */
	public void clear() {
		tetrominoes.clear();
	}

	/**
	 * Generates a new tetromino with a random type and the TetrominoQueue's
	 * starting parameters for rotation and positions. 
	 * 
	 * @return A generated Tetromino.
	 */
	private Tetromino createTetromino() {
		TetrominoType[] validTetrominoTypes = {TetrominoType.I, TetrominoType.J, TetrominoType.L, TetrominoType.O, TetrominoType.S, TetrominoType.T, TetrominoType.Z};
		return new Tetromino(validTetrominoTypes[randomGenerator.nextInt(validTetrominoTypes.length)], startRotation, startXPosition, startYPosition);
	}
	
	/**
	 * Returns the tetrominoes in the queue.
	 * 
	 * @return The tetrominoes in the queue.
	 */
	public ArrayList<Tetromino> getTetrominoes() {
		return tetrominoes;
	}
	
	/**
	 * Returns the amount of tetrominoes the TetrominoQueue contains.
	 * 
	 * @return The amount of tetrominoes the TetrominoQueue contains.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Returns the starting x coordinate of generated tetrominoes.
	 * 
	 * @return The starting x coordinate of generated tetrominoes.
	 */
	public int getStartXPosition() {
		return startXPosition;
	}
	
	/**
	 * Returns the starting y coordinate of generated tetrominoes.
	 * 
	 * @return The starting y coordinate of generated tetrominoes.
	 */
	public int getStartYPosition() {
		return startYPosition;
	}
	
	/**
	 * Returns the starting rotation of generated tetrominoes.
	 * 
	 * @return The starting rotation of generated tetrominoes.
	 */
	public int getStartRotation() {
		return startRotation;
	}
	
}

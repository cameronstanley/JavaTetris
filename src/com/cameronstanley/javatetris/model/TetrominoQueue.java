package com.cameronstanley.javatetris.model;

import java.util.ArrayList;
import java.util.Random;

public class TetrominoQueue {

	private ArrayList<Tetromino> tetrominoes;
	private int size;
	private int startRotation;
	private int startXPosition;
	private int startYPosition;
	private Random randomGenerator;
	
	public TetrominoQueue(int size, int startRotation, int startXPosition, int startYPosition) {
		this.size = size;
		this.startRotation = startRotation;
		this.startXPosition = startXPosition;
		this.startYPosition = startYPosition;
		tetrominoes = new ArrayList<Tetromino>();
		randomGenerator = new Random();
	}
	
	public Tetromino nextTetromino() {
		Tetromino nextTetromino = tetrominoes.get(0);
		tetrominoes.remove(0);
		fill();
		return nextTetromino;
	}
	
	public void fill() {
		while(tetrominoes.size() < size) {
			tetrominoes.add(createTetromino());
		}
	}
	
	public void clear() {
		tetrominoes.clear();
	}

	private Tetromino createTetromino() {
		return new Tetromino(TetrominoType.values()[randomGenerator.nextInt(TetrominoType.values().length)], startRotation, startXPosition, startYPosition);
	}
	
	public ArrayList<Tetromino> getTetrominoes() {
		return tetrominoes;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getStartXPosition() {
		return startXPosition;
	}
	
	public int getStartYPosition() {
		return startYPosition;
	}
	
	public int getStartRotation() {
		return startRotation;
	}
	
}

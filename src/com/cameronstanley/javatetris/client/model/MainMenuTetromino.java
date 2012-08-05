package com.cameronstanley.javatetris.client.model;

public class MainMenuTetromino extends Tetromino {

	private boolean isActive;
	private int xCoordinate;
	private int yCoordinate;
	private float fallSpeed;
	
	public MainMenuTetromino() {
		isActive = false;
		xCoordinate = 0;
		yCoordinate = 0;
		fallSpeed = 1.0f;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public float getFallSpeed() {
		return fallSpeed;
	}

	public void setFallSpeed(float fallSpeed) {
		this.fallSpeed = fallSpeed;
	}
	
}

package com.cameronstanley.javatetris.client.model;

import java.util.ArrayList;
import java.util.Random;

public class MainMenu {

	private ArrayList<MainMenuTetromino> mainMenuTetrominoes;
	private Random randomGenerator;
	
	private final static int MAXTETROMINOES = 20;
	
	public MainMenu() {
		mainMenuTetrominoes = new ArrayList<MainMenuTetromino>();
		for (int i = 0; i < MAXTETROMINOES; i++) {
			mainMenuTetrominoes.add(new MainMenuTetromino());
		}
		
		randomGenerator = new Random();
	}
	
	public void updateState(long delta) {
		for (MainMenuTetromino mainMenuTetromino : mainMenuTetrominoes) {
			if (mainMenuTetromino.isActive()) {
				if (mainMenuTetromino.getyCoordinate() > 600) {
					mainMenuTetromino.setActive(false);
				} else {	
					mainMenuTetromino.setyCoordinate(mainMenuTetromino.getyCoordinate() + (int)(mainMenuTetromino.getFallSpeed() * delta));
				}
			} else {
				mainMenuTetromino.setActive(true);
				mainMenuTetromino.setFallSpeed(randomGenerator.nextFloat() + 0.1f);
				mainMenuTetromino.setRotation(randomGenerator.nextInt(4));
				mainMenuTetromino.setType(TetrominoType.values()[randomGenerator.nextInt(TetrominoType.values().length - 1)]);
				mainMenuTetromino.setxCoordinate(randomGenerator.nextInt(801));
				mainMenuTetromino.setyCoordinate(randomGenerator.nextInt(10) - 15);
			}
		}
	}

	public ArrayList<MainMenuTetromino> getMainMenuTetrominoes() {
		return mainMenuTetrominoes;
	}

	public void setMainMenuTetrominoes(
			ArrayList<MainMenuTetromino> mainMenuTetrominoes) {
		this.mainMenuTetrominoes = mainMenuTetrominoes;
	}
	
}

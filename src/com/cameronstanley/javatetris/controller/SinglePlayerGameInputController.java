package com.cameronstanley.javatetris.controller;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.model.SinglePlayerGame;

public class SinglePlayerGameInputController {
	
	private SinglePlayerGame singlePlayerGame;
	
	public SinglePlayerGameInputController(SinglePlayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
	}

	public void pollInput() {
		if(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					singlePlayerGame.rotateActiveTetromino();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					singlePlayerGame.moveActiveTetrominoRight();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					singlePlayerGame.moveActiveTetrominoDown();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					singlePlayerGame.moveActiveTetrominoLeft();
				}
			}
		}
	}
	
}

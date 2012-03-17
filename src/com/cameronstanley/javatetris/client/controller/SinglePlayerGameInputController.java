package com.cameronstanley.javatetris.client.controller;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.client.model.SinglePlayerGame;

public class SinglePlayerGameInputController {
	
	private SinglePlayerGame singlePlayerGame;
	
	public SinglePlayerGameInputController(SinglePlayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
	}

	public void pollInput() {
		if(Keyboard.next()) {
			Keyboard.enableRepeatEvents(true);
			if(Keyboard.getEventKeyState()) {				
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					singlePlayerGame.rotateActiveTetromino();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					singlePlayerGame.moveActiveTetrominoRight();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					if(Keyboard.isRepeatEvent()) {
						Keyboard.enableRepeatEvents(false);		// Prevent hard dropping twice
						singlePlayerGame.hardDropActiveTetromino();
					} else {
						singlePlayerGame.moveActiveTetrominoDown();
					}
				} else if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					singlePlayerGame.moveActiveTetrominoLeft();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					JavaTetrisController.setState(JavaTetrisControllerState.MAINMENU);
				}
			}
		}
	}
	
}

package com.cameronstanley.javatetris.client.controller.input;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;
import com.cameronstanley.javatetris.client.controller.JavaTetrisControllerState;
import com.cameronstanley.javatetris.client.model.GameLogic;
import com.cameronstanley.javatetris.client.model.SinglePlayerGame;

public class SinglePlayerGameInputController implements InputController {
	
	private SinglePlayerGame singlePlayerGame;
	
	public SinglePlayerGameInputController(SinglePlayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
	}

	public void pollInput() {	
		JavaTetrisController javaTetrisController = JavaTetrisController.getInstance();
		
		if(Keyboard.next()) {
			Keyboard.enableRepeatEvents(true);
			if(Keyboard.getEventKeyState()) {				
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					GameLogic.rotateTetromino(singlePlayerGame.getPlayer());
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					GameLogic.moveTetrominoRight(singlePlayerGame.getPlayer());
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					if(Keyboard.isRepeatEvent()) {
						Keyboard.enableRepeatEvents(false);		// Prevent hard dropping twice
						GameLogic.hardDropTetromino(singlePlayerGame.getPlayer());
					} else {
						GameLogic.moveTetrominoDown(singlePlayerGame.getPlayer());
					}
				} else if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					GameLogic.moveTetrominoLeft(singlePlayerGame.getPlayer());
				} else if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					javaTetrisController.setState(JavaTetrisControllerState.MAINMENU);
				}
			}
		}
	}
	
}

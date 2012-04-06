package com.cameronstanley.javatetris.client.controller.input;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;
import com.cameronstanley.javatetris.client.model.GameLogic;
import com.cameronstanley.javatetris.client.model.OnlineMultiplayerGame;

public class OnlineMultiplayerGameInputController implements InputController {

	private OnlineMultiplayerGame onlineMultiplayerGame;
	
	public OnlineMultiplayerGameInputController(OnlineMultiplayerGame onlineMultiplayerGame) {
		this.onlineMultiplayerGame = onlineMultiplayerGame;
	}
	
	public void pollInput() {		
		if(Keyboard.next()) {
			Keyboard.enableRepeatEvents(true);
			if(Keyboard.getEventKeyState()) {	
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					GameLogic.rotateTetromino(onlineMultiplayerGame.getPlayer());
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					GameLogic.moveTetrominoRight(onlineMultiplayerGame.getPlayer());
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					if(Keyboard.isRepeatEvent()) {
						Keyboard.enableRepeatEvents(false);		// Prevent hard dropping twice
						GameLogic.hardDropTetromino(onlineMultiplayerGame.getPlayer());
					} else {
						GameLogic.moveTetrominoDown(onlineMultiplayerGame.getPlayer());
					}
				} else if(Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					GameLogic.moveTetrominoLeft(onlineMultiplayerGame.getPlayer());
				}
				
				JavaTetrisController.networkClientController.updateActiveTetromino();
			}
		}
	}
	
}

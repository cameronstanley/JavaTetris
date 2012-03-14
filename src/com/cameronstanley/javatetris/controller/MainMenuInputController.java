package com.cameronstanley.javatetris.controller;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.model.Menu;

public class MainMenuInputController {

	private Menu mainMenu;
	
	public MainMenuInputController(Menu mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	public void pollInput() {
		if(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {				
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					mainMenu.selectPreviousMenuItem();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					mainMenu.selectNextMenuItem();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					switch(mainMenu.getSelectedMenuItemIndex()) {
					case 0:
						JavaTetrisController.setState(JavaTetrisControllerState.STARTSINGLEPLAYERGAME);
						break;
					case 1:
						JavaTetrisController.setState(JavaTetrisControllerState.PLAYSINGLEPLAYERGAME);
						break;
					case 2:
						System.exit(0);
						break;
					}
				}
			}
		}
	}
	
}
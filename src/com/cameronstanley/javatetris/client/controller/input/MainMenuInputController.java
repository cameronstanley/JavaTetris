package com.cameronstanley.javatetris.client.controller.input;

import org.lwjgl.input.Keyboard;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;
import com.cameronstanley.javatetris.client.controller.JavaTetrisControllerState;
import com.cameronstanley.javatetris.client.model.Menu;

public class MainMenuInputController implements InputController {

	private Menu mainMenu;
	
	public MainMenuInputController(Menu mainMenu) {
		this.mainMenu = mainMenu;
	}
	
	public void pollInput() {
		JavaTetrisController javaTetrisController = JavaTetrisController.getInstance();
		
		if(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {				
				if(Keyboard.getEventKey() == Keyboard.KEY_UP) {
					mainMenu.selectPreviousMenuItem();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					mainMenu.selectNextMenuItem();
				} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					switch(mainMenu.getSelectedMenuItemIndex()) {
					case 0:
						javaTetrisController.setState(JavaTetrisControllerState.STARTSINGLEPLAYERGAME);
						break;
					case 1:
						javaTetrisController.setState(JavaTetrisControllerState.PLAYONLINEMULTIPLAYER);
						JavaTetrisController.networkClientController.connect();
						break;
					case 2:
						javaTetrisController.setState(JavaTetrisControllerState.PLAYSINGLEPLAYERGAME);
						break;
					case 3:
						System.exit(0);
						break;
					}
				}
			}
		}
	}
	
}
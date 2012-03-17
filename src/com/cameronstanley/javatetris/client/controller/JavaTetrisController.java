package com.cameronstanley.javatetris.client.controller;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.cameronstanley.javatetris.client.model.Menu;
import com.cameronstanley.javatetris.client.model.MenuItem;
import com.cameronstanley.javatetris.client.model.SinglePlayerGame;
import com.cameronstanley.javatetris.client.view.MainMenuView;
import com.cameronstanley.javatetris.client.view.SinglePlayerGameView;

public class JavaTetrisController {
	
	private static JavaTetrisControllerState state;
	private final String WINDOWTITLE = "JavaTetris";
	private final int WINDOWWIDTH = 800;
	private final int WINDOWHEIGHT = 600;
	private final int TARGETFPS = 60;
		
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(WINDOWWIDTH, WINDOWHEIGHT));
			Display.setTitle(WINDOWTITLE);
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		// Set up OpenGL with 2D projection matrix
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WINDOWWIDTH, WINDOWHEIGHT, 0, 0, 1);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		// Enables text rendering
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// Application will start in the main menu
		state = JavaTetrisControllerState.MAINMENU;
		
		// Create a main menu
		Menu mainMenu = new Menu();
		mainMenu.addMenuItem(new MenuItem("Single Player", true));
		mainMenu.addMenuItem(new MenuItem("Online Multiplayer", true));
		mainMenu.addMenuItem(new MenuItem("Continue", false));
		mainMenu.addMenuItem(new MenuItem("Exit", true));
		MainMenuInputController mainMenuInputController = new MainMenuInputController(mainMenu);
		MainMenuView mainMenuView = new MainMenuView(mainMenu);
		
		// Create a single player game
		SinglePlayerGame singlePlayerGame = new SinglePlayerGame();
		SinglePlayerGameInputController singlePlayerGameInputController = new SinglePlayerGameInputController(singlePlayerGame);
		SinglePlayerGameView singlePlayerGameView = new SinglePlayerGameView(singlePlayerGame);
		
		// Create and initialize a timer
		Timer timer = new Timer();
		timer.init();
		
		// Main loop
		while (!Display.isCloseRequested()) {
			switch(state) {
			case MAINMENU:
				mainMenuInputController.pollInput();
				mainMenuView.render();
				timer.init();
				break;
			case STARTSINGLEPLAYERGAME:
				singlePlayerGame.newGame();
				state = JavaTetrisControllerState.PLAYSINGLEPLAYERGAME;
				mainMenu.getMenuItems().get(2).setEnabled(true);
				timer.init();
			case PLAYSINGLEPLAYERGAME:
				singlePlayerGame.updateState(timer.getDelta());
				singlePlayerGameInputController.pollInput();
				singlePlayerGameView.render();
				break;
			case PLAYONLINEMULTIPLAYER:
				break;
			}
			
			Display.update();
			Display.sync(TARGETFPS);
		}
		
		Display.destroy();
	}
		
	public static JavaTetrisControllerState getState() {
		return state;
	}
	
	public static void setState(JavaTetrisControllerState state) {
		JavaTetrisController.state = state;
	}
}

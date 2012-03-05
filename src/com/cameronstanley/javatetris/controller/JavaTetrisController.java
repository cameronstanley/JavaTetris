package com.cameronstanley.javatetris.controller;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.cameronstanley.javatetris.model.SinglePlayerGame;
import com.cameronstanley.javatetris.view.SinglePlayerGameView;

public class JavaTetrisController {
	
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
		
		// Initialize models, input controllers, and views
		SinglePlayerGame singlePlayerGame = new SinglePlayerGame();
		SinglePlayerGameInputController singlePlayerGameInputController = new SinglePlayerGameInputController(singlePlayerGame);
		SinglePlayerGameView singlePlayerGameView = new SinglePlayerGameView(singlePlayerGame);
		Timer timer = new Timer();
		
		timer.init();
		
		while (!Display.isCloseRequested()) {
			singlePlayerGame.updateState(timer.getDelta());
			singlePlayerGameInputController.pollInput();
			singlePlayerGameView.render();
			
			Display.update();
			Display.sync(TARGETFPS);
		}
		
		Display.destroy();
	}
	
}

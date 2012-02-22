package com.cameronstanley.javatetris.controller;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.cameronstanley.javatetris.model.SinglePlayerGame;

public class JavaTetrisController {
	
	private final String WINDOWTITLE = "JavaTetris";
	private final int WINDOWWIDTH = 800;
	private final int WINDOWHEIGHT = 600;
	private final int TARGETFPS = 60;
		
	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(WINDOWWIDTH, WINDOWHEIGHT));
			Display.setTitle(WINDOWTITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		SinglePlayerGame singlePlayerGame = new SinglePlayerGame();
		Timer timer = new Timer();
		
		timer.init();
		
		while (!Display.isCloseRequested()) {
			singlePlayerGame.updateState(timer.getDelta());
			
			Display.update();
			Display.sync(TARGETFPS);
		}
		
		Display.destroy();
	}
	
}

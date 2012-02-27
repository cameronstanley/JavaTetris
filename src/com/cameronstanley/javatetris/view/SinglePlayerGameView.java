package com.cameronstanley.javatetris.view;

import org.lwjgl.opengl.GL11;

import com.cameronstanley.javatetris.model.SinglePlayerGame;
import com.cameronstanley.javatetris.model.TetrominoType;

public class SinglePlayerGameView {
	
	/**
	 * The single player game model to render.
	 */
	private SinglePlayerGame singlePlayerGame;
	
	public SinglePlayerGameView(SinglePlayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
	}
	
	public void render() {	
		clear();
		renderBoardOutline();
		renderBoard();
		renderActiveTetromino();
	}
	
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void renderBoardOutline() {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2i(240, 60);
		GL11.glVertex2i(240, 540);
		GL11.glVertex2i(560, 540);
		GL11.glVertex2i(560, 60);
		GL11.glVertex2i(240, 60);
		GL11.glEnd();
	}

	public void renderBoard() {
		for(int i = 0; i < singlePlayerGame.getBoard().getBoard().length; i++) {
			for(int j = 0; j < singlePlayerGame.getBoard().getBoard()[i].length; j++) {
				if(singlePlayerGame.getBoard().getBoard()[i][j] != TetrominoType.EMPTY) {			
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2i(240 + (j * 32), 60 + (i * 24));
					GL11.glVertex2i(240 + (j * 32) + 32, 60 + (i * 24));
					GL11.glVertex2i(240 + (j * 32) + 32, 60 + (i * 24) + 24);
					GL11.glVertex2i(240 + (j * 32), 60 + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
	public void renderActiveTetromino() {
		int[][] activeTetrominoRepresentation = singlePlayerGame.getActiveTetromino().getTetrominoRepresentation();
		int offsetX = singlePlayerGame.getActiveTetromino().getXPosition();
		int offsetY = singlePlayerGame.getActiveTetromino().getYPosition();
		
		for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
			for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
				if(activeTetrominoRepresentation[i][j] == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
}

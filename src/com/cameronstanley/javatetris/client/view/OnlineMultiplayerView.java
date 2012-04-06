package com.cameronstanley.javatetris.client.view;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.cameronstanley.javatetris.client.model.OnlineMultiplayerGame;
import com.cameronstanley.javatetris.client.model.TetrominoType;

@SuppressWarnings("deprecation")
public class OnlineMultiplayerView implements View {

	private OnlineMultiplayerGame onlineMultiplayerGame;
	
	private TrueTypeFont waitingFont;
	
	public OnlineMultiplayerView(OnlineMultiplayerGame onlineMultiplayerGame) {
		this.onlineMultiplayerGame = onlineMultiplayerGame;
		
		Font waitingAwtFont = new Font("Lucida Grande", Font.BOLD, 36);
		waitingFont = new TrueTypeFont(waitingAwtFont, true);
	}
	
	public void render() {
		clear();
		if (onlineMultiplayerGame.isWaiting()) {
			renderWaiting();
		} else {
			renderPlayerBoardOutline();
			renderPlayerBoardGrid();
			renderPlayerBoard();
			renderPlayerActiveTetromino();
			
			renderOpponentBoardOutline();
			renderOpponentBoardGrid();
			renderOpponentBoard();
			renderOpponentActiveTetromino();
		}
	}
	
	private void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 0.0f);		
		GL11.glLineWidth(1.0f);
	}
	
	private void renderWaiting() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		waitingFont.drawString(300, 200, "Waiting...");
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	private void renderPlayerBoardOutline() {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex2i(53, 60);
		GL11.glVertex2i(53, 542);
		GL11.glVertex2i(373, 542);
		GL11.glVertex2i(373, 60);
		GL11.glEnd();
	}
	
	private void renderPlayerBoardGrid() {
		for (int i = 1; i < onlineMultiplayerGame.getPlayer().getBoard().getHeight(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(53, 60 + (i * 24));
			GL11.glVertex2i(373, 60 + (i * 24));
			GL11.glEnd();
		}
		
		for (int i = 1; i < onlineMultiplayerGame.getPlayer().getBoard().getWidth(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(53 + (i * 32), 60);
			GL11.glVertex2i(53 + (i * 32), 542);
			GL11.glEnd();
		}
	}
	
	private void renderPlayerBoard() {
		for(int i = 0; i < onlineMultiplayerGame.getPlayer().getBoard().getTiles().length; i++) {
			for(int j = 0; j < onlineMultiplayerGame.getPlayer().getBoard().getTiles()[i].length; j++) {
				if(onlineMultiplayerGame.getPlayer().getBoard().getTiles()[i][j] != TetrominoType.EMPTY) {			;
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(onlineMultiplayerGame.getPlayer().getBoard().getTiles()[i][j]);
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(53 + (j * 32), 60 + (i * 24));
					GL11.glVertex2i(53 + (j * 32) + 32, 60 + (i * 24));
					GL11.glVertex2i(53 + (j * 32) + 32, 60 + (i * 24) + 24);
					GL11.glVertex2i(53 + (j * 32), 60 + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(53 + (j * 32) + 1, 60 + (i * 24));
					GL11.glVertex2i(53 + (j * 32) + 1, 60 + (i * 24) + 24);
					// Top
					GL11.glVertex2i(53 + (j * 32), 60 + (i * 24) + 1);
					GL11.glVertex2i(53 + (j * 32) + 32, 60 + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(53 + (j * 32) + 1, 60 + (i * 24) + 24 - 1);
					GL11.glVertex2i(53 + (j * 32) + 32 - 1, 60 + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(53 + (j * 32) + 32 - 1, 60 + (i * 24) + 1);
					GL11.glVertex2i(53 + (j * 32) + 32 - 1, 60 + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}		
	}
	
	private void renderPlayerActiveTetromino() {
		int[][] activeTetrominoRepresentation = onlineMultiplayerGame.getPlayer().getActiveTetromino().getTetrominoRepresentation();
		int offsetX = onlineMultiplayerGame.getPlayer().getActiveTetromino().getXPosition();
		int offsetY = onlineMultiplayerGame.getPlayer().getActiveTetromino().getYPosition();
		
		for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
			for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
				// Don't render squares that hang out over the top of the board
				if(offsetY + i < 0) {
					continue;
				}
				
				if(activeTetrominoRepresentation[i][j] == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(onlineMultiplayerGame.getPlayer().getActiveTetromino().getType());
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24);
					// Top
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(53 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}		
	}
	
	private void renderOpponentBoardOutline() {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex2i(423, 60);
		GL11.glVertex2i(423, 542);
		GL11.glVertex2i(743, 542);
		GL11.glVertex2i(743, 60);
		GL11.glEnd();
	}
	
	private void renderOpponentBoardGrid() {
		for (int i = 1; i < onlineMultiplayerGame.getOpponent().getBoard().getHeight(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(423, 60 + (i * 24));
			GL11.glVertex2i(743, 60 + (i * 24));
			GL11.glEnd();
		}
		
		for (int i = 1; i < onlineMultiplayerGame.getPlayer().getBoard().getWidth(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(423 + (i * 32), 60);
			GL11.glVertex2i(423 + (i * 32), 542);
			GL11.glEnd();
		}
	}
	
	private void renderOpponentBoard() {
		for(int i = 0; i < onlineMultiplayerGame.getOpponent().getBoard().getTiles().length; i++) {
			for(int j = 0; j < onlineMultiplayerGame.getOpponent().getBoard().getTiles()[i].length; j++) {
				if(onlineMultiplayerGame.getOpponent().getBoard().getTiles()[i][j] != TetrominoType.EMPTY) {			;
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(onlineMultiplayerGame.getOpponent().getBoard().getTiles()[i][j]);
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(423 + (j * 32), 60 + (i * 24));
					GL11.glVertex2i(423 + (j * 32) + 32, 60 + (i * 24));
					GL11.glVertex2i(423 + (j * 32) + 32, 60 + (i * 24) + 24);
					GL11.glVertex2i(423 + (j * 32), 60 + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(423 + (j * 32) + 1, 60 + (i * 24));
					GL11.glVertex2i(423 + (j * 32) + 1, 60 + (i * 24) + 24);
					// Top
					GL11.glVertex2i(423 + (j * 32), 60 + (i * 24) + 1);
					GL11.glVertex2i(423 + (j * 32) + 32, 60 + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(423 + (j * 32) + 1, 60 + (i * 24) + 24 - 1);
					GL11.glVertex2i(423 + (j * 32) + 32 - 1, 60 + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(423 + (j * 32) + 32 - 1, 60 + (i * 24) + 1);
					GL11.glVertex2i(423 + (j * 32) + 32 - 1, 60 + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}			
	}
	
	private void renderOpponentActiveTetromino() {
		int[][] activeTetrominoRepresentation = onlineMultiplayerGame.getOpponent().getActiveTetromino().getTetrominoRepresentation();
		int offsetX = onlineMultiplayerGame.getOpponent().getActiveTetromino().getXPosition();
		int offsetY = onlineMultiplayerGame.getOpponent().getActiveTetromino().getYPosition();
		
		for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
			for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
				// Don't render squares that hang out over the top of the board
				if(offsetY + i < 0) {
					continue;
				}
				
				if(activeTetrominoRepresentation[i][j] == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(onlineMultiplayerGame.getOpponent().getActiveTetromino().getType());
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24);
					// Top
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(423 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}				
	}
	
	private Color getTetrominoColor(TetrominoType tetrominoType) {
		switch(tetrominoType) {
		case EMPTY:
			return new Color(0.0f, 0.0f, 0.0f);
		case I:
			return new Color(0.0f, 1.0f, 1.0f);
		case J:
			return new Color(0.0f, 0.0f, 1.0f);
		case L:
			return new Color(1.0f, 0.5f, 0.0f);
		case O:
			return new Color(1.0f, 1.0f, 0.0f);
		case S:
			return new Color(0.0f, 1.0f, 0.0f);
		case T:
			return new Color(1.0f, 0.0f, 1.0f);
		case Z:
			return new Color(1.0f, 0.0f, 0.0f);
		default:
			return new Color(0.0f, 0.0f, 0.0f);
		}
	}
}

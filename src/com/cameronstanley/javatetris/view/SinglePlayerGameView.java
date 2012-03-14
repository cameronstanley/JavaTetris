package com.cameronstanley.javatetris.view;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import com.cameronstanley.javatetris.model.SinglePlayerGame;
import com.cameronstanley.javatetris.model.Tetromino;
import com.cameronstanley.javatetris.model.TetrominoType;

/**
 * Renders a single player game model for displaying the state of
 * the current game.
 * 
 * @author Cameron
 */
@SuppressWarnings("deprecation")
public class SinglePlayerGameView implements View {
	
	/**
	 * The single player game model to render.
	 */
	private SinglePlayerGame singlePlayerGame;
	
	/**
	 * The font used to render text dealing with game statistics.
	 */
	private TrueTypeFont statisticsFont;
	
	/**
	 * Creates and initializes a single player game view.
	 * 
	 * @param singlePlayerGame The single player game model to render.
	 */
	public SinglePlayerGameView(SinglePlayerGame singlePlayerGame) {
		this.singlePlayerGame = singlePlayerGame;
		Font awtFont = new Font("Lucida Grande", Font.BOLD, 18);
		statisticsFont = new TrueTypeFont(awtFont, true);
	}
	
	/**
	 * Calls all the rendering functions to display the view.
	 */
	public void render() {	
		clear();
		renderBoardOutline();
		renderBoardGrid();
		renderBoard();
		renderProjectedTetromino();
		renderActiveTetromino();
		renderTetrominoQueueOutline();
		renderTetrominoQueueGrid();
		renderTetrominoQueue();
		renderStats();
	}
	
	/**
	 * Clears the buffer.
	 */
	private void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 0.0f);
		GL11.glLineWidth(1.0f);
	}
	
	/**
	 * Renders the outline of the board.
	 */
	private void renderBoardOutline() {		
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex2i(239, 60);
		GL11.glVertex2i(239, 542);
		GL11.glVertex2i(561, 542);
		GL11.glVertex2i(561, 60);
		GL11.glEnd();
	}

	/**
	 * Renders the grid inside of the board outline.
	 */
	private void renderBoardGrid() {
		for(int i = 1; i < singlePlayerGame.getBoard().getHeight(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(240, 60 + (i * 24));
			GL11.glVertex2i(560, 60 + (i * 24));
			GL11.glEnd();
		}
		
		for(int i = 1; i < singlePlayerGame.getBoard().getWidth(); i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(240 + (i * 32), 60);
			GL11.glVertex2i(240 + (i * 32), 540);
			GL11.glEnd();
		}
	}
	
	/**
	 * Renders the state of the board.
	 */
	private void renderBoard() {
		for(int i = 0; i < singlePlayerGame.getBoard().getBoard().length; i++) {
			for(int j = 0; j < singlePlayerGame.getBoard().getBoard()[i].length; j++) {
				if(singlePlayerGame.getBoard().getBoard()[i][j] != TetrominoType.EMPTY) {			;
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(singlePlayerGame.getBoard().getBoard()[i][j]);
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(240 + (j * 32), 60 + (i * 24));
					GL11.glVertex2i(240 + (j * 32) + 32, 60 + (i * 24));
					GL11.glVertex2i(240 + (j * 32) + 32, 60 + (i * 24) + 24);
					GL11.glVertex2i(240 + (j * 32), 60 + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(240 + (j * 32) + 1, 60 + (i * 24));
					GL11.glVertex2i(240 + (j * 32) + 1, 60 + (i * 24) + 24);
					// Top
					GL11.glVertex2i(240 + (j * 32), 60 + (i * 24) + 1);
					GL11.glVertex2i(240 + (j * 32) + 32, 60 + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(240 + (j * 32) + 1, 60 + (i * 24) + 24 - 1);
					GL11.glVertex2i(240 + (j * 32) + 32 - 1, 60 + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(240 + (j * 32) + 32 - 1, 60 + (i * 24) + 1);
					GL11.glVertex2i(240 + (j * 32) + 32 - 1, 60 + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
	private void renderActiveTetromino() {
		int[][] activeTetrominoRepresentation = singlePlayerGame.getActiveTetromino().getTetrominoRepresentation();
		int offsetX = singlePlayerGame.getActiveTetromino().getXPosition();
		int offsetY = singlePlayerGame.getActiveTetromino().getYPosition();
		
		for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
			for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
				// Don't render squares that hang out over the top of the board
				if(offsetY + i < 0) {
					continue;
				}
				
				if(activeTetrominoRepresentation[i][j] == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					Color tetrominoColor = getTetrominoColor(singlePlayerGame.getActiveTetromino().getType());
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24);
					// Top
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
	private void renderProjectedTetromino() {
		Tetromino projectedTetromino = singlePlayerGame.generateProjectedTetromino();
		int[][] activeTetrominoRepresentation = projectedTetromino.getTetrominoRepresentation();
		int offsetX = projectedTetromino.getXPosition();
		int offsetY = projectedTetromino.getYPosition();
		
		for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
			for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
				// Don't render squares that hang out over the top of the board
				if(offsetY + i < 0) {
					continue;
				}
				
				if(activeTetrominoRepresentation[i][j] == 1) {
					
					GL11.glBegin(GL11.GL_LINES);
					Color tetrominoColor = getTetrominoColor(singlePlayerGame.getActiveTetromino().getType());
					GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
					// Left
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24));
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24);
					// Top
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32), 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32, 60 + (offsetY * 24) + (i * 24) + 1);
					// Bottom
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 1, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24);
					// Right
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 1);
					GL11.glVertex2i(240 + (offsetX * 32) + (j * 32) + 32 - 1, 60 + (offsetY * 24) + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
	private void renderTetrominoQueueOutline() {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex2i(584, 60);
		GL11.glVertex2i(584, 204);
		GL11.glVertex2i(776, 204);
		GL11.glVertex2i(776, 60);
		GL11.glVertex2i(584, 60);
		GL11.glEnd();
	}
	
	private void renderTetrominoQueueGrid() {
		for(int i = 1; i < 6; i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(584 + (i * 32), 60);
			GL11.glVertex2i(584 + (i * 32), 203);
			GL11.glEnd();
		}
		
		for(int i = 1; i < 6; i++) {
			GL11.glBegin(GL11.GL_LINES);
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex2i(584, 60 + (i * 24));
			GL11.glVertex2i(776, 60 + (i * 24));
			GL11.glEnd();
		}
	}
	
	private void renderTetrominoQueue() {
		int[][] nextTetrominoRepresentation = singlePlayerGame.getTetrominoQueue().getTetrominoes().get(0).getTetrominoRepresentation();
		
		for(int i = 0; i < nextTetrominoRepresentation.length; i++) {
			for(int j = 0; j < nextTetrominoRepresentation[i].length; j++) {
				if(nextTetrominoRepresentation[i][j] == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					Color nextTetrominoColor = getTetrominoColor(singlePlayerGame.getTetrominoQueue().getTetrominoes().get(0).getType());
					GL11.glColor3f(nextTetrominoColor.getRed(), nextTetrominoColor.getGreen(), nextTetrominoColor.getBlue());
					GL11.glVertex2i(584 + (j * 32) + 32, 84 + (i * 24));
					GL11.glVertex2i(584 + (j * 32) + 32 + 32, 84 + (i * 24));
					GL11.glVertex2i(584 + (j * 32) + 32 + 32, 84 + (i * 24) + 24);
					GL11.glVertex2i(584 + (j * 32) + 32, 84 + (i * 24) + 24);
					GL11.glEnd();
					
					// Shade top and left sides dark
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(0, 0, 0);
					// Left
					GL11.glVertex2i(584 + (j * 32) + 32 + 1, 84 + (i * 24));
					GL11.glVertex2i(584 + (j * 32) + 32 + 1, 84 + (i * 24) + 24);
					// Top
					GL11.glVertex2i(584 + (j * 32) + 32, 84 + (i * 24) + 1);
					GL11.glVertex2i(584 + (j * 32) + 32 + 32, 84 + (i * 24) + 1);
					GL11.glEnd();
					
					// Shade bottom and right sides light
					GL11.glBegin(GL11.GL_LINES);
					GL11.glColor3f(255, 255, 255);
					// Bottom
					GL11.glVertex2i(584 + (j * 32) + 32 + 1, 84 + (i * 24) + 24 - 1);
					GL11.glVertex2i(584 + (j * 32) + 32 + 32 - 1, 84 + (i * 24) + 24 - 1);
					// Right
					GL11.glVertex2i(584 + (j * 32) + 32 + 32 - 1, 84 + (i * 24) + 1);
					GL11.glVertex2i(584 + (j * 32) + 32 + 32- 1, 84 + (i * 24) + 24);
					GL11.glEnd();
				}
			}
		}
	}
	
	private void renderStats() {
		String paddedLevel = Integer.toString(singlePlayerGame.getLevel());
		String paddedLines = Integer.toString(singlePlayerGame.getTotalLinesCleared());
		String paddedScore = Long.toString(singlePlayerGame.getPlayer().getScore());
		
		for(int i = paddedLevel.length(); i < 2; i++) {
			paddedLevel = "0" + paddedLevel;
		}
		
		for(int i = paddedLines.length(); i < 6; i++) {
			paddedLines = "0" + paddedLines;
		}
		
		for(int i = paddedScore.length(); i < 10; i++) {
			paddedScore = "0" + paddedScore;
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		statisticsFont.drawString(24, 60, "Level: " + paddedLevel);
		statisticsFont.drawString(24, 90, "Score: " + paddedScore);
		statisticsFont.drawString(24, 120, "Lines: " + paddedLines);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
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

package com.cameronstanley.javatetris.client.view;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.cameronstanley.javatetris.client.model.MainMenu;
import com.cameronstanley.javatetris.client.model.MainMenuTetromino;
import com.cameronstanley.javatetris.client.model.Menu;
import com.cameronstanley.javatetris.client.model.TetrominoType;

@SuppressWarnings("deprecation")
public class MainMenuView implements View {

	private MainMenu mainMenu;
	private Menu menu;
	
	private TrueTypeFont menuItemFont;
	
	private static final int[][] titleJ = {{1, 1, 1, 1, 1},
									 	   {0, 0, 1, 0, 0},
									 	   {0, 0, 1, 0, 0},
									 	   {0, 0, 1, 0, 0},
									 	   {1, 1, 1, 0, 0}};
	
	private static final int[][] titleA = {{0, 1, 1, 1, 0},
									 	   {0, 1, 0, 1, 0},
									 	   {0, 1, 1, 1, 0},
									 	   {1, 1, 0, 1, 1},
									 	   {1, 0, 0, 0, 1}};
	
	private static final int[][] titleV = {{1, 0, 0, 0, 1},
		                             	   {1, 0, 0, 0, 1},
		                             	   {1, 1, 0, 1, 1},
		                             	   {0, 1, 0, 1, 0},
		                             	   {0, 1, 1, 1, 0}};
	
	private static final int[][] titleT = {{1, 1, 1, 1, 1},
									       {0, 0, 1, 0, 0},
									       {0, 0, 1, 0, 0},
									       {0, 0, 1, 0 ,0},
									       {0, 0, 1, 0, 0}};
	
	private static final int[][] titleE = {{1, 1, 1, 1, 1},
				                           {1, 0, 0, 0, 0},
				                           {1, 1, 1, 0, 0},
				                           {1, 0, 0, 0, 0},
				                           {1, 1, 1, 1, 1}};
	
	private static final int[][] titleR = {{1, 1, 1, 0, 0},
		                                   {1, 0, 1, 0, 0},
		                                   {1, 1, 1, 0, 0},
		                                   {1, 0, 1, 1, 0},
		                                   {1, 0, 0, 1, 1}};
	
	private static final int[][] titleI = {{1, 1, 1, 1, 1},
									       {0, 0, 1, 0, 0},
									       {0, 0, 1, 0, 0},
									       {0, 0, 1, 0, 0},
									       {1, 1, 1, 1, 1}};
	
	private static final int[][] titleS = {{1, 1, 1, 1, 1},
									 	   {1, 0, 0, 0, 0},
									 	   {1, 1, 1, 1, 1},
									 	   {0, 0, 0, 0, 1},
									 	   {1, 1, 1, 1, 1}};
	
	private static final int[][][] titleJAVA = {titleJ, titleA, titleV, titleA};
	private static final int[][][] titleTETRIS = {titleT, titleE, titleT, titleR, titleI, titleS};
		
	public MainMenuView(MainMenu mainMenu, Menu menu) {
		this.mainMenu = mainMenu;
		this.menu = menu;
		
		Font menuItemAwtFont = new Font("Tahoma", Font.BOLD, 24);
		menuItemFont = new TrueTypeFont(menuItemAwtFont, true);
	}
	
	public void render() {
		clear();
		renderTetrominoes();
		renderMenuBackground();
		renderTitle();
		renderMenuItems();
		renderSelector();
	}
	
	private void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 0.0f);
		GL11.glLineWidth(1.0f);
	}
	
	private void renderTitle() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(int i = 0; i < titleJAVA.length; i++) {
			for(int j = 0; j < titleJAVA[i].length; j++) {
				for(int k = 0; k < titleJAVA[i][j].length; k++) {
					if(titleJAVA[i][j][k] == 1) {
						GL11.glBegin(GL11.GL_QUADS);
						switch(i) {
						case 0:
							GL11.glColor3f(0.0f, 1.0f, 1.0f);
							break;
						case 1:
							GL11.glColor3f(0.0f, 0.0f, 1.0f);
							break;
						case 2:
							GL11.glColor3f(1.0f, 0.5f, 0.0f);
							break;
						case 3:
							GL11.glColor3f(1.0f, 1.0f, 0.0f);
							break;
						}
						GL11.glVertex2i(190 + (i * 40) + (k * 8), 200 + (j * 8));
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8, 200 + (j * 8));
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8, 200 + (j * 8) + 8);
						GL11.glVertex2i(190 + (i * 40) + (k * 8), 200 + (j * 8) + 8);
						GL11.glEnd();
						
						// Shade top and left sides dark
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(0, 0, 0);
						// Left
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 1, 200 + (j * 8));
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 1, 200 + (j * 8) + 8);
						// Top
						GL11.glVertex2i(190 + (i * 40) + (k * 8), 200 + (j * 8) + 1);
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8, 200 + (j * 8) + 1);
						GL11.glEnd();
						
						// Shade bottom and right sides light
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(255, 255, 255);
						// Bottom
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 1, 200 + (j * 8) + 8 - 1);
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 8 - 1);
						// Right
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 1);
						GL11.glVertex2i(190 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 8);
						GL11.glEnd();
					}
				}
			}
		}
		
		for(int i = 0; i < titleTETRIS.length; i++) {
			for(int j = 0; j < titleTETRIS[i].length; j++) {
				for(int k = 0; k < titleTETRIS[i][j].length; k++) {
					if(titleTETRIS[i][j][k] == 1) {
						GL11.glBegin(GL11.GL_QUADS);
						switch(i) {
						case 0:
							GL11.glColor3f(0.0f, 1.0f, 0.0f);
							break;
						case 1:
							GL11.glColor3f(1.0f, 0.0f, 1.0f);
							break;
						case 2:
							GL11.glColor3f(1.0f, 0.0f, 0.0f);
							break;
						case 3:
							GL11.glColor3f(0.0f, 1.0f, 1.0f);
							break;
						case 4:
							GL11.glColor3f(0.0f, 0.0f, 1.0f);
							break;
						case 5:
							GL11.glColor3f(1.0f, 0.5f, 0.0f);
							break;
						}
						GL11.glVertex2i(370 + (i * 40) + (k * 8), 200 + (j * 8));
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8, 200 + (j * 8));
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8, 200 + (j * 8) + 8);
						GL11.glVertex2i(370 + (i * 40) + (k * 8), 200 + (j * 8) + 8);
						GL11.glEnd();
						
						// Shade top and left sides dark
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(0, 0, 0);
						// Left
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 1, 200 + (j * 8));
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 1, 200 + (j * 8) + 8);
						// Top
						GL11.glVertex2i(370 + (i * 40) + (k * 8), 200 + (j * 8) + 1);
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8, 200 + (j * 8) + 1);
						GL11.glEnd();
						
						// Shade bottom and right sides light
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(255, 255, 255);
						// Bottom
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 1, 200 + (j * 8) + 8 - 1);
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 8 - 1);
						// Right
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 1);
						GL11.glVertex2i(370 + (i * 40) + (k * 8) + 8 - 1, 200 + (j * 8) + 8);
						GL11.glEnd();
					}
				}
			}
		}
	}
	
	private void renderMenuBackground() {
		// Dark gray square behind title
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0.17f, 0.17f, 0.17f);
		GL11.glVertex2i(185, 195);
		GL11.glVertex2i(615, 195);
		GL11.glVertex2i(615, 245);
		GL11.glVertex2i(185, 245);
		GL11.glEnd();
		
		// Light gray square behind menu items
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex2i(185, 275);
		GL11.glVertex2i(615, 275);
		GL11.glVertex2i(615, 500);
		GL11.glVertex2i(185, 500);
		GL11.glEnd();
		
		// Dark gray outline
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(0.15f, 0.15f, 0.15f);
		GL11.glVertex2i(185, 275);
		GL11.glVertex2i(615, 275);
		
		GL11.glVertex2i(185, 275);
		GL11.glVertex2i(185, 500);
		
		GL11.glVertex2i(615, 275);
		GL11.glVertex2i(615, 500);
		
		GL11.glVertex2i(615, 500);
		GL11.glVertex2i(185, 500);
		GL11.glEnd();
	}
	
	private void renderMenuItems() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color fontColor;
		
		for (int i = 0; i < menu.getMenuItems().size(); i++) {
			if (menu.getMenuItems().get(i).isEnabled()) {
				fontColor = Color.white;
			} else {
				fontColor = Color.gray;
			}
			
			menuItemFont.drawString(300, 300 + (i * 50), menu.getMenuItems().get(i).getText(), fontColor);
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	private void renderSelector() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		menuItemFont.drawString(250, 300 + (menu.getSelectedMenuItemIndex() * 50), ">");
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	private void renderTetrominoes() {
		for (MainMenuTetromino mainMenuTetromino : mainMenu.getMainMenuTetrominoes()) {
			int[][] activeTetrominoRepresentation = mainMenuTetromino.getTetrominoRepresentation();
			int offsetX = mainMenuTetromino.getXPosition();
			int offsetY = mainMenuTetromino.getYPosition();
			int xCoord = mainMenuTetromino.getxCoordinate();
			int yCoord = mainMenuTetromino.getyCoordinate();
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			for(int i = 0; i < activeTetrominoRepresentation.length; i++) {
				for(int j = 0; j < activeTetrominoRepresentation[i].length; j++) {
					// Don't render squares that hang out over the top of the board
					if(offsetY + i < 0) {
						continue;
					}
					
					if(activeTetrominoRepresentation[i][j] == 1) {
						GL11.glBegin(GL11.GL_QUADS);
						com.cameronstanley.javatetris.client.view.Color tetrominoColor = getTetrominoColor(mainMenuTetromino.getType());
						GL11.glColor3f(tetrominoColor.getRed(), tetrominoColor.getGreen(), tetrominoColor.getBlue());
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32), yCoord + (offsetY * 24) + (i * 24));
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32, yCoord + (offsetY * 24) + (i * 24));
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32, yCoord + (offsetY * 24) + (i * 24) + 24);
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32), yCoord + (offsetY * 24) + (i * 24) + 24);
						GL11.glEnd();
						
						// Shade top and left sides dark
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(0, 0, 0);
						// Left
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 1, yCoord + (offsetY * 24) + (i * 24));
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 1, yCoord + (offsetY * 24) + (i * 24) + 24);
						// Top
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32), yCoord + (offsetY * 24) + (i * 24) + 1);
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32, yCoord + (offsetY * 24) + (i * 24) + 1);
						GL11.glEnd();
						
						// Shade bottom and right sides light
						GL11.glBegin(GL11.GL_LINES);
						GL11.glColor3f(255, 255, 255);
						// Bottom
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 1, yCoord + (offsetY * 24) + (i * 24) + 24 - 1);
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32 - 1, yCoord + (offsetY * 24) + (i * 24) + 24 - 1);
						// Right
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32 - 1, yCoord + (offsetY * 24) + (i * 24) + 1);
						GL11.glVertex2i(xCoord + (offsetX * 32) + (j * 32) + 32 - 1, yCoord + (offsetY * 24) + (i * 24) + 24);
						GL11.glEnd();
					}
				}
			}
		}
	}

	private com.cameronstanley.javatetris.client.view.Color getTetrominoColor(TetrominoType tetrominoType) {
		switch(tetrominoType) {
		case EMPTY:
			return new com.cameronstanley.javatetris.client.view.Color(0.0f, 0.0f, 0.0f);
		case I:
			return new com.cameronstanley.javatetris.client.view.Color(0.0f, 1.0f, 1.0f);
		case J:
			return new com.cameronstanley.javatetris.client.view.Color(0.0f, 0.0f, 1.0f);
		case L:
			return new com.cameronstanley.javatetris.client.view.Color(1.0f, 0.5f, 0.0f);
		case O:
			return new com.cameronstanley.javatetris.client.view.Color(1.0f, 1.0f, 0.0f);
		case S:
			return new com.cameronstanley.javatetris.client.view.Color(0.0f, 1.0f, 0.0f);
		case T:
			return new com.cameronstanley.javatetris.client.view.Color(1.0f, 0.0f, 1.0f);
		case Z:
			return new com.cameronstanley.javatetris.client.view.Color(1.0f, 0.0f, 0.0f);
		default:
			return new com.cameronstanley.javatetris.client.view.Color(0.0f, 0.0f, 0.0f);
		}
	}

}

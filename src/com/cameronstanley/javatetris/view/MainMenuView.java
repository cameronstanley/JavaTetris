package com.cameronstanley.javatetris.view;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.cameronstanley.javatetris.model.Menu;

@SuppressWarnings("deprecation")
public class MainMenuView implements View {

	private Menu mainMenu;
	
	private TrueTypeFont titleFont;
	private TrueTypeFont menuItemFont;
	
	public MainMenuView(Menu mainMenu) {
		this.mainMenu = mainMenu;
		
		Font titleAwtFont = new Font("Lucida Grande", Font.BOLD, 36);
		Font menuItemAwtFont = new Font("Lucida Grande", Font.BOLD, 24);
		titleFont = new TrueTypeFont(titleAwtFont, true);
		menuItemFont = new TrueTypeFont(menuItemAwtFont, true);
	}
	
	public void render() {
		clear();
		renderTitle();
		renderMenuItems();
		renderSelector();
	}
	
	private void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.25f, 0.25f, 0.25f, 0.0f);
	}
	
	private void renderTitle() {
		titleFont.drawString(300, 200, "JavaTetris");
	}
	
	private void renderMenuItems() {
		Color fontColor;
		
		for (int i = 0; i < mainMenu.getMenuItems().size(); i++) {
			if (mainMenu.getMenuItems().get(i).isEnabled()) {
				fontColor = Color.white;
			} else {
				fontColor = Color.gray;
			}
			
			menuItemFont.drawString(300, 300 + (i * 50), mainMenu.getMenuItems().get(i).getText(), fontColor);
		}
	}
	
	private void renderSelector() {
		menuItemFont.drawString(250, 300 + (mainMenu.getSelectedMenuItemIndex() * 50), ">");
	}
}

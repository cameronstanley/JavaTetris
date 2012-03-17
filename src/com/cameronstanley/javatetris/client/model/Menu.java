package com.cameronstanley.javatetris.client.model;

import java.util.ArrayList;

public class Menu {

	private ArrayList<MenuItem> menuItems;
	private int selectedMenuItemIndex;
	
	public Menu() {
		menuItems = new ArrayList<MenuItem>();
		selectedMenuItemIndex = 0;
	}
	
	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	
	public void selectNextMenuItem() {
		int startingIndex = selectedMenuItemIndex;
		while (true) {
			if (selectedMenuItemIndex == menuItems.size() - 1) {
				selectedMenuItemIndex = 0;
			} else {
				selectedMenuItemIndex++;
			}
			
			if (menuItems.get(selectedMenuItemIndex).isEnabled()) {
				break;
			}
			
			if (selectedMenuItemIndex == startingIndex) {
				break;
			}
		}
	}
	
	public void selectPreviousMenuItem() {
		int startingIndex = selectedMenuItemIndex;
		
		while (true) {
			if (selectedMenuItemIndex == 0) {
				selectedMenuItemIndex = menuItems.size() - 1;
			} else {
				selectedMenuItemIndex--;
			}
			
			if (menuItems.get(selectedMenuItemIndex).isEnabled()) {
				break;
			}
			
			if (selectedMenuItemIndex == startingIndex) {
				break;
			}
		}
	}

	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}
	
	public int getSelectedMenuItemIndex() {
		return selectedMenuItemIndex;
	}

}
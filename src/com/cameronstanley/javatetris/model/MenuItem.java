package com.cameronstanley.javatetris.model;

public class MenuItem {

	private String text;
	private boolean enabled;
	
	public MenuItem(String text, boolean enabled) {
		this.text = text;
		this.enabled = enabled;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}

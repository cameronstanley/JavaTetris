package com.cameronstanley.javatetris.client;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;

/**
 * The driver to run the JavaTetris client.
 * 
 * @author Cameron
 */
public class Main {

	/**
	 * The entry point to the application.
	 * 
	 * @param args The command line arguments to the program.
	 */
	public static void main(String[] args) {
		JavaTetrisController javaTetrisController = JavaTetrisController.getInstance();
		javaTetrisController.start();
	}
	
}

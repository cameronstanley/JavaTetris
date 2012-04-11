package com.cameronstanley.javatetris.client;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;

/**
 * The driver to run the JavaTetris client.
 * 
 * @author Cameron
 */
public class Main {

	/**
	 * The main entry point to the program.
	 * 
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		JavaTetrisController javaTetrisController = JavaTetrisController.getInstance();
		javaTetrisController.start();
	}
	
}

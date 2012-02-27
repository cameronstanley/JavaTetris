package com.cameronstanley.javatetris;

import com.cameronstanley.javatetris.controller.JavaTetrisController;

/**
 * The driver to run the JavaTetris application.
 * 
 * @author Cameron
 */
public class JavaTetris {

	/**
	 * The entry point to the application.
	 * 
	 * @param args The command line arguments to the program.
	 */
	public static void main(String[] args) {
		JavaTetrisController javaTetrisController = new JavaTetrisController();
		javaTetrisController.start();
	}
	
}

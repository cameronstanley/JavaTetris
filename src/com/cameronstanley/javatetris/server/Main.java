package com.cameronstanley.javatetris.server;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		JavaTetrisServer javaTetrisServer = new JavaTetrisServer();
		javaTetrisServer.start();
	}
	
}

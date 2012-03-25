package com.cameronstanley.javatetris.network.messages;

import com.cameronstanley.javatetris.client.model.Tetromino;

public class UpdateTetrominoResponse {

	private Tetromino updatedTetromino;
	
	public UpdateTetrominoResponse() {
		
	}

	public Tetromino getUpdatedTetromino() {
		return updatedTetromino;
	}

	public void setUpdatedTetromino(Tetromino updatedTetromino) {
		this.updatedTetromino = updatedTetromino;
	}
	
}

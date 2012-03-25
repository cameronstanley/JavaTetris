package com.cameronstanley.javatetris.network.messages;

import com.cameronstanley.javatetris.client.model.Tetromino;

public class UpdateTetrominoRequest {
	
	private Tetromino updatedTetromino;
	
	public UpdateTetrominoRequest() {
		
	}

	public Tetromino getUpdatedTetromino() {
		return updatedTetromino;
	}

	public void setUpdatedTetromino(Tetromino updatedTetromino) {
		this.updatedTetromino = updatedTetromino;
	}
	
}

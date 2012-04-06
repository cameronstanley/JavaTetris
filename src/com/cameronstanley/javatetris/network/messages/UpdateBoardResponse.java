package com.cameronstanley.javatetris.network.messages;

import com.cameronstanley.javatetris.client.model.Board;

public class UpdateBoardResponse {
	
	private Board board;
	
	public UpdateBoardResponse() {
		
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
}

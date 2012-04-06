package com.cameronstanley.javatetris.network.messages;

import com.cameronstanley.javatetris.client.model.Board;

public class UpdateBoardRequest {

	private Board board;
	
	public UpdateBoardRequest() {
		
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
}

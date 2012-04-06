package com.cameronstanley.javatetris.server;

import java.util.HashMap;

public class GameManager {

	private HashMap<Integer, Game> activeGames;
	private int gameIDCount;
	
	public GameManager() {
		gameIDCount = 0;
		activeGames = new HashMap<Integer, Game>();
	}

	public int startNewGame(User player1, User player2) {
		Game newGame = new Game();
		player1.setGameID(gameIDCount);
		player2.setGameID(gameIDCount);
		newGame.setPlayer1(player1);
		newGame.setPlayer2(player2);
		activeGames.put(new Integer(gameIDCount), newGame);
		return gameIDCount++;
	}

	public void endGame(int gameID) {
		Game game = activeGames.get(new Integer(gameID));
		game.getPlayer1().setGameID(-1);
		game.getPlayer2().setGameID(-1);
		activeGames.remove(game);
	}
	
	public User retrieveOpponent(User player) {
		Game game = activeGames.get(new Integer(player.getGameID()));
		if (game != null) {
			if (player.equals(game.getPlayer1())) {
				return game.getPlayer2();
			} else {
				return game.getPlayer1();
			}
		} else {
			return null;
		}
	}
	
}

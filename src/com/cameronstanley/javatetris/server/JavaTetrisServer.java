package com.cameronstanley.javatetris.server;

import java.io.IOException;

import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.PlayerConnection;
import com.cameronstanley.javatetris.network.messages.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class JavaTetrisServer {

	private Server server;
	private RegistrationManager registrationManager;
	private LoginManager loginManager;
	private GameManager gameManager;

	public JavaTetrisServer() {
		Log.set(Log.LEVEL_DEBUG);
		registrationManager = new RegistrationManager();
		registrationManager.loadUsers();
		loginManager = new LoginManager();
		gameManager = new GameManager();
	}

	public void start() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

		Network.registerClasses(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				PlayerConnection playerConnection = (PlayerConnection) connection;

				try {
					if (object instanceof RegisterRequest) {
						// Ignore if already logged in
						if (loginManager.isUserLoggedIn(playerConnection.getUser())) {
							return;
						}

						RegisterRequest registerRequest = (RegisterRequest) object;
						RegisterResponse registerResponse = new RegisterResponse();

						User user = new User();
						user.setUsername(registerRequest.getDesiredUsername());
						user.setPassword(registerRequest.getDesiredPassword());

						registerResponse.setSuccessfullyRegistered(registrationManager.registerUser(user));
						connection.sendTCP(registerResponse);
					} else if (object instanceof LoginRequest) {
						// Ignore if already logged in
						if (loginManager.isUserLoggedIn(playerConnection.getUser())) {
							return;
						}

						LoginRequest loginRequest = (LoginRequest) object;
						LoginResponse loginResponse = new LoginResponse();

						User user = new User();
						user.setUsername(loginRequest.getUsername());
						user.setPassword(loginRequest.getPassword());

						if (registrationManager.isUserRegistered(user)) {
							if (loginManager.logInUser(user)) {
								playerConnection.setUser(user);
								loginResponse.setLoggedIn(true);
								connection.sendTCP(loginResponse);

								// Check if we can start a game
								for (User opponent : loginManager.getLoggedInUsers()) {
									if (!user.equals(opponent) && opponent.getGameID() == -1) {
										gameManager.startNewGame(user, opponent);

										Connection opponentConnection = findAssociatedConnection(opponent);
										StartNewGameResponse startNewGameResponse = new StartNewGameResponse();
										opponentConnection.sendTCP(startNewGameResponse);
										playerConnection.sendTCP(startNewGameResponse);
										
										break;
									}
								}
							} else {
								loginResponse.setLoggedIn(false);
								connection.sendTCP(loginResponse);
							}
						} else {
							loginResponse.setLoggedIn(false);
							connection.sendTCP(loginResponse);
						}
					} else if (object instanceof UpdateTetrominoRequest) {
						// Ignore if not logged in or not playing
						if (!loginManager.isUserLoggedIn(playerConnection.getUser()) || playerConnection.getUser().getGameID() == -1) {
							return;
						}

						UpdateTetrominoRequest updateTetrominoRequest = (UpdateTetrominoRequest) object;
						UpdateTetrominoResponse updateTetrominoResponse = new UpdateTetrominoResponse();
						updateTetrominoResponse.setUpdatedTetromino(updateTetrominoRequest.getUpdatedTetromino());

						User opponent = gameManager.retrieveOpponent(playerConnection.getUser());

						Connection opponentConnection = findAssociatedConnection(opponent);
						opponentConnection.sendTCP(updateTetrominoResponse);
					} else if (object instanceof UpdateBoardRequest) {
						// Ignore if not logged in or not playing
						if (!loginManager.isUserLoggedIn(playerConnection.getUser()) || playerConnection.getUser().getGameID() == -1) {
							return;
						}

						UpdateBoardRequest updateBoardRequest = (UpdateBoardRequest) object;
						UpdateBoardResponse updateBoardResponse = new UpdateBoardResponse();
						updateBoardResponse.setBoard(updateBoardRequest.getBoard());

						User opponent = gameManager.retrieveOpponent(playerConnection.getUser());
						Connection opponentConnection = findAssociatedConnection(opponent);
						opponentConnection.sendTCP(updateBoardResponse);
					} else if (object instanceof UpdatePlayerStatsRequest) {
						// Ignore if not logged in or not playing
						if (!loginManager.isUserLoggedIn(playerConnection.getUser()) || playerConnection.getUser().getGameID() == -1) {
							return;
						}
						
						UpdatePlayerStatsRequest updatePlayerStatsRequest = (UpdatePlayerStatsRequest) object;
						
						// Copy request parameters to response
						UpdatePlayerStatsResponse updatePlayerStatsResponse = new UpdatePlayerStatsResponse();
						updatePlayerStatsResponse.setLevel(updatePlayerStatsRequest.getLevel());
						updatePlayerStatsResponse.setTotalLinesCleared(updatePlayerStatsRequest.getTotalLinesCleared());
						updatePlayerStatsResponse.setScore(updatePlayerStatsRequest.getScore());
						
						// Send response to opponent
						User opponent = gameManager.retrieveOpponent(playerConnection.getUser());
						Connection opponentConnection = findAssociatedConnection(opponent);
						opponentConnection.sendTCP(updatePlayerStatsResponse);
					} else if (object instanceof GameOverRequest) {
						// Ignore if not logged in or not playing
						if (!loginManager.isUserLoggedIn(playerConnection.getUser()) || playerConnection.getUser().getGameID() == -1) {
							return;
						}
						
						// Player that sends request is loser
						GameOverResponse winnerGameOverResponse = new GameOverResponse();
						GameOverResponse loserGameOverResponse = new GameOverResponse();
						winnerGameOverResponse.setWinner('W');
						loserGameOverResponse.setWinner('L');
						
						// Send GameOverResponses
						User winner = gameManager.retrieveOpponent(playerConnection.getUser());
						Connection winnerConnection = findAssociatedConnection(winner);
						
						connection.sendTCP(loserGameOverResponse);
						winnerConnection.sendTCP(winnerGameOverResponse);
						
						// End game
						gameManager.endGame(winner.getGameID());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			public void disconnected(Connection connection) {
				PlayerConnection playerConnection = (PlayerConnection) connection;
				User disconnectedUser = playerConnection.getUser();

				loginManager.logOutUser(disconnectedUser);
				if (disconnectedUser.getGameID() != -1) {
					gameManager.endGame(disconnectedUser.getGameID());
				}
			}
		});

		server.bind(Network.PORT);
		server.start();
	}

	private Connection findAssociatedConnection(User player) {
		for (Connection connection : server.getConnections()) {
			PlayerConnection playerConnection = (PlayerConnection) connection;
			if (playerConnection.getUser().equals(player)) {
				return connection;
			}
		}
		
		return null;
	}
	
}

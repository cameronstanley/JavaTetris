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
									
									StartNewGameResponse startNewGameResponse = new StartNewGameResponse();
									
									for (Connection c : server.getConnections()) {
										PlayerConnection p = (PlayerConnection) c;
										if (p.getUser().equals(opponent)) {
											p.sendTCP(startNewGameResponse);
											playerConnection.sendTCP(startNewGameResponse);
											break;
										}
									}
									
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
			
					for (Connection opponentConnection : server.getConnections()) {
						PlayerConnection opponentPlayerConnection = (PlayerConnection) opponentConnection;
						if (opponentPlayerConnection.getUser().equals(opponent)) {
							opponentConnection.sendTCP(updateTetrominoResponse);
							break;
						}
					}
				} else if (object instanceof UpdateBoardRequest) {
					// Ignore if not logged in or not playing
					if (!loginManager.isUserLoggedIn(playerConnection.getUser()) || playerConnection.getUser().getGameID() == -1) {
						return;
					}
										
					UpdateBoardRequest updateBoardRequest = (UpdateBoardRequest) object;
					UpdateBoardResponse updateBoardResponse = new UpdateBoardResponse();
					updateBoardResponse.setBoard(updateBoardRequest.getBoard());
					
					User opponent = gameManager.retrieveOpponent(playerConnection.getUser());
					
					for (Connection opponentConnection : server.getConnections()) {
						PlayerConnection opponentPlayerConnection = (PlayerConnection) opponentConnection;
						if (opponentPlayerConnection.getUser().equals(opponent)) {
							opponentConnection.sendTCP(updateBoardResponse);
							break;
						}
					}
					
				} else if (object instanceof UpdatePlayerStatsRequest) {
					
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
	
	public static void main(String[] args) throws IOException {
		JavaTetrisServer javaTetrisServer = new JavaTetrisServer();
		javaTetrisServer.start();
	}
	
}

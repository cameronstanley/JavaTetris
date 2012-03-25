package com.cameronstanley.javatetris.server;

import java.io.IOException;
import java.util.ArrayList;

import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.PlayerConnection;
import com.cameronstanley.javatetris.network.messages.LoginRequest;
import com.cameronstanley.javatetris.network.messages.LoginResponse;
import com.cameronstanley.javatetris.network.messages.RegisterRequest;
import com.cameronstanley.javatetris.network.messages.RegisterResponse;
import com.cameronstanley.javatetris.network.messages.StartNewGameRequest;
import com.cameronstanley.javatetris.network.messages.StartNewGameResponse;
import com.cameronstanley.javatetris.network.messages.UpdateTetrominoRequest;
import com.cameronstanley.javatetris.network.messages.WaitingPlayersRequest;
import com.cameronstanley.javatetris.network.messages.WaitingPlayersResponse;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class JavaTetrisServer {

	private Server server;
	private RegistrationManager registrationManager;
	private LoginManager loginManager;
	private ArrayList<Game> activeGames;
	
	public JavaTetrisServer() {
		Log.set(Log.LEVEL_DEBUG);
		registrationManager = new RegistrationManager();
		registrationManager.loadUsers();
		loginManager = new LoginManager();
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
							loginResponse.setLoggedIn(true);
						} else {
							loginResponse.setLoggedIn(false);
						}
					} else {
						loginResponse.setLoggedIn(false);
					}
					
					connection.sendTCP(loginResponse);
				} else if (object instanceof WaitingPlayersRequest) {
					// Ignore if not logged in
					if (!loginManager.isUserLoggedIn(playerConnection.getUser())) {
						return;
					}
					
					WaitingPlayersResponse waitingPlayersResponse = new WaitingPlayersResponse();
					ArrayList<User> waitingPlayers = new ArrayList<User>();
					
					for (User user : loginManager.getLoggedInUsers()) {
						if (!user.equals(playerConnection.getUser()) && !user.isPlayingGame()) {
							waitingPlayers.add(user);
						}
					}
					
					waitingPlayersResponse.setWaitingPlayers(waitingPlayers);
					connection.sendTCP(waitingPlayersResponse);
				} else if (object instanceof StartNewGameRequest) {
					// Ignore if not logged in
					if (!loginManager.isUserLoggedIn(playerConnection.getUser())) {
						return;
					}
					
					StartNewGameRequest startNewGameRequest = (StartNewGameRequest) object;
					StartNewGameResponse startNewGameResponse = new StartNewGameResponse();
					User desiredOpponent = startNewGameRequest.getDesiredOpponent();
				
					for (Connection opponentConnection : server.getConnections()) {
						PlayerConnection opponentPlayerConnection = (PlayerConnection) opponentConnection;
						if (opponentPlayerConnection.getUser().equals(desiredOpponent)) {
							if (!opponentPlayerConnection.getUser().isPlayingGame()) {		
								Game newGame = new Game();
								newGame.setPlayer1(playerConnection.getUser());
								newGame.setPlayer2(opponentPlayerConnection.getUser());
								activeGames.add(newGame);
								
								playerConnection.getUser().setPlayingGame(true);
								opponentPlayerConnection.getUser().setPlayingGame(true);

								playerConnection.sendTCP(startNewGameResponse);
								opponentPlayerConnection.sendTCP(startNewGameResponse);
								break;
							}
						}
					}
				} else if (object instanceof UpdateTetrominoRequest) {
					
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

package com.cameronstanley.javatetris.client.controller.input;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.cameronstanley.javatetris.client.controller.JavaTetrisController;
import com.cameronstanley.javatetris.client.controller.JavaTetrisControllerState;
import com.cameronstanley.javatetris.client.model.OnlineMultiplayerGame;
import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.messages.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkController {

	private Client client;
	private Listener registerListener;
	private Listener loginListener;
	private Listener startNewGameListener;
	private Listener playGameListener;
	private Listener disconnectedListener;
	private OnlineMultiplayerGame onlineMultiplayerGame;
	
	public NetworkController(OnlineMultiplayerGame onlineMultiplayerGame) {
		this.onlineMultiplayerGame = onlineMultiplayerGame;
		
		client = new Client();
		client.start();
		Network.registerClasses(client);
		
		registerListener = generateRegisterListener();
		loginListener = generateLoginListener();
		startNewGameListener = generateStartNewGameListener();
		playGameListener = generatePlayGameListener();
		disconnectedListener = generateDisconnectedListener();
		
		client.addListener(registerListener);
		client.addListener(loginListener);
		client.addListener(startNewGameListener);
		client.addListener(playGameListener);
		client.addListener(disconnectedListener);
	}
	
	public void connect() {
		String host = (String) JOptionPane.showInputDialog(null, "Connect to host: ", "Connect", JOptionPane.PLAIN_MESSAGE, null, null, "localhost");
		if (host == null) {
			return;
		}
		
		try {
			client.connect(5000, host, Network.PORT);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error connecting to " + host + "!\nError: " + ex.getMessage(), "Connection Error", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		registerRequest();
	}
	
	public void disconnect() {
		if (client.isConnected()) {
			client.close();
		}
		
		onlineMultiplayerGame.setWaiting(true);
	}
	
	public void registerRequest() {
		int register = JOptionPane.showConfirmDialog(null, "Are you registered on this server?", "Already Registered?", JOptionPane.YES_NO_OPTION);
		if (register == JOptionPane.YES_OPTION) {
			login();
		} else {
			register();
		}
	}
	
	public void register() {
		String desiredUsername = (String) JOptionPane.showInputDialog(null, "Desired Username: ", "Register on Server", JOptionPane.QUESTION_MESSAGE, null, null, "username");
		if (desiredUsername == null) {
			disconnect();
			return;
		}
		
		String desiredPassword = (String) JOptionPane.showInputDialog(null, "Desired Password: ", "Register on Server", JOptionPane.QUESTION_MESSAGE, null, null, "password");
		if (desiredPassword == null) {
			disconnect();
			return;
		}
		
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setDesiredUsername(desiredUsername);
		registerRequest.setDesiredPassword(desiredPassword);
		
		client.sendTCP(registerRequest);	
	}
	
	public void login() {
		String username = (String) JOptionPane.showInputDialog(null, "Username: ", "Login to server", JOptionPane.QUESTION_MESSAGE, null, null, "username");
		if (username == null) {
			disconnect();
			return;
		}
		
		String password = (String) JOptionPane.showInputDialog(null, "Password: ", "Login to server", JOptionPane.QUESTION_MESSAGE, null, null, "password");
		if (password == null) {
			disconnect();
			return;
		}
				
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		
		client.sendTCP(loginRequest);
	}
	
	public void updateActiveTetromino() {
		UpdateTetrominoRequest updateTetrominoRequest = new UpdateTetrominoRequest();
		updateTetrominoRequest.setUpdatedTetromino(onlineMultiplayerGame.getPlayer().getActiveTetromino());

		client.sendTCP(updateTetrominoRequest);
	}
	
	public void updateBoard() {
		UpdateBoardRequest updateBoardRequest = new UpdateBoardRequest();
		updateBoardRequest.setBoard(onlineMultiplayerGame.getPlayer().getBoard());
		
		client.sendTCP(updateBoardRequest);
	}
	
	public void updateStats() {
		UpdatePlayerStatsRequest updatePlayerStatsRequest = new UpdatePlayerStatsRequest();
		updatePlayerStatsRequest.setLevel(onlineMultiplayerGame.getPlayer().getLevel());
		updatePlayerStatsRequest.setTotalLinesCleared(onlineMultiplayerGame.getPlayer().getTotalLinesCleared());
		updatePlayerStatsRequest.setScore(onlineMultiplayerGame.getPlayer().getScore());
		
		client.sendTCP(updatePlayerStatsRequest);
	}
	
	public void playerLost() {
		GameOverRequest gameOverRequest = new GameOverRequest();
		client.sendTCP(gameOverRequest);
	}
	
	private Listener generateRegisterListener() {
		return new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof RegisterResponse) {
					RegisterResponse registerResponse = (RegisterResponse) object;
					if (registerResponse.isSuccessfullyRegistered()) {

					} else {
						
					}
				}
			}
		};
	}
	
	private Listener generateLoginListener() {
		return new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof LoginResponse) {
					LoginResponse loginResponse = (LoginResponse) object;
					if (loginResponse.isLoggedIn()) {
						JavaTetrisController.getInstance().setState(JavaTetrisControllerState.PLAYONLINEMULTIPLAYER);
					} else {
						JOptionPane.showMessageDialog(null, "Login failed! Please try again.");
						login();
					}
				}
			}
		};
	}
	
	private Listener generateStartNewGameListener() {
		return new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof StartNewGameResponse) {
					onlineMultiplayerGame.newGame();
					onlineMultiplayerGame.setWaiting(false);
				}
			}
		};
	}
	
	private Listener generatePlayGameListener() {
		return new Listener() {
			public void received(Connection connection, Object object) {
					if (!onlineMultiplayerGame.isWaiting()) {
						if (object instanceof UpdateTetrominoResponse) {
							UpdateTetrominoResponse updateTetrominoResponse = (UpdateTetrominoResponse) object;
							onlineMultiplayerGame.getOpponent().setActiveTetromino(updateTetrominoResponse.getUpdatedTetromino());
						} else if (object instanceof UpdateBoardResponse) {
							UpdateBoardResponse updateBoardResponse = (UpdateBoardResponse) object;
							onlineMultiplayerGame.getOpponent().setBoard(updateBoardResponse.getBoard());
						} else if (object instanceof UpdatePlayerStatsResponse) {
							UpdatePlayerStatsResponse updatePlayerStatsResponse = (UpdatePlayerStatsResponse) object;
							onlineMultiplayerGame.getOpponent().setLevel(updatePlayerStatsResponse.getLevel());
							onlineMultiplayerGame.getOpponent().setTotalLinesCleared(updatePlayerStatsResponse.getTotalLinesCleared());
							onlineMultiplayerGame.getOpponent().setScore(updatePlayerStatsResponse.getScore());
						} else if (object instanceof GameOverResponse) {							
							onlineMultiplayerGame.setWaiting(true);
							JavaTetrisController.getInstance().setState(JavaTetrisControllerState.MAINMENU);
							
							GameOverResponse gameOverResponse = (GameOverResponse) object;
							if (gameOverResponse.getWinner() == 'W') {
								JOptionPane.showMessageDialog(null, "You win!");
							} else {
								JOptionPane.showMessageDialog(null, "You lose!");
							}
							
							disconnect();
						}
					}
			}
		};
	}
	
	private Listener generateDisconnectedListener() {
		return new Listener() {
			public void disconnected(Connection connection) {
				JOptionPane.showMessageDialog(null, "Disconnected from server!");
				JavaTetrisController.getInstance().setState(JavaTetrisControllerState.MAINMENU);
			}
		};
	}
	
}

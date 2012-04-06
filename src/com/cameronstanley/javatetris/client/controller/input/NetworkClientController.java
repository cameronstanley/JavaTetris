package com.cameronstanley.javatetris.client.controller.input;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.cameronstanley.javatetris.client.model.OnlineMultiplayerGame;
import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.messages.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkClientController {

	private Client client;
	private Listener registerListener;
	private Listener loginListener;
	private Listener startNewGameListener;
	private Listener playGameListener;
	private OnlineMultiplayerGame onlineMultiplayerGame;
	
	public NetworkClientController(OnlineMultiplayerGame onlineMultiplayerGame) {
		this.onlineMultiplayerGame = onlineMultiplayerGame;
		
		client = new Client();
		client.start();
		Network.registerClasses(client);
		
		registerListener = generateRegisterListener();
		loginListener = generateLoginListener();
		startNewGameListener = generateStartNewGameListener();
		playGameListener = generatePlayGameListener();
		
		client.addListener(registerListener);
		client.addListener(loginListener);
		client.addListener(startNewGameListener);
		client.addListener(playGameListener);
	}
	
	public void connect() {
		String host = (String) JOptionPane.showInputDialog(null, "Connect to host: ", "Connect", JOptionPane.PLAIN_MESSAGE, null, null, "localhost");
		
		try {
			client.connect(5000, host, Network.PORT);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Error connecting to " + host + "\nError: " + ex.getMessage(), "Connection Error", JOptionPane.PLAIN_MESSAGE);
		}
		
		login();
	}
	
	public void disconnect() {
		onlineMultiplayerGame.setWaiting(true);
	}
	
	public void register() {
		String desiredUsername = (String) JOptionPane.showInputDialog(null, "Desired Username: ", "Register on server", JOptionPane.QUESTION_MESSAGE, null, null, "username");
		String desiredPassword = (String) JOptionPane.showInputDialog(null, "Desired Password: ", "Register on server", JOptionPane.QUESTION_MESSAGE, null, null, "password");
		
		client.addListener(registerListener);
		
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setDesiredUsername(desiredUsername);
		registerRequest.setDesiredPassword(desiredPassword);
		
		client.sendTCP(registerRequest);	
	}
	
	public void login() {
		String username = (String) JOptionPane.showInputDialog(null, "Username: ", "Login to server", JOptionPane.QUESTION_MESSAGE, null, null, "username");
		String password = (String) JOptionPane.showInputDialog(null, "Password: ", "Login to server", JOptionPane.QUESTION_MESSAGE, null, null, "password");
		
		client.addListener(loginListener);
		
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
						System.out.println("Successfully logged in");
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
					System.out.println("Startnewgameresponse received");
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
							System.out.println("update tetromino received");
							UpdateTetrominoResponse updateTetrominoResponse = (UpdateTetrominoResponse) object;
							onlineMultiplayerGame.getOpponent().setActiveTetromino(updateTetrominoResponse.getUpdatedTetromino());
							System.out.println("type: " + updateTetrominoResponse.getUpdatedTetromino().getType());
						} else if (object instanceof UpdateBoardResponse) {
							UpdateBoardResponse updateBoardResponse = (UpdateBoardResponse) object;
							onlineMultiplayerGame.getOpponent().setBoard(updateBoardResponse.getBoard());
						} else if (object instanceof UpdatePlayerStatsResponse) {
							UpdatePlayerStatsResponse updatePlayerStatsResponse = (UpdatePlayerStatsResponse) object;
						}
					}
			}
		};
	}
	
}

package com.cameronstanley.javatetris.server;

import java.io.IOException;

import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.NewGameRequest;
import com.cameronstanley.javatetris.network.PlayerConnection;
import com.cameronstanley.javatetris.network.WaitResponse;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class JavaTetrisServer {

	private Server server;
	
	public JavaTetrisServer() throws IOException {
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};

		Network.registerClasses(server);
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				PlayerConnection playerConnection = (PlayerConnection) connection;
				if (object instanceof NewGameRequest) {
					connection.sendTCP(new WaitResponse());
				}
			}
		});
		
		server.bind(54555);
		server.start();
	}
	
	public static void main(String[] args) throws IOException {
		JavaTetrisServer javaTetrisServer = new JavaTetrisServer();
	}
}

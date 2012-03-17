package com.cameronstanley.javatetris.server;

import java.io.IOException;

import com.cameronstanley.javatetris.client.model.Board;
import com.cameronstanley.javatetris.client.model.Tetromino;
import com.esotericsoftware.kryo.Kryo;
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

		Kryo kryo = server.getKryo();
		kryo.register(Tetromino.class);
		kryo.register(Board.class);
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				PlayerConnection playerConnection = (PlayerConnection) connection;
			}
		});
		
		server.bind(54555);
		server.start();
	}
	
	public static void main(String[] args) throws IOException {
		JavaTetrisServer javaTetrisServer = new JavaTetrisServer();
	}
}

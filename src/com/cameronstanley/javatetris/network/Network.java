package com.cameronstanley.javatetris.network;

import com.cameronstanley.javatetris.client.model.Board;
import com.cameronstanley.javatetris.client.model.Tetromino;
import com.cameronstanley.javatetris.client.model.TetrominoType;
import com.cameronstanley.javatetris.network.messages.*;
import com.cameronstanley.javatetris.server.User;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

	public static final int PORT = 54555;
	
	public static void registerClasses(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(RegisterRequest.class);
		kryo.register(RegisterResponse.class);
		kryo.register(LoginRequest.class);
		kryo.register(LoginResponse.class);
		kryo.register(StartNewGameResponse.class);
		kryo.register(UpdateTetrominoRequest.class);
		kryo.register(UpdateTetrominoResponse.class);
		kryo.register(UpdateBoardRequest.class);
		kryo.register(UpdateBoardResponse.class);
		kryo.register(Tetromino.class);
		kryo.register(TetrominoType.class);
		kryo.register(TetrominoType[][].class);
		kryo.register(Board.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(User.class);
	}
	
}

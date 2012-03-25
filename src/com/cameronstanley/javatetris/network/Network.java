package com.cameronstanley.javatetris.network;

import com.cameronstanley.javatetris.network.messages.*;
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
		kryo.register(WaitingPlayersRequest.class);
		kryo.register(WaitingPlayersResponse.class);
		kryo.register(StartNewGameRequest.class);
		kryo.register(StartNewGameResponse.class);
		kryo.register(UpdateTetrominoRequest.class);
		kryo.register(UpdateTetrominoResponse.class);
		kryo.register(UpdateBoardRequest.class);
		kryo.register(UpdateBoardResponse.class);
	}
	
}

package com.cameronstanley.javatetris.server;

import java.io.IOException;

import com.cameronstanley.javatetris.network.Network;
import com.cameronstanley.javatetris.network.messages.RegisterRequest;
import com.esotericsoftware.kryonet.Client;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.start();
		Network.registerClasses(client);
		client.connect(5000, "127.0.0.1", Network.PORT);
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setDesiredUsername("derp");
		registerRequest.setDesiredPassword("herp");
		client.sendTCP(registerRequest);
	}
	
}

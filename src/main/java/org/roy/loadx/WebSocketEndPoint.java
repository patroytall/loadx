package org.roy.loadx;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class WebSocketEndPoint extends Endpoint {
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			public void onMessage(String text) {
				System.out.println(text);
			}
		});
	}
}
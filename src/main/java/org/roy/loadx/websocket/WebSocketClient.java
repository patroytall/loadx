package org.roy.loadx.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.http.cookie.Cookie;

public class WebSocketClient {
	private Session session;

	public WebSocketClient(String hostPortPath, List<Cookie> cookies) {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();

		// TODO: check with Fiddler cookie handling
//		ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
//			    .configurator(new ClientEndpointConfig.Configurator() {
//			        @Override
//			        public void beforeRequest(Map<String, List<String>> headers) {
//			            super.beforeRequest(headers);
//			            List<String> cookieList = new ArrayList<>();
//			            for (Cookie cookie : cookies) {
////			            	cookieList.add(cookie.getName() + "=" + cookie.getValue());
//			            	System.out.println(cookie.getName() + "=" + cookie.getValue());
//			            }
//			            headers.put("Cookie", cookieList);
//			        }
//			    }).build();
		
		String uri = "ws://" + hostPortPath;
		try {
			session = container.connectToServer(new WebSocketEndPoint(), URI.create(uri));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

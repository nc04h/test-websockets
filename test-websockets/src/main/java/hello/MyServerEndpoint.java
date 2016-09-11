package hello;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/hello")
public class MyServerEndpoint {

	@OnMessage
	public String onMessage(String message, Session session) {
		System.out.println("onMessage message: " + message);
		System.out.println("onMessage session:" + session);
		return message;
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		System.out.println("onOpen session: " + session);
		session.getBasicRemote().sendText("onOpen");
	}

	@OnError
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("onClose session: " + session);
	}
}

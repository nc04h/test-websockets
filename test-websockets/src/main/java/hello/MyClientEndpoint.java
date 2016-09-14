package hello;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ClientEndpoint
public class MyClientEndpoint {

	private static Log log = LogFactory.getLog(MyClientEndpoint.class);

	@OnOpen
	public void onOpen(Session session) {
		log.debug("onOpen " + session);
	}

	@OnMessage
	public void onMessage(String message) {
		log.debug(String.format("%s %s", "Received message: ", message));
	}
}

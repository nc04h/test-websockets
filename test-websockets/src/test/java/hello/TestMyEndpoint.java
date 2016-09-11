package hello;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.junit.Test;


public class TestMyEndpoint {

	@Test
	public void test() throws Exception {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(MyClientEndpoint.class, 
				new URI(
						"ws://localhost:8080/hello"
						//"ws://echo.websocket.org"
						));
	}

}

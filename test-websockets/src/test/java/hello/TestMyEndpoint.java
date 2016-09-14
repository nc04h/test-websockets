package hello;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Test;


public class TestMyEndpoint {

	public static void main(String[] args) {
		Thread t1 = new Task("1");
		Thread t2 = new Task("2");
		t1.start();
		t2.start();
	
	}
	
	private static class Task extends Thread {
		
		String param;
		
		public Task(String param) {
			this.param = param;
		}

		@Override
		public void run() {
			try {
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				//container.setDefaultMaxSessionIdleTimeout(60000);
				Session s = container.connectToServer(MyClientEndpoint.class, 
						new URI(
								"ws://localhost:8080/hello/" + param
								//"ws://echo.websocket.org"
								));
				System.out.println(s.getOpenSessions());
				while (true) {
					Thread.sleep(10000);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
}

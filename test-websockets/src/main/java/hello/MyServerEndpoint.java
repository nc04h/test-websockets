package hello;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.eclipse.jetty.websocket.jsr356.server.PathParamServerEndpointConfig;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyServerEndpoint extends Endpoint implements MessageListener {

	@Autowired
	private ConnectionFactory connectionFactory;

	private RabbitAdmin rabbitAdmin;
	private Session session;
	private String queueName;
	SimpleMessageListenerContainer container;

	@PostConstruct
	public void init() {
		System.out.println("init");
		rabbitAdmin = new RabbitAdmin(connectionFactory);
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		super.onClose(session, closeReason);
		System.out.println("onClose " + session + " " + closeReason);
		this.session = null;
		container.stop();
		container.destroy();
		rabbitAdmin.deleteQueue(queueName);
	}

	@Override
	public void onError(Session session, Throwable thr) {
		super.onError(session, thr);
		System.out.println("onError " + session + " " + thr);
		this.session = null;
		container.stop();
		container.destroy();

	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println("onOpen " + session);
		System.out.println(config);
		PathParamServerEndpointConfig conf = (PathParamServerEndpointConfig) config;
		String param = conf.getPathParamMap().get("param");
		System.out.println(connectionFactory);
		this.session = session;
		queueName = session.getId();
		System.out.println(queueName);
		Queue queue = new Queue(queueName, false, true, true);
		TopicExchange topic = new TopicExchange("spring-boot-exchange");
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareExchange(topic);
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topic).with("test." + param));

		container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(this);
		container.start();

	}

	@Override
	public void onMessage(Message message) {
		System.out.println("onMessage " + message);
		if (session != null) {
			try {
				session.getBasicRemote().sendText(message.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}


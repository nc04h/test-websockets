package hello;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServerEndpointRegistration;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@Configuration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ServerEndpointExporter endpointExporter() {
		return new ServerEndpointExporter();
	}
	/*
	@Bean
	public MyServerEndpoint echoEndpoint(ConnectionFactory connectionFactory) {
		return new MyServerEndpoint(connectionFactory);
	}
	 */

	@Bean
	public ServerEndpointRegistration myServerEndpoint() {
		return new ServerEndpointRegistration("/hello/{param}", MyServerEndpoint.class);
	}
}

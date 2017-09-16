package br.com.amqp.cloud.receiver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Recv {

	public static void main(String[] args) throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {

		String uri = System.getenv("CLOUDAMQP_URL");
		if (uri == null)
			uri = "amqp://gfwtijxu:TBhL908few0bSN0u-x3Km0m4kiamipwa@rhino.rmq.cloudamqp.com/gfwtijxu";

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri(uri);
		// Recommended settings
		factory.setRequestedHeartbeat(30);
		factory.setConnectionTimeout(30000);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		String queue = "hello"; // queue name
		while (true) {
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					System.out.println(" [x] Received '" + message + "'");
				}
			};
			channel.basicConsume(queue, true, consumer);
		}

	}
}

package br.com.amqp.cloud.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	public static void main(String[] args) throws Exception {
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
		boolean durable = true; // durable - RabbitMQ will never lose the queue if a crash occurs
		boolean exclusive = false; // exclusive - if queue only will be used by one connection
		boolean autoDelete = false; // autodelete - queue is deleted when last consumer unsubscribes

		channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
		String message = "Hello CloudAMQP!";

		String exchangeName = "";
		String routingKey = "hello";
		channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
	}
}

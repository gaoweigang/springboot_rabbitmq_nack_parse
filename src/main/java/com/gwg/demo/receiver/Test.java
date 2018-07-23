package com.gwg.demo.receiver;

import java.io.IOException;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

public class Test {
	
    public static String host = "192.168.1.106";
	public static int port = 5672;
	public static String username = "hbsit";
	public static String  password = "hbsit";
	public static String virtualHost = "/";
	public static String exchange=  "EXCHANGE_DIRECT_TEST" ;
	public static String  queue = "QUEUE_TEST";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		connectionFactory.setVirtualHost(virtualHost);
		
		Connection connection = connectionFactory.createConnection();
		Channel channel = connection.createChannel(false);
		for(int i = 0; i < 50 ; i ++){
			GetResponse response = channel.basicGet(queue, false);
			while(response == null){
				System.out.println("尝试从队列中获取消息");
			    Thread.sleep(2000);
				response = channel.basicGet(queue, false);
			}
			System.out.println("尝试重新放入队列");
		    Thread.sleep(2000);
			System.out.println(new String(response.getBody(), "UTF-8"));
			channel.basicNack(response.getEnvelope().getDeliveryTag(), false, true);
			System.out.println("game over ....");
		}
		
	}

}

package com.gwg.demo.receiver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.ShutdownSignalException;

//@Component
public class HelloReceiver {
	private static final Logger logger = LoggerFactory.getLogger(HelloReceiver.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

    public void process() throws IOException {
        
		logger.info("消费开始....,hello:{}");
		Connection connection = connectionFactory.createConnection();
		final Channel channel = connection.createChannel(false);
		/*channel.exchangeDeclare(exchange, "direct", true, false, null);
		channel.queueDeclare(queue, true, false, false, null);
		logger.info("queueBind ..........");
		channel.queueBind(queue, exchange, routing);*/

		// 第二个参数设为true为自动应答，false为手动ack
		GetResponse response = channel.basicGet(queue, false);
		if(response != null){
			logger.info("消息：{}",new String(response.getBody(), "UTF-8"));
			channel.basicNack(response.getEnvelope().getDeliveryTag(), false, true);
	
		    
		}
		logger.info("game over ....");
	
    }
	
	public static void main(String[] args) {
		
	}
 
}

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

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

//@Component
public class HelloReceiver_back {
	private static final Logger logger = LoggerFactory.getLogger(HelloReceiver_back.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

    public void process(String hello) throws IOException {
        
		logger.info("消费开始....,hello:{}", hello);
		Connection connection = connectionFactory.createConnection();
		final Channel channel = connection.createChannel(false);
		/*channel.exchangeDeclare(exchange, "direct", true, false, null);
		channel.queueDeclare(queue, true, false, false, null);
		logger.info("queueBind ..........");
		channel.queueBind(queue, exchange, routing);*/

		// 第二个参数设为true为自动应答，false为手动ack
		channel.basicConsume(queue, false, new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {

				try {

					Thread.sleep(10000);

					logger.info(new String(body, "UTF-8"));

					// 模拟异常

					int i = 1 / 0;

					// 手动ack

					// channel.basicAck(envelope.getDeliveryTag(), false);

				} catch (ShutdownSignalException e) {
					
					logger.info("抛弃此条消息1....");
					// 抛弃此条消息
					channel.basicNack(envelope.getDeliveryTag(), false, true);
					e.printStackTrace();

				} catch(Exception e){
					logger.info("抛弃此条消息2....");
					channel.basicNack(envelope.getDeliveryTag(), false, true);
					e.printStackTrace();

				} finally {
					// 重新放入队列
					//channel.basicNack(envelope.getDeliveryTag(), false, true);
					logger.info("抛弃此条消息3....");
					// 抛弃此条消息
					//channel.basicNack(envelope.getDeliveryTag(), false, true);
				}

			}

		});

	
    }
 
}

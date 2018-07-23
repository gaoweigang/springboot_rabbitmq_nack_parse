package com.gwg.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	private static Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

	// 测试 调试环境
	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.port}")
	private Integer port;

	@Value("${spring.rabbitmq.exchange}")
	private String exchangeName;
	
	@Value("${spring.rabbitmq.queue}")
	private String queueName;// 同时作为rountingkey
	
	@Value("${spring.rabbitmq.virtual-host}")
	private String virtualHost;// 同时作为rountingkey
	

	@Bean
	public AmqpAdmin mqAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		connectionFactory.setVirtualHost(virtualHost);
		return connectionFactory;
	}
	
	// 必须要生成bean，否则如果不会自动生成该EXCHANGE
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchangeName, true, true);
	}

	/**
	 *  声明队列: 队列名称， 持久性标志
	 */
	@Bean
	public Queue Queue() {
		return new Queue(queueName, true);
	}

	/**
	 * 将交换器 与  队列 进行绑定，并指定队列名称
	 */
	@Bean
	Binding binding() {
		return BindingBuilder.bind(Queue()).to(exchange()).with(queueName);
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

}

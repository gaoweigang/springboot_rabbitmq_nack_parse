package com.gwg.demo.sender;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {
	
	@Value("${spring.rabbitmq.exchange}")
	private String exchangeName;
	@Value("${spring.rabbitmq.queue}")
	private String queueName;// 同时作为rountingkey

	 
    @Autowired
    @Qualifier("${spring.rabbitmq.bean}")
    private AmqpTemplate rabbitTemplate;
 
    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(exchangeName, queueName, context);
    }
 
}

package com.gwg.demo.sender;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloSender {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloSender.class);

	@Value("${spring.rabbitmq.exchange}")
	private String exchangeName;
	@Value("${spring.rabbitmq.queue}")
	private String queueName;// 同时作为rountingkey

	 
    @Autowired
    private AmqpTemplate rabbitTemplate;
 
    public void send() {
    	for(int i = 0; i< 4; i++){
    		 String context = "hello gaoweigang" + i +"end...." ;
    		 logger.info("Sender : " + context);
    	     this.rabbitTemplate.convertAndSend(exchangeName, queueName, context);
    	}
    }
 
}

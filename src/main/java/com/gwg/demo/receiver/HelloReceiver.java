package com.gwg.demo.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloReceiver {
	 
	@RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
 
}

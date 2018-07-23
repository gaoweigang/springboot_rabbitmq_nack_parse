package com.gwg.demo.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gwg.demo.Application;
import com.gwg.demo.receiver.HelloReceiver;
import com.gwg.demo.sender.HelloSender;

/**
 * debug mapper
 * 
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class RabbitMQTest {

	@Autowired
	private HelloSender helloSender;
	@Autowired 
	private HelloReceiver helloReceiver;

	@Test
	public void hello() throws Exception {
		helloSender.send();
	}
	
	@Test
	public void receiver() throws IOException, InterruptedException{
		for(int i = 0 ; i< 50; i++){
			Thread.sleep(2000);
			helloReceiver.process();

		}
	}

}

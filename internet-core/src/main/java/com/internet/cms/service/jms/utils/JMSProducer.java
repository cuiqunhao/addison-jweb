package com.internet.cms.service.jms.utils;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

import com.internet.cms.service.jms.JMSMessageCreator;


//@Service
public class JMSProducer implements IJMSProducerService {

	//@Resource
	private JmsTemplate jmsTemplate;
	
	//@Resource
	private ActiveMQQueue defaultResponseQueue;
	
	@Override
	public void sendMessage(Destination destination,String message) {
		// TODO Auto-generated method stub
		
		System.out.println("**********producer发送一个消息。。。。***********");
		
		JMSMessageCreator myMessageCreator = new JMSMessageCreator(message,defaultResponseQueue);
		jmsTemplate.send(destination, myMessageCreator);
		
	}

}

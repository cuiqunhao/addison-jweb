package com.internet.cms.service.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.SessionAwareMessageListener;


public class JMSConsumerSessionAwareMessageListener implements SessionAwareMessageListener<TextMessage> {
	
	@Override
	public void onMessage(TextMessage textMessage, Session session) throws JMSException {
		// TODO Auto-generated method stub
		System.out.println("SessionAware接收到一个消息……");
		System.out.println("消息是："+textMessage.getText()+"\n");
		System.out.println("**********接收之后，发送一个收到消息*************");
		
		ApplicationContext act =   new ClassPathXmlApplicationContext("applicationContext-jms.xml");//加载applicationContext内容
		Destination queueDestination = (Destination) act.getBean("queueDestination");
		
		MessageProducer producer = session.createProducer(queueDestination);   
        Message textMessage2 = session.createTextMessage("Producer，你好！我已收到你发送之消息，接下来我将……");   
        producer.send(textMessage2);  
		
	}


}

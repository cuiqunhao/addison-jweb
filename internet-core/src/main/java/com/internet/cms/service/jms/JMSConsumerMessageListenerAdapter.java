package com.internet.cms.service.jms;

public class JMSConsumerMessageListenerAdapter {
	
	public void receiveMessage(String message){
		System.out.println("消息配置器收到消息，消息是："+message);
	}
}

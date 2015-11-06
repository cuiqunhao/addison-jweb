package com.internet.cms.service.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JMSDefaultResponseQueueListener implements MessageListener  {

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("===========DefaultResponseQueueListener收到消息：=========");
		try {
			System.out.println("消息为："+((TextMessage)message).getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

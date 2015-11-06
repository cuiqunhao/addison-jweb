package com.internet.cms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * message receiver
 * @description <p></p>
 * @author quzishen
 * @project NormandyPositionII
 * @class MessageReceiver.java
 * @version 1.0
 * @time 2011-1-11
 */
public class MessageReceiver implements MessageListener {
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message m) {
		System.out.println("[receive message]");
		
		ObjectMessage om = (ObjectMessage) m;
		try {
			String key1 = om.getStringProperty("key1");
			
			System.out.println(key1);
			
			System.out.println("model:"+om.getJMSDeliveryMode());
			System.out.println("destination:"+om.getJMSDestination());
			System.out.println("type:"+om.getJMSType());
			System.out.println("messageId:"+om.getJMSMessageID());
			System.out.println("time:"+om.getJMSTimestamp());
			System.out.println("expiredTime:"+om.getJMSExpiration());
			System.out.println("priority:"+om.getJMSPriority());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

package com.internet.cms;

import org.springframework.jms.core.JmsTemplate;

/**
 * message sender
 * 
 * @description <p>
 *              </p>
 * @author quzishen
 * @project NormandyPositionII
 * @class MessageSender.java
 * @version 1.0
 * @time 2011-1-11
 */
public class MessageSender {
	// ~~~ jmsTemplate
	public JmsTemplate jmsTemplate;

	/**
	 * send message
	 */
	public void sendMessage() {
		jmsTemplate.convertAndSend("hello jms!");
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
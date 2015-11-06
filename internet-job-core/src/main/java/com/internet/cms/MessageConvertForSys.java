package com.internet.cms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * message converter
 * 
 * @description <p>
 *              </p>
 * @author quzishen
 * @project NormandyPositionII
 * @class MessageConvertForSys.java
 * @version 1.0
 * @time 2011-1-11
 */
public class MessageConvertForSys implements MessageConverter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#toMessage(
	 * java.lang.Object, javax.jms.Session)
	 */
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
		System.out.println("[toMessage]");
		ObjectMessage objectMessage = session.createObjectMessage();

		objectMessage.setJMSExpiration(1000);
		objectMessage.setStringProperty("key1", object + "_add");

		return objectMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jms.support.converter.MessageConverter#fromMessage
	 * (javax.jms.Message)
	 */
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		System.out.println("[fromMessage]");
		ObjectMessage objectMessage = (ObjectMessage) message;

		return objectMessage.getObjectProperty("key1");
	}
}

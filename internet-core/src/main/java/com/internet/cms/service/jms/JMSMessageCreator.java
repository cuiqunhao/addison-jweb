package com.internet.cms.service.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class JMSMessageCreator implements MessageCreator {

	private Object messageInformation;

	private Destination responseDestination;

	public JMSMessageCreator() {

	}

	public JMSMessageCreator(String textMessage) {
		this.messageInformation = textMessage;
	}

	public JMSMessageCreator(String textMessage, Destination responseDestination) {
		this.messageInformation = textMessage;
		this.responseDestination = responseDestination;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		// TODO Auto-generated method stub
		Message message = session
				.createTextMessage((String) messageInformation);
		((TextMessage) message).setJMSReplyTo(responseDestination);
		return message;
	}

	public Object getMessageInformation() {
		return messageInformation;
	}

	public void setMessageInformation(Object messageInformation) {
		this.messageInformation = messageInformation;
	}

	public Destination getResponseDestination() {
		return responseDestination;
	}

	public void setResponseDestination(Destination responseDestination) {
		this.responseDestination = responseDestination;
	}

}

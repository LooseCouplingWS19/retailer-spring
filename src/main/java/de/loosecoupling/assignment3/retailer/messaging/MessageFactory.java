package de.loosecoupling.assignment3.retailer.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.StreamMessage;
import javax.jms.TopicSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {
	
	@Autowired
	TopicSession activeMQSession;

	public StreamMessage generateRetailerMessage(Destination destination, String correlationId) throws JMSException {
		StreamMessage message = activeMQSession.createStreamMessage();
		message.setJMSDestination(destination);
		message.setJMSCorrelationID(correlationId);
		return message;
		
	}
	
	public StreamMessage fillRetailerMessage(String productName, Integer amount, StreamMessage message) throws JMSException {
		message.writeString(productName);
		message.writeInt(amount);
		return message;
	}
}

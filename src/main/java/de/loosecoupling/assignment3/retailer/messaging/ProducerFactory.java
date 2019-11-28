package de.loosecoupling.assignment3.retailer.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TopicSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerFactory {
	
	@Autowired
	TopicSession activeMQSession;

	public MessageProducer createProducer(Destination destination) throws JMSException {
		return activeMQSession.createProducer(destination);
	}
}

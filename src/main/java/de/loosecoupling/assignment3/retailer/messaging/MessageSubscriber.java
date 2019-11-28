package de.loosecoupling.assignment3.retailer.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.StreamMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.loosecoupling.assignment3.retailer.services.ProcessingService;

@Component
public class MessageSubscriber implements MessageListener {

	private static final Logger LOG = LoggerFactory.getLogger(MessageSubscriber.class);
	
	@Autowired
	ProcessingService processingService;

	@Override
	public void onMessage(Message message) {
		StreamMessage streamMessage = (StreamMessage) message;
		LOG.info("Got a StreamMessage from 'HotDeals'");
		try {
			processingService.processMessage(streamMessage);
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}		
	}
}

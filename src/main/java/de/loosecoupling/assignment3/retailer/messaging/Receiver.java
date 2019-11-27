package de.loosecoupling.assignment3.retailer.messaging;

import javax.jms.JMSException;
import javax.jms.StreamMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import de.loosecoupling.assignment3.retailer.services.ProcessingService;

@Component
public class Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	ProcessingService processingService;

	@JmsListener(destination = "HotDeals", containerFactory = "hotDeals")
	public void receiveMessage(StreamMessage message) {
		LOG.info("Got a StreamMessage from 'HotDeals'");
		try {
			processingService.processMessage(message);
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}
	}

}

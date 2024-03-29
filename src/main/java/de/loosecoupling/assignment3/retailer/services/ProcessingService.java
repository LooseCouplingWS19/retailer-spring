package de.loosecoupling.assignment3.retailer.services;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.StreamMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.loosecoupling.assignment3.retailer.messaging.MessageFactory;
import de.loosecoupling.assignment3.retailer.messaging.ProducerFactory;

@Service
public class ProcessingService {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessingService.class);

	@Autowired
	MessageFactory messageFactory;
	@Autowired
	ProducerFactory publisherFactory;

	public void processMessage(StreamMessage message) throws JMSException {
		LOG.info("------------------------------");
		LOG.info("Got new offer with ID: " + message.getJMSMessageID());

		String productName = message.readString();
		Float newPrice = message.readFloat();
		Float oldPrice = message.readFloat();
		LOG.info("Product Name: " + productName);
		LOG.info("New Price: " + newPrice);
		LOG.info("Old Price: " + oldPrice);

		if (newPrice / oldPrice <= 0.9) {
            LOG.info("-> Good price! Order 1000 items!");
            StreamMessage replyMessage = messageFactory.generateRetailerMessage(message.getJMSReplyTo(), message.getJMSMessageID());
            replyMessage = messageFactory.fillRetailerMessage(productName, 1000, replyMessage);
            MessageProducer messageProducer = publisherFactory.createProducer(message.getJMSReplyTo());
            messageProducer.send(replyMessage);
		}
	}
}

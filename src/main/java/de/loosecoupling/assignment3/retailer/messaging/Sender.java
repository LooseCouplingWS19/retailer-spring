package de.loosecoupling.assignment3.retailer.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Sender {

	@Autowired
	private JmsTemplate myJmsTemplate;

	public void sendMessage(String productName, Integer amount, String correlationId) {
		myJmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				StreamMessage streamMessage = session.createStreamMessage();
				streamMessage.writeString(productName);
				streamMessage.writeInt(amount);
				streamMessage.setJMSCorrelationID(correlationId);
				return streamMessage;
			}
		});
	}
}

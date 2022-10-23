package br.com.rafaelchagasb.jms.ejb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

@Stateless
public class MessageService {

	@Resource(mappedName = "java:jboss/jms/QueueConnectionFactory")
	private ConnectionFactory factory;

	@Resource(mappedName = "java:jboss/jms/DEV.QUEUE.1")
	private Queue target;

	public void sendMessage(String message) throws JMSException {
		try(Connection con = factory.createConnection()){
			Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(target);
			producer.send(session.createTextMessage(message));
			System.out.println("Message sended");
		}
	}
}

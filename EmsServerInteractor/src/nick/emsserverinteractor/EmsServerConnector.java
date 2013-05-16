package nick.emsserverinteractor;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

public class EmsServerConnector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 ConnectionFactory factory = new com.tibco.tibjms.TibjmsConnectionFactory("tcp://esb-ems-wov01:7220");
		 
		 try{
		 
         	Connection connection = factory.createConnection("admin","admin");
         	
         	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         	
         	Queue queue = session.createQueue("ICC.DEV.GiGiTest.1.queue");
         	
         	QueueBrowser queueBrowser = session.createBrowser(queue);
         	
         	Enumeration messages = queueBrowser.getEnumeration();
         	
         	
         	System.out.println(messages.hasMoreElements());
         	TextMessage message =  (TextMessage) messages.nextElement();
         	
         	System.out.println("JMS type is: " + message.getJMSType());
         	System.out.println("JMS timestamp is: " + message.getJMSTimestamp());
         	System.out.println("JMS messageID is: " + message.getJMSMessageID());
         	System.out.println(message.getText());
         	
         	
         	
		 }catch(JMSException jmsException){
			 System.out.print("Problem with EMS connection");
			 jmsException.printStackTrace();
			 
		 }

	}

}

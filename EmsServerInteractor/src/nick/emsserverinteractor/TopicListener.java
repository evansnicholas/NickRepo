package nick.emsserverinteractor;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.tibco.tibjms.TibjmsTopic;
import com.tibco.tibjms.TibjmsTopicConnectionFactory;

public class TopicListener {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int messageCount = 0;
		
		TibjmsTopicConnectionFactory topicConnectionFactory = new TibjmsTopicConnectionFactory("tcp://esb-ems-wov01:7220");
		
		try{	
			
			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection("admin", "admin");
			topicConnection.start();
			TibjmsTopic allQueuesMonitorTopic = new TibjmsTopic("$sys.monitor.Q.s.icc.adpebs.ebsgetproducts.1.queue");	
			TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(allQueuesMonitorTopic);
			
			while(true){
				
				Message message = topicSubscriber.receive();
				
				System.out.print("Message number "+ ++messageCount + " ");
				
				System.out.println(message.getJMSType());
				
			}
			
		}catch(JMSSecurityException jmsSecEx){
			
			System.out.print("Invalid username or password");
			
		}catch(JMSException jmsEx){
			
			System.out.print(jmsEx.getMessage());
			
		}
		
		
		
	}

}

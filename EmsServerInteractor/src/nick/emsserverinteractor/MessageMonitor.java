package nick.emsserverinteractor;

import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.tibco.tibjms.TibjmsTopic;
import com.tibco.tibjms.TibjmsTopicConnectionFactory;

public class MessageMonitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TibjmsTopicConnectionFactory topicConnectionFactory = new TibjmsTopicConnectionFactory("tcp://esb-ems-wov01:7220");
		
		try{	
			
			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection("admin", "admin");
			topicConnection.start();
			TibjmsTopic allQueuesMonitorTopic = new TibjmsTopic("$sys.monitor.Q.s.icc.*.*.*.*");	
			TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(allQueuesMonitorTopic);
			
			
			while(true){
				
				Message message = topicSubscriber.receive(); 
				Topic destinationQueue = (Topic) message.getJMSDestination();
				String queueName = destinationQueue.getTopicName();
				
				System.out.println("Message is being sent to queue: "+ queueName);
				
				
			}
			
		
		}catch(JMSSecurityException jmsSecEx){
				
				System.out.print("Invalid username or password");
				
		}catch(JMSException jmsEx){
				
				System.out.print(jmsEx.getMessage());
				
		}

	}

}

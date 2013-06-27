package nick.emsserverinteractor;

import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.tibco.tibjms.admin.TibjmsAdminException;

public class EmsAdmin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{
			TibjmsAdmin tibjmsAdmin = new TibjmsAdmin("tcp://esb-ems-wov01:7220", "admin", "admin");
			
			QueueInfo[] queues = tibjmsAdmin.getQueues("icc.*.*.*.*");
			
			System.out.println("Found :" + queues.length + " queues");
			
			for (QueueInfo queue : queues){
				
				System.out.println(queue.getName() + " Pending messages: " + queue.getPendingMessageCount() + " Receiver Count: " + queue.getConsumerCount());
				
			}
		
		}catch(TibjmsAdminException adminException){
			
			System.out.println("And error occured connecting to the ems server");
			adminException.getStackTrace();
			
		}

		
		
	}

}

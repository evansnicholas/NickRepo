import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ServiceConfigurations {

	Element rootElement;
	ArrayList<ServiceConfiguration> childrenElements = new ArrayList<ServiceConfiguration>();
	
	ServiceConfigurations(Document doc){
		
		this.rootElement = doc.createElementNS("http://www.ziggo.nl/Integration/ESB/Generic/Configuration/1.0", "cfg:serviceConfigurations");
		doc.appendChild(rootElement);
		
	}
	
	public Element getRootElement(){
		
		return rootElement;
		
	}
	
	public void addServiceConfiguration(ServiceConfiguration serviceConfiguration){
		childrenElements.add(serviceConfiguration);
		
	}
	
	public void addChildrenToRootElement(){
		
		for (ServiceConfiguration i : childrenElements){
			this.rootElement.appendChild(i.getServiceConfiguration());
		}
	}
	
}

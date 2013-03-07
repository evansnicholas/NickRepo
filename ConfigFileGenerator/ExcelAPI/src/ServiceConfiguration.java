import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ServiceConfiguration {

	Element serviceConfiguration;
	ArrayList<Logging> loggings = new ArrayList<Logging>();
	Element generateInternalHeader;
	
	
	ServiceConfiguration(String nameValue, String versionValue, Document doc, String generateInternalHeader){
		
		this.serviceConfiguration = doc.createElement("cfg:serviceConfiguration");
		Attr nameAttribute = doc.createAttribute("name");
		nameAttribute.setValue(nameValue);
		Attr versionAttribute = doc.createAttribute("version");
		versionAttribute.setValue(versionValue);
		serviceConfiguration.setAttributeNode(nameAttribute);
		serviceConfiguration.setAttributeNode(versionAttribute);
		
		this.generateInternalHeader = doc.createElement("cfg:generateInternalHeader");
		this.generateInternalHeader.appendChild(doc.createTextNode(generateInternalHeader));
	}
	

	public Element getServiceConfiguration(){
		
		return serviceConfiguration;
		
	}
	
	public void addLogging(Logging logging){
		loggings.add(logging);
	}
	
	public void addLoggingsToServiceConfiguration(){
		for (Logging i : loggings){
			this.serviceConfiguration.appendChild(i.getLogging());
		}
	}
		
	public void addGenerateInternalHeaderElement(){
		
		this.serviceConfiguration.appendChild(this.generateInternalHeader);
		
	}
	

	
}

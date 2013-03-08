import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ReadConfigFile {

	ArrayList<ArrayList> parsedServiceConfigurations = new ArrayList<ArrayList>();
	ArrayList<String> currentServiceConfigurationValues = new ArrayList<String>();
	String currentServiceConfigurationName;
	String currentServiceConfigurationVersion;
	boolean isGenerateInternalHeader;
	ArrayList<String> allGenerateInternalHeaderValues = new ArrayList<String>();
	
	public void readInConfigFile() {
		// TODO Auto-generated method stub
		
		try{
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			DefaultHandler handler = new DefaultHandler() {
				
				boolean isServiceConfiguration;
				
				
				
				public void startElement(String uri, String localName,String qName, 
		                Attributes attributes) throws SAXException {
					
						if (qName.equalsIgnoreCase("CFG:SERVICECONFIGURATION")){	
					
							currentServiceConfigurationName = attributes.getValue(0);
							currentServiceConfigurationVersion = attributes.getValue(1);
							

						}
					
						if (qName.equalsIgnoreCase("CFG:LOGGING")){
							
							
							String loggingTarget = attributes.getValue(0);
							currentServiceConfigurationValues.add(0, currentServiceConfigurationName);
							currentServiceConfigurationValues.add(1, currentServiceConfigurationVersion);
							currentServiceConfigurationValues.add(2, loggingTarget);
							
						}
						
						if (qName.equalsIgnoreCase("CFG:FILTER")){
							
							currentServiceConfigurationValues.add(attributes.getValue(0));
							currentServiceConfigurationValues.add(attributes.getValue(1));
							currentServiceConfigurationValues.add(attributes.getValue(2));
							
						}
						
						if (qName.equalsIgnoreCase("CFG:GENERATEINTERNALHEADER")){
							isGenerateInternalHeader = true;
							
						}
						
				}
				
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					
					if (qName.equalsIgnoreCase("CFG:LOGGING")){
						parsedServiceConfigurations.add(new ArrayList<String>());
						
						for (int i = 0; i < currentServiceConfigurationValues.size(); i++){
							parsedServiceConfigurations.get(parsedServiceConfigurations.size()-1).add(currentServiceConfigurationValues.get(i));
						}
						
						currentServiceConfigurationValues.clear();
					}
					
				}
				
				public void characters(char ch[], int start, int length) throws SAXException {
					
					if (isGenerateInternalHeader == true){
						
						allGenerateInternalHeaderValues.add(new String(ch, start, length));
						isGenerateInternalHeader = false;
					}
					
				}
				
			};
			
			saxParser.parse("C:\\users\\iggo\\desktop\\TestXMLFile.xml", handler);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void printAServiceConfiguration(){
		
		ArrayList<String> aServiceConfiguration = this.parsedServiceConfigurations.get(0);
		System.out.println("Length: "+this.parsedServiceConfigurations.get(0).size());
		
		for (int i=0; i < aServiceConfiguration.size(); i++){
			System.out.print(aServiceConfiguration.get(i).toString()+", ");
		}
		
	}

}

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Attribute;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLFileCreator {

	
	
	public void makeXMLFile(){
		try{
		
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
		    ServiceConfigurations sc = new ServiceConfigurations(doc);
			
			
			ServiceConfiguration serviceConfiguration = new ServiceConfiguration("as.EBSODS.Customer", "1", doc, "true");
			sc.addServiceConfiguration(serviceConfiguration);
			sc.addChildrenToRootElement();
			
			Logging logging = new Logging(doc, "local");
			serviceConfiguration.addLogging(logging);
			
			Logging logging2 = new Logging(doc, "clever");
			serviceConfiguration.addLogging(logging2);
			
			serviceConfiguration.addLoggingsToServiceConfiguration();
			
			Filter filter1 = new Filter(doc, "2", "INFO", "true");
			logging.addFilter(filter1);
			
			Filter filter2 = new Filter(doc, "3", "DEBUG", "true");
			logging.addFilter(filter2);
			
			logging.addFiltersToLogging();
			
			Filter filter3 = new Filter(doc, "2", "INFO", "true");
			logging2.addFilter(filter3);
			
			Filter filter4 = new Filter(doc, "3", "DEBUG", "true");
			logging2.addFilter(filter4);
			
			logging2.addFiltersToLogging();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\iggo\\Desktop\\TestConfigFile.xml"));
			
			transformer.transform(source, result);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}

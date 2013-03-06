import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

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

	ArrayList<ArrayList> rows = new ArrayList<ArrayList>();
	
	public void makeXMLFile(){
		try{
			
			Stack serviceConfigurationsStack = new Stack();
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			ServiceConfigurations sc = new ServiceConfigurations(doc);
			
			for (ArrayList row : rows){
				
				ServiceConfiguration currentServiceConfiguration;
				
				System.out.println("Number of elements in row: "+row.size());
				
				String serviceName = row.get(1).toString();
				String service = row.get(1).toString();
				String isInternalHeader = row.get(2).toString();
				String version = row.get(3).toString();
				String target = row.get(4).toString();
				String Severity_INFO = row.get(5).toString();
				String INFO_Details = row.get(6).toString();
				String Severity_DEBUG = row.get(7).toString();
				String DEBUG_Details = row.get(8).toString();
				String Severity_ERROR = row.get(9).toString();
				String ERROR_Details = row.get(10).toString();
				String Severity_FATAL = row.get(11).toString();
				String FATAL_Details = row.get(12).toString();
				String Severity_TRACE = row.get(13).toString();
				String TRACE_Details = row.get(14).toString();
				String Severity_WARNING = row.get(15).toString();
				String WARNING_Details = row.get(16).toString();
				
				if (service.equals("REPEATED_SERVICE")){
					currentServiceConfiguration = (ServiceConfiguration) serviceConfigurationsStack.peek();
				}
				
				else {
					
					serviceConfigurationsStack.push(new ServiceConfiguration(service, version.toString(), doc, isInternalHeader));
					currentServiceConfiguration = (ServiceConfiguration) serviceConfigurationsStack.peek();	
					sc.addServiceConfiguration(currentServiceConfiguration);
					sc.addChildrenToRootElement();
				}
				/*ServiceConfiguration serviceConfiguration = new ServiceConfiguration(service, version.toString(), doc, isInternalHeader);
				sc.addServiceConfiguration(serviceConfiguration);
				sc.addChildrenToRootElement();*/
				
				Logging logging = new Logging(doc, target);
				currentServiceConfiguration.addLogging(logging);
				
				currentServiceConfiguration.addLoggingsToServiceConfiguration();
				
				Filter filterINFO = new Filter(doc, INFO_Details, "INFO", Severity_INFO);
				logging.addFilter(filterINFO);
				
				Filter filterDEBUG = new Filter(doc, DEBUG_Details, "DEBUG", Severity_DEBUG);
				logging.addFilter(filterDEBUG);
				
				Filter filterFATAL = new Filter(doc, FATAL_Details, "FATAL", Severity_FATAL);
				logging.addFilter(filterFATAL);
				
				Filter filterERROR = new Filter(doc, ERROR_Details, "ERROR", Severity_ERROR);
				logging.addFilter(filterERROR);
				
				Filter filterWARNING = new Filter(doc, WARNING_Details, "WARNING", Severity_WARNING);
				logging.addFilter(filterWARNING);
				
				Filter filterTRACE = new Filter(doc,TRACE_Details, "TRACE", Severity_TRACE);
				logging.addFilter(filterTRACE);
				
				logging.addFiltersToLogging();
				
			}	
				
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\iggo\\Desktop\\TestConfigFile.xml"));
				
			transformer.transform(source, result);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addRow(ArrayList rowContent){
		
		rows.add(rowContent);
		
	}
	
	public void printSizeOfRows(){
		
		System.out.println("Number of rows: "+rows.size());
		
	}
	
	public void removeLast4ElementsFromRowsAndFirstRow(){
		
		int rowsInitialSize = rows.size();
		
		for (int i=1; i<5; i++){
			rows.remove(rowsInitialSize-i);
		}
		
		rows.remove(0);
	}
	
	public ArrayList getRows(){
		return rows;
	}
	
	
	
}

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;



public class XMLFileCreator {

	ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
	String configFileLocation;
	
	XMLFileCreator(String configFileLocation){
		this.configFileLocation = configFileLocation;
	}
	
	int currentRowIndex;
	
	public void makeXMLFile(){
		try{
			
			Stack<ServiceConfiguration> serviceConfigurationsStack = new Stack<ServiceConfiguration>();
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			ServiceConfigurations sc = new ServiceConfigurations(doc);
			
			this.initializeCurrentRowIndex();
			
			for (ArrayList<String> row : rows){
				
				ServiceConfiguration currentServiceConfiguration;
				
				//System.out.println("Number of elements in row: "+row.size());
				
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
					
				}else {
					
					
					if (serviceConfigurationsStack.empty() == false){
					//Add InternalHeaderElement to last serviceConfiguration before creating new one.
					currentServiceConfiguration = (ServiceConfiguration) serviceConfigurationsStack.peek();
					currentServiceConfiguration.addGenerateInternalHeaderElement();
					}
					
					//Create new ServiceConfigurationElement
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
				
				//if this is the last row then add InternalHeaderElement now
				if(this.currentRowIndex == rows.size()){
					currentServiceConfiguration.addGenerateInternalHeaderElement();
				}
				
				this.incrementCurrentRowIndex();
				
			}	
				
			
			try{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(configFileLocation));
					
				transformer.transform(source, result);
		
			}catch(TransformerException e){
			System.out.println("\n"+"The path to the config file is not correct.  Please check this path in the GenerateConfigFile.bat file.");
			System.exit(-1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addRow(ArrayList<String> rowContent){
		
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
	
	public ArrayList<ArrayList<String>> getRows(){
		return rows;
	}
	
	private int initializeCurrentRowIndex(){
		return this.currentRowIndex = 1;
	}
	
	private void incrementCurrentRowIndex(){
		this.currentRowIndex++;
	}
}

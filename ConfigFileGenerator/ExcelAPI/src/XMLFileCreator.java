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
				
				/*for (int i = 0; i<row.size(); i++){
					System.out.print("#"+i+": "+row.get(i).toString()+" ");
				}*/
				
				String service = "";
				String isInternalHeader = "";
				String version = "";
				String target = "";
				String severity_INFO = "";
				String info_Details = "";
				String severity_DEBUG = "";
				String debug_Details = "";
				String severity_ERROR = "";
				String error_Details = "";
				String severity_FATAL = "";
				String fatal_Details = "";
				String severity_TRACE = "";
				String trace_Details = "";
				String severity_WARNING = "";
				String warning_Details = "";
				
				for (int j = 0; j < row.size(); j++){
					
					if (row.get(j).toString().equals("INFO_Details")){
						
						severity_INFO = row.get(j-3).toString();
						info_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("DEBUG_Details")){
						
						severity_DEBUG = row.get(j-3).toString();
						debug_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("ERROR_Details")){
						
						severity_ERROR = row.get(j-3).toString();
						error_Details = row.get(j-1).toString();
												
					}else if (row.get(j).toString().equals("FATAL_Details")){
						
						severity_FATAL = row.get(j-3).toString();
						fatal_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("TRACE_Details")){
						
						severity_TRACE = row.get(j-3).toString();
						trace_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("WARNING_Details")){
						
						severity_WARNING = row.get(j-3).toString();
						warning_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("Service")){
						service = row.get(j-1);
						
					}else if (row.get(j).toString().equals("generateInternalHeader")){
						isInternalHeader = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("version")){
						version = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("target")){
						target = row.get(j-1).toString();
						
					}
			
				}
				
				
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
				
				Filter filterINFO = new Filter(doc, info_Details, "INFO", severity_INFO);
				logging.addFilter(filterINFO);
				
				Filter filterDEBUG = new Filter(doc, debug_Details, "DEBUG", severity_DEBUG);
				logging.addFilter(filterDEBUG);
				
				Filter filterFATAL = new Filter(doc, fatal_Details, "FATAL", severity_FATAL);
				logging.addFilter(filterFATAL);
				
				Filter filterERROR = new Filter(doc, error_Details, "ERROR", severity_ERROR);
				logging.addFilter(filterERROR);
				
				Filter filterWARNING = new Filter(doc, warning_Details, "WARNING", severity_WARNING);
				logging.addFilter(filterWARNING);
				
				Filter filterTRACE = new Filter(doc, trace_Details, "TRACE", severity_TRACE);
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
		
		/*int rowsInitialSize = rows.size();
		
		for (int i=1; i<5; i++){
			rows.remove(rowsInitialSize-i);
		}*/
		
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

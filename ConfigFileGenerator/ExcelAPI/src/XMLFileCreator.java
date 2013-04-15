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
						
						info_Details = row.get(j-1).toString();
						
					}else if(row.get(j).toString().equals("Severity_INFO")){
						
						severity_INFO = row.get(j-1).toString();
							
					}else if (row.get(j).toString().equals("DEBUG_Details")){
						
						debug_Details = row.get(j-1).toString();
						
					}else if(row.get(j).toString().equals("Severity_DEBUG")){
						
						severity_DEBUG = row.get(j-1).toString();
							
					}else if (row.get(j).toString().equals("ERROR_Details")){
						
						error_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("Severity_ERROR")){
						
						severity_ERROR = row.get(j-1).toString();
							
					}else if (row.get(j).toString().equals("FATAL_Details")){
						
						fatal_Details = row.get(j-1).toString();
					
					}else if (row.get(j).toString().equals("Severity_FATAL")){
						
						severity_FATAL = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("TRACE_Details")){
						
						trace_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("Severity_TRACE")){
						
						severity_TRACE = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("WARNING_Details")){
						
						warning_Details = row.get(j-1).toString();
						
					}else if (row.get(j).toString().equals("Severity_WARNING")){
						
						severity_WARNING = row.get(j-1).toString();
							
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
				
				
				if (!info_Details.equals("") && !severity_INFO.equals("")){
					Filter filterINFO = new Filter(doc, info_Details, "INFO", severity_INFO);
					logging.addFilter(filterINFO);
				}else if (info_Details.equals("") && severity_INFO.equals("")){
					//do nothing.
				}else{
					System.out.println("The excel file is misformed.  Some INFO information is missing.");
					System.exit(-1);
				}
					
				if (!debug_Details.equals("") && !severity_DEBUG.equals("")){
					Filter filterDEBUG = new Filter(doc, debug_Details, "DEBUG", severity_DEBUG);
					logging.addFilter(filterDEBUG);
				}else if(debug_Details.equals("") && severity_DEBUG.equals("")){
					//do nothing.
				}else{
					System.out.println("The excel file is misformed.  Some DEBUG information is missing.");
					System.exit(-1);
				}
				
				if (!fatal_Details.equals("") && !severity_FATAL.equals("")){	
					Filter filterFATAL = new Filter(doc, fatal_Details, "FATAL", severity_FATAL);
					logging.addFilter(filterFATAL);
				}else if(fatal_Details.equals("") && severity_FATAL.equals("")){
					//do nothing.
				
				}else{
					System.out.println("The excel file is misformed.  Some FATAL information is missing.");
					System.exit(-1);
				}
					
				if (!error_Details.equals("") && !severity_ERROR.equals("")){
					Filter filterERROR = new Filter(doc, error_Details, "ERROR", severity_ERROR);
					logging.addFilter(filterERROR);					
				}else if (error_Details.equals("") && severity_ERROR.equals("")){
					//do nothing.
				}else{
					System.out.println("The excel file is misformed.  Some ERROR information is missing.");
					System.exit(-1);
				}
					
				if (!warning_Details.equals("") && !severity_WARNING.equals("")){
					Filter filterWARNING = new Filter(doc, warning_Details, "WARNING", severity_WARNING);
					logging.addFilter(filterWARNING);
				}else if(warning_Details.equals("") && severity_WARNING.equals("")){
					//do nothing.
				}else{
					System.out.println("The excel file is misformed.  Some WARNING information is missing.");
					System.exit(-1);
				}
					
				
				if (!trace_Details.equals("") && !severity_TRACE.equals("")){
					Filter filterTRACE = new Filter(doc, trace_Details, "TRACE", severity_TRACE);
					logging.addFilter(filterTRACE);
				}else if(trace_Details.equals("") && severity_TRACE.equals("")){
					//do nothing.
				}else{
					System.out.println("The excel file is misformed.  Some TRACE information is missing.");
					System.exit(-1);
				}
				
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
	
	public void removeFirstRow(){
		
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

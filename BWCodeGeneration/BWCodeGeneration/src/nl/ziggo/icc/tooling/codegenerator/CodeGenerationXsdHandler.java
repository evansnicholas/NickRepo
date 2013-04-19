package nl.ziggo.icc.tooling.codegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

import javax.swing.JTextArea;

import nl.ziggo.icc.tooling.codegenerator.entity.Service;
import nl.ziggo.icc.tooling.codegenerator.exceptions.NoXsdsFoundException;

public class CodeGenerationXsdHandler {

	JTextArea log;
	
	public CodeGenerationXsdHandler(JTextArea log){
		
		this.log = log;
		
	}
	
	
	public Service[] getServicesFromXsdDirectory(String componentName) throws FileNotFoundException, NoXsdsFoundException{
		
		File xsdFileDirectory = new File(CodeGeneratorConfiguration.svnComponentsFile.toPath()+"\\"+componentName+"\\trunk\\resource\\Schemas");
		
		log.append("Searching for xsd in folder : "+xsdFileDirectory.getPath()+"\n");
    	log.setCaretPosition(log.getDocument().getLength());
		
		Service[] services;
		
		File[] xsds;
		
		if (!xsdFileDirectory.exists()){
			
			throw new FileNotFoundException();
			
		}else{
			
			xsds = xsdFileDirectory.listFiles();
			
			int numberOfServices = xsds.length;
			
			if (numberOfServices == 0){
				throw new NoXsdsFoundException();
			}
			
			services = new Service[numberOfServices];
			
			log.append("Found "+numberOfServices+" xsd(s).\n");
	    	log.setCaretPosition(log.getDocument().getLength());
			
		}
		
		int serviceNumber = 0;
		
		for (File xsd : xsds){
			
			log.append("Found xsd: "+xsd.getName()+"\n");
	    	log.setCaretPosition(log.getDocument().getLength());
			
			Service service = new Service(componentName);
			
			HashMap<String, String> serviceInformation = this.getServiceInformationFromXsd(xsd);
			
			service.setCdmEntity(serviceInformation.get("cdmEntity"));
			service.setOperationName(serviceInformation.get("operationName"));
			service.setOperationVersion(serviceInformation.get("operationVersion"));
			
			services[serviceNumber] = service;
			
			serviceNumber++;
		}
		
		return services;
	}
	
	
	public HashMap<String, String> getServiceInformationFromXsd(File xsd){
		
		HashMap<String, String> serviceInformation = new HashMap<String, String>();
		
		try (BufferedReader reader = Files.newBufferedReader(xsd.toPath(), StandardCharsets.UTF_8)) {
			
		    String line = null;
		    
		    while ((line = reader.readLine()) != null) {
		    	
		    		if (!line.contains("targetNamespace=")){
		    			continue;
		    		}else{
		    			
		    			int indexOfStartOfServiceInfo = line.indexOf("targetNamespace")+62;
		    			int indexOfEndOfServiceInfo = line.indexOf("\"", indexOfStartOfServiceInfo);
		    			String namespace = line.substring(indexOfStartOfServiceInfo, indexOfEndOfServiceInfo);
		    			String[] namespaceSplit = namespace.split("/");
		    			
		    			serviceInformation.put("cdmEntity", namespaceSplit[0]);
		    			serviceInformation.put("operationName", namespaceSplit[1]);
		    			serviceInformation.put("operationVersion", namespaceSplit[2]);    			
		    			
		    			return serviceInformation;
		    		}
		    	
		    }
		
		
		}catch(IOException e){
			
			return null;
			
		}
	
		return null;
		
	}

	
}

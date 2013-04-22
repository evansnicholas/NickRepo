package nl.ziggo.icc.tooling.codegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import nl.ziggo.icc.tooling.codegenerator.entity.Service;
import nl.ziggo.icc.tooling.codegenerator.exceptions.NoXsdsFoundException;

public class CodeGenerationXsdHandler {

	private JTextArea log; 
	
	public CodeGenerationXsdHandler(JTextArea log){
		
		this.log = log;
		
	}
	
	
	public Service[] getServicesFromXsdDirectory(String componentName, boolean sourceCodeExists) throws FileNotFoundException, NoXsdsFoundException{
		
		File[] xsds;
		Service[] services;
		File xsdFileDirectory = new File(CodeGeneratorConfiguration.svnComponentsFile.toPath()+"\\"+componentName+"\\trunk\\resource\\");
		int numberOfxsds = 0;
		LastModifiedFileComparator fileComparer = new LastModifiedFileComparator();
		File componentSourceDirectory = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"\\"+componentName+"\\trunk\\src\\"+componentName);
		ArrayList<File> xsdsForGeneration = new ArrayList<File>();
		
		log.append("Searching for xsds in folder : "+xsdFileDirectory.getPath()+"\n");
    	log.setCaretPosition(log.getDocument().getLength());
		
		if (!xsdFileDirectory.exists()){
			
			throw new FileNotFoundException();
			
		}else{
			
			xsds = xsdFileDirectory.listFiles();			
			numberOfxsds = xsds.length;
			
			if (numberOfxsds == 0){
				throw new NoXsdsFoundException();
			}
			
			//INITIALIZE LATER 
			
			log.append("Found "+numberOfxsds+" xsd(s).\n");
	    	log.setCaretPosition(log.getDocument().getLength());
			
		}
		
		if (sourceCodeExists == true){
			
			//Check if there are new xsds and if the existent xsds match the currently used ones.
			
			for (File xsd : xsds){
				
				String xsdName = xsd.getName();
				String xsdNameWithoutExtension = xsdName.substring(0, xsdName.lastIndexOf("."));
				
				File xsdInBW = new File(componentSourceDirectory.toPath()+"\\Functionalities\\"+xsdNameWithoutExtension+"\\Resources\\InternalResources\\Schemas\\"+xsdName);
				
				//check if xsd exists in project.  If exists check if it is the same as the one in SVN.
				
				if (!xsdInBW.exists()){
					
					xsdsForGeneration.add(xsd);
					continue;
					
				}else{
				
					log.append(xsd.getPath()+" modified on :"+xsd.lastModified()+"\n");
					log.append(xsdInBW.getPath()+" modified on :"+xsdInBW.lastModified()+"\n");
					
					int compare = fileComparer.compare(xsd, xsdInBW);
					
					if (compare == 0){
						
						log.append("Xsd "+xsd.toPath()+" is already used in BW.  This xsd will be skipped. \n");
						log.setCaretPosition(log.getDocument().getLength());
						xsd = null;
						
						
					}else{
						
						
						if (compare < 0){
							log.append("Xsd "+xsd.toPath()+" has been modified in BW.  This xsd will be skipped. \n");
							log.setCaretPosition(log.getDocument().getLength());
							
						}else{
							log.append("Xsd "+xsd.toPath()+" is newer than the version currently used in BW.  This xsd will be skipped. \n");
							log.setCaretPosition(log.getDocument().getLength());
							
						}
						
					}			
				}
			}
		}else{
			
			//Source code does not exist do code for all xsds needs to be generated.
			for (File xsd : xsds){
				
				xsdsForGeneration.add(xsd);
				
			}
			
		}
		
		int numberOfServicesToGenerate = xsdsForGeneration.size();
		
		if (xsdsForGeneration.size() == 0){
			
			throw new NoXsdsFoundException();
			
		}
		
		services = new Service[numberOfServicesToGenerate];
		
		int serviceNumber = 0;
		
		for (File xsd : xsdsForGeneration){
			
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

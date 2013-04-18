package nl.ziggo.icc.tooling.codegenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import nl.ziggo.icc.tooling.codegenerator.entity.Service;

import org.apache.commons.io.FileUtils;


public class CodeGenerationManager {

	String componentName;
	JTextArea log;
	HashMap<String, String> placeHolders;
	
	public CodeGenerationManager(String componentName, JTextArea log, HashMap<String, String> placeHolders){
	
		this.componentName = componentName;
		this.log = log;
		this.placeHolders = placeHolders;
		
	}
	
	public boolean generateBWCodeForComponent(){
		
		boolean isSuccessful;
		
		if (new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"\\"+this.componentName+"\\trunk\\src\\"+componentName).exists() == true){
			
			log.append("Source code for " + componentName + " already exists. \n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		    return isSuccessful = false;
			
		}
		
		CodeGenerationXsdHandler xsdHandler = new CodeGenerationXsdHandler(this.log);
		
		Service[] services = xsdHandler.getServicesFromXsd(componentName);
		
		if (services == null){
			
			log.append("Found no xsds for component "+componentName+".\n");
	    	log.setCaretPosition(log.getDocument().getLength());
	    	
	    	return isSuccessful = false;
		}
		
		File[] templates = CodeGeneratorConfiguration.templatesLocation.listFiles();
		
			for (Service service : services){
				
				log.append("Processing xsd: "+service.getOperationName()+"\n");
		    	log.setCaretPosition(log.getDocument().getLength());
		
				HashMap<String, String> serviceInformation = service.getServiceInformation();
				
				for (File template : templates){
					
					boolean isFolderName = false;
					
					if (template.isDirectory() == true){
						
						continue;
						
					}else{	
						
						
						try{
						
							//Get template as string
							String fileContents = FileUtils.readFileToString(template);
							
							//Replace all necessary values
							for (String key : placeHolders.keySet()){
							
								fileContents = fileContents.replaceAll(placeHolders.get(key), serviceInformation.get(key));
							
							}
							
							//Performing parsing of template Name
							String partialDestinationPath = "";
							String templateName = template.getName();
							
							if (templateName.endsWith(".directory")){
								
								isFolderName = true;
								
								int lastDotIndex = templateName.lastIndexOf(".");
								
								templateName = templateName.substring(0, lastDotIndex);
								
							}
		
							String[] templateNameParts = templateName.split("-");
							
							
							
							for (String part : templateNameParts){
								
								for (String key : placeHolders.keySet()){
									
									part = part.replaceAll(placeHolders.get(key), serviceInformation.get(key));
									
								}
								
								partialDestinationPath = partialDestinationPath+"\\"+part;
								
							}	
							
							
							//Create the final destination path for the code
							File finalGeneratedFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"\\"+this.componentName+"\\trunk\\src\\"+this.componentName+"\\"+partialDestinationPath);
							
							
							//Make the necessary directories for the file if file is not a directory
							
							if (isFolderName == false){
								
								finalGeneratedFile.getParentFile().mkdirs();
								
							}else{
								
								finalGeneratedFile.mkdirs();
								
							}
							
							
							//Write the string to a file in the correct SVN location.
							if (isFolderName == false){
								
								FileUtils.writeStringToFile(finalGeneratedFile, fileContents);
							
							}
							
							//Log what has been done
							log.append("Created: " + finalGeneratedFile.getAbsolutePath() + "\n");
						    log.setCaretPosition(log.getDocument().getLength());
							
							
						}catch(IOException e ){
							
							try{
								
								FileUtils.cleanDirectory(new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"\\"+this.componentName+"\\trunk\\src\\"+this.componentName));
								
							}catch(IOException e2){
								
								log.append("An error occurred: " + e2.getMessage() + "  Code generation aborted.\n");
							    log.setCaretPosition(log.getDocument().getLength());
							    return isSuccessful = false;
								
								
							}
							
							log.append("An error occurred: " + e.getMessage() + "  Code generation aborted.\n");
						    log.setCaretPosition(log.getDocument().getLength());
						  
						    return isSuccessful = false;
							
						}
					
					}			
				
				}
				
			}
		
		return isSuccessful = true;
	}
	
	
}

package nl.ziggo.icc.tooling.codegenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import nl.ziggo.icc.tooling.codegenerator.entity.Service;
import nl.ziggo.icc.tooling.codegenerator.exceptions.CodeGeneratorException;
import nl.ziggo.icc.tooling.codegenerator.exceptions.ComponentNotFoundException;
import nl.ziggo.icc.tooling.codegenerator.exceptions.NoXsdsFoundException;

import org.apache.commons.io.FileUtils;


public class CodeGenerationManager {

	private String componentName;
	private JTextArea log;
	private HashMap<String, String> placeHolders;
	private File componentFile;
	private File componentSourceFile;
	private CodeGenerationXsdHandler xsdHandler; 
	private File schemasLocation;
	
	public CodeGenerationManager(String componentName, JTextArea log, HashMap<String, String> placeHolders){
	
		this.componentName = componentName;
		this.log = log;
		this.placeHolders = placeHolders;
		this.xsdHandler = new CodeGenerationXsdHandler(this.log);
		componentFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName);
		componentSourceFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+componentName);
		schemasLocation = new File(CodeGeneratorConfiguration.svnComponentsFile.toPath()+"/"+componentName+"/trunk/resource");
		
	}
	
	public void generateBWCodeForComponent() throws CodeGeneratorException, NoXsdsFoundException{
		
		Service[] services = null;
		
		log.append("Looking for "+componentFile.toPath() + "\n"); 
		log.setCaretPosition(log.getDocument().getLength());
		
		if (!componentFile.exists()){
			
			throw new ComponentNotFoundException();
			
		}
		
		if (componentSourceFile.exists() == true){
			
			log.append("Source code for " + componentName + " already exists. \n");
			log.append("Searching for new xsds. \n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		    try{	
		    	
		    	services = xsdHandler.getServicesFromXsdDirectory(componentName, true, new File(CodeGeneratorConfiguration.svnComponentsFile.toPath()+"/"+componentName+"/trunk/resource/"));
		    	
		    }catch(FileNotFoundException fileNotFoundException){
		    	
		    	log.append(fileNotFoundException.getMessage());
			    log.setCaretPosition(log.getDocument().getLength());
		    	
		    }
		    	
		}else{
			
			log.append("Source code for " + componentName + " does not exist. \n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		    try{	
		    	
		    	services = xsdHandler.getServicesFromXsdDirectory(componentName, false, new File(CodeGeneratorConfiguration.svnComponentsFile.toPath()+"/"+componentName+"/trunk/resource/"));
		    	
		    }catch(FileNotFoundException fileNotFoundException){
		    	
		    	log.append(fileNotFoundException.getMessage());
			    log.setCaretPosition(log.getDocument().getLength());
			    
			    throw new CodeGeneratorException();
		    	
		    }
			
		}
		
		if (services == null){
			throw new CodeGeneratorException();
		}
		
		log.append("Number of new services to generate: "+services.length+"\n");
    	log.setCaretPosition(log.getDocument().getLength());
		
		File[] templates = CodeGeneratorConfiguration.templatesLocation.listFiles();	
			
		for (Service service : services){
			
			log.append("Processing xsd: "+service.getOperationName()+"_"+service.getOperationVersion()+"\n");
	    	log.setCaretPosition(log.getDocument().getLength());
	
	    	//Copy the xsd to the correct location
	    	
	    	try{
	    		
	    		FileUtils.copyFile(new File(schemasLocation.toPath()+"/"+service.getOperationName()+"_"+service.getOperationVersion()+".xsd"), new File(componentSourceFile.toPath()+"/Functionalities/"+service.getOperationName()+"_"+service.getOperationVersion()+"/Resources/InternalResources/Schemas/"+service.getOperationName()+"_"+service.getOperationVersion()+".xsd"), true);
	    	
	    	}catch(IOException e){
	    		
	    		log.append(e.getMessage()+"\n");
		    	log.setCaretPosition(log.getDocument().getLength());
		    	
		    	throw new CodeGeneratorException();
	    		
	    	}
	    		
	    		
			HashMap<String, String> serviceInformation = service.initializeAndGetServiceInformation();
			
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
						
						//Insert timestamp if necessary
						String timestampString = Long.toString(System.currentTimeMillis());
						
						fileContents = fileContents.replaceAll("~TIMESTAMP~", timestampString);
						
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
							
							partialDestinationPath = partialDestinationPath+"/"+part;
							
						}	
						
						
						//Create the final destination path for the code
						File finalGeneratedFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+this.componentName+"/"+partialDestinationPath);
						
						
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
							
							FileUtils.cleanDirectory(new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+this.componentName));
							
						}catch(IOException e2){
							
							log.append("An error occurred: " + e2.getMessage() + "  Code generation aborted.\n");
						    log.setCaretPosition(log.getDocument().getLength());
						    
						    throw new CodeGeneratorException();
							
							
						}
						
						log.append("An error occurred: " + e.getMessage() + "  Code generation aborted.\n");
					    log.setCaretPosition(log.getDocument().getLength());
					  
					    throw new CodeGeneratorException();
						
					}
				
				}			
			
			}
			
		}
	}
		
	
	public void generateInitialSvnStructure(){
		
		File svnFolderStructureTemplateLocation = new File(CodeGeneratorConfiguration.templatesLocation.toPath()+"/SVN");
		
		File[] templates = svnFolderStructureTemplateLocation.listFiles();
		
		
		for (File template : templates){
			
			if (template.isDirectory()){
				continue;
			}
			
			//Performing parsing of template Name
			String templateName = template.getName();
			int lastDotIndex = templateName.lastIndexOf(".");			
			templateName = templateName.substring(0, lastDotIndex);
			templateName = templateName.replaceAll("[-]","/");
			
			//Create the final File
			File finalGeneratedFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/"+templateName);
			
			//Create the folder on the file system
			finalGeneratedFile.mkdirs();
			
			//Log what has been done
			log.append("Created: " + finalGeneratedFile.getAbsolutePath() + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
			
		}
		
	}
	
	public void createEmptyBWTemplate(Service[] services) throws CodeGeneratorException{
		
		File[] templates = CodeGeneratorConfiguration.templatesLocation.listFiles();
		
		for (Service service : services){
			
			log.append("Making empty template for: "+service.getOperationName()+"_"+service.getOperationVersion()+"\n");
	    	log.setCaretPosition(log.getDocument().getLength());
	    	   		
			HashMap<String, String> serviceInformation = service.initializeAndGetServiceInformation();
			
			for (File template : templates){
				
				boolean isFolderName = false;
				
				if (template.isDirectory() == true){
					
					continue;
					
				}else if (template.getName().contains("vcrepo")){
					
					try{
						//Get template as string
						String fileContents = FileUtils.readFileToString(template);
						fileContents = fileContents.replaceAll("[~]ADAPTER_NAME[~]", service.getAdapterName());
						File vcrepoFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+componentName+"/"+"vcrepo.dat");
						
						vcrepoFile.getParentFile().mkdirs();
						
						FileUtils.writeStringToFile(vcrepoFile, fileContents);
											
					}catch(IOException ioException){
						
						log.append(ioException.getMessage());
						log.setCaretPosition(log.getDocument().getLength());
						
						throw new CodeGeneratorException();
						
					}
				
				}else{	
						
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
							
							partialDestinationPath = partialDestinationPath+"/"+part;
							
						}	
						
						
						//Create the final destination path for the code
						File finalGeneratedFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+this.componentName+"/"+partialDestinationPath);
						
						
						//Make the necessary directories for the file if file is not a directory
						
						if (isFolderName == false){
							
							finalGeneratedFile.getParentFile().mkdirs();
							
						}else{
							
							finalGeneratedFile.mkdirs();
							
						}
						
						
						//Log what has been done
						log.append("Created: " + finalGeneratedFile.getAbsolutePath() + "\n");
					    log.setCaretPosition(log.getDocument().getLength());
				
				}			
			
			}
			
		}
				
	}
	
	public void generateServiceInvocations() throws FileNotFoundException, CodeGeneratorException{
		
		File sharedResourcesSchemasLocation = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+this.componentName+"/SharedResources/Schemas");
		File sharedResourcesWsdlsLocation = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"\\"+this.componentName+"/trunk/src/"+this.componentName+"/SharedResources/WSDL");
		Service[] servicesToBeInvoked = null;
		File serviceInvocationTemplatesLocation = new File(CodeGeneratorConfiguration.templatesLocation+"/Invocation");
		
		if (!sharedResourcesSchemasLocation.exists() || !sharedResourcesWsdlsLocation.exists()){
			
			throw new FileNotFoundException();
			
		}
		
		try{
			
			servicesToBeInvoked = xsdHandler.getServicesFromXsdDirectory(this.componentName, false, sharedResourcesSchemasLocation);
			
		}catch(FileNotFoundException fileNotFoundException){
			
			throw new FileNotFoundException();
			
		}catch(NoXsdsFoundException noXsdsFoundException){
			
			log.append(noXsdsFoundException.getMessage());
		    log.setCaretPosition(log.getDocument().getLength());
		    log.append("No service invocation processes were generated." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		    throw new CodeGeneratorException();
			
		}
		
		for (Service service : servicesToBeInvoked){
			
			log.append("Processing invoked service: "+service.getOperationName()+"_"+service.getOperationVersion()+"\n");
	    	log.setCaretPosition(log.getDocument().getLength());
	    		
			HashMap<String, String> serviceInformation = service.initializeAndGetServiceInformation();
			
			File[] serviceInvocationTemplates = serviceInvocationTemplatesLocation.listFiles();
			
			for (File template : serviceInvocationTemplates){
				
				if (!new File(sharedResourcesWsdlsLocation.toPath()+"/"+service.getOperationName()+"_"+service.getOperationVersion()+"_jms.wsdl").exists()){
					
					log.append("Error! WSDL "+service.getOperationName()+"_"+service.getOperationVersion()+"_jms.wsdl does not exist. \n");
			    	log.setCaretPosition(log.getDocument().getLength());
			    	throw new CodeGeneratorException();
					
				}
				
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
						String[] templateNameParts = templateName.split("-");
												
						for (String part : templateNameParts){
							
							for (String key : placeHolders.keySet()){
								
								part = part.replaceAll(placeHolders.get(key), serviceInformation.get(key));
								
							}
							
							partialDestinationPath = partialDestinationPath+"\\"+part;
							
						}	
						
						
						//Create the final destination path for the code
						File finalGeneratedFile = new File(CodeGeneratorConfiguration.svnComponentsFile.getPath()+"/"+this.componentName+"/trunk/src/"+this.componentName+"/"+partialDestinationPath);
						
						
						//Make the necessary directories for the file				
						finalGeneratedFile.getParentFile().mkdirs();
													
						//Write the string to the file
						FileUtils.writeStringToFile(finalGeneratedFile, fileContents);
											
						//Log what has been done
						log.append("Created: " + finalGeneratedFile.getAbsolutePath() + "\n");
					    log.setCaretPosition(log.getDocument().getLength());
						
						
					}catch(IOException e){
						
						log.append(e.getMessage() + "\n");
					    log.setCaretPosition(log.getDocument().getLength());
					  
					    throw new CodeGeneratorException();
						
					}
				
				}			
			
			}
			
		}
				
	}
	
}

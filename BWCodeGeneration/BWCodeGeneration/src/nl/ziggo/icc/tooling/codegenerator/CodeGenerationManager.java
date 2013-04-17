package nl.ziggo.icc.tooling.codegenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;


public class CodeGenerationManager {

	String componentName;
	JTextArea log;
	ArrayList<String[]> termsToBeReplaced;
	
	public CodeGenerationManager(String componentName, JTextArea log, ArrayList<String[]> termsToBeReplaced){
	
		this.componentName = componentName;
		this.log = log;
		this.termsToBeReplaced = termsToBeReplaced;
		
	}
	
	public boolean generateBWCodeForComponent(){
		
		boolean isSuccessful;
		
		File[] templates = CodeGeneratorConfiguration.templatesLocation.listFiles();
		
		for (File template : templates){
			
			boolean isFolderName = false;
			
			if (template.isDirectory() == true){
				
				continue;
				
			}else{	
				
				
				try{
				
					//Get template as string
					String fileContents = FileUtils.readFileToString(template);
					
					//Replace all necessary values
					for (String[] termPair : this.termsToBeReplaced){
					
						fileContents = fileContents.replaceAll(termPair[0], termPair[1]);
					
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
						
						for (String[] termPair : termsToBeReplaced){
							
							part = part.replaceAll(termPair[0], termPair[1]);
							
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
		
		return isSuccessful = true;
	}
	
}

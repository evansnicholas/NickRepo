package nl.ziggo.ICC;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

import org.apache.commons.io.FileUtils;

public class Utilities {
	
	JTextArea log;
	
	public Utilities(JTextArea log){
		
		this.log = log;
		
	}
	
	
	public String readFileAsString(File file){
		
		String fileContents = "";
		
		try{
			
			fileContents = FileUtils.readFileToString(file);
			
		}catch(IOException e){
			
			log.append("An error occurred reading the file "+file.getPath()+".  Code generation aborted.\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
		
		return fileContents;
		
	}
	
	public void writeStringToFile(String string,File file){
		
		try{
			
			FileUtils.writeStringToFile(file, string);
			
		}catch(IOException e){
			
			log.append("An error occurred writing the file "+ file.getPath() + ".  Code generation aborted.\n");
		    log.setCaretPosition(log.getDocument().getLength());
			
		}
		
	}
	
	public void searchReplaceAndWriteToTempFolder(ArrayList<String[]> termsToBeReplaced, File templatesFolder, File tempLocation){
		
		File[] templates = templatesFolder.listFiles();
		
		for (File template : templates){
			
			if (template.isDirectory() == true){
				
				continue;
				
			}else{
			
				String fileContents = this.readFileAsString(template);
				
				for (String[] termPair : termsToBeReplaced){
				
					fileContents = fileContents.replaceAll(termPair[0], termPair[1]);
				
				}
			
				this.writeStringToFile(fileContents, new File(tempLocation+"\\"+template.getName()));
				
			}
		}		
	}
	
	public void copyFilesFromTempFolderBasedOnName(File rootFolder, File tempFolder, ArrayList<String[]> termsToBeReplaced){		
		
		for (File file : tempFolder.listFiles()){
			
			String partialDestinationPath = "";
			
			String fileName = file.getName();
			String[] fileNameParts = fileName.split("-");
			
			for (String part : fileNameParts){
				
				for (String[] termPair : termsToBeReplaced){
					
					part = part.replaceAll(termPair[0], termPair[1]);
					
				}
				
				partialDestinationPath = partialDestinationPath+"\\"+part;
				
			}			
			
			try{
				
				FileUtils.copyFile(file, new File(rootFolder+"\\"+partialDestinationPath), false);
				
			}catch(IOException e){
				
				log.append("An error occurred wile copying the file.  The error is :"+e.getMessage()+".  Code generation aborted.\n");
			    log.setCaretPosition(log.getDocument().getLength());
			    return;
				
			}catch(Exception e){
				log.append("An error occurred wile copying the file.  The error is :"+e.getMessage()+".  Code generation aborted.\n");
			    log.setCaretPosition(log.getDocument().getLength());
			    return;
			}
			
		}
		
		try{
				
			FileUtils.cleanDirectory(tempFolder);
			
		}catch(IOException e){
			
			log.append("An error occurred wile cleaning the temporary directory.  The error is :"+e.getMessage()+".  Code generation aborted.\n");
		    log.setCaretPosition(log.getDocument().getLength());
			
		}
		
	}
	
}

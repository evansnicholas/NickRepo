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
			System.exit(-1);
		    
		}
		
		return fileContents;
		
	}
	
	public void writeStringToFile(String string,File file){
		
		try{
			
			FileUtils.writeStringToFile(file, string);
			
		}catch(IOException e){
			
			log.append("An error occurred writing the file "+ file.getPath() + ".  Code generation aborted.\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    System.exit(-1);
			
		}
		
	}
	
	public void searchReplaceAndWriteToTempFolder(ArrayList<String[]> termsToBeReplaced, File templatesFolder, File tempLocation){
		
		File[] templates = templatesFolder.listFiles();
		
		for (File template : templates){
			
			String fileContents = this.readFileAsString(template);
			
			for (String[] termPair : termsToBeReplaced){
				
				fileContents.replaceAll(termPair[0], termPair[1]);
				
			}
			
			this.writeStringToFile(fileContents, new File(tempLocation+"//"+template.getName()));
			
		}
		
	}
	
}

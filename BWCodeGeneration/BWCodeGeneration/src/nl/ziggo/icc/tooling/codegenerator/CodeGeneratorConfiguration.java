package nl.ziggo.icc.tooling.codegenerator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class CodeGeneratorConfiguration {

	private static File memoryFileLocation = new File("misc\\memory.txt");
	private static File configFileLocation = new File("misc\\config.txt");
	private Charset charset; 
	private JTextArea log;
	public static File svnComponentsFile;
	public static File templatesLocation;
	private ArrayList<String> pastComponents;
	
	
	public CodeGeneratorConfiguration(Charset charset, JTextArea log){
		
		this.charset = charset;
		this.log = log;
		this.getDirectoryPathsFromConfigFile();
		
	}
	
	public String[] getPastComponents(){
		
		String[] pastComponents; 
		ArrayList<String> pastComponentsList = new ArrayList<String>();
		
		try (BufferedReader reader = Files.newBufferedReader(memoryFileLocation.toPath(), charset)) {
			
		    String line = null;
		    
		    while ((line = reader.readLine()) != null) {
		    	
		    		pastComponentsList.add(line);
		    	
		    }
		    
		    this.pastComponents = pastComponentsList;
		    
		    int pastComponentsListSize = pastComponentsList.size();
		    	
	    	if (pastComponentsListSize > 0){
	    		
	    		pastComponents = new String[pastComponentsListSize];
	    		
	    		for (int i=0; i < pastComponentsListSize; i++){
	    			
	    			pastComponents[i] = pastComponentsList.get(i).toString();
	    			
	    		}
	    		
	    		return pastComponents;
	    		
	    	}
	    	
		       		    
		}catch (IOException x) {
			
		    log.append("The path for the memory file "+x.getMessage()+" does not exist." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
		
		pastComponents = new String[1];
		pastComponents[0] = "no previous components"; 		
		return pastComponents;
		
	}
	
	public void addComponentNameToPastComponentsList(String componentName){
		
		if (componentName.equals("")){
			return;
		}
		
		for (String pastComponent : pastComponents){
			
			if (componentName.equals(pastComponent)){
				
				return;
				
			}
			
		}
		
		this.pastComponents.add(componentName);
		
		try (BufferedWriter writer = Files.newBufferedWriter(memoryFileLocation.toPath(), charset, StandardOpenOption.APPEND)) {
			
		    writer.write(componentName+"\n", 0, componentName.length()+1);
		    
		} catch (IOException x) {
			
			log.append("The path for the memory file "+x.getMessage()+" does not exist." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
		
	}
	
	public void getDirectoryPathsFromConfigFile(){
		
		try (BufferedReader reader = Files.newBufferedReader(configFileLocation.toPath(), charset)) {
			
		    String line = null;
		    
		    while ((line = reader.readLine()) != null) {
		    	
		    		if (line.startsWith("svn_components_location")){
		    			
		    			int equalsPosition = line.indexOf("=");
		    			svnComponentsFile = new File(line.substring(equalsPosition+1, line.length()).trim());
		    			log.append("Configured SVN components location is: "+svnComponentsFile.getPath() + "\n");
		    		    log.setCaretPosition(log.getDocument().getLength());
		    		    		    		
		    		}
		    		
		    		if (line.startsWith("templates_directory_location")){
		    			
		    			int equalsPosition = line.indexOf("=");
		    			templatesLocation = new File(line.substring(equalsPosition+1, line.length()).trim());
		    			log.append("Configured templates directory location is: "+templatesLocation.getPath() + "\n");
		    		    log.setCaretPosition(log.getDocument().getLength());
		    		    		    		
		    		}
		    	
		    }
		    
		}catch(IOException x) {
			
		    log.append("The path for the config file "+x.getMessage()+" does not exist." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
				
	}
	
	
}

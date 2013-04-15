package nl.ziggo.ICC;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import javax.swing.JTextArea;


public class ConfigFileLoader {

	private File configFileLocation;
	Charset charset; 
	JTextArea log;
	
	public ConfigFileLoader(String configFilePath, Charset charset, JTextArea log){
		
		this.configFileLocation = new File(configFilePath);
		this.charset = charset;
		this.log = log;
	}
	
	public String[] getPastComponents(){
		
		String[] pastComponents; 
		ArrayList<String> pastComponentsList = new ArrayList<String>();
		
		try (BufferedReader reader = Files.newBufferedReader(configFileLocation.toPath(), charset)) {
			
		    String line = null;
		    
		    while ((line = reader.readLine()) != null) {
		    	
		    		pastComponentsList.add(line);
		    	
		    }
		    
		    int pastComponentsListSize = pastComponentsList.size();
		    	
	    	if (pastComponentsListSize > 0){
	    		
	    		pastComponents = new String[pastComponentsListSize];
	    		
	    		for (int i=0; i < pastComponentsListSize; i++){
	    			
	    			pastComponents[i] = pastComponentsList.get(i).toString();
	    			
	    		}
	    		
	    		return pastComponents;
	    		
	    	}
	    	
		       		    
		}catch (IOException x) {
			
		    log.append("The path for the config file "+x.getMessage()+" does not exist." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
		
		pastComponents = new String[1];
		pastComponents[0] = "no previous components"; 		
		return pastComponents;
		
	}
	
	public void addComponentNameToPastComponentsList(String componentName){
		
		try (BufferedWriter writer = Files.newBufferedWriter(configFileLocation.toPath(), charset, StandardOpenOption.APPEND)) {
			
		    writer.write("\n"+componentName, 0, componentName.length()+1);
		    
		} catch (IOException x) {
			
			log.append("The path for the config file "+x.getMessage()+" does not exist." + "\n");
		    log.setCaretPosition(log.getDocument().getLength());
		    
		}
		
	}
	
	
}

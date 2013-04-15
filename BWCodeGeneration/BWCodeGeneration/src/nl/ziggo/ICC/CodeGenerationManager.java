package nl.ziggo.ICC;
import java.io.File;

import javax.swing.JTextArea;


public class CodeGenerationManager {

	String componentName;
	File templateFilesLocation;
	JTextArea log;
	
	
	public CodeGenerationManager(String componentName, File templateFilesLocation, JTextArea log){
	
		this.componentName = componentName;
		this.templateFilesLocation = templateFilesLocation;
		this.log = log;
		
	}
	
	
	
	
}

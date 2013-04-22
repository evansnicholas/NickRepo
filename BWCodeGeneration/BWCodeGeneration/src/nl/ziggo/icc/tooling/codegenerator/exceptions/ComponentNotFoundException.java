package nl.ziggo.icc.tooling.codegenerator.exceptions;

public class ComponentNotFoundException extends CodeGeneratorException {

	String message = "The component was not found. \n";
	
	public String getMessage(){
		return this.message;
	}
	
}

package nl.ziggo.icc.tooling.codegenerator.exceptions;

public class NoXsdsFoundException extends CodeGeneratorException {

	String message = "No (new) xsds were found in the Xsd folder. \n";
	
	public String getMessage(){
		return message;
	}
}

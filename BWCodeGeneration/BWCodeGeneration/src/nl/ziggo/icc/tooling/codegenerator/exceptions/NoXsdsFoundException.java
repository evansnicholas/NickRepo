package nl.ziggo.icc.tooling.codegenerator.exceptions;

public class NoXsdsFoundException extends CodeGeneratorException {

	String message = "No xsds were found in the Xsd folder.";
	
	public String getMessage(){
		return message;
	}
}

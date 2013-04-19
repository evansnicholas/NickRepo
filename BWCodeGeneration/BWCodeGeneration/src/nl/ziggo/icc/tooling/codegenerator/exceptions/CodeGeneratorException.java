package nl.ziggo.icc.tooling.codegenerator.exceptions;

public class CodeGeneratorException extends Throwable {

	private String message = "An error occured while trying to generate code.";
	
	public String getMessage(){
		return this.message;
	}
	
}

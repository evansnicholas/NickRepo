package nl.ziggo.ICC;
import java.io.File;


public class Operation {

	String operationName;
	String operationVersion;
	String cdmEntitity;
	File svnLocation;
	
	public Operation(String operationName, String operationVersion, String cdmEntity, File svnLocation){
		
		this.operationName = operationName;
		this.operationVersion = operationVersion;
		this.cdmEntitity = cdmEntity;
		this.svnLocation = svnLocation;
		
	}
	
	
	
}

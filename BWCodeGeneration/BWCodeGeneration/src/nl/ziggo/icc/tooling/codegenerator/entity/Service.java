package nl.ziggo.icc.tooling.codegenerator.entity;

import java.util.HashMap;

public class Service {

	private String adapterName;
	private String operationName;
	private String operationVersion;
	private String cdmEntity;
	private HashMap<String, String> serviceInformation;
	
	public Service(String adapterName){
		
		this.adapterName = adapterName;
		
	}
	
	public void setOperationName(String operationName){
		this.operationName = operationName;	
	}
	
	public void setOperationVersion(String operationVersion){
		this.operationVersion = operationVersion;
	}
	
	public void setCdmEntity(String cdmEntity){
		this.cdmEntity = cdmEntity;
	}
	
	public String getOperationName(){
		return this.operationName;
	}
	
	public String getOperationVersion(){
		return this.operationVersion;
	}
	
	public String getcdmEntity(){
		return this.cdmEntity;
	}
	
	public String getAdapterName(){
		return this.adapterName;
	}
	
	public HashMap<String, String> initializeAndGetServiceInformation(){
		
		HashMap<String, String> serviceInformation = new HashMap<String, String>();
		
		serviceInformation.put("cdmEntity", this.cdmEntity);
		serviceInformation.put("operationName", this.operationName);
		serviceInformation.put("operationVersion", this.operationVersion);
		serviceInformation.put("adapterName", this.adapterName);
		serviceInformation.put("adapterNameLowerCase", this.adapterName.toLowerCase());
		serviceInformation.put("operationNameLowerCase", this.operationName.toLowerCase());
		
		return serviceInformation;
		
	}
	
}

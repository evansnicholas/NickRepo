import java.io.File;


public class Operation {
	
	File operationFolder;
	String operationName;
	File implementationFolder;
	File interfaceFolder;
	File resourcesFolder;
	File serviceInvocationFolder;
	
	Operation(String operationName, File operationFolder){
		
		this.operationName = operationName;
		this.operationFolder = operationFolder;
		operationFolder.mkdirs();
		this.makeImplementationFolder();
		this.makeInterfaceFolder();
		this.makeResourcesFolder();
		this.makeServiceInvocationFolder();
		
	}
	
	public File getOperationFolder(){
		return operationFolder;
	}

	public void makeImplementationFolder(){
		
		this.implementationFolder = new File(operationFolder+"\\Implementation");
		implementationFolder.mkdir();
		
	}
	
	public void makeInterfaceFolder(){
		
		this.interfaceFolder = new File(operationFolder+"\\Interface");
		interfaceFolder.mkdir();
		
	}
	
	public void makeServiceInvocationFolder(){
		
		this.serviceInvocationFolder = new File(operationFolder+"\\Interface");
		serviceInvocationFolder.mkdir();
		
	}
	
	public void makeResourcesFolder(){
		
		this.resourcesFolder = new File(operationFolder+"\\Resources");
		resourcesFolder.mkdir();
		
		File externalResourcesFolder = new File(operationFolder+"\\Resources\\ExternalResources");
		externalResourcesFolder.mkdir();
		
		this.makeResourcesFolders(operationFolder+"\\Resources\\ExternalResources");
		
		File internalResourcesFolder = new File(operationFolder+"\\Resources\\InternalResources");
		internalResourcesFolder.mkdir();
		
		this.makeResourcesFolders(operationFolder+"\\Resources\\InternalResources");
		
		//Make ServiceInvocation Folder
		File serviceInvocationFolder = new File(operationFolder+"\\ServiceInvocation");
		serviceInvocationFolder.mkdir();
		
	}
	
	public void makeResourcesFolders(String resourcesPath){
		
		File connectionsFolder = new File(resourcesPath+"\\Connections");
		connectionsFolder.mkdir();
		
		File schemasFolder = new File(resourcesPath+"\\Schemas");
		schemasFolder.mkdir();
		
		File wsdlFolder = new File(resourcesPath+"\\WSDL");
		wsdlFolder.mkdir();
			
	}
	
}

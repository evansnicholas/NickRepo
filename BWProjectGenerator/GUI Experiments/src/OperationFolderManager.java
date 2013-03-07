/*import java.io.File;
import java.util.ArrayList;


public class OperationFolderManager {

	
	
	public void generateFolderStructureForOperation(Operation operation){
		
		//Implementation Folder
		File implementationFolder = new File(operationHomeDirectory+"\\Implementation");
		implementationFolder.mkdir();
		
		File operationImplementationFolder = new File(operationHomeDirectory+"\\Implementation\\"+operationName+"_Impl");
		operationImplementationFolder.mkdir();
		
		//Interface Folder
		File interfaceFolder = new File(operationHomeDirectory+"\\Interface");
		interfaceFolder.mkdir();
		
		File operationInInterfaceFolder = new File(operationHomeDirectory+"\\Interface\\"+operationName);
		operationInInterfaceFolder.mkdir();
		
		//Resources Folder
		File resourcesFolder = new File(operationHomeDirectory+"\\Resources");
		resourcesFolder.mkdir();
		
		File externalResourcesFolder = new File(operationHomeDirectory+"\\Resources\\ExternalResources");
		externalResourcesFolder.mkdir();
		
		this.makeResourcesFolders(operationHomeDirectory+"\\Resources\\ExternalResources");
		
		File internalResourcesFolder = new File(operationHomeDirectory+"\\Resources\\InternalResources");
		internalResourcesFolder.mkdir();
		
		this.makeResourcesFolders(operationHomeDirectory+"\\Resources\\InternalResources");
		
		//Make ServiceInvocation Folder
		File serviceInvocationFolder = new File(operationHomeDirectory+"\\ServiceInvocation");
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
	
	public String getprojectHomeDirectory(){
		return projectHomeDirectory;
	}
	
	public ArrayList<File> getXMLFilesinFolder(File targetFile){
		
		ArrayList<File> xmlFiles = new ArrayList<File>();
		
		File[] allFiles = targetFile.listFiles();
		
		for(File i : allFiles){
			
			if (!i.isDirectory()){
				xmlFiles.add(i);
			}	
		}
		
		return xmlFiles;
		
	}
	
}*/

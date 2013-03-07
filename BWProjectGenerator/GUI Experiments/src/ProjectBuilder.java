import java.io.File;
import java.util.ArrayList;


public class ProjectBuilder {

	private ArrayList<File> filesToBeEnriched;
	
	ProjectBuilder(){
			
	}
	
	public void enrich(String filePath){
		
		if (filePath.substring(filePath.length()-4, filePath.length()).endsWith(".zip")){
		
			Unzip unzip = new Unzip();
			try{
			filesToBeEnriched = unzip.extractFolder(filePath);
            }catch(Exception e){}
		}
		
			String folderNameToBeParsed = filesToBeEnriched.get(0).getName();
			
			//Parse operation, version and ignite version
			String parsedFolderName = folderNameToBeParsed.replace("_", " ");
			parsedFolderName = parsedFolderName.replace("-", " ");
			String[] parsedValues = parsedFolderName.split(" ");
			
			//FileMaker fileMaker = new FileMaker();
			//String enrichedFilesFolder = fileMaker.makeFolder(folderNameToBeParsed+".enriched");
			
			File bwProject = new File("c:\\Users\\iggo\\Desktop\\"+parsedValues[0]+"_BW_Project"+"\\Functionalities");
			bwProject.mkdirs();
			
			Operation operation = new Operation(parsedValues[0], new File("c:\\Users\\iggo\\Desktop\\"+parsedValues[0]+"_BW_Project"+"\\Functionalities\\"+parsedValues[0]));
			
            //Get contents of Template
            /*ArrayList<File> xmlFiles = bwProject.getXMLFilesinFolder(new File("C:\\Users\\iggo\\workspace\\GUI Experiments\\src\\BWTEMPLATE\\PhoneNumberDeleted\\Implementation\\PhoneNumberDeleted_Impl"));
            for (File i : xmlFiles){
            	System.out.println(i.getName());
            }*/
			
			filesToBeEnriched.remove(0);
			SearchAndReplace replacer = new SearchAndReplace();
			
				for (File i: filesToBeEnriched){
					if (i.getName().contentEquals("service.xsd")==true
						|| i.getName().contentEquals("service.xsd.doc.html")==true
						||i.getName().contentEquals("template.abstract.wsdl")==true){
						
						int firstDotIndex = i.getName().indexOf(".");
						String extension = i.getName().substring(firstDotIndex, i.getName().length());
						
						if (i.getName().contentEquals("service.xsd") || i.getName().contentEquals("service.xsd.doc.html")){
						//Replace the contents of the files with the desired values
						replacer.doSearchAndReplace(i,operation.getOperationFolder()+"\\Resources\\InternalResources\\Schemas",parsedValues[0],parsedValues[1],extension);
						}
						else{
							replacer.doSearchAndReplace(i,operation.getOperationFolder()+"\\Resources\\InternalResources\\WSDL",parsedValues[0],parsedValues[1],extension);
						}
					}
					
				}
			
	}
}
	

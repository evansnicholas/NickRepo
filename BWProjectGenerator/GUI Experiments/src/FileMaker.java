import java.io.File;


public class FileMaker {

	FileMaker(){}
	
	public String makeFolder(String folderName){
		
		File enrichedFolder = new File("c:\\Users\\iggo\\Desktop\\"+folderName);
		enrichedFolder.mkdirs();
		return("c:\\Users\\iggo\\Desktop\\"+folderName);
		
	}
	
}

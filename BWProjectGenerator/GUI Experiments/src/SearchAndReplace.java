import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;


public class SearchAndReplace {


	
	SearchAndReplace(){}
	
	private static String readFileAsString(File file) throws java.io.IOException{
		
	StringBuffer fileData = new StringBuffer(1000);
	BufferedReader reader = new BufferedReader(new FileReader(file));
	char[] buf = new char[1024];
	int numRead=0;
	while((numRead=reader.read(buf)) != -1){
	String readData = String.valueOf(buf, 0, numRead);
	fileData.append(readData);
	buf = new char[1024];
	}
	reader.close();
	return fileData.toString();
	
	}
	
	public void doSearchAndReplace(File file, String destFolder, String serviceName, String serviceVersion, String extension){
			String contents = null;
			String newContents = null;
			try{
			contents = this.readFileAsString(file);
			newContents = contents.replace("{SERVICE_NAME}", serviceName);
			newContents = newContents.replace("{SERVICE_VERSION}", serviceVersion);
			//System.out.println(file.getName());
			
			if (file.getName().contentEquals("service.xsd")){
				
				newContents = newContents.replace("schemaLocation=\"cmm_2.xsd\"", "schemaLocation=\"../../../../../CMM/Resources/Schemas/cmm_2.xsd\"");
				
			}
			
			//System.out.print(newContents);
			}catch(IOException e){}		
			
			BufferedWriter writer = null;
			try
			{
				writer = new BufferedWriter(new FileWriter(destFolder+"\\"+serviceName+"_"+serviceVersion+extension));
				writer.write(newContents);
				

			}
			catch (IOException e){
				System.out.println(e.getMessage());
				}
			finally{
				try{
					if ( writer != null)
						writer.close( );
				}
				catch ( IOException e){}
		    }			
	}

}


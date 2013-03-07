import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


public class Unzip {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		
		Unzip unzip = new Unzip();
		try{
		unzip.extractFolder(args[0]);
		}catch(Exception e){}
	}*/
	
	static public ArrayList extractFolder(String zipFile) throws ZipException, IOException 
	{
		Boolean isFirstEntry = true;
	    ArrayList neededFiles = new ArrayList();
		
		//System.out.println(zipFile);
	    
		int BUFFER = 2048;
	    File file = new File(zipFile);

	    ZipFile zip = new ZipFile(file);
	    String newPath = zipFile.substring(0, zipFile.length() - 4);
	    
	    //TEST
	    //System.out.println(newPath);

	    new File(newPath).mkdir();
	    Enumeration zipFileEntries = zip.entries();
	    

	    // Process each entry
	    while (zipFileEntries.hasMoreElements())
	    {
	        // grab a zip file entry
	        ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	        String currentEntry = entry.getName();
	     
	        
	        //Get just the file name
	        String currentEntryFileName = currentEntry.substring(currentEntry.lastIndexOf("/")+1, currentEntry.length());
	        
	        
	        File destFile = new File(newPath, currentEntry);
	        //System.out.println(destFile.getAbsolutePath());
	        //destFile = new File(newPath, destFile.getName());
	        File destinationParent = destFile.getParentFile();

	        // create the parent directory structure if needed
	        destinationParent.mkdirs();
	        
	        if (   isFirstEntry==true
	        	||	currentEntryFileName.contentEquals("service.xsd")==true 
	        	|| currentEntryFileName.contentEquals("service.xsd.doc.html")==true
	        	|| currentEntryFileName.contentEquals("cmm_2.xsd")==true
	        	||currentEntryFileName.contentEquals("cmm_2.xsd.doc.html")
	        	||currentEntryFileName.contentEquals("template.abstract.wsdl")==true){
	        	
	        	neededFiles.add(destFile);
	        	isFirstEntry=false;
	        	
	        }

	        if (!entry.isDirectory())
	        {
	            BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
	            int currentByte;
	            
	            // establish buffer for writing file
	            byte data[] = new byte[BUFFER];

	            // write the current file to disk
	            FileOutputStream fos = new FileOutputStream(destFile);
	            BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

	            // read and write until last byte is encountered
	            while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
	                dest.write(data, 0, currentByte);
	            }
	            dest.flush();
	            dest.close();
	            is.close();
	        }

	        if (currentEntry.endsWith(".zip"))
	        {
	            // found a zip file, try to open
	            extractFolder(destFile.getAbsolutePath());
	        }
	    }
	    
	    return neededFiles;
	}

}

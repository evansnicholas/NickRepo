 import java.io.*;
import java.util.zip.*;

class  testZipFiles 
{
    public static void main(String[] args) 
    {
         if (args.length != 1) 
        {
            System.out.println("Usage: java testFiles [zipfile path] ");
            return;
        }
        try
        {
            String filename = args[0];
            testZipFiles list = new testZipFiles( );
            list.getZipFiles(filename);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void getZipFiles(String filename)
    {
        try
        {
            String destinationname = "c:\\Users\\iggo\\Desktop\\Unzipped\\";
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream(new FileInputStream(filename));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null) 
            { 
                //for each entry to be extracted
                String entryName = zipentry.getName();
                System.out.println("entryname "+entryName);
                int n;
                FileOutputStream fileoutputstream;
                File newFile = new File(filename.substring(0, filename.length() - 4), entryName);
                String directory = newFile.getParent();
                
                File destinationParent = newFile.getParentFile();
                destinationParent.mkdirs();
                
                if(directory == null)
                {
                    if(newFile.isDirectory())
                        break;
                }
                
                fileoutputstream = new FileOutputStream(
                   newFile);             

                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);

                fileoutputstream.close(); 
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
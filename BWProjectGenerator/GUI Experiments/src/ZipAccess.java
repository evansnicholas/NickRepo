import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipAccess
{
   public static void main(String[] args) throws IOException
   {
      if (args.length != 1)
      {
         System.err.println("usage: java ZipAccess zipfile");
         return;
      }
      try (ZipInputStream zis =
              new ZipInputStream(new FileInputStream(args[0])))
      {
         byte[] buffer = new byte[4096];
         ZipEntry ze;
         while ((ze = zis.getNextEntry()) != null)
        	
         {
            System.out.println("Extracting: "+ze);
            try (FileOutputStream fos = new FileOutputStream("c:\\Users\\iggo\\Desktop\\Unzipped\\"+ze.getName()))            {
               int numBytes;
               while ((numBytes = zis.read(buffer, 0, buffer.length)) != -1)
                  fos.write(buffer, 0, numBytes);
            }
            zis.closeEntry();
         }
      }
   }
}

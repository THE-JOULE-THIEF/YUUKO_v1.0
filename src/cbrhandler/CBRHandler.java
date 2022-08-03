package cbrhandler;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;

public class CBRHandler {
   public boolean convertToPdf(String select_from, String dest) {
      return this.convert(select_from, this.retriveFileList(select_from), dest);
   }

   private byte[] retriveImageData(String zipfilename, String filename) {
      try {
         return (new BufferedInputStream(Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\etc\\UnRAR.exe p \"" + zipfilename + "\" \"" + filename + "\"").getInputStream())).readAllBytes();
      } catch (Exception var4) {
         return null;
      }
   }

   private boolean convert(String zipdestination, LinkedList<String> filelist, String pdfdestination) {
      try {
         Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream(pdfdestination));
         document.open();

         for(int i = 0; i < filelist.size(); ++i) {
            Image image = Image.getInstance(this.retriveImageData(zipdestination, (String)filelist.get(i)));
            document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
            document.setMargins(0.0F, 0.0F, 0.0F, 0.0F);
            document.newPage();
            image.setAbsolutePosition(0.0F, 0.0F);
            document.add(image);
         }

         document.close();
         return true;
      } catch (Exception var7) {
         return false;
      }
   }

   private LinkedList<String> retriveFileList(String path) {
      LinkedList<String> filelist = new LinkedList<String>();

      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String(System.getProperty("user.dir") + "\\etc\\UnRAR.exe vb \"" + path + "\"")).getInputStream()));

         while(true) {
            String line;
            do {
               if ((line = reader.readLine()) == null) {
                  Collections.sort(filelist);
            	  return filelist;
               }

               line = line.trim();
            } while(!line.endsWith(".tif") && !line.endsWith(".tiff") && !line.endsWith(".bmp") && !line.endsWith(".jpg") && !line.endsWith(".jpeg") && !line.endsWith(".gif") && !line.endsWith(".png") && !line.endsWith(".eps") && !line.endsWith(".raw") && !line.endsWith(".cr2") && !line.endsWith(".nef") && !line.endsWith(".ofr") && !line.endsWith(".sr2"));

            filelist.add(line);
         }
      } catch (Exception var5) {
         return null;
      }
   }

   public boolean writeAsImage(byte[] imgdata, String destination) {
      try {
         FileOutputStream fos = new FileOutputStream(new File(destination));
         fos.write(imgdata);
         fos.close();
         return true;
      } catch (Exception var4) {
         return false;
      }
   }
}
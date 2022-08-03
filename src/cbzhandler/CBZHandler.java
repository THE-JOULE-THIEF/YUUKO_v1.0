package cbzhandler;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CBZHandler {
   public boolean convertToPdf(String zipdestination, String pdfdestination) {
      try {
         LinkedList<String> filelist = new LinkedList<String>();
         ZipInputStream zin = new ZipInputStream(new FileInputStream(zipdestination));
         ZipEntry entry = zin.getNextEntry();
         Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream(pdfdestination));
         document.open();

         while(entry != null) {
            if (!entry.isDirectory()) {
               filelist.add(entry.getName());
            }

            zin.closeEntry();
            entry = zin.getNextEntry();
         }

         Collections.sort(filelist);
         zin.close();
         zin = new ZipInputStream(new FileInputStream(zipdestination));
         entry = zin.getNextEntry();
         int i = 0;

         while(entry != null) {
            if (!entry.isDirectory() && entry.getName().trim().equals(((String)filelist.get(i)).trim())) {
               Image image = Image.getInstance(zin.readAllBytes());
               document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
               document.setMargins(0.0F, 0.0F, 0.0F, 0.0F);
               document.newPage();
               image.setAbsolutePosition(0.0F, 0.0F);
               document.add(image);
               ++i;
            }

            zin.closeEntry();
            entry = zin.getNextEntry();
            if (entry == null) {
               zin.close();
               zin = new ZipInputStream(new FileInputStream(zipdestination));
               entry = zin.getNextEntry();
            }

            if (i == filelist.size()) {
               break;
            }
         }

         zin.close();
         document.close();
         return true;
      } catch (Exception var9) {
         return false;
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
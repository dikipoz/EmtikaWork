package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExtractZipArchive {

	public static void extractZipArchive(String dirPathSource, String dirPathDestination, String filename){
		
		try{
			AllWork.getTextArea().append(" Распаковка  " + filename.toString().toLowerCase() + " ...    ");
			Charset CP866 = Charset.forName("CP866");
			ZipFile zip = new ZipFile(dirPathSource + filename, CP866);
		    Enumeration<? extends ZipEntry> entries = zip.entries();
		    LinkedList<ZipEntry> zfiles = new LinkedList<ZipEntry>();
		    while (entries.hasMoreElements()) {
		      ZipEntry entry = (ZipEntry) entries.nextElement();
		      if (entry.isDirectory()) {
		        new File(dirPathDestination + entry.getName()).mkdir();
		      } else {
		        zfiles.add(entry);
		      }
		    }
		    for (ZipEntry entry : zfiles) {
		      InputStream in = zip.getInputStream(entry);
		      OutputStream out = new FileOutputStream(dirPathDestination + entry.getName());
		      byte[] buffer = new byte[1024];
		      int len;
		      RepaintProgressBar.repaintProgressBar(1);
		      while ((len = in.read(buffer)) >= 0){
		    	  out.write(buffer, 0, len);
		   	  }
		      in.close();
		      out.close();
		      }
		    zip.close();
		    AllWork.getTextArea().append("готово \n");
		} catch (IOException ex){
			AllWork.getTextArea().append("ошибка \n");
			AllWork.setErrList(filename.toString() + " распаковка ");
			AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
			ex.printStackTrace();
		}
	}
}

package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipArchive{
	
	
	public static void createZipArchive(File f, String[] sDirList, String destPath, String destFile){
		File tempfile;  
		ZipOutputStream zos = null;
		    
		try{    
			tempfile = new File(destPath + destFile + ".zip");
			zos = new ZipOutputStream(new FileOutputStream(tempfile));
			zos.setLevel(Deflater.DEFAULT_COMPRESSION);
		}   
		catch(Exception e){
			AllWork.getTextArea().append("ошибка \n");
			AllWork.setErrList(e.getMessage() + "   ошибка");
			AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
		}
		
		createZip(f, zos, sDirList, destFile);
	}
	
	
	private static void createZip(File f, ZipOutputStream zos, String[] sDirList, String destFile){
		
		try{    
			for(int i = 0; i < sDirList.length; i++){
			    if(new File(f + File.separator + sDirList[i]).isFile()){
			    	RepaintProgressBar.repaintProgressBar(1);
			    	AllWork.getTextArea().append("Упаковка  " + sDirList[i] + "  в  " + destFile + ".zip...  ");
			    	addFileToZip(zos, f + File.separator, sDirList[i]);
			    	//System.out.println(f + "    и    " + f + File.separator);
			    	AllWork.getTextArea().append("готово \n");
			    }
			  }  
			  zos.close();
			  
		}catch(Exception e){
			AllWork.getTextArea().append("ошибка \n");
			AllWork.setErrList(e.getMessage() + "   ошибка\n");
			AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
		}
	}
	
	private static void addFileToZip(ZipOutputStream zos, String szPath, String szName) throws Exception{
			
			RepaintProgressBar.repaintProgressBar(2);
			ZipEntry ze = new ZipEntry(szName);
			zos.putNextEntry(ze);
			FileInputStream fis = new FileInputStream(szPath + szName);
			byte[] buf = new byte[fis.available()];
			int nLength;
			while(true){
				nLength = fis.read(buf);
					if(nLength < 0)
					    break;
				zos.write(buf, 0, nLength);
				
			}
			fis.close();
			zos.closeEntry();
		}
	
}

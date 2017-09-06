package ru.zaoemtika.EmtikaWork;

import java.io.File;

public class CreateFileNameArray {
	public static String[] createFileNameArray(File f){
		String[] sDirList = f.list();
		if(!f.exists()){
			AllWork.getTextArea().append("ошибка \n");
		}
		if(!f.isDirectory()){
			AllWork.getTextArea().append("ошибка \n");
		}
		return sDirList;
	}
}
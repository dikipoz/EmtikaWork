package ru.zaoemtika.EmtikaWork;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Delete {

	
	public Delete(String pathDir){
		delete2(pathDir);
	}
	
	public Delete(String pathSource, String nameFile){
		delete(pathSource, nameFile);
	}
	
	private static void delete(String pathSource, String nameFile) {
		AllWork.getTextArea().append(" Удаляем  " + nameFile.toLowerCase() + " ...   ");
		try {
			Files.delete(Paths.get(pathSource.toString().toUpperCase() + nameFile));
			AllWork.getTextArea().append("готово\n");
			AllWork.getTextArea().setCaretPosition(AllWork.getTextArea().getText().length());
		} catch (IOException e) {
			AllWork.getTextArea().append("ошибка\n");
			AllWork.getTextArea().setCaretPosition(AllWork.getTextArea().getText().length());
			e.printStackTrace();
		}

	}
	
	private static void delete2(String pathSource){
		Path path = Paths.get(pathSource);
		 try {
			 Files.walkFileTree(path, new MyFileVisitor()); 
            } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	}
}
class MyFileVisitor extends SimpleFileVisitor<Path> { 
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) throws IOException { 
    	Files.delete(path.normalize());
        return FileVisitResult.CONTINUE; 
    } 
} 

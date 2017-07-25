package ru.zaoemtika.EmtikaWork;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class SimplyCopy {
	public static void simplyCopy(String pathS, String pathD) {

		Path pathSource = Paths.get(pathS);
		Path pathDestination = Paths.get(pathD);

		try {
			Files.walkFileTree(pathSource, new MyFileCopy(pathSource, pathDestination));
		} catch (IOException e) {
			AllWork.getTextArea().append(" �� ������ ���� " + e.getMessage() + "   ������!!!!\n");
			AllWork.setErrList(e.getMessage() + "  ������");
			AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
			//e.printStackTrace();
		}
	}
}

class MyFileCopy extends SimpleFileVisitor<Path> {
	private Path source, destination;

	public MyFileCopy(Path s, Path d) {
		source = s;
		destination = d;
	}

	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) throws IOException {

		RepaintProgressBar.repaintProgressBar(1);
		Path newd = destination.resolve(source.relativize(path));
		
		if (destination.toString().toLowerCase().contains(AllWork.TEMP_DIR.toLowerCase()) || destination.toString().toLowerCase().contains("roaming")){
		AllWork.getTextArea()
				.append(" �����������  " + path.getFileName().toString().toLowerCase() +  "  �  �������  FTP...   ");
		} else {
			AllWork.getTextArea()
			.append(" �����������  " + path.getFileName().toString().toLowerCase()  + "  �  " + destination.toString().toLowerCase() +  " ...   ");
		}
		try {
			Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
			AllWork.getTextArea().append("������ \n");
		} catch (IOException e) {
			try {
				AllWork.getTextArea().append("... ������� ���������� ����������� ...\n");
				Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
				AllWork.getTextArea().append("������ \n");
			} catch (IOException ex) {
				AllWork.getTextArea().append("������ \n");
				AllWork.setErrList(ex.getMessage() + " ��������� ����������� ");
				AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
				e.printStackTrace();
			}
			/*AllWork.getTextArea().append("������ \n");
			AllWork.setErrList(e.getMessage() + " ����������� ");
			AllWork.setErrorsCount(AllWork.getErrorsCount() + 1);
			e.printStackTrace();*/
		}
		return FileVisitResult.CONTINUE;
	}

}
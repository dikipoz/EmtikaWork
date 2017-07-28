package ru.zaoemtika.EmtikaWork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

public abstract class CreateDirectory {
	
	public static void createDirectory(Path crDirectory){
		try {
			Files.createDirectory(crDirectory);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Невозможно подключить ресурс", "Ошибка",
					JOptionPane.OK_OPTION, null);
			e.printStackTrace();
		}
	}
}
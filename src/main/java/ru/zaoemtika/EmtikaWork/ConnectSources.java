package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class ConnectSources extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void connectSources() throws IOException {
		Path dir;
/*
		dir = Paths.get("c:" + File.separator + "paradise" + File.separator + "paradise.exe");
		if (!Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
			JOptionPane.showMessageDialog(null, "Не найден Paradise. Ну и хрен с ним.", "Ошибка", JOptionPane.OK_OPTION, null);
			//System.exit(0);
		}*/

		//UIManager.put("OptionPane.background", new Color(126,21,20));
		
		
		dir = Paths.get("i:");
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			JOptionPane.showMessageDialog(null, "<html><font color=#ffffdd>Не найден диск I. Обратитесь к администратору</font>", "Ошибка",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Cancel-48.png")));
			System.exit(0);
		}

		//System.out.println(System.getProperty("user.dir"));
		dir = Paths.get("z:");
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			Process p = Runtime.getRuntime().exec("cmd.exe /c net use z: " + File.separator +  File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator + "ftp");
			// Process p = Runtime.getRuntime().exec("cmd.exe /c subst z:
			// C:\\tmp\\N_EMT");
			try {
				if (p.waitFor() == 0) {
					System.out.println("Подключен диск Z:");
				}
			} catch (InterruptedException e1) {
				AllWork.setErrList(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
		}

		/*dir = Paths.get("c:" + File.separator + "_111" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}*/

		/*if(Files.isDirectory(Paths.get("c:" + File.separator + "_111" + File.separator))){
			System.out.println("найдено");
			Files.delete(Paths.get("c:" + File.separator + "_111" + File.separator));
			}*/
		dir = Paths.get("c:" + File.separator + "base" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("c:" + File.separator + "base" + File.separator + "price" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator + Calendar.getInstance().get(Calendar.YEAR));
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "bs" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}

		dir = Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "sk" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}

		dir = Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "def" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}

		dir = Paths.get("i:" + File.separator + "case" + File.separator + "deficit" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "base" + File.separator + "old_ch" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "FIZ" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		dir = Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "PRICE" + File.separator);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
	}
}
package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public abstract class ConnectSources{

	public static void connectSources() throws IOException {
		
		if (!Files.isDirectory(Paths.get("i:"), LinkOption.NOFOLLOW_LINKS)) {
			JOptionPane.showMessageDialog(null, "<html><font color=#ffffdd>Не найден диск I. Обратитесь к администратору</font>", "Ошибка",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Cancel-48.png")));
			System.exit(0);
		}

		if (!Files.isDirectory(Paths.get("z:"), LinkOption.NOFOLLOW_LINKS)) {
			Process p = Runtime.getRuntime().exec("cmd.exe /c net use z: " + File.separator +  File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator + "ftp");
			try {
				if (p.waitFor() == 0) {
					System.out.println("Подключен диск Z:");
				}
			} catch (InterruptedException e1) {
				AllWork.setErrList(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
		}

		if (!Files.isDirectory(Paths.get("c:" + File.separator + "base" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("c:" + File.separator + "base" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("c:" + File.separator + "base" + File.separator + "price" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("c:" + File.separator + "base" + File.separator + "price" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator + Calendar.getInstance().get(Calendar.YEAR)), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "counts" + File.separator + Calendar.getInstance().get(Calendar.YEAR)));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "bs" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "bs" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator));
		}

		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "sk" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "sk" + File.separator));
		}

		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "def" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "modem" + File.separator + "def" + File.separator));
		}

		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "deficit" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "deficit" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "base" + File.separator + "old_ch" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "base" + File.separator + "old_ch" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "FIZ" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "FIZ" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "PRICE" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "es" + File.separator + "PRICE" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "003" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "003" + File.separator));
		}
		
		if (!Files.isDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "005" + File.separator), LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(Paths.get("i:" + File.separator + "case" + File.separator + "005" + File.separator));
		}
	}
}
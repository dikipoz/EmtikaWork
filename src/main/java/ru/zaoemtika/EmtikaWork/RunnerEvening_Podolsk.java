package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class RunnerEvening_Podolsk implements Runnable {
	private boolean doDeficit;
	private boolean doSklad;
	private boolean doClearDoc;

	private static volatile RunnerEvening_Podolsk instance = null;

	public static RunnerEvening_Podolsk getInstance() {
		return instance;
	}

	private RunnerEvening_Podolsk(String[] filial, JProgressBar progressBar, JTextArea textArea, boolean doDeficit,
			boolean doSklad, boolean doClearDoc) {
		AllWork.setProgressBar(progressBar);
		AllWork.setTextArea(textArea);
		AllWork.setFilial(filial);
		this.doDeficit = doDeficit;
		this.doSklad = doSklad;
		this.doClearDoc = doClearDoc;
	}

	public static RunnerEvening_Podolsk runnerEvening_Podolsk(String[] filial, JProgressBar progressBar,
			JTextArea textArea, boolean doDeficit, boolean doSklad, boolean doClearDoc) {
		if (instance == null) {
			synchronized (RunnerEvening.class) {
				if (instance == null)
					instance = new RunnerEvening_Podolsk(filial, progressBar, textArea, doDeficit, doSklad, doClearDoc);
			}
			return instance;
		}
		return null;
	}

	public void run() {
		AllWork.setErrorsCount(0);
		AllWork.getTextArea().setText(null);
		AllWork.getProgressBar().setMaximum(700);
		AllWork.getProgressBar().setValue(0);

		Path dir = Paths.get(AllWork.C_CASE);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		
		// Запуск программы M3N3
		processFilesFromFolder(new File(System.getProperties().getProperty("user.home") + File.separator + "AppData"
				+ File.separator + "Local" + File.separator + "Apps" + File.separator + "2.0" + File.separator));
		
		
		// копирование post.* из i:\case\003 в z:\defs
		SimplyCopy.simplyCopy(AllWork.I_CASE_003 + "post.db".toLowerCase(), AllWork.Z_DEFS + "post.db".toLowerCase());
		SimplyCopy.simplyCopy(AllWork.I_CASE_003 + "post.px".toLowerCase(), AllWork.Z_DEFS + "post.px".toLowerCase());
		
		// копирование дефицита в z:\defs
		if (doDeficit) {
			String[] defFiles = new String[4];
			defFiles[0] = "defemtik.db";
			defFiles[1] = "defemt.db";
			defFiles[2] = "defemtik.px";
			defFiles[3] = "defemt.px";
			for (int i = 0; i < defFiles.length; i++)
				SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[i], AllWork.C_CASE + defFiles[i]);

			CreateZipArchive.createZipArchive(new File(AllWork.C_CASE), defFiles, AllWork.C_CASE,
					"DEF_BASA".toLowerCase());

			for (int i = 0; i < defFiles.length; i++)
				SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[i], AllWork.I_BASE_PRICE + defFiles[i]);

			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + "defemt.db", AllWork.ZAOEMTIKA_RU_DFS_FTP + "COUNTS" + File.separator + "DEFS" + File.separator + AllWork.getFilial()[3]);
			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + "defemt.px", AllWork.ZAOEMTIKA_RU_DFS_FTP + "COUNTS" + File.separator + "DEFS" + File.separator + "defemt" + ".px");
			new Delete(AllWork.C_PRIC1B_64, "def_old.db");
			new Delete(AllWork.C_PRIC1B_64, "def_old.px");

			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[1], AllWork.C_PRIC1B_64 + "def_old.db");
			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[3], AllWork.C_PRIC1B_64 + "def_old.px");

			SimplyCopy.simplyCopy(AllWork.I_BASE_DOC_OLD, AllWork.I_BASE_DOC);
			new Delete(AllWork.I_BASE_DOC_OLD);

			SimplyCopy.simplyCopy(AllWork.C_CASE + "def_basa.zip", AllWork.Z_DEFS + "def_basa.zip".toLowerCase());

		}

		// копирование складов в z:\sklads
		if (doSklad) {
			CreateZipArchive.createZipArchive(new File(AllWork.I_BASE_PRICE), new String[] { "s1к20000.db" },
					AllWork.C_CASE, "SK_BASA".toUpperCase());
			SimplyCopy.simplyCopy(AllWork.C_CASE + "sk_basa.zip", AllWork.Z_SKLADS + "sk_basa.zip".toUpperCase());
		}

		File destPath = new File(AllWork.I_BASE_DOC);
		Path path = Paths.get(
				AllWork.Z_COUNTS + "pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
		String[] sDirList = CreateFileNameArray.createFileNameArray(destPath);
		if (sDirList.length > 0) {
			AllWork.getProgressBar().setMaximum(AllWork.getProgressBar().getMaximum() + sDirList.length);
			for (String dirContents : sDirList) {
				if (!(Files.isDirectory(Paths.get(AllWork.I_BASE_DOC + dirContents), LinkOption.NOFOLLOW_LINKS)) 
						&& (dirContents.substring(dirContents.length()-2, dirContents.length()).toLowerCase().equals("db")))
					SimplyCopy.simplyCopy(AllWork.I_BASE_DOC + dirContents, AllWork.I_BASE_OLD_CH + dirContents);
					
			}
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)) {
				SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + "pod_"
						+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
				
				// Разархивируем из z:\counts файл свертки на TEMP_DIR
				ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR,
						"pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
				
				// Копируем недостающее из i:\base\doc в TEMP_DIR
				for (String sdirlist : sDirList) {
					SimplyCopy.simplyCopy(AllWork.I_BASE_DOC + sdirlist, AllWork.TEMP_DIR + sdirlist);
				}
				// Удаляем из TEMP_DIR архив свертки
				new Delete(AllWork.TEMP_DIR,
						"pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
				// Создаем архив свертки из TEMP_DIR на ФТП
				sDirList = CreateFileNameArray.createFileNameArray(new File(AllWork.TEMP_DIR));
				CreateZipArchive.createZipArchive(new File(AllWork.TEMP_DIR), sDirList, AllWork.I_CASE_COUNTS,
						"pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2));
			} else {
				CreateZipArchive.createZipArchive(destPath, sDirList, AllWork.I_CASE_COUNTS,
						"pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2));
			}
			SimplyCopy.simplyCopy(
					AllWork.I_CASE_COUNTS + "pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2)
							+ ".zip",
					AllWork.Z_COUNTS + "pod_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
			if (doClearDoc) {
				
				new Delete(AllWork.I_BASE_DOC);
			}
		} else {
			AllWork.getProgressBar().setValue(24);
			JOptionPane.showMessageDialog(null,
					"каталог I:" + File.separator + "BASE" + File.separator + "DOC пустой.");
		}

		AllWork.getProgressBar().setValue(AllWork.getProgressBar().getMaximum());
		
		new Done(false, AllWork.getErrorsCount(), AllWork.getFilial()[0], "\u0420\u0430\u0437\u0434\u0430\u0447\u0430 \u0438 \u0441\u0432\u0435\u0440\u0442\u043A\u0430   ");
		instance = null;

	}
	
	public void processFilesFromFolder(File folder) {
		File[] folderEntries = folder.listFiles();
		for (File entry : folderEntries) {
			if (entry.isDirectory()) {
				processFilesFromFolder(entry);
				continue;
			}
			if (entry.isFile()) {
				if (entry.getAbsolutePath().contains("af44965ba1dc9d18_0001.0000_dfdd96ce11a027c0")) {
					if (entry.getName().equals("testM3N3.exe"))
						try {
							Process p = Runtime.getRuntime().exec(entry.getAbsolutePath());
							while (p.isAlive()) {

							}
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		}
	}
}
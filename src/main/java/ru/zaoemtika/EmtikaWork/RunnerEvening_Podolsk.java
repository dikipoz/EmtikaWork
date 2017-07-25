package ru.zaoemtika.EmtikaWork;

import java.io.File;
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
		AllWork.getProgressBar().setMaximum(300);
		AllWork.getProgressBar().setValue(0);

		Path dir = Paths.get(AllWork.C_CASE);
		if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			CreateDirectory.createDirectory(dir);
		}
		System.out.println(doDeficit);
		System.out.println(doSklad);
		System.out.println(doClearDoc);
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

			new Delete(AllWork.C_PRIC1B_64, "def_old.db");
			new Delete(AllWork.C_PRIC1B_64, "def_old.px");

			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[1], AllWork.C_PRIC1B_64 + "def_old.db");
			SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + defFiles[3], AllWork.C_PRIC1B_64 + "def_old.px");

			SimplyCopy.simplyCopy(AllWork.I_BASE_DOC_OLD, AllWork.I_BASE_DOC);
			new Delete(AllWork.I_BASE_DOC_OLD);

			SimplyCopy.simplyCopy(AllWork.C_CASE + "def_basa.zip", AllWork.Z_DEFS + "def_basa.zip".toLowerCase());

		}

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
		new Done(false, AllWork.getErrorsCount(), AllWork.getFilial()[0], "Вечерняя   ");
		instance = null;

	}
}

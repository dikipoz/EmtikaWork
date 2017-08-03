package ru.zaoemtika.EmtikaWork;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class RunnerMorning_Podolsk implements Runnable {

	private static volatile RunnerMorning_Podolsk instance = null;

	private RunnerMorning_Podolsk(String[] filial, JProgressBar progressBar, JTextArea textArea) {
		AllWork.setProgressBar(progressBar);
		AllWork.setTextArea(textArea);
		AllWork.setFilial(filial);
	}

	public static RunnerMorning_Podolsk runnerMorning_Podolsk(String[] filial, JProgressBar progressBar,
			JTextArea textArea) {
		if (instance == null) {
			synchronized (RunnerMorning.class) {
				if (instance == null)
					instance = new RunnerMorning_Podolsk(filial, progressBar, textArea);
			}
			return instance;
		}
		return null;
	}

	public static RunnerMorning_Podolsk getInstance() {
		return instance;
	}

	public void run() {
		KillParadox.killParadox(AllWork.pathProgramFiles);
		AllWork.setErrorsCount(0);
		AllWork.getTextArea().setText(null);
		AllWork.getProgressBar().setMaximum(140);
		AllWork.getProgressBar().setValue(0);
		Path path;

		// Копирование из z_defs в c:\_111, распаковка и копирование в
		// i:\base\price
		{
			String[] strSuff = { "zip", "db" };
			for (String str : strSuff) {
				AllWork.getListAllSuffix().clear();
				GetAllSuffixFile.getAllSuffixFile(AllWork.Z_DEFS, str);
				for (String zipfilename : AllWork.getListAllSuffix()) {
					path = Paths.get(AllWork.Z_DEFS + zipfilename);
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)
							&& !zipfilename.toLowerCase().equals("def_basa.zip")
							&& !zipfilename.toLowerCase().contains("post")) {
						switch (str) {
						case "zip":
							SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipfilename);
							ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipfilename);
							new Delete(AllWork.TEMP_DIR, zipfilename);
							break;
						case "db":
							SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipfilename);
							break;
						}
					} else
						System.out.println("игнорирование " + zipfilename);
				}
			}
		}

		//System.exit(0);
		// Копирование из z:\sklads с последующей распаковкой в i:\base\price
		AllWork.getListAllSuffix().clear();
		GetAllSuffixFile.getAllSuffixFile(AllWork.Z_SKLADS, "zip");
		for (String zipfilename : AllWork.getListAllSuffix()) {
			path = Paths.get(AllWork.Z_SKLADS + zipfilename);
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)
					&& !zipfilename.toLowerCase().equals("sk_basa.zip")) {
				SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipfilename);
				ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipfilename);
				new Delete(AllWork.TEMP_DIR, zipfilename);
			} else
				System.out.println("игнорирование " + zipfilename);
		}
		SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_BASE_PRICE);
		AllWork.getProgressBar().setValue(AllWork.getProgressBar().getMaximum());
		new Done(true, AllWork.getErrorsCount(), AllWork.getFilial()[0], "\u0423\u0442\u0440\u0435\u043D\u043D\u044F\u044F   ");

		instance = null;
	}
}

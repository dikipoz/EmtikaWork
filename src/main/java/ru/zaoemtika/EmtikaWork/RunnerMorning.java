package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class RunnerMorning implements Runnable {
	static String[] zipFileNames = new String[] { "basa_sum.zip", "buh.zip", "ddd.zip", "prod.zip", "update.zip",
			"nacen.zip", "skk_005.zip", "n_emt.zip", "bs" + CurrentDate.currentDate(true) + ".zip",
			"bs" + CurrentDate.currentDate(false) + ".zip", "es.zip" };

	private static volatile RunnerMorning instance = null;

	private RunnerMorning(String[] filial, JProgressBar progressBar, JTextArea textArea) {
		AllWork.setProgressBar(progressBar);
		AllWork.setTextArea(textArea);
		AllWork.setFilial(filial);
	}

	public static RunnerMorning runnerMorning(String[] filial, JProgressBar progressBar, JTextArea textArea) {
		if (instance == null) {
			synchronized (RunnerMorning.class) {
				if (instance == null)
					instance = new RunnerMorning(filial, progressBar, textArea);
			}
			return instance;
		}
		return null;
	}

	public static RunnerMorning getInstance() {
		return instance;
	}

	public void run() {
		KillParadox.killParadox(AllWork.pathProgramFiles);
		AllWork.setErrorsCount(0);
		AllWork.getTextArea().setText(null);
		AllWork.getProgressBar().setMaximum(1700);
		AllWork.getProgressBar().setValue(0);
		Path path;
		
		Map<String, Boolean> staff = ReadMapFromEWUPD.readFromEWUPD(zipFileNames);
		// Распаковка NACEN и SKK_005 в папку I_CASE_005
		
		AllWork.getTextArea().setText(null);
		{
			for (Map.Entry<String, Boolean> s : staff.entrySet()) {
				if (s.getKey().toLowerCase().contains("nacen") && !s.getValue()) {
					path = Paths.get(AllWork.Z_TO_ALL + "nacen.zip");
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
						SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + "nacen.zip");
						ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, "nacen.zip");
						new Delete(AllWork.TEMP_DIR, "nacen.zip");
					}
				} else {
					if (s.getKey().toLowerCase().contains("nacen") && s.getValue()) {
						AllWork.getTextArea().append(" " + s.getKey().toLowerCase() + "  не  изменялся...  пропущен\n");
						RepaintProgressBar.repaintProgressBar(50);
					}
				}
			}
			for (Map.Entry<String, Boolean> s : staff.entrySet()) {
				if (s.getKey().toLowerCase().contains("skk_005") && !s.getValue()) {
					path = Paths.get(AllWork.Z_TO_ALL + "skk_005.zip");
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
						SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + "skk_005.zip");
						ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, "skk_005.zip");
						new Delete(AllWork.TEMP_DIR, "skk_005.zip");
					}
				}else {
					if (s.getKey().toLowerCase().contains("skk_005") && s.getValue()) {
						AllWork.getTextArea().append(" " + s.getKey().toLowerCase() + "  не  изменялся...  пропущен\n");
						RepaintProgressBar.repaintProgressBar(50);
					}
				}

			}
			SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_005);
		}

		// Копирование из z_defs с распаковкой и копированием в i:\base\price
		{
			String[] strSuff = { "zip", "db" };
			for (String str : strSuff) {
				AllWork.getListAllSuffix().clear();
				GetAllSuffixFile.getAllSuffixFile(AllWork.Z_DEFS, str);
				for (String zipfilename : AllWork.getListAllSuffix()) {
					path = Paths.get(AllWork.Z_DEFS + zipfilename);
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)
							&& !zipfilename.toLowerCase().equals("def_" + AllWork.getFilial()[6] + ".zip")
							&& !zipfilename.toLowerCase().equals("sur_" + AllWork.getFilial()[6] + ".db")
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
		// Копирование из z:\sklads с последующей распаковкой в i:\base\price
		AllWork.getListAllSuffix().clear();
		GetAllSuffixFile.getAllSuffixFile(AllWork.Z_SKLADS, "zip");
		for (String zipfilename : AllWork.getListAllSuffix()) {
			path = Paths.get(AllWork.Z_SKLADS + zipfilename);
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)
					&& !zipfilename.equals("sk_" + AllWork.getFilial()[6] + ".zip")) {
				SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipfilename);
				ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipfilename);
				new Delete(AllWork.TEMP_DIR, zipfilename);
			}
		}
		// ---------------- Копирование из Z:\TO_ALL с последующей распаковкой в i:\base\price
		{
			AllWork.getTextArea().append("*** Разархивируем все из z:\\to_all в i:\\base\\price ***\n");
			for(Map.Entry<String, Boolean> s : staff.entrySet()){
				for (String zipfilename : zipFileNames) {
					path = Paths.get(AllWork.Z_TO_ALL + zipfilename);
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)
							&& s.getKey().toLowerCase().contains(zipfilename)
							&& !s.getKey().toLowerCase().contains("nacen")
							&& !s.getKey().toLowerCase().contains("skk_005")
							&& !s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(true))
							&& !s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(false))
							&& !s.getKey().toLowerCase().contains("es.zip")
							&& !s.getValue()) {
						SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipfilename);
						ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipfilename);
						new Delete(AllWork.TEMP_DIR, zipfilename);
					}else {
						if (s.getKey().toLowerCase().contains(zipfilename) && s.getValue()) {
							AllWork.getTextArea().append(" " + s.getKey().toLowerCase() + "  не  изменялся...  пропущен\n");
							RepaintProgressBar.repaintProgressBar(50);
						}
					}
				}
			}
		}
		
		SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_BASE_PRICE);
		new Delete(AllWork.TEMP_DIR);
		// Новая хрень с ES.ZIP
		{
			AllWork.getTextArea().append("*** Разархивируем es.zip в i:\\case\\ES ***\n");
			for (Map.Entry<String, Boolean> s : staff.entrySet()) {
				if (s.getKey().toLowerCase().contains("es.zip") && !s.getValue()) {
					path = Paths.get(AllWork.Z_TO_ALL + "es.zip");
					if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
						SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + "es.zip");
						ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, "es.zip");
						new Delete(AllWork.TEMP_DIR, "es.zip");
						SimplyCopy.simplyCopy(AllWork.TEMP_DIR + "FIZ" + File.separator, "c:\\base\\price");
						SimplyCopy.simplyCopy(AllWork.TEMP_DIR + "PRICE" + File.separator, "c:\\base\\price");
						SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_ES);
						//System.exit(0);
						new Delete(AllWork.TEMP_DIR);
					}
				}else {
					if (s.getKey().toLowerCase().contains("es.zip") && s.getValue()) {
						AllWork.getTextArea().append(" " + s.getKey().toLowerCase() + "  не  изменялся...  пропущен\n");
						RepaintProgressBar.repaintProgressBar(50);
					}
				}

			}
		}
		
		if(Files.isDirectory(Paths.get(AllWork.TEMP_DIR + File.separator + "PRICE"), LinkOption.NOFOLLOW_LINKS)){
			SimplyCopy.simplyCopy(AllWork.TEMP_DIR + File.separator + "PRICE", "C:\\BASE\\PRICE");
			new Delete(AllWork.TEMP_DIR + File.separator + "PRICE");
		}
		if(Files.isDirectory(Paths.get(AllWork.TEMP_DIR + File.separator + "FIZ"), LinkOption.NOFOLLOW_LINKS)){
			new Delete(AllWork.TEMP_DIR + File.separator + "FIZ");
		}
		
		SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_BASE_PRICE);
		
		//---------------- Копируем BSM_35.ssl
		SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "35.bat", "i:\\case\\003\\35.bat");
		
		SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "BSM_35.ssl", "i:\\case\\003\\BSM_35.ssl");
		
		SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "new_par.bat", "i:\\case\\003\\new_par.bat");
		
		SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "_arm_md.fsl", "c:\\base\\price\\_arm_md.fsl");
		
		/*if (!Files.exists(Paths.get("i:\\case\\003\\35.bat".toUpperCase()), LinkOption.NOFOLLOW_LINKS)){
			SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
					+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "35.bat", "i:\\case\\003\\35.bat");
				System.out.println("копируем 35.bat");
			}else{
				System.out.println("не копируем 35.bat");
			}
		
		if (!Files.exists(Paths.get("i:\\case\\003\\bsm_35.ssl".toUpperCase()), LinkOption.NOFOLLOW_LINKS)){
			SimplyCopy.simplyCopy(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + "BSM_35.ssl", "i:\\case\\003\\BSM_35.ssl");
			System.out.println("копируем bsm_35.ssl");
		}else{
			System.out.println("не копируем bsm_35");
		}*/
		// ---------------- Копируем POST в I:\CASE\003
		{
			path = Paths.get(AllWork.Z_DEFS + "post.db");
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				SimplyCopy.simplyCopy(path.toString(), AllWork.I_CASE_003 + "post.db");
			}
			path = Paths.get(AllWork.Z_DEFS + "post.px");
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				SimplyCopy.simplyCopy(path.toString(), AllWork.I_CASE_003 + "post.px");
			}
		}

		new Delete(AllWork.TEMP_DIR);
		
		// --------------- Копируем BSxxxxx.zip в c:\_111 с последующей распаковкой в i:\case\bs
		{
			for (Map.Entry<String, Boolean> s : staff.entrySet()) {
				if (Files.exists(Paths.get(AllWork.Z_TO_ALL + "bs" + CurrentDate.currentDate(true) + ".zip"), LinkOption.NOFOLLOW_LINKS)
						&& s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(true)) 
						&& !s.getValue()) {
					SimplyCopy.simplyCopy(AllWork.Z_TO_ALL + "bs" + CurrentDate.currentDate(true) + ".zip",
							AllWork.TEMP_DIR + "bs" + CurrentDate.currentDate(true) + ".zip");
					new Delete(AllWork.I_CASE_BS);
					ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR,
							"bs" + CurrentDate.currentDate(true) + ".zip");
					new Delete(AllWork.TEMP_DIR, "bs" + CurrentDate.currentDate(true) + ".zip");
					SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_BS);
				} else {
					if (!Files.exists(Paths.get(AllWork.Z_TO_ALL + "bs" + CurrentDate.currentDate(true) + ".zip"), LinkOption.NOFOLLOW_LINKS) 
							&& s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(false)) 
							&& !s.getValue()) {
						new Delete(AllWork.I_CASE_BS);
						SimplyCopy.simplyCopy(AllWork.Z_TO_ALL + "bs" + CurrentDate.currentDate(false) + ".zip",
								AllWork.TEMP_DIR + "bs" + CurrentDate.currentDate(false) + ".zip");
						ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR,
								"bs" + CurrentDate.currentDate(false) + ".zip");
						new Delete(AllWork.TEMP_DIR, "bs" + CurrentDate.currentDate(false) + ".zip");
						SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_BS);
					
					}
				}
			}
		}
		
		PostReindex.postReindex();
		
		AllWork.getProgressBar().setValue(AllWork.getProgressBar().getMaximum());
		new Done(true, AllWork.getErrorsCount(), AllWork.getFilial()[0], "Утренняя    ");

		instance = null;
	}
}

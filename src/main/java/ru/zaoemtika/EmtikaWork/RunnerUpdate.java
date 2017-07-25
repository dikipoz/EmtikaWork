package ru.zaoemtika.EmtikaWork;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class RunnerUpdate implements Runnable {

	private final String[] zipFileNames = new String[] { "UPDATE.ZIP", "nacen.zip",
			"bs" + CurrentDate.currentDate(true) + ".zip" };
	private boolean doUpdateInvoice;
	private boolean doUpdateNacenka;
	private boolean doUpdateBS;

	private static volatile RunnerUpdate instance = null;

	private RunnerUpdate(String[] filial, JProgressBar progressBar, JTextArea textArea, boolean doUpdateInvoice,
			boolean doUpdateNacenka, boolean doUpdateBS) {

		AllWork.setProgressBar(progressBar);
		AllWork.setTextArea(textArea);
		AllWork.setFilial(filial);
		this.doUpdateInvoice = doUpdateInvoice;
		this.doUpdateNacenka = doUpdateNacenka;
		this.doUpdateBS = doUpdateBS;
	}

	public static RunnerUpdate runnerUpdate(String[] filial, JProgressBar progressBar, JTextArea textArea,
			boolean doUpdateInvoice, boolean doUpdateNacenka, boolean doUpdateBS) {
		if (instance == null) {
			synchronized (RunnerUpdate.class) {
				if (instance == null)
					instance = new RunnerUpdate(filial, progressBar, textArea, doUpdateInvoice, doUpdateNacenka,
							doUpdateBS);
			}
			return instance;
		}
		return null;
	}

	public void run() {
		KillParadox.killParadox(AllWork.pathProgramFiles);
		Map<String, Boolean> staff = ReadMapFromEWUPD.readFromEWUPD(zipFileNames);

		if (!doUpdateInvoice && !doUpdateNacenka && !doUpdateBS) {
			JOptionPane.showMessageDialog(null, "<html><font color=#ffffdd>Выберите хотя бы один вид обновления</font>",
					"Внимание", JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Error-48.png")));
			instance = null;
		} else {

			AllWork.setErrorsCount(0);
			AllWork.getTextArea().setText(null);
			AllWork.getProgressBar().setValue(0);
			int maxPr = 0;
			if (doUpdateInvoice)
				maxPr += 140;
			if (doUpdateNacenka)
				maxPr += 80;
			if (doUpdateBS)
				maxPr += 110;
			if (doUpdateInvoice && doUpdateNacenka && doUpdateBS)
				maxPr = 400;
			AllWork.getProgressBar().setMaximum(maxPr);
			if (doUpdateInvoice) {
				for (Map.Entry<String, Boolean> s : staff.entrySet()) {
					if (s.getKey().toLowerCase().contains("update") && !s.getValue()) {
						Path path = Paths.get(AllWork.Z_TO_ALL + zipFileNames[0]);
						if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)) {
							SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipFileNames[0]);
							ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipFileNames[0]);
							new Delete(AllWork.TEMP_DIR, zipFileNames[0]);
							SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_BASE_PRICE);
							new Delete(AllWork.TEMP_DIR);
						}

					} else {
						if (s.getKey().toLowerCase().contains("update") && s.getValue()) {
							AllWork.getTextArea().append(s.getKey().toLowerCase() + "  не  изменялся...  пропущен\n");
							RepaintProgressBar.repaintProgressBar(100);
						}
					}
				}
				// AllWork.getTextArea().append("\n******************************************************\n");
			}

			if (doUpdateNacenka) {
				for (Map.Entry<String, Boolean> s : staff.entrySet()) {
					if (s.getKey().toLowerCase().contains("nacen") && !s.getValue()) {
						Path path = Paths.get(AllWork.Z_TO_ALL + zipFileNames[1]);
						if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)) {
							SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipFileNames[1]);
							ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, zipFileNames[1]);
							new Delete(AllWork.TEMP_DIR, zipFileNames[1]);
							SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_005);
							new Delete(AllWork.TEMP_DIR);
						}
						// AllWork.getTextArea().append("\n******************************************************\n");
						
					} else {
						if (s.getKey().toLowerCase().contains("nacen") && s.getValue()) {
							AllWork.getTextArea().append(s.getKey().toLowerCase()
									+ "  не  изменялся...  пропущен\n");
							RepaintProgressBar.repaintProgressBar(100);
						}
					}
				}
			}

			if (doUpdateBS) {

				Random random = new Random(36);
				Map<Integer, Integer> hashMap = new HashMap<>();

				for (int i = 0; i < 100; i++){
				    // Создадим число от 0 до 10
				    int number = random.nextInt(10);
				    Integer frequency = hashMap.get(number);
				    hashMap.put(number, frequency == null ? 1 : frequency + 1);
				}
				
					for (Map.Entry<String, Boolean> s : staff.entrySet()) {
						if (s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(true)) && !s.getValue()) {
							Path path = Paths.get(AllWork.Z_TO_ALL + zipFileNames[2]);
							if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)) {
								SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + zipFileNames[2]);
								new Delete(AllWork.I_CASE_BS);
								ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR,
										zipFileNames[2]);
								new Delete(AllWork.TEMP_DIR, zipFileNames[2]);
								SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_CASE_BS);
								SimplyCopy.simplyCopy(AllWork.TEMP_DIR, AllWork.I_BASE_PRICE);
							} else {
								JOptionPane.showMessageDialog(null,
										"Файл " + zipFileNames[2] + " еще не создан отделом цен.");
							}
							// AllWork.getTextArea().append("\n******************************************************\n");
							
						} else{
							if (s.getKey().toLowerCase().contains("bs" + CurrentDate.currentDate(true)) && s.getValue()) {
								AllWork.getTextArea().append(s.getKey().toLowerCase()
										+ "  не  изменялся...  пропущен\n");
								RepaintProgressBar.repaintProgressBar(100);
							}
						}
					}
			}
			
			PostReindex.postReindex();
			
			AllWork.getProgressBar().setValue(AllWork.getProgressBar().getMaximum());
			new Done(true, AllWork.getErrorsCount(), AllWork.getFilial()[0], "Обновление");
			instance = null;
		}
	}

	public static RunnerUpdate getInstance() {
		return instance;
	}
}

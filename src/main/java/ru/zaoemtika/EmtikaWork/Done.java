package ru.zaoemtika.EmtikaWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Map;
import java.net.InetAddress;
import java.nio.charset.Charset;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Done {

	public Done(Boolean doParadise, int errorCounts, String filial, String whatWork) {
		new Delete(AllWork.TEMP_DIR);
		AllWork.getListAllSuffix().clear();

		for (String suffix : AllWork.getFileExt()) {
			GetAllSuffixFile.getAllSuffixFile(AllWork.I_BASE_PRICE, suffix);
		}
		for (String suffix : AllWork.getListAllSuffix()) {
			new Delete(AllWork.I_BASE_PRICE, suffix);
		}
		File file = new File(File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator
				+ "ftp" + File.separator + "__Soft" + File.separator + "EWLog" + File.separator + SwingMainFrame.getNeedFiles()[6] + "_log.txt");
		Calendar c = Calendar.getInstance();
		StringBuilder date = new StringBuilder();
		date.append(
				(c.get(Calendar.DAY_OF_MONTH) > 9 ? c.get(Calendar.DAY_OF_MONTH) : "0" + c.get(Calendar.DAY_OF_MONTH)));
		date.append("-");
		date.append((c.get(Calendar.MONTH) + 1) > 9 ? c.get(Calendar.MONTH) + 1 : "0" + (c.get(Calendar.MONTH) + 1));
		// System.out.println("" + c.get(Calendar.MONTH) + 1);
		date.append("-");
		date.append(c.get(Calendar.YEAR) + " ");
		date.append(c.get(Calendar.HOUR_OF_DAY) > 9 ? c.get(Calendar.HOUR_OF_DAY) : "0" + c.get(Calendar.HOUR_OF_DAY));
		date.append(":");
		date.append(c.get(Calendar.MINUTE) > 9 ? c.get(Calendar.MINUTE) : "0" + c.get(Calendar.MINUTE));
		date.append(":");
		date.append(c.get(Calendar.SECOND) > 9 ? c.get(Calendar.SECOND) : "0" + c.get(Calendar.SECOND));
		AllWork.getTextArea().append("\nЗавершено. Всего ошибок: " + errorCounts + "\n");
		AllWork.getTextArea().setCaretPosition(AllWork.getTextArea().getText().length());
		if (errorCounts == 0 ) {
			if ((whatWork.contains("Утренняя") || whatWork.contains("Обновление")) && !filial.contains("Подольск")) {
				try (PrintWriter writer = new PrintWriter(
						new OutputStreamWriter(
								new FileOutputStream(
										System.getProperty("user.home") + File.separator + "AppData" + File.separator
								+ "Roaming" + File.separator + "Emtika Work" + File.separator + "ewupd.txt"), Charset.forName("UTF-8")))) {
					for (Map.Entry<String, String> entry : ReadMapFromEWUPD.staffLocal.entrySet()) {
						writer.println(entry.getKey() + " " + entry.getValue());
					}
					AllWork.getTextArea().append(" Сохранение изменений в ewupd.txt...  готово\n");
					AllWork.getTextArea().setCaretPosition(AllWork.getTextArea().getText().length());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, "Завершено без ошибок", "Статус завершения",
					JOptionPane.OK_CANCEL_OPTION,
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/new_ok.png")));
		} else {
			JOptionPane.showMessageDialog(null, "<HTML>Завершено с ошибками. <br>Повторите процедуру",
					"Статус завершения", JOptionPane.ERROR_MESSAGE,
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Error-48.png")));
		}
		try (BufferedWriter pst = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"))) {
			
			//BufferedWriter bw = new BufferedWriter(fw);
			pst.newLine();
			if (errorCounts == 0) {
				pst.write(date + ", " + whatWork + " [" + filial + " :: "
						+ InetAddress.getLocalHost().getHostName().toLowerCase() + " :: "
						+ System.getProperties().getProperty("user.name") + " :: "
						+ new SwingMainFrame().getMT().toString().substring(11, 25) + "] ОК");
			} else {
				for (String st : AllWork.getErrList()) {
					if (!st.contains("Процесс не может"))
						pst.write(date + ", " + whatWork + " [" + filial + " :: "
								+ InetAddress.getLocalHost().getHostName().toLowerCase() + " :: "
								+ System.getProperties().getProperty("user.name") + "::"
								+ new SwingMainFrame().getMT().toString().substring(11, 25) + "]	" + st
								+ " ERROR\n");
				}
			}
			//bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (doParadise)
			paradise();
	}

	private void paradise() {
		String pr_name = "Paradise".toUpperCase(); // <-- искомый процесс
		String process_line;
		int flag = 0;
		try {
			Process p = Runtime.getRuntime()
					.exec(System.getenv("windir") + File.separator + "system32" + File.separator + "tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((process_line = input.readLine()) != null) {
				if (process_line.indexOf(pr_name + ".exe") >= 0 || process_line.indexOf(pr_name + ".EXE") >= 0) { // <--
																													// поиск
																													// процесса
					// System
					// System.out.println(process_line.substring(process_line.indexOf(pr_name)));
					flag = 1;
				}
			}
			if (flag == 0) {
				try {
					p = Runtime.getRuntime().exec("C:" + File.separator + "Paradise" + File.separator + "paradise.exe");
				} catch (IOException e1) {
					// JOptionPane.showMessageDialog(null, "Парадиз не найден");
				}
			}
			input.close();
		} catch (Exception err) {

		}
	}
}

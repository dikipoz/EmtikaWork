package ru.zaoemtika.EmtikaWork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReadMapFromEWUPD {
	static Map<String, String> staffLocal;

	public static Map<String, Boolean> readFromEWUPD(String[] zipFileNames) {

		Map<String, Boolean> staff = new HashMap<String, Boolean>();
		Map<String, String> staffServer = new HashMap<String, String>();

		for (String zF : zipFileNames) {
			try {
				staffServer.put(zF.toUpperCase(), Files
						.getAttribute(Paths.get(AllWork.Z_TO_ALL + zF), "lastModifiedTime", LinkOption.NOFOLLOW_LINKS)
						.toString().toUpperCase());
			} catch (IOException e) {
				staffServer.put(zF.toUpperCase(), "afffffffffffffffffffffffffffffff".toUpperCase());
			}
		}

		staffLocal = new HashMap<String, String>();
		// BufferedReader readerServerEW;
		BufferedReader readerLocalEW;
		String line;
		/*
		 * try { readerServerEW = new BufferedReader(new
		 * FileReader(AllWork.ZAOEMTIKA_RU_DFS_FTP + File.separator + "__Soft" +
		 * File.separator + "EWLog" + File.separator + "ewupd.txt"));
		 * 
		 * StringBuilder sbKey = new StringBuilder(); StringBuilder sbValue =
		 * new StringBuilder(); for (int i = 0; i < 3; i++) {
		 * readerServerEW.readLine(); } while ((line =
		 * readerServerEW.readLine()) != null) { if (line.length() != 0) { sbKey
		 * = new StringBuilder(); sbKey.append(line.trim()).delete(0,
		 * 19).delete(sbKey.indexOf(" "), sbKey.length());
		 * sbValue.append(line.trim()).delete(0, sbValue.lastIndexOf(" ") + 1);
		 * 
		 * staffServer.put(sbKey.toString().toUpperCase(),
		 * sbValue.toString().toUpperCase()); } } readerServerEW.close(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		if (SwingMainFrame.isClearOrFast() == false) {
			try {
				readerLocalEW = new BufferedReader(
						new FileReader(System.getProperty("user.home") + File.separator + "AppData" + File.separator
								+ "Roaming" + File.separator + "Emtika Work" + File.separator + "ewupd.txt"));

				while ((line = readerLocalEW.readLine()) != null) {
					if (line.length() != 0 && line != "" && !line.isEmpty() && line != null)
						staffLocal.put(line.substring(0, line.indexOf(" ")).toUpperCase(),
								line.substring(line.indexOf(" ") + 1).toUpperCase());
				}
				readerLocalEW.close();
			} catch (IOException e) {
				System.out.println("заполнение staffLocal");
				for (Map.Entry<String, String> sS : staffServer.entrySet()) {
					staffLocal.put(sS.getKey().toUpperCase(), "ffffffffffffffffffffffffffffffff");
				}
			}

			for (Map.Entry<String, String> sS : staffServer.entrySet()) {
				if (!staffLocal.containsKey(sS.getKey()))
					staffLocal.put(sS.getKey().toUpperCase(), "ffffffffffffffffffffffffffffffff");
			}
			Map<String, String> copy = new HashMap<>(staffLocal);
			for (Map.Entry<String, String> sL : copy.entrySet()) {
				if (!staffServer.containsKey(sL.getKey())) {
					staffLocal.remove(sL.getKey());
				}
			}
		} else {
			for (Map.Entry<String, String> sS : staffServer.entrySet()) {
				staffLocal.put(sS.getKey().toUpperCase(), "ffffffffffffffffffffffffffffffff");
			}
		}

		for (int i = 0; i < zipFileNames.length; i++) {
			for (Map.Entry<String, String> sS : staffServer.entrySet()) {
				for (Map.Entry<String, String> sL : staffLocal.entrySet()) {
					if (sS.getKey().toUpperCase().equals(sL.getKey().toUpperCase())
							&& sS.getKey().equals(zipFileNames[i].toUpperCase())
							&& sS.getValue().toUpperCase().equals(sL.getValue().toUpperCase())) {
						staff.put(sS.getKey(), true);
					} else {
						if (sS.getKey().toUpperCase().equals(sL.getKey().toUpperCase())
								&& sS.getKey().equals(zipFileNames[i].toUpperCase())
								&& !sS.getValue().toUpperCase().equals(sL.getValue().toUpperCase())) {
							staff.put(sS.getKey(), false);
							sL.setValue(sS.getValue());
						} else {
							if (sS.getKey().toUpperCase()
									.equals(("bs" + CurrentDate.currentDate(true) + ".zip").toUpperCase())
									&& !staffLocal.containsKey(
											("bs" + CurrentDate.currentDate(true) + ".zip").toUpperCase())) {
								staff.put(sS.getKey(), false);
							}
						}
					}
				}
			}
		}

		return staff;
	}
}

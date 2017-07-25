package ru.zaoemtika.EmtikaWork;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class KillParadox {
	public static void killParadox(String pathProgramFiles){
		String paradoxProcessName = "pdxwin32.exe"; // <-- искомый процесс
		String ntvdmProcessName = "ntvdm.exe";
		String process_line;
		if (System.getProperty("os.arch").equals("x86")) {
			pathProgramFiles = "Program Files";
		} else
			pathProgramFiles = "Program Files (x86)";

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(Runtime.getRuntime()
					.exec(System.getenv("windir") + File.separator + "system32" + File.separator + "tasklist.exe")
					.getInputStream()));
			while ((process_line = input.readLine()) != null) {
				if (process_line.toLowerCase().contains(paradoxProcessName)
						|| process_line.toLowerCase().contains(ntvdmProcessName)) { // <--
																					// поиск
																					// процесса
					Runtime.getRuntime().exec(System.getenv("windir") + File.separator + "system32" + File.separator
							+ "taskkill.exe /F /IM " + process_line.substring(0, 20).trim());
				}
			}
			input.close();
		} catch (Exception err) {
		}
	}

}

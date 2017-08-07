
package ru.zaoemtika.EmtikaWork;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {
	public static final double VERSION = 9.41;

	public static void main(String[] args) throws IOException {
		
		File semaphore = new File(System.getProperty("user.home") + File.separator + "AppData" + File.separator
				+ "Roaming" + File.separator + "Emtika Work" + File.separator + "semaphore.emt");
		if (semaphore.exists()) {
			String pr_name = "javaw.exe".toLowerCase(); // <-- искомый процесс
			String process_line;
			int flag = 0;
			try {
				Process p = Runtime.getRuntime()
						.exec(System.getenv("windir") + File.separator + "system32" + File.separator + "tasklist.exe");
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((process_line = input.readLine()) != null) {
					if (process_line.toLowerCase().contains(pr_name)) { // <-- поиск процесса
						flag++;
					}
				}
				input.close();
				if (flag == 1) {
					new CheckUpdate(semaphore);
				} else System.exit(0);
			} catch (Exception err) {
			}
		} else {
			new CheckUpdate(semaphore);
		}
	}
}
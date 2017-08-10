package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

public class CheckUpdate {

	public CheckUpdate(File semaphore) throws IOException {

		if (!semaphore.exists())
			semaphore.createNewFile();
		semaphore.deleteOnExit();
		String source = File.separator + File.separator + "zeon" + File.separator + "EMT2012" + File.separator
				+ "EmtikaWork" + File.separator + "EmtWork.exe";
		String destination = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming"
				+ File.separator + "Emtika Work" + File.separator + "EmtWork.exe";
		
		FileReader file = new FileReader(File.separator + File.separator + "zeon" + File.separator + "EMT2012"
				+ File.separator + "EmtikaWork" + File.separator + "version.manifest");
		int c;
		StringBuilder str = new StringBuilder();
		while ((c = file.read()) != -1) {
			str.append((char) c);
		}
		file.close();

		if (Double.parseDouble(str.toString()) > Program.VERSION) {
			FileInputStream is = new FileInputStream(source);
			FileOutputStream os = new FileOutputStream(destination);
			FileChannel fci = is.getChannel();
			FileChannel fco = os.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (true) {
				int read = fci.read(buffer);
				if (read == -1)
					break;
				buffer.flip();
				fco.write(buffer);
				buffer.clear();
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					try {
						Runtime.getRuntime().exec(new File(destination).toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			is.close();
			os.close();
			Files.deleteIfExists(Paths.get(System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming"
					+ File.separator + "Emtika Work" + File.separator + "semaphore.emt"));
			System.exit(0);
		} else {
			try {
				ConnectSources.connectSources();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						SwingMainFrame frame = new SwingMainFrame();
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

	}
}
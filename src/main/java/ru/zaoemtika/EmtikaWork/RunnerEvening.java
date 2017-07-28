package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class RunnerEvening implements Runnable {

	private boolean doDeficit;
	private boolean doSklad;
	private boolean doClearDoc;
	private static volatile RunnerEvening instance = null;

	public static RunnerEvening getInstance() {
		return instance;
	}

	private RunnerEvening(String[] filial, JProgressBar progressBar, JTextArea textArea, boolean doDeficit,
			boolean doSklad, boolean doClearDoc) {
		AllWork.setProgressBar(progressBar);
		AllWork.setTextArea(textArea);
		AllWork.setFilial(filial);
		this.doDeficit = doDeficit;
		this.doSklad = doSklad;
		this.doClearDoc = doClearDoc;
	}

	public static RunnerEvening runnerEvening(String[] filial, JProgressBar progressBar, JTextArea textArea, boolean doDeficit,
			boolean doSklad, boolean doClearDoc){
		if(instance == null){
			synchronized(RunnerEvening.class){
				if (instance == null)
					instance = new RunnerEvening(filial, progressBar, textArea, doDeficit, doSklad, doClearDoc);
			}
			return instance;
		}
		return null;
	}
	public void run() {
		// new Delete(DISK_i);
		if (!doDeficit && !doSklad && !doClearDoc) {
			JOptionPane.showMessageDialog(null,
					"<html><font color=#ffffdd>�������� ��� �������� ������</font>", "��������",
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(SwingMainFrame.class.getResource("/ru/zaoemtika/images/Error-48.png")));
			instance = null;
		} else {
			// -------------- ����������� �������� � ����� DEFS
			AllWork.setErrorsCount(0);
			AllWork.getTextArea().setText(null);
			AllWork.getProgressBar().setMaximum(100);
			AllWork.getProgressBar().setValue(0);
			if (doDeficit) {
				// �������� ����� �� I_CASE_MODEM_DEF � ����������� defxx.db
				new Delete(AllWork.I_CASE_MODEM_DEF);
				SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + "defemt.db", AllWork.I_CASE_MODEM_DEF + AllWork.getFilial()[3]);

				// ��� ���������� ����� ������� ���������� ���
				StringBuilder str = new StringBuilder(AllWork.getFilial()[3]);
				str.replace(0, str.length(), str.delete(str.indexOf("."), str.length()).toString());

				// ����������� defxx.px
				SimplyCopy.simplyCopy(AllWork.C_PRIC1B_64 + "defemt.px", AllWork.I_CASE_MODEM_DEF + str + ".px");

				File destPath = new File(AllWork.I_CASE_MODEM_DEF);
				String[] sDirList = CreateFileNameArray.createFileNameArray(destPath);
				// ***************** etalon *********************
				CreateZipArchive.createZipArchive(destPath, sDirList, AllWork.TEMP_DIR, "def_" + AllWork.getFilial()[6]);
				SimplyCopy.simplyCopy(AllWork.TEMP_DIR + "def_" + AllWork.getFilial()[6] + ".zip",
						AllWork.Z_DEFS + "def_" + AllWork.getFilial()[6] + ".zip");

				SimplyCopy.simplyCopy(AllWork.I_CASE_MODEM + AllWork.getFilial()[4], AllWork.Z_DEFS + AllWork.getFilial()[4]);
				new Delete(AllWork.TEMP_DIR, "def_" + AllWork.getFilial()[6] + ".zip");
			}

			// ------------------------- ����������� ������ � SKLADS
			if (doSklad) {
				new Delete(AllWork.I_CASE_MODEM_SK);
				SimplyCopy.simplyCopy(AllWork.I_BASE_PRICE + AllWork.getFilial()[1], AllWork.I_CASE_MODEM_SK + AllWork.getFilial()[1]);

				// ����������� S1xx000000.db �� i:/base/price � i:/case/modem/sk
				SimplyCopy.simplyCopy(AllWork.I_BASE_PRICE + AllWork.getFilial()[5], AllWork.I_CASE_MODEM_SK + AllWork.getFilial()[5]);

				// ������������� S1 � eexx_n2.db �� z:\skalds\
				File destPath = new File(AllWork.I_CASE_MODEM_SK);
				String[] sDirList = CreateFileNameArray.createFileNameArray(destPath);
				CreateZipArchive.createZipArchive(destPath, sDirList, AllWork.TEMP_DIR, "sk_" + AllWork.getFilial()[6]);
				SimplyCopy.simplyCopy(AllWork.TEMP_DIR + "sk_" + AllWork.getFilial()[6] + ".zip",
						AllWork.Z_SKLADS + "sk_" + AllWork.getFilial()[6] + ".zip");
				new Delete(AllWork.TEMP_DIR, "sk_" + AllWork.getFilial()[6] + ".zip");
			}

			// ---------------------- ����������� DOC �� ������ � �
			// i:/case/counts � ���������

			String mskOrNo = (AllWork.getFilial()[6].equals("opt") ? "mos": AllWork.getFilial()[6]);
			File destPath = new File(AllWork.I_BASE_DOC);
			Path path = Paths.get(AllWork.Z_COUNTS + mskOrNo + "_"
					+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
			String[] sDirList = CreateFileNameArray.createFileNameArray(destPath);
			if (sDirList.length > 0) {
				AllWork.getProgressBar().setMaximum(AllWork.getProgressBar().getMaximum() + sDirList.length);
				for (String dirContents : sDirList) {
					SimplyCopy.simplyCopy(AllWork.I_BASE_DOC + dirContents, AllWork.I_BASE_OLD_CH + dirContents);
				}
				if (Files.exists(path, LinkOption.NOFOLLOW_LINKS) && Files.isReadable(path)) {
					SimplyCopy.simplyCopy(path.toString(), AllWork.TEMP_DIR + mskOrNo + "_"
							+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
					// ������������� �� z:\counts ���� ������� �� TEMP_DIR
					ExtractZipArchive.extractZipArchive(AllWork.TEMP_DIR, AllWork.TEMP_DIR, mskOrNo + "_"
							+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
					// �������� ����������� �� i:\base\doc � TEMP_DIR
					for (String sdirlist : sDirList) {
						SimplyCopy.simplyCopy(AllWork.I_BASE_DOC + sdirlist, AllWork.TEMP_DIR + sdirlist);
					}
					// ������� �� TEMP_DIR ����� �������
					new Delete(AllWork.TEMP_DIR, mskOrNo + "_"
							+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip");
					// ������� ����� ������� �� TEMP_DIR �� ���
					sDirList = CreateFileNameArray.createFileNameArray(new File(AllWork.TEMP_DIR));
					CreateZipArchive.createZipArchive(new File(AllWork.TEMP_DIR), sDirList, AllWork.I_CASE_COUNTS,
							mskOrNo + "_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2));
				} else {
					CreateZipArchive.createZipArchive(destPath, sDirList, AllWork.I_CASE_COUNTS,
							mskOrNo + "_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2));
				}
				SimplyCopy.simplyCopy(
						AllWork.I_CASE_COUNTS + mskOrNo + "_"
								+ new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2) + ".zip",
								AllWork.Z_COUNTS + mskOrNo + "_" + new StringBuilder(CurrentDate.currentDate(true)).delete(0, 2)
								+ ".zip");
				if (doClearDoc) {
					new Delete(AllWork.I_BASE_DOC);
				}
				// String[] dirContents = new File(I_BASE_DOC).list();
			} else {
				AllWork.getProgressBar().setValue(24);
				JOptionPane.showMessageDialog(null,
						"������� I:" + File.separator + "BASE" + File.separator + "DOC ������.");
			}

			// -------- ���������� ������� i:\base\doc ----------

			AllWork.getProgressBar().setValue(AllWork.getProgressBar().getMaximum());
			new Done(false, AllWork.getErrorsCount(),AllWork.getFilial()[0], "��������   ");
			instance = null;
		}
	}
}

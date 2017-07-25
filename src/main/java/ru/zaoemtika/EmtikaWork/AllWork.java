package ru.zaoemtika.EmtikaWork;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public abstract class AllWork {

	private static String[] fileExt = new String[] { "ZIP", "LCK", "EXE", "INI", "FAM", "TV", "PIF" };
	private static List<String> listAllSuffix = new ArrayList<>();
	private static List<String> errList = new ArrayList<>();
	//private static int maxProgressBar;
	
	public static List<String> getErrList() {
		return errList;
	}

	public static void setErrList(String error) {
		AllWork.errList.add(error);
	}
	
	public static List<String> getListAllSuffix() {
		return listAllSuffix;
	}
	public static void clearErrList() {
		AllWork.errList.clear();
	}

	public static void setListAllSuffix(String listAllSuffix) {
		AllWork.listAllSuffix.add(listAllSuffix);
	}
	
	public static void clearListAllSuffix(){
		AllWork.listAllSuffix.clear();
	}

	private static int PRG_BAR;

	public static final String TEMP_DIR;
	public static final String ZAOEMTIKA_RU_DFS_FTP;
	public static final String C_PRIC1B_64;
	public static final String C_CASE;
	public static final String I_BASE_PRICE;
	public static final String I_BASE_DOC;

	public static final String Z_TO_ALL;
	public static final String Z_SKLADS;
	public static final String Z_DEFS;
	public static final String Z_COUNTS;

	public static final String I_BASE_OLD_CH;
	public static final String I_BASE_DOC_OLD;
	public static final String I_CASE_005;
	public static final String I_CASE_003;
	public static final String I_CASE_BS;
	public static final String I_CASE_MODEM_SK;
	public static final String I_CASE_MODEM_DEF;
	public static final String I_CASE_COUNTS;
	public static final String I_CASE_MODEM;
	public static final String I_CASE_ES;
	
	public static final String pathProgramFiles;
	
	private static JProgressBar progressBar;
	private static JTextArea textArea;
	private static String[] filial;
	private static Process p;
	private static int errorsCount = 0;
	
	static{
		//TEMP_DIR = "c:\\_111\\";
		TEMP_DIR = setTempDir();
		//private static final String ZAOEMTIKA_RU_DFS_FTP = "\\\\zaoemtika.ru\\dfs\\ftp";
		ZAOEMTIKA_RU_DFS_FTP = File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator + "ftp" + File.separator;
		I_BASE_PRICE = "i:" + File.separator + "base" + File.separator + "price" + File.separator;

		C_PRIC1B_64 = "c:" + File.separator + "pric1b_64" + File.separator;
		C_CASE = "c:" + File.separator + "case" + File.separator;
		I_BASE_DOC = "i:" + File.separator + "base" + File.separator + "doc"  + File.separator;
		Z_TO_ALL = ZAOEMTIKA_RU_DFS_FTP + "to_all" + File.separator;
		//Z_TO_ALL = File.separator + File.separator + "zaoemtika.ru" + File.separator + "dfs" + File.separator + "ftp" + File.separator + "to_all" + File.separator;
		Z_SKLADS = ZAOEMTIKA_RU_DFS_FTP + "sklads" + File.separator;
		Z_DEFS = ZAOEMTIKA_RU_DFS_FTP + "defs" + File.separator;
		Z_COUNTS = ZAOEMTIKA_RU_DFS_FTP + "counts" + File.separator;
		I_BASE_OLD_CH = "i:" + File.separator + "base" + File.separator + "old_ch" + File.separator;
		I_BASE_DOC_OLD = "i:" + File.separator + "base" + File.separator + "doc_old" + File.separator;
		I_CASE_005 = "i:" + File.separator + "case" + File.separator + "005" + File.separator;
		I_CASE_003 = "i:" + File.separator + "case" + File.separator + "003" + File.separator;
		I_CASE_BS = "i:" + File.separator + "case" + File.separator + "bs" + File.separator;
		I_CASE_MODEM = "i:" + File.separator + "case" + File.separator + "modem" + File.separator;
		I_CASE_MODEM_SK = "i:" + File.separator + "case" + File.separator + "modem" + File.separator + "sk" + File.separator;
		I_CASE_MODEM_DEF = "i:" + File.separator + "case" + File.separator + "modem" + File.separator + "def" + File.separator;
		I_CASE_COUNTS = "i:" + File.separator + "case" + File.separator + "counts" + File.separator + Calendar.getInstance().get(Calendar.YEAR) + File.separator;
		I_CASE_ES = "i:" + File.separator + "case" + File.separator + "ES" + File.separator;
		
		if (System.getProperty("os.arch").equals("x86")) {
			pathProgramFiles = "Program Files";
		} else
			pathProgramFiles = "Program Files (x86)";
	}
	private static String setTempDir(){
        String tmp_dir_prefix = "Emt_";
            Path tmp_2 = null;
			try {
				tmp_2 = Files.createTempDirectory(tmp_dir_prefix);
			} catch (IOException e) {
				e.printStackTrace();
			}
            File files = tmp_2.toFile();
            files.deleteOnExit();
            return tmp_2.toString() + File.separator;
	}

	public static int getPRG_BAR() {
		return PRG_BAR;
	}

	public static void setPRG_BAR(int pRG_BAR) {
		PRG_BAR = pRG_BAR;
	}

	public static String[] getFileExt() {
		return fileExt;
	}

	public static JProgressBar getProgressBar() {
		return progressBar;
	}

	public static void setProgressBar(JProgressBar progressBar) {
		AllWork.progressBar = progressBar;
	}

	public static JTextArea getTextArea() {
		return textArea;
	}

	public static void setTextArea(JTextArea textArea) {

		AllWork.textArea = textArea;
	}

	public static String[] getFilial() {
		return filial;
	}

	public static void setFilial(String[] filial) {
		AllWork.filial = filial;
	}

	public static Process getP() {
		return p;
	}

	public static void setP(Process p) {
		AllWork.p = p;
	}

	public static int getErrorsCount() {
		return errorsCount;
	}

	public static void setErrorsCount(int errorsCount) {
		AllWork.errorsCount = errorsCount;
	}
}

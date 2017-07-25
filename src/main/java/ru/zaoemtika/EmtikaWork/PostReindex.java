package ru.zaoemtika.EmtikaWork;

import java.io.File;

public class PostReindex {

	public static void postReindex(){
		
		//KillParadox.killParadox(AllWork.pathProgramFiles);
		AllWork.getTextArea().append(" Переиндексация post.db...  ");
		try {
			Runtime.getRuntime().exec("C:" + File.separator + AllWork.pathProgramFiles + File.separator + "Corel"
					+ File.separator + "WordPerfect Office 2000" + File.separator + "programs" + File.separator
					+ "pdxwin32.exe -p" + AllWork.TEMP_DIR + " -Wi:\\case\\003 BSM_35.ssl");
				AllWork.getTextArea().append("готово\n");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

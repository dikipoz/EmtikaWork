package ru.zaoemtika.EmtikaWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class FilialFromSQL {
	private static final String user = "emtikaworkuser";
	private static final String pass = "Aa000000";
	private static final String URL = "jdbc:jtds:sqlserver://zevs:1433";

	public static String[] filialFromSQL(String userlogin) throws SQLException {
		String[] needFiles = new String[9];

		String query = "select Ф#И#О#, [e-mail], [Филиал 1], [Код склада], [Дефицит], [Счета по дефициту], [SUR], [Код  полного склада], [N предпр]  "
				+ " from EMT2010.dbo.SHTAT inner join EMT2010.dbo.Filials on [Номер предприятия]=[N предпр]";

		DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
		try (

				Connection con = DriverManager.getConnection(URL, user, pass);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);)

		{
			while (rs.next()) {
				if (rs.getString("e-mail").toLowerCase().contains(userlogin.toLowerCase())) {
					if (rs.getString("Филиал 1") != null && !rs.getString("Филиал 1").isEmpty()) {
						needFiles[0] = rs.getString("Филиал 1");
					} else
						needFiles[0] = "";
					if (rs.getString("Код склада") != null && !rs.getString("Код склада").isEmpty()) {
						needFiles[1] = rs.getString("Код склада");
					} else
						needFiles[1] = "";
					if (rs.getString("Дефицит") != null && !rs.getString("Дефицит").isEmpty()) {
						needFiles[2] = rs.getString("Дефицит").toLowerCase();
					} else
						needFiles[2] = "";
					if (rs.getString("Счета по дефициту") != null && !rs.getString("Счета по дефициту").isEmpty()) {
						needFiles[3] = rs.getString("Счета по дефициту").toLowerCase();
					} else
						needFiles[3] = "";
					if (rs.getString("SUR") != null && !rs.getString("SUR").isEmpty()) {
						needFiles[4] = rs.getString("SUR").toLowerCase();
					} else
						needFiles[4] = "";
					if (rs.getString("Код  полного склада") != null && !rs.getString("Код  полного склада").isEmpty()) {
						needFiles[5] = (rs.getString("Код  полного склада")).toLowerCase();
					} else
						needFiles[5] = "";
					if (rs.getString("Дефицит") != null && !rs.getString("Дефицит").isEmpty()) {
						needFiles[6] = rs.getString("Дефицит").substring(4, rs.getString("Дефицит").indexOf('.'))
								.toLowerCase();
					} else
						needFiles[6] = "";
					if (rs.getString("Ф#И#О#") != null && !rs.getString("Ф#И#О#").isEmpty()) {
						needFiles[7] = rs.getString("Ф#И#О#");
					} else
						needFiles[7] = "";
					if (rs.getString("N предпр") != null && !rs.getString("N предпр").isEmpty()) {
						needFiles[8] = rs.getString("N предпр");
					} else
						needFiles[8] = "";
					
					return needFiles;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Нет сети или нет подключения к SQL серверу. Обратитесь к администратору", "Ошибка",
					JOptionPane.OK_OPTION, null);
		}
		JOptionPane.showMessageDialog(null, "Пользователь не найден. Обратитесь к администратору", "Ошибка",
				JOptionPane.OK_OPTION, null);
		System.exit(0);
		return null;
	}
}

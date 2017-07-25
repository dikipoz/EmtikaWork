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
	  
	public static String[] filialFromSQL(String userlogin) throws SQLException{
		String[] needFiles = new String[9];
		
		String query = "select �#�#�#, [e-mail], [������ 1], [��� ������], [�������], [����� �� ��������], [SUR], [���  ������� ������], [N ������]  "
				+ " from EMT2010.dbo.SHTAT inner join EMT2010.dbo.Filials on [����� �����������]=[N ������]";
		
		DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
		try(
				
				Connection con = DriverManager.getConnection(URL, user, pass);
			    Statement st = con.createStatement();
			    ResultSet rs = st.executeQuery(query);)
		{
		    while(rs.next()){
		        if(rs.getString("e-mail").toLowerCase().contains(userlogin.toLowerCase())){
		        	if (rs.getString("������ 1") != null && !rs.getString("������ 1").isEmpty()){
		        		needFiles[0] = rs.getString("������ 1");
		        	} else needFiles[0] = "";
		        	if (rs.getString("��� ������") != null && !rs.getString("��� ������").isEmpty()){
		        		needFiles[1] = rs.getString("��� ������");
		        	} else needFiles[1] = "";
		        	if (rs.getString("�������") != null && !rs.getString("�������").isEmpty()){
		        		needFiles[2] = rs.getString("�������").toLowerCase();
		        	} else needFiles[2] = "";
		        	if (rs.getString("����� �� ��������") != null && !rs.getString("����� �� ��������").isEmpty()){
		        		needFiles[3] = rs.getString("����� �� ��������").toLowerCase();
		        	} else needFiles[3] = "";
		        	if (rs.getString("SUR") != null && !rs.getString("SUR").isEmpty()){
		        		needFiles[4] = rs.getString("SUR").toLowerCase();
		        	} else needFiles[4] = "";
		        	if (rs.getString("���  ������� ������") != null && !rs.getString("���  ������� ������").isEmpty()){
		        		needFiles[5] = (rs.getString("���  ������� ������")).toLowerCase();
		        	} else needFiles[5] = "";
		        	if (rs.getString("�������") != null && !rs.getString("�������").isEmpty()){
		        		needFiles[6] = rs.getString("�������").substring(4, rs.getString("�������").indexOf('.')).toLowerCase();
		        	} else needFiles[6] = "";
		        	if (rs.getString("�#�#�#") != null && !rs.getString("�#�#�#").isEmpty()){
		        		needFiles[7] = rs.getString("�#�#�#");
		        	} else needFiles[7] = "";
		        	if (rs.getString("N ������") != null && !rs.getString("N ������").isEmpty()){
		        		needFiles[8] = rs.getString("N ������");
		        	} else needFiles[8] = "";
		        	return needFiles;
		        } 
		    }
		    } catch (SQLException e){
		    	JOptionPane.showMessageDialog(null, "��� ���� ��� ��� ����������� � SQL �������. ���������� � ��������������", "������",
						JOptionPane.OK_OPTION, null);
		    }
		JOptionPane.showMessageDialog(null, "������������ �� ������. ���������� � ��������������", "������",
				JOptionPane.OK_OPTION, null);
		System.exit(0);
		return needFiles;
	}
}

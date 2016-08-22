package Company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {

	public static Connection connect(){
		
		Integer i = Integer.valueOf(1);
		String dbdriver="org.gjt.mm.mysql.Driver";
		String url="jdbc:mysql://localhost:3306/company";  
		String userName = "root";
		String password = "ljw";
		Connection con = null;
		try {
			Class.forName(dbdriver); 
			con = DriverManager.getConnection(url,userName,password);
		}
		catch (Exception e) {
			System.out.print("MYSQL conncet failed:" + e.getMessage());
		}
		return con;
	}

	public static ResultSet select(String sql,Connection con){
		ResultSet resultSet = null;
		PreparedStatement pstmt;	
		try {
			pstmt = con.prepareStatement(sql);
			resultSet = pstmt.executeQuery(sql);		
		}
		catch (Exception e) {
			System.out.print("MYSQL ERROR1:" + e.getMessage());
		}
		return resultSet;
	}

	public static int ins_upd(StringBuffer sql,Connection con){
		int Res = 0;
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sql.toString());
			Res = pstmt.executeUpdate(sql.toString());
			//System.out.println("operate data amount:" + Res);
		} catch (Exception e) {
			System.out.print("MYSQL ERROR3:" + e.getMessage());
		}
		return Res;
	}
	
	public static int prepar(Connection con,PreparedStatement pstmt){
		int Res = 0;
		try {
			Res = pstmt.executeUpdate();
			//System.out.println("operate data amount:" + Res);
		} catch (Exception e) {
			System.out.print("MYSQL ERROR4:" + e.getMessage());
		}
		return Res;
	}
}
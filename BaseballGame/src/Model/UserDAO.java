package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;
	public void getConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:xe";
			String user="hr";		
			String password="hr"; 
			conn = DriverManager.getConnection(url,user,password);
			if(conn!=null) {
				System.out.println("DB연결 성공");
			}else {
				System.out.println("DB연결 실패");						
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//=====연결=====
	public void close() {
		try {
		if(rs!=null) {rs.close();}
		if(psmt!=null) {psmt.close();}
		if(conn!=null) {conn.close();}
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB연결종료");
	}
	//=====연결해제=====
}

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;
	public void getConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url="jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:xe";
			String user="campus_23K_AI17_p1_2";		
			String password="smhrd2"; 
			conn = DriverManager.getConnection(url,user,password);
//			if(conn!=null) {
//				System.out.println("DB연결 성공");
//			}else {
//				System.out.println("DB연결 실패");						
//			}
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
	}
	//=====연결해제=====
	
	public UserDTO login(UserDTO dto) {
		UserDTO result = null;
		try {
			getConn();

			String sql = "select * from USER_INFO where id=? and pw=?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			rs = psmt.executeQuery();
			if (rs.next()) {
				result = new UserDTO();
				result.setId(rs.getString(1));
				result.setPw(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	// =====================로그인
	
	public int join(UserDTO dto) {
		int cnt = 0;
		try {
			getConn();
			String sql = "INSERT INTO USER_INFO VALUES(?,?)";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			cnt = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}
	// ======================회원가입
	
	public int idCheck(String id) {
		   try {
		   getConn();
		   
		   String sql = "select * from USER_INFO where id=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				return 1; //중복된 ID가 있다는 의미
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		   return 0; // 중복되지 않은 ID
		   
	   }
	// ======================회원중복확인
	
	public ArrayList<UserDTO> idList() {

		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		try {
			getConn();
			String sql = "select * from user_info";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				UserDTO udto = new UserDTO();
				udto.setId(rs.getString("id"));
				udto.setPw(rs.getString("pw"));
				list.add(udto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}
	// ====================모든회원id확인
}

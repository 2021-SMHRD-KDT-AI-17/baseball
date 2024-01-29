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
			String user="campus_23K_AI17_p1_2";		
			String password="smhrd2"; 
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
	public UserDTO login(UserDTO dto) {

		UserDTO result = null;

		try {
			getConn();

			String sql = "select * from USER_INFO where id=? and pw=?";

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());

			rs = psmt.executeQuery();

			result = new UserDTO();

			if (rs.next()) {
				String getId = rs.getString(1);
				String getPw = rs.getString(2);

				// 조회 결과를 DTO에 옮겨 담기
				result.setId(getId);
				result.setPw(getPw);

				System.out.println(getId + "/" + getPw);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (psmt != null) {
					psmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("DB연결 종료");

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

			if (cnt > 0) {
				System.out.println("회원가입 성공!");
			}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		   return 0; // 중복되지 않은 ID
		   
	   }
}

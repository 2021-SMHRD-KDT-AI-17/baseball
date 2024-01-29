package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseballDAO {
	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

	public void getConn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:xe";
			String user = "campus_23K_AI17_p1_2";
			String password = "smhrd2";
			conn = DriverManager.getConnection(url, user, password);
			if (conn != null) {
				System.out.println("DB연결 성공");
			} else {
				System.out.println("DB연결 실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// =====연결=====
	public void close() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB연결종료");
	}

	// =====연결해제=====
	public int delete(String deleteId) {
		int cnt = 0;

		try {
			getConn();

			String sql = "DELETE FROM BASEBALL WHERE ID=?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, deleteId);
			
			psmt.executeUpdate();
			sql = "DELETE FROM USER_INFO WHERE ID=?";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, deleteId);
			
			cnt = psmt.executeUpdate();
			


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
			System.out.println("DB연결종료!");
		}
		return cnt;

	}
	//////// 회원 탈퇴
	public ArrayList<BaseballDTO> rank(UserDTO udto) {
		ArrayList<BaseballDTO> list = new ArrayList<BaseballDTO>();
		
		// 데이터베이스에 조회된 정보를 저장할 임시 객체
		
		try {
			getConn();
		
			// 점수 상위 10명의 순위, 아이디, 점수를 출력
			// 단, 내림차순 정렬
			// 사용자의 가장 높은 점수만을 가지고 랭킹을 매길 것.
		
			String sql = "select club, score from baseball where rownum <= 5 and id = ? Order By indate DESC ";
		    String Id = udto.getId();
		    
		
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, Id);
			rs = psmt.executeQuery();
			
			
			while(rs.next()) {
				BaseballDTO dto = new BaseballDTO();
				dto.setClub(rs.getString(1));
				dto.setScore(rs.getInt(2));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	/////// 내 기록 정보 조회
	
	public int save(BaseballDTO dto) {
		int cnt = 0;
		try {
			getConn();

			String sql = "INSERT INTO BASEBALL(id, culb, score) VALUES(?, ?, ?)";

			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getClub());
			psmt.setInt(3, dto.getScore());

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return cnt;
	}

	// ========게임기록저장
	public ArrayList<BaseballDTO> rank() {

		ArrayList<BaseballDTO> list = new ArrayList<BaseballDTO>();

		try {
			getConn();
			String sql = "select rownum, id, club, score" + " from (" + " select id, club, score" + " from baseball"
					+ " Order By score DESC)" + " where rownum <= 5";

			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {

				BaseballDTO dto = new BaseballDTO();
				dto.setId(rs.getString("id"));
				dto.setClub(rs.getString("club"));
				dto.setScore(rs.getInt("score"));

				list.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			close();

		}
		return list;
	}

	public ArrayList<BaseballDTO> history(String id) {

		ArrayList<BaseballDTO> list = new ArrayList<BaseballDTO>();
		try {
			getConn();

			// 입력한 사용자의 최근 플레이 5개 가져오는 SQL문
			String sql = "select * from Baseball where id = ? and rownum <=5";

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			rs = psmt.executeQuery();

			while (rs.next()) {

				BaseballDTO dto = new BaseballDTO();
				dto.setId(rs.getString("Id"));
				dto.setIndate(rs.getString("indate"));
				dto.setScore(rs.getInt("score"));

				list.add(dto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;

	}
}

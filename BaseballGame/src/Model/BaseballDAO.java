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
	public int save(BaseballDTO dto) {
	       int cnt = 0;
	       try {
	          getConn();
	          
	          String sql = "INSERT INTO PLAY(id,culb, score) VALUES(?, ?)";
	          
	          psmt = conn.prepareStatement(sql);
	          
	          psmt.setString(1, dto.getId());
	          psmt.setString(2, dto.getClub());
	          psmt.setLong(3,dto.getScore());
	          
	          cnt = psmt.executeUpdate();
	          
	          
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         
	      }
	       return cnt;
	}


	 
	 //========게임기록저장
public ArrayList<BaseballDTO> rank(){
		
		ArrayList<BaseballDTO> list = new ArrayList<BaseballDTO>();
		
		try {
			getConn();
			
//			 String sql = "select id, club, 점수 from (select id,club, MAX(score) as 점수 "
//		               +"from baseball Group By id Order By score DESC) where rownum <= 10";
			 String sql = "select rownum, id, club, score"
			 		+ " from ("
			 		+ " select id, club, score"
			 		+ " from baseball"
			 		+ " Order By score DESC)"
			 		+ " where rownum <= 5";
			 
			  psmt = conn.prepareStatement(sql);
		         rs = psmt.executeQuery();
		         
		         while(rs.next()) {
		             
		            BaseballDTO dto = new BaseballDTO();
		             dto.setId(rs.getString("id"));
		             dto.setClub(rs.getString("club"));
		             dto.setScore(rs.getInt("score"));
		             
		             list.add(dto);
		             
		          }
		}catch (SQLException e) {
            e.printStackTrace();
		}  finally {
			
			close();		
											
				
		}
		return list;
		}
}




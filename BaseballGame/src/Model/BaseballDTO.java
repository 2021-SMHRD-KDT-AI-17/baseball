package Model;

import java.util.ArrayList;

public class BaseballDTO {
	private String id,club,indate;
	private long score;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClub() {
		return club;
	}
	public void setClub(String club) {
		this.club = club;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public ArrayList<BaseballDTO> rank() {
		
		return null;
	}
}

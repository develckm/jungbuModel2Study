package model2_study.com.dto;
/*
+-----------------+--------------+------+-----+---------+----------------+
| Field           | Type         | Null | Key | Default | Extra          |
+-----------------+--------------+------+-----+---------+----------------+
| board_prefer_no | int          | NO   | PRI | NULL    | auto_increment |
| board_no        | int          | NO   | MUL | NULL    |                |
| prefer          | tinyint(1)   | YESno|     | NULL    |                |
| user_id         | varchar(255) | NO   | MUL | NULL    |                |
+-----------------+--------------+------+-----+---------+----------------+
 * */
public class BoardPreferDto {
	private int board_prefer_no; //PK
	private int board_no;   
	private int prefer; //0:bads, 1:likes      
	private String user_id;
	//board_no+user_id : UK(유저가 게시글에 좋아요 싫어요를 1번 할 수 있다,)  
	public int getBoard_prefer_no() {
		return board_prefer_no;
	}
	public void setBoard_prefer_no(int board_prefer_no) {
		this.board_prefer_no = board_prefer_no;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public int getPrefer() {
		return prefer;
	}
	public void setPrefer(int prefer) {
		this.prefer = prefer;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "\"board_perfer\":{ \"board_prefer_no\":\"" + board_prefer_no + "\",\"board_no\":\"" + board_no
				+ "\",\"prefer\":\"" + prefer + "\",\"user_id\":\"" + user_id + "\" }";
	}
	
	
}

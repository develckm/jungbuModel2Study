package model2_study.com.dto;
/*
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| board_img_no | int          | NO   | PRI | NULL    | auto_increment |
| board_no     | int          | NO   | MUL | NULL    |                |
| img_path     | varchar(255) | NO   |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+*/

public class BoardImgDto {
	private int board_img_no; //pk
	private int board_no;	//fk board update,delete cascade
	private String img_path;
	public int getBoard_img_no() {
		return board_img_no;
	}
	public void setBoard_img_no(int board_img_no) {
		this.board_img_no = board_img_no;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	@Override
	public String toString() {
		return "\"emp\":{ \"board_img_no\":\"" + board_img_no + "\",\"board_no\":\"" + board_no + "\",\"img_path\":\""
				+ img_path + "\" }";
	}
	
}

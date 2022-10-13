package model2_study.com.dto;

import java.util.Date;
import java.util.List;

/*
+-----------+--------------+------+-----+-------------------+-------------------+
| Field     | Type         | Null | Key | Default           | Extra             |
+-----------+--------------+------+-----+-------------------+-------------------+
| board_no  | int          | NO   | PRI | NULL              | auto_increment    |
| title     | varchar(255) | NO   |     | NULL              |                   |
| contents  | varchar(255) | YES  |     |                   |                   |
| post_time | datetime     | YES  |     | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
| user_id   | varchar(255) | NO   | MUL | NULL              |                   |
| views     | int          | NO   |     | 0                 |                   |
+-----------+--------------+------+-----+-------------------+-------------------+
*/
public class BoardDto {
	private int board_no; 
	private String title;    
	private String contents; 
	private Date post_time;
	private String user_id;  //user.user_id가 있기 때문에 삭제해야한다. 
	private int views;
	
	private UserDto user;  //board : user = N : 1 (fk user_id)
	
	private List<ReplyDto> replyList;  //board : reply = 1 : N (fk board_no)
	private List<BoardImgDto> boardImgList;  //board : board_img = 1 : N (fk board_no)
	
	private int replyCount; //sub query 
	private String thumbPath; //sub query
	
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public List<ReplyDto> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<ReplyDto> replyList) {
		this.replyList = replyList;
	}
	public List<BoardImgDto> getBoardImgList() {
		return boardImgList;
	}
	public void setBoardImgList(List<BoardImgDto> boardImgList) {
		this.boardImgList = boardImgList;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Date getPost_time() {
		return post_time;
	}
	public void setPost_time(Date post_time) {
		this.post_time = post_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	@Override
	public String toString() {
		return "\"board\":{ \"board_no\":" + board_no + ",\"title\":\"" + title + "\",\"contents\":\"" + contents
				+ "\",\"post_time\":\"" + post_time + "\",\"user_id\":\"" + user_id + "\",\"views\":" + views
				+ ",\"user\":\"" + user + "\" }";
	}



}

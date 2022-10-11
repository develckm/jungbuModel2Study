package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model2_study.com.dto.ReplyDto;

public class ReplyDaoImp implements ReplyDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String listFindByBoardNoSql="SELECT * FROM REPLY WHERE board_no=? LIMIT ?,?";
	private String insertSql="INSERT INTO REPLY (board_no,title,contents,user_id) VALUES (?,?,?,?)";
	private final int ROWS=10;
	private String detailSql="SELECT * FROM REPLY WHERE reply_no=?";
	private String updateSql="UPDATE REPLY SET title=?, contents=? WHERE reply_no=?";
	private String deleteSql="DELETE FROM REPLY WHERE reply_no=?";
	public ReplyDaoImp() throws Exception {
		conn=SpringBoardDB.getConn();
		System.out.println(conn);
	}
	@Override
	public List<ReplyDto> list(int page) throws ClassNotFoundException, SQLException {
		return null;
	}

	@Override
	public ReplyDto detail(Integer replyNo) throws ClassNotFoundException, SQLException {
		ReplyDto reply=null;
		pstmt=conn.prepareStatement(detailSql);
		pstmt.setInt(1, replyNo);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			reply=new ReplyDto();
			reply.setReply_no(rs.getInt("reply_no"));
			reply.setBoard_no(rs.getInt("board_no"));
			reply.setImg_path(rs.getString("img_path"));
			reply.setTitle(rs.getString("title"));
			reply.setContents(rs.getString("contents"));
			reply.setUser_id(rs.getString("user_id"));
			reply.setPost_time(rs.getDate("post_time"));
		}
		return reply;
	}

	@Override
	public int update(ReplyDto reply) throws ClassNotFoundException, SQLException {
		int update=0;
		pstmt=conn.prepareStatement(updateSql);
		pstmt.setString(1, reply.getTitle());
		pstmt.setString(2, reply.getContents());
		pstmt.setInt(3, reply.getReply_no());
		update=pstmt.executeUpdate();
		return update;
	}

	@Override
	public int insert(ReplyDto reply) throws ClassNotFoundException, SQLException {
		int insert=0;
		pstmt=conn.prepareStatement(insertSql);
		pstmt.setInt(1, reply.getBoard_no());
		pstmt.setString(2, reply.getTitle());
		pstmt.setString(3, reply.getContents());
		pstmt.setString(4, reply.getUser_id());
		insert=pstmt.executeUpdate();
		return insert;
	}

	@Override
	public int delete(Integer replyNo) throws ClassNotFoundException, SQLException {
		int delete=0;
		pstmt=conn.prepareStatement(deleteSql);
		pstmt.setInt(1, replyNo);
		delete=pstmt.executeUpdate();
		return delete;
	}


	@Override
	public List<ReplyDto> listFindByBoardNo(int page, int boardNo)  throws Exception{
		List<ReplyDto> replyList=null;
		pstmt=conn.prepareStatement(listFindByBoardNoSql);
		pstmt.setInt(1, boardNo);
		pstmt.setInt(2, (page-1)*ROWS );
		pstmt.setInt(3, ROWS);
		rs=pstmt.executeQuery();
		replyList=new ArrayList<ReplyDto>();
		while(rs.next()) {
			ReplyDto reply=new ReplyDto();
			reply.setReply_no(rs.getInt("reply_no"));
			reply.setBoard_no(rs.getInt("board_no"));
			reply.setImg_path(rs.getString("img_path"));
			reply.setTitle(rs.getString("title"));
			reply.setContents(rs.getString("contents"));
			reply.setUser_id(rs.getString("user_id"));
			reply.setPost_time(rs.getDate("post_time"));
			replyList.add(reply);			
		}
		return replyList;
	}

	@Override
	public List<ReplyDto> listFindByUserId(int page, String userId)  throws Exception{
		return null;
	}

	@Override
	public void close() {		
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(new ReplyDaoImp().listFindByBoardNo(1, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

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
	private final int ROWS=10;
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
		return null;
	}

	@Override
	public int update(ReplyDto reply) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int insert(ReplyDto reply) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int delete(Integer replyNo) throws ClassNotFoundException, SQLException {
		return 0;
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

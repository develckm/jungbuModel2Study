package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.BoardPreferDto;

public class BoardPreferDaoImp implements BoardPreferDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	String detailFindbyUserIdAndBoardNoSql="SELECT * FROM BOARD_PREFER WHERE user_id=? AND board_no=?";
	String insertSql="INSERT INTO BOARD_PREFER (board_no,prefer,user_id) VALUES (?,?,?)";
	String updateSql="UPDATE BOARD_PREFER set prefer=? WHERE board_prefer_no=?";
	String deleteSql="DELETE FROM BOARD_PREFER WHERE board_prefer_no=?";
	public BoardPreferDaoImp() throws Exception{
		conn=SpringBoardDB.getConn();
	}
	//로그인한 유저가 게시글 상세에서 좋아요를 했는지 확인하는 용도
	@Override
	public BoardPreferDto detailFindbyUserIdAndBoardNo(String userId, int boardNo) throws Exception {
		BoardPreferDto boardPrefer=null;
		pstmt=conn.prepareStatement(detailFindbyUserIdAndBoardNoSql);
		pstmt.setString(1, userId);
		pstmt.setInt(2, boardNo);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			boardPrefer=new BoardPreferDto();
			boardPrefer.setBoard_prefer_no(rs.getInt("board_prefer_no"));
			boardPrefer.setBoard_no(rs.getInt("board_no"));
			boardPrefer.setPrefer(rs.getInt("prefer"));
			boardPrefer.setUser_id(rs.getString("user_id"));
		}
		return boardPrefer;
	}

	@Override
	public List<BoardPreferDto> listFindByUserId(String userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BoardPreferDto> list(int page) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardPreferDto detail(Integer pk) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(BoardPreferDto dto) throws ClassNotFoundException, SQLException {
		int update=0;
		pstmt=conn.prepareStatement(updateSql);
		pstmt.setInt(1, dto.getPrefer());
		pstmt.setInt(2, dto.getBoard_prefer_no());
		update=pstmt.executeUpdate();
		return update;
	}

	@Override
	public int insert(BoardPreferDto dto) throws ClassNotFoundException, SQLException {
		//board_no,prefer,user_id
		int insert=0;
		pstmt=conn.prepareStatement(insertSql);
		pstmt.setInt(1, dto.getBoard_no());
		pstmt.setInt(2, dto.getPrefer());
		pstmt.setString(3, dto.getUser_id());
		insert=pstmt.executeUpdate();
		return insert;
	}

	@Override
	public int delete(Integer pk) throws ClassNotFoundException, SQLException {
		int delete=0;
		pstmt=conn.prepareStatement(deleteSql);
		pstmt.setInt(1, pk);
		delete=pstmt.executeUpdate();
		return delete;
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


}

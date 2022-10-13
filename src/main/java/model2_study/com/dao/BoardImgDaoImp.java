package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardImgDto;

public class BoardImgDaoImp implements BoardImgDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String insertSql="INSERT INTO BOARD_IMG (board_no,img_path) values (?,?)";
	private String boardListSql="SELECT * FROM BOARD_IMG WHERE board_no=?";
	
	private String detailSql="SELECT * FROM BOARD_IMG WHERE board_img_no=?";
	private String deleteSql="DELETE FROM BOARD_IMG WHERE board_img_no=?";
	private String boardDeleteSql="DELETE FROM BOARD_IMG WHERE board_no=?"; //board 삭제할때 참조하는 board_img 삭제
	private String boardCountSql="SELECT COUNT(*) cnt FROM BOARD_IMG WHERE board_no=?"; //최대 5개만 등록하려고 검사

	public BoardImgDaoImp() throws Exception {
		conn=SpringBoardDB.getConn();
	}
	@Override
	public List<BoardImgDto> list(int page) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardImgDto detail(Integer pk) throws ClassNotFoundException, SQLException {
		BoardImgDto boardImg=null;
		pstmt=conn.prepareStatement(detailSql);
		pstmt.setInt(1, pk);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			boardImg=new BoardImgDto();
			boardImg.setBoard_img_no(rs.getInt("board_img_no"));
			boardImg.setBoard_no(rs.getInt("board_no"));
			boardImg.setImg_path(rs.getString("img_path"));
		}
		return boardImg;
	}
	@Override
	public int update(BoardImgDto dto) throws ClassNotFoundException, SQLException {
		return 0;
	}
	@Override
	public int insert(BoardImgDto dto) throws ClassNotFoundException, SQLException {
		int insert=0;
		pstmt=conn.prepareStatement(insertSql);
		pstmt.setInt(1, dto.getBoard_no());
		pstmt.setString(2, dto.getImg_path());
		insert=pstmt.executeUpdate();	
		return insert;
	}
	@Override
	public List<BoardImgDto> boardList(int boardNo)throws Exception{
		List<BoardImgDto> boardImgList=null;
		pstmt=conn.prepareStatement(boardListSql);
		pstmt.setInt(1, boardNo);
		rs=pstmt.executeQuery();
		boardImgList=new ArrayList<BoardImgDto>();
		while(rs.next()) {
			BoardImgDto boardImg=new BoardImgDto();
			boardImg.setBoard_img_no(rs.getInt("board_img_no"));
			boardImg.setBoard_no(rs.getInt("board_no"));
			boardImg.setImg_path(rs.getString("img_path"));
			boardImgList.add(boardImg);
		}
		return boardImgList;
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
	public int boardDelete(int boardNo)  throws Exception{
		int delete=0;
		pstmt=conn.prepareStatement(boardDeleteSql);
		pstmt.setInt(1,boardNo);
		delete=pstmt.executeUpdate();
		return delete;
	}


	@Override
	public int boardCount(int boardNo)  throws Exception{
		int boardCount=0;
		pstmt=conn.prepareStatement(boardCountSql);
		pstmt.setInt(1, boardNo);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			boardCount=rs.getInt("cnt");
		}
		return boardCount;
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

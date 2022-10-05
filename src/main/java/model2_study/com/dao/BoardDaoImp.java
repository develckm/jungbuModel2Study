package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.BoardDto;

public class BoardDaoImp implements BoardDao {
	//db 접속해서 dql or dml 을 실행하는 객체 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String insertSql="";
	private String deleteSql="";
	private String updateSql="";
	private String viewsUpdateSql="";
	private String detailSql="SELECT * FROM BOARD WHERE board_no=?";
	private String listSql="SELECT * FROM BOARD LIMIT ?,7";
	//생성자를 호출할때 db를 접속해서 다른 쿼리를 실행할때 한번 접속한 객체를 사용 ??(싱글톤패턴과 유사)
	public BoardDaoImp() throws ClassNotFoundException, SQLException {
		conn=SpringBoardDB.getConn();
	}
	
	@Override
	public int insert(BoardDto board) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int delete(int boardNo) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int update(BoardDto board) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int veiwsUpdate(int boardNo) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public BoardDto detail(int boardNo) throws ClassNotFoundException, SQLException {
		return null;
	}

	@Override
	public List<BoardDto> list(int page) throws ClassNotFoundException, SQLException {
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
}

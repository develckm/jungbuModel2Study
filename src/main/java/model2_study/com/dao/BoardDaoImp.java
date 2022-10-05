package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model2_study.com.dto.BoardDto;

public class BoardDaoImp implements BoardDao {
	//db 접속해서 dql or dml 을 실행하는 객체 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String insertSql="INSERT INTO BOARD (user_id, title, contents) VALUES (?,?,?)";
	private String deleteSql="";
	private String updateSql="";
	private String viewsUpdateSql="";
	private String detailSql="SELECT * FROM BOARD WHERE board_no=?";
	private String listSql="SELECT * FROM BOARD ORDER BY board_no DESC LIMIT ?,?";
	private final int ROWS=7; //list 출력시 한페이지에 보이는 row의 수
	//생성자를 호출할때 db를 접속해서 다른 쿼리를 실행할때 한번 접속한 객체를 사용 ??(싱글톤패턴과 유사)
	public BoardDaoImp() throws ClassNotFoundException, SQLException {
		conn=SpringBoardDB.getConn();
	}
	
	@Override
	public int insert(BoardDto board) throws ClassNotFoundException, SQLException {
		int insert=0;
		pstmt=conn.prepareStatement(insertSql);
		pstmt.setString(1, board.getUser_id());
		pstmt.setString(2, board.getTitle());
		pstmt.setString(3, board.getContents());
		insert=pstmt.executeUpdate();
		return insert;
	}

	@Override
	public int delete(Integer boardNo) throws ClassNotFoundException, SQLException {
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
	public BoardDto detail(Integer boardNo) throws ClassNotFoundException, SQLException {
		BoardDto board=null;
		pstmt=conn.prepareStatement(detailSql);
		pstmt.setInt(1, boardNo);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			board=new BoardDto();
			board.setBoard_no(rs.getInt("board_no"));
			board.setViews(rs.getInt("views"));
			board.setTitle(rs.getString("title"));
			board.setContents(rs.getString("contents"));
			board.setUser_id(rs.getString("user_id"));
			board.setPost_time(rs.getDate("post_time"));
		}
		System.out.println(board);
		return board;
	}

	@Override
	public List<BoardDto> list(int page) throws ClassNotFoundException, SQLException {
		List<BoardDto> boardList=null;
		//conn=SpringBoardDB.getConn(); //생성할때(초기화) conn을 생성했기 때문에 생략
		pstmt=conn.prepareStatement(listSql);
		//limit ?,7;
		int startRow=(page-1)*ROWS; //1 => 0 , 2 =>7 , 3=>14 .....
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, ROWS);
		rs=pstmt.executeQuery();
		boardList=new ArrayList<BoardDto>();//오류가 없이 실행되면 객체 생성 
		while(rs.next()) {
			BoardDto board=new BoardDto();
			board.setBoard_no(rs.getInt("board_no"));
			board.setViews(rs.getInt("views"));
			board.setTitle(rs.getString("title"));
			board.setContents(rs.getString("contents"));
			board.setUser_id(rs.getString("user_id"));
			board.setPost_time(rs.getDate("post_time"));
			boardList.add(board);
		}
		return boardList;
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
		BoardDao boardDao=null;
		try {
			boardDao=new BoardDaoImp();//conn 객체 생성
			System.out.println(boardDao.list(3));
			System.out.println(boardDao.list(1));
			System.out.println(boardDao.list(2));

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			boardDao.close();
		}
	}
	
}

package model2_study.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.UserDto;

public class UserDaoImp implements UserDao{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String listSql="";
	private String detailSql="";
	private String updateSql="";
	private String insertSql="";
	private String deleteSql="";
	private String loginSql="SELECT * FROM USER WHERE user_id=? and pw=?";
	@Override
	public List<UserDto> list(int page) {
		return null;
	}

	@Override
	public UserDto detail(String userId) {
		return null;
	}

	@Override
	public UserDto login(String userId, String pw) throws ClassNotFoundException, SQLException {
		UserDto user=null;

		conn=SpringBoardDB.getConn();
		pstmt=conn.prepareStatement(loginSql);
		pstmt.setString(1, userId);
		pstmt.setString(2, pw);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			user=new UserDto();
			user.setUserId(rs.getString("user_id"));
			user.setPw(rs.getString("pw"));
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setName(rs.getString("name"));
			user.setSignup(rs.getDate("signup"));
			user.setBirth(rs.getDate("birth"));
		}
		return user;
	}

	@Override
	public int update(UserDto user) {
		return 0;
	}

	@Override
	public int insert(UserDto user) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int delete(String userId) {
		return 0;
	}

	//구조를 미리 정의하는 것 -> 설계 (추상화,interface)
	//List<UserDto> list(page)
	//UserDto detail(String userId)
	//int update(UserDto user)
	//int insert(UserDto user)
	//int delete(String userId)
	
}

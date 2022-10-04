package model2_study.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.mysql.cj.jdbc.Driver;
public class SpringBoardDB {
	private static String url="jdbc:mysql://localhost:3306/spring_board";
	private static String user="board_user";
	private static String pw="1234";
	private static String driver="com.mysql.cj.jdbc.Driver";
	private static Connection conn;
	public static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn=DriverManager.getConnection(url,user,pw);
		return conn;
	}//CRUD 게시판 생성 model2(ModelViewController 디자인 패턴) 
	/*
	public static void main(String[] args) {
		try {
			System.out.println(SpringBoardDB.getConn());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	 * */
}

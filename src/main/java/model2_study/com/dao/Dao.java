package model2_study.com.dao;

import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.UserDto;

public interface Dao <T,P>{
	List<T> list(int page) throws ClassNotFoundException, SQLException;
	T detail(P pk) throws ClassNotFoundException, SQLException;
	int update(T user) throws ClassNotFoundException, SQLException;
	int insert(T user) throws ClassNotFoundException, SQLException;
	int delete(P pk) throws ClassNotFoundException, SQLException;
	void close();

}

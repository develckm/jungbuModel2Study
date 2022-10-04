package model2_study.com.dao;

import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.UserDto;
//CRUD model의 서비스가 약속이 되어 있기 때문에 interface로 설계할 수 있다. 
public interface UserDao {
	List<UserDto> list(int page) throws ClassNotFoundException, SQLException;
	UserDto detail(String userId) throws ClassNotFoundException, SQLException;
	UserDto login(String userId,String pw) throws ClassNotFoundException, SQLException;
	int update(UserDto user) throws ClassNotFoundException, SQLException;
	int insert(UserDto user) throws ClassNotFoundException, SQLException;
	int delete(String userId) throws ClassNotFoundException, SQLException;
}

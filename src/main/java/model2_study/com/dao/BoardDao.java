package model2_study.com.dao;

import java.sql.SQLException;
import java.util.List;

import model2_study.com.dto.BoardDto;

//게시글 CRUD interface 
public interface BoardDao extends Dao<BoardDto, Integer>{
	//2가지 기능 == model =>sql(dba) 설계(약속)
	//CRUD Interface 의 재사용
	//dml
	int insert(BoardDto board) throws ClassNotFoundException,SQLException;
	int delete(Integer boardNo) throws ClassNotFoundException,SQLException;
	int update(BoardDto board) throws ClassNotFoundException,SQLException;
	int veiwsUpdate(int boardNo) throws ClassNotFoundException,SQLException;
	//dql
	BoardDto detail(Integer boardNo) throws ClassNotFoundException,SQLException;
	List<BoardDto> list(int page) throws ClassNotFoundException,SQLException;
	void close();
}
package model2_study.com.dao;

import java.util.List;

import model2_study.com.dto.BoardPreferDto;

public interface BoardPreferDao extends Dao<BoardPreferDto,Integer>{
	//uk :user_id+board_no
	BoardPreferDto detailFindbyUserIdAndBoardNo(String userId,int boardNo)throws Exception;
	List<BoardPreferDto> listFindByUserId(String userId) throws Exception;
}

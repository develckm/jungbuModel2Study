package model2_study.com.dao;

import java.util.List;

import model2_study.com.dto.BoardImgDto;

public interface BoardImgDao extends Dao<BoardImgDto, Integer>{
	int boardDelete(int boardNo) throws Exception;
	List<BoardImgDto> boardList(int boardNo) throws Exception; //최대 5개만 등록
	int boardCount(int boardNo) throws Exception; //해당하는 게시글에 몇개의 이미지가 등록되었는지
}

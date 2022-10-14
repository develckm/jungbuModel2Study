package model2_study.com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dao.BoardImgDao;
import model2_study.com.dao.BoardImgDaoImp;
import model2_study.com.dao.BoardPreferDao;
import model2_study.com.dao.BoardPreferDaoImp;
import model2_study.com.dao.ReplyDao;
import model2_study.com.dao.ReplyDaoImp;
import model2_study.com.dao.SpringBoardDB;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardImgDto;
import model2_study.com.dto.BoardPreferDto;
import model2_study.com.dto.ReplyDto;
import model2_study.com.dto.UserDto;

@WebServlet("/boardDetail.do")
public class BoardDetailServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String boardNo_str=req.getParameter("boardNo");
		HttpSession session=req.getSession();
		Object loginUser_obj=session.getAttribute("loginUser");
		BoardDto boardDto=null;
		String errorPage="./boardList.do";//실패시 리스트로 이동
		String forwardPage="./boardDetail.jsp"; //성공시 출력할 view 페이지
		BoardDao boardDao=null;
		ReplyDao replyDao=null;
		BoardImgDao boardImgDao=null;
		BoardPreferDao boardPreferDao=null;
		try {
			int boardNo=Integer.parseInt(boardNo_str);
			boardDao=new BoardDaoImp();
			replyDao=new ReplyDaoImp();
			boardImgDao=new BoardImgDaoImp();
			if(loginUser_obj!=null) {
				UserDto loginUser=(UserDto)loginUser_obj;
				boardPreferDao=new BoardPreferDaoImp();
				BoardPreferDto boardPrefer=
						boardPreferDao.detailFindbyUserIdAndBoardNo(loginUser.getUserId(), boardNo);
				if(boardPrefer!=null) {
					req.setAttribute("boardPrefer", boardPrefer);
				}
			}
			
			boardDto=boardDao.detail(boardNo);
			List<ReplyDto> replyList=replyDao.listFindByBoardNo(1, boardNo);
			List<BoardImgDto> boardImgList=boardImgDao.boardList(boardNo);
			
			boardDto.setReplyList(replyList);
			boardDto.setBoardImgList(boardImgList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(boardDao!=null)boardDao.close();
			if(replyDao!=null)replyDao.close();
			if(boardImgDao!=null)boardImgDao.close();
		}
		if(boardDto==null) {
			resp.sendRedirect(errorPage);
		}else {
			req.setAttribute("board", boardDto);
			req.getRequestDispatcher(forwardPage).forward(req, resp);
		}
		
	}
}

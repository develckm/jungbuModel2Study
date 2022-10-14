package model2_study.com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2_study.com.dao.BoardDao;
import model2_study.com.dao.BoardDaoImp;
import model2_study.com.dao.BoardPreferDao;
import model2_study.com.dao.BoardPreferDaoImp;
import model2_study.com.dto.BoardDto;
import model2_study.com.dto.BoardPreferDto;
import model2_study.com.dto.UserDto;

@WebServlet("/boardPreferDetail.do")
public class BoardPreferDetailServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("boardPreferDetail.do");
		String boardNo_str=req.getParameter("boardNo");
		Object loginUser_obj=req.getSession().getAttribute("loginUser");
		String forwardPage="./boardPreferDetail.jsp";
		BoardDto board=null;
		BoardPreferDto boardPrefer=null;
		BoardDao boardDao=null;
		BoardPreferDao boardPreferDao=null; 
		try {
			int boardNo=Integer.parseInt(boardNo_str);
			boardDao=new BoardDaoImp();
			System.out.println(boardNo);
			board=boardDao.detail(boardNo);
			System.out.println(board);
			if(loginUser_obj!=null) {
				UserDto loginUser=(UserDto)loginUser_obj;
				boardPreferDao=new BoardPreferDaoImp();
				boardPrefer=boardPreferDao.detailFindbyUserIdAndBoardNo(loginUser.getUserId(), boardNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		if(board!=null) {
			req.setAttribute("board", board);
			req.setAttribute("boardPrefer", boardPrefer);
			req.getRequestDispatcher(forwardPage).forward(req, resp);
		}else {
			resp.setStatus(404);
		}
		
	}
}
